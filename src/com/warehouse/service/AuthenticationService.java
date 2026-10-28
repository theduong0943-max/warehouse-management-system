package com.warehouse.service;

import com.warehouse.model.Role;
import com.warehouse.model.User;
import java.util.HashMap;
import java.util.Map;

public class AuthenticationService {
    private Map<String, User> userDatabase = new HashMap<>();
    private User currentUser = null; // Lưu phiên làm việc của người dùng hiện tại

    public AuthenticationService() {
        // Khởi tạo sẵn tài khoản mẫu cho các vai trò khác nhau
        userDatabase.put("admin", new User("admin", "123456", "Quản Trị Viên", Role.ADMIN));
        userDatabase.put("thukho", new User("thukho", "123456", "Nguyễn Văn Thủ Kho", Role.WAREHOUSE_MANAGER));
        userDatabase.put("nhanvien", new User("nhanvien", "123456", "Trần Văn Nhân Viên", Role.STAFF));
    }

    // Xử lý đăng nhập
    public boolean login(String username, String password) {
        User user = userDatabase.get(username);
        if (user != null && user.getPassword().equals(password)) {
            currentUser = user;
            System.out.println("[ĐĂNG NHẬP THÀNH CÔNG] Chào mừng " + currentUser.getFullName() + " (" + currentUser.getRole() + ")");
            return true;
        }
        System.err.println("[LỖI ĐĂNG NHẬP] Sai tên đăng nhập hoặc mật khẩu!");
        return false;
    }

    // Xử lý đăng xuất
    public void logout() {
        if (currentUser != null) {
            System.out.println("[ĐĂNG XUẤT] Người dùng " + currentUser.getFullName() + " đã thoát hệ thống.");
        }
        currentUser = null;
    }

    // Lấy thông tin user đang đăng nhập
    public User getCurrentUser() {
        return currentUser;
    }

    // Kiểm tra quyền hạn (Role-based Authorization)
    public boolean hasPermission(Role requiredRole) {
        if (currentUser == null) {
            System.err.println("[CẢNH BÁO] Chưa đăng nhập hệ thống!");
            return false;
        }
        // Nếu là ADMIN thì có toàn quyền, ngược lại phải khớp đúng vai trò yêu cầu
        if (currentUser.getRole() == Role.ADMIN) return true;
        return currentUser.getRole() == requiredRole;
    }
}