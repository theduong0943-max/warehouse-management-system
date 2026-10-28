package com.warehouse.service;

import com.warehouse.exception.OutOfStockException;
import com.warehouse.exception.ProductNotFoundException;
import com.warehouse.model.ExportTransaction;
import com.warehouse.model.ImportTransaction;
import com.warehouse.model.Product;
import com.warehouse.model.Role;
import com.warehouse.model.Transaction;
import com.warehouse.repository.ProductRepository;
import java.util.ArrayList;
import java.util.List;

public class WarehousesServiceImpl implements WarehousesService {
    private final ProductRepository productRepository;
    private final List<Transaction> history = new ArrayList<>();
    private final AuthenticationService authService; // Khai báo service bảo mật

    // Constructor đã được gán đầy đủ giá trị
    public WarehousesServiceImpl(ProductRepository productRepository, AuthenticationService authService) {
        this.productRepository = productRepository;
        this.authService = authService;
    }

    @Override
    public synchronized void importProduct(String transactionId, String productId, int quantity, String performedBy) 
            throws ProductNotFoundException {
        // Kiểm tra quyền: Chỉ WAREHOUSE_MANAGER hoặc ADMIN mới được nhập kho
        if (!authService.hasPermission(Role.WAREHOUSE_MANAGER) && !authService.hasPermission(Role.ADMIN)) {
            System.err.println("[TỪ CHỐI TRUY CẬP] Tài khoản " + authService.getCurrentUser().getFullName() + " không có quyền nhập kho.");
            return;
        }

        Product product = productRepository.findById(productId)
                .orElseThrow(() -> new ProductNotFoundException("Sản phẩm mã " + productId + " không tồn tại."));

        product.addQuantity(quantity);

        // ĐÃ SỬA: Truyền đối tượng `product` vào thay vì `productId`
        Transaction trans = new ImportTransaction(transactionId, product, quantity, performedBy);
        history.add(trans);
        System.out.println("[NHẬP HÀNG THÀNH CÔNG] NV: " + performedBy + " | Số lượng: " + quantity + " | Sản phẩm: " + product.getName());
    }

    @Override
    public synchronized void exportProduct(String transactionId, String productId, int quantity, String performedBy) 
            throws ProductNotFoundException, OutOfStockException {
        // Kiểm tra quyền: Chỉ WAREHOUSE_MANAGER hoặc ADMIN mới được xuất kho
        if (!authService.hasPermission(Role.WAREHOUSE_MANAGER) && !authService.hasPermission(Role.ADMIN)) {
            System.err.println("[TỪ CHỐI TRUY CẬP] Tài khoản " + authService.getCurrentUser().getFullName() + " không có quyền xuất kho.");
            return;
        }

        Product product = productRepository.findById(productId)
                .orElseThrow(() -> new ProductNotFoundException("Sản phẩm mã " + productId + " không tồn tại."));

        // Kiểm tra số lượng tồn kho
        if (product.getQuantity() < quantity) {
            throw new OutOfStockException("Sản phẩm " + product.getName() + " không đủ số lượng trong kho. Tồn kho hiện tại: " + product.getQuantity());
        }

        product.subtractQuantity(quantity);

        // ĐÃ SỬA: Truyền đối tượng `product` vào thay vì `productId`
        Transaction trans = new ExportTransaction(transactionId, product, quantity, performedBy);
        history.add(trans);
        System.out.println("[XUẤT HÀNG THÀNH CÔNG] NV: " + performedBy + " | Số lượng: " + quantity + " | Sản phẩm: " + product.getName());
    }
}   