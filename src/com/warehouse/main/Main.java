package com.warehouse.main;

import com.warehouse.exception.OutOfStockException;
import com.warehouse.exception.ProductNotFoundException;
import com.warehouse.model.Product;
import com.warehouse.reporting.ReportService;
import com.warehouse.repository.ProductRepository;
import com.warehouse.repository.ProductRepositoryImpl;
import com.warehouse.service.AuthenticationService;
import com.warehouse.service.WarehousesService;
import com.warehouse.service.WarehousesServiceImpl;

public class Main {
    public static void main(String[] args) {
        System.out.println("\n=== KHỞI TẠO HỆ THỐNG QUẢN LÝ KHO ===");

        // 1. Khởi tạo dịch vụ bảo mật & Repository
        AuthenticationService authService = new AuthenticationService();
        ProductRepository productRepo = new ProductRepositoryImpl();
        
        // Tải dữ liệu cũ (nếu có)
        com.warehouse.util.FileStorage.loadProducts(productRepo);
        
        // Thêm sản phẩm mẫu vào kho
        productRepo.save(new Product("SP01", "Laptop Dell XPS", 25000000, 10));
        productRepo.save(new Product("SP02", "Chuột Logitech MX Master", 2200000, 20));

        // 2. Khởi tạo Service chính
        WarehousesService warehouseService = new WarehousesServiceImpl(productRepo, authService);
        ReportService reportService = new ReportService(productRepo, warehouseService);

        // 3. Hiển thị báo cáo tồn kho ban đầu
        System.out.println("\n--- BÁO CÁO TỒN KHO BAN ĐẦU ---");
        reportService.printInventoryReport();

        // 4. --- TEST PHÂN QUYỀN: Nhân viên cố gắng nhập kho ---
        System.out.println("\n--- TEST 1: Tài khoản Nhân viên (Staff) ---");
        authService.login("nhanvien", "123456");
        warehouseService.importProduct("T01", "SP01", 10, authService.getCurrentUser().getFullName());
        authService.logout();

        // 5. --- TEST NGHIỆP VỤ: Thủ kho thực hiện Nhập / Xuất kho ---
        System.out.println("\n--- TEST 2: Tài khoản Thủ kho (Warehouse Manager) ---");
        authService.login("thukho", "123456");
        
        try {
            // Thủ kho nhập kho (Đã đủ 4 tham số)
            warehouseService.importProduct("T02", "SP01", 5, authService.getCurrentUser().getFullName());
            
            // Thủ kho xuất kho
            warehouseService.exportProduct("T03", "SP02", 5, authService.getCurrentUser().getFullName());
        } catch (ProductNotFoundException | OutOfStockException e) {
            System.err.println("Lỗi giao dịch: " + e.getMessage());
        }
        
        authService.logout();

        // 6. Hiển thị báo cáo tổng kết sau giao dịch
        System.out.println("\n--- BÁO CÁO TỒN KHO SAU GIAO DỊCH ---");
        reportService.printInventoryReport();
        
        System.out.println("\n--- BÁO CÁO LỊCH SỬ GIAO DỊCH ---");
        reportService.printTransactionReport();
    }
}