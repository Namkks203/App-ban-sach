package com.namkks.appbansach123.models;

import com.namkks.appbansach123.controller.DAO;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;

public class TacGia {
    private int id;
    private String ten;

    public TacGia() {
    }

    public TacGia addTacGia() {
        DAO dao = new DAO();
        try {

            PreparedStatement stm = dao.con.prepareStatement(
                    "insert into tac_gias (ten) values (?)",
                    Statement.RETURN_GENERATED_KEYS
            );
            stm.setString(1, this.ten);

            int affected = stm.executeUpdate();
            if(affected > 0){
                ResultSet rs = stm.getGeneratedKeys();
                if(rs.next()){
                    TacGia tacGia = getTacGiaById(rs.getInt(1));

                    dao.close();
                    return tacGia;
                }
            }

            dao.close();
            return null;
        } catch (SQLException e) {
            e.printStackTrace();
            dao.close();
            return null;
        }
    }

    public static ArrayList<TacGia> getAllTacGia() {
        try {
            ArrayList<TacGia> listTacGia = new ArrayList<>();
            DAO dao = new DAO();
            PreparedStatement stm = dao.con.prepareStatement("select * from tac_gias");
            ResultSet rs = stm.executeQuery();
            while (rs.next()) {
                TacGia tacGia = new TacGia();
                tacGia.setId(rs.getInt("id"));
                tacGia.setTen(rs.getString("ten"));
                listTacGia.add(tacGia);
            }
            return listTacGia;
        } catch (SQLException e) {
            return null;
        }
    }

    public static TacGia getTacGiaById(int id) {
        DAO dao = new DAO();
        try {
            PreparedStatement stm = dao.con.prepareStatement("select * from tac_gias where id = ?");
            stm.setInt(1, id);
            ResultSet rs = stm.executeQuery();
            rs.next();
            TacGia tacGia = new TacGia();
            tacGia.setId(rs.getInt("id"));
            tacGia.setTen(rs.getString("ten"));

            dao.close();
            return tacGia;
        } catch (SQLException e) {
            dao.close();
            return null;
        }
    }

    public static TacGia getTacGiaByTen(String ten) {
        try {
            DAO dao = new DAO();
            PreparedStatement stm = dao.con.prepareStatement("select * from tac_gias where ten = ?");
            stm.setString(1, ten);
            ResultSet rs = stm.executeQuery();
            rs.next();
            TacGia tacGia = new TacGia();
            tacGia.setId(rs.getInt("id"));
            tacGia.setTen(rs.getString("ten"));

            return tacGia;
        } catch (SQLException e) {
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

    @Override
    public String toString() {
        return ten;
    }
}
