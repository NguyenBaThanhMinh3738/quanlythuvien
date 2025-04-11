/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package controllers;

import data.ConnectDB;
import java.sql.ResultSet;
import static data.ConnectDB.stmt;
import models.HoaDon;


public class hoaDonController {

    public hoaDonController() {
        new ConnectDB();
    }
    
    public int layMaCuoi(){
        int ma = 0;
        String sq = "Select MAX(`maHD`) from `hoadon`";
        
        try {
            ResultSet rs = stmt.executeQuery(sq);
            while(rs.next()){
                if(rs.getString(1)!=null)
                ma = Integer.parseInt(rs.getString(1));
            }
        } catch (Exception e) {
            e.printStackTrace();;
        }
        return ma+1;
    }
    
    public void themHoaDon(HoaDon hd){
        String sq = "INSERT INTO `hoadon`(`maHD`, `ngayTao`, `maNd`)"
                + " VALUES ("+hd.getMaHD()+",'"+hd.getNgayTao()+"',"+hd.getMaNd()+")";
        try {
            stmt.executeUpdate(sq);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    public int getSl(String date){
        int i = 0;
        String sq = "Select count(maHD) from `hoadon`";
        if (date != null){
            sq += " where `ngayTao` = '"+date+"'";
        }
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
    public HoaDon[] getHoaDon(String date){
        int i = -1;
        HoaDon[] hds = new HoaDon[getSl(date)];
        String sq = "Select * from `hoadon`";
        if (date != null){
            sq += " where `ngayTao` = '"+date+"'";
        }
        try {
            ResultSet rs = stmt.executeQuery(sq);
            while(rs.next()){
                i++;
                int maHD = Integer.parseInt(rs.getString("maHD"));
                String ngayTao = rs.getString("ngayTao");
                int maNd = Integer.parseInt(rs.getString("maNd"));
                
                hds[i] = new HoaDon(maHD, ngayTao, maNd);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return hds;
    }
    public void xoaHDs(int maHD){
        String sq = "Delete from `hoadon` where `maHD` = " + maHD;
        try {
            stmt.executeUpdate(sq);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
