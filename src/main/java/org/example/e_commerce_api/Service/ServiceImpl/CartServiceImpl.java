package org.example.e_commerce_api.Service.ServiceImpl;

import lombok.RequiredArgsConstructor;
import org.example.e_commerce_api.Config.CustomUserDetails;
import org.example.e_commerce_api.DTO.Request.Cart_Request.AddCartItemRequest;
import org.example.e_commerce_api.DTO.Request.Cart_Request.UpdateCartItemRequest;
import org.example.e_commerce_api.DTO.Response.CartItemResponse;
import org.example.e_commerce_api.DTO.Response.CartResponse;
import org.example.e_commerce_api.Entity.Cart_items;
import org.example.e_commerce_api.Entity.Carts;
import org.example.e_commerce_api.Entity.Products;
import org.example.e_commerce_api.Entity.Users;
import org.example.e_commerce_api.Exception.*;
import org.example.e_commerce_api.Mapper.CartItemMapper;
import org.example.e_commerce_api.Repository.CartItemRepository;
import org.example.e_commerce_api.Repository.CartRepository;
import org.example.e_commerce_api.Repository.ProductRepository;
import org.example.e_commerce_api.Repository.UserRepository;
import org.example.e_commerce_api.Service.CartService;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
@Transactional
public class CartServiceImpl implements CartService {

    private final CartRepository cartRepository;
    private final CartItemRepository cartItemRepository;
    private final ProductRepository productRepository;
    private final UserRepository userRepository;

    private Users getCurrentUser() {

        Authentication authentication =
                SecurityContextHolder.getContext().getAuthentication();

        CustomUserDetails userDetails =
                (CustomUserDetails) authentication.getPrincipal();

        return userRepository.findById(userDetails.getId())
                .orElseThrow(UserNotFoundException::new);
    }

    private Carts getCurrentUserCart() {

        Users user = getCurrentUser();

        return cartRepository.findByUsersId(user.getId())
                .orElseGet(() -> {

                    Carts cart = Carts.builder()
                            .users(user)
                            .build();

                    return cartRepository.save(cart);
                });
    }

    @Transactional(readOnly = true)
    @Override
    public CartResponse viewMyCart() {

        Users user = getCurrentUser();

        Carts cart = getCurrentUserCart();

        List<Cart_items> cartItems =
                cartItemRepository.findByCartsId(
                        cart.getId());

        List<CartItemResponse> items =
                cartItems.stream()
                        .map(CartItemMapper::toResponse)
                        .toList();

        BigDecimal total = items.stream()
                .map(CartItemResponse::getSubtotal)
                .reduce(BigDecimal.ZERO, BigDecimal::add);

        return CartResponse.builder()
                .id(cart.getId())
                .userId(user.getId())
                .cartItems(items)
                .total(total)
                .build();
    }
    @Override
    public CartItemResponse addItemToCart(AddCartItemRequest request) {

        Carts cart = getCurrentUserCart();

        Products product = productRepository.findById(
                        request.getProductId())
                .orElseThrow(ProductNotFoundException::new);

        validateStockAvailability(
                request.getQuantity(),
                product.getStock_quantity()
        );

        Optional<Cart_items> existingItem =
                cartItemRepository.findByCartsIdAndProductsId(
                        cart.getId(),
                        product.getId()
                );

        Cart_items item;

        if (existingItem.isPresent()) {

            item = existingItem.get();

            int newQuantity =
                    item.getQuantity() + request.getQuantity();

            validateStockAvailability(
                    newQuantity,
                    product.getStock_quantity()
            );

            item.setQuantity(newQuantity);

        } else {

            item = Cart_items.builder()
                    .carts(cart)
                    .products(product)
                    .quantity(request.getQuantity())
                    .build();
        }

        Cart_items savedItem = cartItemRepository.save(item);

        return CartItemMapper.toResponse(savedItem);
    }

    @Override
    public CartItemResponse updateCartItem(
            Long itemId,
            UpdateCartItemRequest request) {

        Carts cart = getCurrentUserCart();

        Cart_items item = cartItemRepository.findById(itemId)
                .orElseThrow(CartItemNotFoundException::new);

        if (!item.getCarts().getId().equals(cart.getId())) {
            throw new UnauthorizedCartAccessException();
        }

        Products product = item.getProducts();

        validateStockAvailability(
                request.getQuantity(),
                product.getStock_quantity()
        );

        item.setQuantity(request.getQuantity());

        Cart_items savedItem = cartItemRepository.save(item);

        return CartItemMapper.toResponse(savedItem);
    }

    @Override
    public void deleteCartItem(Long itemId) {

        Carts cart = getCurrentUserCart();

        Cart_items item = cartItemRepository.findById(itemId)
                .orElseThrow(CartItemNotFoundException::new);

        if (!item.getCarts().getId().equals(cart.getId())) {
            throw new UnauthorizedCartAccessException();
        }

        cartItemRepository.delete(item);
    }

    private void validateStockAvailability(
            Integer requestedQuantity,
            Integer availableStock) {

        if (requestedQuantity > availableStock) {
            throw new InsufficientStockException();
        }
    }
}