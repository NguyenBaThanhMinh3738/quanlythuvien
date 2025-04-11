/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package controllers;

import data.ConnectDB;
import static data.ConnectDB.stmt;
import java.sql.ResultSet;
import java.sql.SQLException;
import javax.swing.DefaultListModel;
import javax.swing.JList;
import models.NXB;


public class nxbController {

    public nxbController() {
        new ConnectDB();
    }
    //lay ve mang nha xua ban
    public NXB[] getNxb(){
        NXB[] nxb = null;
        int n = 0;
        int i =-1;
        String sq = "Select count(`maNxb`) from `nxb`";
        try {
            ResultSet rs = stmt.executeQuery(sq);
            while(rs.next()){
                n = Integer.parseInt(rs.getString(1));
            }
        } catch (Exception e) {
            System.err.println("Khong co nxb nao: "+ e.getMessage());
        }
        nxb = new NXB[n];
        String sq1 = "Select * from `nxb`";
        try {
            ResultSet rs = stmt.executeQuery(sq1);
            while(rs.next()){
                i++;
                nxb[i] = new NXB(rs.getString("maNxb"), rs.getString("tenNxb"));
            }
        } catch (Exception e) {
        }
        return nxb;
    }
    //cap nhap thong tin nha xuat ban
    public void updateNxb(NXB nxb){
        String sq = "UPDATE `nxb` SET `tenNxb`='"+nxb.getTenNxb()+"' WHERE `maNxb` = '"+nxb.getMaNxb()+"'";
        try {
            stmt.executeUpdate(sq);
        } catch (Exception e) {
            System.err.println(e.getMessage());
        }
    }
    //Xoa nha xuat ban
    public void deleteNxb(NXB nxb){
        //Xoa cac tap chi co nxb nay
        String sq1 = "DELETE FROM `tapchi` WHERE `maNxb` = '"+nxb.getMaNxb()+"'";
        String sq2 = "ALTER TABLE `tapchi` DROP FOREIGN KEY `fk_maNxb`;";
        String sq3 = "DELETE FROM `nxb` WHERE `maNxb` = '"+nxb.getMaNxb()+"'";
        String sq4 = "ALTER TABLE `tapchi` ADD CONSTRAINT `fk_maNxb` FOREIGN KEY(`maNxb`) REFERENCES `nxb`(`maNxb`);";
        try {
            stmt.executeUpdate(sq1);
            stmt.executeUpdate(sq2);
            stmt.executeUpdate(sq3);
            stmt.executeUpdate(sq4);
        } catch (Exception e) {
            System.err.println(e.getMessage());
        }
    }
    //lay Chi so chuoi 
    public int layMaCuoi(){
       int maCuoi = 1;
       String maCuoiString = "";
       String sq = "SELECT `maNxb` FROM `nxb`";
        
        try {
            ResultSet rs = stmt.executeQuery(sq);
            while(rs.next()){
                maCuoiString =  rs.getString("maNxb");
                maCuoiString = maCuoiString.substring(1, maCuoiString.length());
                maCuoi = Integer.parseInt(maCuoiString) + 1;
            }
        } catch (SQLException ex) {
            System.err.println("Loi: " + ex.getMessage());
        }
        
        return maCuoi;
    }
    //them Nxb
    public void insertNxb(NXB nxb){
        String sq = "INSERT INTO `nxb`(`maNxb`, `tenNxb`) VALUES ('"+nxb.getMaNxb()+"','"+nxb.getTenNxb()+"')";
        try {
            stmt.executeUpdate(sq);
        } catch (Exception e) {
            System.err.println(e.getMessage());
        }
    }
    //Lay ma tu ten
    public String getMa(String name){
        String ma = "";
        String sq = "Select `maNxb` from `nxb` where `tenNxb`  = '"+name+"'";
        try {
            ResultSet rs = stmt.executeQuery(sq);
            while(rs.next()){
                ma = rs.getString("maNxb");
            }
        } catch (Exception e) {
        }
        return ma;
    }
    //Lay ten tu ma
    public String getTen(String ma){
        String ten = "";
        String sq = "Select `tenNxb` from `nxb` where `maNxb`  = '"+ma+"'";
        try {
            ResultSet rs = stmt.executeQuery(sq);
            while(rs.next()){
                ten = rs.getString("tenNxb");
            }
        } catch (Exception e) {
            System.err.println(e.getMessage());
        }
        return ten;
    }
    
    public static void main(String[] args) {
        nxbController nxbController = new nxbController();
        NXB[] nxb = nxbController.getNxb();
        System.out.println(nxbController.getMa("Hoa hoc tro"));
    }
    
}
