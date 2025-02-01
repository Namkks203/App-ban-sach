package com.namkks.appbansach123.models;

import com.namkks.appbansach123.controller.DAO;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

public class Sach {
    private int id;
    private String tenSach;
    private String tacGia;
    private String loai;
    private String nxb;
    private String moTa;
    private int giaTien;
    private String anh;

    public Sach() {
    }

    public Sach(int id, String tenSach, String tacGia, String loai, String nxb, int giaTien, String anh) {
        this.id = id;
        this.tenSach = tenSach;
        this.tacGia = tacGia;
        this.loai = loai;
        this.nxb = nxb;
        this.giaTien = giaTien;
        this.anh = anh;
    }

    public boolean ThemSach(){
        try {
            DAO a = new DAO();
            PreparedStatement stm = a.conn.prepareStatement("INSERT INTO `sach`(`TenSach`, `TacGia`, `Loai`, `NXB`, `MoTa`, `GiaTien`, `Anh`) " +
                    "VALUES (?, ?, ?, ?, ?, ?, ?)");
            stm.setString(1, this.tenSach);
            stm.setString(2, this.tacGia);
            stm.setString(3, this.loai);
            stm.setString(4, this.nxb);
            stm.setString(5, this.moTa);
            stm.setInt(6, this.giaTien);
            stm.setString(7, this.anh);

            return stm.executeUpdate() > 0;
        }catch (SQLException e){
            return false;
        }
    }
    public static ArrayList<Sach> ListSachOfLoai(String loai){
        ArrayList<Sach> listSach = new ArrayList<>();
        try{
            DAO a = new DAO();
            PreparedStatement stm = a.conn.prepareStatement("SELECT * FROM `sach` WHERE Loai = ?");
            stm.setString(1, loai);
            ResultSet rs = stm.executeQuery();
            while (rs.next()){
                Sach sach = new Sach();
                sach.setId(rs.getInt(1));
                sach.setTenSach(rs.getString(2));
                sach.setTacGia(rs.getString(3));
                sach.setLoai(rs.getString(4));
                sach.setNxb(rs.getString(5));
                sach.setMoTa(rs.getString(6));
                sach.setGiaTien(rs.getInt(7));
                sach.setAnh(rs.getString(8));
                listSach.add(sach);
            }
            return listSach;
        }catch (SQLException e){
            return listSach;
        }
    }
    public static ArrayList<Sach> TimKiemSach(String tensach){
        ArrayList<Sach> listSach = new ArrayList<>();
        try{
            DAO a = new DAO();
            PreparedStatement stm = a.conn.prepareStatement("SELECT * FROM `sach` WHERE TenSach LIKE '%"+tensach
                    +"' OR TenSach LIKE '"+tensach+"%' OR TenSach LIKE '%"+tensach+"%'");
            ResultSet rs = stm.executeQuery();
            while (rs.next()){
                Sach sach = new Sach();
                sach.setId(rs.getInt(1));
                sach.setTenSach(rs.getString(2));
                sach.setTacGia(rs.getString(3));
                sach.setLoai(rs.getString(4));
                sach.setNxb(rs.getString(5));
                sach.setMoTa(rs.getString(6));
                sach.setGiaTien(rs.getInt(7));
                sach.setAnh(rs.getString(8));
                listSach.add(sach);
            }
            return listSach;
        }catch (SQLException e){
            return null;
        }
    }
    public static Sach getSach(int id){
        try {
            DAO a = new DAO();
            PreparedStatement stm = a.conn.prepareStatement("SELECT * FROM `sach` WHERE id = ?");
            stm.setInt(1, id);
            ResultSet rs = stm.executeQuery();
            rs.next();
            Sach s = new Sach();
            s.setId(rs.getInt(1));
            s.setTenSach(rs.getString(2));
            s.setTacGia(rs.getString(3));
            s.setLoai(rs.getString(4));
            s.setNxb(rs.getString(5));
            s.setMoTa(rs.getString(6));
            s.setGiaTien(rs.getInt(7));
            s.setAnh(rs.getString(8));

            return s;
        }catch (SQLException e){
            return null;
        }
    }

    public static boolean XoaSach(int id_sach){
        try {
            DAO a = new DAO();
            PreparedStatement stm = a.conn.prepareStatement("DELETE FROM `sach` WHERE id = ?");
            stm.setInt(1, id_sach);

            return stm.executeUpdate() > 0;
        }catch (SQLException e){
            return false;
        }
    }
    public boolean SuaSach(){
        try {
            DAO a = new DAO();
            PreparedStatement stm = a.conn.prepareStatement("UPDATE `sach` SET `TenSach`=?,`TacGia`=?,`Loai`=?,`NXB`=?, `MoTa`=?," +
                    "`GiaTien`=?,`Anh`=? WHERE `id`=?");
            stm.setString(1, this.tenSach);
            stm.setString(2, this.tacGia);
            stm.setString(3, this.loai);
            stm.setString(4, this.nxb);
            stm.setString(5, this.moTa);
            stm.setInt(6, this.giaTien);
            stm.setString(7, this.anh);
            stm.setInt(8, this.id);

            return stm.executeUpdate() > 0;
        }catch (SQLException e){
            return false;
        }
    }

    public ArrayList<String> loaiSach(){
        ArrayList<String> loaiSach = new ArrayList<>();
        try {
            DAO a = new DAO();
            PreparedStatement stm = a.conn.prepareStatement("SELECT DISTINCT Loai FROM `sach`");
            ResultSet rs = stm.executeQuery();
            while (rs.next()){
                loaiSach.add(rs.getString(1));
            }
            return loaiSach;
        }catch (SQLException e){
            return null;
        }
    }
    public ArrayList<Sach> ListSachHot(){
        ArrayList<Sach> listSach = new ArrayList<>();
        try{
            DAO a = new DAO();
            PreparedStatement stm = a.conn.prepareStatement("SELECT * FROM `sach` ORDER BY id DESC LIMIT 8");
            ResultSet rs = stm.executeQuery();
            while (rs.next()){
                Sach sach = new Sach();
                sach.setId(rs.getInt(1));
                sach.setTenSach(rs.getString(2));
                sach.setTacGia(rs.getString(3));
                sach.setLoai(rs.getString(4));
                sach.setNxb(rs.getString(5));
                sach.setMoTa(rs.getString(6));
                sach.setGiaTien(rs.getInt(7));
                sach.setAnh(rs.getString(8));
                listSach.add(sach);
            }
            return listSach;
        }catch (SQLException e){
            return null;
        }
    }
    public static ArrayList<Sach> ListSach(){
        ArrayList<Sach> listSach = new ArrayList<>();
        try{
            DAO a = new DAO();
            PreparedStatement stm = a.conn.prepareStatement("SELECT * FROM `sach` ORDER BY id DESC");
            ResultSet rs = stm.executeQuery();
            while (rs.next()){
                Sach sach = new Sach();
                sach.setId(rs.getInt(1));
                sach.setTenSach(rs.getString(2));
                sach.setTacGia(rs.getString(3));
                sach.setLoai(rs.getString(4));
                sach.setNxb(rs.getString(5));
                sach.setMoTa(rs.getString(6));
                sach.setGiaTien(rs.getInt(7));
                sach.setAnh(rs.getString(8));
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

    public String getTacGia() {
        return tacGia;
    }

    public void setTacGia(String tacGia) {
        this.tacGia = tacGia;
    }

    public String getLoai() {
        return loai;
    }

    public void setLoai(String loai) {
        this.loai = loai;
    }

    public String getNxb() {
        return nxb;
    }

    public void setNxb(String nxb) {
        this.nxb = nxb;
    }

    public String getMoTa() {
        return moTa;
    }

    public void setMoTa(String moTa) {
        this.moTa = moTa;
    }

    public int getGiaTien() {
        return giaTien;
    }

    public void setGiaTien(int giaTien) {
        this.giaTien = giaTien;
    }

    public String getAnh() {
        return anh;
    }

    public void setAnh(String anh) {
        this.anh = anh;
    }
}
