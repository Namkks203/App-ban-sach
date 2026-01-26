package com.namkks.appbansach123.models;

import com.namkks.appbansach123.controller.DAO;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;

public class SachTheLoai {
    private int sachId;
    private int theLoaiId;

    public SachTheLoai() {
    }

    public boolean addSachTheLoai(){
        DAO dao = new DAO();
        try{
            PreparedStatement stm = dao.con.prepareStatement("INSERT INTO sach_the_loais (sach_id, the_loai_id) VALUES (?, ?)");
            stm.setInt(1, this.sachId);
            stm.setInt(2, this.theLoaiId);

            boolean result = stm.executeUpdate() > 0;
            dao.close();
            return result;
        }catch (Exception e){
            dao.close();
            return false;
        }
    }

    public static ArrayList<SachTheLoai> getSachTheLoaiBySachId(int sachId){
        DAO dao = new DAO();
        try{
            ArrayList<SachTheLoai> theLoaiArrayList = new ArrayList<>();
            PreparedStatement stm = dao.con.prepareStatement("SELECT * FROM sach_the_loais WHERE sach_id = ?");
            stm.setInt(1, sachId);

            ResultSet rs = stm.executeQuery();
            while (rs.next()){
                SachTheLoai sachTheLoai = new SachTheLoai();
                sachTheLoai.setSachId(rs.getInt("sach_id"));
                sachTheLoai.setTheLoaiId(rs.getInt("the_loai_id"));

                theLoaiArrayList.add(sachTheLoai);
            }
            dao.close();
            return theLoaiArrayList;
        }catch (Exception e){
            dao.close();
            return null;
        }
    }
    public static ArrayList<SachTacGia> getSachTheLoaiByTheLoaiId(int theLoaiId){
        DAO dao = new DAO();
        try{
            ArrayList<SachTacGia> listSachTacGias = new ArrayList<>();
            PreparedStatement stm = dao.con.prepareStatement("SELECT * FROM sach_the_loais WHERE the_loai_id = ?");
            stm.setInt(1, theLoaiId);

            ResultSet rs = stm.executeQuery();
            while (rs.next()){
                SachTacGia sachTacGia = new SachTacGia();
                sachTacGia.setSachId(rs.getInt("sach_id"));
                sachTacGia.setTacGiaId(rs.getInt("the_loai_id"));

                listSachTacGias.add(sachTacGia);
            }
            dao.close();
            return listSachTacGias;
        }catch (Exception e){
            dao.close();
            return null;
        }
    }

    public static boolean xoaSachTheLoai(int idSach){
        DAO dao = new DAO();
        try{
            PreparedStatement stm = dao.con.prepareStatement("DELETE FROM sach_the_loais WHERE sach_id = ?");
            stm.setInt(1, idSach);

            boolean result = stm.executeUpdate() > 0;

            dao.close();
            return result;
        } catch (Exception e) {
            dao.close();
            return false;
        }
    }

    public int getSachId() {
        return sachId;
    }

    public void setSachId(int sachId) {
        this.sachId = sachId;
    }

    public int getTheLoaiId() {
        return theLoaiId;
    }

    public void setTheLoaiId(int theLoaiId) {
        this.theLoaiId = theLoaiId;
    }
}
