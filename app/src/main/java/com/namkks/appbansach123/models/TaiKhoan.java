package com.namkks.appbansach123.models;

import com.namkks.appbansach123.controller.DAO;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Timestamp;

public class TaiKhoan {
    private int id;
    private String tenDangNhap;
    private String matKhau;
    private String loaiTaiKhoan;
    private String trangThai;
    private Timestamp createdAt;

    public TaiKhoan() {
    }

    public static TaiKhoan dangNhap(String tenDangNhap, String matKhau){
        try {
            DAO dao = new DAO();
            PreparedStatement stm = dao.con.prepareStatement("select * from tai_khoans where ten_dang_nhap = ? and mat_khau = ?");
            stm.setString(1, tenDangNhap);
            stm.setString(2, matKhau);
            ResultSet rs = stm.executeQuery();
            rs.next();

            TaiKhoan tk = new TaiKhoan();
            tk.setId(rs.getInt("id"));
            tk.setTenDangNhap(rs.getString("ten_dang_nhap"));
            tk.setMatKhau(rs.getString("mat_khau"));
            tk.setLoaiTaiKhoan(rs.getString("loai_tai_khoan"));
            tk.setTrangThai(rs.getString("trang_thai"));
            tk.setCreatedAt(rs.getTimestamp("created_at"));

            return tk;
        }catch (Exception e){
            return null;
        }
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getTenDangNhap() {
        return tenDangNhap;
    }

    public void setTenDangNhap(String tenDangNhap) {
        this.tenDangNhap = tenDangNhap;
    }

    public String getMatKhau() {
        return matKhau;
    }

    public void setMatKhau(String matKhau) {
        this.matKhau = matKhau;
    }

    public String getLoaiTaiKhoan() {
        return loaiTaiKhoan;
    }

    public void setLoaiTaiKhoan(String loaiTaiKhoan) {
        this.loaiTaiKhoan = loaiTaiKhoan;
    }

    public String getTrangThai() {
        return trangThai;
    }

    public void setTrangThai(String trangThai) {
        this.trangThai = trangThai;
    }

    public Timestamp getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(Timestamp createdAt) {
        this.createdAt = createdAt;
    }
}
