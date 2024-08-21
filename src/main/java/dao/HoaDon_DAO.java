package dao;

import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.sql.PreparedStatement;

import connectDB.ConnectDB;
import entity.HoaDon;

public class HoaDon_DAO {
    public HoaDon_DAO() {

    }

    public boolean create(HoaDon hoaDon) {
        ConnectDB.getInstance();
        Connection con = ConnectDB.getConnection();
        PreparedStatement statement = null;
        int n = 0;
        try {
            String sql = "INSERT INTO HoaDon (maHD, ngayLapHD, tongTien, tongThanhToan, maKH, maNV, gioVao) " +
                    "VALUES (?, ?, ?, ?, ?, ?, ?)";
            statement = con.prepareStatement(sql);
            statement.setString(1, hoaDon.getMaHoaDon());
            statement.setDate(2, hoaDon.getNgayLapHD());
            statement.setDouble(3, hoaDon.getTongTien());
            statement.setDouble(4, hoaDon.getTongThanhToan());
            if (hoaDon.getKhachHang() != null) {
                statement.setString(5, hoaDon.getKhachHang().getMaKH());
            } else {
                statement.setObject(5, null);
            }
            statement.setString(6, hoaDon.getNhanVien().getMa());
            statement.setObject(7, hoaDon.getGioVao());
            n = statement.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            try {
                if (statement != null) {
                    statement.close();
                }
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
        return n > 0;
    }

}
