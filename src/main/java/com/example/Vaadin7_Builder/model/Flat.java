package com.example.Vaadin7_Builder.model;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.util.Date;

public class Flat {

	private int idFlatTable;
	private String flatSet;
	private String buildingCorps;
	private int flatFloor;
	private int flatNumber;
	private double flatArea;
	private int flatRooms;

	private int idflatBuyerTable;

	private String flatBuyerFirstname;
	private String flatBuyerLastname;
	private String flatBuyerSurname;
	private String flatContractNumber;
	private Date flatContractDate;
	private int flatCost;
	private double flatCost_m;
	private String flatSellerName;
	private String flatNotes;

	private int flatGeneralExpenses;
	private int flatBCExpenses;
	private int flatCMExpenses;

	private Date expensesTableDate;
	private int expensesTableSum;
	private String expensesTableCategory;
	private String expensesTableValue;

//	private Date bankTableContractDate;
//	private String bankTableContractNumber;
	private Date bankTablePaymentDate;
	private double bankTablePaymentSum;
	
	private Date new_date_tablecol;
	private Date new_date_tablecol1;
	
	
	public int getIdFlatTable() {
		return idFlatTable;
	}

	public void setIdFlatTable(int idFlatTable) {
		this.idFlatTable = idFlatTable;
	}

	public String getFlatSet() {
		return flatSet;
	}

	public void setFlatSet(String flatSet) {
		this.flatSet = flatSet;
	}

	public String getBuildingCorps() {
		return buildingCorps;
	}

	public void setBuildingCorps(String buildingCorps) {
		this.buildingCorps = buildingCorps;
	}

	public int getFlatFloor() {
		return flatFloor;
	}

	public void setFlatFloor(int flatFloor) {
		this.flatFloor = flatFloor;
	}

	public int getFlatNumber() {
		return flatNumber;
	}

	public void setFlatNumber(int flatNumber) {
		this.flatNumber = flatNumber;
	}

	public double getFlatArea() {
		return flatArea;
	}

	public void setFlatArea(double flatArea) {
		this.flatArea = flatArea;
	}

	public int getFlatRooms() {
		return flatRooms;
	}

	public void setFlatRooms(int flatRooms) {
		this.flatRooms = flatRooms;
	}

	public int getIdflatBuyerTable() {
		return idflatBuyerTable;
	}

	public void setIdflatBuyerTable(int idflatBuyerTable) {
		this.idflatBuyerTable = idflatBuyerTable;
	}

	public String getFlatBuyerFirstname() {
		return flatBuyerFirstname;
	}

	public void setFlatBuyerFirstname(String flatBuyerFirstname) {
		this.flatBuyerFirstname = flatBuyerFirstname;
	}

	public String getFlatBuyerLastname() {
		return flatBuyerLastname;
	}

	public void setFlatBuyerLastname(String flatBuyerLastname) {
		this.flatBuyerLastname = flatBuyerLastname;
	}

	public String getFlatBuyerSurname() {
		return flatBuyerSurname;
	}

	public void setFlatBuyerSurname(String flatBuyerSurname) {
		this.flatBuyerSurname = flatBuyerSurname;
	}

	public String getFlatContractNumber() {
		return flatContractNumber;
	}

	public void setFlatContractNumber(String flatContractNumber) {
		this.flatContractNumber = flatContractNumber;
	}

	public Date getFlatContractDate() {
		return flatContractDate;
	}

	public void setFlatContractDate(Date flatContractDate) {
		this.flatContractDate = flatContractDate;
	}

	public int getFlatCost() {
		return flatCost;
	}

	public void setFlatCost(int flatCost) {
		this.flatCost = flatCost;
	}

	public double getFlatCost_m() {
		return flatCost_m;
	}

	public void setFlatCost_m(double flatCost_m) {
		this.flatCost_m = flatCost_m;
	}

	public String getFlatSellerName() {
		return flatSellerName;
	}

	public void setFlatSellerName(String flatSellerName) {
		this.flatSellerName = flatSellerName;
	}

	public String getFlatNotes() {
		return flatNotes;
	}

	public void setFlatNotes(String flatNotes) {
		this.flatNotes = flatNotes;
	}

	public int getFlatGeneralExpenses() {
		return flatGeneralExpenses;
	}

	public void setFlatGeneralExpenses(int flatGeneralExpenses) {
		this.flatGeneralExpenses = flatGeneralExpenses;
	}

	public int getFlatBCExpenses() {
		return flatBCExpenses;
	}

	public void setFlatBCExpenses(int flatBCExpenses) {
		this.flatBCExpenses = flatBCExpenses;
	}

	public int getFlatCMExpenses() {
		return flatCMExpenses;
	}

	public void setFlatCMExpenses(int flatCMExpenses) {
		this.flatCMExpenses = flatCMExpenses;
	}

	public Date getExpensesTableDate() {
		return expensesTableDate;
	}

	public void setExpensesTableDate(Date expensesTableDate) {
		this.expensesTableDate = expensesTableDate;
	}

	public int getExpensesTableSum() {
		return expensesTableSum;
	}

	public void setExpensesTableSum(int expensesTableSum) {
		this.expensesTableSum = expensesTableSum;
	}

	public String getExpensesTableCategory() {
		return expensesTableCategory;
	}

	public void setExpensesTableCategory(String expensesTableCategory) {
		this.expensesTableCategory = expensesTableCategory;
	}

	public String getExpensesTableValue() {
		return expensesTableValue;
	}

	public void setExpensesTableValue(String expensesTableValue) {
		this.expensesTableValue = expensesTableValue;
	}

	
	
	
	
	
//	public Date getBankTableContractDate() {
//		return bankTableContractDate;
//	}
//
//	public void setBankTableContractDate(Date bankTableContractDate) {
//		this.bankTableContractDate = bankTableContractDate;
//	}
//	
//	
//	public String getBankTableContractNumber() {
//		return bankTableContractNumber;
//	}
//
//	public void setBankTableContractNumber(String bankTableContractNumber) {
//		this.bankTableContractNumber = bankTableContractNumber;
//	}
	
	public Date getNew_date_tablecol() throws ParseException {
		
//		DateFormat dateFormat = new SimpleDateFormat("dd.MM.yyyy HH:mm:ss");
//		
//		String strDate = dateFormat.format(new_date_tablecol);
//		
//		String dateStr = strDate;
//		
//		Date new_date_tablecol = new SimpleDateFormat("dd.MM.yyyy HH:mm:ss").parse(dateStr);
		
		return new_date_tablecol;
	}

	public void setNew_date_tablecol(Date new_date_tablecol) throws ParseException {
		
//		DateFormat dateFormat = new SimpleDateFormat("dd-MM-yyyy HH:mm:ss");
//		
//		String strDate = dateFormat.format(new_date_tablecol);
//		
//		String dateStr = strDate;
//		
//		new_date_tablecol = new SimpleDateFormat("dd.MM.yyyy HH:mm:ss").parse(dateStr);
		
		
		
		
		this.new_date_tablecol = new_date_tablecol;
	}
	
	public Date getNew_date_tablecol1() {
		return new_date_tablecol1;
	}

	public void setNew_date_tablecol1(Date new_date_tablecol1) {
		this.new_date_tablecol1 = new_date_tablecol1;
	}
	
	
	
	
	
	

	public Date getBankTablePaymentDate() {
		return bankTablePaymentDate;
	}

	public void setBankTablePaymentDate(Date bankTablePaymentDate) {
		this.bankTablePaymentDate = bankTablePaymentDate;
	}
	
	
	public double getBankTablePaymentSum() {
		return bankTablePaymentSum;
	}

	public void setBankTablePaymentSum(double bankTablePaymentSum) {
		this.bankTablePaymentSum = bankTablePaymentSum;
	}
	
	
	
}
