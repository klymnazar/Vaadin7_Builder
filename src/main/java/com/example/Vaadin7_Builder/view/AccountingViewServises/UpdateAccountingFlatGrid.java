package com.example.Vaadin7_Builder.view.AccountingViewServises;

import java.sql.SQLException;
import java.text.DecimalFormat;
import java.util.Iterator;
import java.util.List;

import com.example.Vaadin7_Builder.model.Flat;
import com.example.Vaadin7_Builder.service.FlatService;
import com.vaadin.ui.Grid;
import com.vaadin.ui.Grid.FooterRow;

public class UpdateAccountingFlatGrid {

	FlatService flatService = new FlatService();

	private DecimalFormat decimalFormat = new DecimalFormat("0.00");

	private String generalExpenses = "General";
	private String bcExpenses = "B.C.";
	private String cmExpenses = "C.M.";

	public void updateAccountingFlatGrid(Grid flatGrid)
			throws SQLException {

		flatGrid.getContainerDataSource().removeAllItems();

		double flatArea = 0;
		int flatCost = 0;
		int expensesRow = 0;
		int availableSumRow = 0;
		double mAvailableSumRow = 0;
		int generalExpensesRow = 0;
		double mGeneralExpensesRow = 0;
		int bcExpensesRow = 0;
		double mBCExpensesRow = 0;
		int cmExpensesRow = 0;
		double mCMExpensesRow = 0;
		double mTotalBCExpensesRow = 0;
		double mTotalCMExpensesRow = 0;
		double bankPaymentSum = 0;
		int number = 1;

		List<Flat> flatList = flatService.getFlatsFromOrderedFlatTable();
		
		Iterator<Flat> itr = flatList.iterator();
		while (itr.hasNext()) {
			Flat flatFromList = itr.next();

			Flat flatFromBuyerTable = flatService.getFlatByFlatIdFromFlatBuyerTable(flatFromList.getIdFlatTable());

			int expensesGeneral = flatService
					.getExpensesFlatInfoFromExpensesTableByFlatId(flatFromList.getIdFlatTable(), generalExpenses);
			int expensesBC = flatService.getExpensesFlatInfoFromExpensesTableByFlatId(flatFromList.getIdFlatTable(),
					bcExpenses);
			int expensesCM = flatService.getExpensesFlatInfoFromExpensesTableByFlatId(flatFromList.getIdFlatTable(),
					cmExpenses);
			double paymentSum = flatService.getPaymentSumFromBankTableByFlatTableId(flatFromList.getIdFlatTable());

			double flatCost_m$ = flatFromBuyerTable.getFlatCost() / flatFromList.getFlatArea();
			if (flatCost_m$ == 0) {
				flatCost_m$ = 0.000000001;
			}

			int expenses = expensesGeneral + expensesBC + expensesCM;

			int availableSum = flatFromBuyerTable.getFlatCost() - expenses;

			flatArea = flatArea + flatFromList.getFlatArea();
			flatCost = flatCost + flatFromBuyerTable.getFlatCost();
			expensesRow = expensesRow + expenses;
			availableSumRow = availableSumRow + availableSum;
			mAvailableSumRow = mAvailableSumRow + availableSum / flatCost_m$;
			generalExpensesRow = generalExpensesRow + expensesGeneral;
			mGeneralExpensesRow = mGeneralExpensesRow + (expensesGeneral) / flatCost_m$;
			bcExpensesRow = bcExpensesRow + expensesBC;
			mBCExpensesRow = mBCExpensesRow + (expensesBC) / flatCost_m$;
			cmExpensesRow = cmExpensesRow + expensesCM;
			mCMExpensesRow = mCMExpensesRow + (expensesCM) / flatCost_m$;
			mTotalBCExpensesRow = mTotalBCExpensesRow + (expensesBC / flatCost_m$)
					+ (expensesGeneral / flatCost_m$ / 2);
			mTotalCMExpensesRow = mTotalCMExpensesRow + (expensesCM / flatCost_m$)
					+ (expensesGeneral / flatCost_m$ / 2);
			bankPaymentSum = bankPaymentSum + paymentSum;

			flatGrid.addRow(number, flatFromList.getIdFlatTable(), flatFromList.getBuildingCorps(),
					flatFromList.getFlatRooms(), flatFromList.getFlatFloor(), flatFromList.getFlatNumber(),
					flatFromList.getFlatArea(), flatFromList.getFlatSet(), flatFromBuyerTable.getFlatBuyerFirstname(),
					flatFromBuyerTable.getFlatBuyerLastname(), flatFromBuyerTable.getFlatBuyerSurname(),
					flatFromBuyerTable.getFlatNotes(), flatFromBuyerTable.getFlatContractDate(),
					flatFromBuyerTable.getFlatContractNumber(), flatCost_m$, flatFromBuyerTable.getFlatCost(), expenses,
					"Expenses", availableSum, availableSum / flatCost_m$, flatFromBuyerTable.getFlatSellerName(),
					expensesGeneral, expensesGeneral / flatCost_m$, expensesBC, expensesBC / flatCost_m$,
					expensesBC / flatCost_m$ + expensesGeneral / flatCost_m$ / 2, expensesCM, expensesCM / flatCost_m$,
					expensesCM / flatCost_m$ + expensesGeneral / flatCost_m$ / 2, paymentSum,
					paymentSum / flatFromList.getFlatArea());

			number++;
		}

		FooterRow flatGridFooterRow = flatGrid.getFooterRow(0);
		
		
		flatGridFooterRow.getCell("buildingCorps").setText("Total:");
		flatGridFooterRow.getCell("flatArea").setText(decimalFormat.format(flatArea));
		flatGridFooterRow.getCell("$, flatCost").setText(decimalFormat.format(flatCost));
		flatGridFooterRow.getCell("$, expenses").setText(decimalFormat.format(expensesRow));
		flatGridFooterRow.getCell("$, availableSum").setText(decimalFormat.format(availableSumRow));
		flatGridFooterRow.getCell("m2, availableSum").setText(decimalFormat.format(mAvailableSumRow));
		flatGridFooterRow.getCell("$, 50/50GeneralExpenses").setText(decimalFormat.format(generalExpensesRow));
		flatGridFooterRow.getCell("m2, 50/50GeneralExpenses").setText(decimalFormat.format(mGeneralExpensesRow));
		flatGridFooterRow.getCell("$, BCExpenses").setText(decimalFormat.format(bcExpensesRow));
		flatGridFooterRow.getCell("m2, BCExpenses").setText(decimalFormat.format(mBCExpensesRow));
		flatGridFooterRow.getCell("m2, totalBCExpenses").setText(decimalFormat.format(mTotalBCExpensesRow) + "m2"
				+ ", left - " + decimalFormat.format(flatArea * 70 / 100 - mTotalBCExpensesRow) + "m2");
		flatGridFooterRow.getCell("$, CMExpenses").setText(decimalFormat.format(cmExpensesRow));
		flatGridFooterRow.getCell("m2, CMExpenses").setText(decimalFormat.format(mCMExpensesRow));
		flatGridFooterRow.getCell("m2, totalCMExpenses").setText(decimalFormat.format(mTotalCMExpensesRow) + "m2"
				+ ", left - " + decimalFormat.format(flatArea * 30 / 100 - mTotalCMExpensesRow) + "m2");
		flatGridFooterRow.getCell("bankPaymentSum").setText(decimalFormat.format(bankPaymentSum));

	}

}
