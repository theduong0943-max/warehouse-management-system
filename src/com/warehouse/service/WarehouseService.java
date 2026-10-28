package com.warehouse.service;

import com.warehouse.exception.OutOfStockException;
import com.warehouse.exception.ProductNotFoundException;
import com.warehouse.model.Transaction;

import java.util.List;

public interface WarehouseService {
    void importProduct(String transactionId, String productId, int quantity) throws ProductNotFoundException;
    void exportProduct(String transactionId, String productId, int quantity) throws ProductNotFoundException, OutOfStockException;
    List<Transaction> getTransactionHistory();
}