/*
 * Đây là file main chứa phương thức `main()` để khởi động chương trình
 */
package quanlyhieusach;  // Khai báo package chính của chương trình

import views.loginFrame;  // Import lớp `loginFrame` từ package `views`

/**
 * Lớp QuanLyHieuSach chứa phương thức main() để chạy ứng dụng quản lý hiệu sách
 */
public class QuanLyHieuSach {

    /**
     * Phương thức main là điểm bắt đầu của ứng dụng
     * @param args là các tham số truyền từ command line khi chạy chương trình
     */
    public static void main(String[] args) {
        // Tạo một đối tượng `loginFrame` là cửa sổ đăng nhập cho người dùng
        loginFrame loginFrame = new loginFrame();

        // Hiển thị giao diện đăng nhập bằng cách đặt thuộc tính "visible" thành true
        loginFrame.setVisible(true);
    }
}
