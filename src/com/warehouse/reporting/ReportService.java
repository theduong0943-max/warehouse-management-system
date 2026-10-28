package com.warehouse.reporting;

import com.warehouse.model.Product;
import com.warehouse.model.Transaction;
import com.warehouse.repository.ProductRepository;
import com.warehouse.service.WarehouseService;

import java.util.List;

public class ReportService {
    private final ProductRepository productRepository;
    private final WarehouseService warehouseService;

    public ReportService(ProductRepository productRepository, WarehouseService warehouseService) {
        this.productRepository = productRepository;
        this.warehouseService = warehouseService;
    }

    public void printInventoryReport() {
        System.out.println("\n========= BÁO CÁO TỒN KHO =========");
        List<Product> products = productRepository.findAll();
        if (products.isEmpty()) {
            System.out.println("Kho hàng trống.");
        } else {
            products.forEach(System.out::println);
        }
        System.out.println("====================================");
    }

    public void printTransactionReport() {
        System.out.println("\n====== LỊCH SỬ GIAO DỊCH XUẤT/NHẬP ======");
        List<Transaction> transactions = warehouseService.getTransactionHistory();
        if (transactions.isEmpty()) {
            System.out.println("Chưa có giao dịch nào.");
        } else {
            transactions.forEach(System.out::println);
        }
        System.out.println("=========================================");
    }
}