package dao;

import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

import connectDB.ConnectDB;
import entity.NhanVien;

import java.sql.Statement;

public class DangNhap_DAO {
    private Connection con;
    private String username;
    private String ten;
    private String password;

    public DangNhap_DAO() {
        // Initialize connection in the constructor

        con = ConnectDB.getInstance().getConnection();
    }

    public String containTen(String tenDN) {
        try {
            String sql = "SELECT * FROM TaiKhoan WHERE  tenTaiKhoan = ?";
            PreparedStatement pstmt = con.prepareStatement(sql);
            pstmt.setString(1, tenDN);

            ResultSet rs = pstmt.executeQuery();

            if (rs.next()) {
                username = rs.getString("tenTaiKhoan");
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }
        return username;
    }

    public ArrayList<String> dangNhap(String TenDN, String MK) {
        ArrayList<String> list = new ArrayList<>();
        try {
            PreparedStatement pstmt = con.prepareStatement("SELECT * FROM TaiKhoan WHERE tenTaiKhoan = ? AND matKhau = ?");
            pstmt.setString(1, TenDN);
            pstmt.setString(2, MK);

            ResultSet rs = pstmt.executeQuery();

            if (rs.next()) {
                ten = rs.getString("tenTaiKhoan");
                password = rs.getString("matKhau");

            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        list.add(ten);
        list.add(password);
        return list;
    }


    public String getNameOfEmployeeWhenLogin(String tenTaiKhoan) {
        String hoTen = null;
        try {
            // Chuẩn bị câu lệnh gọi thủ tục
            String sql = "{CALL GetNameOfEmployeeWhenLogin(?)}";
            CallableStatement cs = con.prepareCall(sql);

            // Đặt giá trị cho tham số đầu vào
            cs.setString(1, tenTaiKhoan);

            // Thực thi thủ tục
            ResultSet rs = cs.executeQuery();

            // Lấy kết quả từ ResultSet
            if (rs.next()) {
                hoTen = rs.getString(1); // Lấy tên nhân viên từ cột đầu tiên của kết quả
            }

            // Đóng tài nguyên
            rs.close();
            cs.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return hoTen;
    }

    // Lấy đối tượng nhân viên theo tên tài khoản
    public NhanVien getNhanVienByTenTaiKhoan(String tenTaiKhoan) {
        NhanVien nv = null;
        try {
            // Chuẩn bị câu lệnh gọi thủ tục
            String sql = "{CALL GetNhanVienByTaiKhoan(?)}";
            CallableStatement cs = con.prepareCall(sql);

            // Đặt giá trị cho tham số đầu vào
            cs.setString(1, tenTaiKhoan);

            // Thực thi thủ tục
            ResultSet rs = cs.executeQuery();

            // Lấy kết quả từ ResultSet
            if (rs.next()) {
                nv = new NhanVien(); // Khởi tạo đối tượng NhanVien mới
                nv.setMa(rs.getString("MaNV"));
                nv.setHoTen(rs.getString("HoTen"));
                nv.setNgaySinh(rs.getDate("NgaySinh"));
                nv.setGioiTinh(rs.getBoolean("GioiTinh"));
                nv.setDiaChi(rs.getString("DiaChi"));
                nv.setSdt(rs.getString("SDT"));
                nv.setChucVu(rs.getString("ChucVu"));
                nv.setLuong(rs.getDouble("Luong"));
            }
            cs.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return nv;
    }

}
