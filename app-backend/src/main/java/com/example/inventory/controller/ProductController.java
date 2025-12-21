// controller/ProductController.java
package com.example.inventory.controller;

import com.example.inventory.entity.Product;
import com.example.inventory.service.ProductService;

import jakarta.servlet.http.HttpSession;
import jakarta.validation.Valid;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.math.BigDecimal;

@Controller
public class ProductController {

    private final ProductService productService;

    public ProductController(ProductService productService) {
        this.productService = productService;
    }

    // ダッシュボード（在庫一覧）
    @GetMapping({"/", "/dashboard"})
    public String dashboard(Model model, HttpSession session) {
        System.out.println("[DASH] sessionId=" + session.getId()
        + " LOGIN_USER_ID=" + session.getAttribute("LOGIN_USER_ID")
        + " LOGIN_USERNAME=" + session.getAttribute("LOGIN_USERNAME")
        + " LOGIN_ROLE=" + session.getAttribute("LOGIN_ROLE"));

        model.addAttribute("products", productService.findAll());
        return "dashboard";
    }

    // 新規商品登録フォーム表示
    @GetMapping("/products/new")
    public String newProductForm(Model model) {
        model.addAttribute("product", new Product());
        model.addAttribute("title", "新規商品登録");
        return "product-form";
    }

    // 新規登録
    @PostMapping("/products")
    public String createProduct(@RequestParam String name,
                                @RequestParam String category,
                                @RequestParam BigDecimal price,
                                @RequestParam Integer stock) {

        Product product = new Product();
        product.setName(name);
        product.setCategory(category);
        product.setPrice(price);
        product.setStock(stock);
        productService.save(product);

        return "redirect:/dashboard";
    }

    // 編集フォーム表示
    @GetMapping("/products/{id}/edit")
    public String editProductForm(@PathVariable Long id, Model model) {
        Product product = productService.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("指定された商品が見つかりません: " + id));
        model.addAttribute("product", product);
        model.addAttribute("title", "商品編集");
        return "product-form";
    }

    // 更新
    @PostMapping("/products/{id}")
    public String updateProduct(@PathVariable Long id,
                                @RequestParam String name,
                                @RequestParam String category,
                                @RequestParam BigDecimal price,
                                @RequestParam Integer stock) {

        Product product = productService.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("指定された商品が見つかりません: " + id));
        product.setName(name);
        product.setCategory(category);
        product.setPrice(price);
        product.setStock(stock);
        productService.save(product);

        return "redirect:/dashboard";
    }

    // 削除
    @PostMapping("/products/{id}/delete")
    public String deleteProduct(@PathVariable Long id) {
        productService.deleteById(id);
        return "redirect:/dashboard";
    }
}
