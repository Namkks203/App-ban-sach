package com.namkks.appbansach123.models;

import com.namkks.appbansach123.controller.DAO;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class HoaDon {
    private int id;
    private String ngayLapHoaDon;
    private int id_khachhang;
    private int id_nhanven;

    public HoaDon() {
    }

    public HoaDon(int id, String ngayLapHoaDon, int id_khachhang, int id_nhanven) {
        this.id = id;
        this.ngayLapHoaDon = ngayLapHoaDon;
        this.id_khachhang = id_khachhang;
        this.id_nhanven = id_nhanven;
    }

    public int addHoaDon(){
        try{
            DAO a = new DAO();
            PreparedStatement stm = a.con.prepareStatement("INSERT INTO `hoadon`(`ngay_lap_hoa_don`, `id_khachhang`, `id_nhanvien`) VALUES (?, ?, ?)");
            stm.setString(1, ngayLapHoaDon);
            stm.setInt(2, id_khachhang);
            stm.setInt(3, id_nhanven);
            stm.executeUpdate();
            stm = a.con.prepareStatement("SELECT MAX(id) FROM `hoadon`");
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

    public void setId(int id) {
        this.id = id;
    }

    public String getNgayLapHoaDon() {
        return ngayLapHoaDon;
    }

    public void setNgayLapHoaDon(String ngayLapHoaDon) {
        this.ngayLapHoaDon = ngayLapHoaDon;
    }

    public int getId_khachhang() {
        return id_khachhang;
    }

    public void setId_khachhang(int id_khachhang) {
        this.id_khachhang = id_khachhang;
    }

    public int getId_nhanven() {
        return id_nhanven;
    }

    public void setId_nhanven(int id_nhanven) {
        this.id_nhanven = id_nhanven;
    }
}
