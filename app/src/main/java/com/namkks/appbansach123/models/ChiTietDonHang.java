package com.namkks.appbansach123.models;

import com.namkks.appbansach123.controller.DAO;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

public class ChiTietDonHang extends DonHang{
    private int id_sach;
    private int soLuong;

    public ChiTietDonHang() {
    }

    public ChiTietDonHang(int id, int id_khachhang, int is_sach, int soLuong) {
        super(id, id_khachhang);
        this.id_sach = is_sach;
        this.soLuong = soLuong;
    }

//    public boolean addCTDH(){
//        try {
//            int id_dh = addDonHang();
//            if(id_dh > 0){
//                for (GioHang item: GioHang.getGioHang(this.getId_khachhang())) {
//                    DAO a = new DAO();
//                    PreparedStatement stm = a.con.prepareStatement("INSERT INTO `chitietdonhang`(`id_donhang`, `id_sach`, `soluong`) VALUES (?, ?, ?)");
//                    stm.setInt(1, id_dh);
//                    stm.setInt(2, item.getId());
//                    stm.setInt(3, item.getSoLuong());
//
//                    if(stm.executeUpdate() > 0){
//                        stm = a.con.prepareStatement("DELETE FROM giohang where id_khachhang = ? and id_sach = ?");
//                        stm.setInt(1, this.getId_khachhang());
//                        stm.setInt(2, item.getId());
//                        stm.executeUpdate();
//                    }
//                }
//                return true;
//            }
//            return false;
//        }catch (SQLException e){
//            return false;
//        }
//    }
//    public boolean muaNgay(int id_sach) {
//        try {
//            int id_dh = addDonHang();
//            if (id_dh > 0) {
//                DAO a = new DAO();
//                PreparedStatement stm = a.con.prepareStatement("INSERT INTO `chitietdonhang`(`id_donhang`, `id_sach`, `soluong`) VALUES (?, ?, ?)");
//                stm.setInt(1, id_dh);
//                stm.setInt(2, id_sach);
//                stm.setInt(3, 1);
//                return stm.executeUpdate() > 0;
//            }
//            return false;
//        } catch (SQLException e) {
//            return false;
//        }
//    }
    public static boolean XoaDonHang(int iddh){
        try{
            DAO a = new DAO();
            PreparedStatement stm = a.con.prepareStatement("DELETE FROM `chitietdonhang` WHERE id_donhang = ?");
            stm.setInt(1, iddh);
            if(stm.executeUpdate() > 0){
                stm = a.con.prepareStatement("DELETE FROM `donhang` WHERE id = ?");
                stm.setInt(1, iddh);
                return stm.executeUpdate() > 0;
            }
            return false;
        }catch (SQLException e){
            return false;
        }
    }
    public static  ArrayList<ChiTietDonHang> timKiemDonHang(String sdt){
        ArrayList<ChiTietDonHang> ctdh = new ArrayList<>();
        try{
            DAO a = new DAO();
            PreparedStatement stm = a.con.prepareStatement("SELECT dh.id, kh.id FROM `donhang` dh INNER JOIN khachhang kh on " +
                    "kh.id = dh.id_khachhang WHERE kh.sdt = ? ORDER BY dh.id DESC");
            stm.setString(1, sdt);
            ResultSet rs = stm.executeQuery();
            while (rs.next()){
                stm = a.con.prepareStatement("SELECT * FROM `chitietdonhang` WHERE id_donhang = ?");
                stm.setInt(1, rs.getInt(1));
                ResultSet rs2 = stm.executeQuery();
                rs2.next();
                ChiTietDonHang ct = new ChiTietDonHang();
                ct.setId(rs2.getInt(1));
                ct.setId_khachhang(rs.getInt(2));
                ct.setId_sach(rs2.getInt(2));
                ct.setSoLuong(rs2.getInt(3));
                ctdh.add(ct);
            }
            return ctdh;
        }catch (SQLException e){
            return null;
        }
    }
    public static ArrayList<ChiTietDonHang> getListDonHangNV(){
        ArrayList<ChiTietDonHang> ctdh = new ArrayList<>();
        try{
            DAO a = new DAO();
            PreparedStatement stm = a.con.prepareStatement("SELECT donhang.id, khachhang.id FROM `donhang`, khachhang where " +
                    "khachhang.id = donhang.id_khachhang ORDER BY donhang.id DESC");
            ResultSet rs = stm.executeQuery();
            while (rs.next()){
                stm = a.con.prepareStatement("SELECT * FROM `chitietdonhang` WHERE id_donhang = ?");
                stm.setInt(1, rs.getInt(1));
                ResultSet rs2 = stm.executeQuery();
                rs2.next();
                ChiTietDonHang ct = new ChiTietDonHang();
                ct.setId(rs2.getInt(1));
                ct.setId_khachhang(rs.getInt(2));
                ct.setId_sach(rs2.getInt(2));
                ct.setSoLuong(rs2.getInt(3));
                ctdh.add(ct);
            }
            return ctdh;
        }catch (SQLException e){
            return null;
        }
    }
    public static ArrayList<ChiTietDonHang> getListDonHang(int id_kh){
        ArrayList<ChiTietDonHang> ctdh = new ArrayList<>();
        try{
            DAO a = new DAO();
            PreparedStatement stm = a.con.prepareStatement("SELECT id FROM `donhang` WHERE id_khachhang = ? ORDER BY id DESC");
            stm.setInt(1, id_kh);
            ResultSet rs = stm.executeQuery();
            while (rs.next()){
                stm = a.con.prepareStatement("SELECT * FROM `chitietdonhang` WHERE id_donhang = ?");
                stm.setInt(1, rs.getInt(1));
                ResultSet rs2 = stm.executeQuery();
                rs2.next();
                ChiTietDonHang ct = new ChiTietDonHang();
                ct.setId(rs2.getInt(1));
                ct.setId_sach(rs2.getInt(2));
                ct.setSoLuong(rs2.getInt(3));
                ctdh.add(ct);
            }
            return ctdh;
        }catch (SQLException e){
            return null;
        }
    }
    public static ArrayList<ChiTietDonHang> getChiTietDonHang(int id_dh){
        ArrayList<ChiTietDonHang> ctdh = new ArrayList<>();
        try{
            DAO a = new DAO();
            PreparedStatement stm = a.con.prepareStatement("SELECT * FROM `chitietdonhang` WHERE id_donhang = ?");
            stm.setInt(1, id_dh);
            ResultSet rs2 = stm.executeQuery();
            while (rs2.next()){
                ChiTietDonHang ct = new ChiTietDonHang();
                ct.setId(rs2.getInt(1));
                ct.setId_sach(rs2.getInt(2));
                ct.setSoLuong(rs2.getInt(3));
                ctdh.add(ct);
            }
        }catch (SQLException e){
            return null;
        }
        return ctdh;
    }
    public static int getSoL(int id_dh){
        try {
            DAO a = new DAO();
            PreparedStatement stm = a.con.prepareStatement("SELECT COUNT(id_donhang) FROM `chitietdonhang` WHERE id_donhang = ?");
            stm.setInt(1, id_dh);
            ResultSet rs = stm.executeQuery();
            rs.next();
            return rs.getInt(1);
        }catch (SQLException e){
            return 0;
        }
    }

    public static int getTongTien(int id_dh){
        try{
            DAO a = new DAO();
            PreparedStatement stm = a.con.prepareStatement("SELECT SUM(ct.soluong * s.gia_tien) FROM `chitietdonhang` ct " +
                    "INNER JOIN quanao s on ct.id_sach = s.id WHERE ct.id_donhang = ?");
            stm.setInt(1, id_dh);
            ResultSet rs = stm.executeQuery();
            rs.next();

            return rs.getInt(1);
        }catch (SQLException e){
            return 0;
        }
    }

    public int getId_sach() {
        return id_sach;
    }

    public void setId_sach(int is_sach) {
        this.id_sach = is_sach;
    }

    public int getSoLuong() {
        return soLuong;
    }

    public void setSoLuong(int soLuong) {
        this.soLuong = soLuong;
    }
}
