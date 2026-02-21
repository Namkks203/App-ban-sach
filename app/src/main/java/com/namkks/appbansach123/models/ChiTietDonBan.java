package com.namkks.appbansach123.models;

import com.namkks.appbansach123.controller.DAO;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;

public class ChiTietDonBan {
    private int donBanId;
    private int sachId;
    private int soLuong;
    private int donGia;

    public ChiTietDonBan() {
    }

    public ChiTietDonBan(int donThueId, int sachId, int soLuong, int giaThue) {
        this.donBanId = donThueId;
        this.sachId = sachId;
        this.soLuong = soLuong;
        this.donGia = giaThue;
    }

    public ChiTietDonBan addChiTietDonBan(){
        DAO dao = new DAO();
        try{
            PreparedStatement statement = dao.con.prepareStatement(
                    "INSERT INTO chi_tiet_don_bans (don_ban_id, sach_id, so_luong, don_gia) " +
                            "VALUES (?, ?, ?, ?)"
            );
            statement.setInt(1, this.donBanId);
            statement.setInt(2, this.sachId);
            statement.setInt(3, this.soLuong);
            statement.setInt(4, this.donGia);

            int affected = statement.executeUpdate();
            if(affected > 0){
                ChiTietDonBan chiTietDonBan = getChiTietDonBan(this.donBanId, this.sachId);

                dao.close();
                return chiTietDonBan;
            }

            dao.close();
            return null;
        } catch (Exception e) {
            dao.close();
            return null;
        }
    }

    public static ArrayList<ChiTietDonBan> getChiTietDonBans(int donBanId){
        DAO dao = new DAO();
        try {
            ArrayList<ChiTietDonBan> chiTietDonBans = new ArrayList<>();
            PreparedStatement statement = dao.con.prepareStatement(
                    "SELECT * FROM chi_tiet_don_bans WHERE don_ban_id = ?"
            );
            statement.setInt(1, donBanId);

            ResultSet resultSet = statement.executeQuery();
            while (resultSet.next()){
                ChiTietDonBan chiTietDonBan = new ChiTietDonBan();
                chiTietDonBan.setDonBanId(resultSet.getInt("don_ban_id"));
                chiTietDonBan.setSachId(resultSet.getInt("sach_id"));
                chiTietDonBan.setSoLuong(resultSet.getInt("so_luong"));
                chiTietDonBan.setDonGia(resultSet.getInt("don_gia"));

                chiTietDonBans.add(chiTietDonBan);
            }
            dao.close();
            return chiTietDonBans;
        } catch (Exception e) {
            dao.close();
            return null;
        }
    }
    public static ChiTietDonBan getChiTietDonBan(int donBanId, int sachId){
        DAO dao = new DAO();
        try {
            PreparedStatement statement = dao.con.prepareStatement(
                    "SELECT * FROM chi_tiet_don_bans WHERE don_ban_id = ? AND sach_id = ?"
            );
            statement.setInt(1, donBanId);
            statement.setInt(2, sachId);

            ResultSet resultSet = statement.executeQuery();
            if(resultSet.next()){
                ChiTietDonBan chiTietDonBan = new ChiTietDonBan();
                chiTietDonBan.setDonBanId(resultSet.getInt("don_ban_id"));
                chiTietDonBan.setSachId(resultSet.getInt("sach_id"));
                chiTietDonBan.setSoLuong(resultSet.getInt("so_luong"));
                chiTietDonBan.setDonGia(resultSet.getInt("don_gia"));

                dao.close();
                return chiTietDonBan;
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

    public int getDonBanId() {
        return donBanId;
    }

    public void setDonBanId(int donBanId) {
        this.donBanId = donBanId;
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

    public int getDonGia() {
        return donGia;
    }

    public void setDonGia(int donGia) {
        this.donGia = donGia;
    }
}
