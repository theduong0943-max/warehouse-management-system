package com.warehouse.model;

public class ImportTransaction extends Transaction {
    public ImportTransaction(String transactionId, Product product, int quantity) {
        super(transactionId, product, quantity);
    }

    @Override
    public String getType() {
        return "NHẬP KHO";
    }
}