package com.warehouse.model;

public enum Role {
    ADMIN("Quản lý toàn bộ hệ thống, cấu hình và báo cáo"),
    WAREHOUSE_MANAGER("Thủ kho: Thực hiện nhập/xuất kho và kiểm kê"),
    STAFF("Nhân viên: Chỉ được xem danh sách và truy vấn tồn kho");

    private final String description;

    Role(String description) {
        this.description = description;
    }

    public String getDescription() {
        return description;
    }
}