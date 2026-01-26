package com.namkks.appbansach123.models;

import com.namkks.appbansach123.controller.DAO;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

public class BinhLuan extends KhachHang{
    private int id;
    private float soSao;
    private String danhGia;
    private int id_sach;
    private int id_khachhang;

    public BinhLuan() {
    }

    public BinhLuan(int id, float soSao, String danhGia, int id_sach, int id_khachhang) {
        this.id = id;
        this.soSao = soSao;
        this.danhGia = danhGia;
        this.id_sach = id_sach;
        this.id_khachhang = id_khachhang;
    }

    public boolean addBinhLuan(){
        try {
            DAO a = new DAO();
            PreparedStatement stm = a.con.prepareStatement("INSERT INTO `binhluan`(`danh_gia`, `so_sao`, `id_sach`, `id_khachhang`) VALUES (?, ?, ?, ?)");
            stm.setString(1, this.danhGia);
            stm.setFloat(2, this.soSao);
            stm.setInt(3, this.id_sach);
            stm.setInt(4, this.id_khachhang);
            return stm.executeUpdate() > 0;
        }catch (SQLException e){
            return false;
        }
    }
    public static float getSaoTB(int id_sach){
        try{
            DAO a = new DAO();
            PreparedStatement stm = a.con.prepareStatement("SELECT (sum(so_sao) / COUNT(so_sao))as saotb FROM `binhluan` WHERE id_sach = ?");
            stm.setInt(1, id_sach);
            ResultSet rs = stm.executeQuery();
            rs.next();
            return rs.getFloat(1);
        }catch (SQLException e){
            return 0;
        }
    }
    public static ArrayList<BinhLuan> getBinhLuanOfSach(int id_sach){
        ArrayList<BinhLuan> listbl = new ArrayList<>();
        try {
            DAO a = new DAO();
            PreparedStatement stm = a.con.prepareStatement("SELECT bl.*, kh.ho_ten FROM `binhluan` bl INNER JOIN khachhang kh " +
                    "ON kh.id = bl.id_khachhang WHERE bl.id_sach = ? ORDER BY bl.id DESC");
            stm.setInt(1,id_sach);
            ResultSet rs = stm.executeQuery();
            while (rs.next()){
                BinhLuan bl = new BinhLuan();
                bl.setId(rs.getInt(1));
                bl.setDanhGia(rs.getString(2));
                bl.setSoSao(rs.getFloat(3));
                bl.setId_sach(rs.getInt(4));
                bl.setId_khachhang(rs.getInt(5));
                bl.setHoTen(rs.getString(6));
                listbl.add(bl);
            }
            return listbl;
        }catch (SQLException e){
            return listbl;
        }
    }
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public float getSoSao() {
        return soSao;
    }

    public void setSoSao(float soSao) {
        this.soSao = soSao;
    }

    public String getDanhGia() {
        return danhGia;
    }

    public void setDanhGia(String danhGia) {
        this.danhGia = danhGia;
    }

    public int getId_sach() {
        return id_sach;
    }

    public void setId_sach(int id_sach) {
        this.id_sach = id_sach;
    }

    public int getId_khachhang() {
        return id_khachhang;
    }

    public void setId_khachhang(int id_khachhang) {
        this.id_khachhang = id_khachhang;
    }
}
