package com.namkks.appbansach123.models;

import com.namkks.appbansach123.controller.DAO;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class NhanVien {
    private int id;
    private String hoTen;
    private String sdt;
    private String pass;
    private int quyen;

    public NhanVien() {
    }

    public NhanVien(int id, String hoTen, String sdt, String pass, int quyen) {
        this.id = id;
        this.hoTen = hoTen;
        this.sdt = sdt;
        this.pass = pass;
        this.quyen = quyen;
    }
    public static boolean KiemtraTT(String sdt){
        try {
            DAO a = new DAO();
            PreparedStatement stm = a.conn.prepareStatement("SELECT `id` FROM `nhanvien` WHERE SDT = ?");
            stm.setString(1, sdt);
            ResultSet rs = stm.executeQuery();
            rs.next();
            return rs.getInt(1) > 0;
        }catch (SQLException e){
            return false;
        }
    }
    public int KiemTraDN(){
        try {
            DAO a = new DAO();
            PreparedStatement stm = a.conn.prepareStatement("SELECT `id` FROM `nhanvien` WHERE SDT = ? and Pass = ?");
            stm.setString(1, this.sdt);
            stm.setString(2, this.pass);
            ResultSet rs = stm.executeQuery();
            rs.next();
            return rs.getInt(1);
        }catch (SQLException e){
            return 0;
        }
    }
    public int getId() {
        return id;
    }
    public static NhanVien getNhanVien(int id){
        try {
            DAO a = new DAO();
            PreparedStatement stm = a.conn.prepareStatement("SELECT * FROM `nhanvien` WHERE id = ?");
            stm.setInt(1, id);
            ResultSet rs = stm.executeQuery();
            rs.next();
            NhanVien kh = new NhanVien();
            kh.setId(rs.getInt(1));
            kh.setHoTen(rs.getString(2));
            kh.setSdt(rs.getString(3));
            kh.setPass(rs.getString(4));
            kh.setQuyen(rs.getInt(5));

            return kh;
        }catch (SQLException e){
            return null;
        }
    }
    public void setId(int id) {
        this.id = id;
    }

    public String getHoTen() {
        return hoTen;
    }

    public void setHoTen(String hoTen) {
        this.hoTen = hoTen;
    }

    public String getSdt() {
        return sdt;
    }

    public void setSdt(String sdt) {
        this.sdt = sdt;
    }

    public String getPass() {
        return pass;
    }

    public void setPass(String pass) {
        this.pass = pass;
    }

    public int getQuyen() {
        return quyen;
    }

    public void setQuyen(int quyen) {
        this.quyen = quyen;
    }
}
