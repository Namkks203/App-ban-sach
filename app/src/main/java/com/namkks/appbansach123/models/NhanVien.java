package com.namkks.appbansach123.models;

import com.namkks.appbansach123.controller.DAO;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class NhanVien {
    private int id;
    private int chiNhanhId;
    private String hoTen;
    private String vaiTro;
    private String dienThoai;
    private String email;
    private int taiKhoanid;

    public NhanVien() {
    }

    public int getId() {
        return id;
    }
    public static NhanVien getNhanVien(int taiKhoanid){
        try {
            DAO a = new DAO();
            PreparedStatement stm = a.con.prepareStatement("SELECT * FROM `nhan_viens` WHERE tai_khoan_id = ?");
            stm.setInt(1, taiKhoanid);
            ResultSet rs = stm.executeQuery();
            rs.next();
            NhanVien nv = new NhanVien();
            nv.setId(rs.getInt("id"));
            nv.setChiNhanhId(rs.getInt("chi_nhanh_id"));
            nv.setHoTen(rs.getString("ten"));
            nv.setDienThoai(rs.getString("dien_thoai"));
            nv.setEmail(rs.getString("email"));
            nv.setVaiTro(rs.getString("vai_tro"));
            nv.setTaiKhoanid(rs.getInt("tai_khoan_id"));

            return nv;
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

    public int getChiNhanhId() {
        return chiNhanhId;
    }

    public void setChiNhanhId(int chiNhanhId) {
        this.chiNhanhId = chiNhanhId;
    }

    public String getVaiTro() {
        return vaiTro;
    }

    public void setVaiTro(String vaiTro) {
        this.vaiTro = vaiTro;
    }

    public String getDienThoai() {
        return dienThoai;
    }

    public void setDienThoai(String dienThoai) {
        this.dienThoai = dienThoai;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public int getTaiKhoanid() {
        return taiKhoanid;
    }

    public void setTaiKhoanid(int taiKhoanid) {
        this.taiKhoanid = taiKhoanid;
    }
}
