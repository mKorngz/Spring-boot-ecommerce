package org.example.e_commerce_api.Controller.Page;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/admin")
public class AdminPageController {

    @GetMapping("/dashboard")
    public String dashboard() {
        return "admin/dashboard";
    }
    @GetMapping("/products")
    public String products() {
        return "admin/products";
    }
    @GetMapping("/categories")
    public String categories() {
        return "admin/categories";
    }
    @GetMapping("/orders")
    public String orders() {
        return "admin/orders";
    }
}