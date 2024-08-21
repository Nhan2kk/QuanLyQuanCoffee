package dao;

import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;

import connectDB.ConnectDB;
import entity.NhanVien;

public class NhanVien_DAO {
    public NhanVien_DAO() {
    }

    public ArrayList<NhanVien> getAllNhanVien() {
        ArrayList<NhanVien> dsNV = new ArrayList<NhanVien>();
        try {
            ConnectDB.getInstance();
            Connection con = ConnectDB.getConnection();

            String sql = "SELECT * FROM NhanVien";
            Statement statement = con.createStatement();

            ResultSet rs = statement.executeQuery(sql);

            while (rs.next()) {
                String maNV = rs.getString(1);
                String hoTen = rs.getString(2);
                String email = rs.getString(3);
                String sdt = rs.getString(4);
                Date ngaySinh = rs.getDate(5);
                String chucVu = rs.getString(6);
                String diaChi = rs.getString(7);
                double luong = rs.getDouble(8);
                boolean gioiTinh = rs.getBoolean(9);
                NhanVien nv = new NhanVien(maNV, hoTen, email, sdt, gioiTinh, ngaySinh, chucVu, diaChi, luong);
                dsNV.add(nv);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return dsNV;
    }

    public boolean create(NhanVien nv) {
        ConnectDB.getInstance();
        Connection con = ConnectDB.getConnection();
        PreparedStatement statement = null;
        int n = 0;
        try {
            statement = con.prepareStatement(
                    "INSERT INTO NhanVien VALUES(?,?,?,?,?,?,?,?,?)");
            statement.setString(1, nv.getMa());
            statement.setString(2, nv.getHoTen());
            statement.setString(3, nv.getEmail());
            statement.setString(4, nv.getSdt());
            statement.setDate(5, nv.getNgaySinh());
            statement.setString(6, nv.getChucVu());
            statement.setString(7, nv.getDiaChi());
            statement.setDouble(8, nv.getLuong());
            statement.setBoolean(9, nv.isGioiTinh());
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
    public String getTenNVTheoMaHD(String maHD) {
		Connection con = null;
		PreparedStatement statement = null;
		ResultSet rs = null;
		String tenNV  = null;

		try {
			con = ConnectDB.getConnection();
			String sql = "SELECT nv.hoTen FROM NhanVien nv join HoaDon hd on nv.maNV = hd.maNV  WHERE hd.maHD = ?";
			statement = con.prepareStatement(sql);
			statement.setString(1, maHD);
			rs = statement.executeQuery();

			if (rs.next()) {
				tenNV = rs.getString(1);
			}
			if(tenNV == null)
				tenNV = "Khach hang le";
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
		return tenNV;
	}

    public boolean delete(NhanVien nv) {
        ConnectDB.getInstance();
        Connection con = ConnectDB.getConnection();
        PreparedStatement statement = null;
        int n = 0;
        try {
            statement = con.prepareStatement("DELETE FROM NhanVien WHERE maNV = ?");
            statement.setString(1, nv.getMa());
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

    public boolean update(NhanVien nv) {
        ConnectDB.getInstance();
        Connection con = ConnectDB.getConnection();
        PreparedStatement statement = null;
        int n = 0;
        try {
            statement = con.prepareStatement(
                    "UPDATE NhanVien SET hoTen = ?, email = ?, sdt = ?, ngaySinh = ?, chucVu = ?, diaChi = ?, luong = ?, gioiTinh = ? WHERE maNV = ?");
            statement.setString(1, nv.getHoTen());
            statement.setString(2, nv.getEmail());
            statement.setString(3, nv.getSdt());
            statement.setDate(4, nv.getNgaySinh());
            statement.setString(5, nv.getChucVu());
            statement.setString(6, nv.getDiaChi());
            statement.setDouble(7, nv.getLuong());
            statement.setBoolean(8, nv.isGioiTinh());
            statement.setString(9, nv.getMa());
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

    //
}
