/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package controllers;

import models.NguoiDung;
import data.ConnectDB;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.logging.Level;
import java.util.logging.Logger;

import static data.ConnectDB.stmt;

public class nguoiDungController {
    
    private NguoiDung user1;

    public NguoiDung getUser1() {
        return user1;
    }
//Ham update mk
    public void updatePass(String name,String pass){
        try {
            String sq0 = "UPDATE `nguoidung` SET `matKhau`='" + pass + "' WHERE `tenNd`='" + name + "'";
            stmt.executeUpdate(sq0);
        } catch (SQLException ex) {
            Logger.getLogger(nguoiDungController.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
//Ham kiem tra su ton tai cua nguoi dung   
    public boolean checkUser(String userName){
        boolean i = true;
        try {
            String sq0 = "SELECT * FROM `nguoidung` WHERE `tenNd`= '" + userName + "'";
            ResultSet rs = stmt.executeQuery(sq0);
            i = rs.next();
        } catch (SQLException ex) {
        }
        return i;
    }
//Ham lay thong tin nguoi dung
    public void getUserInfor(String userName){
        int id;
        String name,pass;
        String sq0 = "SELECT * FROM `nguoidung` WHERE `tenNd`= '" + userName + "'";
        try {
            ResultSet rs = stmt.executeQuery(sq0);
            while(rs.next()){
                id = Integer.parseInt(rs.getString("maNd"));
                name =  rs.getString("tenNd");
                pass =  rs.getString("matKhau");
                user1 = new NguoiDung(id,name,pass);
            }
        } catch (SQLException ex) {
        }
    }
//Ham them nguoi dung moi    
    public void insertUser(String name, String pass){
        try {
            String sq1 = "INSERT INTO `nguoidung`(`tenNd`, `matKhau`) VALUES ('" + name +"','" + pass + "')";
            stmt.executeUpdate(sq1);
        } catch (SQLException ex) {
            Logger.getLogger(nguoiDungController.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    //Update thong tin nguoi dung
    public void updateUser(NguoiDung user){
        String sq = "UPDATE `nguoidung` SET `tenND`='"+user.getTenND()+"',`matKhau`='"+user.getMatKhau()+"' WHERE `maNd`= "+user.getMaND()+";";
        try {
            stmt.executeUpdate(sq);
        } catch (Exception e) {
        }
    }
    //Ham lay thong tin nguoi dung tu ma
    public NguoiDung getUserByID(int ma){
        NguoiDung u = null;
        String sq0 = "SELECT * FROM `nguoidung` WHERE `maNd`= " + ma ;
        try {
            ResultSet rs = stmt.executeQuery(sq0);
            while(rs.next()){
                int id = Integer.parseInt(rs.getString("maNd"));
                String name =  rs.getString("tenNd");
                String pass =  rs.getString("matKhau");
                u = new NguoiDung(id,name,pass);
            }
        } catch (SQLException ex) {
            ex.printStackTrace();
        }
        return u;
    }
//Khoi tao    
    
    public nguoiDungController() {
        new ConnectDB();
    }
//    public static void main(String[] args) {
//        userController u = new userController();
//    }
}