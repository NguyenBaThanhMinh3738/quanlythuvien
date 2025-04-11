/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/GUIForms/JFrame.java to edit this template
 */
package views;

import controllers.loaiController;
import controllers.sachController;
import data.excelFile;
import javax.swing.DefaultComboBoxModel;
import javax.swing.ImageIcon;
import javax.swing.JOptionPane;
import javax.swing.table.DefaultTableModel;
import models.Loai;
import models.NguoiDung;
import models.Sach;
import util.imageProcess;

public class homeFame extends javax.swing.JFrame {

    loaiController loaiCTL = new loaiController();
    sachController tcCTL = new sachController();
    Sach[] TC = tcCTL.getData("", true);
    DefaultTableModel modelHome = null;
    DefaultComboBoxModel modelLoai = null;
    NguoiDung user = new NguoiDung();
    imageProcess imagePRC = new imageProcess();

    /**
     * Creates new form homeFame
     */
    public homeFame() {
        initComponents();
        init();
    }

    public homeFame(NguoiDung u) {
        user = u;
        initComponents();
        init();
        userNameLabel.setText(user.getTenND());
    }

    public void init() {
        setTitle("Quản lý hiệu sách");
        loadTable();
        loadLoaiCB();
        setInfor();

        //set ICON
        ImageIcon icon2 = new ImageIcon("D://quanlyhieusach//QuanLyHieuSach_v1001//QuanLyHieuSach//icon2.png");
        ImageIcon icon3 = new ImageIcon("D://quanlyhieusach//QuanLyHieuSach_v1001//QuanLyHieuSach//icon3.png");
        ImageIcon icon4 = new ImageIcon("D://quanlyhieusach//QuanLyHieuSach_v1001//QuanLyHieuSach///icon4.png");

        icon2 = imagePRC.scaleImage(icon2, visualLabel);
        icon3 = imagePRC.scaleImage(icon3, visualLabel);
        icon4 = imagePRC.scaleImage(icon4, anhLabel);

        setUserBTN.setIcon(icon2);
        exitBTN.setIcon(icon3);
        anhLabel.setIcon(icon4);
    }

    public void refresh() {
        loadTable();
        setInfor();
    }
// hàm lọc sách
    public void loadLoaiCB() {
        Loai[] loai = loaiCTL.getLoaiList();
        modelLoai = new DefaultComboBoxModel();
        modelLoai.addElement("All");
        for (int i = 0; i < loai.length; i++) {
            modelLoai.addElement(loai[i].getTenLoai());
        }
        LoaiCB.setModel(modelLoai);
    }
// ham hien thi danh sach tap chi
    public void loadTable() {
        TC = tcCTL.getData(loaiCTL.getMa((String) LoaiCB.getSelectedItem()), isAll());
        modelHome = (DefaultTableModel) homeTable.getModel(); //lay mo hinh du lieu bang
        modelHome.setRowCount(0); // Xóa dữ liệu cũ trên bảng
        if (TC.length > 0) {
            for (int i = 0; i < TC.length; i++) { //Duyet qua danh sach
                String[] data = {
                    TC[i].getMaTc(),
                    TC[i].getTenTc(),
                    String.valueOf(TC[i].getSoLuong()),
                    TC[i].getNxb(),
                    TC[i].getNgayXb(),
                    TC[i].getLoai(),
                    String.valueOf(TC[i].getGiaTien())
                };
                modelHome.addRow(data); // Hien thi du lieu len bang, Thêm từng dòng sách vào bảng
            }
        }
    }

    //Ham seleted{
    public boolean isAll() {
        return !slHienCoCheckBox.isSelected();
    }

    //Ham thong tin phân tích
    public void setInfor() {
        int count = 0;
        int max = 0;
        tenTcMaxField.setText("");
        String ten = "";
        for (Sach TC1 : TC) {
            count++;
            if (TC1.getSoLuong() >= max) {
                max = TC1.getSoLuong();
            }
        }
        for (Sach TC1 : TC) {
            if (TC1.getSoLuong() == max) {
                ten = ten.concat(TC1.getTenTc() + "\n");
            }
        }
        sumLabel.setText(String.valueOf(count));
        tenTcMaxField.setText(ten);
    }

    ///--------------------------------------
    public void reloadFunc() {
        refresh();
    }

    public void hienCoFun() {
        loadTable();
    }

    //ham tìm tap chi
    public void searchFunc() {
        resultSearchField.setText("");  // truong tim kiem
        String key = searchField.getText(); // lay key nhap tu truong tim kiem
        String kq = "";
        String gia = "";
        Sach[] tcKey = tcCTL.Search(key);
        for (Sach tapChi : tcKey) {
            gia = loaiCTL.getKe(tapChi.getLoai());
            kq = kq.concat(gia + " - " + tapChi.getTenTc() + "\n");
        }
        resultSearchField.setText(kq);
    }

    //ham hien thi giao dien them tap chi moi
    public void themFunc() {
        themFrame themFrame = new themFrame();
        themFrame.setVisible(true);
    }

    public void caiDatFunc() {
        settingFrame settFrame = new settingFrame();
        settFrame.setVisible(true);
    }

    public void nhapExcelViewFunc() {
        nhapExcelFrame nhapExcelFrame = new nhapExcelFrame();
        nhapExcelFrame.setVisible(true);
    }
// xuat ra file excel
    public void xuatExcelFunc() {
        try {
            excelFile exCTL = new excelFile();
            exCTL.exportExcel(homeTable);
            JOptionPane.showMessageDialog(rootPane, "Xuất file excel thành công!");
        } catch (Exception e) {
            JOptionPane.showMessageDialog(rootPane, "Xuất file excel không thành công!", "Lỗi", 0);
        }
    }
//hàm hien thi chinh sua thong tin sach
    public void chiTietFunc() {
        int i = homeTable.getSelectedRow();
        if (i > -1) {
            chiTietFrame chiTietFrame = new chiTietFrame(TC[i]); // Mở giao diện chi tiết với thông tin sách
            chiTietFrame.setVisible(true);
        }
    }

    //Ham cài đặt thông tin người dùng
    public void userSettingFunc() {
        userSettingFrame userSettingFrame = new userSettingFrame(user);
        userSettingFrame.setVisible(true);
    }

    //Ham xoa sach
    public void xoaNhieuTCFunc() {
        int[] TCsels = homeTable.getSelectedRows(); // lay cac chi so cua cac dong duoc chon
        try {
            for (int TCsel : TCsels) {
                String ma = (String) homeTable.getValueAt(TCsel, 0); // lấy mã sách từ cột đầu tiên của dòng đc chọn
                tcCTL.deleteTapChi(ma);
                System.out.println("Deleted " + ma);
            }
            JOptionPane.showMessageDialog(rootPane, "Xóa thành công!");
        } catch (Exception e) {
            JOptionPane.showMessageDialog(rootPane, "Lỗi không xóa được", "Lỗi", 0);
            System.err.println("Error: " + e.getMessage());
        }
        refresh();
    }

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jScrollPane1 = new javax.swing.JScrollPane();
        homeTable = new javax.swing.JTable();
        jPanel1 = new javax.swing.JPanel();
        jButton3 = new javax.swing.JButton();
        jButton4 = new javax.swing.JButton();
        jButton5 = new javax.swing.JButton();
        themBTN = new javax.swing.JButton();
        reloadBTN = new javax.swing.JButton();
        LoaiCB = new javax.swing.JComboBox<>();
        jButton9 = new javax.swing.JButton();
        slHienCoCheckBox = new javax.swing.JCheckBox();
        ReloadLoaiLabel = new javax.swing.JLabel();
        sumLabel = new javax.swing.JLabel();
        jLabel5 = new javax.swing.JLabel();
        jPanel4 = new javax.swing.JPanel();
        jScrollPane4 = new javax.swing.JScrollPane();
        resultSearchField = new javax.swing.JTextArea();
        searchField = new javax.swing.JTextField();
        searchBTN = new javax.swing.JButton();
        jScrollPane3 = new javax.swing.JScrollPane();
        tenTcMaxField = new javax.swing.JTextArea();
        updateBTN = new javax.swing.JButton();
        visualLabel = new javax.swing.JLabel();
        DonBTN = new javax.swing.JButton();
        taoDonBTN = new javax.swing.JButton();
        jPanel2 = new javax.swing.JPanel();
        anhLabel = new javax.swing.JLabel();
        userNameLabel = new javax.swing.JLabel();
        exitBTN = new javax.swing.JButton();
        setUserBTN = new javax.swing.JButton();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        setPreferredSize(new java.awt.Dimension(1048, 670));

        homeTable.setFont(new java.awt.Font("Segoe UI", 0, 14)); // NOI18N
        homeTable.setForeground(new java.awt.Color(0, 51, 51));
        homeTable.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {
                "Mã ", "Tên ", "SL", "NXB", "Ngày xuất bản", "Loại", "Giá (VND)"
            }
        ) {
            Class[] types = new Class [] {
                java.lang.String.class, java.lang.String.class, java.lang.String.class, java.lang.String.class, java.lang.String.class, java.lang.String.class, java.lang.String.class
            };
            boolean[] canEdit = new boolean [] {
                false, true, true, true, true, true, true
            };

            public Class getColumnClass(int columnIndex) {
                return types [columnIndex];
            }

            public boolean isCellEditable(int rowIndex, int columnIndex) {
                return canEdit [columnIndex];
            }
        });
        jScrollPane1.setViewportView(homeTable);

        jPanel1.setBackground(new java.awt.Color(255, 255, 255));

        jButton3.setText("CHI TIẾT");
        jButton3.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton3ActionPerformed(evt);
            }
        });

        jButton4.setText("NHẬP");
        jButton4.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton4ActionPerformed(evt);
            }
        });

        jButton5.setText("XUẤT");
        jButton5.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton5ActionPerformed(evt);
            }
        });

        themBTN.setText("THÊM");
        themBTN.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                themBTNActionPerformed(evt);
            }
        });

        reloadBTN.setText("TẢI LẠI");
        reloadBTN.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                reloadBTNActionPerformed(evt);
            }
        });

        jButton9.setText("CÀI ĐẶT");
        jButton9.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton9ActionPerformed(evt);
            }
        });

        slHienCoCheckBox.setText("Hiện có");
        slHienCoCheckBox.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                slHienCoCheckBoxActionPerformed(evt);
            }
        });

        ReloadLoaiLabel.setForeground(new java.awt.Color(153, 153, 153));
        ReloadLoaiLabel.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        ReloadLoaiLabel.setText("RL");
        ReloadLoaiLabel.setMinimumSize(new java.awt.Dimension(22, 22));
        ReloadLoaiLabel.setPreferredSize(new java.awt.Dimension(22, 22));
        ReloadLoaiLabel.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                ReloadLoaiLabelMouseClicked(evt);
            }
        });

        sumLabel.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        sumLabel.setText("0");

        jLabel5.setText("Sách còn nhiều nhất");

        resultSearchField.setEditable(false);
        resultSearchField.setColumns(20);
        resultSearchField.setLineWrap(true);
        resultSearchField.setRows(10);
        jScrollPane4.setViewportView(resultSearchField);

        searchBTN.setText("TÌM");
        searchBTN.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                searchBTNActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel4Layout = new javax.swing.GroupLayout(jPanel4);
        jPanel4.setLayout(jPanel4Layout);
        jPanel4Layout.setHorizontalGroup(
            jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel4Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jScrollPane4)
                    .addGroup(jPanel4Layout.createSequentialGroup()
                        .addComponent(searchField)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(searchBTN)))
                .addContainerGap())
        );
        jPanel4Layout.setVerticalGroup(
            jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel4Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(searchField, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(searchBTN))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jScrollPane4, javax.swing.GroupLayout.PREFERRED_SIZE, 0, Short.MAX_VALUE)
                .addContainerGap())
        );

        tenTcMaxField.setColumns(1);
        tenTcMaxField.setLineWrap(true);
        tenTcMaxField.setRows(10);
        jScrollPane3.setViewportView(tenTcMaxField);

        updateBTN.setText("XÓA");
        updateBTN.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                updateBTNActionPerformed(evt);
            }
        });

        visualLabel.setName("visualLabel"); // NOI18N
        visualLabel.setPreferredSize(new java.awt.Dimension(72, 23));

        DonBTN.setText("ĐƠN");
        DonBTN.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                DonBTNActionPerformed(evt);
            }
        });

        taoDonBTN.setBackground(new java.awt.Color(0, 102, 0));
        taoDonBTN.setForeground(new java.awt.Color(255, 255, 255));
        taoDonBTN.setText("TẠO ĐƠN");
        taoDonBTN.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                taoDonBTNActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel1Layout.createSequentialGroup()
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addComponent(visualLabel, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(32, 32, 32)
                        .addComponent(ReloadLoaiLabel, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(LoaiCB, 0, 173, Short.MAX_VALUE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 42, Short.MAX_VALUE))
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addContainerGap()
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(jPanel1Layout.createSequentialGroup()
                                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                                    .addComponent(DonBTN, javax.swing.GroupLayout.DEFAULT_SIZE, 82, Short.MAX_VALUE)
                                    .addComponent(jButton4, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                                .addGap(0, 0, Short.MAX_VALUE))
                            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel1Layout.createSequentialGroup()
                                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(jButton5, javax.swing.GroupLayout.PREFERRED_SIZE, 82, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addComponent(jButton9, javax.swing.GroupLayout.PREFERRED_SIZE, 82, javax.swing.GroupLayout.PREFERRED_SIZE))
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 100, Short.MAX_VALUE)
                                .addComponent(jLabel5, javax.swing.GroupLayout.PREFERRED_SIZE, 141, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(0, 18, Short.MAX_VALUE)))))
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addComponent(jScrollPane3)
                        .addGap(26, 26, 26))
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addComponent(slHienCoCheckBox)
                        .addGap(33, 33, 33)
                        .addComponent(sumLabel, javax.swing.GroupLayout.PREFERRED_SIZE, 24, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 133, Short.MAX_VALUE)))
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addGroup(javax.swing.GroupLayout.Alignment.LEADING, jPanel1Layout.createSequentialGroup()
                        .addComponent(taoDonBTN, javax.swing.GroupLayout.DEFAULT_SIZE, 93, Short.MAX_VALUE)
                        .addGap(18, 18, 18)
                        .addComponent(reloadBTN)
                        .addGap(18, 18, 18)
                        .addComponent(themBTN)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(jButton3)
                        .addGap(18, 18, 18)
                        .addComponent(updateBTN))
                    .addComponent(jPanel4, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addContainerGap())
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                        .addComponent(jButton3)
                        .addComponent(themBTN)
                        .addComponent(reloadBTN)
                        .addComponent(LoaiCB, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addComponent(updateBTN)
                        .addComponent(ReloadLoaiLabel, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addComponent(taoDonBTN)
                        .addComponent(slHienCoCheckBox)
                        .addComponent(sumLabel))
                    .addComponent(visualLabel, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(28, 28, 28)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel1Layout.createSequentialGroup()
                        .addComponent(jButton9, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(jButton5, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jButton4, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(DonBTN, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel1Layout.createSequentialGroup()
                        .addGap(21, 21, 21)
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(jPanel1Layout.createSequentialGroup()
                                .addComponent(jLabel5, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                .addGap(79, 79, 79))
                            .addComponent(jScrollPane3, javax.swing.GroupLayout.PREFERRED_SIZE, 0, Short.MAX_VALUE))))
                .addContainerGap())
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel1Layout.createSequentialGroup()
                .addGap(35, 35, 35)
                .addComponent(jPanel4, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        jPanel2.setBackground(new java.awt.Color(255, 255, 255));

        anhLabel.setBackground(new java.awt.Color(204, 204, 204));
        anhLabel.setForeground(new java.awt.Color(102, 102, 102));
        anhLabel.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);

        userNameLabel.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
        userNameLabel.setForeground(new java.awt.Color(0, 102, 102));
        userNameLabel.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        userNameLabel.setText("Nobody");

        exitBTN.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                exitBTNActionPerformed(evt);
            }
        });

        setUserBTN.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                setUserBTNActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel2Layout = new javax.swing.GroupLayout(jPanel2);
        jPanel2.setLayout(jPanel2Layout);
        jPanel2Layout.setHorizontalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel2Layout.createSequentialGroup()
                        .addContainerGap()
                        .addComponent(userNameLabel, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                    .addGroup(jPanel2Layout.createSequentialGroup()
                        .addGap(22, 22, 22)
                        .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                            .addComponent(setUserBTN, javax.swing.GroupLayout.PREFERRED_SIZE, 0, Short.MAX_VALUE)
                            .addComponent(exitBTN, javax.swing.GroupLayout.PREFERRED_SIZE, 55, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(0, 17, Short.MAX_VALUE))
                    .addGroup(jPanel2Layout.createSequentialGroup()
                        .addContainerGap()
                        .addComponent(anhLabel, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)))
                .addContainerGap())
        );
        jPanel2Layout.setVerticalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(anhLabel, javax.swing.GroupLayout.PREFERRED_SIZE, 56, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(userNameLabel)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 198, Short.MAX_VALUE)
                .addComponent(setUserBTN)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(exitBTN)
                .addGap(17, 17, 17))
        );

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(jPanel2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(18, 18, 18)
                        .addComponent(jScrollPane1))
                    .addComponent(jPanel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addContainerGap())
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 0, Short.MAX_VALUE)
                    .addComponent(jPanel2, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addGap(18, 18, 18)
                .addComponent(jPanel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addContainerGap())
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void ReloadLoaiLabelMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_ReloadLoaiLabelMouseClicked
        // TODO add your handling code here:
        loadLoaiCB();
    }//GEN-LAST:event_ReloadLoaiLabelMouseClicked

    private void slHienCoCheckBoxActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_slHienCoCheckBoxActionPerformed
        // TODO add your handling code here:
        hienCoFun();
    }//GEN-LAST:event_slHienCoCheckBoxActionPerformed

    private void reloadBTNActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_reloadBTNActionPerformed
        // TODO add your handling code here:
        reloadFunc();
    }//GEN-LAST:event_reloadBTNActionPerformed

    private void searchBTNActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_searchBTNActionPerformed
        // TODO add your handling code here:
        searchFunc();
    }//GEN-LAST:event_searchBTNActionPerformed

    private void themBTNActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_themBTNActionPerformed
        // TODO add your handling code here:
        themFunc();
    }//GEN-LAST:event_themBTNActionPerformed

    private void jButton9ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton9ActionPerformed
        // TODO add your handling code here:
        caiDatFunc();
    }//GEN-LAST:event_jButton9ActionPerformed

    private void jButton4ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton4ActionPerformed
        // TODO add your handling code here:
        nhapExcelViewFunc();
    }//GEN-LAST:event_jButton4ActionPerformed

    private void jButton5ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton5ActionPerformed
        // TODO add your handling code here:
        xuatExcelFunc();
    }//GEN-LAST:event_jButton5ActionPerformed

    private void jButton3ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton3ActionPerformed
        // TODO add your handling code here:
        chiTietFunc();
    }//GEN-LAST:event_jButton3ActionPerformed

    private void exitBTNActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_exitBTNActionPerformed
        // TODO add your handling code here:
        int option = JOptionPane.showConfirmDialog(rootPane, "Bạn muốn thoát chương trình?",
                "Thoát", JOptionPane.YES_NO_OPTION);
        if (option == 0) {
            dispose();
        }
    }//GEN-LAST:event_exitBTNActionPerformed

    private void setUserBTNActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_setUserBTNActionPerformed
        // TODO add your handling code here:
        userSettingFunc();
    }//GEN-LAST:event_setUserBTNActionPerformed

    private void updateBTNActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_updateBTNActionPerformed
        // TODO add your handling code here:
        xoaNhieuTCFunc();
    }//GEN-LAST:event_updateBTNActionPerformed

    private void taoDonBTNActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_taoDonBTNActionPerformed
        // TODO add your handling code here:
        hoaDonFrame hoaDonFrame = new hoaDonFrame(user);
        hoaDonFrame.setVisible(true);
    }//GEN-LAST:event_taoDonBTNActionPerformed

    private void DonBTNActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_DonBTNActionPerformed
        // TODO add your handling code here:
        DShoaDonFrame dShoaDonFrame = new DShoaDonFrame();
        dShoaDonFrame.setVisible(true);
    }//GEN-LAST:event_DonBTNActionPerformed

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
            java.util.logging.Logger.getLogger(homeFame.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(homeFame.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(homeFame.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(homeFame.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new homeFame().setVisible(true);
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton DonBTN;
    private javax.swing.JComboBox<String> LoaiCB;
    private javax.swing.JLabel ReloadLoaiLabel;
    private javax.swing.JLabel anhLabel;
    private javax.swing.JButton exitBTN;
    private javax.swing.JTable homeTable;
    private javax.swing.JButton jButton3;
    private javax.swing.JButton jButton4;
    private javax.swing.JButton jButton5;
    private javax.swing.JButton jButton9;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JPanel jPanel4;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JScrollPane jScrollPane3;
    private javax.swing.JScrollPane jScrollPane4;
    private javax.swing.JButton reloadBTN;
    private javax.swing.JTextArea resultSearchField;
    private javax.swing.JButton searchBTN;
    private javax.swing.JTextField searchField;
    private javax.swing.JButton setUserBTN;
    private javax.swing.JCheckBox slHienCoCheckBox;
    private javax.swing.JLabel sumLabel;
    private javax.swing.JButton taoDonBTN;
    private javax.swing.JTextArea tenTcMaxField;
    private javax.swing.JButton themBTN;
    private javax.swing.JButton updateBTN;
    private javax.swing.JLabel userNameLabel;
    private javax.swing.JLabel visualLabel;
    // End of variables declaration//GEN-END:variables
}
