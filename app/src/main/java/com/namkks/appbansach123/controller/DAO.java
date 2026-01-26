package com.namkks.appbansach123.controller;

import android.os.StrictMode;
import android.widget.Toast;

import com.namkks.appbansach123.BuildConfig;
import com.namkks.appbansach123.view.MainActivity;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.logging.Level;
import java.util.logging.Logger;

public class DAO {
    public Connection con;
    String url = BuildConfig.BASE_URL;
    String database = "quan_ly_ban_va_thue_sach";
    String user = "namkks";
    String password = "Namkks203";
    public DAO() {
        StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
        StrictMode.setThreadPolicy(policy);
        try {
            Class.forName("com.mysql.jdbc.Driver");
            con = DriverManager.getConnection("jdbc:mysql://"+url+":3306/"
                    +database+"?user="+user+"&password="+password+"&characterEncoding=UTF-8");
        } catch (SQLException | ClassNotFoundException ex) {
            Logger.getLogger(DAO.class.getName()).log(Level.SEVERE, null, ex);
            throw new RuntimeException(ex.getMessage());
        }
    }
    public void close() {
        try {
            con.close();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
}
