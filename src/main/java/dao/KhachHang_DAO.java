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
import entity.HoaDon;
import entity.KhachHang;

public class KhachHang_DAO {
	public ArrayList<KhachHang> getAllKhachHang() {
		ArrayList<KhachHang> dsKH = new ArrayList<KhachHang>();
		try {
			ConnectDB.getInstance();
			Connection con = ConnectDB.getConnection();

			String sql = "Select * from KhachHang order by maKH";
			Statement statement = con.createStatement();

			ResultSet rs = statement.executeQuery(sql);

			while (rs.next()) {
				String makh = rs.getString(1);
				String tenkh = rs.getString(2);
				String sdt = rs.getString(3);
				int diemTL = rs.getInt(4);

				KhachHang kh = new KhachHang(makh, tenkh, sdt);
				kh.setDiemTL(diemTL);
				dsKH.add(kh);
			}

		} catch (SQLException e) {
			e.printStackTrace();
		}
		return dsKH;
	}

	public boolean create(KhachHang kh) {
		ConnectDB.getInstance();
		Connection con = ConnectDB.getConnection();
		PreparedStatement statement = null;
		int n = 0;
		try {
			statement = con.prepareStatement("INSERT INTO KhachHang VALUES(?, ?, ?, ?)");
			statement.setString(1, kh.getMaKH());
			statement.setString(2, kh.getTenKH());
			statement.setString(3, kh.getSdt());
			statement.setInt(4, kh.getDiemTL());
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

	public boolean update(KhachHang kh) {
		ConnectDB.getInstance();
		Connection con = ConnectDB.getConnection();
		PreparedStatement statement = null;
		int n = 0;
		try {
			statement = con.prepareStatement("update KhachHang set maKH = ?"
					+ ", tenKH  = ?, diemTL = ?"
					+ " where sdtKH = ?");
			statement.setString(1, kh.getMaKH());
			statement.setString(2, kh.getTenKH());
			// statement.setInt(3, kh.getDiemTL());
			statement.setString(4, kh.getSdt());

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

	public boolean delete(String sdt) {
		ConnectDB.getInstance();
		Connection con = ConnectDB.getConnection();
		PreparedStatement statement = null;
		int n = 0;
		try {
			statement = con.prepareStatement("DELETE FROM KhachHang WHERE sdt = ?");
			statement.setString(1, sdt);
			n = statement.executeUpdate();
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
		return n > 0;
	}

	public KhachHang searchKhachHangTheoSdtKH(String dt) {
		Connection con = null;
		PreparedStatement statement = null;
		ResultSet rs = null;
		KhachHang kh = null;

		try {
			con = ConnectDB.getConnection();
			String sql = "SELECT * FROM KhachHang WHERE sdt =?";
			statement = con.prepareStatement(sql);
			statement.setString(1, dt);
			rs = statement.executeQuery();

			// Nếu tìm thấy nhân viên, tạo đối tượng NhanVien và gán giá trị
			if (rs.next()) {
				String maKH = rs.getString(1);
				String tenKH = rs.getString(2);
				String sdt = rs.getString(3);
				int diemTL = rs.getInt(4);

				kh = new KhachHang(maKH, tenKH, sdt);
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
		return kh;
	}

	public boolean kiemtraMa(String dt) {
		ConnectDB.getInstance();
		Connection con = ConnectDB.getConnection();
		PreparedStatement statement = null;
		ResultSet rs = null;
		try {
			statement = con.prepareStatement("SELECT COUNT(*) FROM KhachHang WHERE sdt = ?");
			statement.setString(1, dt);
			rs = statement.executeQuery();
			if (rs.next()) {
				int count = rs.getInt(1);
				return count > 0;// mã nhân viên đã tồn tại
			}
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			try {
				if (rs != null) {
					rs.close();
				}
				if (statement != null) {
					statement.close();
				}
			} catch (SQLException e2) {
				e2.printStackTrace();
			}
		}
		return false;

	}

	public KhachHang getKhachHangBySDT(String sdt) {
		Connection con = null;
		PreparedStatement statement = null;
		ResultSet rs = null;
		KhachHang kh = null;
		try {
			con = ConnectDB.getConnection();
			String sql = "EXEC GetKhachHangBYSDT @sdt = ?";
			statement = con.prepareStatement(sql);
			statement.setString(1, sdt);
			rs = statement.executeQuery();
			if (rs.next()) {
				String maKH = rs.getString(1);
				String tenKH = rs.getString(2);
				String sdtKH = rs.getString(3);
				int diemTL = rs.getInt(4);
				kh = new KhachHang(maKH, tenKH, sdtKH);
				kh.setDiemTL(diemTL);
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
		return kh;
	}

	public boolean updateKhachHang(KhachHang kh) {
		ConnectDB.getInstance();
		Connection con = ConnectDB.getConnection();
		PreparedStatement statement = null;
		int n = 0;
		try {
			statement = con.prepareStatement("UPDATE KhachHang SET tenKH = ?, sdt = ?, diemTL = ? WHERE maKH = ?");
			statement.setString(1, kh.getTenKH());
			statement.setString(2, kh.getSdt());
			statement.setInt(3, kh.getDiemTL());
			statement.setString(4, kh.getMaKH());

			n = statement.executeUpdate();
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
		return n > 0;
	}
	public String getTenTheoMaHD(String maHD) {
		Connection con = null;
		PreparedStatement statement = null;
		ResultSet rs = null;
		String tenKH  = null;

		try {
			con = ConnectDB.getConnection();
			String sql = "SELECT kh.tenKH FROM KhachHang kh join HoaDon hd on kh.maKH = hd.maKH  WHERE hd.maHD = ?";
			statement = con.prepareStatement(sql);
			statement.setString(1, maHD);
			rs = statement.executeQuery();

			if (rs.next()) {
				tenKH = rs.getString(1);
			}
			if(tenKH == null)
				tenKH = "Khach hang le";
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
		return tenKH;
	}
	public ArrayList<HoaDon> getKhachHangTop() {
		ArrayList<HoaDon> dsHoaDon = new ArrayList<HoaDon>();
		try {
			ConnectDB.getInstance();
			Connection con = ConnectDB.getConnection();

			// Gọi thủ tục từ Java
			CallableStatement cs = con.prepareCall("{CALL GetKhachHangTop}");

			// Thực thi thủ tục
			ResultSet rs = cs.executeQuery();

			while (rs.next()) {
				// Lấy thông tin từ ResultSet
				String maKH = rs.getString("maKH");
				String tenKH = rs.getString("tenKH");
				Date ngayLapHD = rs.getDate("ngayLapHD");
				double tongThanhToan = rs.getDouble("tongThanhToan");

				// Tạo đối tượng KhachHang
				KhachHang khachHang = new KhachHang();
				khachHang.setMaKH(maKH);
				khachHang.setTenKH(tenKH);

				// Tạo đối tượng HoaDon và gán thông tin khách hàng
				HoaDon hoaDon = new HoaDon();
				hoaDon.setKhachHang(khachHang);
				hoaDon.setNgayLapHD(ngayLapHD);
				hoaDon.setTongThanhToan(tongThanhToan);

				// Thêm vào danh sách
				dsHoaDon.add(hoaDon);
			}

			// Đóng ResultSet và CallableStatement
		} catch (Exception e) {
			e.printStackTrace();
		}
		return dsHoaDon;
	}

}
