package dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;

import connectDB.ConnectDB;
import entity.NhanVien;
import entity.TaiKhoan;

public class TaiKhoan_DAO {

    public TaiKhoan_DAO() {
    }

    public ArrayList<TaiKhoan> getAllTaiKhoan() {
        ArrayList<TaiKhoan> dsTK = new ArrayList<TaiKhoan>();
        try {
            ConnectDB.getInstance();
            Connection con = ConnectDB.getConnection();

            String sql = "SELECT * FROM TaiKhoan";
            Statement statement = con.createStatement();

            ResultSet rs = statement.executeQuery(sql);

            while (rs.next()) {
                String tenTaiKhoan = rs.getString(1);
                String matKhau = rs.getString(2);
                NhanVien nv = new NhanVien(rs.getString(3));

                TaiKhoan tk = new TaiKhoan(tenTaiKhoan, matKhau, nv);
                dsTK.add(tk);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return dsTK;
    }

    public boolean create(TaiKhoan taiKhoan) {
        ConnectDB.getInstance();
        Connection con = ConnectDB.getConnection();
        PreparedStatement statement = null;
        int n = 0;
        try {
            statement = con.prepareStatement("INSERT INTO TaiKhoan VALUES(?, ?, ?)");
            statement.setString(1, taiKhoan.getTenTaiKhoan());
            statement.setString(2, taiKhoan.getMatKhau());
            statement.setString(3, taiKhoan.getNhanVien().getMa());
            n = statement.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            try {
                statement.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
        return n > 0;
    }

}
