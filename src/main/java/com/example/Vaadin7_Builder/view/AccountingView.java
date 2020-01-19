package com.example.Vaadin7_Builder.view;

import java.sql.SQLException;
import java.text.DateFormat;
import java.text.DecimalFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Iterator;
import java.util.List;
//import java.util.TimeZone;
import com.example.Vaadin7_Builder.model.Flat;
import com.example.Vaadin7_Builder.service.FlatService;
import com.example.Vaadin7_Builder.view.AccountingViewServises.AddExpensesButton;
import com.example.Vaadin7_Builder.view.AccountingViewServises.ExpensesInfoButton;
import com.vaadin.data.Property.ReadOnlyException;
import com.vaadin.navigator.View;
import com.vaadin.navigator.ViewChangeListener.ViewChangeEvent;
import com.vaadin.shared.ui.datefield.Resolution;
import com.vaadin.ui.Alignment;
import com.vaadin.ui.Button;
import com.vaadin.ui.CheckBox;
import com.vaadin.ui.ComboBox;
import com.vaadin.ui.DateField;
import com.vaadin.ui.FormLayout;
import com.vaadin.ui.Grid;
import com.vaadin.ui.GridLayout;
import com.vaadin.ui.Grid.FooterRow;
import com.vaadin.ui.Grid.SingleSelectionModel;
import com.vaadin.ui.renderers.ButtonRenderer;
import com.vaadin.ui.renderers.DateRenderer;
import com.vaadin.ui.HorizontalLayout;
import com.vaadin.ui.Layout;
import com.vaadin.ui.Panel;
import com.vaadin.ui.TextArea;
import com.vaadin.ui.TextField;
import com.vaadin.ui.UI;
import com.vaadin.ui.VerticalLayout;
import com.vaadin.ui.Window;

public class AccountingView extends HorizontalLayout implements View {

	FlatService flatService = new FlatService();

	AddExpensesButton addExpensesButton = new AddExpensesButton();
	
	ExpensesInfoButton expensesInfoButton = new ExpensesInfoButton();
	
	private Grid flatGrid = new Grid();

	private ComboBox bankContractNumberComboBox = new ComboBox("Contract Number");
	
//	private GridLayout accountingInfoGridLayout = new GridLayout(1, 15);

	private String generalExpenses = "General";
	private String bcExpenses = "B.C.";
	private String cmExpenses = "C.M.";

//		private String flatSetReserved = "Reserved";
//		private String flatSetSolded = "Solded";
//		private String flatSetFree = "";

	private DecimalFormat decimalFormat = new DecimalFormat("0.00");

	private double generalBC = 0.5; // 50%
	private double generalCM = 0.5; // 50%

	public AccountingView() throws SQLException {

//		TimeZone.setDefault(TimeZone.getTimeZone("Europe/Kiev"));

		setSizeFull();
		setMargin(true);
		setSpacing(true);

		VerticalLayout accountingInfoGridVerticalLayout = new VerticalLayout();
		accountingInfoGridVerticalLayout.setSizeFull();
		accountingInfoGridVerticalLayout.setSpacing(true);
		addComponent(accountingInfoGridVerticalLayout);

		setExpandRatio(accountingInfoGridVerticalLayout, 1.0f);

		addComponent(flatCheckBoxVerticalPanel());

		flatGrid = flatGridFlatInfoView(flatService.getFlatsFromFlatTableAndFlatBuyerDB());
		


		accountingInfoGridVerticalLayout.addComponent(flatGrid);

		accountingInfoGridVerticalLayout.setExpandRatio(flatGrid, 1.0f);

		accountingInfoGridVerticalLayout.addComponent(flatButtonHorizontalLayout());

	}

	
	public Layout flatButtonHorizontalLayout() {

		HorizontalLayout buttonHorizontalLayout = new HorizontalLayout();

		buttonHorizontalLayout.setSpacing(true);
		buttonHorizontalLayout.setHeight("50px");
		buttonHorizontalLayout.setWidth("100%");

		
		
		
		buttonHorizontalLayout.addComponent(addExpensesButton.addExpensesButton(flatGrid));
		
		buttonHorizontalLayout.addComponent(expensesInfoButton.expensesInfoButton(flatGrid));

		
		
		
		
		
		
		
		
		buttonHorizontalLayout.addComponent(reportButton());

		buttonHorizontalLayout.addComponent(addDateButton());

		buttonHorizontalLayout.addComponent(addBankInfoButton());

		buttonHorizontalLayout.addComponent(paymentInfoButton());

		buttonHorizontalLayout.addComponent(expensesButton());
		
		buttonHorizontalLayout.addComponent(expensesInfoButton());

		buttonHorizontalLayout.addComponent(cancelButton());

		return buttonHorizontalLayout;
	}
	
	
	
	
	
	
	
	
	
	
	
	
	
	public Grid flatGridFlatInfoView(List<Flat> flatList) throws SQLException {

		DecimalFormat decimalFormat = new DecimalFormat("0.00");

		Grid flatGrid = new Grid();

		flatGrid.setSizeFull();

		flatGrid.addColumn("№", Integer.class);
		flatGrid.addColumn("idFlatTable", Integer.class);
		flatGrid.addColumn("buildingCorps", String.class);
		flatGrid.addColumn("flatRooms", Integer.class);
		flatGrid.addColumn("flatFloor", Integer.class);
		flatGrid.addColumn("flatNumber", Integer.class);
		flatGrid.addColumn("flatArea", Double.class);
		flatGrid.addColumn("flatSet", String.class);

		flatGrid.addColumn("flatBuyerFirstname", String.class);
		flatGrid.addColumn("flatBuyerLastname", String.class);
		flatGrid.addColumn("flatBuyerSurname", String.class);
		flatGrid.addColumn("flatContractDate", Date.class);
		flatGrid.addColumn("flatContractNumber", String.class);
		flatGrid.addColumn("m2, flatCost", Double.class);
		flatGrid.addColumn("$, flatCost", Integer.class);
		flatGrid.addColumn("$, expenses", Integer.class);

		flatGrid.addColumn("details", String.class);
		Grid.Column buttonColumn = flatGrid.getColumn("details");
		buttonColumn.setRenderer(new ButtonRenderer(clickEvent -> {

			Window detailsInfoWindow = new Window("Detail Info Window");

			VerticalLayout detailsInfoWindowVerticalLayout = new VerticalLayout();

			detailsInfoWindow.setContent(detailsInfoWindowVerticalLayout);

			Grid detailsInfoWindowGrid = new Grid("Details Info");
			detailsInfoWindowGrid.setSizeFull();
			detailsInfoWindowGrid.setHeight("77px");
			detailsInfoWindowGrid.addColumn("buildingCorps", String.class);
			detailsInfoWindowGrid.addColumn("flatNumber", Integer.class);
			detailsInfoWindowGrid.addColumn("flatArea", Double.class);
			detailsInfoWindowGrid.addColumn("flatContractDate", Date.class);
			detailsInfoWindowGrid.addColumn("$, flatCost", Integer.class);

			detailsInfoWindowGrid.getColumn("flatContractDate").setHeaderCaption("Flat Contract Date")
					.setRenderer(new DateRenderer("%1$td.%1$tm.%1$tY"));

			int idFlatTableFromSelectedRowFlatGrid = Integer.parseInt(flatGrid.getContainerDataSource()
					.getItem(clickEvent.getItemId()).getItemProperty("idFlatTable").getValue().toString());

			try {
				Flat flatTable = flatService.getFlatByFlatIdFromFlatTable(idFlatTableFromSelectedRowFlatGrid);
				Flat flatBuyer = flatService.getFlatByFlatIdFromFlatBuyerTable(idFlatTableFromSelectedRowFlatGrid);

				detailsInfoWindowGrid.addRow(flatTable.getBuildingCorps(), flatTable.getFlatNumber(),
						flatTable.getFlatArea(), flatBuyer.getFlatContractDate(), flatBuyer.getFlatCost());
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}

			detailsInfoWindowVerticalLayout.addComponent(detailsInfoWindowGrid);

			Grid detailsGeneralInfoWindowGrid = new Grid("General Expenses Info");
			detailsGeneralInfoWindowGrid.setSizeFull();
			detailsGeneralInfoWindowGrid.setHeight("267px");
			detailsGeneralInfoWindowGrid.addColumn("expensesDate", Date.class);
			detailsGeneralInfoWindowGrid.addColumn("expensesSum", Integer.class);
			detailsGeneralInfoWindowGrid.addColumn("expensesValue", String.class);

			detailsGeneralInfoWindowGrid.getColumn("expensesDate").setHeaderCaption("Expenses Date")
					.setRenderer(new DateRenderer("%1$td.%1$tm.%1$tY"));

			int expensesSumGeneral = 0;

			try {
				List<Flat> expensesTableList = flatService.getExpensesByFlatIdAndCategoryFromExpensesTable(
						idFlatTableFromSelectedRowFlatGrid, generalExpenses);

				Iterator<Flat> itr = expensesTableList.iterator();
				while (itr.hasNext()) {
					Flat flatFromList = itr.next();

					expensesSumGeneral = expensesSumGeneral + flatFromList.getExpensesTableSum();

					detailsGeneralInfoWindowGrid.addRow(flatFromList.getExpensesTableDate(),
							flatFromList.getExpensesTableSum(), flatFromList.getExpensesTableValue());
				}
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}

			FooterRow detailsGeneralInfoWindowGridFooterRow = detailsGeneralInfoWindowGrid.prependFooterRow();
			detailsGeneralInfoWindowGridFooterRow.getCell("expensesSum").setText("Total: " + expensesSumGeneral);

			detailsInfoWindowVerticalLayout.addComponent(detailsGeneralInfoWindowGrid);

			Grid detailsBCInfoWindowGrid = new Grid("BC Expenses Info");
			detailsBCInfoWindowGrid.setSizeFull();
			detailsBCInfoWindowGrid.setHeight("267px");
			detailsBCInfoWindowGrid.addColumn("expensesDate", Date.class);
			detailsBCInfoWindowGrid.addColumn("expensesSum", Integer.class);
			detailsBCInfoWindowGrid.addColumn("expensesValue", String.class);

			detailsBCInfoWindowGrid.getColumn("expensesDate").setHeaderCaption("Expenses Date")
					.setRenderer(new DateRenderer("%1$td.%1$tm.%1$tY"));

			int expensesSumBC = 0;

			try {
				List<Flat> expensesTableList = flatService.getExpensesByFlatIdAndCategoryFromExpensesTable(
						idFlatTableFromSelectedRowFlatGrid, bcExpenses);

				Iterator<Flat> itr = expensesTableList.iterator();
				while (itr.hasNext()) {
					Flat flatFromList = itr.next();

					expensesSumBC = expensesSumBC + flatFromList.getExpensesTableSum();

					detailsBCInfoWindowGrid.addRow(flatFromList.getExpensesTableDate(),
							flatFromList.getExpensesTableSum(), flatFromList.getExpensesTableValue());
				}
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}

			FooterRow detailsBCInfoWindowGridFooterRow = detailsBCInfoWindowGrid.prependFooterRow();
			detailsBCInfoWindowGridFooterRow.getCell("expensesSum").setText("Total: " + expensesSumBC);

			detailsInfoWindowVerticalLayout.addComponent(detailsBCInfoWindowGrid);

			Grid detailsCMInfoWindowGrid = new Grid("CM Expenses Info");
			detailsCMInfoWindowGrid.setSizeFull();
			detailsCMInfoWindowGrid.setHeight("267px");
			detailsCMInfoWindowGrid.addColumn("expensesDate", Date.class);
			detailsCMInfoWindowGrid.addColumn("expensesSum", Integer.class);
			detailsCMInfoWindowGrid.addColumn("expensesValue", String.class);

			detailsCMInfoWindowGrid.getColumn("expensesDate").setHeaderCaption("Expenses Date")
					.setRenderer(new DateRenderer("%1$td.%1$tm.%1$tY"));

			int expensesSumCM = 0;

			try {
				List<Flat> expensesTableList = flatService.getExpensesByFlatIdAndCategoryFromExpensesTable(
						idFlatTableFromSelectedRowFlatGrid, cmExpenses);

				Iterator<Flat> itr = expensesTableList.iterator();
				while (itr.hasNext()) {
					Flat flatFromList = itr.next();

					expensesSumCM = expensesSumCM + flatFromList.getExpensesTableSum();

					detailsCMInfoWindowGrid.addRow(flatFromList.getExpensesTableDate(),
							flatFromList.getExpensesTableSum(), flatFromList.getExpensesTableValue());
				}
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}

			FooterRow detailsCMInfoWindowGridFooterRow = detailsCMInfoWindowGrid.prependFooterRow();
			detailsCMInfoWindowGridFooterRow.getCell("expensesSum").setText("Total: " + expensesSumCM);

			detailsInfoWindowVerticalLayout.addComponent(detailsCMInfoWindowGrid);

			detailsInfoWindow.center();
			UI.getCurrent().addWindow(detailsInfoWindow);
		}));

		flatGrid.addColumn("$, availableSum", Integer.class);
		flatGrid.addColumn("m2, availableSum", Double.class);

		flatGrid.addColumn("flatSellerName", String.class);
		flatGrid.addColumn("flatNotes", String.class);

		flatGrid.addColumn("$, 50/50GeneralExpenses", Integer.class);
		flatGrid.addColumn("m2, 50/50GeneralExpenses", Double.class);
		flatGrid.addColumn("$, BCExpenses", Integer.class);
		flatGrid.addColumn("m2, BCExpenses", Double.class);
		flatGrid.addColumn("m2, totalBCExpenses", Double.class);
		flatGrid.addColumn("$, CMExpenses", Integer.class);
		flatGrid.addColumn("m2, CMExpenses", Double.class);
		flatGrid.addColumn("m2, totalCMExpenses", Double.class);

//		flatGrid.addColumn("bankPaymentDate", Date.class);
//		flatGrid.getColumn("bankPaymentDate").setHeaderCaption("Bank Payment Date")
//				.setRenderer(new DateRenderer("%1$td.%1$tm.%1$tY"));
		flatGrid.addColumn("bankPaymentSum", Double.class);
		flatGrid.addColumn("m2, bank", Double.class);

		flatGrid.getColumn("idFlatTable").setHidden(true);
		flatGrid.getColumn("flatRooms").setHidden(true);
		flatGrid.getColumn("flatFloor").setHidden(true);
		flatGrid.getColumn("flatBuyerFirstname").setHidden(true);
		flatGrid.getColumn("flatBuyerLastname").setHidden(true);
		flatGrid.getColumn("flatBuyerSurname").setHidden(true);
		flatGrid.getColumn("flatContractDate").setHidden(true);
		flatGrid.getColumn("flatContractDate").setHeaderCaption("Flat Contract Date")
				.setRenderer(new DateRenderer("%1$td.%1$tm.%1$tY"));
		flatGrid.getColumn("flatContractNumber").setHidden(true);
		flatGrid.getColumn("$, flatCost").setHidden(true);
		flatGrid.getColumn("m2, flatCost").setHidden(true);
		flatGrid.getColumn("flatSellerName").setHidden(true);
		flatGrid.getColumn("flatNotes").setHidden(true);

		flatGrid.getColumn("$, expenses").setHidden(true);
		flatGrid.getColumn("details").setHidden(true);
		flatGrid.getColumn("$, availableSum").setHidden(true);
		flatGrid.getColumn("$, 50/50GeneralExpenses").setHidden(true);
		flatGrid.getColumn("m2, 50/50GeneralExpenses").setHidden(true);
		flatGrid.getColumn("$, BCExpenses").setHidden(true);
		flatGrid.getColumn("m2, BCExpenses").setHidden(true);
		flatGrid.getColumn("$, CMExpenses").setHidden(true);
		flatGrid.getColumn("m2, CMExpenses").setHidden(true);

//		flatGrid.getColumn("bankPaymentDate").setHidden(true);
		flatGrid.getColumn("bankPaymentSum").setHidden(true);
		flatGrid.getColumn("m2, bank").setHidden(true);

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

		Iterator<Flat> itr = flatList.iterator();
		while (itr.hasNext()) {
			Flat flatFromList = itr.next();

			double flatCost_m$ = flatFromList.getFlatCost() / flatFromList.getFlatArea();
			if (flatCost_m$ == 0) {
				flatCost_m$ = 0.000000001;
			}

			int expenses = flatService.getExpensesFlatInfoFromExpensesTableByFlatId(flatFromList.getIdFlatTable(),
					generalExpenses)
					+ flatService.getExpensesFlatInfoFromExpensesTableByFlatId(flatFromList.getIdFlatTable(),
							bcExpenses)
					+ flatService.getExpensesFlatInfoFromExpensesTableByFlatId(flatFromList.getIdFlatTable(),
							cmExpenses);

			int availableSum = flatFromList.getFlatCost() - expenses;

			flatArea = flatArea + flatFromList.getFlatArea();
			flatCost = flatCost + flatFromList.getFlatCost();
			expensesRow = expensesRow + expenses;
			availableSumRow = availableSumRow + availableSum;
			mAvailableSumRow = mAvailableSumRow + availableSum / flatCost_m$;
			generalExpensesRow = generalExpensesRow + flatService
					.getExpensesFlatInfoFromExpensesTableByFlatId(flatFromList.getIdFlatTable(), generalExpenses);
			mGeneralExpensesRow = mGeneralExpensesRow
					+ (flatService.getExpensesFlatInfoFromExpensesTableByFlatId(flatFromList.getIdFlatTable(),
							generalExpenses)) / flatCost_m$;
			bcExpensesRow = bcExpensesRow + flatService
					.getExpensesFlatInfoFromExpensesTableByFlatId(flatFromList.getIdFlatTable(), bcExpenses);
			mBCExpensesRow = mBCExpensesRow
					+ (flatService.getExpensesFlatInfoFromExpensesTableByFlatId(flatFromList.getIdFlatTable(),
							bcExpenses)) / flatCost_m$;
			cmExpensesRow = cmExpensesRow + flatService
					.getExpensesFlatInfoFromExpensesTableByFlatId(flatFromList.getIdFlatTable(), cmExpenses);
			mCMExpensesRow = mCMExpensesRow
					+ (flatService.getExpensesFlatInfoFromExpensesTableByFlatId(flatFromList.getIdFlatTable(),
							cmExpenses)) / flatCost_m$;
			mTotalBCExpensesRow = mTotalBCExpensesRow
					+ (flatService.getExpensesFlatInfoFromExpensesTableByFlatId(flatFromList.getIdFlatTable(),
							bcExpenses) / flatCost_m$)
					+ (flatService.getExpensesFlatInfoFromExpensesTableByFlatId(flatFromList.getIdFlatTable(),
							generalExpenses) / flatCost_m$ / 2);
			mTotalCMExpensesRow = mTotalCMExpensesRow
					+ (flatService.getExpensesFlatInfoFromExpensesTableByFlatId(flatFromList.getIdFlatTable(),
							cmExpenses) / flatCost_m$)
					+ (flatService.getExpensesFlatInfoFromExpensesTableByFlatId(flatFromList.getIdFlatTable(),
							generalExpenses) / flatCost_m$ / 2);
			bankPaymentSum = bankPaymentSum + flatService.getPaymentSumFromBankTableByFlatTableId(flatFromList.getIdFlatTable());

			flatGrid.addRow(number, flatFromList.getIdFlatTable(), flatFromList.getBuildingCorps(), flatFromList.getFlatRooms(),
					flatFromList.getFlatFloor(), flatFromList.getFlatNumber(), flatFromList.getFlatArea(),
					flatFromList.getFlatSet(), flatFromList.getFlatBuyerFirstname(),
					flatFromList.getFlatBuyerLastname(), flatFromList.getFlatBuyerSurname(),
					flatFromList.getFlatContractDate(), flatFromList.getFlatContractNumber(), flatCost_m$,
					flatFromList.getFlatCost(), expenses, "Expenses", availableSum, availableSum / flatCost_m$,
					flatFromList.getFlatSellerName(), flatFromList.getFlatNotes(),
					flatService.getExpensesFlatInfoFromExpensesTableByFlatId(flatFromList.getIdFlatTable(),
							generalExpenses),
					(flatService.getExpensesFlatInfoFromExpensesTableByFlatId(flatFromList.getIdFlatTable(),
							generalExpenses)) / flatCost_m$,
					flatService.getExpensesFlatInfoFromExpensesTableByFlatId(flatFromList.getIdFlatTable(), bcExpenses),
					(flatService.getExpensesFlatInfoFromExpensesTableByFlatId(flatFromList.getIdFlatTable(),
							bcExpenses)) / flatCost_m$,
					(flatService.getExpensesFlatInfoFromExpensesTableByFlatId(flatFromList.getIdFlatTable(),
							bcExpenses)) / flatCost_m$
							+ (flatService.getExpensesFlatInfoFromExpensesTableByFlatId(flatFromList.getIdFlatTable(),
									generalExpenses)) / flatCost_m$ / 2,
					flatService.getExpensesFlatInfoFromExpensesTableByFlatId(flatFromList.getIdFlatTable(), cmExpenses),
					(flatService.getExpensesFlatInfoFromExpensesTableByFlatId(flatFromList.getIdFlatTable(),
							cmExpenses)) / flatCost_m$,
					(flatService.getExpensesFlatInfoFromExpensesTableByFlatId(flatFromList.getIdFlatTable(),
							cmExpenses)) / flatCost_m$
							+ (flatService.getExpensesFlatInfoFromExpensesTableByFlatId(flatFromList.getIdFlatTable(),
									generalExpenses)) / flatCost_m$ / 2
							
							
							

//					, flatFromList.getBankTablePaymentDate()
//					, flatFromList.getBankTablePaymentSum()
					, flatService.getPaymentSumFromBankTableByFlatTableId(flatFromList.getIdFlatTable())
					, flatService.getPaymentSumFromBankTableByFlatTableId(flatFromList.getIdFlatTable())/flatFromList.getFlatArea()

			);
			
			number++;
		}

		FooterRow flatGridFooterRow = flatGrid.prependFooterRow();
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

		return flatGrid;

	}

	public Panel flatCheckBoxVerticalPanel() {

		Panel settingsCheckBoxPanel = new Panel("Settings");
		settingsCheckBoxPanel.setWidth("200px");
		settingsCheckBoxPanel.setHeight("100%");

		VerticalLayout checkBoxVerticalLayout = new VerticalLayout();
		checkBoxVerticalLayout.setMargin(true);
		checkBoxVerticalLayout.setSpacing(true);
		checkBoxVerticalLayout.setSizeFull();

		settingsCheckBoxPanel.setContent(checkBoxVerticalLayout);

		CheckBox flatInfoCheckBox = new CheckBox("Flat Info");
		checkBoxVerticalLayout.addComponent(flatInfoCheckBox);
		flatInfoCheckBox.setValue(false);
		flatInfoCheckBox.addValueChangeListener(event -> {

			if (flatInfoCheckBox.isEmpty()) {

				flatGrid.getColumn("flatRooms").setHidden(true);
				flatGrid.getColumn("flatFloor").setHidden(true);
			} else {
				flatGrid.getColumn("flatRooms").setHidden(false);
				flatGrid.getColumn("flatFloor").setHidden(false);

			}

		});
		
		
		
		
		
		
		CheckBox buyerInfoCheckBox = new CheckBox("Buyer Info");
		checkBoxVerticalLayout.addComponent(buyerInfoCheckBox);
		buyerInfoCheckBox.setValue(false);
		buyerInfoCheckBox.addValueChangeListener(event -> {

			if (buyerInfoCheckBox.isEmpty()) {

				flatGrid.getColumn("flatBuyerFirstname").setHidden(true);
				flatGrid.getColumn("flatBuyerLastname").setHidden(true);
				flatGrid.getColumn("flatBuyerSurname").setHidden(true);
			} else {
				flatGrid.getColumn("flatBuyerFirstname").setHidden(false);
				flatGrid.getColumn("flatBuyerLastname").setHidden(false);
				flatGrid.getColumn("flatBuyerSurname").setHidden(false);

			}

		});

		CheckBox contractInfoCheckBox = new CheckBox("Contract Info");
		checkBoxVerticalLayout.addComponent(contractInfoCheckBox);

		contractInfoCheckBox.addValueChangeListener(event -> {
			if (contractInfoCheckBox.isEmpty()) {

				flatGrid.getColumn("flatContractDate").setHidden(true);
				flatGrid.getColumn("flatContractNumber").setHidden(true);
			} else {
				flatGrid.getColumn("flatContractDate").setHidden(false);
				flatGrid.getColumn("flatContractNumber").setHidden(false);
			}

		});

		CheckBox flatCostM2CheckBox = new CheckBox("Flat Cost m2");
		checkBoxVerticalLayout.addComponent(flatCostM2CheckBox);
		flatCostM2CheckBox.addValueChangeListener(event -> {

			if (flatCostM2CheckBox.isEmpty()) {

				flatGrid.getColumn("m2, flatCost").setHidden(true);
			} else {
				flatGrid.getColumn("m2, flatCost").setHidden(false);
			}

		});

		CheckBox flatCostCheckBox = new CheckBox("Flat Cost");
		checkBoxVerticalLayout.addComponent(flatCostCheckBox);
		flatCostCheckBox.addValueChangeListener(event -> {

			if (flatCostCheckBox.isEmpty()) {

				flatGrid.getColumn("$, flatCost").setHidden(true);
			} else {
				flatGrid.getColumn("$, flatCost").setHidden(false);
			}

		});

		CheckBox expensesCheckBox = new CheckBox("Expenses Info");
		checkBoxVerticalLayout.addComponent(expensesCheckBox);
		expensesCheckBox.setValue(false);
		expensesCheckBox.addValueChangeListener(event -> {

			if (expensesCheckBox.isEmpty()) {

				flatGrid.getColumn("$, expenses").setHidden(true);
				flatGrid.getColumn("details").setHidden(true);

			} else {
				flatGrid.getColumn("$, expenses").setHidden(false);
				flatGrid.getColumn("details").setHidden(false);

			}

		});

		CheckBox availableSumCheckBox = new CheckBox("Available Sum");
		checkBoxVerticalLayout.addComponent(availableSumCheckBox);
		availableSumCheckBox.setValue(false);
		availableSumCheckBox.addValueChangeListener(event -> {

			if (availableSumCheckBox.isEmpty()) {

				flatGrid.getColumn("$, availableSum").setHidden(true);

			} else {
				flatGrid.getColumn("$, availableSum").setHidden(false);

			}

		});

		CheckBox generalExpensesCheckBox = new CheckBox("General Expenses");
		checkBoxVerticalLayout.addComponent(generalExpensesCheckBox);
		generalExpensesCheckBox.setValue(false);
		generalExpensesCheckBox.addValueChangeListener(event -> {

			if (generalExpensesCheckBox.isEmpty()) {

				flatGrid.getColumn("$, 50/50GeneralExpenses").setHidden(true);
				flatGrid.getColumn("m2, 50/50GeneralExpenses").setHidden(true);

			} else {
				flatGrid.getColumn("$, 50/50GeneralExpenses").setHidden(false);
				flatGrid.getColumn("m2, 50/50GeneralExpenses").setHidden(false);

			}

		});

		CheckBox bcExpensesCheckBox = new CheckBox("B.C. Expenses");
		checkBoxVerticalLayout.addComponent(bcExpensesCheckBox);
		bcExpensesCheckBox.setValue(false);
		bcExpensesCheckBox.addValueChangeListener(event -> {

			if (bcExpensesCheckBox.isEmpty()) {

				flatGrid.getColumn("$, BCExpenses").setHidden(true);
				flatGrid.getColumn("m2, BCExpenses").setHidden(true);

			} else {
				flatGrid.getColumn("$, BCExpenses").setHidden(false);
				flatGrid.getColumn("m2, BCExpenses").setHidden(false);

			}

		});

		CheckBox cmExpensesCheckBox = new CheckBox("C.M. Expenses");
		checkBoxVerticalLayout.addComponent(cmExpensesCheckBox);
		cmExpensesCheckBox.setValue(false);
		cmExpensesCheckBox.addValueChangeListener(event -> {

			if (cmExpensesCheckBox.isEmpty()) {

				flatGrid.getColumn("$, CMExpenses").setHidden(true);
				flatGrid.getColumn("m2, CMExpenses").setHidden(true);

			} else {
				flatGrid.getColumn("$, CMExpenses").setHidden(false);
				flatGrid.getColumn("m2, CMExpenses").setHidden(false);

			}

		});

		CheckBox bankInfoCheckBox = new CheckBox("Bank Info");
		checkBoxVerticalLayout.addComponent(bankInfoCheckBox);
		bankInfoCheckBox.addValueChangeListener(event -> {

			if (bankInfoCheckBox.isEmpty()) {

				flatGrid.getColumn("bankPaymentSum").setHidden(true);
				flatGrid.getColumn("m2, bank").setHidden(true);
			} else {

				flatGrid.getColumn("bankPaymentSum").setHidden(false);
				flatGrid.getColumn("m2, bank").setHidden(false);
			}

		});

		return settingsCheckBoxPanel;
	}



	public Button expensesButton() {

//			expenses sum сума витрат
//			income sum сума доходу
//			available sum оступна сума

		Button expensesButton = new Button("Add Expenses");
		expensesButton.setSizeFull();

		expensesButton.setEnabled(false);

		expensesButton.addClickListener(e -> {

			Window expensesWindow = new Window("Expenses Window");
			expensesWindow.setHeight("600px");
			expensesWindow.setWidth("900px");

			
			
			
			
			
			GridLayout expensesWindowGridLayout = new GridLayout(2, 11);
			expensesWindowGridLayout.setSizeFull();
			expensesWindowGridLayout.setSpacing(true);
			expensesWindowGridLayout.setMargin(true);
			expensesWindow.setContent(expensesWindowGridLayout);

			Object selected = ((SingleSelectionModel) flatGrid.getSelectionModel()).getSelectedRow();
			String idFlatTableFromSelectedRow = flatGrid.getContainerDataSource().getItem(selected)
					.getItemProperty("idFlatTable").getValue().toString();
			int idFlatTableIntFromSelectedRow = Integer.parseInt(idFlatTableFromSelectedRow);

			Panel addExpensesWindowPanel = new Panel("Add Expenses");
			addExpensesWindowPanel.setSizeFull();
			expensesWindowGridLayout.addComponent(addExpensesWindowPanel, 0, 0, 0, 9);

			FormLayout expensesWindowExpensesFormLayout = new FormLayout();
			expensesWindowExpensesFormLayout.setMargin(true);
			expensesWindowExpensesFormLayout.setSizeFull();
			addExpensesWindowPanel.setContent(expensesWindowExpensesFormLayout);

			DateField expensesDateField = new DateField("Expenses Date");
			expensesDateField.setValue(new java.util.Date());

			expensesDateField.setDateFormat("dd.MM.yyyy");

			expensesWindowExpensesFormLayout.addComponent(expensesDateField);

			TextField expensesSumTextField = new TextField("Expenses Sum");

			expensesWindowExpensesFormLayout.addComponent(expensesSumTextField);

			ComboBox expensesCategoryComboBox = new ComboBox("Expenses Category");
			expensesCategoryComboBox.addItem("General");
			expensesCategoryComboBox.addItem("B.C.");
			expensesCategoryComboBox.addItem("C.M.");

			expensesWindowExpensesFormLayout.addComponent(expensesCategoryComboBox);

			ComboBox expensesValueComboBox = new ComboBox("Expenses Value");
			expensesValueComboBox.addItem("Податки, що залишилися на нашій Львівській фірмі ЛТБ (прогонка квартир)");
			expensesValueComboBox.addItem("Маклер і оформлення в нотаріуса");
			expensesValueComboBox.addItem("Переведено на УМБ ФІНАНС (Козловському)");
			expensesValueComboBox.addItem("Передано В.С.");
			expensesValueComboBox.addItem("Передано С.М.");

			expensesWindowExpensesFormLayout.addComponent(expensesValueComboBox);

			TextArea expensesValueTextArea = new TextArea("Expenses Value");
			expensesValueTextArea.setHeight("60px");

			expensesWindowExpensesFormLayout.addComponent(expensesValueTextArea);

			Panel expensesWindowInfoPanel = new Panel();
			expensesWindowInfoPanel.setSizeFull();

			expensesWindowGridLayout.addComponent(expensesWindowInfoPanel, 1, 0, 1, 9);

			Grid expensesWindowGrid = new Grid();
			expensesWindowGrid.setSizeFull();

			expensesWindowGrid.addColumn("expensesDate", Date.class);
			expensesWindowGrid.addColumn("sum", Integer.class);
			expensesWindowGrid.addColumn("category", String.class);
			expensesWindowGrid.addColumn("value", String.class);

			expensesWindowGrid.getColumn("expensesDate").setRenderer(new DateRenderer("%1$td.%1$tm.%1$tY"));

			List<Flat> flatList = new ArrayList<>();

			int expensesSum = 0;

			try {
				flatList = flatService
						.getExpensesByFlatIdFromExpensesTable(Integer.parseInt(idFlatTableFromSelectedRow));
				Iterator<Flat> itr = flatList.iterator();
				while (itr.hasNext()) {
					Flat flatFromList = itr.next();

					expensesWindowGrid.addRow(flatFromList.getExpensesTableDate(), flatFromList.getExpensesTableSum(),
							flatFromList.getExpensesTableCategory(), flatFromList.getExpensesTableValue());

					expensesSum = expensesSum + flatFromList.getExpensesTableSum();

				}
			} catch (NumberFormatException | SQLException e4) {
				// TODO Auto-generated catch block
				e4.printStackTrace();
			}

			FooterRow expensesWindowGridFooterRow = expensesWindowGrid.prependFooterRow();
			expensesWindowGridFooterRow.getCell("expensesDate").setText("Total: ");
			expensesWindowGridFooterRow.getCell("sum").setText(decimalFormat.format(expensesSum).replace(",", "."));

			expensesWindowInfoPanel.setContent(expensesWindowGrid);

			Flat selectedSoldedFlatBuyerInfo = new Flat();

			try {
				selectedSoldedFlatBuyerInfo = flatService
						.getFlatByFlatIdFromFlatBuyerTable(idFlatTableIntFromSelectedRow);
				int flatCost = selectedSoldedFlatBuyerInfo.getFlatCost();
				int expensesGeneral = flatService
						.getExpensesFlatInfoFromExpensesTableByFlatId(idFlatTableIntFromSelectedRow, "General");
				int expensesBC = flatService.getExpensesFlatInfoFromExpensesTableByFlatId(idFlatTableIntFromSelectedRow,
						"B.C.");
				int expensesCM = flatService.getExpensesFlatInfoFromExpensesTableByFlatId(idFlatTableIntFromSelectedRow,
						"C.M.");

				int availableSum = flatCost - expensesGeneral - expensesBC - expensesCM;

				expensesWindowInfoPanel.setCaption("Expenses Info (B.C. = " + expensesBC + "$: C.M. = " + expensesCM
						+ "$: General = " + expensesGeneral + "$)");
				addExpensesWindowPanel.setCaption("Add Expenses (Available Sum = " + availableSum + "$)");

			} catch (SQLException e3) {
				// TODO Auto-generated catch block
				e3.printStackTrace();
			}

			HorizontalLayout expensesWindowButtonHorizontalLayout = new HorizontalLayout();

			expensesWindowButtonHorizontalLayout.setSpacing(true);
			expensesWindowButtonHorizontalLayout.setHeight("40px");
			expensesWindowButtonHorizontalLayout.setWidth("100%");

			expensesWindowGridLayout.addComponent(expensesWindowButtonHorizontalLayout, 0, 10, 1, 10);
			expensesWindowGridLayout.setComponentAlignment(expensesWindowButtonHorizontalLayout,
					Alignment.BOTTOM_CENTER);

			Button expensesAddButton = new Button("Add Expenses");
			expensesAddButton.setSizeFull();
			expensesAddButton.addClickListener(e1 -> {
				expensesWindowGrid.addRow(expensesDateField.getValue(),
						Integer.parseInt(expensesSumTextField.getValue()), expensesCategoryComboBox.getValue(),
						expensesValueComboBox.getValue() + " " + expensesValueTextArea.getValue());

				Flat expensesFlat = new Flat();
				expensesFlat.setIdFlatTable(Integer.parseInt(idFlatTableFromSelectedRow));
				expensesFlat.setExpensesTableDate(expensesDateField.getValue());
				expensesFlat.setExpensesTableSum(Integer.parseInt(expensesSumTextField.getValue()));
				expensesFlat.setExpensesTableCategory(expensesCategoryComboBox.getValue().toString());
				expensesFlat.setExpensesTableValue(
						expensesValueComboBox.getValue() + " " + expensesValueTextArea.getValue());

				try {
					flatService.createExpensesFlat(expensesFlat);
				} catch (SQLException e2) {
					// TODO Auto-generated catch block
					e2.printStackTrace();
				}

			});

			expensesWindowButtonHorizontalLayout.addComponent(expensesAddButton);

			Button expensesCancelButton = new Button("Cancel");
			expensesCancelButton.setSizeFull();
			expensesCancelButton.addClickListener(click -> {
				expensesWindow.close();
			});
			expensesWindowButtonHorizontalLayout.addComponent(expensesCancelButton);

			expensesWindow.center();

			UI.getCurrent().addWindow(expensesWindow);

		});

		flatGrid.addSelectionListener(SelectionEvent -> {
			expensesButton.setEnabled(true);
		});

		return expensesButton;
	}
	
	
	public Button expensesInfoButton() {

//		expenses sum сума витрат
//		income sum сума доходу
//		available sum оступна сума

	Button expensesInfoButton = new Button("Expenses Info");
	expensesInfoButton.setSizeFull();

	expensesInfoButton.setEnabled(false);

	expensesInfoButton.addClickListener(e -> {

		Window expensesWindow = new Window("Expenses Window");
		expensesWindow.setHeight("600px");
		expensesWindow.setWidth("900px");

		GridLayout expensesWindowGridLayout = new GridLayout(2, 11);
		expensesWindowGridLayout.setSizeFull();
		expensesWindowGridLayout.setSpacing(true);
		expensesWindowGridLayout.setMargin(true);
		expensesWindow.setContent(expensesWindowGridLayout);

		Object selected = ((SingleSelectionModel) flatGrid.getSelectionModel()).getSelectedRow();
		String idFlatTableFromSelectedRow = flatGrid.getContainerDataSource().getItem(selected)
				.getItemProperty("idFlatTable").getValue().toString();
		int idFlatTableIntFromSelectedRow = Integer.parseInt(idFlatTableFromSelectedRow);

		Panel addExpensesWindowPanel = new Panel("Add Expenses");
		addExpensesWindowPanel.setSizeFull();
		expensesWindowGridLayout.addComponent(addExpensesWindowPanel, 0, 0, 0, 9);

		FormLayout expensesWindowExpensesFormLayout = new FormLayout();
		expensesWindowExpensesFormLayout.setMargin(true);
		expensesWindowExpensesFormLayout.setSizeFull();
		addExpensesWindowPanel.setContent(expensesWindowExpensesFormLayout);

		DateField expensesDateField = new DateField("Expenses Date");
		expensesDateField.setValue(new java.util.Date());

		expensesDateField.setDateFormat("dd.MM.yyyy");

		expensesWindowExpensesFormLayout.addComponent(expensesDateField);

		TextField expensesSumTextField = new TextField("Expenses Sum");

		expensesWindowExpensesFormLayout.addComponent(expensesSumTextField);

		ComboBox expensesCategoryComboBox = new ComboBox("Expenses Category");
		expensesCategoryComboBox.addItem("General");
		expensesCategoryComboBox.addItem("B.C.");
		expensesCategoryComboBox.addItem("C.M.");

		expensesWindowExpensesFormLayout.addComponent(expensesCategoryComboBox);

		ComboBox expensesValueComboBox = new ComboBox("Expenses Value");
		expensesValueComboBox.addItem("Податки, що залишилися на нашій Львівській фірмі ЛТБ (прогонка квартир)");
		expensesValueComboBox.addItem("Маклер і оформлення в нотаріуса");
		expensesValueComboBox.addItem("Переведено на УМБ ФІНАНС (Козловському)");
		expensesValueComboBox.addItem("Передано В.С.");
		expensesValueComboBox.addItem("Передано С.М.");

		expensesWindowExpensesFormLayout.addComponent(expensesValueComboBox);

		TextArea expensesValueTextArea = new TextArea("Expenses Value");
		expensesValueTextArea.setHeight("60px");

		expensesWindowExpensesFormLayout.addComponent(expensesValueTextArea);

		Panel expensesWindowInfoPanel = new Panel();
		expensesWindowInfoPanel.setSizeFull();

		expensesWindowGridLayout.addComponent(expensesWindowInfoPanel, 1, 0, 1, 9);

		Grid expensesWindowGrid = new Grid();
		expensesWindowGrid.setSizeFull();

		expensesWindowGrid.addColumn("expensesDate", Date.class);
		expensesWindowGrid.addColumn("sum", Integer.class);
		expensesWindowGrid.addColumn("category", String.class);
		expensesWindowGrid.addColumn("value", String.class);

		expensesWindowGrid.getColumn("expensesDate").setRenderer(new DateRenderer("%1$td.%1$tm.%1$tY"));

		List<Flat> flatList = new ArrayList<>();

		int expensesSum = 0;

		try {
			flatList = flatService
					.getExpensesByFlatIdFromExpensesTable(Integer.parseInt(idFlatTableFromSelectedRow));
			Iterator<Flat> itr = flatList.iterator();
			while (itr.hasNext()) {
				Flat flatFromList = itr.next();

				expensesWindowGrid.addRow(flatFromList.getExpensesTableDate(), flatFromList.getExpensesTableSum(),
						flatFromList.getExpensesTableCategory(), flatFromList.getExpensesTableValue());

				expensesSum = expensesSum + flatFromList.getExpensesTableSum();

			}
		} catch (NumberFormatException | SQLException e4) {
			// TODO Auto-generated catch block
			e4.printStackTrace();
		}

		FooterRow expensesWindowGridFooterRow = expensesWindowGrid.prependFooterRow();
		expensesWindowGridFooterRow.getCell("expensesDate").setText("Total: ");
		expensesWindowGridFooterRow.getCell("sum").setText(decimalFormat.format(expensesSum).replace(",", "."));

		expensesWindowInfoPanel.setContent(expensesWindowGrid);

		Flat selectedSoldedFlatBuyerInfo = new Flat();

		try {
			selectedSoldedFlatBuyerInfo = flatService
					.getFlatByFlatIdFromFlatBuyerTable(idFlatTableIntFromSelectedRow);
			int flatCost = selectedSoldedFlatBuyerInfo.getFlatCost();
			int expensesGeneral = flatService
					.getExpensesFlatInfoFromExpensesTableByFlatId(idFlatTableIntFromSelectedRow, "General");
			int expensesBC = flatService.getExpensesFlatInfoFromExpensesTableByFlatId(idFlatTableIntFromSelectedRow,
					"B.C.");
			int expensesCM = flatService.getExpensesFlatInfoFromExpensesTableByFlatId(idFlatTableIntFromSelectedRow,
					"C.M.");

			int availableSum = flatCost - expensesGeneral - expensesBC - expensesCM;

			expensesWindowInfoPanel.setCaption("Expenses Info (B.C. = " + expensesBC + "$: C.M. = " + expensesCM
					+ "$: General = " + expensesGeneral + "$)");
			addExpensesWindowPanel.setCaption("Add Expenses (Available Sum = " + availableSum + "$)");

		} catch (SQLException e3) {
			// TODO Auto-generated catch block
			e3.printStackTrace();
		}

		HorizontalLayout expensesWindowButtonHorizontalLayout = new HorizontalLayout();

		expensesWindowButtonHorizontalLayout.setSpacing(true);
		expensesWindowButtonHorizontalLayout.setHeight("40px");
		expensesWindowButtonHorizontalLayout.setWidth("100%");

		expensesWindowGridLayout.addComponent(expensesWindowButtonHorizontalLayout, 0, 10, 1, 10);
		expensesWindowGridLayout.setComponentAlignment(expensesWindowButtonHorizontalLayout,
				Alignment.BOTTOM_CENTER);

		Button expensesAddButton = new Button("Add Expenses");
		expensesAddButton.setSizeFull();
		expensesAddButton.addClickListener(e1 -> {
			expensesWindowGrid.addRow(expensesDateField.getValue(),
					Integer.parseInt(expensesSumTextField.getValue()), expensesCategoryComboBox.getValue(),
					expensesValueComboBox.getValue() + " " + expensesValueTextArea.getValue());

			Flat expensesFlat = new Flat();
			expensesFlat.setIdFlatTable(Integer.parseInt(idFlatTableFromSelectedRow));
			expensesFlat.setExpensesTableDate(expensesDateField.getValue());
			expensesFlat.setExpensesTableSum(Integer.parseInt(expensesSumTextField.getValue()));
			expensesFlat.setExpensesTableCategory(expensesCategoryComboBox.getValue().toString());
			expensesFlat.setExpensesTableValue(
					expensesValueComboBox.getValue() + " " + expensesValueTextArea.getValue());

			try {
				flatService.createExpensesFlat(expensesFlat);
			} catch (SQLException e2) {
				// TODO Auto-generated catch block
				e2.printStackTrace();
			}

		});

		expensesWindowButtonHorizontalLayout.addComponent(expensesAddButton);

		Button expensesCancelButton = new Button("Cancel");
		expensesCancelButton.setSizeFull();
		expensesCancelButton.addClickListener(click -> {
			expensesWindow.close();
		});
		expensesWindowButtonHorizontalLayout.addComponent(expensesCancelButton);

		expensesWindow.center();

		UI.getCurrent().addWindow(expensesWindow);

	});

	flatGrid.addSelectionListener(SelectionEvent -> {
		expensesInfoButton.setEnabled(true);
	});

	return expensesInfoButton;
}
	
	
	
	
	
	
	
	
	

	public Grid bankInfoWindowPaymentInfoGrid(List<Flat> paymentList) {

		Grid bankInfoWindowPaymentInfoGrid = new Grid();
		bankInfoWindowPaymentInfoGrid.setSizeFull();

		bankInfoWindowPaymentInfoGrid.addColumn("paymentDate", String.class);
		bankInfoWindowPaymentInfoGrid.addColumn("paymentSum", Double.class);
		bankInfoWindowPaymentInfoGrid.addColumn("contractNumber", String.class);
		bankInfoWindowPaymentInfoGrid.addColumn("payer", String.class);

		double paymentSum = 0;

		try {

			Iterator<Flat> itr = paymentList.iterator();
			while (itr.hasNext()) {
				Flat flatFromList = itr.next();

				paymentSum = paymentSum + flatFromList.getBankTablePaymentSum();

				bankInfoWindowPaymentInfoGrid.addRow(
						flatService.dateFormatForGrid(flatFromList.getBankTablePaymentDate()),
						flatFromList.getBankTablePaymentSum(),
						flatService.getFlatByFlatIdFromFlatBuyerTable(flatFromList.getIdFlatTable())
								.getFlatContractNumber()
								+ " from "
								+ flatService.getFlatByFlatIdFromFlatBuyerTable(flatFromList.getIdFlatTable())
										.getFlatContractDate(),
						flatService.getFlatByFlatIdFromFlatBuyerTable(flatFromList.getIdFlatTable())
								.getFlatBuyerFirstname()
								+ " "
								+ flatService.getFlatByFlatIdFromFlatBuyerTable(flatFromList.getIdFlatTable())
										.getFlatBuyerLastname()
								+ " " + flatService.getFlatByFlatIdFromFlatBuyerTable(flatFromList.getIdFlatTable())
										.getFlatBuyerSurname()

				);

			}
			FooterRow bankInfoWindowPaymentInfoGridFooterRow = bankInfoWindowPaymentInfoGrid.prependFooterRow();

			bankInfoWindowPaymentInfoGridFooterRow.getCell("paymentDate").setText("Total: ");
			bankInfoWindowPaymentInfoGridFooterRow.getCell("paymentSum")
					.setText(decimalFormat.format(paymentSum).replace(",", ".") + " hrn");

		} catch (SQLException e3) {
			// TODO Auto-generated catch block
			e3.printStackTrace();
		}

		return bankInfoWindowPaymentInfoGrid;
	}

	
	public ComboBox bankInfoWindowContractComboBox() {
		
//		ComboBox bankContractNumberComboBox = new ComboBox("Contract Number");
		
		List<Flat> flatBuyerList = new ArrayList<>();

		try {
			flatBuyerList = flatService.getFlatsFromFlatBuyerTable();
			Iterator<Flat> iter = flatBuyerList.iterator();
			while (iter.hasNext()) {
				Flat flatFromList = iter.next();

				bankContractNumberComboBox.addItem(flatFromList.getFlatContractNumber() + " from "

						+ flatService.dateFormatForGrid(flatFromList.getFlatContractDate()));
				
				bankContractNumberComboBox.setNullSelectionAllowed(false);
			}
			
		} catch (SQLException e3) {
			// TODO Auto-generated catch block
			e3.printStackTrace();
		}
		

		return bankContractNumberComboBox;
	}
	
	
	
	
	
	
	public Button paymentInfoButton() {

		Button paymentInfoButton = new Button("Payment Info");
		paymentInfoButton.setSizeFull();

		paymentInfoButton.addClickListener(e1 -> {

			Window bankInfoWindow = new Window("Payment Info");
			bankInfoWindow.setWidth("700px");
			bankInfoWindow.setHeight("500px");

			VerticalLayout bankInfoWindowVerticalLayout = new VerticalLayout();
			bankInfoWindowVerticalLayout.setMargin(true);
			bankInfoWindowVerticalLayout.setSizeFull();
			bankInfoWindow.setContent(bankInfoWindowVerticalLayout);

			HorizontalLayout bankInfoWindowDateHorizontalLayout = new HorizontalLayout();
			bankInfoWindowDateHorizontalLayout.setSpacing(true);
			bankInfoWindowDateHorizontalLayout.setHeight("65px");
			bankInfoWindowDateHorizontalLayout.setWidth("100%");
			bankInfoWindowVerticalLayout.addComponent(bankInfoWindowDateHorizontalLayout);


			
			DateField bankPaymentDateFromDateField = new DateField("From Payment Date");
			bankPaymentDateFromDateField.setValue(new java.util.Date());
			bankPaymentDateFromDateField.setDateFormat("dd.MM.yyyy");
			bankInfoWindowDateHorizontalLayout.addComponent(bankPaymentDateFromDateField);
			bankInfoWindowDateHorizontalLayout.setComponentAlignment(bankPaymentDateFromDateField, Alignment.BOTTOM_CENTER);


			
			DateField bankPaymentDateToDateField = new DateField("To Payment Date");
			bankPaymentDateToDateField.setValue(new java.util.Date());
			bankPaymentDateToDateField.setDateFormat("dd.MM.yyyy");
			bankInfoWindowDateHorizontalLayout.addComponent(bankPaymentDateToDateField);
			bankInfoWindowDateHorizontalLayout.setComponentAlignment(bankPaymentDateToDateField, Alignment.BOTTOM_CENTER);

			
			
			HorizontalLayout bankInfoWindowContractHorizontalLayout = new HorizontalLayout();
			bankInfoWindowContractHorizontalLayout.setSpacing(true);
			bankInfoWindowContractHorizontalLayout.setHeight("65px");
			bankInfoWindowContractHorizontalLayout.setWidth("100%");
			bankInfoWindowVerticalLayout.addComponent(bankInfoWindowContractHorizontalLayout);

			ComboBox bankInfoWindowContractComboBox = new ComboBox();
			bankInfoWindowContractComboBox = bankInfoWindowContractComboBox();
			bankInfoWindowContractHorizontalLayout.addComponent(bankInfoWindowContractComboBox);
			bankInfoWindowContractHorizontalLayout.setComponentAlignment(bankInfoWindowContractComboBox, Alignment.BOTTOM_CENTER);
			
			
			
			
			
			
			
			HorizontalLayout bankInfoWindowPreGridHorizontalLayout = new HorizontalLayout();
			bankInfoWindowPreGridHorizontalLayout.setHeight("5px");
			bankInfoWindowPreGridHorizontalLayout.setWidth("100%");
			bankInfoWindowVerticalLayout.addComponent(bankInfoWindowPreGridHorizontalLayout);

			HorizontalLayout bankInfoWindowGridHorizontalLayout = new HorizontalLayout();
			bankInfoWindowGridHorizontalLayout.setSizeFull();
			bankInfoWindowVerticalLayout.addComponent(bankInfoWindowGridHorizontalLayout);
			bankInfoWindowVerticalLayout.setExpandRatio(bankInfoWindowGridHorizontalLayout, 1.0f);

			Button bankInfoWindowSelectDateButton = new Button("Select");
//			bankInfoWindowSelectButton.setHeight("40px");
			bankInfoWindowSelectDateButton.setWidth("185px");
//			bankInfoWindowSelectDateButton.setWidth("100%");
//			bankInfoWindowSelectButton.setSizeFull();
			bankInfoWindowDateHorizontalLayout.addComponent(bankInfoWindowSelectDateButton);
			bankInfoWindowDateHorizontalLayout.setComponentAlignment(bankInfoWindowSelectDateButton, Alignment.BOTTOM_CENTER);

			bankInfoWindowSelectDateButton.addClickListener(click -> {

				bankInfoWindowGridHorizontalLayout.removeAllComponents();

				String fromDate = flatService.dateFormatForDB(bankPaymentDateFromDateField.getValue());
				String toDate = flatService.dateFormatForDB(bankPaymentDateToDateField.getValue());

				try {
					bankInfoWindowGridHorizontalLayout.addComponent(bankInfoWindowPaymentInfoGrid(
							flatService.getSelectedItemsByDateFromBankTable(fromDate, toDate)));

				} catch (SQLException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}

			});

			
			Button bankInfoWindowSelectContractButton = new Button("Select");
			bankInfoWindowSelectContractButton.setEnabled(false);
//			bankInfoWindowSelectButton.setHeight("40px");
			bankInfoWindowSelectContractButton.setWidth("185px");
//			bankInfoWindowSelectContractButton.setWidth("100%");
//			bankInfoWindowSelectButton.setSizeFull();
			bankInfoWindowContractHorizontalLayout.addComponent(bankInfoWindowSelectContractButton);
			bankInfoWindowContractHorizontalLayout.setComponentAlignment(bankInfoWindowSelectContractButton, Alignment.BOTTOM_CENTER);
			
			bankInfoWindowContractComboBox().addValueChangeListener(event -> {
				
				String contractTextFromComboBox = bankInfoWindowContractComboBox().getValue().toString();
				
				if (contractTextFromComboBox.equals("")) {
					bankInfoWindowSelectContractButton.setEnabled(false);
				}
				else {
					bankInfoWindowSelectContractButton.setEnabled(true);
				}

			});
			
			
			bankInfoWindowSelectContractButton.addClickListener(click -> {

				bankInfoWindowGridHorizontalLayout.removeAllComponents();

				String contractTextFromComboBox = bankInfoWindowContractComboBox().getValue().toString();
				
				String[] contractNumberDate = contractTextFromComboBox.split(" from ");
				
				String contractDate = flatService.dateFormatForDB(contractNumberDate[1]);
				
				try {

					bankInfoWindowGridHorizontalLayout.addComponent(bankInfoWindowPaymentInfoGrid(
							flatService.getFlatsFromBankTableByIdFlatTable(flatService.getFlatFromFlatBuyerTableByContract(contractNumberDate[0], contractDate).getIdFlatTable())));
					
				} catch (SQLException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}

			});
			
			
			
			
			Button bankInfoWindowSelectAllButton = new Button("Select All");
//			bankInfoWindowSelectAllButton.setSizeFull();
			bankInfoWindowSelectAllButton.setWidth("185px");
//			bankInfoWindowSelectAllButton.setWidth("100%");
			bankInfoWindowContractHorizontalLayout.addComponent(bankInfoWindowSelectAllButton);
			bankInfoWindowContractHorizontalLayout.setComponentAlignment(bankInfoWindowSelectAllButton, Alignment.BOTTOM_CENTER);

			bankInfoWindowSelectAllButton.addClickListener(click -> {

				bankInfoWindowGridHorizontalLayout.removeAllComponents();

				List<Flat> paymentList = new ArrayList<>();
				try {
					paymentList = flatService.getPaymentsFromBankTable();
					bankInfoWindowGridHorizontalLayout.addComponent(bankInfoWindowPaymentInfoGrid(paymentList));
				} catch (SQLException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}

			});

			bankInfoWindow.center();

			UI.getCurrent().addWindow(bankInfoWindow);

		});

		return paymentInfoButton;
	}

////////////////////////////	
	public Button addDateButton() {

		Button addDateButton = new Button("Add Date");
		addDateButton.setSizeFull();

		addDateButton.addClickListener(e1 -> {

			Window bankInfoWindow = new Window("Add Date");
			bankInfoWindow.setWidth("600px");
			bankInfoWindow.setHeight("410px");

			VerticalLayout bankInfoWindowVerticalLayout = new VerticalLayout();

			bankInfoWindow.setContent(bankInfoWindowVerticalLayout);

			FormLayout bankInfoWindowFormLayout = new FormLayout();
			bankInfoWindowFormLayout.setSizeFull();
			bankInfoWindowFormLayout.setMargin(true);

			bankInfoWindowFormLayout.setSizeFull();

			DateField bankPaymentDateDateField = new DateField("Payment Date");
			bankPaymentDateDateField.setValue(new java.util.Date());

			bankPaymentDateDateField.setResolution(Resolution.MINUTE);

			bankPaymentDateDateField.setDateFormat("dd.MM.yyyy HH:mm:ss");
			bankInfoWindowFormLayout.addComponent(bankPaymentDateDateField);

			bankInfoWindowVerticalLayout.addComponent(bankInfoWindowFormLayout);

			HorizontalLayout bankInfoWindowHorizontalLayout = new HorizontalLayout();
			bankInfoWindowHorizontalLayout.setSizeFull();
			bankInfoWindowHorizontalLayout.setMargin(true);
			bankInfoWindowHorizontalLayout.setSpacing(true);
			bankInfoWindowVerticalLayout.addComponent(bankInfoWindowHorizontalLayout);

			Button bankInfoWindowPaymentButton = new Button("Add Date");
			bankInfoWindowPaymentButton.setSizeFull();

			bankInfoWindowPaymentButton.addClickListener(click -> {

				Flat flat = new Flat();

				try {
					flat.setNew_date_tablecol(bankPaymentDateDateField.getValue());
				} catch (ParseException e2) {
					// TODO Auto-generated catch block
					e2.printStackTrace();
				}
				flat.setNew_date_tablecol1(bankPaymentDateDateField.getValue());

				try {
					flatService.createDateTable(flat);
				} catch (SQLException | ParseException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}

			});

			bankInfoWindowHorizontalLayout.addComponent(bankInfoWindowPaymentButton);

			Button bankInfoWindowShowButton = new Button("Show Date");
			bankInfoWindowShowButton.setSizeFull();

			bankInfoWindowShowButton.addClickListener(click -> {

				Flat flat = new Flat();

				try {
					try {
						flat = flatService.getDateFromDateTable(2);
					} catch (ParseException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
				} catch (SQLException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}

//
				try {
					DateFormat dateFormat = new SimpleDateFormat("dd.MM.yyyy HH:mm:ss");
					String dateStr = dateFormat.format(flat.getNew_date_tablecol());

					Grid dateGrid = new Grid("Date");

					dateGrid.addColumn("col", String.class);
					;
					dateGrid.addColumn("col1", Date.class);

					dateGrid.addRow(dateStr
//	
					, flat.getNew_date_tablecol1());
					bankInfoWindowVerticalLayout.addComponent(dateGrid);

				} catch (ParseException e2) {
					// TODO Auto-generated catch block
					e2.printStackTrace();
				}

			});

			bankInfoWindowHorizontalLayout.addComponent(bankInfoWindowShowButton);

			Button bankInfoWindowCancelButton = new Button("Cancel");
			bankInfoWindowCancelButton.setSizeFull();

			bankInfoWindowCancelButton.addClickListener(event -> {
				bankInfoWindow.close();
			});

			bankInfoWindowHorizontalLayout.addComponent(bankInfoWindowCancelButton);

			bankInfoWindow.center();

			UI.getCurrent().addWindow(bankInfoWindow);

		});

		return addDateButton;
	}
////////////////////////////

	public Button addBankInfoButton() {

		Button addBankInfoButton = new Button("Add Bank Info");
		addBankInfoButton.setSizeFull();

		addBankInfoButton.addClickListener(e1 -> {

			Window bankInfoWindow = new Window("Bank Info");
			bankInfoWindow.setWidth("450px");
			bankInfoWindow.setHeight("410px");

			VerticalLayout bankInfoWindowVerticalLayout = new VerticalLayout();

			bankInfoWindow.setContent(bankInfoWindowVerticalLayout);

			FormLayout bankInfoWindowFormLayout = new FormLayout();
			bankInfoWindowFormLayout.setSizeFull();
			bankInfoWindowFormLayout.setMargin(true);

			bankInfoWindowFormLayout.setSizeFull();

			DateField bankPaymentDateDateField = new DateField("Payment Date");
			bankPaymentDateDateField.setValue(new java.util.Date());

			bankPaymentDateDateField.setResolution(Resolution.MINUTE);

			bankPaymentDateDateField.setDateFormat("dd.MM.yyyy HH:mm:ss");
			bankInfoWindowFormLayout.addComponent(bankPaymentDateDateField);

			TextField bankPaymentSumTextField = new TextField("Payment Sum");
			bankInfoWindowFormLayout.addComponent(bankPaymentSumTextField);

//			ComboBox bankContractNumberComboBox = new ComboBox("Contract Number");
//
//			List<Flat> flatBuyerList = new ArrayList<>();
//
//			try {
//				flatBuyerList = flatService.getFlatsFromFlatBuyerTable();
//				Iterator<Flat> iter = flatBuyerList.iterator();
//				while (iter.hasNext()) {
//					Flat flatFromList = iter.next();
//
//					DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
//
//					LocalDate localDate = LocalDate.parse(dateFormat.format(flatFromList.getFlatContractDate()))
//							.plusDays(1);
//
//					String[] contractDate = localDate.toString().split("-");
//
//					String contractDateNew = contractDate[2] + "." + contractDate[1] + "." + contractDate[0];
//
//					bankContractNumberComboBox.addItem(flatFromList.getFlatContractNumber() + " from "
//
//							+ contractDateNew);
//
//				}
//			} catch (SQLException e3) {
//				// TODO Auto-generated catch block
//				e3.printStackTrace();
//			}
//
//			bankInfoWindowFormLayout.addComponent(bankContractNumberComboBox);

			bankInfoWindowFormLayout.addComponent(bankInfoWindowContractComboBox());
			
			
			
			TextField bankPayerTextField = new TextField("Payer Info");

//			bankContractNumberComboBox.addValueChangeListener(select -> {

			bankInfoWindowContractComboBox().addValueChangeListener(select -> {
			
				Flat paymentFlat = new Flat();

				String contractText = bankInfoWindowContractComboBox().getValue().toString();

				String[] contractNumberDate = contractText.split(" from ");

				String contractDateReplace = contractNumberDate[1].replace(".", "-");

				String[] contractDate = contractDateReplace.split("-");

				String contractDateNew = contractDate[2] + "." + contractDate[1] + "." + contractDate[0];

				try {
					paymentFlat = flatService.getFlatByFlatIdFromFlatBuyerTable(
							flatService.getFlatFromFlatBuyerTableByContract(contractNumberDate[0], contractDateNew)
									.getIdFlatTable());

				} catch (SQLException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}

				bankPayerTextField.setValue(paymentFlat.getFlatBuyerFirstname() + " "
						+ paymentFlat.getFlatBuyerLastname() + " " + paymentFlat.getFlatBuyerSurname());

			});

			bankPayerTextField.setEnabled(false);

			bankInfoWindowFormLayout.addComponent(bankPayerTextField);

			bankInfoWindowVerticalLayout.addComponent(bankInfoWindowFormLayout);

			HorizontalLayout bankInfoWindowHorizontalLayout = new HorizontalLayout();
			bankInfoWindowHorizontalLayout.setSizeFull();
			bankInfoWindowHorizontalLayout.setMargin(true);
			bankInfoWindowHorizontalLayout.setSpacing(true);
			bankInfoWindowVerticalLayout.addComponent(bankInfoWindowHorizontalLayout);

			Button bankInfoWindowPaymentButton = new Button("Add Payment");
			bankInfoWindowPaymentButton.setSizeFull();
			bankInfoWindowPaymentButton.setEnabled(false);

			bankPaymentSumTextField.addValueChangeListener(event -> {

				if (bankPaymentSumTextField.getValue().equals("")) {
					bankInfoWindowPaymentButton.setEnabled(false);
				} else {

					bankPayerTextField.addValueChangeListener(event1 -> {

						if (bankPayerTextField.getValue().equals("")) {
							bankInfoWindowPaymentButton.setEnabled(false);
						} else {
							bankInfoWindowPaymentButton.setEnabled(true);
						}

					});
				}

			});

			bankInfoWindowPaymentButton.addClickListener(click -> {

				Flat paymentFlat = new Flat();

				String contractText = bankInfoWindowContractComboBox().getValue().toString();

				String[] contractNumberDate = contractText.split(" from ");

				String contractDateReplace = contractNumberDate[1].replace(".", "-");

				String[] contractDate = contractDateReplace.split("-");

				String contractDateNew = contractDate[2] + "." + contractDate[1] + "." + contractDate[0];

				try {
					paymentFlat.setIdFlatTable(
							flatService.getFlatFromFlatBuyerTableByContract(contractNumberDate[0], contractDateNew)
									.getIdFlatTable());
					paymentFlat.setBankTablePaymentDate(bankPaymentDateDateField.getValue());
					paymentFlat.setBankTablePaymentSum(Double.parseDouble(bankPaymentSumTextField.getValue()));
					flatService.createBankPayment(paymentFlat);

				} catch (SQLException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}

				bankInfoWindowPaymentButton.setEnabled(false);

			});

			bankInfoWindowHorizontalLayout.addComponent(bankInfoWindowPaymentButton);

			Button bankInfoWindowCancelButton = new Button("Cancel");
			bankInfoWindowCancelButton.setSizeFull();

			bankInfoWindowCancelButton.addClickListener(event -> {
				bankInfoWindow.close();
			});

			bankInfoWindowHorizontalLayout.addComponent(bankInfoWindowCancelButton);

			bankInfoWindow.center();

			UI.getCurrent().addWindow(bankInfoWindow);

		});

		return addBankInfoButton;
	}

	public Button cancelButton() {

		Button cancelButton = new Button("Cancel");
		cancelButton.setSizeFull();
		cancelButton.addClickListener(e1 -> {
			UI.getCurrent().getNavigator().navigateTo("main");
		});

		return cancelButton;
	}

	//////////////////////////////////////
	public Button reportButton() {

		Button reportButton = new Button("Report !!!");
		addComponent(reportButton);

		reportButton.addClickListener(e -> {
			Window reportWindow = new Window("Report");

			VerticalLayout reportWindowVerticalLayout = new VerticalLayout();
//			reportWindowVerticalLayout.setSizeFull();
			reportWindow.setContent(reportWindowVerticalLayout);

			Grid reportGrid = new Grid();
//			reportGrid.setwColumnsizeMode(ColumnResizeMode.SIMPLE);;
			reportGrid.setSizeFull();
			reportGrid.addColumn("buildingCorps", String.class);
			reportGrid.addColumn("flatNumber", Integer.class);
			reportGrid.addColumn("flatArea", Double.class);
			reportGrid.addColumn("flatCost_$", Integer.class);
			reportGrid.addColumn("generalExpenses_$", Integer.class);
//			reportGrid.addColumn("generalArea", Double.class);
			reportGrid.addColumn("BCExpenses_$", Integer.class);
			reportGrid.addColumn("BC50%generalExpenses_$", Integer.class);
			reportGrid.addColumn("BCArea", Double.class);
			reportGrid.addColumn("CMExpenses_$", Integer.class);
			reportGrid.addColumn("CM50%generalExpenses_$", Integer.class);
			reportGrid.addColumn("CMArea", Double.class);

			List<Flat> flatList = new ArrayList<>();

			double flatArea = 0;
			double BCArea = 0;
			double CMArea = 0;

			try {
//				flatList = flatService.getFlatsFromDB();

				flatList = flatService.getFlatsByFlatSetFromDB("Solded");

			} catch (SQLException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
//			Date dateFromDB = new Date();

			Iterator<Flat> itr = flatList.iterator();
			while (itr.hasNext()) {
				Flat flatFromList = itr.next();

				double BCGridArea = 0;
				double CMGridArea = 0;
				try {
					BCGridArea = (flatService
							.getExpensesFlatInfoFromExpensesTableByFlatId(flatFromList.getIdFlatTable(), "B.C.")
							+ (flatService.getExpensesFlatInfoFromExpensesTableByFlatId(flatFromList.getIdFlatTable(),
									"General") / 2))
							/ (flatFromList.getFlatCost() / flatFromList.getFlatArea());
					CMGridArea = (flatService
							.getExpensesFlatInfoFromExpensesTableByFlatId(flatFromList.getIdFlatTable(), "C.M.")
							+ (flatService.getExpensesFlatInfoFromExpensesTableByFlatId(flatFromList.getIdFlatTable(),
									"General") / 2))
							/ (flatFromList.getFlatCost() / flatFromList.getFlatArea());

				} catch (SQLException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}

				flatArea = flatArea + flatFromList.getFlatArea();

				BCArea = BCArea + BCGridArea;
				CMArea = CMArea + CMGridArea;

				try {
					reportGrid.addRow(flatFromList.getBuildingCorps(), flatFromList.getFlatNumber(),
							flatFromList.getFlatArea(), flatFromList.getFlatCost(),
							flatService.getExpensesFlatInfoFromExpensesTableByFlatId(flatFromList.getIdFlatTable(),
									"General")

							,
							flatService.getExpensesFlatInfoFromExpensesTableByFlatId(flatFromList.getIdFlatTable(),
									"B.C."),
							flatService.getExpensesFlatInfoFromExpensesTableByFlatId(flatFromList.getIdFlatTable(),
									"General") / 2,
							BCGridArea

							,
							flatService.getExpensesFlatInfoFromExpensesTableByFlatId(flatFromList.getIdFlatTable(),
									"C.M."),
							flatService.getExpensesFlatInfoFromExpensesTableByFlatId(flatFromList.getIdFlatTable(),
									"General") / 2,
							CMGridArea

					);

				} catch (SQLException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}

			}

			FooterRow reportGridFooterRow = reportGrid.prependFooterRow();
			reportGridFooterRow.getCell("flatArea").setText("Total: " + flatArea);
			reportGridFooterRow.getCell("BCArea").setText("Total: " + BCArea);
			reportGridFooterRow.getCell("CMArea").setText("Total: " + CMArea);
//			reportGridFooterRow.getCell("flatArea").setText("Total: " + flatArea);
//			reportGridFooterRow.getCell("flatArea").setText("Total: " + flatArea);

			reportWindowVerticalLayout.addComponent(reportGrid);

			TextField allFlatsAreaTextField = new TextField("All Flats Area");
			try {
				allFlatsAreaTextField.setValue(String.valueOf(flatService.sumAllFlatsArea()));
			} catch (ReadOnlyException | SQLException e3) {
				// TODO Auto-generated catch block
				e3.printStackTrace();
			}
			allFlatsAreaTextField.setEnabled(false);

			reportWindowVerticalLayout.addComponent(allFlatsAreaTextField);

			Grid distributionGrid = new Grid();
//			distributionGrid.setHeightByRows(2.0);
			distributionGrid.setSizeFull();
			distributionGrid.addColumn("name", String.class);
			distributionGrid.addColumn("flatArea", Double.class);

			distributionGrid.addColumn("receivedArea", Double.class);

			distributionGrid.addColumn("residualArea", Double.class);

			distributionGrid.addRow("B.C. 70%", Double.parseDouble(allFlatsAreaTextField.getValue()) * 70 / 100, BCArea,
					Double.parseDouble(allFlatsAreaTextField.getValue()) * 70 / 100 - BCArea);
			distributionGrid.addRow("C.M. 30%", Double.parseDouble(allFlatsAreaTextField.getValue()) * 30 / 100, CMArea,
					Double.parseDouble(allFlatsAreaTextField.getValue()) * 30 / 100 - CMArea);

			reportWindowVerticalLayout.addComponent(distributionGrid);

			HorizontalLayout reportInfoWindowHorizontalLayout = new HorizontalLayout();
			reportWindowVerticalLayout.addComponent(reportInfoWindowHorizontalLayout);

			reportWindow.center();
			UI.getCurrent().addWindow(reportWindow);
		});

		return reportButton;

	}
//	}

	@Override
	public void enter(ViewChangeEvent event) {
		// TODO Auto-generated method stub

	}

}
