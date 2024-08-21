package dao;

import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;

import connectDB.ConnectDB;
import entity.LoaiSanPham;
import entity.SanPham;

public class SanPham_DAO {

    public SanPham_DAO() {
    }

    public ArrayList<SanPham> getAllSanPham() {
        ArrayList<SanPham> dsSP = new ArrayList<SanPham>();
        try {
            ConnectDB.getInstance();
            Connection con = ConnectDB.getConnection();

            String sql = "SELECT * FROM SanPham";
            Statement statement = con.createStatement();

            ResultSet rs = statement.executeQuery(sql);

            while (rs.next()) {
                String maSP = rs.getString(1);
                String tenSP = rs.getString(2);
                String hinhAnh = rs.getString(3);
                boolean trangThai = rs.getBoolean(4);
                Date ngayCapNhat = rs.getDate(5);
                LoaiSanPham loaiSP = new LoaiSanPham(rs.getString(6));
                SanPham sp = new SanPham(maSP, tenSP, hinhAnh, trangThai, ngayCapNhat, loaiSP);
                dsSP.add(sp);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return dsSP;
    }

    public ArrayList<SanPham> getAllProduct() {
        ArrayList<SanPham> productList = new ArrayList<>();
        try {
            ConnectDB.getInstance();
            Connection con = ConnectDB.getConnection();

            // Gọi thủ tục từ Java
            CallableStatement cs = con.prepareCall("{CALL GetAllProduct}");

            // Thực thi thủ tục
            ResultSet rs = cs.executeQuery();

            while (rs.next()) {
                String maSP = rs.getString("maSP");
                String tenSP = rs.getString("tenSP");
                String hinhAnh = rs.getString("hinhAnh");
                boolean trangThai = rs.getBoolean("trangThai");
                Date ngayCapNhat = rs.getDate("ngayCapNhat");
                String tenLoai = rs.getString("tenLoai");

                // Tạo đối tượng LoaiSanPham từ tên loại
                LoaiSanPham loaiSP = new LoaiSanPham();
                loaiSP.setTenLoai(tenLoai);

                // Tạo đối tượng SanPham từ dữ liệu từ ResultSet
                SanPham sp = new SanPham(maSP, tenSP, hinhAnh, trangThai, ngayCapNhat, loaiSP);
                productList.add(sp);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return productList;
    }

    public ArrayList<SanPham> getProductInfo() {
        ArrayList<SanPham> productList = new ArrayList<>();
        try {
            ConnectDB.getInstance();
            Connection con = ConnectDB.getConnection();

            // Gọi thủ tục từ Java
            CallableStatement cs = con.prepareCall("{CALL GetProductInfo}");

            // Thực thi thủ tục
            ResultSet rs = cs.executeQuery();

            while (rs.next()) {
                String maSP = rs.getString("maSP");
                String tenSP = rs.getString("tenSP");
                String tenLoai = rs.getString("tenLoai");
                boolean trangThai = rs.getBoolean("trangThai");
                Date ngayCapNhat = rs.getDate("ngayCapNhat");

                // Tạo đối tượng LoaiSanPham từ tên loại
                LoaiSanPham loaiSP = new LoaiSanPham();
                loaiSP.setTenLoai(tenLoai);

                // Tạo đối tượng SanPham từ dữ liệu từ ResultSet
                SanPham sp = new SanPham(maSP, tenSP, trangThai, ngayCapNhat, loaiSP);
                productList.add(sp);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return productList;
    }

    public boolean create(SanPham sp) {
        ConnectDB.getInstance();
        Connection con = ConnectDB.getConnection();
        PreparedStatement statement = null;
        int n = 0;
        try {
            statement = con.prepareStatement(
                    "INSERT INTO SanPham VALUES (?, ?, ?, ?, ?, ?)");
            statement.setString(1, sp.getMaSP());
            statement.setString(2, sp.getTenSP());
            statement.setString(3, sp.getImage());
            statement.setBoolean(4, sp.isTrangThai());
            statement.setDate(5, sp.getNgayCN());
            statement.setString(6, sp.getLoaiSP().getMaLoai());
            n = statement.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            try {
                statement.close();
            } catch (SQLException e2) {
                e2.printStackTrace();
            }
        }
        return n > 0;
    }

    public boolean update(SanPham sp) {
        ConnectDB.getInstance();
        Connection con = ConnectDB.getConnection();
        PreparedStatement statement = null;
        int n = 0;
        try {
            statement = con.prepareStatement(
                    "UPDATE SanPham SET tenSP = ?, hinhAnh = ?, trangThai = ?, ngayCapNhat = ?, maLoai = ? WHERE maSP = ?");
            statement.setString(1, sp.getTenSP());
            statement.setString(2, sp.getImage());
            statement.setBoolean(3, sp.isTrangThai());
            statement.setDate(4, sp.getNgayCN());
            statement.setString(5, sp.getLoaiSP().getMaLoai());
            statement.setString(6, sp.getMaSP());
            n = statement.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            try {
                statement.close();
            } catch (SQLException e2) {
                e2.printStackTrace();
            }
        }
        return n > 0;
    }

    public boolean delete(SanPham sp) {
        ConnectDB.getInstance();
        Connection con = ConnectDB.getConnection();
        PreparedStatement statement = null;
        int n = 0;
        try {
            statement = con.prepareStatement("DELETE FROM SanPham WHERE maSP = ?");
            statement.setString(1, sp.getMaSP());
            n = statement.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            try {
                statement.close();
            } catch (SQLException e2) {
                e2.printStackTrace();
            }
        }
        return n > 0;
    }

    public ArrayList<SanPham> getSanPhamByCategory(String category) {
        ArrayList<SanPham> dsSP = new ArrayList<>();
        try {
            ConnectDB.getInstance();
            Connection con = ConnectDB.getConnection();

            String sql = "SELECT SanPham.maSP, SanPham.tenSP, SanPham.hinhAnh, SanPham.trangThai, SanPham.ngayCapNhat, LoaiSanPham.maLoai, LoaiSanPham.tenLoai "
                    +
                    "FROM SanPham INNER JOIN LoaiSanPham ON SanPham.maLoai = LoaiSanPham.maLoai " +
                    "WHERE LoaiSanPham.tenLoai = ? AND SanPham.trangThai = ?";
            PreparedStatement statement = con.prepareStatement(sql);
            statement.setString(1, category);
            statement.setBoolean(2, true);
            ResultSet rs = statement.executeQuery();

            while (rs.next()) {
                String maSP = rs.getString(1);
                String tenSP = rs.getString(2);
                String hinhAnh = rs.getString(3);
                boolean trangThai = rs.getBoolean(4);
                Date ngayCapNhat = rs.getDate(5);
                String maLoai = rs.getString(6);
                String tenLoai = rs.getString(7);

                LoaiSanPham loaiSP = new LoaiSanPham(maLoai, tenLoai);
                SanPham sp = new SanPham(maSP, tenSP, hinhAnh, trangThai, ngayCapNhat, loaiSP);
                dsSP.add(sp);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return dsSP;
    }

    public ArrayList<SanPham> getSanPhamByStatus() {
        ArrayList<SanPham> dsSP = new ArrayList<>();
        try {
            ConnectDB.getInstance();
            Connection con = ConnectDB.getConnection();

            String sql = "SELECT SanPham.maSP, SanPham.tenSP, SanPham.hinhAnh, SanPham.trangThai, SanPham.ngayCapNhat, LoaiSanPham.maLoai, LoaiSanPham.tenLoai "
                    +
                    "FROM SanPham INNER JOIN LoaiSanPham ON SanPham.maLoai = LoaiSanPham.maLoai " +
                    "WHERE SanPham.trangThai = ?";
            PreparedStatement statement = con.prepareStatement(sql);
            statement.setBoolean(1, true);
            ResultSet rs = statement.executeQuery();

            while (rs.next()) {
                String maSP = rs.getString(1);
                String tenSP = rs.getString(2);
                String hinhAnh = rs.getString(3);
                boolean trangThai = rs.getBoolean(4);
                Date ngayCapNhat = rs.getDate(5);
                String maLoai = rs.getString(6);
                String tenLoai = rs.getString(7);

                LoaiSanPham loaiSP = new LoaiSanPham(maLoai, tenLoai);
                SanPham sp = new SanPham(maSP, tenSP, hinhAnh, trangThai, ngayCapNhat, loaiSP);
                dsSP.add(sp);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return dsSP;
    }

    public String getSanPhamByTenAndSize(String tenSP, String size) {
        String maSP = "";
        try {
            ConnectDB.getInstance();
            Connection con = ConnectDB.getConnection();

            String sql = "SELECT SanPham.maSP " +
                    "FROM SanPham INNER JOIN DonGiaSanPham ON SanPham.maSP = DonGiaSanPham.maSP " +
                    "WHERE SanPham.tenSP = ? AND DonGiaSanPham.size = ?";
            PreparedStatement statement = con.prepareStatement(sql);
            statement.setString(1, tenSP);
            statement.setString(2, size);
            ResultSet rs = statement.executeQuery();

            if (rs.next()) {
                maSP = rs.getString(1);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return maSP;
    }
    public ArrayList<Object[]> getTop5SanPham() {
        ArrayList<Object[]> top5SanPham = new ArrayList<>();
        try {
            Connection conn = ConnectDB.getConnection();
            CallableStatement cs = conn.prepareCall("{CALL GetTop5SanPham}");

            ResultSet rs = cs.executeQuery();

            while (rs.next()) {
                Object[] row = new Object[3];
                row[0] = rs.getString("maSP");
                row[1] = rs.getString("tenSP");
                row[2] = rs.getInt("SoluongBan");
                top5SanPham.add(row);
            }

            rs.close();
            cs.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return top5SanPham;
    }


}
