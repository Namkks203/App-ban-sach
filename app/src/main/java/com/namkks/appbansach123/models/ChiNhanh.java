package com.namkks.appbansach123.models;

import com.namkks.appbansach123.controller.DAO;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;
import java.sql.Timestamp;

public class ChiNhanh {
    private int id;
    private String ten;
    private String diaChi;
    private String dienThoai;
    private Timestamp createdAt;

    public ChiNhanh() {
    }

    public ChiNhanh(int id, String ten, String diaChi, String dienThoai, Timestamp createdAt) {
        this.id = id;
        this.ten = ten;
        this.diaChi = diaChi;
        this.dienThoai = dienThoai;
        this.createdAt = createdAt;
    }

    public ChiNhanh themChiNhanh(){
        DAO dao = new DAO();
        try{
            PreparedStatement stm = dao.con.prepareStatement(
                    "insert into chi_nhanhs (ten, dia_chi, dien_thoai) values (?, ?, ?)",
                    Statement.RETURN_GENERATED_KEYS);

            stm.setString(1, this.ten);
            stm.setString(2, this.diaChi);
            stm.setString(3, this.dienThoai);
            int affected = stm.executeUpdate();
            if(affected > 0){
                ResultSet rs = stm.getGeneratedKeys();
                if(rs.next()){
                    ChiNhanh chiNhanh = getChiNhanh(rs.getInt(1));

                    dao.close();
                    return chiNhanh;
                }
            }

            dao.close();
            return null;
        }catch (Exception e){
            dao.close();
            return null;
        }
    }

    public static ChiNhanh getChiNhanh(int chiNhanhId){
        DAO dao = new DAO();
        try{
            PreparedStatement stm = dao.con.prepareStatement("SELECT * FROM chi_nhanhs WHERE id = ?");
            stm.setInt(1, chiNhanhId);

            ResultSet rs = stm.executeQuery();
            if(rs.next()){
                ChiNhanh chiNhanh = new ChiNhanh();
                chiNhanh.setId(rs.getInt("id"));
                chiNhanh.setTen(rs.getString("ten"));
                chiNhanh.setDiaChi(rs.getString("dia_chi"));
                chiNhanh.setDienThoai(rs.getString("dien_thoai"));
                chiNhanh.setCreatedAt(rs.getTimestamp("created_at"));

                dao.close();
                return chiNhanh;
            }

            dao.close();
            return null;
        }catch (Exception e){
            dao.close();
            return null;
        }
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

    public String getDiaChi() {
        return diaChi;
    }

    public void setDiaChi(String diaChi) {
        this.diaChi = diaChi;
    }

    public String getDienThoai() {
        return dienThoai;
    }

    public void setDienThoai(String dienThoai) {
        this.dienThoai = dienThoai;
    }

    public Timestamp getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(Timestamp createdAt) {
        this.createdAt = createdAt;
    }
}
