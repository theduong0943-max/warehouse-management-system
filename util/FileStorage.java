package com.warehouse.util;
import com.warehouse.model.Product;
import com.warehouse.repository.ProductRepository;

import java.io.*;
import java.util.List;

public class FileStorage {
    private static final String PRODUCT_FILE = "products.txt";

    // 1. Lưu danh sách sản phẩm ra file text
    public static void saveProducts(List<Product> products) {
        try (PrintWriter pw = new PrintWriter(new FileWriter(PRODUCT_FILE))) {
            for (Product p : products) {
                pw.println(p.getId() + "," + p.getName() + "," + p.getPrice() + "," + p.getQuantity() + "," + p.getMinQuantity());
            }
            System.out.println("[HỆ THỐNG] Đã lưu dữ liệu sản phẩm thành công vào file!");
        } catch (IOException e) {
            System.err.println("Lỗi khi lưu sản phẩm: " + e.getMessage());
        }
    }

    // 2. Đọc danh sách sản phẩm từ file khi khởi động ứng dụng
    public static void loadProducts(ProductRepository productRepo) {
        File file = new File(PRODUCT_FILE);
        if (!file.exists()) {
            return; // Nếu file chưa tồn tại thì bỏ qua
        }

        try (BufferedReader br = new BufferedReader(new FileReader(file))) {
            String line;
            while ((line = br.readLine()) != null) {
                String[] parts = line.split(",");
                if (parts.length >= 5) {
                    String id = parts[0];
                    String name = parts[1];
                    double price = Double.parseDouble(parts[2]);
                    int quantity = Integer.parseInt(parts[3]);
                    int minQuantity = Integer.parseInt(parts[4]);

                    productRepo.save(new Product(id, name, price, quantity, minQuantity));
                }
            }
            System.out.println("[HỆ THỐNG] Đã tải dữ liệu sản phẩm từ file thành công!");
        } catch (IOException e) {
            System.err.println("Lỗi khi đọc file sản phẩm: " + e.getMessage());
        }
    }
}