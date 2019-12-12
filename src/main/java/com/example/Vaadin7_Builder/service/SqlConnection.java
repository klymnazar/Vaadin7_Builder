package com.example.Vaadin7_Builder.service;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class SqlConnection {
	
	public Connection sqlConnection() throws SQLException {
	
		Connection conn = null;
		
		try {
			String url = "jdbc:mysql://localhost/builderdb?serverTimezone=Europe/Moscow&useSSL=false";
			String username = "root";
			String password = "root";
			Class.forName("com.mysql.cj.jdbc.Driver").getDeclaredConstructor().newInstance();
			try {
				conn = DriverManager.getConnection(url, username, password);
				System.out.println("Connection to builberdb succesfull!");
			}
			catch (SQLException ex) {
				System.out.println("Connection to builberdb failed...");
			}
		}
		catch (Exception ex) {
			System.out.println("Connection failed...");
			
			System.out.println(ex);
		}
		return conn;
	}
}