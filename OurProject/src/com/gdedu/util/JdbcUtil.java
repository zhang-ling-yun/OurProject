package com.gdedu.util;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
public class JdbcUtil {
	private static String driver = "oracle.jdbc.driver.OracleDriver";
	private static String url = "jdbc:oracle:thin:@localhost:1521:MLDN";
	private static String userName = "school";
	private static String password = "school";
	static{
		try {			
			Class.forName(driver);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	public static Connection getConnection() throws SQLException {		
		return DriverManager.getConnection(url, userName, password);
	}	
	public static void free(Connection conn,Statement statement,ResultSet rs) throws SQLException{
		if (conn!=null) {
			conn.close();
		}
		if (statement!=null) {
			statement.close();
		}
		if (rs!=null) {
			rs.close();
		}
	}
}
