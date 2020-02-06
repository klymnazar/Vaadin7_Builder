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
import com.vaadin.ui.Grid.SingleSelectionModel;
import com.vaadin.ui.HorizontalLayout;
import com.vaadin.ui.TextArea;
import com.vaadin.ui.TextField;

public class ExpensesInfoButton extends IncomeExpensesInfo {
	
	FlatService flatService = new FlatService();

	private DecimalFormat decimalFormat = new DecimalFormat("0.00");

	@Override
	public Grid infoGrid(List<Flat> flatList) throws SQLException {

		Grid infoGrid = new Grid();
		infoGrid.setSizeFull();

		infoGrid.addColumn("idExpensesTable", Integer.class);
		infoGrid.addColumn("expensesDate", String.class);
		infoGrid.addColumn("sum", Integer.class);
		infoGrid.addColumn("m2", Double.class);
		infoGrid.addColumn("category", String.class);
		infoGrid.addColumn("value", String.class);

		FooterRow infoGridFooterRow = infoGrid.prependFooterRow();
		
		updateInfoGrid(infoGrid, flatList);
		

		
		return infoGrid;
	}

	public Button expensesInfoButton() throws SQLException {

		List<Flat> selectAllflatsList = flatService.getExpensesFromExpensesTable();
		
		////////
		
		List<Flat> updateFlatExpensesList = flatService.getExpensesFromExpensesTable();
		
//		List<Flat> selectAllflatsList = flatService.getExpensesFromExpensesTable();
		
		
		
		/////////

		Button expensesInfoButton = infoButton("Expenses Info", "Expenses Info", selectAllflatsList, updateFlatExpensesList);

		return expensesInfoButton;

	}

	@Override
	public List<Flat> expensesWindowCorpsComboBoxInfoByFlatId(int flatId) {
		// TODO Auto-generated method stub

		List<Flat> flatListExpensesByFlatId = new ArrayList<>();

		try {

			flatListExpensesByFlatId = flatService.getExpensesByFlatIdFromExpensesTable(flatId);

		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		return flatListExpensesByFlatId;
	}

	@Override
	public List<Flat> expensesWindowFlatComboBoxInfoByCorpsAndNumber(String corps, int number) {
		// TODO Auto-generated method stub

		Flat flatFromComboBox = new Flat();

		List<Flat> expensesList = new ArrayList<>();

		try {

			flatFromComboBox = flatService.getFlatByCorpsAndNumberFromFlatTable(corps, number);
			expensesList = flatService.getExpensesByFlatIdFromExpensesTable(flatFromComboBox.getIdFlatTable());

		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		return expensesList;
	}

	@Override
	public void updateInfoGrid(Grid infoGrid, List<Flat> flatList) {
		// TODO Auto-generated method stub
		
		

			
			
			infoGrid.getContainerDataSource().removeAllItems();
			
			FooterRow infoGridFooterRow = infoGrid.getFooterRow(0);
			
			
			try {
				flatService.getExpensesFromExpensesTable();
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			
			
			int expensesSum = 0;
			double expensesM2 = 0;

			try {

				Iterator<Flat> itr = flatList.iterator();
				while (itr.hasNext()) {
					Flat flatFromList = itr.next();

					double m2Cost = flatService.getFlatByFlatIdFromFlatBuyerTable(flatFromList.getIdFlatTable())
								.getFlatCost()
								/ flatService.getFlatByFlatIdFromFlatTable(flatFromList.getIdFlatTable()).getFlatArea();
						double m2 = flatFromList.getExpensesTableSum() / m2Cost;
					

//					infoWindowInfoGrid.addRow(flatFromList.getIdExpensesTable(), flatService.dateFormatForGrid(flatFromList.getExpensesTableDate()),
//							flatFromList.getExpensesTableSum(), m2, flatFromList.getExpensesTableCategory(),
//							flatFromList.getExpensesTableValue());
					
					infoGrid.addRow(flatFromList.getIdExpensesTable(), flatService.dateFormatForGrid(flatFromList.getExpensesTableDate()),
							flatFromList.getExpensesTableSum(), m2, flatFromList.getExpensesTableCategory(),
							flatFromList.getExpensesTableValue());

					expensesSum = expensesSum + flatFromList.getExpensesTableSum();
					expensesM2 = expensesM2 + m2;

				}
			} catch (SQLException | NumberFormatException e4) {
				// TODO Auto-generated catch block
				e4.printStackTrace();
			}

//			FooterRow infoWindowGridFooterRow = infoWindowInfoGrid.prependFooterRow();
			
//			infoGrid.removeFooterRow(1);

			
//			FooterRow infoWindowGridFooterRow = infoGrid.prependFooterRow();
			
			
			infoGridFooterRow.getCell("expensesDate").setText("Total Expenses: ");
			infoGridFooterRow.getCell("sum").setText(decimalFormat.format(expensesSum).replace(",", "."));
			infoGridFooterRow.getCell("m2").setText(decimalFormat.format(expensesM2).replace(",", "."));
			
			
			
			
//			infoGrid.addRow(flatFromList.getIdExpensesTable(), flatService.dateFormatForGrid(flatFromList.getExpensesTableDate()),
//					flatFromList.getExpensesTableSum(), m2, flatFromList.getExpensesTableCategory(),
//					flatFromList.getExpensesTableValue());
			
	}			
			
		
		
		
		
		


	@Override
	public int getIdFlatTableIntFromSelectedRow(Grid flatGrid) {
		// TODO Auto-generated method stub
		Object selected = ((SingleSelectionModel) flatGrid.getSelectionModel()).getSelectedRow();
		String idFlatTableFromSelectedRow = flatGrid.getContainerDataSource().getItem(selected)
				.getItemProperty("idExpensesTable").getValue().toString();
		int idFlatTableIntFromSelectedRow = Integer.parseInt(idFlatTableFromSelectedRow);
		
		
		
		return idFlatTableIntFromSelectedRow;

	}

	@Override
	public Grid infoGrid(int idFlatTableIntFromSelectedRow) {
		// TODO Auto-generated method stub
		return null;
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
			flat = flatService.getFlatByIdFromExpensesTable(getIdExpensesTableIntFromSelectedRow(infoGrid));
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		expensesDateField.setValue(flat.getExpensesTableDate());
		expensesSumTextField.setValue("" + flat.getExpensesTableSum());
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
//	}

//	@Override
//	public void setExpensesValuesBySelectedRow(Grid infoGrid, DateField expensesDateField, TextField expensesSumTextField, ComboBox expensesCategoryComboBox, ComboBox expensesValueComboBox, TextArea expensesValueTextArea) {
//		// TODO Auto-generated method stub
//		
//		
//		infoGrid.addSelectionListener(select -> {
//			Flat flat = new Flat();
//			
//			try {
//				flat = flatService.getFlatByIdFromExpensesTable(getIdExpensesTableIntFromSelectedRow(infoGrid));
//			} catch (SQLException e) {
//				// TODO Auto-generated catch block
//				e.printStackTrace();
//			}
//			
//			expensesDateField.setValue(flat.getExpensesTableDate());
//			expensesSumTextField.setValue("" + flat.getExpensesTableSum());
//			expensesCategoryComboBox.setValue(flat.getExpensesTableCategory());
//			expensesValueComboBox.setValue(flat.getExpensesTableValue());
//			expensesValueTextArea.setValue(flat.getExpensesTableValueTA());
//			
//		});
//			
//
//		
//		
//	}

//	@Override
//	public void setExpensesValuesBySelectedRow() {
//		// TODO Auto-generated method stub
//		
//	}


}
