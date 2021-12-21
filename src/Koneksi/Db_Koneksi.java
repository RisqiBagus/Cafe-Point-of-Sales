/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Koneksi;


import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import javax.swing.JOptionPane;


/**
 *
 * @author Risqi Bagus Palevi
 */
public class Db_Koneksi {
    private static Connection conn;
    public static Connection getKoneksi(){
        String host = "jdbc:mysql://localhost/db_cafe",
                user = "root",
                pass = "";
        try {
            conn = (Connection) DriverManager.getConnection(host,user,pass);
        }catch(SQLException err) {
            JOptionPane.showMessageDialog(null, err.getMessage());
        }
        return conn;
        
    }
}