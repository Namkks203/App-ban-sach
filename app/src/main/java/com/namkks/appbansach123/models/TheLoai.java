package com.namkks.appbansach123.models;

import com.namkks.appbansach123.controller.DAO;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;

public class TheLoai {
    private int id;
    private String ten;

    public TheLoai() {
    }

    public TheLoai addTheLoai(){
        DAO dao = new DAO();
        try{

            PreparedStatement stm = dao.con.prepareStatement("insert into the_loais (ten) values (?)", Statement.RETURN_GENERATED_KEYS);
            stm.setString(1, this.ten);


            int affected =  stm.executeUpdate();
            if(affected > 0){
                ResultSet rs = stm.getGeneratedKeys();
                if(rs.next()){
                    TheLoai theLoai = getTheLoaiById(rs.getInt(1));

                    dao.close();
                    return theLoai;
                }
            }

            dao.close();
            return null;
        }catch (SQLException e){
            return null;
        }
    }

    public static ArrayList<TheLoai> getAllTheLoai(){
        try{
            ArrayList<TheLoai> theLoais = new ArrayList<>();
            DAO dao = new DAO();
            PreparedStatement stm = dao.con.prepareStatement("select * from the_loais");
            ResultSet rs = stm.executeQuery();
            while (rs.next()){
                TheLoai theLoai = new TheLoai();
                theLoai.setId(rs.getInt("id"));
                theLoai.setTen(rs.getString("ten"));
                theLoais.add(theLoai);
            }
            return theLoais;
        }catch (SQLException e){
            return null;
        }
    }
    public static TheLoai getTheLoaiById(int id){
        try{
            DAO dao = new DAO();
            PreparedStatement stm = dao.con.prepareStatement("select * from the_loais where id = ?");
            stm.setInt(1, id);
            ResultSet rs = stm.executeQuery();
            rs.next();
            TheLoai theLoai = new TheLoai();
            theLoai.setId(rs.getInt("id"));
            theLoai.setTen(rs.getString("ten"));

            return theLoai;
        }catch (SQLException e){
            return null;
        }
    }
    public static TheLoai getTheLoaiByTen(int id){
        try{
            DAO dao = new DAO();
            PreparedStatement stm = dao.con.prepareStatement("select * from the_loais where ten = ?");
            stm.setInt(1, id);
            ResultSet rs = stm.executeQuery();
            rs.next();
            TheLoai theLoai = new TheLoai();
            theLoai.setId(rs.getInt("id"));
            theLoai.setTen(rs.getString("ten"));

            return theLoai;
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
