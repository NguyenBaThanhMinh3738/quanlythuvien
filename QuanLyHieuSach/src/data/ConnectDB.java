/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package data;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;

public class ConnectDB {
    public static Connection con;
    public static Statement stmt;
    public ConnectDB(){
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
            con = (Connection) DriverManager.getConnection("jdbc:mysql://localhost:3306/quanlyhieusach", "root", "root");
            stmt = con.createStatement();
        } catch (ClassNotFoundException | SQLException ex) {
            System.err.println("Loi file ConnectDB.java: " + ex.getMessage());
        }
    }
 
}
