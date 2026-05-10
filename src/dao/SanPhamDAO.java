package dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

import database.DBConnection;
import model.SanPham;

public class SanPhamDAO {

	Connection conn = DBConnection.getConnection();

	public boolean themSanPham(SanPham sp) {

		try {

			String sql = "INSERT INTO SANPHAM " + "VALUES(?,?,?,?)";

			PreparedStatement ps = conn.prepareStatement(sql);

			ps.setString(1, sp.getMaSP());

			ps.setString(2, sp.getTenSP());

			ps.setDouble(3, sp.getGia());

			ps.setInt(4, sp.getSoLuong());

			return ps.executeUpdate() > 0;

		} catch (Exception e) {

			e.printStackTrace();
		}

		return false;
	}

	public boolean suaSanPham(SanPham sp) {

		try {

			String sql = "UPDATE SANPHAM " + "SET TenSP=?, " + "Gia=?, " + "SoLuong=? " + "WHERE MaSP=?";

			PreparedStatement ps = conn.prepareStatement(sql);

			ps.setString(1, sp.getTenSP());

			ps.setDouble(2, sp.getGia());

			ps.setInt(3, sp.getSoLuong());

			ps.setString(4, sp.getMaSP());

			return ps.executeUpdate() > 0;

		} catch (Exception e) {

			e.printStackTrace();
		}

		return false;
	}

	public boolean xoaSanPham(String maSP) {

		try {

			String sql = "DELETE FROM SANPHAM " + "WHERE MaSP=?";

			PreparedStatement ps = conn.prepareStatement(sql);

			ps.setString(1, maSP);

			return ps.executeUpdate() > 0;

		} catch (Exception e) {

			e.printStackTrace();
		}

		return false;
	}

	public ResultSet timKiemSanPham(String keyword) {

		try {

			String sql = "SELECT * FROM SANPHAM " + "WHERE TenSP LIKE ?";

			PreparedStatement ps = conn.prepareStatement(sql);

			ps.setString(1, "%" + keyword + "%");

			return ps.executeQuery();

		} catch (Exception e) {

			e.printStackTrace();
		}

		return null;
	}

	public ResultSet getAllSanPham() {

		try {

			String sql = "SELECT * FROM SANPHAM";

			PreparedStatement ps = conn.prepareStatement(sql);

			return ps.executeQuery();

		} catch (Exception e) {

			e.printStackTrace();
		}

		return null;
	}
	public boolean capNhatSoLuong(
			String maSP,
			int soLuongMoi) {

				try {

					String sql =
					"UPDATE SANPHAM "
					+ "SET SoLuong=? "
					+ "WHERE MaSP=?";

					PreparedStatement ps =
					conn.prepareStatement(sql);

					ps.setInt(
					1,
					soLuongMoi);

					ps.setString(
					2,
					maSP);

					return
					ps.executeUpdate() > 0;

				} catch (Exception e) {

					e.printStackTrace();
				}

				return false;
			}
	
}