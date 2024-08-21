package dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;

import connectDB.ConnectDB;
import entity.LoaiSanPham;

public class LoaiSanPham_DAO {
	public LoaiSanPham_DAO() {

	}

	public ArrayList<LoaiSanPham> getAllLoaiSP() {
		ArrayList<LoaiSanPham> dsLoaiSP = new ArrayList<LoaiSanPham>();
		try {
			ConnectDB.getInstance();
			Connection con = ConnectDB.getConnection();

			String sql = "SELECT * FROM LoaiSanPham";
			Statement statement = con.createStatement();

			ResultSet rs = statement.executeQuery(sql);

			while (rs.next()) {
				String maLoai = rs.getString(1);
				String tenLoai = rs.getString(2);
				LoaiSanPham loai = new LoaiSanPham(maLoai, tenLoai);
				dsLoaiSP.add(loai);
			}

		} catch (SQLException e) {
			e.printStackTrace();
		}
		return dsLoaiSP;
	}

	// Lấy tên loại theo mã loại
	public String getTenLoaiSP(String maLoai) {
		String tenLoai = "";
		try {
			ConnectDB.getInstance();
			Connection con = ConnectDB.getConnection();

			String sql = "SELECT TenLoai FROM LoaiSanPham WHERE MaLoai = ?";
			PreparedStatement statement = con.prepareStatement(sql);
			statement.setString(1, maLoai);

			ResultSet rs = statement.executeQuery();

			if (rs.next()) {
				tenLoai = rs.getString(1);
			}

		} catch (SQLException e) {
			e.printStackTrace();
		}
		return tenLoai;
	}

}
