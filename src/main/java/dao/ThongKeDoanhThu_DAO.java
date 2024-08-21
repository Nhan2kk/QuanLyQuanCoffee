package dao;

import java.sql.Connection;
import java.sql.Date;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;

import connectDB.ConnectDB;
import entity.HoaDon;
import entity.KhachHang;
import entity.NhanVien;

public class ThongKeDoanhThu_DAO {
    public ArrayList<HoaDon> getAllHoaDon() {
        ArrayList<HoaDon> dsHoaDon = new ArrayList<HoaDon>();
        try {
            ConnectDB.getInstance();
            Connection con = ConnectDB.getConnection();

            String sql = "SELECT maHD, hoTen, tenKH, ngayLapHD, tongTien "
                    + "FROM HoaDon hd "
                    + "JOIN NhanVien nv on hd.maNV = nv.maNV "
                    + "JOIN KhachHang kh on hd.maKH = kh.maKH";
            Statement statement = con.createStatement();

            ResultSet rs = statement.executeQuery(sql);

            while (rs.next()) {
                String maHD = rs.getString(1);
                NhanVien tenNV = new NhanVien(rs.getString(2));
                KhachHang tenKH = new KhachHang(rs.getString(3));
                Date ngayLap = rs.getDate(4);
                double tongTien = rs.getDouble(5);

                // HoaDon hd = new HoaDon(maHD,tenNV,tenKH,ngayLap,tongTien);
                // dsHoaDon.add(hd);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return dsHoaDon;
    }
}
