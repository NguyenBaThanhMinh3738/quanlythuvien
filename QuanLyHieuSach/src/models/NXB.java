/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package models;


public class NXB {
    private String maNxb;
    private String tenNxb;

    public NXB() {
    }

    public NXB(String maNxb, String tenNxb) {
        this.maNxb = maNxb;
        this.tenNxb = tenNxb;
    }

    public String getMaNxb() {
        return maNxb;
    }

    public void setMaNxb(String maNxb) {
        this.maNxb = maNxb;
    }

    public String getTenNxb() {
        return tenNxb;
    }

    public void setTenNxb(String tenNxb) {
        this.tenNxb = tenNxb;
    }
    
    
}
