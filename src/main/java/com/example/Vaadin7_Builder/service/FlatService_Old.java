package com.example.Vaadin7_Builder.service;
//package com.example.Vaadin7_Builder.service;
//
//import java.sql.Connection;
//import java.sql.ResultSet;
//import java.sql.ResultSetMetaData;
//import java.sql.SQLException;
//import java.sql.Statement;
//import java.text.DateFormat;
//import java.text.SimpleDateFormat;
//import java.util.ArrayList;
//import java.util.Date;
//import java.util.List;
//
//import com.example.Vaadin7_Builder.model.Flat;
//
//public class FlatService {
//
//	SqlConnection sqlConnection = new SqlConnection();
//	Connection conn = sqlConnection.sqlConnection();
//
//
//	
//	
//	public void createFlat(Flat flat) throws SQLException {
//
//		String buildingCorps = flat.getBuildingCorps();
//		int flatFloor = flat.getFlatFloor();
//		int flatNumber = flat.getFlatNumber();
//		double flatArea = flat.getFlatArea();
//		Object flatRooms = flat.getFlatRooms();
//		String flatSet = "";
//
////		SqlConnection sqlConnection = new SqlConnection();
////		Connection conn = sqlConnection.sqlConnection();
//
//		String sql = "INSERT INTO flatTable (buildingCorps, flatFloor, flatNumber, flatArea, flatRooms, flatSet) VALUES ('"
//				+ buildingCorps + "'," + flatFloor + ", " + flatNumber + ", " + flatArea + ", " + flatRooms + ", '"
//				+ flatSet + "')";
//
////		System.out.println(sql);
//		Statement statement = conn.createStatement();
//		statement.executeUpdate(sql);
//
//		//		conn.close();
//
//	
//		
//	}
//
//	public void createBuyerFlat(Flat buyerFlat) throws SQLException {
//
//		DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
//
//		int idflatTable = buyerFlat.getIdFlatTable();
//		String flatBuyerFirstname = buyerFlat.getFlatBuyerFirstname();
//		String flatBuyerLarstname = buyerFlat.getFlatBuyerLastname();
//		String flatBuyerSurname = buyerFlat.getFlatBuyerSurname();
//		String flatContractNumber = buyerFlat.getFlatContractNumber();
//		String flatContractDate = dateFormat.format(buyerFlat.getFlatContractDate());
//		int flatCost = buyerFlat.getFlatCost();
//		String flatSellerName = buyerFlat.getFlatSellerName();
//		String flatNotes = buyerFlat.getFlatNotes();
//
////		SqlConnection sqlConnection = new SqlConnection();
////		Connection conn = sqlConnection.sqlConnection();
//
//		String sql = "INSERT INTO flatbuyerTable (idflatTable, flatBuyerFirstname, flatBuyerLastname, flatBuyerSurname,"
//				+ " flatContractNumber, flatContractDate, flatCost, flatSellerName, flatNotes)" + " VALUES ("
//				+ idflatTable + ", '" + flatBuyerFirstname + "', '" + flatBuyerLarstname + "', '" + flatBuyerSurname
//				+ "'," + " '" + flatContractNumber + "', TIMESTAMP('" + flatContractDate + "'), " + flatCost + ", '"
//				+ flatSellerName + "', '" + flatNotes + "')";
//
////		System.out.println(sql);
//		Statement statement = conn.createStatement();
//
//		statement.executeUpdate(sql);
//
//		//		conn.close();
//
//	}
//
//	public void createBankPayment(Flat flat) throws SQLException {
//
//		DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
//
//		int idflatTable = flat.getIdFlatTable();
//		String bankTablePaymentDate = dateFormat.format(flat.getBankTablePaymentDate());
//		double bankTablePaymentSum = flat.getBankTablePaymentSum();
//
////		SqlConnection sqlConnection = new SqlConnection();
////		Connection conn = sqlConnection.sqlConnection();
//
//		String sql = "INSERT INTO bankTable (idflatTable, bankTablePaymentDate, bankTablePaymentSum) VALUES ("
//				+ idflatTable + ", TIMESTAMP('" + bankTablePaymentDate + "'), " + bankTablePaymentSum + ")";
//
////		System.out.println(sql);
//		Statement statement = conn.createStatement();
//		statement.executeUpdate(sql);
//
//		//		conn.close();
//
//	}
//
//	public void createSettings(Flat flat) throws SQLException {
//
//		String buildingCorps = flat.getBuildingCorps();
//
////		SqlConnection sqlConnection = new SqlConnection();
////		Connection conn = sqlConnection.sqlConnection();
//
//		String sql = "INSERT INTO settingsTable (buildingCorps) VALUES ('" + buildingCorps + "')";
//
////		System.out.println(sql);
//		Statement statement = conn.createStatement();
//		statement.executeUpdate(sql);
//
////		//		conn.close();
//
//	}
//
//	public List<Flat> getSelectedItemsByDateFromBankTable(String fromDate, String toDate) throws SQLException {
//
//		List<Flat> flatList = new ArrayList<>();
//
////		SqlConnection sqlConnection = new SqlConnection();
////		Connection conn = sqlConnection.sqlConnection();
//
//		String sql = "SELECT * FROM bankTable WHERE bankTablePaymentDate >= '" + fromDate
//				+ "' AND bankTablePaymentDate <= '" + toDate + "'";
//
////		System.out.println(sql);
//		Statement statement = conn.createStatement();
//
//		ResultSet rs = statement.executeQuery(sql);
//
//		ResultSetMetaData md = rs.getMetaData();
//		int cnt = md.getColumnCount();
//
//		while (rs.next()) {
//
//			Flat flat = new Flat();
//			for (int i = 1; i <= cnt; i++) {
//				flat.setIdFlatTable(rs.getInt(2));
//				flat.setBankTablePaymentDate(rs.getDate(3));
//				flat.setBankTablePaymentSum(rs.getDouble(4));
//			}
//			flatList.add(flat);
//		}
//
////		//		conn.close();
//
//		return flatList;
//	}
//
//	public void updateFlatTableByFlatId(int flatId, Flat flat) throws SQLException {
//
//		String buildingCorps = flat.getBuildingCorps();
//		int flatRooms = flat.getFlatRooms();
//		int flatFloor = flat.getFlatFloor();
//		int flatNumber = flat.getFlatNumber();
//		double flatArea = flat.getFlatArea();
//
////		SqlConnection sqlConnection = new SqlConnection();
////		Connection conn = sqlConnection.sqlConnection();
//
//		String sql = "UPDATE flatTable SET buildingCorps = '" + buildingCorps + "', flatRooms = " + flatRooms
//				+ ", flatFloor = " + flatFloor + ", flatNumber = " + flatNumber + ", flatArea = " + flatArea
//				+ " WHERE idFlatTable = " + flatId;
//
////		System.out.println(sql);
//		Statement statement = conn.createStatement();
//
//		statement.executeUpdate(sql);
//
//		//		conn.close();
//
//	}
//
//	
//	public void updateExpensesTableById(int flatId, Flat flat) throws SQLException {
//
//		
//		DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
//
////		int idflatTable = flat.getIdFlatTable();
//
//		String expensesTableDate = dateFormat.format(flat.getExpensesTableDate());
//
//		int expensesTableSum = flat.getExpensesTableSum();
//
//		String expensesTableCategory = flat.getExpensesTableCategory();
//		String expensesTableValue = flat.getExpensesTableValue();
//		String expensesTableValueTA = flat.getExpensesTableValueTA();
//
////		SqlConnection sqlConnection = new SqlConnection();
////		Connection conn = sqlConnection.sqlConnection();
//
////		String sql = "INSERT INTO expensesTable (idflatTable, expensesTableDate, expensesTableSum, expensesTableCategory, expensesTableValue, expensesTableValue) VALUES ("
////				+ idflatTable + ", TIMESTAMP('" + expensesTableDate + "'), " + expensesTableSum + ", '"
////				+ expensesTableCategory + "', '" + expensesTableValue + "', '" + expensesTableValueTA + "')";
//
//		String sql = "UPDATE expensesTable SET expensesTableDate = TIMESTAMP('" + expensesTableDate + "'), expensesTableSum = " + expensesTableSum + ", expensesTableCategory = '" + expensesTableCategory + "', expensesTableValue = '" + expensesTableValue + "', expensesTableValueTA = '" + expensesTableValueTA + "' WHERE idexpensesTable = " + flatId;
//
////		System.out.println(sql);
//		Statement statement = conn.createStatement();
//
//		statement.executeUpdate(sql);
//
//		//		conn.close();
//
//	}
//	
//	
//	
//	public void updateFlatSetByFlatIdInFlatTable(int flatId, String flatSet) throws SQLException {
//
////		SqlConnection sqlConnection = new SqlConnection();
////		Connection conn = sqlConnection.sqlConnection();
//
//		String sql = "UPDATE flatTable SET flatSet = '" + flatSet + "' WHERE idflatTable =" + flatId;
//
////		System.out.println(sql);
//		Statement statement = conn.createStatement();
//
//		statement.executeUpdate(sql);
//
//		//		conn.close();
//
//	}
//
//	
//	public void updateFlatBuyerTableByFlatId(int flatId, Flat flat) throws SQLException {
//
//		
//		DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
//		String flatContractDate = dateFormat.format(flat.getFlatContractDate());
//		
//		String flatBuyerLastname = flat.getFlatBuyerLastname();
//		String flatBuyerFirstname = flat.getFlatBuyerFirstname();
//		String flatBuyerSurname = flat.getFlatBuyerSurname();
//		Integer flatCost = flat.getFlatCost();
//		String flatNotes = flat.getFlatNotes();
//		
////		SqlConnection sqlConnection = new SqlConnection();
////		Connection conn = sqlConnection.sqlConnection();
//
//		String sql = "UPDATE flatBuyerTable SET flatContractDate = TIMESTAMP('" + flatContractDate + "'),"
//				+ " flatBuyerLastname = '" + flatBuyerLastname + "', flatBuyerFirstname = '" + flatBuyerFirstname + "',"
//						+ " flatBuyerSurname = '" + flatBuyerSurname + "', flatCost = " + flatCost + ","
//								+ " flatNotes = '" + flatNotes + "' WHERE idflatTable =" + flatId;
//
////		System.out.println(sql);
//		Statement statement = conn.createStatement();
//
//		statement.executeUpdate(sql);
//
//		//		conn.close();
//
//	}
//	
//	
//	
//	public void deleteFlatByFlatId(int flatId) throws SQLException {
//
////		SqlConnection sqlConnection = new SqlConnection();
////		Connection conn = sqlConnection.sqlConnection();
//
//		String sql = "DELETE FROM flatTable WHERE idFlatTable = " + flatId;
//
////			System.out.println(sql);
//		Statement statement = conn.createStatement();
//		statement.executeUpdate(sql);
//
//		//		conn.close();
//	}
//	
//	
//	public void deleteFlatByIdFromBuyerTable(int flatId) throws SQLException {
//
////		SqlConnection sqlConnection = new SqlConnection();
////		Connection conn = sqlConnection.sqlConnection();
//
//		String sql = "DELETE FROM flatBuyerTable WHERE idFlatTable = " + flatId;
// 
////			System.out.println(sql);
//		Statement statement = conn.createStatement();
//		statement.executeUpdate(sql);
//
//		//		conn.close();
//	}
//	
//	
//	public void deleteFlatByIdFromExpensesTable(int flatId) throws SQLException {
//
////		SqlConnection sqlConnection = new SqlConnection();
////		Connection conn = sqlConnection.sqlConnection();
//
//		String sql = "DELETE FROM expensesTable WHERE idExpensesTable = " + flatId;
// 
////			System.out.println(sql);
//		Statement statement = conn.createStatement();
//		statement.executeUpdate(sql);
//
//		//		conn.close();
//	}
//	
//	
//	
//
//	public int countFlats() throws SQLException {
//
//		int count = 0;
//
////		SqlConnection sqlConnection = new SqlConnection();
////		Connection conn = sqlConnection.sqlConnection();
//
//		String sql = "SELECT COUNT(flatNumber) FROM flatTable";
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
//			for (int i = 1; i <= cnt; i++) {
//
//				count = rs.getInt(1);
//
//			}
//		}
//
//		//		conn.close();
//
//		return count;
//	}
//
//	public double sumFlatsAreaByCorpsFromFlatTable(String corps) throws SQLException {
//
//		double allFlatsArea = 0;
//
////		SqlConnection sqlConnection = new SqlConnection();
////		Connection conn = sqlConnection.sqlConnection();
//
//		String sql = "SELECT SUM(flatArea) FROM flatTable WHERE buildingCorps = '" + corps + "'";
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
//			for (int i = 1; i <= cnt; i++) {
//
//				allFlatsArea = rs.getDouble(1);
//
//			}
//		}
//
//		//		conn.close();
//
//		return allFlatsArea;
//	}
//
//	public double sumAllFlatsArea() throws SQLException {
//
//		double allFlatsArea = 0;
//
////		SqlConnection sqlConnection = new SqlConnection();
////		Connection conn = sqlConnection.sqlConnection();
//
//		String sql = "SELECT SUM(flatArea) FROM flatTable";
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
//			for (int i = 1; i <= cnt; i++) {
//
//				allFlatsArea = rs.getDouble(1);
//
//			}
//		}
//
//		//		conn.close();
//
//		return allFlatsArea;
//	}
//
//	public List<Flat> getFlatsFromOrderedFlatTable() throws SQLException {
//
//		List<Flat> flatList = new ArrayList<>();
//
////		SqlConnection sqlConnection = new SqlConnection();
////		Connection conn = sqlConnection.sqlConnection();
//
//		String sql = "SELECT * FROM flatTable ORDER BY buildingCorps, flatNumber";
//
////		System.out.println(sql);
//		Statement statement = conn.createStatement();
//
//		ResultSet rs = statement.executeQuery(sql);
//
//		ResultSetMetaData md = rs.getMetaData();
//		int cnt = md.getColumnCount();
//
//		while (rs.next()) {
//
//			Flat flat = new Flat();
//			for (int i = 1; i <= cnt; i++) {
//				flat.setIdFlatTable(rs.getInt(1));
//				flat.setBuildingCorps(rs.getString(2));
//				flat.setFlatRooms(rs.getInt(3));
//				flat.setFlatFloor(rs.getInt(4));
//				flat.setFlatNumber(rs.getInt(5));
//				flat.setFlatArea(rs.getDouble(6));
//				flat.setFlatSet(rs.getString(7));
//
//			}
//			flatList.add(flat);
//		}
//
//		//		conn.close();
//
//		return flatList;
//	}
//
//	public List<Flat> getFlatsByCorpsFromFlatTable(String corps) throws SQLException {
//
//		List<Flat> flatList = new ArrayList<>();
//
////		SqlConnection sqlConnection = new SqlConnection();
////		Connection conn = sqlConnection.sqlConnection();
//
//		String sql = "SELECT * FROM flatTable WHERE buildingCorps = '" + corps + "'";
//
////		System.out.println(sql);
//		Statement statement = conn.createStatement();
//
//		ResultSet rs = statement.executeQuery(sql);
//
//		ResultSetMetaData md = rs.getMetaData();
//		int cnt = md.getColumnCount();
//
//		while (rs.next()) {
//
//			Flat flat = new Flat();
//			for (int i = 1; i <= cnt; i++) {
//				flat.setIdFlatTable(rs.getInt(1));
//				flat.setBuildingCorps(rs.getString(2));
//				flat.setFlatRooms(rs.getInt(3));
//				flat.setFlatFloor(rs.getInt(4));
//				flat.setFlatNumber(rs.getInt(5));
//				flat.setFlatArea(rs.getDouble(6));
//				flat.setFlatSet(rs.getString(7));
//
//			}
//			flatList.add(flat);
//		}
//
//		//		conn.close();
//
//		return flatList;
//	}
//
//	public Flat getFlatByCorpsAndNumberFromFlatTable(String corps, int number) throws SQLException {
//
////		SqlConnection sqlConnection = new SqlConnection();
////		Connection conn = sqlConnection.sqlConnection();
//
//		String sql = "SELECT * FROM flatTable WHERE buildingCorps = '" + corps + "' AND flatNumber = " + number;
//
////		System.out.println(sql);
//		Statement statement = conn.createStatement();
//
//		ResultSet rs = statement.executeQuery(sql);
//
//		Flat flat = new Flat();
//
//		while (rs.next()) {
//
//			flat.setIdFlatTable(rs.getInt(1));
//			flat.setBuildingCorps(rs.getString(2));
//			flat.setFlatRooms(rs.getInt(3));
//			flat.setFlatFloor(rs.getInt(4));
//			flat.setFlatNumber(rs.getInt(5));
//			flat.setFlatArea(rs.getDouble(6));
//			flat.setFlatSet(rs.getString(7));
//
//		}
//
//		//		conn.close();
//
//		return flat;
//	}
//
//	public List<Flat> getCorpsFromSettingsTable() throws SQLException {
//
//		List<Flat> flatList = new ArrayList<>();
//
////		SqlConnection sqlConnection = new SqlConnection();
////		Connection conn = sqlConnection.sqlConnection();
//
//		String sql = "SELECT * FROM settingsTable";
//
////		System.out.println(sql);
//		Statement statement = conn.createStatement();
//
//		ResultSet rs = statement.executeQuery(sql);
//
//		ResultSetMetaData md = rs.getMetaData();
//		int cnt = md.getColumnCount();
//
//		while (rs.next()) {
//
//			Flat flat = new Flat();
//			for (int i = 1; i <= cnt; i++) {
//				flat.setBuildingCorps(rs.getString(2));
//
//			}
//			flatList.add(flat);
//		}
//
//		//		conn.close();
//
//		return flatList;
//	}
//
//	public List<Flat> getFlatsFromFlatBuyerTable() throws SQLException {
//
//		List<Flat> flatList = new ArrayList<>();
//
////		SqlConnection sqlConnection = new SqlConnection();
////		Connection conn = sqlConnection.sqlConnection();
//
//		String sql = "SELECT * FROM flatBuyerTable";
//
////		System.out.println(sql);
//		Statement statement = conn.createStatement();
//
//		ResultSet rs = statement.executeQuery(sql);
//
//		ResultSetMetaData md = rs.getMetaData();
//		int cnt = md.getColumnCount();
//
//		while (rs.next()) {
//
//			Flat flat = new Flat();
//			for (int i = 1; i <= cnt; i++) {
//				flat.setIdFlatTable(rs.getInt(2));
//				flat.setFlatBuyerFirstname(rs.getString(3));
//				flat.setFlatBuyerLastname(rs.getString(4));
//				flat.setFlatBuyerSurname(rs.getString(5));
//				flat.setFlatContractDate(rs.getDate(6));
//				flat.setFlatContractNumber(rs.getString(7));
//				flat.setFlatCost(rs.getInt(8));
//				flat.setFlatSellerName(rs.getString(9));
//				flat.setFlatNotes(rs.getString(10));
//
//			}
//			flatList.add(flat);
//		}
//
//		//		conn.close();
//
//		return flatList;
//	}
//
//	public List<Flat> getPaymentsFromBankTable() throws SQLException {
//
//		List<Flat> paymentList = new ArrayList<>();
//
////		SqlConnection sqlConnection = new SqlConnection();
////		Connection conn = sqlConnection.sqlConnection();
//
//		String sql = "SELECT * FROM bankTable";
//
////		System.out.println(sql);
//		Statement statement = conn.createStatement();
//
//		ResultSet rs = statement.executeQuery(sql);
//
//		ResultSetMetaData md = rs.getMetaData();
//		int cnt = md.getColumnCount();
//
//		while (rs.next()) {
//
//			Flat flat = new Flat();
//			for (int i = 1; i <= cnt; i++) {
//				flat.setIdFlatTable(rs.getInt(2));
//
//				flat.setBankTablePaymentDate(rs.getDate(3));
//				flat.setBankTablePaymentSum(rs.getDouble(4));
//
//			}
//			paymentList.add(flat);
//		}
//
//		//		conn.close();
//
//		return paymentList;
//	}
//
//	public Flat getFlatFromFlatBuyerTableByContract(String contractNumber) throws SQLException {
//
//		Flat flat = new Flat();
//
////		SqlConnection sqlConnection = new SqlConnection();
////		Connection conn = sqlConnection.sqlConnection();
//
//		String sql = "SELECT * FROM flatBuyerTable WHERE flatContractNumber = '" + contractNumber + "'";
//		
//
////		System.out.println(sql);
//		Statement statement = conn.createStatement();
//
//		ResultSet rs = statement.executeQuery(sql);
//
//		ResultSetMetaData md = rs.getMetaData();
//		int cnt = md.getColumnCount();
//
//		while (rs.next()) {
//
//			for (int i = 1; i <= cnt; i++) {
//
//				flat.setIdFlatTable(rs.getInt(2));
//
//				flat.setFlatBuyerFirstname(rs.getString(3));
//				flat.setFlatBuyerLastname(rs.getString(4));
//				flat.setFlatBuyerSurname(rs.getString(5));
//				flat.setFlatContractDate(rs.getDate(6));
////				flat.setFlatContractDate(rs.getTimestamp(6));
//				flat.setFlatContractNumber(rs.getString(7));
//				flat.setFlatCost(rs.getInt(8));
//				flat.setFlatSellerName(rs.getString(9));
//				flat.setFlatNotes(rs.getString(10));
//
//			}
//
//		}
//
//		//		conn.close();
//
//		return flat;
//	}
//
//
//
//	public List<Flat> getFlatsFromBankTableByIdFlatTable(int idFlatTable) throws SQLException {
//
//		List<Flat> flatList = new ArrayList<>();
//
////		SqlConnection sqlConnection = new SqlConnection();
////		Connection conn = sqlConnection.sqlConnection();
//
//		String sql = "SELECT * FROM bankTable WHERE idFlatTable = " + idFlatTable;
//
////		System.out.println(sql);
//		Statement statement = conn.createStatement();
//
//		ResultSet rs = statement.executeQuery(sql);
//
//		ResultSetMetaData md = rs.getMetaData();
//		int cnt = md.getColumnCount();
//
//		while (rs.next()) {
//			Flat flat = new Flat();
//			for (int i = 1; i <= cnt; i++) {
//				flat.setIdFlatTable(rs.getInt(2));
//
//				flat.setBankTablePaymentDate(rs.getDate(3));
//				flat.setBankTablePaymentSum(rs.getDouble(4));
//
//			}
//			flatList.add(flat);
//		}
//
//		//		conn.close();
//
//		return flatList;
//	}
//
//	public List<Flat> getFlatsByFlatSetFromDB(String flatSet) throws SQLException {
//
//		List<Flat> flatList = new ArrayList<>();
//
////		SqlConnection sqlConnection = new SqlConnection();
////		Connection conn = sqlConnection.sqlConnection();
//
//		String sql = "SELECT flattable.*, flatbuyertable.* FROM flatTable LEFT JOIN flatbuyertable ON flatTable.idFlatTable = flatbuyertable.idflatTable WHERE flatSet = '"
//				+ flatSet + "'";
//
////		System.out.println(sql);
//		Statement statement = conn.createStatement();
//
//		ResultSet rs = statement.executeQuery(sql);
//
//		ResultSetMetaData md = rs.getMetaData();
//		int cnt = md.getColumnCount();
//
//		while (rs.next()) {
//
//			Flat flat = new Flat();
//			for (int i = 1; i <= cnt; i++) {
//				flat.setIdFlatTable(rs.getInt(1));
//				flat.setBuildingCorps(rs.getString(2));
//				flat.setFlatRooms(rs.getInt(3));
//				flat.setFlatFloor(rs.getInt(4));
//				flat.setFlatNumber(rs.getInt(5));
//				flat.setFlatArea(rs.getDouble(6));
//				flat.setFlatSet(rs.getString(7));
//
//				flat.setFlatContractDate(rs.getDate(13));
//
//			}
//			flatList.add(flat);
//		}
//
//		//		conn.close();
//
//		return flatList;
//	}
//
//	public List<Flat> getFlatsByFlatSetFromOrderedFlatTable(String flatSet) throws SQLException {
//
//		List<Flat> flatList = new ArrayList<>();
//
////		SqlConnection sqlConnection = new SqlConnection();
////		Connection conn = sqlConnection.sqlConnection();
//
//		String sql = "SELECT * FROM flatTable WHERE flatSet = '" + flatSet + "' ORDER BY buildingCorps, flatNumber";
//
////		System.out.println(sql);
//		Statement statement = conn.createStatement();
//
//		ResultSet rs = statement.executeQuery(sql);
//
//		ResultSetMetaData md = rs.getMetaData();
//		int cnt = md.getColumnCount();
//
//		while (rs.next()) {
//			Flat flat = new Flat();
//			for (int i = 1; i <= cnt; i++) {
//				flat.setIdFlatTable(rs.getInt(1));
//				flat.setBuildingCorps(rs.getString(2));
//				flat.setFlatRooms(rs.getInt(3));
//				flat.setFlatFloor(rs.getInt(4));
//				flat.setFlatNumber(rs.getInt(5));
//				flat.setFlatArea(rs.getDouble(6));
//				flat.setFlatSet(rs.getString(7));
//
//			}
//			flatList.add(flat);
//		}
//
//		//		conn.close();
//
//		return flatList;
//	}
//
//	public Flat getFlatByFlatIdFromFlatBuyerTable(int flatId) throws SQLException {
//
//		Flat flat = new Flat();
//
////		SqlConnection sqlConnection = new SqlConnection();
////		Connection conn = sqlConnection.sqlConnection();
//
//		String sql = "SELECT * FROM flatbuyertable WHERE idFlatTable = " + flatId;
//
////		System.out.println(sql);
//		Statement statement = conn.createStatement();
//		ResultSet rs = statement.executeQuery(sql);
//
//		while (rs.next()) {
//
//			flat.setIdFlatTable(rs.getInt(2));
//			flat.setFlatBuyerLastname(rs.getString(3));
//			flat.setFlatBuyerFirstname(rs.getString(4));
//			flat.setFlatBuyerSurname(rs.getString(5));
//			flat.setFlatContractDate(rs.getDate(6));
//			flat.setFlatContractNumber(rs.getString(7));
//			flat.setFlatCost(rs.getInt(8));
//			flat.setFlatSellerName(rs.getString(9));
//			flat.setFlatNotes(rs.getString(10));
//
//		}
//
//		//		conn.close();
//
//		return flat;
//	}
//
//	public List<Flat> getExpensesByFlatIdFromExpensesTable(int idFlatTable) throws SQLException {
//
//		List<Flat> flatList = new ArrayList<>();
//
////		SqlConnection sqlConnection = new SqlConnection();
////		Connection conn = sqlConnection.sqlConnection();
//
//		String sql = "SELECT * FROM expensesTable WHERE idflatTable = " + idFlatTable;
//
////		System.out.println(sql);
//		Statement statement = conn.createStatement();
//
//		ResultSet rs = statement.executeQuery(sql);
//
//		ResultSetMetaData md = rs.getMetaData();
//		int cnt = md.getColumnCount();
//
//		while (rs.next()) {
//
//			Flat flat = new Flat();
//			for (int i = 1; i <= cnt; i++) {
//				flat.setIdFlatTable(rs.getInt(2));
//				flat.setExpensesTableDate(rs.getDate(3));
//				flat.setExpensesTableSum(rs.getInt(4));
//				flat.setExpensesTableCategory(rs.getString(5));
//				flat.setExpensesTableValue(rs.getString(6));
//				flat.setExpensesTableValueTA(rs.getString(7));
//
//			}
//			flatList.add(flat);
//		}
//
//		//		conn.close();
//
//		return flatList;
//	}
//
//	public List<Flat> getExpensesByFlatIdAndCategoryFromExpensesTable(int idFlatTable, String category)
//			throws SQLException {
//
//		List<Flat> flatList = new ArrayList<>();
//
////		SqlConnection sqlConnection = new SqlConnection();
////		Connection conn = sqlConnection.sqlConnection();
//
//		String sql = "SELECT * FROM expensesTable WHERE idflatTable = " + idFlatTable + " AND expensesTableCategory = '"
//				+ category + "'";
//
////		System.out.println(sql);
//		Statement statement = conn.createStatement();
//
//		ResultSet rs = statement.executeQuery(sql);
//
//		ResultSetMetaData md = rs.getMetaData();
//		int cnt = md.getColumnCount();
//
//		while (rs.next()) {
//			Flat flat = new Flat();
//			for (int i = 1; i <= cnt; i++) {
//				flat.setExpensesTableDate(rs.getDate(3));
//				flat.setExpensesTableSum(rs.getInt(4));
//				flat.setExpensesTableCategory(rs.getString(5));
//				flat.setExpensesTableValue(rs.getString(6));
//
//			}
//			flatList.add(flat);
//		}
//
//		//		conn.close();
//
//		return flatList;
//	}
//
//	public List<Flat> getExpensesFromExpensesTable() throws SQLException {
//
//		List<Flat> flatList = new ArrayList<>();
//
////		SqlConnection sqlConnection = new SqlConnection();
////		Connection conn = sqlConnection.sqlConnection();
//
//		String sql = "SELECT * FROM expensesTable";
//
////		System.out.println(sql);
//		Statement statement = conn.createStatement();
//
//		ResultSet rs = statement.executeQuery(sql);
//
//		ResultSetMetaData md = rs.getMetaData();
//		int cnt = md.getColumnCount();
//
//		while (rs.next()) {
//			Flat flat = new Flat();
//			for (int i = 1; i <= cnt; i++) {
//				flat.setIdExpensesTable(rs.getInt(1));
//				flat.setIdFlatTable(rs.getInt(2));
//				flat.setExpensesTableDate(rs.getTimestamp(3));
//				flat.setExpensesTableSum(rs.getInt(4));
//				flat.setExpensesTableCategory(rs.getString(5));
//				flat.setExpensesTableValue(rs.getString(6));
//				flat.setExpensesTableValueTA(rs.getString(7));
//
//			}
//			flatList.add(flat);
//		}
//
//		//		conn.close();
//
//		return flatList;
//	}
//
//	public Flat getFlatByFlatIdFromFlatTable(int flatId) throws SQLException {
//
//		Flat flat = new Flat();
//
////		SqlConnection sqlConnection = new SqlConnection();
////		Connection conn = sqlConnection.sqlConnection();
//
//		String sql = "SELECT * FROM flatTable WHERE idFlatTable=" + flatId;
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
//			for (int i = 1; i <= cnt; i++) {
//				flat.setIdFlatTable(rs.getInt(1));
//				flat.setBuildingCorps(rs.getString(2));
//				flat.setFlatRooms(rs.getInt(3));
//				flat.setFlatFloor(rs.getInt(4));
//				flat.setFlatNumber(rs.getInt(5));
//				flat.setFlatArea(rs.getDouble(6));
//				flat.setFlatSet(rs.getString(7));
//
//			}
//		}
//
//		//		conn.close();
//
//		return flat;
//	}
//	
//	
//	public Flat getFlatByIdFromExpensesTable(int flatId) throws SQLException {
//
//		Flat flat = new Flat();
//
////		SqlConnection sqlConnection = new SqlConnection();
////		Connection conn = sqlConnection.sqlConnection();
//
//		String sql = "SELECT * FROM expensesTable WHERE idExpensesTable = " + flatId;
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
//			for (int i = 1; i <= cnt; i++) {
//				flat.setIdExpensesTable(rs.getInt(1));
//				flat.setIdFlatTable(rs.getInt(2));
//				flat.setExpensesTableDate(rs.getTimestamp(3));
//				flat.setExpensesTableSum(rs.getInt(4));
//				flat.setExpensesTableCategory(rs.getString(5));
//				flat.setExpensesTableValue(rs.getString(6));
//				flat.setExpensesTableValueTA(rs.getString(7));
//
//			}
//		}
//
//		//		conn.close();
//
//		return flat;
//	}
//	
//
//	public Flat getFlatByFlatIdFromFlatTableShortInfo(int flatId) throws SQLException {
//
//		Flat flat = new Flat();
//
////		SqlConnection sqlConnection = new SqlConnection();
////		Connection conn = sqlConnection.sqlConnection();
//
//		String sql = "SELECT * FROM flatTable WHERE idFlatTable=" + flatId;
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
//			for (int i = 1; i <= cnt; i++) {
//				flat.setIdFlatTable(rs.getInt(1));
//				flat.setBuildingCorps(rs.getString(2));
//				flat.setFlatRooms(rs.getInt(3));
//				flat.setFlatFloor(rs.getInt(4));
//				flat.setFlatNumber(rs.getInt(5));
//				flat.setFlatArea(rs.getDouble(6));
//			}
//		}
//
//		//		conn.close();
//
//		return flat;
//	}
//
//	public String getFlatFromDbByColumnIndex(int index, Flat flat) {
//
//		if (index == 0) {
//			return Integer.toString(flat.getIdFlatTable());
//		}
//
//		if (index == 1) {
//			return flat.getBuildingCorps();
//		}
//
//		if (index == 2) {
//			return Integer.toString(flat.getFlatRooms());
//		}
//
//		if (index == 3) {
//			return Integer.toString(flat.getFlatFloor());
//		}
//
//		if (index == 4) {
//			return Integer.toString(flat.getFlatNumber());
//		}
//
//		if (index == 5) {
//			return Double.toString(flat.getFlatArea());
//		}
//
//		if (index == 6) {
//			return flat.getFlatSet();
//		}
//
//		if (index == 7) {
//			return flat.getFlatBuyerFirstname();
//		}
//
//		if (index == 8) {
//			return flat.getFlatBuyerLastname();
//		}
//
//		if (index == 9) {
//			return flat.getFlatBuyerSurname();
//		}
//
//		if (index == 10) {
//			DateFormat dateFormat = new SimpleDateFormat("dd.MM.yyyy");
//			return dateFormat.format(flat.getFlatContractDate());
//		}
//
//		if (index == 11) {
//			return flat.getFlatContractNumber();
//		}
//
//		if (index == 12) {
//			return Integer.toString(flat.getFlatCost());
//		}
//
//		return null;
//	}
//
//	public void createExpensesFlat(Flat flat) throws SQLException {
//
//		DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
//
//		int idflatTable = flat.getIdFlatTable();
//
//		String expensesTableDate = dateFormat.format(flat.getExpensesTableDate());
//
//		int expensesTableSum = flat.getExpensesTableSum();
//
//		String expensesTableCategory = flat.getExpensesTableCategory();
//		String expensesTableValue = flat.getExpensesTableValue();
//		String expensesTableValueTA = flat.getExpensesTableValueTA();
//
////		SqlConnection sqlConnection = new SqlConnection();
////		Connection conn = sqlConnection.sqlConnection();
//
//		String sql = "INSERT INTO expensesTable (idflatTable, expensesTableDate, expensesTableSum, expensesTableCategory, expensesTableValue, expensesTableValue) VALUES ("
//				+ idflatTable + ", TIMESTAMP('" + expensesTableDate + "'), " + expensesTableSum + ", '"
//				+ expensesTableCategory + "', '" + expensesTableValue + "', '" + expensesTableValueTA + "')";
//
////		System.out.println(sql);
//		Statement statement = conn.createStatement();
//		statement.executeUpdate(sql);
//
//		//		conn.close();
//	}
//
//	public int getExpensesFlatInfoFromExpensesTableByFlatId(int flatId, String expensesTableCategory)
//			throws SQLException {
//
//		int sumExpensesTableCategory = 10;
//
////		SqlConnection sqlConnection = new SqlConnection();
////		Connection conn = sqlConnection.sqlConnection();
//
//		String sql = "SELECT SUM(expensesTableSum) FROM expensesTable WHERE expensesTableCategory = '"
//				+ expensesTableCategory + "' AND idflatTable = " + flatId;
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
//			for (int i = 1; i <= cnt; i++) {
//
//				sumExpensesTableCategory = rs.getInt(1);
//
//			}
//		}
//
//		//		conn.close();
//
//		return sumExpensesTableCategory;
//
//	}
//
//	public double getPaymentSumFromBankTableByFlatTableId(int flatId) throws SQLException {
//
//		double paymentSum = 0;
//
////		SqlConnection sqlConnection = new SqlConnection();
////		Connection conn = sqlConnection.sqlConnection();
//
//		String sql = "SELECT SUM(bankTablePaymentSum) FROM bankTable WHERE idflatTable = " + flatId;
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
//			for (int i = 1; i <= cnt; i++) {
//
//				paymentSum = rs.getInt(1);
//
//			}
//		}
//
//		//		conn.close();
//
//		return paymentSum;
//
//	}
//
//	public String dateFormatForGrid(Date date) {
//
//		DateFormat dateFormat = new SimpleDateFormat("dd.MM.yyyy"); // "dd.MM.yyyy HH:mm:ss"
//		String dateStr = dateFormat.format(date); // flatFromList.getBankTablePaymentDate()
//
//		return dateStr;
//	}
//
//	public String dateFormatForDB(Date date) {
//
//		DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd"); // "dd.MM.yyyy HH:mm:ss"
//		String dateStr = dateFormat.format(date); // flatFromList.getBankTablePaymentDate()
//
//		return dateStr;
//	}
//
//	public String dateFormatForDB(String dateStr) {
//
//		String dateReplace = dateStr.replace(".", "-");
//
//		String[] contractDate = dateReplace.split("-");
//
//		String dateNew = contractDate[2] + "." + contractDate[1] + "." + contractDate[0];
//
//		return dateNew;
//	}
//
////------------------------------------------	
//
//}
