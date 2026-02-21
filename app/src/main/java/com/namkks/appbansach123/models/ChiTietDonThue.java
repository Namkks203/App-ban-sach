package com.namkks.appbansach123.models;

import com.namkks.appbansach123.controller.DAO;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.Date;

public class ChiTietDonThue {
    private int donThueId;
    private int sachId;
    private int soLuong;
    private int giaThue;

    public ChiTietDonThue() {
    }

    public ChiTietDonThue(int donThueId, int sachId, int soLuong, int giaThue) {
        this.donThueId = donThueId;
        this.sachId = sachId;
        this.soLuong = soLuong;
        this.giaThue = giaThue;
    }

    public ChiTietDonThue addChiTietDonThue(){
        DAO dao = new DAO();
        try{
            PreparedStatement statement = dao.con.prepareStatement(
                    "INSERT INTO chi_tiet_don_thues (don_thue_id, sach_id, so_luong, gia_thue) " +
                            "VALUES (?, ?, ?, ?)"
            );
            statement.setInt(1, this.donThueId);
            statement.setInt(2, this.sachId);
            statement.setInt(3, this.soLuong);
            statement.setInt(4, this.giaThue);

            int affected = statement.executeUpdate();
            if(affected > 0){
                ChiTietDonThue chiTietDonThue = getChiTietDonThue(this.donThueId, this.sachId);

                dao.close();
                return chiTietDonThue;
            }

            dao.close();
            return null;
        } catch (Exception e) {
            dao.close();
            return null;
        }
    }

    public static ArrayList<ChiTietDonThue> getChiTietDonThues(int donThueId){
        DAO dao = new DAO();
        try {
            ArrayList<ChiTietDonThue> chiTietDonThues = new ArrayList<>();
            PreparedStatement statement = dao.con.prepareStatement(
                    "SELECT * FROM chi_tiet_don_thues WHERE don_thue_id = ?"
            );
            statement.setInt(1, donThueId);

            ResultSet resultSet = statement.executeQuery();
            while (resultSet.next()){
                ChiTietDonThue chiTietDonThue = new ChiTietDonThue();
                chiTietDonThue.setDonThueId(resultSet.getInt("don_thue_id"));
                chiTietDonThue.setSachId(resultSet.getInt("sach_id"));
                chiTietDonThue.setSoLuong(resultSet.getInt("so_luong"));
                chiTietDonThue.setGiaThue(resultSet.getInt("gia_thue"));

                chiTietDonThues.add(chiTietDonThue);
            }
            dao.close();
            return chiTietDonThues;
        } catch (Exception e) {
            dao.close();
            return null;
        }
    }
    public static ChiTietDonThue getChiTietDonThue(int donThueId, int sachId){
        DAO dao = new DAO();
        try {
            PreparedStatement statement = dao.con.prepareStatement(
                    "SELECT * FROM chi_tiet_don_thues WHERE don_thue_id = ? AND sach_id = ?"
            );
            statement.setInt(1, donThueId);
            statement.setInt(2, sachId);

            ResultSet resultSet = statement.executeQuery();
            if(resultSet.next()){
                ChiTietDonThue chiTietDonThue = new ChiTietDonThue();
                chiTietDonThue.setDonThueId(resultSet.getInt("don_thue_id"));
                chiTietDonThue.setSachId(resultSet.getInt("sach_id"));
                chiTietDonThue.setSoLuong(resultSet.getInt("so_luong"));
                chiTietDonThue.setGiaThue(resultSet.getInt("gia_thue"));

                dao.close();
                return chiTietDonThue;
            }

            dao.close();
            return null;
        } catch (Exception e) {
            dao.close();
            return null;
        }
    }

    public boolean deleteChiTietDonThue(int donThueId){
        DAO dao = new DAO();
        try{
            PreparedStatement statement = dao.con.prepareStatement(
                    "DELETE FROM chi_tiet_don_thues WHERE don_thue_id = ?"
            );
            statement.setInt(1, donThueId);

            int affected = statement.executeUpdate();
            dao.close();
            return affected > 0;
        } catch (Exception e) {
            dao.close();
            return false;
        }
    }

    public int getDonThueId() {
        return donThueId;
    }

    public void setDonThueId(int donThueId) {
        this.donThueId = donThueId;
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

    public int getGiaThue() {
        return giaThue;
    }

    public void setGiaThue(int giaThue) {
        this.giaThue = giaThue;
    }
}
