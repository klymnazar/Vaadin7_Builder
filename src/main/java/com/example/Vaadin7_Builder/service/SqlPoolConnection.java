package com.example.Vaadin7_Builder.service;

import com.mchange.v2.c3p0.ComboPooledDataSource;

import java.beans.PropertyVetoException;
//import java.io.BufferedReader;
//import java.io.FileReader;
//import java.io.InputStream;
//import java.io.Reader;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;

import javax.naming.InitialContext;
import javax.naming.NamingException;
import javax.sql.DataSource;
//import java.sql.Statement;




public class SqlPoolConnection {

	private static SqlPoolConnection dataSource;
	private ComboPooledDataSource comboPooledDataSource;

//	public Connection poolConnection() throws Exception {

	private SqlPoolConnection() {
		try {
			String driver = "org.h2.Driver";
			String url = "jdbc:h2:mem:builderdb;DB_CLOSE_DELAY=-1;INIT=RUNSCRIPT FROM 'classpath:/scripts/schema.sql'";
			String username = "sa";
			String password = "";
			comboPooledDataSource = new ComboPooledDataSource();
			comboPooledDataSource.setDriverClass(driver);
			comboPooledDataSource.setJdbcUrl(url);
			comboPooledDataSource.setUser(username);
			comboPooledDataSource.setPassword(password);
		} catch (PropertyVetoException ex1) {
			ex1.printStackTrace();
		}

	}

	public static SqlPoolConnection getInstance() {
		if (dataSource == null)
			dataSource = new SqlPoolConnection();
		return dataSource;
	}

	public Connection getConnection() {
		Connection conn = null;
		try {
			conn = comboPooledDataSource.getConnection();
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return conn;
	}


	
	
	
//	public Connection sqlConnection() {
//
//		Connection conn = null;
//		
//		try {
//			
//			//Connection MySQL
////			String url = "jdbc:mysql://localhost/builderdb?serverTimezone=Europe/Kiev&useSSL=false";
////			String username = "root";
////			String password = "root";
////			Class.forName("com.mysql.cj.jdbc.Driver").getDeclaredConstructor().newInstance();
//			
///////////////////////////////////////
//			
//			//Connection H2 in memory
//			
//			
////			String url = "jdbc:h2:mem:builderdb;DB_CLOSE_DELAY=-1;DATABASE_TO_UPPER=false;INIT=RUNSCRIPT FROM 'classpath:/schema.sql'";
////			String url = "jdbc:h2:mem:builderdb;DATABASE_TO_UPPER=false;INIT=RUNSCRIPT FROM 'classpath:/schema.sql'";
////			String url = "jdbc:h2:mem:builderdb;INIT=RUNSCRIPT FROM 'classpath:/schema.sql'";
////			String url = "jdbc:h2:mem:builderdb;DB_CLOSE_DELAY=-1;DATABASE_TO_UPPER=false";
//
//			
//			
////			String url = "jdbc:h2:mem:builderdb;INIT=RUNSCRIPT FROM 'classpath:/scripts/schema.sql'";
//			
//			String url = "jdbc:h2:mem:builderdb;DB_CLOSE_DELAY=-1;INIT=RUNSCRIPT FROM 'classpath:/scripts/schema.sql'";
//			
////			String url = "jdbc:h2:mem:builderdb;DB_CLOSE_DELAY=-1;INIT=CREATE SCHEMA IF NOT EXISTS BUILDERDB\\;RUNSCRIPT FROM 'classpath:/scripts/schema.sql'";
//			
//
//			
//			
////			String url = "jdbc:h2:mem:builderdb;DB_CLOSE_DELAY=-1;DATABASE_TO_UPPER=false;INIT=CREATE SCHEMA IF NOT EXISTS BUILDERDB";
////			String url = "jdbc:h2:mem:builderdb;DB_CLOSE_DELAY=-1;DATABASE_TO_UPPER=false;INIT=CREATE SCHEMA IF NOT EXISTS BUILDERDB\\;RUNSCRIPT FROM 'resources/schema.sql'";
////			String url = "jdbc:h2:mem:builderdb;DB_CLOSE_DELAY=-1;DATABASE_TO_UPPER=false;INIT=RUNSCRIPT FROM 'data.sql'";
//			//		+ "\\;RUNSCRIPT FROM '~/data.sql'";
//
//			
//			//						       jdbc:h2:mem:test;INIT=runscript from '~/create.sql'\\;runscript from '~/init.sql'"
//				//	+ "RUNSCRIPT FROM '//resources/data.sql'";
//			
//			//image/logo-design-30.jpg
//			
////			String url = "jdbc:h2:mem:builderdb;DB_CLOSE_DELAY=-1";
//			String username = "sa";
//			String password = "";
//			Class.forName("org.h2.Driver").getDeclaredConstructor().newInstance();
//			
//
//			
//			
///////////////////////////////////////
//			
//			
//			try {
//				conn = DriverManager.getConnection(url, username, password);			
//				
//				System.out.println("Connection to builberdb succesfull!");
//			} catch (SQLException ex) {
//				System.out.println("Connection to builberdb failed...");
//				System.out.println(ex);
//			}
//		} catch (Exception ex) {
//			System.out.println("Connection failed...");
//			System.out.println(ex);
//		}
//		return conn;
//	}
	
	

	
	
	
	
//	public Connection sqlConnection() {
//
//		Connection conn = null;
//		
//		try {
//			
//			//Connection MySQL
////			String url = "jdbc:mysql://localhost/builderdb?serverTimezone=Europe/Kiev&useSSL=false";
////			String username = "root";
////			String password = "root";
////			Class.forName("com.mysql.cj.jdbc.Driver").getDeclaredConstructor().newInstance();
//			
///////////////////////////////////////
//			
//			//Connection H2 in memory
//			
//			
////			String url = "jdbc:h2:mem:builderdb;DB_CLOSE_DELAY=-1;DATABASE_TO_UPPER=false;INIT=RUNSCRIPT FROM 'classpath:/schema.sql'";
////			String url = "jdbc:h2:mem:builderdb;DATABASE_TO_UPPER=false;INIT=RUNSCRIPT FROM 'classpath:/schema.sql'";
////			String url = "jdbc:h2:mem:builderdb;INIT=RUNSCRIPT FROM 'classpath:/schema.sql'";
////			String url = "jdbc:h2:mem:builderdb;DB_CLOSE_DELAY=-1;DATABASE_TO_UPPER=false";
//
//			
//			
////			String url = "jdbc:h2:mem:builderdb;INIT=RUNSCRIPT FROM 'classpath:/scripts/schema.sql'";
//			
////			String url = "jdbc:h2:mem:builderdb;DB_CLOSE_DELAY=-1;INIT=RUNSCRIPT FROM 'classpath:/scripts/schema.sql'";
//			
//			String url = "jdbc:h2:mem:builderdb;DB_CLOSE_DELAY=-1;INIT=CREATE SCHEMA IF NOT EXISTS BUILDERDB\\;RUNSCRIPT FROM 'classpath:/scripts/schema.sql'";
//			
//			
//			
//			
////			String url = "jdbc:h2:mem:builderdb;DB_CLOSE_DELAY=-1;DATABASE_TO_UPPER=false;INIT=CREATE SCHEMA IF NOT EXISTS BUILDERDB";
////			String url = "jdbc:h2:mem:builderdb;DB_CLOSE_DELAY=-1;DATABASE_TO_UPPER=false;INIT=CREATE SCHEMA IF NOT EXISTS BUILDERDB\\;RUNSCRIPT FROM 'resources/schema.sql'";
////			String url = "jdbc:h2:mem:builderdb;DB_CLOSE_DELAY=-1;DATABASE_TO_UPPER=false;INIT=RUNSCRIPT FROM 'data.sql'";
//			//		+ "\\;RUNSCRIPT FROM '~/data.sql'";
//
//			
//			//						       jdbc:h2:mem:test;INIT=runscript from '~/create.sql'\\;runscript from '~/init.sql'"
//				//	+ "RUNSCRIPT FROM '//resources/data.sql'";
//			
//			//image/logo-design-30.jpg
//			
////			String url = "jdbc:h2:mem:builderdb;DB_CLOSE_DELAY=-1";
//			String username = "sa";
//			String password = "";
//			Class.forName("org.h2.Driver").getDeclaredConstructor().newInstance();
//			
//
//			
//			
///////////////////////////////////////
//			
//			
//			try {
//				conn = DriverManager.getConnection(url, username, password);			
//				
//				System.out.println("Connection to builberdb succesfull!");
//			} catch (SQLException ex) {
//				System.out.println("Connection to builberdb failed...");
//				System.out.println(ex);
//			}
//		} catch (Exception ex) {
//			System.out.println("Connection failed...");
//			System.out.println(ex);
//		}
//		return conn;
//	}
}