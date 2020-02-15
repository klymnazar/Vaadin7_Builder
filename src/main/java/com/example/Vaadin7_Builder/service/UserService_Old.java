//package com.example.Vaadin7_Builder.service;
//
//import java.sql.Connection;
//import java.sql.ResultSet;
//import java.sql.ResultSetMetaData;
//import java.sql.SQLException;
//import java.sql.Statement;
//import java.util.ArrayList;
//import java.util.List;
//
//import com.example.Vaadin7_Builder.model.User;
//
//public class UserService_Old {
//	
//	SqlConnection sqlConnection = new SqlConnection();
//	Connection conn = sqlConnection.sqlConnection();
//	
//	public List<User> getUsers() throws SQLException {
//		
//		List<User> userList = new ArrayList<>();
//		
////		SqlConnection sqlConnection = new SqlConnection();
////		Connection conn = sqlConnection.sqlConnection();
//		
//		String sql = "SELECT * FROM userTable";
//		
////		System.out.println(sql);
//		Statement statement = conn.createStatement();
//		
//		ResultSet rs = statement.executeQuery(sql);
//		
//		ResultSetMetaData md = rs.getMetaData();
//		int cnt = md.getColumnCount();
//		
//		int row = 0;
//				
//		while (rs.next()) {
//			row++;
//			User user = new User();
//			for (int i = 1; i <= cnt; i++) {
//				user.setIduserTable(rs.getInt(1));
//				user.setUserFirstName(rs.getString(2));
//				user.setUserLastName(rs.getString(3));
//				user.setUserName(rs.getString(4));
//				user.setUserPassword(rs.getString(5));
//				user.setUserMail(rs.getString(6));
//				user.setUserPhone(rs.getString(7));
//			}
//			userList.add(user);
//		}
//		
//		conn.close();
//		
//		return userList;
//	}
//	
//
//	public void createUser(User user) throws SQLException {
//		
//		
//		String userPhone = user.getUserPhone();
//		String userFirstName = user.getUserFirstName();
//		String userLastName = user.getUserLastName(); 
//		String userName = user.getUserName();
//		String userPassword = user.getUserPassword();
//		String userMail = user.getUserMail();
//		
////		SqlConnection sqlConnection = new SqlConnection();
////		Connection conn = sqlConnection.sqlConnection();
//		
//		String sql = "INSERT INTO userTable (userFirstName, userLastName, userName, userPassword, userMail, userPhone)"
//     			 + " VALUES ('" + userFirstName + "', '" + userLastName + "', '" + userName + "',"
//     			 	    + " '" + userPassword + "', '" + userMail + "', " + userPhone + ")";
//
////		System.out.println(sql);
//		Statement statement = conn.createStatement();
//		statement.executeUpdate(sql);
//		
//		conn.close();
//	}
//	
//	
//	public User getUserById(int id) throws SQLException {
//		
//		User user = new User();
//
////		SqlConnection sqlConnection = new SqlConnection();
////		Connection conn = sqlConnection.sqlConnection();
//		
//		String sql = "SELECT * FROM usertable WHERE iduserTable='" + id + "'";
//				
////		System.out.println(sql);
//		Statement statement = conn.createStatement();
//		ResultSet rs = statement.executeQuery(sql);
//		
//		ResultSetMetaData md = rs.getMetaData();
//		int cnt = md.getColumnCount();
//		
//		while (rs.next()) {
//
//
//			for (int i = 1; i <= cnt; i++) {
//				user.setIduserTable(rs.getInt(1));
//				user.setUserFirstName(rs.getString(2));
//				user.setUserLastName(rs.getString(3));
//				user.setUserName(rs.getString(4));
//				user.setUserPassword(rs.getString(5));
//				user.setUserMail(rs.getString(6));
//				user.setUserPhone(rs.getString(7));
//			}
//		}
//		
//		conn.close();
//		
//		return user;
//	}
//	
//	
//	public User getUserByUserName(String userName) throws SQLException {
//		
//		User user = new User();
//
////		SqlConnection sqlConnection = new SqlConnection();
////		Connection conn = sqlConnection.sqlConnection();
//		
//		String sql = "SELECT * FROM usertable WHERE userName='" + userName + "'";
//				
////		System.out.println(sql);
//		Statement statement = conn.createStatement();
//		ResultSet rs = statement.executeQuery(sql);
//		
//		ResultSetMetaData md = rs.getMetaData();
//		int cnt = md.getColumnCount();
//		
//		while (rs.next()) {
//
//
//			for (int i = 1; i <= cnt; i++) {
//				user.setIduserTable(rs.getInt(1));
//				user.setUserFirstName(rs.getString(2));
//				user.setUserLastName(rs.getString(3));
//				user.setUserName(rs.getString(4));
//				user.setUserPassword(rs.getString(5));
//				user.setUserMail(rs.getString(6));
//				user.setUserPhone(rs.getString(7));
//			}
//		}
//		
//		conn.close();
//		
//		return user;
//	}
//	
//	
//	public void deleteUser(User user) throws SQLException {
//		//by id or username
//		
////		int idUser = 13;
//		String userName = user.getUserName();
//		
////		SqlConnection sqlConnection = new SqlConnection();
////		Connection conn = sqlConnection.sqlConnection();
//				
//		String sql = "DELETE FROM usertable WHERE userName='" + userName + "'";
//		
////		System.out.println(sql);
//		Statement statement = conn.createStatement();
//		statement.executeUpdate(sql);
//		
//		conn.close();
//	}
//	
//	
//	public void updateUser(User user) throws SQLException {
//		//by username (id)
//		
////		int idUser = user.getIduserTable();
//		String userName = user.getUserName();
//		String userFirstName = user.getUserFirstName();
//		String userLastName = user.getUserLastName();
//		String userPassword = user.getUserPassword();
//		String userMail = user.getUserMail();
//		String userPhone = user.getUserPhone();
//		
////		SqlConnection sqlConnection = new SqlConnection();
////		Connection conn = sqlConnection.sqlConnection();
//		
//		String sql = "UPDATE usertable SET userFirstName='" + userFirstName + "', userLastName='" + userLastName + "',"
//										+ " userPassword='" + userPassword + "', userMail='" + userMail + "',"
//										+ " userPhone=" + userPhone + " WHERE userName='" + userName + "'";
//		
////		String sql = "UPDATE usertable SET userFirstName='" + userFirstName + "' WHERE iduserTable='" + idUser + "'";
//		
////		System.out.println(sql);
//		Statement statement = conn.createStatement();
//		statement.executeUpdate(sql);
//		
//		conn.close();
//	}
//	
//	///////////////////
//	
//	public int countUser() throws SQLException {
//		
////		SqlConnection sqlConnection = new SqlConnection();
////		Connection conn = sqlConnection.sqlConnection();
//		
//		String sql = "SELECT COUNT(iduserTable) FROM usertable";
//		
////		System.out.println(sql);
//		Statement statement = conn.createStatement();
//		ResultSet rs = statement.executeQuery(sql);
//		
//		int val = 0;
//		
//		while (rs.next()) {
//			val = rs.getInt(1);
//		}
//		conn.close();
//		return val;
//	}
//
//	
//	public List<Integer> getIdUserTable() throws SQLException {
//		
//		List<Integer> idUserTableList = new ArrayList<>();
//		
////		SqlConnection sqlConnection = new SqlConnection();
////		Connection conn = sqlConnection.sqlConnection();
//		
//		String sql = "SELECT iduserTable FROM userTable";
//		
////		System.out.println(sql);
//		Statement statement = conn.createStatement();
//		ResultSet rs = statement.executeQuery(sql);
//
//		int row = 0;
//				
//		while (rs.next()) {
//			row++;
//			idUserTableList.add(rs.getInt(1));
//		}
//		conn.close();
//		return idUserTableList;
//	}
//	
//	
//	//not Test
//	
//	public boolean isUser(String userName) throws SQLException {
//		
////		List<User> userList = new ArrayList<>();
//		
////		SqlConnection sqlConnection = new SqlConnection();
////		Connection conn = sqlConnection.sqlConnection();
//		
//		String sql = "SELECT userName FROM userTable";
//		
//		Statement statement = conn.createStatement();
//		
//		ResultSet rs = statement.executeQuery(sql);
//		
//		ResultSetMetaData md = rs.getMetaData();
//		int cnt = md.getColumnCount();
//		
//		int row = 0;
//				
//		while (rs.next()) {
//			row++;
//			User user = new User();
//			for (int i = 1; i <= cnt; i++) {
////				user.setIduserTable(rs.getInt(1));
////				user.setUserFirstName(rs.getString(2));
////				user.setUserLastName(rs.getString(3));
////				user.setUserName(rs.getString(4));
////				user.setUserPassword(rs.getString(5));
////				user.setUserMail(rs.getString(6));
////				user.setUserPhone(rs.getString(7));
//				
//				user.setUserName(rs.getString(1));
//				
//				if (user.getUserName().equals(userName))
//					{
//					conn.close();
//					return true;
//					}
//				
//
//				
//				
//				
//			}
////			userList.add(user);
//		}
//		
//		conn.close();
//		
////		return userList;
//		
//		
//		
//		return false;
//	}
//	
//	
//	
//	
//	
//	
//	
//	
//	
//	
//	
//	public void printAllDB() throws SQLException {
//		
////		SqlConnection sqlConnection = new SqlConnection();
////		Connection conn = sqlConnection.sqlConnection();
//		
//		String sql = "SELECT * FROM usertable";
//				
//		System.out.println(sql);
//		Statement statement = conn.createStatement();
//		ResultSet rs = statement.executeQuery(sql);
//		
//		ResultSetMetaData md = rs.getMetaData();
//		int cnt = md.getColumnCount();
//		
//		int row = 0;
//		
//		while (rs.next()) {
//			row++;
//			System.out.println(row);
//			for (int i = 1; i <= cnt; i++) {
//				String name = md.getColumnName(i);
//				String val = rs.getString(i);
//				System.out.println(name + " = " + val);
//			}
//		}
//		
//		conn.close();
//	}	
//	
//	
//	public void printByIdDB(int id) throws SQLException {
//		
////		SqlConnection sqlConnection = new SqlConnection();
////		Connection conn = sqlConnection.sqlConnection();
//		
//		String sql = "SELECT * FROM usertable WHERE iduserTable='" + id + "'";
//				
//		System.out.println(sql);
//		Statement statement = conn.createStatement();
//		ResultSet rs = statement.executeQuery(sql);
//		
//		ResultSetMetaData md = rs.getMetaData();
//		int cnt = md.getColumnCount();
//		
//		while (rs.next()) {
//			for (int i = 1; i <= cnt; i++) {
//				String name = md.getColumnName(i);
//				String val = rs.getString(i);
//				System.out.println(name + " = " + val);
//			}
//		}
//		
//		conn.close();
//	}	
//	
//	
//	
//	
//
//}
