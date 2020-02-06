package com.example.Vaadin7_Builder.view.AccountingViewServises;

import java.sql.SQLException;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import com.example.Vaadin7_Builder.model.Flat;
import com.example.Vaadin7_Builder.service.FlatService;
import com.vaadin.ui.Button;
import com.vaadin.ui.ComboBox;
import com.vaadin.ui.DateField;
import com.vaadin.ui.Grid;
import com.vaadin.ui.Grid.FooterRow;
import com.vaadin.ui.HorizontalLayout;
import com.vaadin.ui.TextArea;
import com.vaadin.ui.TextField;

public class IncomeSumButton extends IncomeExpensesInfo {
//дохід

	FlatService flatService = new FlatService();

	private DecimalFormat decimalFormat = new DecimalFormat("0.00");

	@Override
	public Grid infoGrid(List<Flat> flatList) throws SQLException {

		Grid incomeWindowInfoGrid = new Grid();
		incomeWindowInfoGrid.setSizeFull();

		incomeWindowInfoGrid.addColumn("buildingCorps", String.class);
		incomeWindowInfoGrid.addColumn("flatNumber", Integer.class);
		incomeWindowInfoGrid.addColumn("flatArea", Double.class);
		incomeWindowInfoGrid.addColumn("buyer", String.class);
		incomeWindowInfoGrid.addColumn("flatCost", Integer.class);
		incomeWindowInfoGrid.addColumn("m2_flatCost", Double.class);

		double flatArea = 0;
		int flatCost = 0;
		double flatCostM2 = 0;

		try {

			Iterator<Flat> itr = flatList.iterator();
			while (itr.hasNext()) {
				Flat flatFromList = itr.next();

				Flat flat = flatService.getFlatByFlatIdFromFlatTable(flatFromList.getIdFlatTable());

				incomeWindowInfoGrid.addRow(flat.getBuildingCorps(), flat.getFlatNumber(), flat.getFlatArea(),
						flatFromList.getFlatBuyerFirstname() + " " + flatFromList.getFlatBuyerLastname() + " "
								+ flatFromList.getFlatBuyerSurname(),
						flatFromList.getFlatCost(), flatFromList.getFlatCost() / flat.getFlatArea());

				flatArea = flatArea + flat.getFlatArea();
				flatCost = flatCost + flatFromList.getFlatCost();
				flatCostM2 = flatCost / flatArea;

			}
		} catch (NumberFormatException e4) {
			// TODO Auto-generated catch block
			e4.printStackTrace();
		}

		FooterRow incomeWindowGridFooterRow = incomeWindowInfoGrid.prependFooterRow();
		incomeWindowGridFooterRow.getCell("flatNumber").setText("Total: ");
		incomeWindowGridFooterRow.getCell("flatArea").setText(decimalFormat.format(flatArea).replace(",", "."));
		incomeWindowGridFooterRow.getCell("flatCost").setText(decimalFormat.format(flatCost).replace(",", "."));
		incomeWindowGridFooterRow.getCell("m2_flatCost").setText(decimalFormat.format(flatCostM2).replace(",", "."));

		return incomeWindowInfoGrid;
	}

////////////////////////////////////////

	public Button incomeSumButton() throws SQLException {

		List<Flat> selectAllflatsList = flatService.getFlatsFromFlatBuyerTable();

		
		
		List<Flat> updateFlatBuyerList = flatService.getFlatsFromFlatBuyerTable();
		
		
		
		
		
		Button incomeSumButton = infoButton("Income Sum", "Income Info", selectAllflatsList, updateFlatBuyerList);

		return incomeSumButton;

	}

	@Override
	public List<Flat> expensesWindowCorpsComboBoxInfoByFlatId(int flatId) {
		// TODO Auto-generated method stub

		Flat flatIncomeByFlatId = new Flat();

		List<Flat> flatListIncomeByFlatId = new ArrayList<>();

		try {
			flatIncomeByFlatId = flatService.getFlatByFlatIdFromFlatBuyerTable(flatId);
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		if (flatId == flatIncomeByFlatId.getIdFlatTable()) {

			flatListIncomeByFlatId.add(flatIncomeByFlatId);

		}

		return flatListIncomeByFlatId;

	}

	@Override
	public List<Flat> expensesWindowFlatComboBoxInfoByCorpsAndNumber(String corps, int number) {
		// TODO Auto-generated method stub

		Flat flatFromComboBox = new Flat();
		Flat flatFromBuyerTable = new Flat();

		List<Flat> expensesList = new ArrayList<>();

		try {

			flatFromComboBox = flatService.getFlatByCorpsAndNumberFromFlatTable(corps, number);

			flatFromBuyerTable = flatService.getFlatByFlatIdFromFlatBuyerTable(flatFromComboBox.getIdFlatTable());

			if (flatFromComboBox.getIdFlatTable() == flatFromBuyerTable.getIdFlatTable()) {

				expensesList.add(flatFromBuyerTable);

			}

		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		return expensesList;

	}

	@Override
	public void updateInfoGrid(Grid infoGrid, List<Flat> flatList) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public Grid infoGrid(int idFlatTableIntFromSelectedRow) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public int getIdFlatTableIntFromSelectedRow(Grid flatGrid) {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public void addInfoGrid(HorizontalLayout expensesWindowInfoHorizontalLayout, Grid flatGrid) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void setValuesBySelectedRow(Grid infoGrid, DateField expensesDateField,
			TextField expensesSumTextField, ComboBox expensesCategoryComboBox, ComboBox expensesValueComboBox,
			TextArea expensesValueTextArea) {
		Flat flat = new Flat();
		
		try {
			flat = flatService.getFlatByFlatIdFromFlatBuyerTable(30);
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		expensesDateField.setValue(flat.getFlatContractDate());
		expensesSumTextField.setValue("" + flat.getFlatCost());
		expensesCategoryComboBox.setValue(flat.getExpensesTableCategory());
		expensesValueComboBox.setValue(flat.getExpensesTableValue());
		expensesValueTextArea.setValue(flat.getExpensesTableValueTA());
		
	}

//	@Override
//	public Flat setExpensesValuesBySelectedRow(Grid infoGrid, Flat flat) {
//		try {
//			flat = flatService.getFlatByIdFromExpensesTable(getIdExpensesTableIntFromSelectedRow(infoGrid));
//		} catch (SQLException e) {
//			e.printStackTrace();
//		}
//		
//		return flat;
//		return null;
//	}

//	@Override
//	public void setExpensesValuesBySelectedRow() {
//		// TODO Auto-generated method stub
//		
//	}

//	@Override
//	public void setExpensesValuesBySelectedRow(Grid infoGrid, DateField expensesDateField,
//			TextField expensesSumTextField, ComboBox expensesCategoryComboBox, ComboBox expensesValueComboBox,
//			TextArea expensesValueTextArea) {
//		// TODO Auto-generated method stub
//		
//	}

//	@Override
//	public List<Flat> expensesWindowAllExpenses() {
//		// TODO Auto-generated method stub
//		return null;
//	}

}
