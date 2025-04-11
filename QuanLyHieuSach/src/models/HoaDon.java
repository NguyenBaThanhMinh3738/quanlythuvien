/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package models;


public class HoaDon {
    private int maHD;
    private String ngayTao;
    private int maNd;

    public HoaDon(int maHD, String ngayTao, int maNd) {
        this.maHD = maHD;
        this.ngayTao = ngayTao;
        this.maNd = maNd;
    }

    public int getMaHD() {
        return maHD;
    }

    public void setMaHD(int maHD) {
        this.maHD = maHD;
    }

    public String getNgayTao() {
        return ngayTao;
    }

    public void setNgayTao(String ngayTao) {
        this.ngayTao = ngayTao;
    }

    public int getMaNd() {
        return maNd;
    }

    public void setMaNd(int maNd) {
        this.maNd = maNd;
    }
    
    
}
