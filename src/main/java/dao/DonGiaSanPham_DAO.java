package dao;

import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.UUID;

import connectDB.ConnectDB;
import entity.DonGiaSanPham;
import entity.SanPham;

public class DonGiaSanPham_DAO {
    // Phương thức lấy danh sách đơn giá sản phẩm
    public ArrayList<DonGiaSanPham> getAllDonGiaSanPham() {
        ArrayList<DonGiaSanPham> dsDonGiaSanPham = new ArrayList<>();
        try {
            ConnectDB.getInstance();
            Connection con = ConnectDB.getConnection();

            String sql = "SELECT * FROM DonGiaSanPham";
            PreparedStatement statement = con.prepareStatement(sql);

            ResultSet rs = statement.executeQuery();

            while (rs.next()) {
                String maDonGia = rs.getString(1);
                double donGia = rs.getDouble(2);
                String size = rs.getString(3);
                SanPham sp = new SanPham(rs.getString(4));

                DonGiaSanPham donGiaSanPham = new DonGiaSanPham(donGia, size, sp);
                dsDonGiaSanPham.add(donGiaSanPham);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return dsDonGiaSanPham;
    }

    public boolean create(DonGiaSanPham donGiaSanPham) {
        Connection con = null;
        PreparedStatement statement = null;
        int rowsAffected = 0;

        try {
            con = ConnectDB.getConnection();
            String query = "INSERT INTO DonGiaSanPham (donGia, size, maSP) VALUES (?, ?, ?)";
            statement = con.prepareStatement(query);

            // Đặt giá trị cho các tham số của câu lệnh SQL
            statement.setDouble(1, donGiaSanPham.getDonGia());
            statement.setString(2, donGiaSanPham.getKichThuoc());
            statement.setString(3, donGiaSanPham.getSanPham().getMaSP());

            rowsAffected = statement.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            try {
                statement.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }

        return rowsAffected > 0;
    }

    public boolean checkSizeExist(String size, String maSP) {
        Connection con = null;
        PreparedStatement statement = null;
        ResultSet resultSet = null;

        try {
            con = ConnectDB.getConnection();
            String query = "SELECT COUNT(*) AS count FROM DonGiaSanPham WHERE size = ? AND maSP = ?";
            statement = con.prepareStatement(query);
            statement.setString(1, size);
            statement.setString(2, maSP);
            resultSet = statement.executeQuery();

            if (resultSet.next()) {
                int count = resultSet.getInt("count");
                return count > 0; // Trả về true nếu size đã tồn tại, ngược lại trả về false
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            try {
                statement.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
        return false;
    }

    public boolean delete(String maSP, String size) {
        Connection con = null;
        PreparedStatement statement = null;
        int rowsAffected = 0;

        try {
            con = ConnectDB.getConnection();
            String query = "DELETE FROM DonGiaSanPham WHERE maSP = ? AND size = ?";
            statement = con.prepareStatement(query);
            statement.setString(1, maSP);
            statement.setString(2, size);

            rowsAffected = statement.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            try {
                statement.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }

        return rowsAffected > 0;
    }

    public double getPriceBySize(String maSP, String size) {
        double price = 0.0;
        try {
            ConnectDB.getInstance();
            Connection con = ConnectDB.getConnection();

            String sql = "SELECT donGia FROM DonGiaSanPham WHERE maSP = ? AND size = ?";
            PreparedStatement statement = con.prepareStatement(sql);
            statement.setString(1, maSP);
            statement.setString(2, size);

            ResultSet rs = statement.executeQuery();

            if (rs.next()) {
                price = rs.getDouble("donGia");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return price;
    }

    public ArrayList<DonGiaSanPham> getDonGiaByProductID(String maSP) {
        ArrayList<DonGiaSanPham> danhSachDonGia = new ArrayList<>();
        try {
            Connection con = ConnectDB.getConnection();

            // Gọi thủ tục GetProductPriceByProductID với CallableStatement
            CallableStatement callableStatement = con.prepareCall("{call GetProductPriceByProductID(?)}");
            callableStatement.setString(1, maSP); // Thiết lập tham số cho thủ tục

            // Thực thi thủ tục và lấy kết quả
            ResultSet rs = callableStatement.executeQuery();

            // Xử lý kết quả
            while (rs.next()) {
                String maSPResult = rs.getString("maSP");
                double donGia = rs.getDouble("donGia");
                String size = rs.getString("size");

                // Tạo đối tượng SanPham
                SanPham sanPham = new SanPham(maSPResult);

                // Tạo đối tượng DonGiaSanPham và thêm vào danh sách
                DonGiaSanPham donGiaSanPham = new DonGiaSanPham(donGia, size, sanPham);
                danhSachDonGia.add(donGiaSanPham);
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }
        return danhSachDonGia;
    }

}
