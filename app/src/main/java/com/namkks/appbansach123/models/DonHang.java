package com.namkks.appbansach123.models;

import com.namkks.appbansach123.controller.DAO;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class DonHang {
    private int id;
    private int id_khachhang;

    public DonHang() {
    }

    public DonHang(int id, int id_khachhang) {
        this.id = id;
        this.id_khachhang = id_khachhang;
    }
//    public int addDonHang(){
//        try {
//            if(GioHang.getGioHang(this.id_khachhang) != null){
//                DAO a = new DAO();
//                PreparedStatement stm = a.con.prepareStatement("INSERT INTO `donhang`(`id_khachhang`,trang_thai) VALUES (?, 0)");
//                stm.setInt(1, this.id_khachhang);
//
//                if(stm.executeUpdate() > 0){
//                    stm = a.con.prepareStatement("SELECT MAX(id) FROM `donhang`");
//                    ResultSet rs = stm.executeQuery();
//                    rs.next();
//                    return rs.getInt(1);
//                }
//                else {
//                    return 0;
//                }
//            }else {
//                return 0;
//            }
//        }catch (SQLException e){
//            return 0;
//        }
//    }
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getId_khachhang() {
        return id_khachhang;
    }

    public void setId_khachhang(int id_khachhang) {
        this.id_khachhang = id_khachhang;
    }
}
