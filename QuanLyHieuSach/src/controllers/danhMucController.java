/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package controllers;

import data.ConnectDB;
import static data.ConnectDB.stmt;
import java.sql.ResultSet;
import models.DanhMuc;


public class danhMucController {

    public danhMucController() {
        new ConnectDB();
    }
    
    public void themDanhMuc(DanhMuc dm){
        String sq = "INSERT INTO `danhmuc`(`maHD`, `tenDM`, `giaTien`, `soLuong`) "
                + "VALUES ("+dm.getMaHD()+",'"+dm.getTenDM()+"',"+dm.getGiaTien()+","+dm.getSoLuong()+")";
        try {
            stmt.executeUpdate(sq);
        } catch (Exception e) {
           e.printStackTrace();
        }
    }
    public int getSl(int maHD){
        int i = 0;
        String sq = "Select count(maHD) from `danhmuc` where `maHD` = " + maHD;
        try {
            ResultSet rs = stmt.executeQuery(sq);
            while (rs.next()){
                i = Integer.parseInt(rs.getString(1));
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return i;
    }
    public DanhMuc[] getDanhMucs(int maHD){
        int i = -1;
        DanhMuc[] dms = new DanhMuc[getSl(maHD)];
        String sq = "Select * from `danhmuc` where `maHD` = " + maHD;
        try {
            ResultSet rs = stmt.executeQuery(sq);
            while(rs.next()){
                i++;
                int giaTien = Integer.parseInt(rs.getString("giaTien"));
                String tenDM = rs.getString("tenDM");
                int soLuong = Integer.parseInt(rs.getString("soLuong"));
                
                dms[i] = new DanhMuc(maHD, tenDM, giaTien, soLuong);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return dms;
    }
    public void xoaDMs(int maHD){
        String sq = "Delete from `danhmuc` where `maHD` = " + maHD;
        try {
            stmt.executeUpdate(sq);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
