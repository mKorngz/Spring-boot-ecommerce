package org.example.e_commerce_api.Service.ServiceImpl;

import lombok.RequiredArgsConstructor;
import org.example.e_commerce_api.Config.CustomUserDetails;
import org.example.e_commerce_api.Constant.OrderStatusEnum;
import org.example.e_commerce_api.DTO.Request.Order_Request.CreateOrderRequest;
import org.example.e_commerce_api.DTO.Request.Order_Request.UpdateOrderStatusRequest;
import org.example.e_commerce_api.DTO.Response.OrderItemResponse;
import org.example.e_commerce_api.DTO.Response.OrderResponse;
import org.example.e_commerce_api.Entity.*;
import org.example.e_commerce_api.Exception.*;
import org.example.e_commerce_api.Repository.*;
import org.example.e_commerce_api.Service.OrderService;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
@Transactional
public class OrderServiceImpl implements OrderService {

    private final OrderRepository orderRepository;
    private final CartItemRepository cartItemRepository;
    private final ProductRepository productRepository;
    private final UserRepository userRepository;

    @Override
    public OrderResponse createOrder(CreateOrderRequest request) {

        Users user = getCurrentUser();

        List<Cart_items> selectedItems =
                cartItemRepository.findAllByIdIn(
                        request.getCartItemIds());

        if (selectedItems.isEmpty()) {
            throw new EmptyOrderException();
        }

        for (Cart_items item : selectedItems) {

            if (!item.getCarts()
                    .getUsers()
                    .getId()
                    .equals(user.getId())) {

                throw new UnauthorizedCartAccessException();
            }
        }

        double totalAmount = 0.0;

        Orders order = Orders.builder()
                .users(user)
                .order_number(generateOrderNumber())
                .orderStatusEnum(OrderStatusEnum.PENDING)
                .build();

        List<Order_items> orderItems = new ArrayList<>();

        for (Cart_items cartItem : selectedItems) {

            Products product = cartItem.getProducts();

            if (cartItem.getQuantity()
                    > product.getStock_quantity()) {

                throw new InsufficientStockException(
                        product.getName()
                                + " is out of stock");
            }

            product.setStock_quantity(
                    product.getStock_quantity()
                            - cartItem.getQuantity());

            productRepository.save(product);

            BigDecimal subTotal =
                    product.getPrice()
                            .multiply(
                                    BigDecimal.valueOf(
                                            cartItem.getQuantity()));

            totalAmount += subTotal.doubleValue();

            Order_items orderItem =
                    Order_items.builder()
                            .orders(order)
                            .products(product)
                            .product_name(product.getName())
                            .price(product.getPrice())
                            .quantity(cartItem.getQuantity())
                            .build();

            orderItems.add(orderItem);
        }

        order.setTotal_amount(totalAmount);
        order.setOrderItems(orderItems);

        Orders savedOrder =
                orderRepository.save(order);

        cartItemRepository.deleteAll(selectedItems);

        return mapToResponse(savedOrder);
    }
    @Override
    @Transactional(readOnly = true)
    public List<OrderResponse> getMyOrders() {

        Users user = getCurrentUser();

        return orderRepository
                .findByUsersId(user.getId())
                .stream()
                .map(this::mapToResponse)
                .toList();
    }
    @Override
    @Transactional(readOnly = true)
    public OrderResponse getOrderById(Long id) {

        Users user = getCurrentUser();

        Orders order = orderRepository.findById(id)
                .orElseThrow(OrderNotFoundException::new);

        if (!order.getUsers().getId().equals(user.getId())) {
            throw new UnauthorizedOrderAccessException();
        }

        return mapToResponse(order);
    }
    @Override
    @Transactional(readOnly = true)
    public List<OrderResponse> getAllOrders() {

        return orderRepository.findAll()
                .stream()
                .map(this::mapToResponse)
                .toList();
    }
    @Override
    public OrderResponse updateOrderStatus(
            Long id,
            UpdateOrderStatusRequest request) {

        Orders order = orderRepository.findById(id)
                .orElseThrow(OrderNotFoundException::new);

        validateStatusTransition(
                order.getOrderStatusEnum(),
                request.getStatus());

        order.setOrderStatusEnum(
                request.getStatus());

        Orders savedOrder =
                orderRepository.save(order);

        return mapToResponse(savedOrder);
    }
    //=================================HELPER METHOD=================================================
    //get current user
    private Users getCurrentUser() {

        Authentication authentication =
                SecurityContextHolder.getContext().getAuthentication();

        CustomUserDetails userDetails =
                (CustomUserDetails) authentication.getPrincipal();

        return userRepository.findById(userDetails.getId())
                .orElseThrow(UserNotFoundException::new);
    }
    //valid status transaction
    private void validateStatusTransition(
            OrderStatusEnum current,
            OrderStatusEnum next) {

        switch (current) {

            case PENDING -> {

                if (next != OrderStatusEnum.CONFIRMED
                        && next != OrderStatusEnum.CANCELLED) {

                    throw new InvalidOrderStatusTransitionException();
                }
            }

            case CONFIRMED -> {

                if (next != OrderStatusEnum.COMPLETED
                        && next != OrderStatusEnum.CANCELLED) {

                    throw new InvalidOrderStatusTransitionException();
                }
            }

            case COMPLETED, CANCELLED ->

                    throw new InvalidOrderStatusTransitionException(
                            "Order can no longer be modified");
        }
    }
    private String generateOrderNumber() {

        return "ORD-" + System.currentTimeMillis();
    }
    private OrderResponse mapToResponse(
            Orders order) {

        List<OrderItemResponse> items =
                order.getOrderItems()
                        .stream()
                        .map(item -> {

                            BigDecimal subTotal =
                                    item.getPrice()
                                            .multiply(
                                                    BigDecimal.valueOf(
                                                            item.getQuantity()));

                            return OrderItemResponse.builder()
                                    .id(item.getId())
                                    .productId(
                                            item.getProducts().getId())
                                    .product_name(
                                            item.getProduct_name())
                                    .price(item.getPrice())
                                    .quantity(item.getQuantity())
                                    .subTotal(subTotal)
                                    .build();
                        })
                        .toList();

        return OrderResponse.builder()
                .id(order.getId())
                .order_number(order.getOrder_number())
                .userId(order.getUsers().getId())
                .total_amount(order.getTotal_amount())
                .status(order.getOrderStatusEnum())
                .createdAt(order.getCreatedAt())
                .items(items)
                .build();
    }
//========================================================================================================
}

