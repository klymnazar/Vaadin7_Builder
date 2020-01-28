package com.example.Vaadin7_Builder.view.AccountingViewServises;

import java.sql.SQLException;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import com.example.Vaadin7_Builder.model.Flat;
import com.example.Vaadin7_Builder.service.FlatService;
import com.vaadin.ui.Button;
import com.vaadin.ui.Grid;
import com.vaadin.ui.Grid.FooterRow;

public class ExpensesInfoButton extends IncomeExpensesInfo {

	FlatService flatService = new FlatService();

	private DecimalFormat decimalFormat = new DecimalFormat("0.00");

	@Override
	public Grid infoGrid(List<Flat> flatList) throws SQLException {

		Grid expensesWindowInfoGrid = new Grid();
		expensesWindowInfoGrid.setSizeFull();

		expensesWindowInfoGrid.addColumn("expensesDate", String.class);
		expensesWindowInfoGrid.addColumn("sum", Integer.class);
		expensesWindowInfoGrid.addColumn("m2", Double.class);
		expensesWindowInfoGrid.addColumn("category", String.class);
		expensesWindowInfoGrid.addColumn("value", String.class);

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

				expensesWindowInfoGrid.addRow(flatService.dateFormatForGrid(flatFromList.getExpensesTableDate()),
						flatFromList.getExpensesTableSum(), m2, flatFromList.getExpensesTableCategory(),
						flatFromList.getExpensesTableValue());

				expensesSum = expensesSum + flatFromList.getExpensesTableSum();
				expensesM2 = expensesM2 + m2;

			}
		} catch (NumberFormatException e4) {
			// TODO Auto-generated catch block
			e4.printStackTrace();
		}

		FooterRow expensesWindowGridFooterRow = expensesWindowInfoGrid.prependFooterRow();
		expensesWindowGridFooterRow.getCell("expensesDate").setText("Total Expenses: ");
		expensesWindowGridFooterRow.getCell("sum").setText(decimalFormat.format(expensesSum).replace(",", "."));
		expensesWindowGridFooterRow.getCell("m2").setText(decimalFormat.format(expensesM2).replace(",", "."));

		return expensesWindowInfoGrid;
	}

	public Button expensesInfoButton() throws SQLException {

		List<Flat> selectAllflatsList = flatService.getExpensesFromExpensesTable();

		Button expensesInfoButton = infoButton("Expenses Info", "Expenses Info", selectAllflatsList);

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

}
