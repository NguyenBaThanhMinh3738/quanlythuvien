/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/GUIForms/JFrame.java to edit this template
 */
package views;

import controllers.danhMucController;
import controllers.hoaDonController;
import controllers.nguoiDungController;
import java.time.LocalDate;
import javax.swing.DefaultComboBoxModel;
import javax.swing.JComboBox;
import javax.swing.JOptionPane;
import javax.swing.table.DefaultTableModel;
import models.DanhMuc;
import models.HoaDon;


public class DShoaDonFrame extends javax.swing.JFrame {
    hoaDonController hoaDonCTL = new hoaDonController();
    nguoiDungController ndCTL = new nguoiDungController();
    danhMucController dmCTL = new danhMucController();
    DefaultTableModel modelHDTable = null;
    DefaultTableModel modelDMTable = null;
    HoaDon[] hds = null;
    DanhMuc[] dms = null;
    /**
     * Creates new form DShoaDonFrame
     */
    public DShoaDonFrame() {
        initComponents();
        init_bonus();
    }
    public void init_bonus(){
        modelHDTable = (DefaultTableModel) hoaDonTable.getModel();
        modelDMTable = (DefaultTableModel) danhMucTable.getModel();
        setSchedule(yearCB, monthCB, dayCB);
        loadData();
        DateToF(false);
    }
    public void DateToF(boolean bool){
        yearCB.setEnabled(bool);
        monthCB.setEnabled(bool);
        dayCB.setEnabled(bool);
    }
    public void setDate(){
        if(dateCheckBox.isSelected()){
            DateToF(true);
        }else{
            DateToF(false);
        }
    }
    public void loadData(){
        hds = null;
        modelHDTable.setRowCount(0);
        dms = null;
        modelDMTable.setRowCount(0);
        
        if(dateCheckBox.isSelected()){
            String date = yearCB.getSelectedItem()+"-"+monthCB.getSelectedItem()+"-"+dayCB.getSelectedItem();
            hds = hoaDonCTL.getHoaDon(date);
        }else{
            hds = hoaDonCTL.getHoaDon(null);
        }
        System.out.println(hds.length);
        for (HoaDon hd : hds) {
            String maHD = String.valueOf(hd.getMaHD());
            String ngayTao = hd.getNgayTao();
            String nguoiTao = ndCTL.getUserByID(hd.getMaNd()).getTenND();
            modelHDTable.addRow(new String[]{maHD, ngayTao,nguoiTao});
        }
        
        hoaDonTable.setModel(modelHDTable);
    }
    //lay cac danh muc trong hoa don
    public void chiTietHDFunc(){
        if(hoaDonTable.getSelectedRow()>-1){
            dms = null;
            modelDMTable.setRowCount(0);
            int maHD = Integer.parseInt((String) hoaDonTable.getValueAt(hoaDonTable.getSelectedRow(), 0));
            dms = dmCTL.getDanhMucs(maHD);
            for (DanhMuc dm : dms) {
                String tenDM = dm.getTenDM();
                String sl = String.valueOf(dm.getSoLuong());
                String giaTien  = String.valueOf(dm.getGiaTien());
                modelDMTable.addRow(new String[]{tenDM,sl,giaTien});
            }
        }
        
    }
    //xoa hoa don 
    public void xoaFunc(){
        int[] index = hoaDonTable.getSelectedRows();
        if(index.length >0){
            for (int i : index) {
                int maHD = Integer.parseInt((String) hoaDonTable.getValueAt(i, 0));
                dmCTL.xoaDMs(maHD);
                hoaDonCTL.xoaHDs(maHD);
            }
            JOptionPane.showMessageDialog(rootPane, "Đã xóa xong!","", 1);
        }
        loadData();
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
        hoaDonTable = new javax.swing.JTable();
        jScrollPane2 = new javax.swing.JScrollPane();
        danhMucTable = new javax.swing.JTable();
        yearCB = new javax.swing.JComboBox<>();
        monthCB = new javax.swing.JComboBox<>();
        dayCB = new javax.swing.JComboBox<>();
        jLabel1 = new javax.swing.JLabel();
        xoaBTN = new javax.swing.JButton();
        taiLaiBTN = new javax.swing.JButton();
        dateCheckBox = new javax.swing.JCheckBox();

        setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);

        jPanel1.setBackground(new java.awt.Color(255, 255, 255));

        hoaDonTable.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {
                "Mã HD", "Ngày tạo", "Người tạo"
            }
        ) {
            Class[] types = new Class [] {
                java.lang.String.class, java.lang.String.class, java.lang.String.class
            };

            public Class getColumnClass(int columnIndex) {
                return types [columnIndex];
            }
        });
        hoaDonTable.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                hoaDonTableMouseClicked(evt);
            }
        });
        jScrollPane1.setViewportView(hoaDonTable);

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
        jScrollPane2.setViewportView(danhMucTable);

        yearCB.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                yearCBActionPerformed(evt);
            }
        });

        monthCB.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                monthCBActionPerformed(evt);
            }
        });

        jLabel1.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel1.setText("DANH SÁCH ĐƠN HÀNG");

        xoaBTN.setText("XÓA");
        xoaBTN.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                xoaBTNActionPerformed(evt);
            }
        });

        taiLaiBTN.setText("Tải lại");
        taiLaiBTN.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                taiLaiBTNActionPerformed(evt);
            }
        });

        dateCheckBox.setText("Theo ngày");
        dateCheckBox.addChangeListener(new javax.swing.event.ChangeListener() {
            public void stateChanged(javax.swing.event.ChangeEvent evt) {
                dateCheckBoxStateChanged(evt);
            }
        });

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel1Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel1Layout.createSequentialGroup()
                        .addGap(217, 217, 217)
                        .addComponent(jLabel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addGap(217, 217, 217))
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel1Layout.createSequentialGroup()
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                            .addGroup(jPanel1Layout.createSequentialGroup()
                                .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 0, Short.MAX_VALUE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(jScrollPane2, javax.swing.GroupLayout.PREFERRED_SIZE, 0, Short.MAX_VALUE))
                            .addGroup(jPanel1Layout.createSequentialGroup()
                                .addGap(33, 33, 33)
                                .addComponent(dateCheckBox)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                .addComponent(yearCB, javax.swing.GroupLayout.PREFERRED_SIZE, 75, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(monthCB, javax.swing.GroupLayout.PREFERRED_SIZE, 75, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(dayCB, javax.swing.GroupLayout.PREFERRED_SIZE, 75, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(77, 77, 77)
                                .addComponent(taiLaiBTN, javax.swing.GroupLayout.PREFERRED_SIZE, 77, javax.swing.GroupLayout.PREFERRED_SIZE)))
                        .addContainerGap())))
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel1Layout.createSequentialGroup()
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(xoaBTN)
                .addContainerGap())
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel1Layout.createSequentialGroup()
                .addGap(10, 10, 10)
                .addComponent(jLabel1)
                .addGap(18, 18, 18)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(yearCB, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(monthCB, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(dayCB, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(taiLaiBTN)
                    .addComponent(dateCheckBox))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jScrollPane1, javax.swing.GroupLayout.DEFAULT_SIZE, 299, Short.MAX_VALUE)
                    .addComponent(jScrollPane2, javax.swing.GroupLayout.PREFERRED_SIZE, 0, Short.MAX_VALUE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(xoaBTN)
                .addGap(14, 14, 14))
        );

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jPanel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addContainerGap())
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jPanel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addContainerGap())
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void taiLaiBTNActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_taiLaiBTNActionPerformed
         // TODO add your handling code here:
         loadData();
    }//GEN-LAST:event_taiLaiBTNActionPerformed

    private void yearCBActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_yearCBActionPerformed
        // TODO add your handling code here:
        updateInfor();
    }//GEN-LAST:event_yearCBActionPerformed

    private void monthCBActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_monthCBActionPerformed
        // TODO add your handling code here:
        updateInfor();
    }//GEN-LAST:event_monthCBActionPerformed

    private void dateCheckBoxStateChanged(javax.swing.event.ChangeEvent evt) {//GEN-FIRST:event_dateCheckBoxStateChanged
        // TODO add your handling code here:
        setDate();
    }//GEN-LAST:event_dateCheckBoxStateChanged

    private void hoaDonTableMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_hoaDonTableMouseClicked
        // TODO add your handling code here:
        chiTietHDFunc();
    }//GEN-LAST:event_hoaDonTableMouseClicked

    private void xoaBTNActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_xoaBTNActionPerformed
        // TODO add your handling code here:
        xoaFunc();
    }//GEN-LAST:event_xoaBTNActionPerformed

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
            java.util.logging.Logger.getLogger(DShoaDonFrame.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(DShoaDonFrame.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(DShoaDonFrame.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(DShoaDonFrame.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new DShoaDonFrame().setVisible(true);
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JTable danhMucTable;
    private javax.swing.JCheckBox dateCheckBox;
    private javax.swing.JComboBox<String> dayCB;
    private javax.swing.JTable hoaDonTable;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JScrollPane jScrollPane2;
    private javax.swing.JComboBox<String> monthCB;
    private javax.swing.JButton taiLaiBTN;
    private javax.swing.JButton xoaBTN;
    private javax.swing.JComboBox<String> yearCB;
    // End of variables declaration//GEN-END:variables
    //Ham update du lieu 
    public void updateInfor(){
        dayCB.setModel(new DefaultComboBoxModel(new String[]{}));
        if(monthCB.getSelectedIndex()>=0 && yearCB.getSelectedIndex()>=0){
            int monthNow = LocalDate.now().getMonthValue();
            int yearNow = LocalDate.now().getYear();
            int year = Integer.parseInt(String.valueOf(yearCB.getSelectedItem()));
            int month = Integer.parseInt(String.valueOf(monthCB.getSelectedItem()));
            int dayCount = 30;
            if(month == monthNow && year == yearNow){
                dayCount = LocalDate.now().getDayOfMonth();
            }
            else if(month == 2){
                dayCount = 28;
            }
            else if(month <= 8){
                if(month == 8 || month%2 == 1){
                    dayCount = 31;
                }
                else{
                    dayCount = 30;
                }
            }else if (month%2 == 1) dayCount = 30;
            else dayCount = 31;
            
            for(int i = dayCount; i>=1; i--){
                if(i<10){
                    dayCB.addItem("0"+i);
                }else{
                    dayCB.addItem(""+i);
                }
            }
        }
        dayCB.setSelectedItem(LocalDate.now().getDayOfMonth());
    }
    //Ham cai comboBox
    public void setSchedule(JComboBox yearCB, JComboBox monthCB,JComboBox dayCB ){
        //Them nam
        int currentYear = LocalDate.now().getYear();
        for(int i = currentYear; i>=2000;i--){
            yearCB.addItem(i);
        }
        //Them thang cho months
        for(int i = 1; i<=12;i++){
            if(i<10){
                monthCB.addItem("0"+i);
            }else{
                monthCB.addItem(i);
            }
        }
        monthCB.setSelectedItem(LocalDate.now().getMonthValue());
    }
}

