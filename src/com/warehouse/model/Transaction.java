package com.warehouse.model;

import java.time.LocalDateTime;

public abstract class Transaction {

    private final String transactionId;
    private final Product product;
    private final int quantity;
    private final LocalDateTime timestamp;

    // Đã sửa 'quantit' thành 'quantity' ở tham số truyền vào
    public Transaction(String transactionId, Product product, int quantity) {
        this.transactionId = transactionId;
        this.product = product;
        this.quantity = quantity;
        this.timestamp = LocalDateTime.now();
    }

    public String getTransactionId() {
        return transactionId;
    }

    public Product getProduct() {
        return product;
    }

    public int getQuantity() {
        return quantity;
    }

    public LocalDateTime getTimestamp() {
        return timestamp;
    }

    public abstract String getType();

    @Override
    public String toString() {
        return String.format("[%s] Loại: %s | SP: %s | SL: %d | Thời gian: %s",
                transactionId, getType(), product.getName(), quantity, timestamp);
    }
}