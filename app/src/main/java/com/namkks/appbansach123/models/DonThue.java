package com.namkks.appbansach123.models;

import com.namkks.appbansach123.controller.DAO;

import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;
import java.sql.Timestamp;

public class DonThue {
    private int id;
    private int donHangId;
    private Date ngayThue;
    private Date ngayHenTra;
    private Date ngayTraThucTe;
    private String trangThai;
    private Timestamp createdAt;

    public DonThue() {
    }

    public DonThue(int id, int donHangId, Date ngayThue, Date ngayHenTra, Date ngayTraThucTe,
                   String trangThai, Timestamp createdAt) {
        this.id = id;
        this.donHangId = donHangId;
        this.ngayThue = ngayThue;
        this.ngayHenTra = ngayHenTra;
        this.ngayTraThucTe = ngayTraThucTe;
        this.trangThai = trangThai;
        this.createdAt = createdAt;
    }

    public DonThue addDonThue(){
        DAO dao = new DAO();
        try {
            PreparedStatement statement = dao.con.prepareStatement(
                    "INSERT INTO don_thues (don_hang_id, ngay_hen_tra) VALUES ( ?, ?)",
                    Statement.RETURN_GENERATED_KEYS
            );

            statement.setInt(1, this.donHangId);
            statement.setDate(2, this.ngayHenTra);

            int affected = statement.executeUpdate();
            if(affected > 0){
                ResultSet resultSet = statement.getGeneratedKeys();

                if(resultSet.next()){
                    DonThue donThue = getDonThueById(resultSet.getInt(1));

                    dao.close();
                    return donThue;
                }
            }
            dao.close();
            return null;
        } catch (Exception e) {
            dao.close();
            return null;
        }
    }

    public static DonThue getDonThueById(int id){
        DAO dao = new DAO();
        try{
            PreparedStatement statement = dao.con.prepareStatement(
                    "SELECT * FROM don_thues WHERE id = ?"
            );
            statement.setInt(1, id);

            ResultSet resultSet = statement.executeQuery();
            if(resultSet.next()){
                DonThue donThue = new DonThue();

                donThue.setId(resultSet.getInt("id"));
                donThue.setDonHangId(resultSet.getInt("don_hang_id"));
                donThue.setNgayThue(resultSet.getDate("ngay_thue"));
                donThue.setNgayHenTra(resultSet.getDate("ngay_hen_tra"));
                donThue.setNgayTraThucTe(resultSet.getDate("ngay_tra_thuc_te"));
                donThue.setTrangThai(resultSet.getString("trang_thai"));
                donThue.setCreatedAt(resultSet.getTimestamp("created_at"));

                dao.close();
                return donThue;
            }

            dao.close();
            return null;
        }catch (Exception e){
            dao.close();
            return null;
        }
    }

    public static DonThue getDonThueByDonHangId(int donHangId){
        DAO dao = new DAO();
        try{
            PreparedStatement statement = dao.con.prepareStatement(
                    "SELECT * FROM don_thues WHERE don_hang_id = ?"
            );
            statement.setInt(1, donHangId);

            ResultSet resultSet = statement.executeQuery();
            if(resultSet.next()){
                DonThue donThue = new DonThue();

                donThue.setId(resultSet.getInt("id"));
                donThue.setDonHangId(resultSet.getInt("don_hang_id"));
                donThue.setNgayThue(resultSet.getDate("ngay_thue"));
                donThue.setNgayHenTra(resultSet.getDate("ngay_hen_tra"));
                donThue.setNgayTraThucTe(resultSet.getDate("ngay_tra_thuc_te"));
                donThue.setTrangThai(resultSet.getString("trang_thai"));
                donThue.setCreatedAt(resultSet.getTimestamp("created_at"));

                dao.close();
                return donThue;
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

    public int getDonHangId() {
        return donHangId;
    }

    public void setDonHangId(int donHangId) {
        this.donHangId = donHangId;
    }

    public Date getNgayThue() {
        return ngayThue;
    }

    public void setNgayThue(Date ngayThue) {
        this.ngayThue = ngayThue;
    }

    public Date getNgayHenTra() {
        return ngayHenTra;
    }

    public void setNgayHenTra(Date ngayHenTra) {
        this.ngayHenTra = ngayHenTra;
    }

    public Date getNgayTraThucTe() {
        return ngayTraThucTe;
    }

    public void setNgayTraThucTe(Date ngayTraThucTe) {
        this.ngayTraThucTe = ngayTraThucTe;
    }

    public String getTrangThai() {
        return trangThai;
    }

    public void setTrangThai(String trangThai) {
        this.trangThai = trangThai;
    }

    public Timestamp getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(Timestamp createdAt) {
        this.createdAt = createdAt;
    }
}
