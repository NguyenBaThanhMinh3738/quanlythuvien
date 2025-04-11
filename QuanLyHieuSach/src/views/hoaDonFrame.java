/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/GUIForms/JFrame.java to edit this template
 */
package views;

import controllers.danhMucController;
import controllers.hoaDonController;
import controllers.sachController;
import java.time.LocalDate;
import java.util.Scanner;
import javax.swing.DefaultListModel;
import javax.swing.JOptionPane;
import javax.swing.table.DefaultTableModel;
import models.DanhMuc;
import models.HoaDon;
import models.NguoiDung;
import models.Sach;


public class hoaDonFrame extends javax.swing.JFrame {
    NguoiDung userC = new NguoiDung();
    sachController sachCTL = new sachController();
    hoaDonController hdCTL = new hoaDonController();
    danhMucController dmCTL = new danhMucController();
    DefaultListModel modelDMList = new DefaultListModel();
    DefaultTableModel modelDMTable = null;
    Sach[] sachList = null;
    /**
     * Creates new form HoaDonFrame
     */
    public hoaDonFrame(NguoiDung user) {
        initComponents();
        init_Bonus();
        userLabel.setText(user.getTenND());
        userC = user;
    }
    public hoaDonFrame() {
        initComponents();
        init_Bonus();
    }
    //hàm khởi tạo bổ sung
    public void init_Bonus(){
        loadDMList();
        modelDMTable = (DefaultTableModel) danhMucTable.getModel();
        slBox.setValue(1);
        maHDField.setText(String.valueOf(hdCTL.layMaCuoi()));
    }
    //hàm load danh sách cho list
    public void loadDMList(){
        sachList = sachCTL.getData("", false);
        for (Sach sach : sachList) {
            modelDMList.addElement(sach.getMaTc());
        }
        danhMucList.setModel(modelDMList);
    }
    //Hàm refresh về trạng thái ban đầu
    public void refresh(){
        modelDMTable.setRowCount(0);
        danhMucTable.setModel(modelDMTable);
        tongTienField.setText("");
        tienPhaiTraField.setText("");
        khachDuaField.setText("");
        traLaiField.setText("");
    }
    //Hàm chuyển danh muc chọn được từ list sang table
    public void setDMFunc(){
        int index = danhMucList.getSelectedIndex();
        if(index >= 0){
            String tenSach = sachList[index].getTenTc();
            String sl = String.valueOf(slBox.getValue());
            String giaTien = String.valueOf(sachList[index].getGiaTien());
            modelDMTable.addRow(new String[] {tenSach, sl, giaTien});

            danhMucTable.setModel(modelDMTable);
            tinhTienFunc();
        }
    }
    //Hàm xoa danh mục
    public void xoaDMFunc(){
        int[] dsIndex = danhMucTable.getSelectedRows();
        
        if(dsIndex.length>0){
            for (int i = dsIndex[dsIndex.length-1]; i>=dsIndex[0];i-- ) {
                modelDMTable.removeRow(i);
            }
            danhMucTable.setModel(modelDMTable);
        }
        tinhTienFunc();
    }
    //Ham tính tiền
    public void tinhTienFunc(){
        float sum = 0;
        for (int i = 0; i < danhMucTable.getRowCount(); i++){
            int sl = Integer.parseInt((String) danhMucTable.getValueAt(i, 1));
            float gia = Float.parseFloat((String) danhMucTable.getValueAt(i, 2));
            sum += sl*gia;
        }
        tongTienField.setText(String.valueOf((int)sum));
        tienPhaiTraField.setText(String.valueOf((int)sum*1.1));
        
        if(!khachDuaField.getText().equals("")){
            float tienTL = 0;
            try {
                tienTL = (float) (Float.parseFloat(khachDuaField.getText()) - sum*1.1);
                traLaiField.setText(String.valueOf(tienTL));
            } catch (Exception e) {
                JOptionPane.showMessageDialog(rootPane, "Kiểm tra lại thông tin!", "", 0);
            }
        }
        
    }
    //Hàm lưu hoa dơn
    public void luuHDFunc() {
        int maHD = Integer.parseInt(maHDField.getText());
        String date = LocalDate.now().toString();
        int maNd = userC.getMaND();

        HoaDon hd = new HoaDon(maHD, date, maNd);
        try {
            hdCTL.themHoaDon(hd);
            for (int i = 0; i < danhMucTable.getRowCount(); i++) {
                String tenDM = (String) danhMucTable.getValueAt(i, 0);
                int giaTien = Integer.parseInt((String) danhMucTable.getValueAt(i, 2));
                int sl = Integer.parseInt((String) danhMucTable.getValueAt(i, 1));

                DanhMuc dm = new DanhMuc(maHD, tenDM, giaTien, sl);
                dmCTL.themDanhMuc(dm);  // Thêm danh mục

                // Lấy mã sách từ tên sách và giảm số lượng
                String maTc = sachCTL.getMaTcByTen(tenDM);
                sachCTL.giamSoLuong(maTc, sl);  // Giảm số lượng sách
            }
            JOptionPane.showMessageDialog(rootPane, "Thêm thành công!", "", JOptionPane.INFORMATION_MESSAGE);
            maHDField.setText(String.valueOf(Integer.parseInt(maHDField.getText()) + 1));
            refresh();  // Làm mới giao diện đơn hàng
        } catch (Exception e) {
            JOptionPane.showMessageDialog(rootPane, "Thêm thất bại!", "Lỗi", JOptionPane.ERROR_MESSAGE);
            e.printStackTrace();
        }
    }

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jPanel1 = new javax.swing.JPanel();
        jScrollPane1 = new javax.swing.JScrollPane();
        danhMucTable = new javax.swing.JTable();
        jPanel2 = new javax.swing.JPanel();
        jLabel3 = new javax.swing.JLabel();
        maHDField = new javax.swing.JTextField();
        setBTN = new javax.swing.JButton();
        slBox = new javax.swing.JSpinner();
        luuBTN = new javax.swing.JButton();
        jLabel4 = new javax.swing.JLabel();
        khachDuaField = new javax.swing.JFormattedTextField();
        traLaiField = new javax.swing.JFormattedTextField();
        jLabel5 = new javax.swing.JLabel();
        xoaBTN = new javax.swing.JButton();
        tongTienField = new javax.swing.JTextField();
        tienPhaiTraField = new javax.swing.JTextField();
        jLabel2 = new javax.swing.JLabel();
        jLabel1 = new javax.swing.JLabel();
        jScrollPane2 = new javax.swing.JScrollPane();
        danhMucList = new javax.swing.JList<>();
        userLabel = new javax.swing.JLabel();

        setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);

        jPanel1.setBackground(new java.awt.Color(255, 255, 255));

        danhMucTable.setFont(new java.awt.Font("Segoe UI", 0, 14)); // NOI18N
        danhMucTable.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {
                "Tên sách", "Số lượng", "Giá"
            }
        ) {
            Class[] types = new Class [] {
                java.lang.String.class, java.lang.String.class, java.lang.String.class
            };

            public Class getColumnClass(int columnIndex) {
                return types [columnIndex];
            }
        });
        danhMucTable.setSelectionMode(javax.swing.ListSelectionModel.MULTIPLE_INTERVAL_SELECTION);
        jScrollPane1.setViewportView(danhMucTable);
        if (danhMucTable.getColumnModel().getColumnCount() > 0) {
            danhMucTable.getColumnModel().getColumn(0).setMinWidth(60);
        }

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel1Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jScrollPane1, javax.swing.GroupLayout.DEFAULT_SIZE, 287, Short.MAX_VALUE)
                .addContainerGap())
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 0, Short.MAX_VALUE)
                .addContainerGap())
        );

        jPanel2.setBackground(new java.awt.Color(255, 255, 255));

        jLabel3.setText("Mã hóa đơn");

        maHDField.setText("0110");

        setBTN.setBackground(new java.awt.Color(255, 255, 102));
        setBTN.setText("<<");
        setBTN.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                setBTNActionPerformed(evt);
            }
        });

        slBox.setFont(new java.awt.Font("Segoe UI", 0, 18)); // NOI18N

        luuBTN.setText("Lưu");
        luuBTN.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                luuBTNActionPerformed(evt);
            }
        });

        jLabel4.setText("Khách đưa");

        khachDuaField.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyReleased(java.awt.event.KeyEvent evt) {
                khachDuaFieldKeyReleased(evt);
            }
        });

        jLabel5.setText("Trả lại");

        xoaBTN.setBackground(new java.awt.Color(204, 51, 0));
        xoaBTN.setText("Xóa");
        xoaBTN.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                xoaBTNActionPerformed(evt);
            }
        });

        jLabel2.setText("Thành tiền (+VAT)");

        jLabel1.setText("Tổng cộng");

        danhMucList.setFont(new java.awt.Font("Segoe UI", 0, 14)); // NOI18N
        jScrollPane2.setViewportView(danhMucList);

        userLabel.setHorizontalAlignment(javax.swing.SwingConstants.RIGHT);
        userLabel.setText("Nobody");

        javax.swing.GroupLayout jPanel2Layout = new javax.swing.GroupLayout(jPanel2);
        jPanel2.setLayout(jPanel2Layout);
        jPanel2Layout.setHorizontalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addGap(12, 12, 12)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(slBox, javax.swing.GroupLayout.PREFERRED_SIZE, 0, Short.MAX_VALUE)
                    .addComponent(xoaBTN, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(setBTN, javax.swing.GroupLayout.PREFERRED_SIZE, 0, Short.MAX_VALUE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jScrollPane2, javax.swing.GroupLayout.DEFAULT_SIZE, 107, Short.MAX_VALUE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addGroup(jPanel2Layout.createSequentialGroup()
                        .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jLabel3)
                            .addComponent(jLabel4)
                            .addComponent(jLabel5)
                            .addComponent(jLabel1, javax.swing.GroupLayout.PREFERRED_SIZE, 114, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jLabel2))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(jPanel2Layout.createSequentialGroup()
                                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(tienPhaiTraField, javax.swing.GroupLayout.DEFAULT_SIZE, 72, Short.MAX_VALUE)
                                    .addComponent(tongTienField)
                                    .addComponent(traLaiField)
                                    .addComponent(khachDuaField)
                                    .addComponent(maHDField))
                                .addGap(12, 12, 12))
                            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel2Layout.createSequentialGroup()
                                .addComponent(luuBTN)
                                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))))
                    .addGroup(jPanel2Layout.createSequentialGroup()
                        .addComponent(userLabel, javax.swing.GroupLayout.PREFERRED_SIZE, 106, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(12, 12, 12))))
        );
        jPanel2Layout.setVerticalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel2Layout.createSequentialGroup()
                        .addComponent(slBox, javax.swing.GroupLayout.DEFAULT_SIZE, 48, Short.MAX_VALUE)
                        .addGap(24, 24, 24)
                        .addComponent(setBTN, javax.swing.GroupLayout.DEFAULT_SIZE, 40, Short.MAX_VALUE)
                        .addGap(24, 24, 24)
                        .addComponent(xoaBTN, javax.swing.GroupLayout.DEFAULT_SIZE, 40, Short.MAX_VALUE)
                        .addGap(141, 141, 141))
                    .addGroup(jPanel2Layout.createSequentialGroup()
                        .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jLabel3)
                            .addComponent(maHDField, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(31, 31, 31)
                        .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jLabel1)
                            .addComponent(tongTienField, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(18, 18, 18)
                        .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(jPanel2Layout.createSequentialGroup()
                                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                    .addComponent(jLabel2)
                                    .addComponent(tienPhaiTraField, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                                .addGap(58, 58, 58))
                            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel2Layout.createSequentialGroup()
                                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                    .addComponent(khachDuaField, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addComponent(jLabel4))
                                .addGap(14, 14, 14)))
                        .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                            .addComponent(traLaiField, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jLabel5))
                        .addGap(44, 44, 44)
                        .addComponent(luuBTN)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(userLabel))
                    .addComponent(jScrollPane2))
                .addGap(8, 8, 8))
        );

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jPanel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jPanel2, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addContainerGap())
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jPanel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jPanel2, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addContainerGap())
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void setBTNActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_setBTNActionPerformed
        // TODO add your handling code here:
        setDMFunc();
    }//GEN-LAST:event_setBTNActionPerformed

    private void xoaBTNActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_xoaBTNActionPerformed
        // TODO add your handling code here:
        xoaDMFunc();
    }//GEN-LAST:event_xoaBTNActionPerformed

    private void khachDuaFieldKeyReleased(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_khachDuaFieldKeyReleased
        // TODO add your handling code here:
        tinhTienFunc();
    }//GEN-LAST:event_khachDuaFieldKeyReleased

    private void luuBTNActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_luuBTNActionPerformed
        // TODO add your handling code here:
        luuHDFunc();
    }//GEN-LAST:event_luuBTNActionPerformed

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
            java.util.logging.Logger.getLogger(hoaDonFrame.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(hoaDonFrame.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(hoaDonFrame.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(hoaDonFrame.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new hoaDonFrame().setVisible(true);
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JList<String> danhMucList;
    private javax.swing.JTable danhMucTable;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JScrollPane jScrollPane2;
    private javax.swing.JFormattedTextField khachDuaField;
    private javax.swing.JButton luuBTN;
    private javax.swing.JTextField maHDField;
    private javax.swing.JButton setBTN;
    private javax.swing.JSpinner slBox;
    private javax.swing.JTextField tienPhaiTraField;
    private javax.swing.JTextField tongTienField;
    private javax.swing.JFormattedTextField traLaiField;
    private javax.swing.JLabel userLabel;
    private javax.swing.JButton xoaBTN;
    // End of variables declaration//GEN-END:variables
}
