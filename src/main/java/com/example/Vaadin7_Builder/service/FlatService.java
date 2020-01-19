package com.example.Vaadin7_Builder.service;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.sql.Statement;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
//import java.util.TimeZone;

import com.example.Vaadin7_Builder.model.Flat;
import com.example.Vaadin7_Builder.model.User;

public class FlatService {

	public void createFlat(Flat flat) throws SQLException {

		String buildingCorps = flat.getBuildingCorps();
		int flatFloor = flat.getFlatFloor();
		int flatNumber = flat.getFlatNumber();
		double flatArea = flat.getFlatArea();
		Object flatRooms = flat.getFlatRooms();
		String flatSet = "";

		SqlConnection sqlConnection = new SqlConnection();
		Connection conn = sqlConnection.sqlConnection();

		String sql = "INSERT INTO flatTable (buildingCorps, flatFloor, flatNumber, flatArea, flatRooms, flatSet) VALUES ('"
				+ buildingCorps + "'," + flatFloor + ", " + flatNumber + ", " + flatArea + ", " + flatRooms + ", '"
				+ flatSet + "')";

//		System.out.println(sql);
		Statement statement = conn.createStatement();
		statement.executeUpdate(sql);

		conn.close();

	}

	public void createBuyerFlat(Flat buyerFlat) throws SQLException {

		DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
//		DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");

		int idflatTable = buyerFlat.getIdFlatTable();
		String flatBuyerFirstname = buyerFlat.getFlatBuyerFirstname();
		String flatBuyerLarstname = buyerFlat.getFlatBuyerLastname();
		String flatBuyerSurname = buyerFlat.getFlatBuyerSurname();
		String flatContractNumber = buyerFlat.getFlatContractNumber();
		String flatContractDate = dateFormat.format(buyerFlat.getFlatContractDate());
		int flatCost = buyerFlat.getFlatCost();
		String flatSellerName = buyerFlat.getFlatSellerName();
		String flatNotes = buyerFlat.getFlatNotes();

		SqlConnection sqlConnection = new SqlConnection();
		Connection conn = sqlConnection.sqlConnection();

		String sql = "INSERT INTO flatbuyerTable (idflatTable, flatBuyerFirstname, flatBuyerLastname, flatBuyerSurname,"
				+ " flatContractNumber, flatContractDate, flatCost, flatSellerName, flatNotes)" + " VALUES ("
				+ idflatTable + ", '" + flatBuyerFirstname + "', '" + flatBuyerLarstname + "', '" + flatBuyerSurname
				+ "'," + " '" + flatContractNumber + "', TIME('" + flatContractDate + "'), " + flatCost + ", '"
				+ flatSellerName + "', '" + flatNotes + "')";

//		System.out.println(sql);
		Statement statement = conn.createStatement();

		statement.executeUpdate(sql);

		conn.close();

	}
	
	
	public void createBankPayment(Flat flat) throws SQLException {

		DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		
		int idflatTable = flat.getIdFlatTable();
		String bankTablePaymentDate = dateFormat.format(flat.getBankTablePaymentDate());
		double bankTablePaymentSum = flat.getBankTablePaymentSum();

		SqlConnection sqlConnection = new SqlConnection();
		Connection conn = sqlConnection.sqlConnection();

		String sql = "INSERT INTO bankTable (idflatTable, bankTablePaymentDate, bankTablePaymentSum) VALUES ("
				+ idflatTable + ", TIME('" + bankTablePaymentDate + "'), " + bankTablePaymentSum + ")";

//		System.out.println(sql);
		Statement statement = conn.createStatement();
		statement.executeUpdate(sql);

		conn.close();

	}
	
	
	public void createSettings(Flat flat) throws SQLException {

		String buildingCorps = flat.getBuildingCorps();
//		String buildingCorps = flat.getBuildingCorps();
//		int flatFloor = flat.getFlatFloor();
//		int flatNumber = flat.getFlatNumber();
//		double flatArea = flat.getFlatArea();
//		Object flatRooms = flat.getFlatRooms();
//		String flatSet = "";

		SqlConnection sqlConnection = new SqlConnection();
		Connection conn = sqlConnection.sqlConnection();

		
		String sql = "INSERT INTO settingsTable (buildingCorps) VALUES ('" + buildingCorps + "')";
		
		
//		String sql = "INSERT INTO settingsTable (buildingCorps, flatFloor, flatNumber, flatArea, flatRooms, flatSet) VALUES ('"
//				+ buildingCorps + "'," + flatFloor + ", " + flatNumber + ", " + flatArea + ", " + flatRooms + ", '"
//				+ flatSet + "')";

//		System.out.println(sql);
		Statement statement = conn.createStatement();
		statement.executeUpdate(sql);

		conn.close();

	}
	
	
	
	
	public List<Flat> getSelectedItemsByDateFromBankTable(String fromDate, String toDate) throws SQLException {

		List<Flat> flatList = new ArrayList<>();

		SqlConnection sqlConnection = new SqlConnection();
		Connection conn = sqlConnection.sqlConnection();

		String sql = "SELECT * FROM bankTable WHERE bankTablePaymentDate >= '" + fromDate + "' AND bankTablePaymentDate <= '" + toDate + "'";

//		System.out.println(sql);
		Statement statement = conn.createStatement();

		ResultSet rs = statement.executeQuery(sql);

		ResultSetMetaData md = rs.getMetaData();
		int cnt = md.getColumnCount();

		int row = 0;

		while (rs.next()) {
			row++;
			Flat flat = new Flat();
			for (int i = 1; i <= cnt; i++) {
				flat.setIdFlatTable(rs.getInt(2));
				flat.setBankTablePaymentDate(rs.getDate(3));
				flat.setBankTablePaymentSum(rs.getDouble(4));
			}
			flatList.add(flat);
		}

		conn.close();

		return flatList;
	}
	
	
	
	
	
	

	
	
	
	
	
	
	
////////////////////////////////////	
	public void createDateTable(Flat flat) throws SQLException, ParseException {

		DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
//		DateFormat dateFormat = new SimpleDateFormat("dd-MM-yyyy HH:mm:ss");
		
//		int idflatTable = flat.getIdFlatTable();
		String bankTablePaymentDate = dateFormat.format(flat.getNew_date_tablecol());
		
//		Date bankTablePaymentDateDate = flat.getNew_date_tablecol();
//		double bankTablePaymentSum = flat.getBankTablePaymentSum();

		SqlConnection sqlConnection = new SqlConnection();
		Connection conn = sqlConnection.sqlConnection();

		String sql = "INSERT INTO new_date_table (new_date_tablecol, new_date_tablecol1) VALUES (TIME('" + bankTablePaymentDate + "'), DATE('" + bankTablePaymentDate + "'))";

//		System.out.println(sql);
		Statement statement = conn.createStatement();
		statement.executeUpdate(sql);

		conn.close();

	}
	
	
	
	public Flat getDateFromDateTable(int idnew_date_table) throws SQLException, ParseException {

		
		
//		TimeZone.setDefault(TimeZone.getTimeZone("Europe/Kiev"));
//		TimeZone.setDefault(TimeZone.getTimeZone("Europe/Minsk"));
		
		Flat flat = new Flat();

		SqlConnection sqlConnection = new SqlConnection();
		Connection conn = sqlConnection.sqlConnection();

		String sql = "SELECT * FROM new_date_table WHERE idnew_date_table = " + idnew_date_table;

//		System.out.println(sql);
		Statement statement = conn.createStatement();

		ResultSet rs = statement.executeQuery(sql);

		ResultSetMetaData md = rs.getMetaData();
		int cnt = md.getColumnCount();

		int row = 0;

		while (rs.next()) {
			row++;
//			Flat flat = new Flat();
			for (int i = 1; i <= cnt; i++) {
				
				flat.setNew_date_tablecol(rs.getTimestamp(2));
				flat.setNew_date_tablecol1(rs.getTimestamp(3));
//				flat.setNew_date_tablecol1(rs.getDate(3));
				


			}
//			flatList.add(flat);
		}

		conn.close();

		return flat;
	}
	
	
	
	
	
////////////////////////////////////////	
	
	public void updateFlatTableByFlatId(int flatId, Flat flat) throws SQLException {
		
		String buildingCorps = flat.getBuildingCorps();
		int flatRooms = flat.getFlatRooms();
		int flatFloor = flat.getFlatFloor();
		int flatNumber = flat.getFlatNumber();
		double flatArea = flat.getFlatArea();
//		String flatSet = "";
		

		SqlConnection sqlConnection = new SqlConnection();
		Connection conn = sqlConnection.sqlConnection();
		
		String sql = "UPDATE flatTable SET buildingCorps = '" + buildingCorps + "', flatRooms = " + flatRooms + ", flatFloor = " + flatFloor + ", flatNumber = " + flatNumber + ", flatArea = " + flatArea + " WHERE idFlatTable = " + flatId;
		
//		System.out.println(sql);
		Statement statement = conn.createStatement();

		statement.executeUpdate(sql);

		conn.close();

	}
	
	
	
	
	

	public void updateFlatSetByFlatIdInFlatTable(int flatId, String flatSet) throws SQLException {

		SqlConnection sqlConnection = new SqlConnection();
		Connection conn = sqlConnection.sqlConnection();

		String sql = "UPDATE flatTable SET flatSet = '" + flatSet + "' WHERE idflatTable =" + flatId;

//		System.out.println(sql);
		Statement statement = conn.createStatement();

		statement.executeUpdate(sql);

		conn.close();

	}

	public void deleteFlatByFlatId(int flatId) throws SQLException {

		SqlConnection sqlConnection = new SqlConnection();
		Connection conn = sqlConnection.sqlConnection();

		String sql = "DELETE FROM flatTable WHERE idFlatTable=" + flatId;

//			System.out.println(sql);
		Statement statement = conn.createStatement();
		statement.executeUpdate(sql);

		conn.close();
	}

	public int countFlats() throws SQLException {

//		Flat flat = new Flat();

		int count = 0;

		SqlConnection sqlConnection = new SqlConnection();
		Connection conn = sqlConnection.sqlConnection();

		String sql = "SELECT COUNT(flatNumber) FROM flatTable";

//		System.out.println(sql);
		Statement statement = conn.createStatement();
		ResultSet rs = statement.executeQuery(sql);

		ResultSetMetaData md = rs.getMetaData();
		int cnt = md.getColumnCount();

		while (rs.next()) {

			for (int i = 1; i <= cnt; i++) {

				count = rs.getInt(1);

			}
		}

		conn.close();

		return count;
	}

	public double sumAllFlatsArea() throws SQLException {

		double allFlatsArea = 0;

		SqlConnection sqlConnection = new SqlConnection();
		Connection conn = sqlConnection.sqlConnection();

		String sql = "SELECT SUM(flatArea) FROM flatTable";

//		System.out.println(sql);
		Statement statement = conn.createStatement();
		ResultSet rs = statement.executeQuery(sql);

		ResultSetMetaData md = rs.getMetaData();
		int cnt = md.getColumnCount();

		while (rs.next()) {

			for (int i = 1; i <= cnt; i++) {

				allFlatsArea = rs.getDouble(1);

			}
		}

		conn.close();

		return allFlatsArea;
	}

	public List<Flat> getFlatsFromFlatTable() throws SQLException {

		List<Flat> flatList = new ArrayList<>();

		SqlConnection sqlConnection = new SqlConnection();
		Connection conn = sqlConnection.sqlConnection();

		String sql = "SELECT * FROM flatTable";

//		System.out.println(sql);
		Statement statement = conn.createStatement();

		ResultSet rs = statement.executeQuery(sql);

		ResultSetMetaData md = rs.getMetaData();
		int cnt = md.getColumnCount();

		int row = 0;

		while (rs.next()) {
			row++;
			Flat flat = new Flat();
			for (int i = 1; i <= cnt; i++) {
				flat.setIdFlatTable(rs.getInt(1));
				flat.setBuildingCorps(rs.getString(2));
				flat.setFlatRooms(rs.getInt(3));
				flat.setFlatFloor(rs.getInt(4));
				flat.setFlatNumber(rs.getInt(5));
				flat.setFlatArea(rs.getDouble(6));
				flat.setFlatSet(rs.getString(7));

			}
			flatList.add(flat);
		}

		conn.close();

		return flatList;
	}

	
	public List<Flat> getFlatsByCorpsFromFlatTable(String corps) throws SQLException {

		List<Flat> flatList = new ArrayList<>();

		SqlConnection sqlConnection = new SqlConnection();
		Connection conn = sqlConnection.sqlConnection();

		String sql = "SELECT * FROM flatTable WHERE buildingCorps = '" + corps + "'";

//		System.out.println(sql);
		Statement statement = conn.createStatement();

		ResultSet rs = statement.executeQuery(sql);

		ResultSetMetaData md = rs.getMetaData();
		int cnt = md.getColumnCount();

		int row = 0;

		while (rs.next()) {
			row++;
			Flat flat = new Flat();
			for (int i = 1; i <= cnt; i++) {
				flat.setIdFlatTable(rs.getInt(1));
				flat.setBuildingCorps(rs.getString(2));
				flat.setFlatRooms(rs.getInt(3));
				flat.setFlatFloor(rs.getInt(4));
				flat.setFlatNumber(rs.getInt(5));
				flat.setFlatArea(rs.getDouble(6));
				flat.setFlatSet(rs.getString(7));

			}
			flatList.add(flat);
		}

		conn.close();

		return flatList;
	}
	
	
	
	
	

	public List<Flat> getCorpsFromSettingsTable() throws SQLException {

		List<Flat> flatList = new ArrayList<>();

		SqlConnection sqlConnection = new SqlConnection();
		Connection conn = sqlConnection.sqlConnection();

		String sql = "SELECT * FROM settingsTable";

//		System.out.println(sql);
		Statement statement = conn.createStatement();

		ResultSet rs = statement.executeQuery(sql);

		ResultSetMetaData md = rs.getMetaData();
		int cnt = md.getColumnCount();

		int row = 0;

		while (rs.next()) {
			row++;
			Flat flat = new Flat();
			for (int i = 1; i <= cnt; i++) {
//				flat.setIdFlatTable(rs.getInt(1));
				flat.setBuildingCorps(rs.getString(2));
//				flat.setFlatRooms(rs.getInt(3));
//				flat.setFlatFloor(rs.getInt(4));
//				flat.setFlatNumber(rs.getInt(5));
//				flat.setFlatArea(rs.getDouble(6));
//				flat.setFlatSet(rs.getString(7));

			}
			flatList.add(flat);
		}

		conn.close();

		return flatList;
	}
	
	
	
	public List<Flat> getFlatsFromFlatBuyerTable() throws SQLException {

//		TimeZone.setDefault(TimeZone.getTimeZone("Europe/Minsk"));
		
		List<Flat> flatList = new ArrayList<>();

		SqlConnection sqlConnection = new SqlConnection();
		Connection conn = sqlConnection.sqlConnection();

		String sql = "SELECT * FROM flatBuyerTable";

//		System.out.println(sql);
		Statement statement = conn.createStatement();

		ResultSet rs = statement.executeQuery(sql);

		ResultSetMetaData md = rs.getMetaData();
		int cnt = md.getColumnCount();

		int row = 0;

		while (rs.next()) {
			row++;
			Flat flat = new Flat();
			for (int i = 1; i <= cnt; i++) {
				flat.setIdFlatTable(rs.getInt(2));
				flat.setFlatBuyerFirstname(rs.getString(3));
				flat.setFlatBuyerLastname(rs.getString(4));
				flat.setFlatBuyerSurname(rs.getString(5));
				flat.setFlatContractDate(rs.getDate(6));
				flat.setFlatContractNumber(rs.getString(7));
				flat.setFlatCost(rs.getInt(8));
				flat.setFlatSellerName(rs.getString(9));
				flat.setFlatNotes(rs.getString(10));

			}
			flatList.add(flat);
		}

		conn.close();

		return flatList;
	}	
	
	
	
	public List<Flat> getPaymentsFromBankTable() throws SQLException {

//		TimeZone.setDefault(TimeZone.getTimeZone("Europe/Minsk"));
		
		List<Flat> paymentList = new ArrayList<>();

		SqlConnection sqlConnection = new SqlConnection();
		Connection conn = sqlConnection.sqlConnection();

		String sql = "SELECT * FROM bankTable";

//		System.out.println(sql);
		Statement statement = conn.createStatement();

		ResultSet rs = statement.executeQuery(sql);

		ResultSetMetaData md = rs.getMetaData();
		int cnt = md.getColumnCount();

		int row = 0;

		while (rs.next()) {
			row++;
			Flat flat = new Flat();
			for (int i = 1; i <= cnt; i++) {
				flat.setIdFlatTable(rs.getInt(2));

				flat.setBankTablePaymentDate(rs.getDate(3));
				flat.setBankTablePaymentSum(rs.getDouble(4));

			}
			paymentList.add(flat);
		}

		conn.close();

		return paymentList;
	}	
	
	
	
	
	
	
	
	
	
	
	public Flat getFlatFromFlatBuyerTableByContract(String contractNumber, String contractDate) throws SQLException {

//		TimeZone.setDefault(TimeZone.getTimeZone("Europe/Minsk"));
		
		Flat flat = new Flat();

		SqlConnection sqlConnection = new SqlConnection();
		Connection conn = sqlConnection.sqlConnection();

		String sql = "SELECT * FROM flatBuyerTable WHERE flatContractNumber = '" + contractNumber + "' AND flatContractDate = DATE('" + contractDate + "')";

//		System.out.println(sql);
		Statement statement = conn.createStatement();

		ResultSet rs = statement.executeQuery(sql);

		ResultSetMetaData md = rs.getMetaData();
		int cnt = md.getColumnCount();

		int row = 0;

		while (rs.next()) {
			row++;

			for (int i = 1; i <= cnt; i++) {
				
				flat.setIdFlatTable(rs.getInt(2));

				flat.setFlatBuyerFirstname(rs.getString(3));
				flat.setFlatBuyerLastname(rs.getString(4));
				flat.setFlatBuyerSurname(rs.getString(5));
				flat.setFlatContractDate(rs.getDate(6));
				flat.setFlatContractNumber(rs.getString(7));
				flat.setFlatCost(rs.getInt(8));
				flat.setFlatSellerName(rs.getString(9));
				flat.setFlatNotes(rs.getString(10));

			}

		}

		conn.close();

		return flat;
	}
	
	
	
	
	public List<Flat> getFlatsFromFlatTableAndFlatBuyerDB() throws SQLException {

//		TimeZone.setDefault(TimeZone.getTimeZone("Europe/Minsk"));
		
		List<Flat> flatList = new ArrayList<>();

		SqlConnection sqlConnection = new SqlConnection();
		Connection conn = sqlConnection.sqlConnection();

		String sql = "SELECT flattable.*, flatbuyertable.* FROM flatTable LEFT JOIN flatbuyertable ON flatTable.idFlatTable = flatbuyertable.idflatTable";

//		System.out.println(sql);
		Statement statement = conn.createStatement();

		ResultSet rs = statement.executeQuery(sql);

		ResultSetMetaData md = rs.getMetaData();
		int cnt = md.getColumnCount();

		int row = 0;

		while (rs.next()) {
			row++;
			Flat flat = new Flat();
			for (int i = 1; i <= cnt; i++) {
				flat.setIdFlatTable(rs.getInt(1));
				flat.setBuildingCorps(rs.getString(2));
				flat.setFlatRooms(rs.getInt(3));
				flat.setFlatFloor(rs.getInt(4));
				flat.setFlatNumber(rs.getInt(5));
				flat.setFlatArea(rs.getDouble(6));
				flat.setFlatSet(rs.getString(7));

				flat.setFlatBuyerFirstname(rs.getString(10));
				flat.setFlatBuyerLastname(rs.getString(11));
				flat.setFlatBuyerSurname(rs.getString(12));
				flat.setFlatContractDate(rs.getDate(13));
				flat.setFlatContractNumber(rs.getString(14));
				flat.setFlatCost(rs.getInt(15));
				flat.setFlatSellerName(rs.getString(16));
				flat.setFlatNotes(rs.getString(17));

			}
			flatList.add(flat);
		}

		conn.close();

		return flatList;
	}
	
	public List<Flat> getFlatsFromBankTableByIdFlatTable(int idFlatTable) throws SQLException {

//		TimeZone.setDefault(TimeZone.getTimeZone("Europe/Minsk"));
		
		List<Flat> flatList = new ArrayList<>();

		SqlConnection sqlConnection = new SqlConnection();
		Connection conn = sqlConnection.sqlConnection();

		String sql = "SELECT * FROM bankTable WHERE idFlatTable = " + idFlatTable;

//		System.out.println(sql);
		Statement statement = conn.createStatement();

		ResultSet rs = statement.executeQuery(sql);

		ResultSetMetaData md = rs.getMetaData();
		int cnt = md.getColumnCount();

		int row = 0;

		while (rs.next()) {
			row++;
			Flat flat = new Flat();
			for (int i = 1; i <= cnt; i++) {
				flat.setIdFlatTable(rs.getInt(2));

				flat.setBankTablePaymentDate(rs.getDate(3));
				flat.setBankTablePaymentSum(rs.getDouble(4));

			}
			flatList.add(flat);
		}

		conn.close();

		return flatList;
	}
	
	
	
	

	public List<Flat> getFlatsByFlatSetFromDB(String flatSet) throws SQLException {

		
//		TimeZone.setDefault(TimeZone.getTimeZone("Europe/Minsk"));
		
		List<Flat> flatList = new ArrayList<>();

		SqlConnection sqlConnection = new SqlConnection();
		Connection conn = sqlConnection.sqlConnection();

		String sql = "SELECT flattable.*, flatbuyertable.* FROM flatTable LEFT JOIN flatbuyertable ON flatTable.idFlatTable = flatbuyertable.idflatTable WHERE flatSet = '"
				+ flatSet + "'";

//		System.out.println(sql);
		Statement statement = conn.createStatement();

		ResultSet rs = statement.executeQuery(sql);

		ResultSetMetaData md = rs.getMetaData();
		int cnt = md.getColumnCount();

		int row = 0;

		while (rs.next()) {
			row++;
			Flat flat = new Flat();
			for (int i = 1; i <= cnt; i++) {
				flat.setIdFlatTable(rs.getInt(1));
				flat.setBuildingCorps(rs.getString(2));
				flat.setFlatRooms(rs.getInt(3));
				flat.setFlatFloor(rs.getInt(4));
				flat.setFlatNumber(rs.getInt(5));
				flat.setFlatArea(rs.getDouble(6));
				flat.setFlatSet(rs.getString(7));

				flat.setFlatContractDate(rs.getDate(13));

			}
			flatList.add(flat);
		}

		conn.close();

		return flatList;
	}

	public Flat getFlatByFlatIdFromFlatBuyerTable(int flatId) throws SQLException {

//		TimeZone.setDefault(TimeZone.getTimeZone("Europe/Minsk"));
		
		Flat flat = new Flat();

		SqlConnection sqlConnection = new SqlConnection();
		Connection conn = sqlConnection.sqlConnection();

		String sql = "SELECT * FROM flatbuyertable WHERE idFlatTable = " + flatId;

//		System.out.println(sql);
		Statement statement = conn.createStatement();
		ResultSet rs = statement.executeQuery(sql);

		ResultSetMetaData md = rs.getMetaData();
		int cnt = md.getColumnCount();

		while (rs.next()) {

			for (int i = 1; i <= cnt; i++) {

				flat.setFlatBuyerFirstname(rs.getString(3));
				flat.setFlatBuyerLastname(rs.getString(4));
				flat.setFlatBuyerSurname(rs.getString(5));
				flat.setFlatContractDate(rs.getDate(6));
				flat.setFlatContractNumber(rs.getString(7));
				flat.setFlatCost(rs.getInt(8));
				flat.setFlatSellerName(rs.getString(9));
				flat.setFlatNotes(rs.getString(10));

			}
		}

		conn.close();

		return flat;
	}

	public List<Flat> getExpensesByFlatIdFromExpensesTable(int idFlatTable) throws SQLException {

//		TimeZone.setDefault(TimeZone.getTimeZone("Europe/Minsk"));
		
		List<Flat> flatList = new ArrayList<>();

		SqlConnection sqlConnection = new SqlConnection();
		Connection conn = sqlConnection.sqlConnection();

		String sql = "SELECT * FROM expensesTable WHERE idflatTable = " + idFlatTable;

//		System.out.println(sql);
		Statement statement = conn.createStatement();

		ResultSet rs = statement.executeQuery(sql);

		ResultSetMetaData md = rs.getMetaData();
		int cnt = md.getColumnCount();

		int row = 0;

		while (rs.next()) {
			row++;
			Flat flat = new Flat();
			for (int i = 1; i <= cnt; i++) {
				flat.setExpensesTableDate(rs.getDate(3));
				flat.setExpensesTableSum(rs.getInt(4));
				flat.setExpensesTableCategory(rs.getString(5));
				flat.setExpensesTableValue(rs.getString(6));

			}
			flatList.add(flat);
		}

		conn.close();

		return flatList;
	}

	public List<Flat> getExpensesByFlatIdAndCategoryFromExpensesTable(int idFlatTable, String category)
			throws SQLException {

//		TimeZone.setDefault(TimeZone.getTimeZone("Europe/Minsk"));
		
		List<Flat> flatList = new ArrayList<>();

		SqlConnection sqlConnection = new SqlConnection();
		Connection conn = sqlConnection.sqlConnection();

		String sql = "SELECT * FROM expensesTable WHERE idflatTable = " + idFlatTable + " AND expensesTableCategory = '"
				+ category + "'";

//		System.out.println(sql);
		Statement statement = conn.createStatement();

		ResultSet rs = statement.executeQuery(sql);

		ResultSetMetaData md = rs.getMetaData();
		int cnt = md.getColumnCount();

		int row = 0;

		while (rs.next()) {
			row++;
			Flat flat = new Flat();
			for (int i = 1; i <= cnt; i++) {
				flat.setExpensesTableDate(rs.getDate(3));
				flat.setExpensesTableSum(rs.getInt(4));
				flat.setExpensesTableCategory(rs.getString(5));
				flat.setExpensesTableValue(rs.getString(6));

			}
			flatList.add(flat);
		}

		conn.close();

		return flatList;
	}
	
	
	public List<Flat> getExpensesFromExpensesTable()
			throws SQLException {

//		TimeZone.setDefault(TimeZone.getTimeZone("Europe/Minsk"));
		
		List<Flat> flatList = new ArrayList<>();

		SqlConnection sqlConnection = new SqlConnection();
		Connection conn = sqlConnection.sqlConnection();

		String sql = "SELECT * FROM expensesTable";

//		System.out.println(sql);
		Statement statement = conn.createStatement();

		ResultSet rs = statement.executeQuery(sql);

		ResultSetMetaData md = rs.getMetaData();
		int cnt = md.getColumnCount();

		int row = 0;

		while (rs.next()) {
			row++;
			Flat flat = new Flat();
			for (int i = 1; i <= cnt; i++) {
				flat.setIdFlatTable(rs.getInt(2));
				flat.setExpensesTableDate(rs.getTimestamp(3));
				flat.setExpensesTableSum(rs.getInt(4));
				flat.setExpensesTableCategory(rs.getString(5));
				flat.setExpensesTableValue(rs.getString(6));

			}
			flatList.add(flat);
		}

		conn.close();

		return flatList;
	}
	
	

	
	
	

	public Flat getFlatByFlatIdFromFlatTableAndFlatBuyerDB(int flatId) throws SQLException {

//		TimeZone.setDefault(TimeZone.getTimeZone("Europe/Minsk"));
		
		Flat flat = new Flat();

		SqlConnection sqlConnection = new SqlConnection();
		Connection conn = sqlConnection.sqlConnection();

		String sql = "SELECT flattable.*, flatbuyertable.* FROM flatTable LEFT JOIN flatbuyertable ON flatTable.idFlatTable = flatbuyertable.idflatTable WHERE flattable.idflatTable = "
				+ flatId;

//		System.out.println(sql);
		Statement statement = conn.createStatement();
		ResultSet rs = statement.executeQuery(sql);

		ResultSetMetaData md = rs.getMetaData();
		int cnt = md.getColumnCount();

		while (rs.next()) {

			for (int i = 1; i <= cnt; i++) {
				flat.setIdFlatTable(rs.getInt(1));
				flat.setBuildingCorps(rs.getString(2));
				flat.setFlatRooms(rs.getInt(3));
				flat.setFlatFloor(rs.getInt(4));
				flat.setFlatNumber(rs.getInt(5));
				flat.setFlatArea(rs.getDouble(6));
				flat.setFlatSet(rs.getString(7));

				flat.setFlatCost(rs.getInt(8));
				flat.setFlatCost_m(rs.getDouble(9));
				flat.setIdflatBuyerTable(rs.getInt(8));
//				flat.setIdFlatTable(rs.getInt(9));

				flat.setFlatBuyerFirstname(rs.getString(10));
				flat.setFlatBuyerLastname(rs.getString(11));
				flat.setFlatBuyerSurname(rs.getString(12));
				flat.setFlatContractDate(rs.getDate(13));
				flat.setFlatContractNumber(rs.getString(14));
				flat.setFlatCost(rs.getInt(15));
				flat.setFlatSellerName(rs.getString(16));
				flat.setFlatNotes(rs.getString(17));

			}
		}

		conn.close();

		return flat;
	}

	public Flat getFlatByFlatIdFromFlatTable(int flatId) throws SQLException {

//		TimeZone.setDefault(TimeZone.getTimeZone("Europe/Minsk"));
		
		
		Flat flat = new Flat();

		SqlConnection sqlConnection = new SqlConnection();
		Connection conn = sqlConnection.sqlConnection();

		String sql = "SELECT * FROM flatTable WHERE idFlatTable=" + flatId;

//		System.out.println(sql);
		Statement statement = conn.createStatement();
		ResultSet rs = statement.executeQuery(sql);

		ResultSetMetaData md = rs.getMetaData();
		int cnt = md.getColumnCount();

		while (rs.next()) {

			for (int i = 1; i <= cnt; i++) {
				flat.setIdFlatTable(rs.getInt(1));
				flat.setBuildingCorps(rs.getString(2));
				flat.setFlatRooms(rs.getInt(3));
				flat.setFlatFloor(rs.getInt(4));
				flat.setFlatNumber(rs.getInt(5));
				flat.setFlatArea(rs.getDouble(6));
				flat.setFlatSet(rs.getString(7));

			}
		}

		conn.close();

		return flat;
	}
	
	
	public Flat getFlatByFlatIdFromFlatTableShortInfo(int flatId) throws SQLException {

//		TimeZone.setDefault(TimeZone.getTimeZone("Europe/Minsk"));
		
		Flat flat = new Flat();

		SqlConnection sqlConnection = new SqlConnection();
		Connection conn = sqlConnection.sqlConnection();

		String sql = "SELECT * FROM flatTable WHERE idFlatTable=" + flatId;

//		System.out.println(sql);
		Statement statement = conn.createStatement();
		ResultSet rs = statement.executeQuery(sql);

		ResultSetMetaData md = rs.getMetaData();
		int cnt = md.getColumnCount();

		while (rs.next()) {

			for (int i = 1; i <= cnt; i++) {
//				flat.setIdFlatTable(rs.getInt(1));
				flat.setBuildingCorps(rs.getString(2));
				flat.setFlatRooms(rs.getInt(3));
				flat.setFlatFloor(rs.getInt(4));
				flat.setFlatNumber(rs.getInt(5));
				flat.setFlatArea(rs.getDouble(6));
//				flat.setFlatSet(rs.getString(7));

			}
		}

		conn.close();

		return flat;
	}
	

	public String getFlatFromDbByColumnIndex(int index, Flat flat) {

		if (index == 0) {
			return Integer.toString(flat.getIdFlatTable());
		}

		if (index == 1) {
			return flat.getBuildingCorps();
		}

		if (index == 2) {
			return Integer.toString(flat.getFlatRooms());
		}

		if (index == 3) {
			return Integer.toString(flat.getFlatFloor());
		}

		if (index == 4) {
			return Integer.toString(flat.getFlatNumber());
		}

		if (index == 5) {
			return Double.toString(flat.getFlatArea());
		}

		if (index == 6) {
			return flat.getFlatSet();
		}

		if (index == 7) {
			return flat.getFlatBuyerFirstname();
		}

		if (index == 8) {
			return flat.getFlatBuyerLastname();
		}

		if (index == 9) {
			return flat.getFlatBuyerSurname();
		}

		if (index == 10) {
			DateFormat dateFormat = new SimpleDateFormat("dd.MM.yyyy");
			return dateFormat.format(flat.getFlatContractDate());
		}

		if (index == 11) {
			return flat.getFlatContractNumber();
		}

		if (index == 12) {
			return Integer.toString(flat.getFlatCost());
		}

//		if (index == 8) {
//			return Double.toString(flat.getFlatCost_m());
//		}
//		if (index == 13) {
//			return flat.getFlatContractDate().toString();
//		}
//		
//		if (index == 14) {
//			return flat.getFlatSellerName();
//		}
//		
//		if (index == 15) {
//			return flat.getFlatNotes();
//		}

		return null;
	}

	public void createExpensesFlat(Flat flat) throws SQLException {

		DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		
//		DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");

		int idflatTable = flat.getIdFlatTable();

		String expensesTableDate = dateFormat.format(flat.getExpensesTableDate());

		int expensesTableSum = flat.getExpensesTableSum();
//		String spendTableCurrency = flat.getSpendTableCurrency();
		String expensesTableCategory = flat.getExpensesTableCategory();
		String expensesTableValue = flat.getExpensesTableValue();

		SqlConnection sqlConnection = new SqlConnection();
		Connection conn = sqlConnection.sqlConnection();

		String sql = "INSERT INTO expensesTable (idflatTable, expensesTableDate, expensesTableSum, expensesTableCategory, expensesTableValue) VALUES ("
				+ idflatTable + ", TIME('" + expensesTableDate + "'), " + expensesTableSum + ", '"
				+ expensesTableCategory + "', '" + expensesTableValue + "')";

//		System.out.println(sql);
		Statement statement = conn.createStatement();
		statement.executeUpdate(sql);

		conn.close();
	}

	public int getExpensesFlatInfoFromExpensesTableByFlatId(int flatId, String expensesTableCategory)
			throws SQLException {

//		TimeZone.setDefault(TimeZone.getTimeZone("Europe/Minsk"));
		
		int sumExpensesTableCategory = 10;

		SqlConnection sqlConnection = new SqlConnection();
		Connection conn = sqlConnection.sqlConnection();

		String sql = "SELECT SUM(expensesTableSum) FROM expensesTable WHERE expensesTableCategory = '"
				+ expensesTableCategory + "' AND idflatTable = " + flatId;

//		System.out.println(sql);
		Statement statement = conn.createStatement();
		ResultSet rs = statement.executeQuery(sql);

		ResultSetMetaData md = rs.getMetaData();
		int cnt = md.getColumnCount();

		while (rs.next()) {

			for (int i = 1; i <= cnt; i++) {

				sumExpensesTableCategory = rs.getInt(1);

			}
		}

		conn.close();

		return sumExpensesTableCategory;

	}

	
	public double getPaymentSumFromBankTableByFlatTableId(int flatId)
			throws SQLException {

//		TimeZone.setDefault(TimeZone.getTimeZone("Europe/Minsk"));
		
		double paymentSum = 0;

		SqlConnection sqlConnection = new SqlConnection();
		Connection conn = sqlConnection.sqlConnection();

		String sql = "SELECT SUM(bankTablePaymentSum) FROM bankTable WHERE idflatTable = " + flatId;

//		System.out.println(sql);
		Statement statement = conn.createStatement();
		ResultSet rs = statement.executeQuery(sql);

		ResultSetMetaData md = rs.getMetaData();
		int cnt = md.getColumnCount();

		while (rs.next()) {

			for (int i = 1; i <= cnt; i++) {

				paymentSum = rs.getInt(1);

			}
		}

		conn.close();

		return paymentSum;

	}
	
	
	
	
	public String dateFormatForGrid(Date date) {
		
		DateFormat dateFormat = new SimpleDateFormat("dd.MM.yyyy"); //"dd.MM.yyyy HH:mm:ss"
//		DateFormat dateFormat = new SimpleDateFormat("dd.MM.yyyy HH:mm:ss"); //"dd.MM.yyyy HH:mm:ss"
		String dateStr = dateFormat.format(date); //flatFromList.getBankTablePaymentDate()
		
		return dateStr;
	}
	
	public String dateFormatForDB(Date date) {
		
		DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd"); //"dd.MM.yyyy HH:mm:ss"
		String dateStr = dateFormat.format(date); //flatFromList.getBankTablePaymentDate()
		
		return dateStr;
	}
	
	public String dateFormatForDB(String dateStr) {
		
		String dateReplace = dateStr.replace(".", "-");
		
		String[] contractDate = dateReplace.split("-");

		String dateNew = contractDate[2] + "." + contractDate[1] + "." + contractDate[0];
		
		return dateNew;
	}
	
	
	
//------------------------------------------	

	public void updateUser(User user) throws SQLException {
		// by username (id)

//		int idUser = user.getIduserTable();
		String userName = user.getUserName();
		String userFirstName = user.getUserFirstName();
		String userLastName = user.getUserLastName();
		String userPassword = user.getUserPassword();
		String userMail = user.getUserMail();
		String userPhone = user.getUserPhone();

		SqlConnection sqlConnection = new SqlConnection();
		Connection conn = sqlConnection.sqlConnection();

		String sql = "UPDATE usertable SET userFirstName='" + userFirstName + "', userLastName='" + userLastName + "',"
				+ " userPassword='" + userPassword + "', userMail='" + userMail + "'," + " userPhone=" + userPhone
				+ " WHERE userName='" + userName + "'";

//		String sql = "UPDATE usertable SET userFirstName='" + userFirstName + "' WHERE iduserTable='" + idUser + "'";

//		System.out.println(sql);
		Statement statement = conn.createStatement();
		statement.executeUpdate(sql);

		conn.close();
	}

	public boolean isUser(String userName) throws SQLException {

//		List<User> userList = new ArrayList<>();

		SqlConnection sqlConnection = new SqlConnection();
		Connection conn = sqlConnection.sqlConnection();

		String sql = "SELECT userName FROM userTable";

		Statement statement = conn.createStatement();

		ResultSet rs = statement.executeQuery(sql);

		ResultSetMetaData md = rs.getMetaData();
		int cnt = md.getColumnCount();

		int row = 0;

		while (rs.next()) {
			row++;
			User user = new User();
			for (int i = 1; i <= cnt; i++) {
//				user.setIduserTable(rs.getInt(1));
//				user.setUserFirstName(rs.getString(2));
//				user.setUserLastName(rs.getString(3));
//				user.setUserName(rs.getString(4));
//				user.setUserPassword(rs.getString(5));
//				user.setUserMail(rs.getString(6));
//				user.setUserPhone(rs.getString(7));

				user.setUserName(rs.getString(1));

				if (user.getUserName().equals(userName)) {
					conn.close();
					return true;
				}

			}
//			userList.add(user);
		}

		conn.close();

//		return userList;

		return false;
	}

}
