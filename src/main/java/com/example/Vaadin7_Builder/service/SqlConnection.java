package com.example.Vaadin7_Builder.service;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.InputStream;
import java.io.Reader;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;



public class SqlConnection {

	public Connection sqlConnection() throws SQLException {

		Connection conn = null;

		try {
//			String url = "jdbc:mysql://localhost/builderdb?serverTimezone=Europe/Kiev&useSSL=false";
//			String username = "root";
//			String password = "root";
//			Class.forName("com.mysql.cj.jdbc.Driver").getDeclaredConstructor().newInstance();
			
/////////////////////////////////////			
//			String url = "jdbc:h2:mem:builderdb;DB_CLOSE_DELAY=-1;DATABASE_TO_UPPER=false;INIT=RUNSCRIPT FROM 'classpath:/schema.sql'";
//			String url = "jdbc:h2:mem:builderdb;DATABASE_TO_UPPER=false;INIT=RUNSCRIPT FROM 'classpath:/schema.sql'";
//			String url = "jdbc:h2:mem:builderdb;INIT=RUNSCRIPT FROM 'classpath:/schema.sql'";
//			String url = "jdbc:h2:mem:builderdb;DB_CLOSE_DELAY=-1;DATABASE_TO_UPPER=false";
			String url = "jdbc:h2:mem:builderdb;INIT=RUNSCRIPT FROM 'classpath:/scripts/schema.sql'";
			
//			String url = "jdbc:h2:mem:builderdb;DB_CLOSE_DELAY=-1;DATABASE_TO_UPPER=false;INIT=CREATE SCHEMA IF NOT EXISTS BUILDERDB";
//			String url = "jdbc:h2:mem:builderdb;DB_CLOSE_DELAY=-1;DATABASE_TO_UPPER=false;INIT=CREATE SCHEMA IF NOT EXISTS BUILDERDB\\;RUNSCRIPT FROM 'resources/schema.sql'";
//			String url = "jdbc:h2:mem:builderdb;DB_CLOSE_DELAY=-1;DATABASE_TO_UPPER=false;INIT=RUNSCRIPT FROM 'data.sql'";
			//		+ "\\;RUNSCRIPT FROM '~/data.sql'";

			
			//						       jdbc:h2:mem:test;INIT=runscript from '~/create.sql'\\;runscript from '~/init.sql'"
				//	+ "RUNSCRIPT FROM '//resources/data.sql'";
			
			//image/logo-design-30.jpg
			
//			String url = "jdbc:h2:mem:builderdb;DB_CLOSE_DELAY=-1";
			String username = "sa";
			String password = "";
			Class.forName("org.h2.Driver").getDeclaredConstructor().newInstance();
			
//			// schema init
//		    Resource initSchema = new ClassPathResource("scripts/schema-h2.sql");
//		    Resource initData = new ClassPathResource("scripts/data-h2.sql");
//		    DatabasePopulator databasePopulator = new ResourceDatabasePopulator(initSchema, initData);
//		    DatabasePopulatorUtils.execute(databasePopulator, dataSource);

			// schema init
//		    Resource initSchema = new ClasspathResource("scripts/schema-h2.sql");
//		    Resource initData = new ClassPathResource("scripts/data-h2.sql");
//		    DatabasePopulator databasePopulator = new ResourceDatabasePopulator(initSchema, initData);
//		    DatabasePopulatorUtils.execute(databasePopulator, dataSource);
			
//			InputStream in = getClass().getResourceAsStream("schema.sql");
//			RunScript.execute(conn, new InputStreamReader(in));
			
			InputStream inputStream = getClass()
					.getClassLoader().getResourceAsStream("schema.sql");
			
			
/////////////////////////////////////
			
			
			try {
				conn = DriverManager.getConnection(url, username, password);
				
/////////////////////////////////////					


				
				
				
/////////////////////////////////////				
				
				System.out.println("Connection to builberdb succesfull!");
			} catch (SQLException ex) {
				System.out.println("Connection to builberdb failed...");
				System.out.println(ex);
			}
		} catch (Exception ex) {
			System.out.println("Connection failed...");

			System.out.println(ex);
		}
		return conn;
	}
}