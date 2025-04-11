/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/GUIForms/JFrame.java to edit this template
 */
package views;

import controllers.keSachController;
import controllers.loaiController;
import controllers.nxbController;
import javax.swing.DefaultComboBoxModel;
import javax.swing.DefaultListModel;
import javax.swing.JOptionPane;
import models.KeSach;
import models.Loai;
import models.NXB;

public class settingFrame extends javax.swing.JFrame {
    keSachController keSachCTL = new keSachController();
    nxbController nxbCTL = new nxbController();
    loaiController loaiCTL  = new loaiController();
    KeSach[] gia = keSachCTL.getMangKe();
    NXB[] nxblist = nxbCTL.getNxb();
    Loai[] loai_list = loaiCTL.getLoaiList();
    DefaultListModel modelList = new DefaultListModel();
    DefaultListModel modelLoai = new DefaultListModel();
    int indexC_loai = -1;
    int indexC_nxb = -1;
    /**
     * Creates new form settingFrame
     */
    public settingFrame() {
        initComponents();
        init();
    }
    
    //Ham khoi tao bo sung
    public void init(){
        setTitle("Setting");
        keSachCTL.loadComboBox(giaComboBox);
        keSachCTL.loadComboBox(giaLoaiMoiCB);
        keSachCTL.loadComboBox(giaLoaiCB);
        maGiaMoiField.setText("KS"+String.valueOf(keSachCTL.layMaCuoi()));
        hienThiMoTa();
        for(int i = 0; i<nxblist.length; i++){
            modelList.addElement(nxblist[i].getTenNxb());
        }
        nxbList.setModel(modelList);
        
        loadModelLoai();
    }
//    ///Panel GIA
    //Ham khoi phuc trang thai (su dung khi CSDL duoc cap nhap)
    public void refresh(){
        gia = keSachCTL.getMangKe();
        giaComboBox.setModel(new DefaultComboBoxModel(new String[]{}));
        giaLoaiCB.setModel(new DefaultComboBoxModel(new String[]{}));
        giaLoaiMoiCB.setModel(new DefaultComboBoxModel(new String[]{}));
        keSachCTL.loadComboBox(giaComboBox);
        keSachCTL.loadComboBox(giaLoaiMoiCB);
        keSachCTL.loadComboBox(giaLoaiCB);
        hienThiMoTa();
        maGiaMoiField.setText("KS"+String.valueOf(keSachCTL.layMaCuoi()));
        moTaMoiField.setText("");
    }
    //Hàm trạng thái chỉnh sửa
    public void editStatus(){
        if(chinhSuaCheck.isSelected()){
            motaField.setEditable(true);
            moTaMoiField.setEnabled(true);
            capNhapBTN.setEnabled(true);
            themBTN.setEnabled(true);
        }else{
            motaField.setEditable(false);
            moTaMoiField.setEnabled(false);
            capNhapBTN.setEnabled(false);
            themBTN.setEnabled(false);
        }
    }
    //Ham lay mo ta khi ma thay doi
    public void hienThiMoTa(){
        String item = (String) giaComboBox.getSelectedItem();
        if(item == null){
                motaField.setText("");
        }
        else{
            for(int i = 0; i< gia.length; i++){
                if(gia[i].getMaKe().equals(item)){
                    motaField.setText(gia[i].getMoTa());
                }
            }
        }
    }
    //Ham xu ly nut xoa
    public void xoaFunc(){
        String item = (String) giaComboBox.getSelectedItem();
        keSachCTL.xoaKe(item);
        refresh();
        JOptionPane.showMessageDialog(rootPane, "Đã xóa kệ "+ item, "Message", 1);
    }
    //Ham xu ly nut them
    public void themFunc(){
        KeSach giaThem = new KeSach(maGiaMoiField.getText(),moTaMoiField.getText());
        try {
            keSachCTL.themKe(giaThem);
            refresh();
        } catch (Exception e) {
            JOptionPane.showMessageDialog(rootPane, "Không thêm được kệ sách", "Message", 0);
            System.err.println("Khong them duoc kesach!");
        }
    }
    //Ham xu ly nut cap nhap
    public void capNhapFunc(){
        KeSach giaCN = new KeSach((String) giaComboBox.getSelectedItem(),motaField.getText());
        try {
            keSachCTL.capNhapGia(giaCN);
            JOptionPane.showMessageDialog(rootPane, "Đã cập nhập kệ sách "+ giaCN.getMaKe(), "Message", 1);
            refresh();
        } catch (Exception e) {
            System.err.println("Khong cap nhap duoc gia!");
        }
    }
    ///Panel NXB
    //ham reload du lieu tu database
    public void refreshNxb(){
        modelList =new DefaultListModel();
        nxblist = nxbCTL.getNxb();
        for(int i = 0; i<nxblist.length; i++){
            modelList.addElement(nxblist[i].getTenNxb());
        }
        nxbList.setModel(modelList);
        xoaNxbBTN.setEnabled(false);
        capNhapNxbBTN.setEnabled(false);
    }
    //Ham chinh sua khi list duoc chon
    public void editNxbFunc(){
        indexC_nxb = nxbList.getSelectedIndex();
        nxbField.setText(nxbList.getSelectedValue());
        xoaNxbBTN.setEnabled(true);
        capNhapNxbBTN.setEnabled(true);
    }
    //ham cap nhap nha cuat ban
    public void capNhapNxbFunc(){
        nxblist[indexC_nxb].setTenNxb( nxbField.getText());
        nxbCTL.updateNxb(nxblist[indexC_nxb]);
        System.out.println(nxblist[indexC_nxb].getMaNxb() + nxblist[indexC_nxb].getTenNxb());
        JOptionPane.showMessageDialog(rootPane, "Đã cập nhập "+ nxblist[indexC_nxb].getTenNxb(), "Message", 1);
        refreshNxb();
    }
    //Ham xoa nxb
    public void xoaNxbFunc(){
        try {
            indexC_nxb = nxbList.getSelectedIndex();
            nxbCTL.deleteNxb(nxblist[indexC_nxb]);
            JOptionPane.showMessageDialog(rootPane, "Đã xóa "+ nxblist[indexC_nxb].getTenNxb(), "Message", 1);
            refreshNxb();
        } catch (Exception e) {
            System.err.println(e.getMessage());
        }
    }
    //them Nxb
    public void themNxbFunc(){
        if(!nxbMoiField.getText().equals("")){
            String maNxb = "N"+nxbCTL.layMaCuoi();
            NXB nxb = new NXB(maNxb,nxbMoiField.getText());
            nxbCTL.insertNxb(nxb);
            nxbMoiField.setText("");
            refreshNxb();
        }
    }
    
/// Panel Loai
    
    //load model Loai
    public void loadModelLoai(){
        //Tai model moi tu CSDL cap nhap mang loai 
        loai_list = loaiCTL.getLoaiList();
        //xoa model hien tai
        modelLoai = new DefaultListModel();
        for(int i = 0; i<loai_list.length; i++){
            modelLoai.addElement(loai_list[i].getTenLoai());
        }
        loaiList.setModel(modelLoai);
    }
    //Ham cap nhap thong tin tu CSDL
    public void refreshLoai(){
        loadModelLoai();
        loaiField.setText("");
        loaiMoiField.setText("");
    }
    //Ham chon loai
    public void loaiSelectedFunc(){
        if(loaiList.getSelectedValue()!= null){
            indexC_loai = loaiList.getSelectedIndex();
            loaiField.setText(loai_list[indexC_loai].getTenLoai());
            giaLoaiCB.setSelectedItem(loai_list[indexC_loai].getMaKe());
        }
    }
    
    //Ham them loai moi
    public void themLoaiFunc(){
        if(!loaiMoiField.getText().equals("")){
            loaiCTL.insertLoai(loaiMoiField.getText(), (String) giaLoaiMoiCB.getSelectedItem());
            refreshLoai();
        }
    }
    //Hma xoa loai
    public void xoaLoaiFunc(){
        loaiSelectedFunc();
        if(!loaiField.getText().equals("")){
            loaiCTL.deleteLoai(loai_list[indexC_loai].getMaLoai());
            JOptionPane.showMessageDialog(rootPane, "Đã xóa loại "+ loai_list[indexC_loai].getTenLoai(), "Message", 1);
            refreshLoai();
        }
    }
    //Ham cap nhap loai
    public void capNhapLoaiFunc(){
        if(!loaiField.getText().equals("")){
            loaiCTL.updateLoai(loai_list[indexC_loai],loaiField.getText(), (String) giaLoaiCB.getSelectedItem());
            JOptionPane.showMessageDialog(rootPane, "Đã Cập nhập loại "+ loai_list[indexC_loai].getTenLoai(), "Message", 1);
            refreshLoai();
        }
    }
    //hien gap cac loi sau: khong cap nhap duoc, them duoc nhung bi loi
    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jTabbedPane1 = new javax.swing.JTabbedPane();
        jPanel1 = new javax.swing.JPanel();
        jLabel1 = new javax.swing.JLabel();
        chinhSuaCheck = new javax.swing.JCheckBox();
        giaComboBox = new javax.swing.JComboBox<>();
        jScrollPane1 = new javax.swing.JScrollPane();
        motaField = new javax.swing.JTextArea();
        jLabel2 = new javax.swing.JLabel();
        capNhapBTN = new javax.swing.JButton();
        xoaBTN = new javax.swing.JButton();
        jScrollPane2 = new javax.swing.JScrollPane();
        moTaMoiField = new javax.swing.JTextArea();
        jLabel3 = new javax.swing.JLabel();
        jLabel4 = new javax.swing.JLabel();
        maGiaMoiField = new javax.swing.JTextField();
        themBTN = new javax.swing.JButton();
        jPanel2 = new javax.swing.JPanel();
        loaiField = new javax.swing.JTextField();
        jLabel6 = new javax.swing.JLabel();
        jLabel7 = new javax.swing.JLabel();
        giaLoaiCB = new javax.swing.JComboBox<>();
        xoaLoaiBTN = new javax.swing.JButton();
        capNhapLoaiBTN = new javax.swing.JButton();
        giaLoaiMoiCB = new javax.swing.JComboBox<>();
        loaiMoiField = new javax.swing.JTextField();
        themLoaiBTN = new javax.swing.JButton();
        jScrollPane5 = new javax.swing.JScrollPane();
        loaiList = new javax.swing.JList<>();
        jPanel3 = new javax.swing.JPanel();
        jScrollPane3 = new javax.swing.JScrollPane();
        nxbList = new javax.swing.JList<>();
        jLabel5 = new javax.swing.JLabel();
        nxbMoiField = new javax.swing.JTextField();
        themNxbBTN = new javax.swing.JButton();
        capNhapNxbBTN = new javax.swing.JButton();
        xoaNxbBTN = new javax.swing.JButton();
        nxbField = new javax.swing.JTextField();

        setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);

        jTabbedPane1.setBackground(new java.awt.Color(255, 255, 255));
        jTabbedPane1.setForeground(new java.awt.Color(102, 102, 102));

        jPanel1.setBackground(new java.awt.Color(255, 255, 255));

        jLabel1.setText("Kệ");

        chinhSuaCheck.setText("Chỉnh sửa");
        chinhSuaCheck.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                chinhSuaCheckActionPerformed(evt);
            }
        });

        giaComboBox.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                giaComboBoxActionPerformed(evt);
            }
        });

        motaField.setEditable(false);
        motaField.setColumns(15);
        motaField.setRows(2);
        jScrollPane1.setViewportView(motaField);

        jLabel2.setText("Mô tả");

        capNhapBTN.setText("Cập nhập");
        capNhapBTN.setEnabled(false);
        capNhapBTN.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                capNhapBTNActionPerformed(evt);
            }
        });

        xoaBTN.setText("Xóa");
        xoaBTN.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                xoaBTNActionPerformed(evt);
            }
        });

        moTaMoiField.setColumns(15);
        moTaMoiField.setRows(2);
        moTaMoiField.setEnabled(false);
        jScrollPane2.setViewportView(moTaMoiField);

        jLabel3.setText("Kệ mới");

        jLabel4.setText("Mô tả mới");

        maGiaMoiField.setEnabled(false);

        themBTN.setText("Thêm");
        themBTN.setEnabled(false);
        themBTN.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                themBTNActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addGap(26, 26, 26)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel1)
                    .addComponent(jLabel2)
                    .addComponent(jLabel3)
                    .addComponent(jLabel4))
                .addGap(41, 41, 41)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jScrollPane2, javax.swing.GroupLayout.DEFAULT_SIZE, 263, Short.MAX_VALUE)
                    .addComponent(jScrollPane1)
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(maGiaMoiField, javax.swing.GroupLayout.PREFERRED_SIZE, 119, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(giaComboBox, javax.swing.GroupLayout.PREFERRED_SIZE, 118, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(0, 0, Short.MAX_VALUE)))
                .addGap(51, 51, 51)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(chinhSuaCheck)
                    .addComponent(capNhapBTN, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(xoaBTN, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(themBTN, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addGap(34, 34, 34))
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addGap(20, 20, 20)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel1)
                    .addComponent(chinhSuaCheck)
                    .addComponent(giaComboBox, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(28, 28, 28)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel2)
                    .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 63, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addComponent(capNhapBTN)
                        .addGap(18, 18, 18)
                        .addComponent(xoaBTN)))
                .addGap(18, 18, 18)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel3)
                    .addComponent(maGiaMoiField, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(18, 18, 18)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel4)
                    .addComponent(themBTN)
                    .addComponent(jScrollPane2, javax.swing.GroupLayout.PREFERRED_SIZE, 60, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap(37, Short.MAX_VALUE))
        );

        jTabbedPane1.addTab("Kệ", jPanel1);

        jPanel2.setBackground(new java.awt.Color(255, 255, 255));

        jLabel6.setText("Chỉnh sửa");

        jLabel7.setText("Thêm loại");

        xoaLoaiBTN.setText("Xóa");
        xoaLoaiBTN.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                xoaLoaiBTNActionPerformed(evt);
            }
        });

        capNhapLoaiBTN.setText("Cập nhập");
        capNhapLoaiBTN.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                capNhapLoaiBTNActionPerformed(evt);
            }
        });

        themLoaiBTN.setText("Thêm");
        themLoaiBTN.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                themLoaiBTNActionPerformed(evt);
            }
        });

        loaiList.addListSelectionListener(new javax.swing.event.ListSelectionListener() {
            public void valueChanged(javax.swing.event.ListSelectionEvent evt) {
                loaiListValueChanged(evt);
            }
        });
        jScrollPane5.setViewportView(loaiList);

        javax.swing.GroupLayout jPanel2Layout = new javax.swing.GroupLayout(jPanel2);
        jPanel2.setLayout(jPanel2Layout);
        jPanel2Layout.setHorizontalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jScrollPane5, javax.swing.GroupLayout.PREFERRED_SIZE, 220, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel2Layout.createSequentialGroup()
                        .addGap(122, 122, 122)
                        .addComponent(themLoaiBTN)
                        .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                    .addGroup(jPanel2Layout.createSequentialGroup()
                        .addGap(40, 40, 40)
                        .addComponent(xoaLoaiBTN)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 79, Short.MAX_VALUE)
                        .addComponent(capNhapLoaiBTN)
                        .addGap(53, 53, 53))
                    .addGroup(jPanel2Layout.createSequentialGroup()
                        .addGap(14, 14, 14)
                        .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                            .addComponent(loaiField)
                            .addComponent(jLabel6)
                            .addComponent(jLabel7)
                            .addComponent(giaLoaiCB, 0, 285, Short.MAX_VALUE)
                            .addComponent(loaiMoiField)
                            .addComponent(giaLoaiMoiCB, 0, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                        .addGap(0, 0, Short.MAX_VALUE))))
        );
        jPanel2Layout.setVerticalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addGap(19, 19, 19)
                .addComponent(jLabel6)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(loaiField, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(giaLoaiCB, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(xoaLoaiBTN)
                    .addComponent(capNhapLoaiBTN))
                .addGap(26, 26, 26)
                .addComponent(jLabel7)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(loaiMoiField, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(giaLoaiMoiCB, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(12, 12, 12)
                .addComponent(themLoaiBTN)
                .addContainerGap())
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jScrollPane5))
        );

        jTabbedPane1.addTab("Loại", jPanel2);

        jPanel3.setBackground(new java.awt.Color(255, 255, 255));

        nxbList.addListSelectionListener(new javax.swing.event.ListSelectionListener() {
            public void valueChanged(javax.swing.event.ListSelectionEvent evt) {
                nxbListValueChanged(evt);
            }
        });
        jScrollPane3.setViewportView(nxbList);

        jLabel5.setText("Thêm NXB mới");

        themNxbBTN.setText("Thêm");
        themNxbBTN.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                themNxbBTNActionPerformed(evt);
            }
        });

        capNhapNxbBTN.setText("Cập nhập");
        capNhapNxbBTN.setEnabled(false);
        capNhapNxbBTN.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                capNhapNxbBTNActionPerformed(evt);
            }
        });

        xoaNxbBTN.setText("Xóa");
        xoaNxbBTN.setEnabled(false);
        xoaNxbBTN.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                xoaNxbBTNActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel3Layout = new javax.swing.GroupLayout(jPanel3);
        jPanel3.setLayout(jPanel3Layout);
        jPanel3Layout.setHorizontalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel3Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jScrollPane3, javax.swing.GroupLayout.PREFERRED_SIZE, 256, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel3Layout.createSequentialGroup()
                        .addGap(18, 18, 18)
                        .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(jPanel3Layout.createSequentialGroup()
                                .addComponent(jLabel5, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                .addGap(143, 143, 143))
                            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel3Layout.createSequentialGroup()
                                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                                    .addComponent(nxbField, javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(nxbMoiField, javax.swing.GroupLayout.Alignment.LEADING))
                                .addContainerGap())
                            .addGroup(jPanel3Layout.createSequentialGroup()
                                .addGap(37, 37, 37)
                                .addComponent(xoaNxbBTN)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 42, Short.MAX_VALUE)
                                .addComponent(capNhapNxbBTN)
                                .addGap(39, 39, 39))))
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel3Layout.createSequentialGroup()
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(themNxbBTN)
                        .addGap(106, 106, 106))))
        );
        jPanel3Layout.setVerticalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel3Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jScrollPane3)
                .addContainerGap())
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel3Layout.createSequentialGroup()
                .addGap(35, 35, 35)
                .addComponent(nxbField, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(capNhapNxbBTN)
                    .addComponent(xoaNxbBTN))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 71, Short.MAX_VALUE)
                .addComponent(jLabel5)
                .addGap(12, 12, 12)
                .addComponent(nxbMoiField, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addComponent(themNxbBTN)
                .addGap(29, 29, 29))
        );

        jTabbedPane1.addTab("NXB", jPanel3);

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jTabbedPane1)
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jTabbedPane1)
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void chinhSuaCheckActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_chinhSuaCheckActionPerformed
        // TODO add your handling code here:
        editStatus();
    }//GEN-LAST:event_chinhSuaCheckActionPerformed

    private void giaComboBoxActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_giaComboBoxActionPerformed
        // TODO add your handling code here:
        hienThiMoTa();
    }//GEN-LAST:event_giaComboBoxActionPerformed

    private void xoaBTNActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_xoaBTNActionPerformed
        // TODO add your handling code here:
        xoaFunc();
    }//GEN-LAST:event_xoaBTNActionPerformed

    private void themBTNActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_themBTNActionPerformed
        // TODO add your handling code here:
        themFunc();
    }//GEN-LAST:event_themBTNActionPerformed

    private void capNhapBTNActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_capNhapBTNActionPerformed
        // TODO add your handling code here:
        capNhapFunc();
    }//GEN-LAST:event_capNhapBTNActionPerformed

    private void capNhapNxbBTNActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_capNhapNxbBTNActionPerformed
        // TODO add your handling code here:
        capNhapNxbFunc();
    }//GEN-LAST:event_capNhapNxbBTNActionPerformed

    private void nxbListValueChanged(javax.swing.event.ListSelectionEvent evt) {//GEN-FIRST:event_nxbListValueChanged
        // TODO add your handling code here:
        editNxbFunc();
    }//GEN-LAST:event_nxbListValueChanged

    private void xoaNxbBTNActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_xoaNxbBTNActionPerformed
        // TODO add your handling code here:
        xoaNxbFunc();
    }//GEN-LAST:event_xoaNxbBTNActionPerformed

    private void themNxbBTNActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_themNxbBTNActionPerformed
        // TODO add your handling code here:
        themNxbFunc();
    }//GEN-LAST:event_themNxbBTNActionPerformed

    private void themLoaiBTNActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_themLoaiBTNActionPerformed
        // TODO add your handling code here:
        themLoaiFunc();
    }//GEN-LAST:event_themLoaiBTNActionPerformed

    private void xoaLoaiBTNActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_xoaLoaiBTNActionPerformed
        // TODO add your handling code here:
        xoaLoaiFunc();
    }//GEN-LAST:event_xoaLoaiBTNActionPerformed

    private void capNhapLoaiBTNActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_capNhapLoaiBTNActionPerformed
        // TODO add your handling code here:
        capNhapLoaiFunc();
    }//GEN-LAST:event_capNhapLoaiBTNActionPerformed

    private void loaiListValueChanged(javax.swing.event.ListSelectionEvent evt) {//GEN-FIRST:event_loaiListValueChanged
        // TODO add your handling code here:
        loaiSelectedFunc();
    }//GEN-LAST:event_loaiListValueChanged

    /**
     * @param args the command line arguments
     */
    public static void main(String args[]) {
        /* Set the Nimbus look and feel */
        //<editor-fold defaultstate="collapsed" desc=" Look and feel setting code (optional) ">
        /* If Nimbus (introduced in Java SE 6) is not available, stay with the default look and feel.
         * For details see http://download.oracle.com/javase/tutorial/uiswing/lookandfeel/plaf.html 
         */
        try {
            for (javax.swing.UIManager.LookAndFeelInfo info : javax.swing.UIManager.getInstalledLookAndFeels()) {
                if ("Nimbus".equals(info.getName())) {
                    javax.swing.UIManager.setLookAndFeel(info.getClassName());
                    break;
                }
            }
        } catch (ClassNotFoundException ex) {
            java.util.logging.Logger.getLogger(settingFrame.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(settingFrame.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(settingFrame.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(settingFrame.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new settingFrame().setVisible(true);
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton capNhapBTN;
    private javax.swing.JButton capNhapLoaiBTN;
    private javax.swing.JButton capNhapNxbBTN;
    private javax.swing.JCheckBox chinhSuaCheck;
    private javax.swing.JComboBox<String> giaComboBox;
    private javax.swing.JComboBox<String> giaLoaiCB;
    private javax.swing.JComboBox<String> giaLoaiMoiCB;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JLabel jLabel6;
    private javax.swing.JLabel jLabel7;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JPanel jPanel3;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JScrollPane jScrollPane2;
    private javax.swing.JScrollPane jScrollPane3;
    private javax.swing.JScrollPane jScrollPane5;
    private javax.swing.JTabbedPane jTabbedPane1;
    private javax.swing.JTextField loaiField;
    private javax.swing.JList<String> loaiList;
    private javax.swing.JTextField loaiMoiField;
    private javax.swing.JTextField maGiaMoiField;
    private javax.swing.JTextArea moTaMoiField;
    private javax.swing.JTextArea motaField;
    private javax.swing.JTextField nxbField;
    private javax.swing.JList<String> nxbList;
    private javax.swing.JTextField nxbMoiField;
    private javax.swing.JButton themBTN;
    private javax.swing.JButton themLoaiBTN;
    private javax.swing.JButton themNxbBTN;
    private javax.swing.JButton xoaBTN;
    private javax.swing.JButton xoaLoaiBTN;
    private javax.swing.JButton xoaNxbBTN;
    // End of variables declaration//GEN-END:variables
}
