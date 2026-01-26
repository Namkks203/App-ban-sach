package com.namkks.appbansach123.models;

import com.namkks.appbansach123.controller.DAO;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

public class GioHang extends Sach {
    private int soLuong;

    public GioHang() {
    }

    public GioHang(int soLuong) {
        this.soLuong = soLuong;
    }

    public static boolean ThemGioHang(int id_kh, int id_sach){
        try{
            DAO a = new DAO();
            PreparedStatement stm = a.con.prepareStatement("INSERT INTO `giohang`(`id_khachhang`, `id_sach`, `so_luong`) VALUES (?, ?, 1)");
            stm.setInt(1, id_kh);
            stm.setInt(2, id_sach);

            return stm.executeUpdate() > 0;
        }catch (SQLException e){
            return false;
        }
    }

    public static int GetTongTien(int id_kh){
        try{
            DAO a = new DAO();
            PreparedStatement stm = a.con.prepareStatement("SELECT SUM(gh.so_luong * s.gia_tien) as thanhtien FROM giohang gh, quanao s " +
                    "WHERE gh.id_sach = s.id AND id_khachhang = ?");
            stm.setInt(1, id_kh);
            ResultSet rs = stm.executeQuery();
            rs.next();
            return rs.getInt(1);
        }catch (SQLException e){
            return 0;
        }
    }

    public static ArrayList<GioHang> getGioHang(int id_kh){
        ArrayList<GioHang> list = new ArrayList<>();
        try{
            DAO a = new DAO();
            PreparedStatement stm = a.con.prepareStatement("SELECT s.id, s.ten_quan_ao, s.gia_tien, gh.so_luong, s.anh FROM giohang gh ,quanao s " +
                    "WHERE gh.id_sach = s.id and gh.id_khachhang = ?");
            stm.setInt(1, id_kh);
            ResultSet rs = stm.executeQuery();
            while (rs.next()){
                GioHang gh = new GioHang();
                gh.setId(rs.getInt(1));
                gh.setTenSach(rs.getString(2));
                gh.setGiaBan(rs.getInt(3));
                gh.setSoLuong(rs.getInt(4));
                gh.setAnh(rs.getString(5));
                list.add(gh);
            }
            return list;
        }catch (SQLException e){
            return list;
        }
    }
    public boolean UpdateSoLuong(int id_kh, int soL){
        try{
            DAO a = new DAO();
            PreparedStatement stm = a.con.prepareStatement("UPDATE `giohang` SET `so_luong`= ? WHERE id_khachhang = ? and id_sach = ?");
            stm.setInt(1, soL);
            stm.setInt(2, id_kh);
            stm.setInt(3, this.getId());

            return stm.executeUpdate() > 0;
        }catch (SQLException e){
            return false;
        }
    }

    public boolean DeleteItemGH(int id_kh){
        try{
            DAO a = new DAO();
            PreparedStatement stm = a.con.prepareStatement("DELETE FROM `giohang` WHERE id_khachhang = ? and id_sach = ?");
            stm.setInt(1, id_kh);
            stm.setInt(2, this.getId());

            return stm.executeUpdate() > 0;
        }catch (SQLException e){
            return false;
        }
    }

    public int getSoLuong() {
        return soLuong;
    }

    public void setSoLuong(int soLuong) {
        this.soLuong = soLuong;
    }
}
