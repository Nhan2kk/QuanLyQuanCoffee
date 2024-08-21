package dao;

import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.sql.Time;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.ArrayList;

import connectDB.ConnectDB;
import entity.HoaDon;
import entity.KhachHang;
import entity.NhanVien;

public class HoaDonBanHang_DAO {

    public HoaDonBanHang_DAO() {

    }

    public ArrayList<HoaDon> getAllHoaDon() {
        ArrayList<HoaDon> dsHoaDon = new ArrayList<>();
        try {
            ConnectDB.getInstance();
            Connection con = ConnectDB.getConnection();

            String sql = "SELECT hd.maHD, nv.hoTen AS hoTenNV, kh.tenKH, hd.ngayLapHD, hd.tongThanhToan, hd.gioVao "
                    + "FROM HoaDon hd "
                    + "LEFT JOIN NhanVien nv ON hd.maNV = nv.maNV "
                    + "LEFT JOIN KhachHang kh ON hd.maKH = kh.maKH ORDER BY hd.gioVao DESC ";
            Statement statement = con.createStatement();
            ResultSet rs = statement.executeQuery(sql);

            while (rs.next()) {
                String maHD = rs.getString("maHD");
                String hoTenNV = rs.getString("hoTenNV");
                NhanVien nhanVien = new NhanVien(hoTenNV); // Tạo đối tượng NhanVien đầy đủ
                String tenKH = rs.getString("tenKH");
                KhachHang khachHang = null; // Khởi tạo khách hàng là null

                if (tenKH != null) {
                    khachHang = new KhachHang(tenKH); // Tạo đối tượng KhachHang đầy đủ
                } else {
                    // Nếu khách hàng là null, gán là khách hàng lẻ
                    khachHang = new KhachHang("Khách hàng lẻ");
                }

                Date ngayLap = rs.getDate("ngayLapHD");
                double tongThanhToan = rs.getDouble("tongThanhToan");
                LocalDateTime gioVao = rs.getTimestamp("gioVao").toLocalDateTime(); // Chuyển từ Timestamp sang
                                                                                    // LocalDateTime
                HoaDon hd = new HoaDon(maHD, ngayLap, khachHang, nhanVien, gioVao);
                hd.setTongThanhToan(tongThanhToan);
                dsHoaDon.add(hd);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return dsHoaDon;
    }

    public HoaDon searchHoaDonTheoSdtKH(String dt) {
        Connection con = null;
        PreparedStatement statement = null;
        ResultSet rs = null;
        HoaDon hd = null;

        try {
            con = ConnectDB.getConnection();
            String sql = "SELECT * FROM KhachHang WHERE sdt =?";
            statement = con.prepareStatement(sql);
            statement.setString(1, dt);
            rs = statement.executeQuery();

            if (rs.next()) {
                String maHD = rs.getString(1);
                NhanVien tenNV = new NhanVien(rs.getString(2));
                KhachHang tenKH = new KhachHang(rs.getString(3));
                Date ngayLap = rs.getDate(4);
                double tongThanhToan = rs.getDouble(5);
                LocalDateTime gioVao = rs.getTimestamp(6).toLocalDateTime();

                hd = new HoaDon(maHD, ngayLap, tenKH, tenNV, gioVao);
                hd.setTongThanhToan(tongThanhToan);

            }
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            try {
                if (statement != null) {
                    statement.close();
                }
            } catch (SQLException e2) {
                e2.printStackTrace();
            }
        }
        return hd;
    }
    
}