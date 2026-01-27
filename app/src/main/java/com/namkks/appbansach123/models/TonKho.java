package com.namkks.appbansach123.models;

import com.namkks.appbansach123.controller.DAO;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;

public class TonKho {
    private int chiNhanhId;
    private int sachId;
    private int soLuong;

    public TonKho() {
    }

    public TonKho(int chiNhanhId, int sachId, int soLuong) {
        this.chiNhanhId = chiNhanhId;
        this.sachId = sachId;
        this.soLuong = soLuong;
    }

    public TonKho themTonKho(){
        DAO dao = new DAO();
        try {
            PreparedStatement statement = dao.con.prepareStatement("INSERT INTO ton_khos " +
                    "(chi_nhanh_id, sach_id, so_luong) VALUES (?, ?, ?)");
            statement.setInt(1, this.chiNhanhId);
            statement.setInt(2, this.sachId);
            statement.setInt(3, this.soLuong);

            int affected = statement.executeUpdate();
            if(affected > 0){
                TonKho tonKho = getTonKho(this.chiNhanhId, this.sachId);

                dao.close();
                return tonKho;
            }
            dao.close();
            return null;
        } catch (Exception e) {
            dao.close();
            return null;
        }
    }

    public static TonKho getTonKho(int chiNhanhId, int sachId){
        DAO dao = new DAO();
        try{
            PreparedStatement stm = dao.con.prepareStatement("SELECT * FROM ton_khos WHERE " +
                    "chi_nhanh_id = ? AND sach_id = ?");
            stm.setInt(1, chiNhanhId);
            stm.setInt(2, sachId);

            ResultSet resultSet = stm.executeQuery();
            if(resultSet.next()){
                TonKho tonKho = new TonKho();
                tonKho.setChiNhanhId(resultSet.getInt("chi_nhanh_id"));
                tonKho.setSachId(resultSet.getInt("sach_id"));
                tonKho.setSoLuong(resultSet.getInt("so_luong"));

                dao.close();
                return tonKho;
            }

            dao.close();
            return null;
        }catch (Exception e){
            dao.close();
            return null;
        }
    }
    public static ArrayList<TonKho> getListTonKhoByChiNhanhId(int chiNhanhId){
        DAO dao = new DAO();
        try{
            ArrayList<TonKho> tonKhoArrayList = new ArrayList<>();
            PreparedStatement stm = dao.con.prepareStatement("SELECT * FROM ton_khos WHERE " +
                    "chi_nhanh_id = ?");
            stm.setInt(1, chiNhanhId);

            ResultSet resultSet = stm.executeQuery();
            while (resultSet.next()){
                TonKho tonKho = new TonKho();
                tonKho.setChiNhanhId(resultSet.getInt("chi_nhanh_id"));
                tonKho.setSachId(resultSet.getInt("sach_id"));
                tonKho.setSoLuong(resultSet.getInt("so_luong"));

                tonKhoArrayList.add(tonKho);
            }

            dao.close();
            return tonKhoArrayList;
        }catch (Exception e){
            dao.close();
            return null;
        }
    }

    public int getChiNhanhId() {
        return chiNhanhId;
    }

    public void setChiNhanhId(int chiNhanhId) {
        this.chiNhanhId = chiNhanhId;
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
}
