package com.namkks.appbansach123.models;

import com.namkks.appbansach123.controller.DAO;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

public class KhachHang {
    private int id;
    private String hoTen;
    private String sdt;
    private String pass;
    private String diaChi;

    public KhachHang() {
    }

    public KhachHang(int id, String hoTen, String sdt, String pass, String diaChi) {
        this.id = id;
        this.hoTen = hoTen;
        this.sdt = sdt;
        this.pass = pass;
        this.diaChi = diaChi;
    }

    public boolean AddKhachHang(){
        try {
            if (KiemTraTT(this.sdt))
                return false;
            DAO a = new DAO();
            PreparedStatement stm = a.con.prepareStatement("INSERT INTO `khachhang`(`ho_ten`, `sdt`, `pass`, `dia_chi`) " +
                    "VALUES (?, ?, ?, ?)");
            stm.setString(1, this.hoTen);
            stm.setString(2, this.sdt);
            stm.setString(3, this.pass);
            stm.setString(4, this.diaChi);

            return stm.executeUpdate() > 0;
        }catch (SQLException e){
            return false;
        }
    }

    public int KiemTraDN(){
        try {
            DAO a = new DAO();
            PreparedStatement stm = a.con.prepareStatement("SELECT `id` FROM `khachhang` WHERE sdt = ? and pass = ?");
            stm.setString(1, this.sdt);
            stm.setString(2, this.pass);
            ResultSet rs = stm.executeQuery();
            rs.next();
            return rs.getInt(1);
        }catch (SQLException e){
            return 0;
        }
    }
    public boolean KiemTraTT(String sdt){
        try {
            DAO a = new DAO();
            PreparedStatement stm = a.con.prepareStatement("SELECT `id` FROM `khachhang` WHERE sdt = ?");
            stm.setString(1, this.sdt);
            ResultSet rs = stm.executeQuery();
            rs.next();
            return rs.getInt(1) > 0;
        }catch (SQLException e){
            return false;
        }
    }
    public int getSoLDonHang(){
        try {
            DAO a = new DAO();
            PreparedStatement stm = a.con.prepareStatement("SELECT COUNT(id) FROM `donhang` WHERE id_khachhang = ?");
            stm.setInt(1, this.id);
            ResultSet rs = stm.executeQuery();
            rs.next();
            return rs.getInt(1);
        }catch (SQLException e){
            return 0;
        }
    }
    public int getSoLHoaDon(){
        try {
            DAO a = new DAO();
            PreparedStatement stm = a.con.prepareStatement("SELECT COUNT(id) FROM `hoadon` WHERE id_khachhang = ?");
            stm.setInt(1, this.id);
            ResultSet rs = stm.executeQuery();
            rs.next();
            return rs.getInt(1);
        }catch (SQLException e){
            return 0;
        }
    }
    public static KhachHang getKH(int id){
        try {
            DAO a = new DAO();
            PreparedStatement stm = a.con.prepareStatement("SELECT * FROM `khachhang` WHERE id = ?");
            stm.setInt(1, id);
            ResultSet rs = stm.executeQuery();
            rs.next();
            KhachHang kh = new KhachHang();
            kh.setId(rs.getInt(1));
            kh.setHoTen(rs.getString(2));
            kh.setSdt(rs.getString(3));
            kh.setPass(rs.getString(4));
            kh.setDiaChi(rs.getString(5));

            return kh;
        }catch (SQLException e){
            return null;
        }
    }
    public static ArrayList<KhachHang> TimKiemKH(String sdt){
        ArrayList<KhachHang> listKH = new ArrayList<>();
        try{
            DAO a = new DAO();
            PreparedStatement stm = a.con.prepareStatement("SELECT * FROM `khachhang` WHERE sdt = ?");
            stm.setString(1, sdt);
            ResultSet rs = stm.executeQuery();
            rs.next();
            KhachHang kh = new KhachHang();
            kh.setId(rs.getInt(1));
            kh.setHoTen(rs.getString(2));
            kh.setSdt(rs.getString(3));
            kh.setPass(rs.getString(4));
            kh.setDiaChi(rs.getString(5));

            listKH.add(kh);
        }catch (SQLException e){
            return null;
        }
        return listKH;
    }
    public static boolean XoaKH(int id){
        try{
            DAO a = new DAO();
            PreparedStatement stm = a.con.prepareStatement("DELETE FROM `khachhang` WHERE id = ?");
            stm.setInt(1, id);
            return stm.executeUpdate() > 0 ;
        }catch (SQLException e){
            return false;
        }
    }
    public boolean SuaKH(){
        try{
            DAO a = new DAO();
            PreparedStatement stm = a.con.prepareStatement("UPDATE `khachhang` SET `ho_ten`= ?,`sdt`=?,`pass`=?,`dia_chi`=? WHERE id = ?");
            stm.setString(1, this.hoTen);
            stm.setString(2, this.sdt);
            stm.setString(3, this.pass);
            stm.setString(4, this.diaChi);
            stm.setInt(5, this.id);

            return stm.executeUpdate() > 0;
        }catch (SQLException e){
            return false;
        }
    }
    public static ArrayList<KhachHang> getAllKH(){
        ArrayList<KhachHang> listKH = new ArrayList<>();
        try{
            DAO a = new DAO();
            PreparedStatement stm = a.con.prepareStatement("SELECT * FROM `khachhang` order by id DESC");
            ResultSet rs = stm.executeQuery();
            while (rs.next()){
                KhachHang kh = new KhachHang();
                kh.setId(rs.getInt(1));
                kh.setHoTen(rs.getString(2));
                kh.setSdt(rs.getString(3));
                kh.setPass(rs.getString(4));
                kh.setDiaChi(rs.getString(5));

                listKH.add(kh);
            }
        }catch (SQLException e){
            return null;
        }
        return listKH;
    }

    public int getId() {
        return id;
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

    public String getDiaChi() {
        return diaChi;
    }

    public void setDiaChi(String diaChi) {
        this.diaChi = diaChi;
    }
}
