package dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

import database.DBConnection;

public class HoaDonDAO {

	Connection conn =
	DBConnection.getConnection();

	public String taoMaHD() {

		try {

			String sql =

			"SELECT TOP 1 MaHD "
			+ "FROM HOADON "
			+ "ORDER BY MaHD DESC";

			PreparedStatement ps =

			conn.prepareStatement(sql);

			ResultSet rs =
			ps.executeQuery();

			if(rs.next()) {

				String maHD =
				rs.getString("MaHD");

				int so =

				Integer.parseInt(
				maHD.substring(2));

				so++;

				return String.format(
				"HD%02d",
				so);
			}

		} catch (Exception e) {

			e.printStackTrace();
		}

		return "HD01";
	}

	public boolean themHoaDon(
			String maHD,
			double tongTien) {

				try {

					String sql =

					"INSERT INTO HOADON "
					+ "(MaHD,NgayLap,TongTien) "
					+ "VALUES(?,?,?)";

					PreparedStatement ps =

					conn.prepareStatement(sql);

					ps.setString(
					1,
					maHD);

					ps.setDate(
					2,
					new java.sql.Date(
					System.currentTimeMillis()));

					ps.setDouble(
					3,
					tongTien);

					return
					ps.executeUpdate() > 0;

				} catch (Exception e) {

					e.printStackTrace();
				}

				return false;
			}
}