package dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

import database.DBConnection;
import model.ChiTietHoaDon;

public class ChiTietHoaDonDAO {

	Connection conn =
	DBConnection.getConnection();

	public boolean themChiTietHoaDon(
	ChiTietHoaDon cthd) {

		try {

			String sql =

			"INSERT INTO "
			+ "CHITIETHOADON "
			+ "VALUES(?,?,?,?,?)";

			PreparedStatement ps =

			conn.prepareStatement(sql);

			ps.setString(
			1,
			cthd.getMaHD());

			ps.setString(
			2,
			cthd.getMaSP());

			ps.setInt(
			3,
			cthd.getSoLuong());

			ps.setDouble(
			4,
			cthd.getDonGia());

			ps.setDouble(
			5,
			cthd.getThanhTien());

			return
			ps.executeUpdate() > 0;

		} catch (Exception e) {

			e.printStackTrace();
		}

		return false;
	}

	public ResultSet getChiTietHoaDon(
	String maHD) {

		try {

			String sql =

			"SELECT "

			+ "ct.MaHD, "

			+ "sp.MaSP, "

			+ "sp.TenSP, "

			+ "ct.SoLuong, "

			+ "ct.DonGia, "

			+ "ct.ThanhTien "

			+ "FROM CHITIETHOADON ct "

			+ "JOIN SANPHAM sp "

			+ "ON ct.MaSP = sp.MaSP "

			+ "WHERE ct.MaHD=?";

			PreparedStatement ps =

			conn.prepareStatement(sql);

			ps.setString(
			1,
			maHD);

			return ps.executeQuery();

		} catch (Exception e) {

			e.printStackTrace();
		}

		return null;
	}
}