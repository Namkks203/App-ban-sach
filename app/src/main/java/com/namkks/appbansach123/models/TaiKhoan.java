package com.namkks.appbansach123.models;

import com.namkks.appbansach123.controller.DAO;

import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;
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

    public TaiKhoan addTaiKhoan(){
        DAO dao = new DAO();
        try{
            PreparedStatement statement = dao.con.prepareStatement(
                    "INSERT INTO tai_khoans (ten_dang_nhap, mat_khau, loai_tai_khoan) VALUES " +
                            "(?, ?, ?)",
                    Statement.RETURN_GENERATED_KEYS
            );
            statement.setString(1, this.tenDangNhap);
            statement.setString(2, this.matKhau);
            statement.setString(3, this.loaiTaiKhoan);

            int affected = statement.executeUpdate();
            if(affected > 0){
                ResultSet resultSet = statement.getGeneratedKeys();
                if(resultSet.next()){
                    TaiKhoan taiKhoan = getTaiKhoanById(resultSet.getInt(1));

                    dao.close();
                    return taiKhoan;
                }
            }

            dao.close();
            return null;
        } catch (Exception e) {
            dao.close();
            return null;
        }
    }

    public static TaiKhoan dangNhap(String tenDangNhap, String matKhau){
        DAO dao = new DAO();
        try {
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

            dao.close();
            return tk;
        }catch (Exception e){
            dao.close();
            return null;
        }
    }

    public static TaiKhoan getTaiKhoanById(int id){
        DAO dao = new DAO();
        try{
            PreparedStatement statement = dao.con.prepareStatement(
                    "SELECT * FROM tai_khoans WHERE id = ?"
            );
            statement.setInt(1, id);

            ResultSet resultSet = statement.executeQuery();
            if(resultSet.next()){
                TaiKhoan taiKhoan = new TaiKhoan();

                taiKhoan.setId(resultSet.getInt("id"));
                taiKhoan.setTenDangNhap(resultSet.getString("ten_dang_nhap"));
                taiKhoan.setMatKhau(resultSet.getString("mat_khau"));
                taiKhoan.setLoaiTaiKhoan(resultSet.getString("loai_tai_khoan"));
                taiKhoan.setTrangThai(resultSet.getString("trang_thai"));
                taiKhoan.setCreatedAt(resultSet.getTimestamp("created_at"));

                dao.close();
                return taiKhoan;
            }

            dao.close();
            return null;
        } catch (Exception e) {
            dao.close();
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
