package com.warehouse.model;

public class ExportTransaction extends Transaction {
    public ExportTransaction(String transactionId, Product product, int quantity) {
        super(transactionId, product, quantity);
    }

    @Override
    public String getType() {
        return "XUẤT KHO";
    }
}