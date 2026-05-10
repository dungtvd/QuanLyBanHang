package database;

import java.sql.Connection;
import java.sql.DriverManager;

public class DBConnection {

	public static Connection getConnection() {

		Connection conn = null;

		try {

			Class.forName("com.microsoft.sqlserver.jdbc.SQLServerDriver");

			String url = "jdbc:sqlserver://localhost:1433;" + "databaseName=QuanLyBanHang;" + "encrypt=true;"
					+ "trustServerCertificate=true";

			String user = "dung";

			String password = "123456";

			conn = DriverManager.getConnection(url, user, password);

		} catch (Exception e) {

			e.printStackTrace();
		}

		return conn;
	}
}