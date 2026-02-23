package com.namkks.appbansach123.models;

import com.namkks.appbansach123.controller.DAO;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.sql.Timestamp;
import java.util.ArrayList;

public class KhachHang {
    private int id;
    private String ten;
    private String dienThoai;
    private String email;
    private String diaChi;
    private Timestamp createdAt;
    private int taiKhoanId;

    public KhachHang() {
    }

    public KhachHang(int id, String ten, String dienThoai, String email, String diaChi, Timestamp createdAt, int taiKhoanId) {
        this.id = id;
        this.ten = ten;
        this.dienThoai = dienThoai;
        this.email = email;
        this.diaChi = diaChi;
        this.createdAt = createdAt;
        this.taiKhoanId = taiKhoanId;
    }

    public KhachHang addKhachHang(){
        DAO a = new DAO();
        try {
            PreparedStatement stm = a.con.prepareStatement("INSERT INTO `khach_hangs`" +
                    "(ten, dien_thoai, email, dia_chi, tai_khoan_id) " +
                    "VALUES (?, ?, ?, ?, ?)",
                    Statement.RETURN_GENERATED_KEYS);
            stm.setString(1, this.ten);
            stm.setString(2, this.dienThoai);
            stm.setString(3, this.email);
            stm.setString(4, this.diaChi);
            stm.setInt(5, this.taiKhoanId);

            int affected =  stm.executeUpdate();
            if(affected > 0){
                ResultSet rs = stm.getGeneratedKeys();
                if(rs.next()){
                    KhachHang kh = getKH(rs.getInt(1));

                    a.close();
                    return kh;
                }
            }

            a.close();
            return null;
        }catch (SQLException e){
            a.close();
            return null;
        }
    }

    public static boolean KiemTraTT(String sdt, String email){
        DAO a = new DAO();
        try {
            PreparedStatement stm = a.con.prepareStatement(
                    "SELECT `id` FROM `khach_hangs` WHERE dien_thoai = ? OR email = ?"
            );
            stm.setString(1, sdt);
            stm.setString(2, email);
            ResultSet rs = stm.executeQuery();
            boolean result =  rs.next();

            a.close();
            return result;
        }catch (SQLException e){
            a.close();
            return false;
        }
    }
    public static KhachHang getKH(int id){
        DAO a = new DAO();
        try {

            PreparedStatement stm = a.con.prepareStatement("SELECT * FROM `khach_hangs` WHERE id = ?");
            stm.setInt(1, id);
            ResultSet rs = stm.executeQuery();
            if(rs.next()){
                KhachHang kh = new KhachHang();
                kh.setId(rs.getInt("id"));
                kh.setTen(rs.getString("ten"));
                kh.setDienThoai(rs.getString("dien_thoai"));
                kh.setEmail(rs.getString("email"));
                kh.setDiaChi(rs.getString("dia_chi"));
                kh.setCreatedAt(rs.getTimestamp("created_at"));
                kh.setTaiKhoanId(rs.getInt("tai_khoan_id"));

                a.close();
                return kh;
            }

            a.close();
            return null;
        }catch (SQLException e){
            a.close();
            return null;
        }
    }

    public static KhachHang getKhachHangByTaiKhoanId(int taiKhoanId){
        DAO a = new DAO();
        try {

            PreparedStatement stm = a.con.prepareStatement("SELECT * FROM `khach_hangs` WHERE tai_khoan_id = ?");
            stm.setInt(1, taiKhoanId);
            ResultSet rs = stm.executeQuery();
            if(rs.next()){
                KhachHang kh = new KhachHang();
                kh.setId(rs.getInt("id"));
                kh.setTen(rs.getString("ten"));
                kh.setDienThoai(rs.getString("dien_thoai"));
                kh.setEmail(rs.getString("email"));
                kh.setDiaChi(rs.getString("dia_chi"));
                kh.setCreatedAt(rs.getTimestamp("created_at"));
                kh.setTaiKhoanId(rs.getInt("tai_khoan_id"));

                a.close();
                return kh;
            }

            a.close();
            return null;
        }catch (SQLException e){
            a.close();
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
            kh.setTen(rs.getString(2));
            kh.setDienThoai(rs.getString(3));
            kh.setEmail(rs.getString(4));
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
            stm.setString(1, this.ten);
            stm.setString(2, this.dienThoai);
            stm.setString(3, this.email);
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
                kh.setTen(rs.getString(2));
                kh.setDienThoai(rs.getString(3));
                kh.setEmail(rs.getString(4));
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

    public String getTen() {
        return ten;
    }

    public void setTen(String ten) {
        this.ten = ten;
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

    public String getDiaChi() {
        return diaChi;
    }

    public void setDiaChi(String diaChi) {
        this.diaChi = diaChi;
    }

    public Timestamp getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(Timestamp createdAt) {
        this.createdAt = createdAt;
    }

    public int getTaiKhoanId() {
        return taiKhoanId;
    }

    public void setTaiKhoanId(int taiKhoanId) {
        this.taiKhoanId = taiKhoanId;
    }
}
