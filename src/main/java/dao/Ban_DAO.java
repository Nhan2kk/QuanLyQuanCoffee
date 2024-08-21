package dao;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.sql.PreparedStatement;

import connectDB.ConnectDB;
import entity.Ban;
import entity.HoaDon;
import entity.KhachHang;

public class Ban_DAO {
	public Ban_DAO() {
	}

	public ArrayList<Ban> getAllBan() {
		ArrayList<Ban> dsBan = new ArrayList<Ban>();
		try {
			ConnectDB.getInstance();
			Connection con = ConnectDB.getConnection();

			String sql = "SELECT * FROM Ban";
			Statement statement = con.createStatement();

			ResultSet rs = statement.executeQuery(sql);

			while (rs.next()) {
				String maBan = rs.getString(1);
				String tenBan = rs.getString(2);
				HoaDon hoaDon = new HoaDon();

				Ban ban = new Ban(maBan, tenBan, hoaDon);
				dsBan.add(ban);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return dsBan;
	}

	public boolean create(Ban ban) {
		ConnectDB.getInstance();
		Connection con = ConnectDB.getConnection();
		PreparedStatement statement = null;
		int n = 0;
		try {
			statement = con.prepareStatement("INSERT INTO Ban VALUES(?, ?, ?)");
			statement.setString(1, autoGenerateID());
			statement.setString(2, ban.getTenBan());
			statement.setString(3, ban.getHoaDon().getMaHoaDon());
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
	
	public Ban searchBanTheomaHD(String maHD) {
		Connection con = null;
		PreparedStatement statement = null;
		ResultSet rs = null;
		Ban ban = null;

		try {
			con = ConnectDB.getConnection();
			String sql = "SELECT * FROM Ban WHERE maHD =?";
			statement = con.prepareStatement(sql);
			statement.setString(1, maHD);
			rs = statement.executeQuery();

			// Nếu tìm thấy nhân viên, tạo đối tượng NhanVien và gán giá trị
			if (rs.next()) {
				String maBan = rs.getString(1);
				String tenBan = rs.getString(2);
				HoaDon hd = new HoaDon(rs.getString(3));

				ban = new Ban(maBan, tenBan, hd);
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
		return ban;
	}

	// mã bàn tự sinh theo số thứ tự 1, 2, 3
//	public String autoGenerateID() {
//		ArrayList<Ban> dsBan = getAllBan();
//		int max = 0;
//		for (Ban ban : dsBan) {
//			int id = Integer.parseInt(ban.getMaBan().substring(2));
//			if (id > max) {
//				max = id;
//			}
//		
//		}
//		return "B" + (max + 1);
//	}
	
	public String autoGenerateID() {
	    ArrayList<Ban> dsBan = getAllBan();
	    int max = 0;
	    for (Ban ban : dsBan) {
	        String maBan = ban.getMaBan();
	        if (maBan != null && maBan.length() >= 2) {
	            try {
	                int id = Integer.parseInt(maBan.substring(1));
	                if (id > max) {
	                    max = id;
	                }
	            } catch (NumberFormatException e) {
	                e.printStackTrace();
	            }
	        }
	    }
	    return "B" + (max + 1);
	}


}
