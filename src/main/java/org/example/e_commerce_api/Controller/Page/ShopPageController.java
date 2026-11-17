package org.example.e_commerce_api.Controller.Page;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
@Controller
@RequestMapping("/shop")
public class ShopPageController {

    @GetMapping
    public String shop() {
        return "shop/shop";
    }

    @GetMapping("/cart")
    public String cart() {
        return "shop/cart";
    }

    @GetMapping("/orders")
    public String ordersUsers() {
        return "shop/ordersUsers";
    }
}

