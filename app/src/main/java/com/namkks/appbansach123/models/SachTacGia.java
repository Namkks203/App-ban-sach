package com.namkks.appbansach123.models;

import com.namkks.appbansach123.controller.DAO;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;

public class SachTacGia {
    private int sachId;
    private int tacGiaId;

    public SachTacGia() {
    }

    public boolean addSachTacGia(){
        DAO dao = new DAO();
        try{
            PreparedStatement stm = dao.con.prepareStatement("INSERT INTO sach_tac_gias (sach_id, tac_gia_id) VALUES (?, ?)");
            stm.setInt(1, this.sachId);
            stm.setInt(2, this.tacGiaId);

            boolean result = stm.executeUpdate() > 0;
            dao.close();
            return result;
        }catch (Exception e){
            dao.close();
            return false;
        }
    }

    public static ArrayList<SachTacGia> getSachTacGiaBySachId(int sachId){
        DAO dao = new DAO();
        try{
            ArrayList<SachTacGia> listSachTacGias = new ArrayList<>();
            PreparedStatement stm = dao.con.prepareStatement("SELECT * FROM sach_tac_gias WHERE sach_id = ?");
            stm.setInt(1, sachId);

            ResultSet rs = stm.executeQuery();
            while (rs.next()){
                SachTacGia sachTacGia = new SachTacGia();
                sachTacGia.setSachId(rs.getInt("sach_id"));
                sachTacGia.setTacGiaId(rs.getInt("tac_gia_id"));

                listSachTacGias.add(sachTacGia);
            }
            dao.close();
            return listSachTacGias;
        }catch (Exception e){
            dao.close();
            return null;
        }
    }

    public static ArrayList<SachTacGia> getSachTacGiaByTacGiaId(int tacGiaId){
        DAO dao = new DAO();
        try{
            ArrayList<SachTacGia> listSachTacGias = new ArrayList<>();
            PreparedStatement stm = dao.con.prepareStatement("SELECT * FROM sach_tac_gias WHERE tac_gia_id = ?");
            stm.setInt(1, tacGiaId);

            ResultSet rs = stm.executeQuery();
            while (rs.next()){
                SachTacGia sachTacGia = new SachTacGia();
                sachTacGia.setSachId(rs.getInt("sach_id"));
                sachTacGia.setTacGiaId(rs.getInt("tac_gia_id"));

                listSachTacGias.add(sachTacGia);
            }
            dao.close();
            return listSachTacGias;
        }catch (Exception e){
            dao.close();
            return null;
        }
    }

    public int getTacGiaId() {
        return tacGiaId;
    }

    public void setTacGiaId(int tacGiaId) {
        this.tacGiaId = tacGiaId;
    }

    public int getSachId() {
        return sachId;
    }

    public void setSachId(int sachId) {
        this.sachId = sachId;
    }
}
