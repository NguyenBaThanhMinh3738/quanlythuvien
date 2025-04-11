/*
 * Lớp `imageProcess` để xử lý hình ảnh (scale hình ảnh, lấy ảnh từ CSDL, cập nhật ảnh).
 */
package util;  // Khai báo package `util`

import data.ConnectDB;  // Import lớp `ConnectDB` để dùng kết nối đến cơ sở dữ liệu
import static data.ConnectDB.con;  // Import biến kết nối `con` từ `ConnectDB`
import static data.ConnectDB.stmt;  // Import đối tượng `stmt` để thực hiện câu lệnh SQL
import java.awt.Image;  // Thư viện xử lý hình ảnh
import java.io.InputStream;  // Đọc ảnh từ luồng dữ liệu
import java.sql.PreparedStatement;  // Đối tượng để thực hiện các câu lệnh SQL dạng tham số
import java.sql.ResultSet;  // Đối tượng lưu kết quả trả về từ câu lệnh `SELECT`
import java.sql.SQLException;  // Xử lý các ngoại lệ về SQL
import javax.swing.ImageIcon;  // Đối tượng biểu diễn hình ảnh dạng icon (dùng trong JLabel)
import javax.swing.JLabel;  // Thẻ JLabel để hiển thị ảnh trong giao diện

/**
 * Lớp `imageProcess` cung cấp các hàm xử lý hình ảnh.
 */
public class imageProcess {

    // Constructor lớp `imageProcess`, tạo kết nối database bằng cách khởi tạo `ConnectDB`
    public imageProcess() {
        new ConnectDB();  // Tạo kết nối đến database
    }

    // Phương thức scaleImage: điều chỉnh kích thước ảnh theo kích thước JLabel
    public ImageIcon scaleImage(ImageIcon icon, JLabel label) {
        Image img = icon.getImage();  // Lấy ảnh từ ImageIcon

        // Kích thước ảnh gốc
        float w_img = icon.getIconWidth();  // Chiều rộng ảnh gốc
        float h_img = icon.getIconHeight();  // Chiều cao ảnh gốc

        // Kích thước của JLabel (khung chứa ảnh)
        float w = label.getWidth();  // Chiều rộng JLabel
        float h = label.getHeight();  // Chiều cao JLabel

        // Tính tỷ lệ khung ảnh (label) và tỷ lệ ảnh gốc
        float tlK = w / h;  // Tỷ lệ chiều rộng/chiều cao của JLabel
        System.err.println(tlK);  // In tỷ lệ khung ra màn hình console
        float tl = w_img / h_img;  // Tỷ lệ chiều rộng/chiều cao của ảnh

        // Điều chỉnh kích thước ảnh theo tỷ lệ khung chứa ảnh
        if (tl >= tlK) {  // Nếu tỷ lệ ảnh >= tỷ lệ khung
            w_img = w;  // Đặt chiều rộng ảnh bằng chiều rộng khung
            h_img = w_img / tl;  // Tính lại chiều cao ảnh
        } else {  // Nếu tỷ lệ ảnh < tỷ lệ khung
            h_img = h;  // Đặt chiều cao ảnh bằng chiều cao khung
            w_img = h_img * tl;  // Tính lại chiều rộng ảnh
        }

        // Làm tròn kích thước ảnh về số nguyên
        int wf = Math.round(w_img);  // Làm tròn chiều rộng
        int hf = Math.round(h_img);  // Làm tròn chiều cao

        // In thông tin kích thước ảnh sau khi scale
        System.out.print("(" + w + "," + h + ") " + tl + " => ");
        System.out.println("(" + wf + "," + hf + ")");

        // Thực hiện scale ảnh với kích thước mới
        Image sImg = img.getScaledInstance(wf, hf, Image.SCALE_SMOOTH);
        icon = new ImageIcon(sImg);  // Tạo lại ImageIcon từ ảnh đã scale
        return icon;  // Trả về ảnh đã điều chỉnh kích thước
    }

    // Phương thức getImageByID: lấy ảnh từ CSDL theo mã `maTc`
    public byte[] getImageByID(String id) {
        byte[] bin = null;  // Mảng byte để lưu ảnh (dạng nhị phân)
        String sq = "SELECT `hinhanh` FROM `sach` WHERE `maTc` = '" + id + "'";  // Câu lệnh SQL lấy ảnh theo mã
        try {
            ResultSet rs = stmt.executeQuery(sq);  // Thực hiện câu lệnh SQL
            while (rs.next()) {  // Duyệt kết quả trả về
                bin = rs.getBytes("hinhanh");  // Lấy ảnh dưới dạng byte[]
            }
        } catch (NumberFormatException | SQLException e) {
            System.err.println("Lỗi khi lấy ảnh: " + e.getMessage());  // In thông báo lỗi nếu có
        }
        return bin;  // Trả về ảnh dạng mảng byte
    }

    // Phương thức updateImage: cập nhật ảnh trong CSDL theo mã `maTc`
    public void updateImage(String id, InputStream is) {
        try {
            // Câu lệnh SQL để cập nhật ảnh
            PreparedStatement ps = con.prepareStatement("UPDATE `sach` SET `hinhanh`= ? WHERE `maTc`= '" + id + "'");
            ps.setBlob(1, is);  // Truyền ảnh dạng InputStream vào câu lệnh SQL
            ps.executeUpdate();  // Thực thi câu lệnh cập nhật ảnh
            System.out.println("Cập nhật ảnh thành công cho mã: " + id);
        } catch (SQLException e) {
            System.err.println("Lỗi khi cập nhật ảnh: " + e.getMessage());  // In thông báo lỗi nếu có
        }
    }
}
