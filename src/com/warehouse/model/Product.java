package com.warehouse.model;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

@Entity
@Table(name = "products")
public class Product {

    @Id
    @Column(name = "product_id", length = 50, nullable = false)
    private String productId; 

    @Column(name = "name", nullable = false)
    private String name;

    @Column(name = "price", nullable = false)
    private double price;

    @Column(name = "quantity", nullable = false)
    private int quantity;

    @Column(name = "min_quantity")
    private int minQuantity;

    // 1. Constructor mặc định (Bắt buộc đối với JPA)
    public Product() {
    }

    // Constructor đầy đủ tham số
    public Product(String productId, String name, double price, int quantity, int minQuantity) {
        this.productId = productId;
        this.name = name;
        this.price = price;
        this.quantity = quantity;
        this.minQuantity = minQuantity;
    }

    // Getter cho productId (để khớp với code gọi product.getProductId())
    public String getProductId() {
        return productId;
    }

    public void setProductId(String productId) {
        this.productId = productId;
    }

    // Giữ nguyên các getter/setter cũ
    public String getId() {
        return productId;
    }

    public String getName() {
        return name;
    }

    public double getPrice() {
        return price;
    }

    public int getQuantity() {
        return quantity;
    }

    public void addQuantity(int amount) {
        this.quantity += amount;
    }

    public void reduceQuantity(int amount) {
        this.quantity -= amount;
    }

    public int getMinQuantity() {
        return minQuantity;
    }

    public void setMinQuantity(int minQuantity) {
        this.minQuantity = minQuantity;
    }

    @Override
    public String toString() {
        return String.format("[%s] %s - Giá: %,.0f VNĐ - Tồn kho: %d", productId, name, price, quantity);
    }
}