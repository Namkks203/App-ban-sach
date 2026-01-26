package com.namkks.appbansach123.models;

import com.namkks.appbansach123.controller.DAO;


import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

public class ChiTietHoaDon extends HoaDon{
    private int id_sach;
    private int soL;

    public ChiTietHoaDon() {
    }

    public ChiTietHoaDon(int id, String ngayLapHoaDon, int id_khachhang, int id_nhanven, int id_sach, int soL) {
        super(id, ngayLapHoaDon, id_khachhang, id_nhanven);
        this.id_sach = id_sach;
        this.soL = soL;
    }

    public boolean addChiTietHoaDon(int id_dh){
        try {
            ArrayList<ChiTietDonHang> ctdh = ChiTietDonHang.getChiTietDonHang(id_dh);
            int id_hd = addHoaDon();
            DAO a = new DAO();
            if(id_hd > 0){
                for (ChiTietDonHang i : ctdh){
                    PreparedStatement stm = a.con.prepareStatement("INSERT INTO `chitiethoadon`(`id_hoadon`, `id_sach`, `so_luong`) VALUES (?, ?, ?)");
                    stm.setInt(1, id_hd);
                    stm.setInt(2, i.getId_sach());
                    stm.setInt(3, i.getSoLuong());
                    stm.executeUpdate();
                }
                ChiTietDonHang.XoaDonHang(id_dh);
                return true;
            }
            return false;
        }catch (SQLException e){
            return false;
        }
    }
    public static ArrayList<ChiTietHoaDon> getChiTietHoaDon(int id_dh){
        ArrayList<ChiTietHoaDon> ctdh = new ArrayList<>();
        try{
            DAO a = new DAO();
            PreparedStatement stm = a.con.prepareStatement("SELECT * FROM `chitiethoadon` WHERE id_hoadon = ?");
            stm.setInt(1, id_dh);
            ResultSet rs2 = stm.executeQuery();
            while (rs2.next()){
                ChiTietHoaDon ct = new ChiTietHoaDon();
                ct.setId(rs2.getInt(1));
                ct.setId_sach(rs2.getInt(2));
                ct.setSoL(rs2.getInt(3));
                ctdh.add(ct);
            }
        }catch (SQLException e){
            return null;
        }
        return ctdh;
    }
    public static  String getNgayLap(int idhd){
        try {
            DAO a = new DAO();
            PreparedStatement stm = a.con.prepareStatement("SELECT `ngay_lap_hoa_don` FROM `hoadon` WHERE id = ?");
            stm.setInt(1, idhd);
            ResultSet rs = stm.executeQuery();
            rs.next();
            return rs.getString(1);
        }catch (SQLException e){
            return "";
        }
    }
    public static ArrayList<ChiTietHoaDon> getListHoaDon(int id_kh){
        ArrayList<ChiTietHoaDon> ctdh = new ArrayList<>();
        try{
            DAO a = new DAO();
            PreparedStatement stm = a.con.prepareStatement("SELECT id FROM `hoadon` WHERE id_khachhang = ? ORDER BY id DESC");
            stm.setInt(1, id_kh);
            ResultSet rs = stm.executeQuery();
            while (rs.next()){
                stm = a.con.prepareStatement("SELECT * FROM `chitiethoadon` WHERE id_hoadon = ?");
                stm.setInt(1, rs.getInt(1));
                ResultSet rs2 = stm.executeQuery();
                rs2.next();
                ChiTietHoaDon ct = new ChiTietHoaDon();
                ct.setId(rs2.getInt(1));
                ct.setId_sach(rs2.getInt(2));
                ct.setSoL(rs2.getInt(3));
                ctdh.add(ct);
            }
            return ctdh;
        }catch (SQLException e){
            return null;
        }
    }
    public static int getSoLuong(int id_dh){
        try {
            DAO a = new DAO();
            PreparedStatement stm = a.con.prepareStatement("SELECT COUNT(id_hoadon) FROM `chitiethoadon` WHERE id_hoadon = ?");
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
            PreparedStatement stm = a.con.prepareStatement("SELECT SUM(ct.so_luong * s.gia_tien) FROM `chitiethoadon` ct " +
                    "INNER JOIN quanao s on ct.id_sach = s.id WHERE ct.id_hoadon = ?");
            stm.setInt(1, id_dh);
            ResultSet rs = stm.executeQuery();
            rs.next();

            return rs.getInt(1);
        }catch (SQLException e){
            return 0;
        }
    }
    public static ArrayList<String> getLoaiInHD(){
        ArrayList<String> listSt = new ArrayList<>();
        try{
            DAO a = new DAO();
            PreparedStatement stm = a.con.prepareStatement("SELECT DISTINCT s.loai FROM `chitiethoadon` cthd, quanao s WHERE cthd.id_sach = s.id");
            ResultSet rs = stm.executeQuery();
            while (rs.next()){
                listSt.add(rs.getString(1));
            }
        }catch (SQLException e){
        }
        return listSt;
    }
    public static int getSLLoai(String loai){
        try{
            DAO a = new DAO();
            PreparedStatement stm = a.con.prepareStatement("SELECT Sum(so_luong) FROM `chitiethoadon` cthd, quanao s where s.id = cthd.id_sach AND s.Loai = ?");
            stm.setString(1, loai);
            ResultSet rs = stm.executeQuery();
            rs.next();
            return rs.getInt(1);
        }catch (SQLException e){
            return 0;
        }
    }
    public static int getTongTien(){
        try{
            DAO a = new DAO();
            PreparedStatement stm = a.con.prepareStatement("SELECT SUM(cthd.so_luong * s.gia_tien) FROM `chitiethoadon` cthd, quanao s WHERE cthd.id_sach = s.id");
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

    public void setId_sach(int id_sach) {
        this.id_sach = id_sach;
    }

    public int getSoL() {
        return soL;
    }

    public void setSoL(int soL) {
        this.soL = soL;
    }
}
