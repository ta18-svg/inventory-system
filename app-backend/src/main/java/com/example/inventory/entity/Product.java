package com.example.inventory.entity;

import jakarta.persistence.*;
import java.math.BigDecimal;

@Entity
@Table(name = "products")
public class Product {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY) // 一意のID自動採番
    private Long id;

    @Column(nullable = false)
    private String name;         // 商品名

    @Column(nullable = false)
    private String category;     // カテゴリ

    @Column(nullable = false)
    private BigDecimal price;    // 価格

    @Column(nullable = false)
    private Integer stock;       // 在庫数

    public Product() {}

    // getter/setter
    public Long getId() { return id; }

    public String getName() { return name; }
    public void setName(String name) { this.name = name; }

    public String getCategory() { return category; }
    public void setCategory(String category) { this.category = category; }

    public BigDecimal getPrice() { return price; }
    public void setPrice(BigDecimal price) { this.price = price; }

    public Integer getStock() { return stock; }
    public void setStock(Integer stock) { this.stock = stock; }
}
