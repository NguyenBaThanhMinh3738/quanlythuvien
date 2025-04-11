/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package controllers;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import data.ConnectDB;
import static data.ConnectDB.stmt;
import java.sql.SQLException;
import models.Sach;

public class sachController {
    loaiController loaiCTL = new loaiController();
    nxbController nxbCTL = new nxbController();
    public sachController() {
    }
    //Ham dem
    public int countTC(String maLoai, boolean all){
        int i = 0;
        String sq = "Select count(`maTc`) from `sach`";
        if(maLoai.equals("")){
            if(all == false)
                sq = "SELECT count(`maTc`) FROM `sach` where `soLuong` > 0";
        }else{
            if(all == false){
                sq = "SELECT count(`maTc`) FROM `sach` where `soLuong` > 0 and `maLoai` = '" + maLoai +"'";
            }else{
                sq = "SELECT count(`maTc`) FROM `sach` where `maLoai` = '" + maLoai +"'";
            }
        }
        try {
            ResultSet rs = stmt.executeQuery(sq);
            while(rs.next()){
                i = Integer.parseInt(rs.getString(1));
            }
        } catch (Exception e) {
            System.err.println(e.getMessage());
        }
        System.err.println("i: " + i);
        return i;
    }
    
    //Ham lay du lieu ve model
    public Sach[] getData(String maLoai, boolean all){
        int i = -1;
        Sach[] tc = new Sach[countTC("",true)];
        String sq = "SELECT * FROM `sach`";
        if(maLoai.equals("")){
            if(all == false)
            {
                sq = "SELECT * FROM `sach` where `soLuong` > 0";
                tc = new Sach[countTC("",false)];
            }
                
        }else{
            if(all == false){
                sq = "SELECT * FROM `sach` where `soLuong` > 0 and `maLoai` = '" + maLoai +"'";
                tc = new Sach[countTC(maLoai,false)];
            }else{
                sq = "SELECT * FROM `sach` where `maLoai` = '" + maLoai +"'";
                tc = new Sach[countTC(maLoai,true)];
            }
        }
        try {
            System.out.println(sq);
            ResultSet rs = stmt.executeQuery(sq);
            while(rs.next()){  // duyet ket qua truy van
                i++;
                tc[i] = new Sach(
                        rs.getString("maTc"),
                        rs.getString("tenTc"), 
                        Integer.parseInt(rs.getString("soLuong")),
                        rs.getString("maNxb"),
                        rs.getString("ngayXb"),
                        rs.getString("maLoai"),
                        Integer.parseInt(rs.getString("giaTien"))
                        );
            }
            for (Sach tc1 : tc) { //cap nhat thong tin bo sung cho sach
                tc1.setLoai(loaiCTL.getTen(tc1.getLoai()));
                tc1.setNxb(nxbCTL.getTen(tc1.getNxb()));
            }
        } catch (Exception e) {
            System.err.println(e.getMessage());
        }
        return tc;
    }
    //Tìm kiếm tương đối
    public Sach[] Search(String key){
        Sach[] tc = null;
        int i =-1;
        String sq0 = "SELECT count(`maTc`) FROM `sach` WHERE `tenTc` LIKE '%"+key+"%';";  // dem sl ket qua
        String sq = "SELECT * FROM `sach` WHERE `tenTc` LIKE '%"+key+"%';"; // lay ten sach
        try {
            ResultSet rs= stmt.executeQuery(sq0);
            while(rs.next()){
                int n = Integer.parseInt(rs.getString(1));
                tc = new Sach[n];
            }
        } catch (Exception e) {
        }
        try {
            ResultSet rs = stmt.executeQuery(sq); // gửi truy vấn tìm hiếm đến SQL
            while(rs.next()){
                i++;
                tc[i] = new Sach(
                        rs.getString("maTc"),
                        rs.getString("tenTc"), 
                        Integer.parseInt(rs.getString("soLuong")),
                        rs.getString("maNxb"),
                        rs.getString("ngayXb"),
                        rs.getString("maLoai"),
                        Integer.parseInt(rs.getString("giaTien"))
                        );
            }
            for (Sach tc1 : tc) { //bo sung thong tin cho sach
                tc1.setLoai(loaiCTL.getTen(tc1.getLoai()));
                tc1.setNxb(nxbCTL.getTen(tc1.getNxb()));
            }
        } catch (Exception e) {
            System.err.println(e.getMessage());
        }
        return tc;
    }
    //Ham lay ma cuoi 
    public int layMaCuoi(){
       int maCuoi = 1;
       String maCuoiString = "";
       String sq = "SELECT * FROM `sach`";
        
        try {
            ResultSet rs = stmt.executeQuery(sq);
            while(rs.next()){
                maCuoiString =  rs.getString("maTc");
                maCuoiString = maCuoiString.substring(1, maCuoiString.length());
                maCuoi = Integer.parseInt(maCuoiString)+1;
            }
        } catch (SQLException ex) {
            System.err.println("Loi: " + ex.getMessage());
        }
        System.out.println("ma cuoi: "+maCuoi);
        return maCuoi;
    }
    //Them tap chi
    public void insertTapChi(Sach tc){
        String maNxbString = nxbCTL.getMa(tc.getNxb()); //lay mã
        String maLoaiString = loaiCTL.getMa(tc.getLoai());
        
        String sq = "INSERT INTO `sach`(`maTc`, `tenTc`, `soLuong`, `maNxb`, `ngayXb`, `maLoai`,`giaTien`,`hinhanh`) "
                + "VALUES ('"+tc.getMaTc()
                +"','"+tc.getTenTc()+"',"
                +tc.getSoLuong()
                +",'"+maNxbString
                +"','"+tc.getNgayXb()
                +"','"+maLoaiString
                +"',"+tc.getGiaTien()
                +",null)";
        try {
            stmt.executeUpdate(sq);
        } catch (Exception e) {
            System.err.println(e.getMessage());
        }
    }
    //Ham xoa 1 cuốn tạp chí

    // Phương thức xóa 1 cuốn sách theo mã
    public void deleteTapChi(String ma) {
        String sq = "DELETE FROM `sach` WHERE `maTc` = ?";
        try (PreparedStatement ps = ConnectDB.con.prepareStatement(sq)) {
            ps.setString(1, ma);  // Mã sách cần xóa
            int rowsAffected = ps.executeUpdate(); // thuc thi lenh delete
            if (rowsAffected > 0) { //kiem tra so luong ban ghi bi xoa
                System.out.println("Xóa thành công mã sách: " + ma);
            } else {
                System.out.println("Không tìm thấy mã sách: " + ma);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }


    public void deleteAllTapChi(){
        String sq = "Delete from `sach` where 1=1";
        try {
            stmt.executeUpdate(sq);
        } catch (Exception e) {
        }
    }
    
    //Ham cap nhat 1 cuốn tạp chí
    public void updateTapChi(Sach tc){
        String maNxbString = nxbCTL.getMa(tc.getNxb());
        String maLoaiString = loaiCTL.getMa(tc.getLoai());
        
        String sq = "UPDATE `sach` SET "
                + "`tenTc`='"+tc.getTenTc()+"',`soLuong`="+tc.getSoLuong()+","
                + "`maNxb`='"+maNxbString+"',`ngayXb`='"+tc.getNgayXb()+"',"
                + "`maLoai`='"+maLoaiString+"',"
                + "`giaTien` = "+tc.getGiaTien()
                + " WHERE `maTc` = '"+tc.getMaTc()+"'";
        try {
            stmt.executeUpdate(sq);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    // Phương thức giảm số lượng sách
    public void giamSoLuong(String maTc, int soLuongDaBan) {
        String sql = "UPDATE `sach` SET `soLuong` = `soLuong` - ? WHERE `maTc` = ?";
        try (PreparedStatement ps = ConnectDB.con.prepareStatement(sql)) {
            ps.setInt(1, soLuongDaBan);  // Số lượng cần giảm
            ps.setString(2, maTc);  // Mã sách
            ps.executeUpdate();  // Thực hiện câu lệnh `UPDATE`
        } catch (SQLException e) {
            e.printStackTrace();  // In lỗi ra console nếu có lỗi
        }
    }

    // Phương thức lấy mã sách từ tên sách
    public String getMaTcByTen(String tenTc) {
        String maTc = "";
        String sql = "SELECT `maTc` FROM `sach` WHERE `tenTc` = ?";
        try (PreparedStatement ps = ConnectDB.con.prepareStatement(sql)) {
            ps.setString(1, tenTc);  // Truyền tên sách vào câu lệnh SQL
            ResultSet rs = ps.executeQuery();  // Thực hiện câu lệnh `SELECT`
            if (rs.next()) {
                maTc = rs.getString("maTc");  // Lấy mã sách nếu tìm thấy
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return maTc;  // Trả về mã sách
    }


    public static void main(String[] args) {
        sachController tcCTL = new sachController();
        Sach[] tc = tcCTL.getData("",true);
        for(int i = 0; i< tc.length; i++){
            System.out.println(tc[i].getTenTc());
        }
    }
}
