package com.namkks.appbansach123.models;

import com.namkks.appbansach123.controller.DAO;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Date;

public class DonHang {
    private int id;
    private int chiNhanhId;
    private int khachHangId;
    private int nhanVienId;
    private String loaiDon;
    private String trangThai;
    private int tongTien;
    private int tongTienCoc;
    private Timestamp createdAt;

    public DonHang() {
    }

    public DonHang(int id, int chiNhanhId, int khachHangId, int nhanVienId, String loaiDon,
                   String trangThai, int tongTien, int tongTienCoc, Timestamp createdAt) {
        this.id = id;
        this.chiNhanhId = chiNhanhId;
        this.khachHangId = khachHangId;
        this.nhanVienId = nhanVienId;
        this.loaiDon = loaiDon;
        this.trangThai = trangThai;
        this.tongTien = tongTien;
        this.tongTienCoc = tongTienCoc;
        this.createdAt = createdAt;
    }

    public DonHang addDonHang() {
        DAO dao = new DAO();
        try {
            PreparedStatement stm = dao.con.prepareStatement("INSERT INTO don_hangs (khach_hang_id," +
                    "loai_don, tong_tien, tong_tien_coc) VALUES (?, ?, ?, ?)", Statement.RETURN_GENERATED_KEYS);
            stm.setInt(1, this.khachHangId);
            stm.setString(2, this.loaiDon);
            stm.setInt(3, this.tongTien);
            stm.setInt(4, this.tongTienCoc);

            int affected = stm.executeUpdate();
            if (affected > 0) {
                ResultSet resultSet = stm.getGeneratedKeys();
                if (resultSet.next()) {
                    DonHang donHang = getDonHangById(resultSet.getInt(1));

                    dao.close();
                    return donHang;
                }
            }
            dao.close();
            return null;
        } catch (Exception e) {
            dao.close();
            return null;
        }
    }

    public static DonHang getDonHangById(int id) {
        DAO dao = new DAO();
        try {
            PreparedStatement stm = dao.con.prepareStatement("SELECT * FROM don_hangs WHERE id = ?");
            stm.setInt(1, id);

            ResultSet resultSet = stm.executeQuery();
            if (resultSet.next()) {
                DonHang donHang = new DonHang();
                donHang.setId(resultSet.getInt("id"));
                donHang.setChiNhanhId(resultSet.getInt("chi_nhanh_id"));
                donHang.setKhachHangId(resultSet.getInt("khach_hang_id"));
                donHang.setNhanVienId(resultSet.getInt("nhan_vien_id"));
                donHang.setLoaiDon(resultSet.getString("loai_don"));
                donHang.setTrangThai(resultSet.getString("trang_thai"));
                donHang.setTongTien(resultSet.getInt("tong_tien"));
                donHang.setTongTienCoc(resultSet.getInt("tong_tien_coc"));
                donHang.setCreatedAt(resultSet.getTimestamp("created_at"));

                dao.close();
                return donHang;
            }
            dao.close();
            return null;
        } catch (Exception e) {
            dao.close();
            return null;
        }
    }

    public static ArrayList<DonHang> getDonHangsByKhachHangId(int khachHangId) {
        DAO dao = new DAO();
        try {
            ArrayList<DonHang> donHangs = new ArrayList<>();
            PreparedStatement statement = dao.con.prepareStatement("SELECT * FROM don_hangs " +
                    "WHERE khach_hang_id = ?");
            statement.setInt(1, khachHangId);
            ResultSet resultSet = statement.executeQuery();
            while (resultSet.next()) {
                DonHang donHang = new DonHang();
                donHang.setId(resultSet.getInt("id"));
                donHang.setChiNhanhId(resultSet.getInt("chi_nhanh_id"));
                donHang.setKhachHangId(resultSet.getInt("khach_hang_id"));
                donHang.setNhanVienId(resultSet.getInt("nhan_vien_id"));
                donHang.setLoaiDon(resultSet.getString("loai_don"));
                donHang.setTrangThai(resultSet.getString("trang_thai"));
                donHang.setTongTien(resultSet.getInt("tong_tien"));
                donHang.setTongTienCoc(resultSet.getInt("tong_tien_coc"));
                donHang.setCreatedAt(resultSet.getTimestamp("created_at"));

                donHangs.add(donHang);
            }

            dao.close();
            return donHangs;
        } catch (Exception e) {
            dao.close();
            return null;
        }
    }

    public static ArrayList<DonHang> getDonHangsByChiNhanhId(int chiNhanhId) {
        DAO dao = new DAO();
        try {
            ArrayList<DonHang> donHangs = new ArrayList<>();
            PreparedStatement statement = dao.con.prepareStatement("SELECT * FROM don_hangs " +
                    "WHERE chi_nhanh_id = ?");
            statement.setInt(1, chiNhanhId);
            ResultSet resultSet = statement.executeQuery();
            while (resultSet.next()) {
                DonHang donHang = new DonHang();
                donHang.setId(resultSet.getInt("id"));
                donHang.setChiNhanhId(resultSet.getInt("chi_nhanh_id"));
                donHang.setKhachHangId(resultSet.getInt("khach_hang_id"));
                donHang.setNhanVienId(resultSet.getInt("nhan_vien_id"));
                donHang.setLoaiDon(resultSet.getString("loai_don"));
                donHang.setTrangThai(resultSet.getString("trang_thai"));
                donHang.setTongTien(resultSet.getInt("tong_tien"));
                donHang.setTongTienCoc(resultSet.getInt("tong_tien_coc"));
                donHang.setCreatedAt(resultSet.getTimestamp("created_at"));

                donHangs.add(donHang);
            }

            dao.close();
            return donHangs;
        } catch (Exception e) {
            dao.close();
            return null;
        }
    }

    public static ArrayList<DonHang> getDonHangsTrangThaiByKhachHangId(String trangThai, int khachHangId) {
        DAO dao = new DAO();
        try {
            ArrayList<DonHang> donHangs = new ArrayList<>();
            PreparedStatement statement = dao.con.prepareStatement("SELECT * FROM don_hangs " +
                    "WHERE khach_hang_id = ? AND trang_thai = ?");
            statement.setInt(1, khachHangId);
            statement.setString(2, trangThai);

            ResultSet resultSet = statement.executeQuery();
            while (resultSet.next()) {
                DonHang donHang = new DonHang();
                donHang.setId(resultSet.getInt("id"));
                donHang.setChiNhanhId(resultSet.getInt("chi_nhanh_id"));
                donHang.setKhachHangId(resultSet.getInt("khach_hang_id"));
                donHang.setNhanVienId(resultSet.getInt("nhan_vien_id"));
                donHang.setLoaiDon(resultSet.getString("loai_don"));
                donHang.setTrangThai(resultSet.getString("trang_thai"));
                donHang.setTongTien(resultSet.getInt("tong_tien"));
                donHang.setTongTienCoc(resultSet.getInt("tong_tien_coc"));
                donHang.setCreatedAt(resultSet.getTimestamp("created_at"));

                donHangs.add(donHang);
            }

            dao.close();
            return donHangs;
        } catch (Exception e) {
            dao.close();
            return null;
        }
    }

    public DonHang updateTrangThai(String trangThai) {
        DAO dao = new DAO();
        try {
            PreparedStatement statement = dao.con.prepareStatement(
                    "UPDATE don_hangs set trang_thai = ? WHERE id = ?"
            );
            statement.setString(1, trangThai);
            statement.setInt(2, this.id);

            int affected = statement.executeUpdate();
            if(affected > 0){
                this.setTrangThai(trangThai);

                dao.close();
                return this;
            }

            dao.close();
            return null;
        }catch (Exception e){
            dao.close();
            return null;
        }
    }

    public static boolean deleteDonHangById(int id){
        DAO dao = new DAO();
        try{
            PreparedStatement statement = dao.con.prepareStatement(
                    "DELETE FROM don_hangs WHERE id = ?");
            statement.setInt(1, id);

            int affected = statement.executeUpdate();

            dao.close();
            return affected > 0;
        }catch (Exception e){
            dao.close();
            return false;
        }
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getChiNhanhId() {
        return chiNhanhId;
    }

    public void setChiNhanhId(int chiNhanhId) {
        this.chiNhanhId = chiNhanhId;
    }

    public int getKhachHangId() {
        return khachHangId;
    }

    public void setKhachHangId(int khachHangId) {
        this.khachHangId = khachHangId;
    }

    public int getNhanVienId() {
        return nhanVienId;
    }

    public void setNhanVienId(int nhanVienId) {
        this.nhanVienId = nhanVienId;
    }

    public String getLoaiDon() {
        return loaiDon;
    }

    public void setLoaiDon(String loaiDon) {
        this.loaiDon = loaiDon;
    }

    public String getTrangThai() {
        return trangThai;
    }

    public void setTrangThai(String trangThai) {
        this.trangThai = trangThai;
    }

    public int getTongTien() {
        return tongTien;
    }

    public void setTongTien(int tongTien) {
        this.tongTien = tongTien;
    }

    public int getTongTienCoc() {
        return tongTienCoc;
    }

    public void setTongTienCoc(int tongTienCoc) {
        this.tongTienCoc = tongTienCoc;
    }

    public Timestamp getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(Timestamp createdAt) {
        this.createdAt = createdAt;
    }
}
