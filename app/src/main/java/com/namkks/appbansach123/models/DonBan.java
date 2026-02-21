package com.namkks.appbansach123.models;

import com.namkks.appbansach123.controller.DAO;

import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;
import java.sql.Timestamp;

public class DonBan {
    private int id;
    private int donHangId;
    private Timestamp createdAt;

    public DonBan() {
    }

    public DonBan(int id, int donHangId, Timestamp createdAt) {
        this.id = id;
        this.donHangId = donHangId;
        this.createdAt = createdAt;
    }

    public DonBan addDonBan(){
        DAO dao = new DAO();
        try {
            PreparedStatement statement = dao.con.prepareStatement(
                    "INSERT INTO don_bans (don_hang_id) VALUES (?)",
                    Statement.RETURN_GENERATED_KEYS
            );
            statement.setInt(1, this.donHangId);

            int affected = statement.executeUpdate();
            if(affected > 0){
                ResultSet resultSet = statement.getGeneratedKeys();
                if(resultSet.next()){
                    DonBan donBan = getDonBanById(resultSet.getInt(1));

                    dao.close();
                    return donBan;
                }
            }

            dao.close();
            return null;
        } catch (Exception e) {
            dao.close();
            return null;
        }
    }

    public static DonBan getDonBanById(int id){
        DAO dao = new DAO();
        try {
            PreparedStatement statement = dao.con.prepareStatement(
                    "SELECT * FROM don_bans WHERE id = ?"
            );
            statement.setInt(1, id);

            ResultSet resultSet = statement.executeQuery();
            if(resultSet.next()){
                DonBan donBan = new DonBan();

                donBan.setId(resultSet.getInt("id"));
                donBan.setDonHangId(resultSet.getInt("don_hang_id"));
                donBan.setCreatedAt(resultSet.getTimestamp("created_at"));

                dao.close();
                return donBan;
            }

            dao.close();
            return null;
        } catch (Exception e) {
            dao.close();
            return null;
        }
    }

    public static DonBan getDonBanByDonHangId(int donHangId){
        DAO dao = new DAO();
        try {
            PreparedStatement statement = dao.con.prepareStatement(
                    "SELECT * FROM don_bans WHERE don_hang_id = ?"
            );
            statement.setInt(1, donHangId);

            ResultSet resultSet = statement.executeQuery();
            if(resultSet.next()){
                DonBan donBan = new DonBan();

                donBan.setId(resultSet.getInt("id"));
                donBan.setDonHangId(resultSet.getInt("don_hang_id"));
                donBan.setCreatedAt(resultSet.getTimestamp("created_at"));

                dao.close();
                return donBan;
            }

            dao.close();
            return null;
        } catch (Exception e) {
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

    public Timestamp getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(Timestamp createdAt) {
        this.createdAt = createdAt;
    }
}
