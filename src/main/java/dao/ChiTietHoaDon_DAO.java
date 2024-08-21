package dao;

import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;

import connectDB.ConnectDB;
import entity.ChiTietHoaDon;
import entity.HoaDon;
import entity.KhachHang;
import entity.LoaiSanPham;
import entity.SanPham;

public class ChiTietHoaDon_DAO {
	
	public ChiTietHoaDon_DAO() {

	}

	public ArrayList<ChiTietHoaDon> getAll_CTHD() {
		ArrayList<ChiTietHoaDon> dsCTHD = new ArrayList<ChiTietHoaDon>();
		Connection con = ConnectDB.getInstance().getConnection();
		try {
			Statement st = con.createStatement();
			String sql = "SELECT * FROM ChiTietHoaDon";
			ResultSet rs = st.executeQuery(sql);
			
			while (rs.next()) {
//				ChiTietHoaDon cthd = new ChiTietHoaDon();
				int soLuong = (rs.getInt("soLuong"));
				double thanhTien = (rs.getDouble("thanhTien"));
				double giamGia = (rs.getDouble("tienGiam"));
				String maHD = rs.getString("maHD");
				String maSP = rs.getString("maSP");
				
//				cthd.setHoaDon(new HoaDon(rs.getString("maHD")));
//				cthd.setSanPham(new SanPham(rs.getString("maSP")));
				
				HoaDon hd = new HoaDon(maHD);
				SanPham sp = new SanPham(maSP);
				
				ChiTietHoaDon ctNew = new ChiTietHoaDon(hd, sp);
				ctNew.setSoLuong(soLuong);
				ctNew.setThanhTien(thanhTien);
				ctNew.setTienGiam(giamGia);
				
				
				dsCTHD.add(ctNew);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return dsCTHD;
	}

	public boolean create(ChiTietHoaDon cthd) {
		Connection con = ConnectDB.getInstance().getConnection();
		try {
			Statement st = con.createStatement();
			String sql = "INSERT INTO ChiTietHoaDon VALUES ('"+cthd.getSoLuong() + "', '" + cthd.getThanhTien()
					+ "', '"
					+ cthd.getTienGiam() + "', '" + cthd.getHoaDon().getMaHoaDon() + "', '"
					+ cthd.getSanPham().getMaSP() + "')";
			int rs = st.executeUpdate(sql);
			if (rs == 1) {
				return true;
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return false;
	}
	public ArrayList<String> getAll_CTHDAtmaHD(String maHD) {
	    ArrayList<String> dsCTHD = new ArrayList<>();
	    Connection con = ConnectDB.getInstance().getConnection();
	    PreparedStatement statement = null;
	    try {
	        String sql = "SELECT sp.tenSP AS TenSanPham, dgsp.size AS Size, cthd.soLuong AS SoLuong, dgsp.donGia AS DonGia, (cthd.soLuong * dgsp.donGia) AS ThanhTien " +
	                "FROM ChiTietHoaDon cthd " +
	                "JOIN SanPham sp ON cthd.maSP = sp.maSP " +
	                "JOIN DonGiaSanPham dgsp ON cthd.maSP = dgsp.maSP " +
	                "WHERE cthd.maHD = ?";
	        statement = con.prepareStatement(sql);
	        statement.setString(1, maHD);
	        ResultSet rs = statement.executeQuery(); // Thực thi truy vấn tại đây, không truyền câu lệnh SQL
	        while (rs.next()) {
	            String invoiceDetail = rs.getString("TenSanPham") + "," + rs.getString("Size") +
	                    "," + rs.getInt("SoLuong") +
	                    "," + rs.getDouble("DonGia") +
	                    "," + rs.getDouble("ThanhTien");

	            // Thêm dòng vào danh sách
	            dsCTHD.add(invoiceDetail);
	        }
	    } catch (SQLException e) {
	        e.printStackTrace();
	    } finally {
	        // Đóng tài nguyên
	        try {
	            if (statement != null) statement.close();
	            if (con != null) con.close();
	        } catch (SQLException e) {
	            e.printStackTrace();
	        }
	    }
	    return dsCTHD;
	}
	public int getSoLuongTheoMaHD(String maHD) {
		Connection con = null;
		PreparedStatement statement = null;
		ResultSet rs = null;
		int soluong  = 0;

		try {
			con = ConnectDB.getConnection();
			String sql = " SELECT soLuong FROM ChiTietHoaDon WHERE maHD = ?";
			statement = con.prepareStatement(sql);
			statement.setString(1, maHD);
			rs = statement.executeQuery();
			
			if (rs.next()) {
	            soluong = rs.getInt(1); // Truy cập dữ liệu từ ResultSet
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
		return soluong;
	}
	public double getGiaGiamTheoMaHD(String maHD) {
		Connection con = null;
		PreparedStatement statement = null;
		ResultSet rs = null;
		double giagiam  = 0;

		try {
			con = ConnectDB.getConnection();
			String sql = "SELECT tienGiam FROM ChiTietHoaDon WHERE maHD =  ?";
			statement = con.prepareStatement(sql);
			statement.setString(1, maHD);
			rs = statement.executeQuery();
			giagiam = rs.getInt(1);
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
		return giagiam;
	}
	public double gettongtienTheoMaHD(String maHD) {
		Connection con = null;
		PreparedStatement statement = null;
		ResultSet rs = null;
		double tongtien  = 0;

		try {
			con = ConnectDB.getConnection();
			String sql = "SELECT sum(cthd.soLuong * dgsp.donGia)"
					+ "FROM ChiTietHoaDon cthd"
					+ "     JOIN SanPham sp ON cthd.maSP = sp.maSP"
					+ "     JOIN DonGiaSanPham dgsp ON cthd.maSP = dgsp.maSP"
					+ "WHERE cthd.maHD = ?";
			statement = con.prepareStatement(sql);
			statement.setString(1, maHD);
			rs = statement.executeQuery();
			tongtien = rs.getDouble(1);
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
		return tongtien;
	}


}
