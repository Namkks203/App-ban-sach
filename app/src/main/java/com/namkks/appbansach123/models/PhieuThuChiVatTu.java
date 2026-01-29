package com.namkks.appbansach123.models;

import com.namkks.appbansach123.controller.DAO;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;
import java.sql.Timestamp;

public class PhieuThuChiVatTu {
    private int id;
    private String loaiPhieu;
    private int chiNhanhId;

    private int sachId;
    private int soLuong;
    private String lyDo;
    private Timestamp createdAt;

    public PhieuThuChiVatTu() {
    }

    public PhieuThuChiVatTu(int id, String loaiPhieu, int chiNhanhId, int sachId, int soLuong,
                            String lyDo, Timestamp createdAt) {
        this.id = id;
        this.loaiPhieu = loaiPhieu;
        this.chiNhanhId = chiNhanhId;
        this.sachId = sachId;
        this.soLuong = soLuong;
        this.lyDo = lyDo;
        this.createdAt = createdAt;
    }

    public static PhieuThuChiVatTu getPhieuThuChiVatTu(int id){
        DAO dao = new DAO();
        try{
            PreparedStatement stm = dao.con.prepareStatement("SELECT * FROM phieu_thu_chi_vat_tu" +
                    " where id = ?");
            stm.setInt(1, id);

            ResultSet rs = stm.executeQuery();
            if(rs.next()){
                PhieuThuChiVatTu phieuVatTu = new PhieuThuChiVatTu();
                phieuVatTu.setId(rs.getInt("id"));
                phieuVatTu.setLoaiPhieu(rs.getString("loai_phieu"));
                phieuVatTu.setChiNhanhId(rs.getInt("chi_nhanhs_id"));
                phieuVatTu.setSachId(rs.getInt("sach_id"));
                phieuVatTu.setSoLuong(rs.getInt("so_luong"));
                phieuVatTu.setLyDo(rs.getString("ly_do"));
                phieuVatTu.setCreatedAt(rs.getTimestamp("created_at"));

                dao.close();
                return phieuVatTu;
            }

            dao.close();
            return null;
        }catch (Exception e){
            dao.close();
            return null;
        }
    }

    public PhieuThuChiVatTu addPhieuThuChiVatTu(){
        DAO dao = new DAO();
        try{
            PreparedStatement stm = dao.con.prepareStatement("INSERT INTO phieu_thu_chi_vat_tu" +
                    " (loai_phieu, chi_nhanhs_id, sach_id, so_luong, ly_do) VALUES (?, ?, ?, ?, ?)",
                    Statement.RETURN_GENERATED_KEYS);
            stm.setString(1, this.loaiPhieu);
            stm.setInt(2, this.chiNhanhId);
            stm.setInt(3, this.sachId);
            stm.setInt(4, this.soLuong);
            stm.setString(5, this.lyDo);

            int affected = stm.executeUpdate();
            if(affected > 0){
                ResultSet rs = stm.getGeneratedKeys();
                if(rs.next()){
                    PhieuThuChiVatTu phieuVatTu = getPhieuThuChiVatTu(rs.getInt(1));

                    dao.close();
                    return phieuVatTu;
                }
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

    public String getLoaiPhieu() {
        return loaiPhieu;
    }

    public void setLoaiPhieu(String loaiPhieu) {
        this.loaiPhieu = loaiPhieu;
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

    public String getLyDo() {
        return lyDo;
    }

    public void setLyDo(String lyDo) {
        this.lyDo = lyDo;
    }

    public Timestamp getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(Timestamp createdAt) {
        this.createdAt = createdAt;
    }
}
