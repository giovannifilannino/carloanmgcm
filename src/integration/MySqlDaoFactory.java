package integration;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class MySqlDaoFactory {
	
	private static MySqlDaoFactory dao;
	private static String connessione;
	private static String password;
	private static String username;
	private static Connection con;
	
	private MySqlDaoFactory(){
		connessione="jdbc:mysql://localhost/CarLoanDB";
		password="nicoletta94";
		username="root";
	}

	public static MySqlDaoFactory getIstance() {
		if (dao == null) {
			dao = new MySqlDaoFactory();
		}
		return dao;
	}
	public static Connection connetti() throws SQLException {
		getIstance();
		con = DriverManager.getConnection(connessione, username, password);
		return con;
	}
}
