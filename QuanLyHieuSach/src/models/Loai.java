/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package models;


public class Loai {
    private String maLoai;
    private String tenLoai;
    private String maKe;

    public Loai(String maLoai, String tenLoai, String maKe) {
        this.maLoai = maLoai;
        this.tenLoai = tenLoai;
        this.maKe = maKe;
    }

    public Loai() {
    }

    public String getMaLoai() {
        return maLoai;
    }

    public void setMaLoai(String maLoai) {
        this.maLoai = maLoai;
    }

    public String getTenLoai() {
        return tenLoai;
    }

    public void setTenLoai(String tenLoai) {
        this.tenLoai = tenLoai;
    }

    public String getMaKe() {
        return maKe;
    }

    public void getMaKe(String maKe) {
        this.maKe = maKe;
    }
    
}
