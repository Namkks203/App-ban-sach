package com.namkks.appbansach123.controller;

import android.os.StrictMode;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.logging.Level;
import java.util.logging.Logger;

public class DAO {
    public Connection conn;
//    String url = "sql12.freesqldatabase.com";
//    String database = "sql12732743";
//    String user = "sql12732743";
//    String password = "ARbEa7Q5ZR";
    String url = "192.168.43.192";
    String database = "quanlybansach";
    String user = "namkks";
    String password = "Namkks203";
    public DAO() {
        StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
        StrictMode.setThreadPolicy(policy);
        try {
            Class.forName("com.mysql.jdbc.Driver");
            conn = DriverManager.getConnection("jdbc:mysql://"+url+":3306/"
                    +database+"?user="+user+"&password="+password+"&characterEncoding=UTF-8");
        } catch (SQLException | ClassNotFoundException ex) {
            Logger.getLogger(DAO.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    public void Close() {
        try {
            conn.close();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
}
