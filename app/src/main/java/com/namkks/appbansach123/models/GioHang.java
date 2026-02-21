package com.namkks.appbansach123.models;

import com.namkks.appbansach123.controller.DAO;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

public class GioHang{
    private int khachHangId;
    private int sachId;
    private int soLuong;

    public GioHang() {
    }

    public GioHang(int khachHangId, int sachId, int soLuong) {
        this.khachHangId = khachHangId;
        this.sachId = sachId;
        this.soLuong = soLuong;
    }

    public static GioHang ThemGioHang(int id_kh, int id_sach){
        DAO a = new DAO();
        try{

            PreparedStatement stm = a.con.prepareStatement("INSERT INTO `gio_hangs`(`khach_hang_id`, `sach_id`, `so_luong`) VALUES (?, ?, 1)");
            stm.setInt(1, id_kh);
            stm.setInt(2, id_sach);

            int affected = stm.executeUpdate();
            if(affected > 0){
                GioHang gh = getGioHang(id_kh, id_sach);

                a.close();
                return gh;
            }

            a.close();
            return null;
        }catch (SQLException e){
            a.close();
            return null;
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

    public static ArrayList<GioHang> getGioHangByKhachHangId(int id_kh){
        DAO a = new DAO();
        try{
            ArrayList<GioHang> list = new ArrayList<>();
            PreparedStatement stm = a.con.prepareStatement("SELECT * FROM gio_hangs where khach_hang_id = ?");
            stm.setInt(1, id_kh);
            ResultSet rs = stm.executeQuery();
            while (rs.next()){
                GioHang gh = new GioHang();
                gh.setKhachHangId(rs.getInt("khach_hang_id"));
                gh.setSachId(rs.getInt("sach_id"));
                gh.setSoLuong(rs.getInt("so_luong"));

                list.add(gh);
            }

            a.close();
            return list;
        }catch (SQLException e){
            a.close();
            return null;
        }
    }
    public static GioHang getGioHang(int khachHangId, int sachId){
        DAO a = new DAO();
        try{
            PreparedStatement stm = a.con.prepareStatement("SELECT * FROM gio_hangs " +
                    "where khach_hang_id = ? AND sach_id = ?");
            stm.setInt(1, khachHangId);
            stm.setInt(2, sachId);
            ResultSet rs = stm.executeQuery();
            if (rs.next()){
                GioHang gh = new GioHang();
                gh.setKhachHangId(rs.getInt("khach_hang_id"));
                gh.setSachId(rs.getInt("sach_id"));
                gh.setSoLuong(rs.getInt("so_luong"));

                a.close();
                return gh;
            }
            a.close();
            return null;
        }catch (SQLException e){
            a.close();
            return null;
        }
    }
    public static GioHang UpdateSoLuong(int id_kh,int sach_id, int soL){
        DAO a = new DAO();
        try{
            PreparedStatement stm = a.con.prepareStatement("UPDATE `gio_hangs` SET `so_luong`= ? " +
                    "WHERE khach_hang_id = ? and sach_id = ?");
            stm.setInt(1, soL);
            stm.setInt(2, id_kh);
            stm.setInt(3, sach_id);

            int affected =  stm.executeUpdate();
            if(affected > 0){
                GioHang gioHang = getGioHang(id_kh, sach_id);

                a.close();
                return gioHang;
            }

            a.close();
            return null;
        }catch (SQLException e){
            a.close();
            return null;
        }
    }

    public static boolean DeleteItemGH(int id_kh, int sachId){
        DAO a = new DAO();
        try{
            PreparedStatement stm = a.con.prepareStatement("DELETE FROM `gio_hangs` WHERE khach_hang_id = ? and sach_id = ?");
            stm.setInt(1, id_kh);
            stm.setInt(2, sachId);

            boolean result = stm.executeUpdate() > 0;

            a.close();
            return result;
        }catch (SQLException e){
            a.close();
            return false;
        }
    }

    public static boolean DeleteGH(int id_kh){
        DAO a = new DAO();
        try{
            PreparedStatement stm = a.con.prepareStatement(
                    "DELETE FROM `gio_hangs` WHERE khach_hang_id = ?");
            stm.setInt(1, id_kh);

            boolean result = stm.executeUpdate() > 0;

            a.close();
            return result;
        }catch (SQLException e){
            a.close();
            return false;
        }
    }

    public int getKhachHangId() {
        return khachHangId;
    }

    public void setKhachHangId(int khachHangId) {
        this.khachHangId = khachHangId;
    }

    public int getSachId() {
        return sachId;
    }

    public void setSachId(int sachId) {
        this.sachId = sachId;
    }

    public int getSoLuong() {
        return soLuong;
    }

    public void setSoLuong(int soLuong) {
        this.soLuong = soLuong;
    }
}
