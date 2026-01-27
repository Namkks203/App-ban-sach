package com.namkks.appbansach123.models;

import com.namkks.appbansach123.controller.DAO;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.sql.Timestamp;
import java.util.ArrayList;

public class Sach {
    private int id;
    private String tenSach;
    private String nhaXuatBan;
    private int namXuatBan;
    private int giaThue;
    private String moTa;
    private int giaBan;
    private String anh;

    private Timestamp createdAt;

    public Sach() {
    }

    public Sach themSach() {
        DAO a = new DAO();
        try {
            PreparedStatement stm = a.con.prepareStatement(
                    "INSERT INTO sachs(ten_sach, nha_xuat_ban, nam_xuat_ban, gia_ban, gia_thue, anh, mo_ta) " +
                            "VALUES (?, ?, ?, ?, ?, ?, ?)",
                    Statement.RETURN_GENERATED_KEYS   // 👈 cực kỳ quan trọng
            );

            stm.setString(1, this.tenSach);
            stm.setString(2, this.nhaXuatBan);
            stm.setInt(3, this.namXuatBan);
            stm.setFloat(4, this.giaBan);
            stm.setFloat(5, this.giaThue);
            stm.setString(6, this.anh);
            stm.setString(7, this.moTa);

            int affected = stm.executeUpdate();

            if (affected > 0) {
                ResultSet rs = stm.getGeneratedKeys();
                if (rs.next()) {
                    Sach sach = getSachById(rs.getInt(1));

                    a.close();
                    return sach;
                }
            }
            a.close();
            return null;

        } catch (SQLException e) {
            e.printStackTrace();
            a.close();
            return null;
        }
    }

    public static ArrayList<Sach> ListQuanAoOfLoai(String loai){
        ArrayList<Sach> listSach = new ArrayList<>();
        try{
            DAO a = new DAO();
            PreparedStatement stm = a.con.prepareStatement("SELECT * FROM `quanao` WHERE loai = ?");
            stm.setString(1, loai);
            ResultSet rs = stm.executeQuery();
            while (rs.next()){
                Sach sach = new Sach();
                sach.setId(rs.getInt(1));
                sach.setTenSach(rs.getString(2));
                sach.setNhaXuatBan(rs.getString(3));
                sach.setNamXuatBan(rs.getInt(4));
                sach.setMoTa(rs.getString(5));
                sach.setGiaBan(rs.getInt(6));
                sach.setAnh(rs.getString(7));
                listSach.add(sach);
            }
            return listSach;
        }catch (SQLException e){
            return listSach;
        }
    }
    public static ArrayList<Sach> TiemKiemQuanAo(String tensach){
        ArrayList<Sach> listSach = new ArrayList<>();
        try{
            DAO a = new DAO();
            PreparedStatement stm = a.con.prepareStatement("SELECT * FROM `quanao` WHERE ten_quan_ao LIKE '%"+tensach
                    +"' OR ten_quan_ao LIKE '"+tensach+"%' OR ten_quan_ao LIKE '%"+tensach+"%'");
            ResultSet rs = stm.executeQuery();
            while (rs.next()){
                Sach sach = new Sach();
                sach.setId(rs.getInt(1));
                sach.setTenSach(rs.getString(2));
                sach.setNhaXuatBan(rs.getString(3));
                sach.setNamXuatBan(rs.getInt(4));
                sach.setMoTa(rs.getString(5));
                sach.setGiaBan(rs.getInt(6));
                sach.setAnh(rs.getString(7));
                listSach.add(sach);
            }
            return listSach;
        }catch (SQLException e){
            return null;
        }
    }
    public static Sach getSachById(int id){
        DAO a = new DAO();
        try {

            PreparedStatement stm = a.con.prepareStatement("SELECT * FROM `sachs` WHERE id = ?");
            stm.setInt(1, id);
            ResultSet rs = stm.executeQuery();
            rs.next();
            Sach sach = new Sach();
            sach.setId(rs.getInt("id"));
            sach.setTenSach(rs.getString("ten_sach"));
            sach.setNhaXuatBan(rs.getString("nha_xuat_ban"));
            sach.setNamXuatBan(rs.getInt("nam_xuat_ban"));
            sach.setMoTa(rs.getString("mo_ta"));
            sach.setGiaThue(rs.getInt("gia_thue"));
            sach.setGiaBan(rs.getInt("gia_ban"));
            sach.setAnh(rs.getString("anh"));
            sach.setCreatedAt(rs.getTimestamp("created_at"));

            a.close();
            return sach;
        }catch (SQLException e){
            a.close();
            return null;
        }
    }

    public static boolean xoaSach(int id_sach){
        DAO a = new DAO();
        try {
            PreparedStatement stm = a.con.prepareStatement("DELETE FROM `sachs` WHERE id = ?");
            stm.setInt(1, id_sach);

            boolean result = stm.executeUpdate() > 0;
            a.close();

            return result;
        }catch (SQLException e){
            return false;
        }
    }
    public boolean suaSach(){
        DAO a = new DAO();
        try {
            PreparedStatement stm = a.con.prepareStatement("Update sachs set ten_sach = ?, nha_xuat_ban = ?," +
                    "nam_xuat_ban = ?, gia_ban = ?, gia_thue = ?, anh = ? , mo_ta = ? where id = ?");
            stm.setString(1, this.tenSach);
            stm.setString(2, this.nhaXuatBan);
            stm.setInt(3, this.namXuatBan);
            stm.setInt(4, this.giaBan);
            stm.setFloat(5, this.giaThue);
            stm.setString(6, this.anh);
            stm.setString(7, this.moTa);
            stm.setInt(8, this.id);

            boolean result =  stm.executeUpdate() > 0;
            a.close();
            return  result;
        }catch (SQLException e){
            a.close();
            return false;
        }
    }

    public ArrayList<String> getLoaiSach(){
        ArrayList<String> loaiSach = new ArrayList<>();
        try {
            DAO a = new DAO();
            PreparedStatement stm = a.con.prepareStatement("SELECT DISTINCT loai FROM `quanao`");
            ResultSet rs = stm.executeQuery();
            while (rs.next()){
                loaiSach.add(rs.getString(1));
            }
            return loaiSach;
        }catch (SQLException e){
            return null;
        }
    }
    public ArrayList<Sach> getSachHot(){
        ArrayList<Sach> listSach = new ArrayList<>();
        try{
            DAO a = new DAO();
            PreparedStatement stm = a.con.prepareStatement("SELECT * FROM `sachs` ORDER BY id DESC LIMIT 8");
            ResultSet rs = stm.executeQuery();
            while (rs.next()){
                Sach sach = new Sach();
                sach.setId(rs.getInt("id"));
                sach.setTenSach(rs.getString("ten_sach"));
                sach.setNhaXuatBan(rs.getString("nha_xuat_ban"));
                sach.setNamXuatBan(rs.getInt("nam_xuat_ban"));
                sach.setMoTa(rs.getString("mo_ta"));
                sach.setGiaThue(rs.getInt("gia_thue"));
                sach.setGiaBan(rs.getInt("gia_ban"));
                sach.setAnh(rs.getString("anh"));
                sach.setCreatedAt(rs.getTimestamp("created_at"));
                listSach.add(sach);
            }
            return listSach;
        }catch (SQLException e){
            return listSach;
        }
    }
    public static ArrayList<Sach> getAllSach(){
        ArrayList<Sach> listSach = new ArrayList<>();
        try{
            DAO a = new DAO();
            PreparedStatement stm = a.con.prepareStatement("SELECT * FROM `sachs` ORDER BY id DESC");
            ResultSet rs = stm.executeQuery();
            while (rs.next()){
                Sach sach = new Sach();
                sach.setId(rs.getInt("id"));
                sach.setTenSach(rs.getString("ten_sach"));
                sach.setNhaXuatBan(rs.getString("nha_xuat_ban"));
                sach.setNamXuatBan(rs.getInt("nam_xuat_ban"));
                sach.setMoTa(rs.getString("mo_ta"));
                sach.setGiaThue(rs.getInt("gia_thue"));
                sach.setGiaBan(rs.getInt("gia_ban"));
                sach.setAnh(rs.getString("anh"));
                sach.setCreatedAt(rs.getTimestamp("created_at"));
                listSach.add(sach);
            }
            return listSach;
        }catch (SQLException e){
            return null;
        }
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getTenSach() {
        return tenSach;
    }

    public void setTenSach(String tenSach) {
        this.tenSach = tenSach;
    }

    public String getNhaXuatBan() {
        return nhaXuatBan;
    }

    public void setNhaXuatBan(String nhaXuatBan) {
        this.nhaXuatBan = nhaXuatBan;
    }

    public int getNamXuatBan() {
        return namXuatBan;
    }

    public void setNamXuatBan(int namXuatBan) {
        this.namXuatBan = namXuatBan;
    }

    public int getGiaThue() {
        return giaThue;
    }

    public void setGiaThue(int giaThue) {
        this.giaThue = giaThue;
    }

    public String getMoTa() {
        return moTa;
    }

    public void setMoTa(String moTa) {
        this.moTa = moTa;
    }

    public int getGiaBan() {
        return giaBan;
    }

    public void setGiaBan(int giaBan) {
        this.giaBan = giaBan;
    }

    public String getAnh() {
        return anh;
    }

    public void setAnh(String anh) {
        this.anh = anh;
    }

    public Timestamp getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(Timestamp createdAt) {
        this.createdAt = createdAt;
    }
}
