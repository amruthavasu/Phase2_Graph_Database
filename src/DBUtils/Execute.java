package DBUtils;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;



public class Execute {
	public static Connection createConnection() {
		Connection con = null;
		try {
			con = DriverManager.getConnection(DBProperties.CONNECTION_STRING, DBProperties.USERNAME,
					DBProperties.PASSWORD);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return con;
	}
	
	public static List<String> executeQuery(String query, String returnType) {
		List<String> results = new ArrayList<String>();
		try {
			ResultSet resultSet = createConnection().createStatement().executeQuery(query);
			while (resultSet.next()) {
				results.add(resultSet.getString(returnType));
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return results;
	}
	
	public static String execute(String query, String returnType) {
		try {
			ResultSet resultSet = createConnection().createStatement().executeQuery(query);
			while (resultSet.next()) {
				return (resultSet.getString(returnType));
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}
}
