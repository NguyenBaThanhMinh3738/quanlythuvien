/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package controllers;

import data.ConnectDB;
import static data.ConnectDB.stmt;
import java.sql.SQLException;
import java.sql.ResultSet;
import javax.swing.JComboBox;
import models.KeSach;


public class keSachController {
    loaiController loaiCTL = new loaiController();
    public keSachController() {
        new ConnectDB();
    }
    public KeSach getKeSach(String maKe){
        KeSach keSach = new KeSach();
        String sq = "SELECT `maKe`, `moTa` FROM `keSach` WHERE `maKe` = '"+maKe+"'";
        
        try {
            ResultSet rs = stmt.executeQuery(sq);
            while(rs.next()){
                keSach.setMaKe(rs.getString("maKe"));
                keSach.setMoTa(rs.getString("moTa"));
            }
        } catch (SQLException ex) {
            System.err.println("Loi: " + ex.getMessage());
        }
        return keSach;
    }
    public int laySoKe(){
        int sl = 0;
        String sq = "SELECT COUNT(`maKe`) FROM `keSach`";
         try {
            ResultSet rs = stmt.executeQuery(sq);
            while(rs.next()){
                sl =  Integer.parseInt(rs.getString(1));
            }
        } catch (SQLException ex) {
            System.err.println("Loi: " + ex.getMessage());
        }
        return sl;
    }
    public KeSach[] getMangKe(){
        int i = -1;
        KeSach[] keSachs = new KeSach[laySoKe()];
        String sq = "SELECT `maKe`, `moTa` FROM `keSach`";
        
        try {
            ResultSet rs = stmt.executeQuery(sq);
            while(rs.next()){
                i++;
                keSachs[i] = new KeSach(rs.getString("maKe"),rs.getString("moTa"));
            }
        } catch (SQLException ex) {
            System.err.println("Loi: " + ex.getMessage());
        }
        return keSachs;
    }
    
    public void capNhapGia(KeSach keSach){
        String sq = "UPDATE `keSach` SET `moTa`='"+keSach.getMoTa()+"' WHERE `maKe` = '" + keSach.getMaKe()+"'";
        
        try {
            stmt.executeUpdate(sq);
        } catch (SQLException ex) {
            System.err.println("Loi: " + ex.getMessage());
        }
    }
    
    public void xoaKe(String maKe){
        String[] maLoai = loaiCTL.getMaLoai(maKe);
        for(int i = 0; i<maLoai.length; i++){
            String sq01 = "DELETE FROM `sach` WHERE `maLoai` = '"+ maLoai[i] +"';";
            String sq02 = "ALTER TABLE `sach` DROP FOREIGN KEY `fk_maLoai`;";
            String sq03 =  "DELETE FROM `loai` WHERE `maLoai` = '" + maLoai[i]+"';";
            String sq04 =  "ALTER TABLE `sach` ADD CONSTRAINT `fk_maLoai` FOREIGN KEY(`maLoai`) REFERENCES `loai`(`maLoai`);";
            try {
                stmt.executeUpdate(sq01);
                stmt.executeUpdate(sq02);
                stmt.executeUpdate(sq03);
                stmt.executeUpdate(sq04);
            } catch (Exception e) {
                System.err.println("Xoa cac sach loai " + maLoai[i]+"o ke "+maKe+" khong thanh cong!");
            }
        }
        
        
        String sq = "DELETE FROM `loai` WHERE `maKe` = '"+ maKe +"';";
        String sq1 = "ALTER TABLE `loai` DROP FOREIGN KEY `fk_maKe`;";
        String sq2 =  "DELETE FROM `keSach` WHERE `maKe` = '" + maKe+"';";
        String sq3 =  "ALTER TABLE `loai` ADD CONSTRAINT `fk_maKe` FOREIGN KEY(`maKe`) REFERENCES `keSach`(`maKe`);";
        
        try {
            stmt.executeUpdate(sq);
            stmt.executeUpdate(sq1);
            stmt.executeUpdate(sq2);
            stmt.executeUpdate(sq3);
        } catch (SQLException ex) {
            System.err.println("Loi: " + ex.getMessage());
        }
    }
    public void themKe(KeSach keSach){
        String sq = "INSERT INTO `keSach`(`maKe`, `moTa`) VALUES ('"+keSach.getMaKe()+"','"+keSach.getMoTa()+"')";
        
        try {
            stmt.executeUpdate(sq);
        } catch (SQLException ex) {
            System.err.println("Loi: " + ex.getMessage());
        }
    }
    //Ham cai dat comboBox
    public void loadComboBox(JComboBox box){
        String sq = "SELECT `maKe` FROM `keSach`";
        try {
            ResultSet rs = stmt.executeQuery(sq);
            while(rs.next()){
                box.addItem(rs.getString("maKe"));
            }
        } catch (SQLException ex) {
            System.err.println("Loi: " + ex.getMessage());
        }
    }
    //Ham lay thong tin mo ta theo ma
    public String layMoTa(String maKe){
        String moTa = "Khong co mo ta!";
        String sq = "SELECT `moTa` FROM `keSach` WHERE `maKe` = '"+maKe+"'";
        try {
            ResultSet rs = stmt.executeQuery(sq);
            while(rs.next()){
                moTa = rs.getString("moTa");
            }
        } catch (SQLException ex) {
            System.err.println("Loi: " + ex.getMessage());
        }
        return moTa;
    }
    //Ham lay gia cuoi cung
    public int layMaCuoi(){
       int maCuoi = 1;
       String maCuoiString = "";
       String sq = "SELECT `maKe`, `moTa` FROM `keSach`";
        
        try {
            ResultSet rs = stmt.executeQuery(sq);
            while(rs.next()){
                maCuoiString =  rs.getString("maKe");
                maCuoiString = maCuoiString.substring(2, maCuoiString.length());
                maCuoi = Integer.parseInt(maCuoiString) + 1;
            }
        } catch (SQLException ex) {
            System.err.println("Loi: " + ex.getMessage());
        }
        
        return maCuoi;
    }
    public static void main(String[] args) {
        keSachController giaController = new keSachController();
        KeSach[] keSach = giaController.getMangKe();
        System.out.println(keSach[0].getMoTa());
    }
}
