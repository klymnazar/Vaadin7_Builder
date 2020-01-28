package com.example.Vaadin7_Builder.view;

import java.sql.SQLException;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Iterator;
import java.util.List;
import com.example.Vaadin7_Builder.model.Flat;
import com.example.Vaadin7_Builder.service.FlatService;
import com.example.Vaadin7_Builder.view.AccountingViewServises.AddBankInfoButton;
import com.example.Vaadin7_Builder.view.AccountingViewServises.AddExpensesButton;
import com.example.Vaadin7_Builder.view.AccountingViewServises.ExpensesInfoButton;
import com.example.Vaadin7_Builder.view.AccountingViewServises.IncomeSumButton;
import com.example.Vaadin7_Builder.view.AccountingViewServises.PaymentInfoButton;
import com.vaadin.navigator.View;
import com.vaadin.navigator.ViewChangeListener.ViewChangeEvent;
import com.vaadin.ui.Button;
import com.vaadin.ui.CheckBox;
import com.vaadin.ui.ComboBox;
import com.vaadin.ui.Grid;
import com.vaadin.ui.Grid.FooterRow;
import com.vaadin.ui.renderers.ButtonRenderer;
import com.vaadin.ui.renderers.DateRenderer;
import com.vaadin.ui.HorizontalLayout;
import com.vaadin.ui.Layout;
import com.vaadin.ui.Panel;
import com.vaadin.ui.UI;
import com.vaadin.ui.VerticalLayout;
import com.vaadin.ui.Window;

public class AccountingView extends HorizontalLayout implements View {

	FlatService flatService = new FlatService();

	IncomeSumButton incomeSumButton = new IncomeSumButton();

	AddExpensesButton addExpensesButton = new AddExpensesButton();

	ExpensesInfoButton expensesInfoButton = new ExpensesInfoButton();

	AddBankInfoButton addBankInfoButton = new AddBankInfoButton();

	PaymentInfoButton paymentInfoButton = new PaymentInfoButton();

//	private DecimalFormat decimalFormat = new DecimalFormat("0.00");
	
	private Grid flatGrid = new Grid();

	private Panel accountingInfoPanel = new Panel("Accounting Info Panel");

	private String generalExpenses = "General";
	private String bcExpenses = "B.C.";
	private String cmExpenses = "C.M.";

//	private double generalBC = 0.5; // 50%
//	private double generalCM = 0.5; // 50%

	public AccountingView() throws SQLException {

		setSizeFull();
		setMargin(true);
		setSpacing(true);

		VerticalLayout accountingInfoGridVerticalLayout = new VerticalLayout();
		accountingInfoGridVerticalLayout.setSizeFull();
		accountingInfoGridVerticalLayout.setSpacing(true);
		addComponent(accountingInfoGridVerticalLayout);

		setExpandRatio(accountingInfoGridVerticalLayout, 1.0f);

		addComponent(flatCheckBoxVerticalPanel());

		try {

			flatGrid = flatGridFlatInfoView(flatService.getFlatsFromOrderedFlatTable());

		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		accountingInfoPanel.setSizeFull();
		accountingInfoGridVerticalLayout.addComponent(accountingInfoPanel);

		accountingInfoPanel.setContent(flatGrid);
		accountingInfoGridVerticalLayout.setExpandRatio(accountingInfoPanel, 1.0f);

		accountingInfoGridVerticalLayout.addComponent(flatButtonHorizontalLayout());

	}

	public Layout flatButtonHorizontalLayout() throws SQLException {

		HorizontalLayout buttonHorizontalLayout = new HorizontalLayout();

		buttonHorizontalLayout.setSpacing(true);
		buttonHorizontalLayout.setHeight("50px");
		buttonHorizontalLayout.setWidth("100%");

		buttonHorizontalLayout.addComponent(incomeSumButton.incomeSumButton());

		buttonHorizontalLayout.addComponent(addExpensesButton.addExpensesButton(flatGrid));

		buttonHorizontalLayout.addComponent(expensesInfoButton.expensesInfoButton());

		buttonHorizontalLayout.addComponent(addBankInfoButton.addBankInfoButton());

		buttonHorizontalLayout.addComponent(paymentInfoButton.paymentInfoButton());

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

			int idFlatTableFromSelectedRowFlatGrid = Integer.parseInt(flatGrid.getContainerDataSource()
					.getItem(clickEvent.getItemId()).getItemProperty("idFlatTable").getValue().toString());

			detailsInfoWindow(idFlatTableFromSelectedRowFlatGrid);

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
					flatFromBuyerTable.getFlatContractDate(), flatFromBuyerTable.getFlatContractNumber(), flatCost_m$,
					flatFromBuyerTable.getFlatCost(), expenses, "Expenses", availableSum, availableSum / flatCost_m$,
					flatFromBuyerTable.getFlatSellerName(), flatFromBuyerTable.getFlatNotes(), expensesGeneral,
					expensesGeneral / flatCost_m$, expensesBC, expensesBC / flatCost_m$,
					expensesBC / flatCost_m$ + expensesGeneral / flatCost_m$ / 2, expensesCM, expensesCM / flatCost_m$,
					expensesCM / flatCost_m$ + expensesGeneral / flatCost_m$ / 2, paymentSum,
					paymentSum / flatFromList.getFlatArea());

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

	public Window detailsInfoWindow(int idFlatTableFromSelectedRowFlatGrid) {

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
			List<Flat> expensesTableList = flatService
					.getExpensesByFlatIdAndCategoryFromExpensesTable(idFlatTableFromSelectedRowFlatGrid, bcExpenses);

			Iterator<Flat> itr = expensesTableList.iterator();
			while (itr.hasNext()) {
				Flat flatFromList = itr.next();

				expensesSumBC = expensesSumBC + flatFromList.getExpensesTableSum();

				detailsBCInfoWindowGrid.addRow(flatFromList.getExpensesTableDate(), flatFromList.getExpensesTableSum(),
						flatFromList.getExpensesTableValue());
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
			List<Flat> expensesTableList = flatService
					.getExpensesByFlatIdAndCategoryFromExpensesTable(idFlatTableFromSelectedRowFlatGrid, cmExpenses);

			Iterator<Flat> itr = expensesTableList.iterator();
			while (itr.hasNext()) {
				Flat flatFromList = itr.next();

				expensesSumCM = expensesSumCM + flatFromList.getExpensesTableSum();

				detailsCMInfoWindowGrid.addRow(flatFromList.getExpensesTableDate(), flatFromList.getExpensesTableSum(),
						flatFromList.getExpensesTableValue());
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

		return detailsInfoWindow;
	}

	public Panel flatCheckBoxVerticalPanel() {

		Panel settingsCheckBoxPanel = new Panel("Settings");
		settingsCheckBoxPanel.setWidth("210px");
		settingsCheckBoxPanel.setHeight("100%");

		VerticalLayout settingPanelVerticalLayout = new VerticalLayout();
		settingPanelVerticalLayout.setMargin(true);
		settingPanelVerticalLayout.setSpacing(true);
		settingPanelVerticalLayout.setSizeFull();

		settingsCheckBoxPanel.setContent(settingPanelVerticalLayout);

		HorizontalLayout checkBoxHorizontalLayout = new HorizontalLayout();
		checkBoxHorizontalLayout.setHeight("70px");
		checkBoxHorizontalLayout.setWidth("100%");

		settingPanelVerticalLayout.addComponent(checkBoxHorizontalLayout);

		VerticalLayout checkBoxVerticalLayout = new VerticalLayout();
		checkBoxVerticalLayout.setSizeFull();

		settingPanelVerticalLayout.addComponent(checkBoxVerticalLayout);
		settingPanelVerticalLayout.setExpandRatio(checkBoxVerticalLayout, 1.0f);

		CheckBox flatInfoCheckBox = new CheckBox("Flat Info");
		checkBoxVerticalLayout.addComponent(flatInfoCheckBox);
		flatInfoCheckBox.setValue(false);
		flatInfoCheckBox.addValueChangeListener(event -> {

			flatInfoCheckBoxChangeListener(flatInfoCheckBox);

		});

		CheckBox buyerInfoCheckBox = new CheckBox("Buyer Info");
		checkBoxVerticalLayout.addComponent(buyerInfoCheckBox);
		buyerInfoCheckBox.setValue(false);
		buyerInfoCheckBox.addValueChangeListener(event -> {

			buyerInfoCheckBoxChangeListener(buyerInfoCheckBox);

		});

		CheckBox contractInfoCheckBox = new CheckBox("Contract Info");
		checkBoxVerticalLayout.addComponent(contractInfoCheckBox);

		contractInfoCheckBox.addValueChangeListener(event -> {

			contractInfoCheckBoxChangeListener(contractInfoCheckBox);

		});

		CheckBox flatCostM2CheckBox = new CheckBox("Flat Cost m2");
		checkBoxVerticalLayout.addComponent(flatCostM2CheckBox);
		flatCostM2CheckBox.addValueChangeListener(event -> {

			flatCostM2CheckBoxChangeListener(flatCostM2CheckBox);
		});

		CheckBox flatCostCheckBox = new CheckBox("Flat Cost");
		checkBoxVerticalLayout.addComponent(flatCostCheckBox);
		flatCostCheckBox.addValueChangeListener(event -> {

			flatCostCheckBoxChangeListener(flatCostCheckBox);

		});

		CheckBox expensesCheckBox = new CheckBox("Expenses Info");
		checkBoxVerticalLayout.addComponent(expensesCheckBox);
		expensesCheckBox.setValue(false);
		expensesCheckBox.addValueChangeListener(event -> {

			expensesCheckBoxChangeListener(expensesCheckBox);

		});

		CheckBox availableSumCheckBox = new CheckBox("Available Sum");
		checkBoxVerticalLayout.addComponent(availableSumCheckBox);
		availableSumCheckBox.setValue(false);
		availableSumCheckBox.addValueChangeListener(event -> {

			availableSumCheckBoxChangeListener(availableSumCheckBox);

		});

		CheckBox generalExpensesCheckBox = new CheckBox("General Expenses");
		checkBoxVerticalLayout.addComponent(generalExpensesCheckBox);
		generalExpensesCheckBox.setValue(false);
		generalExpensesCheckBox.addValueChangeListener(event -> {

			generalExpensesCheckBoxChangeListener(generalExpensesCheckBox);

		});

		CheckBox bcExpensesCheckBox = new CheckBox("B.C. Expenses");
		checkBoxVerticalLayout.addComponent(bcExpensesCheckBox);
		bcExpensesCheckBox.setValue(false);
		bcExpensesCheckBox.addValueChangeListener(event -> {

			bcExpensesCheckBoxChangeListener(bcExpensesCheckBox);

		});

		CheckBox cmExpensesCheckBox = new CheckBox("C.M. Expenses");
		checkBoxVerticalLayout.addComponent(cmExpensesCheckBox);
		cmExpensesCheckBox.setValue(false);
		cmExpensesCheckBox.addValueChangeListener(event -> {

			cmExpensesCheckBoxChangeListener(cmExpensesCheckBox);

		});

		CheckBox bankInfoCheckBox = new CheckBox("Bank Info");
		checkBoxVerticalLayout.addComponent(bankInfoCheckBox);
		bankInfoCheckBox.addValueChangeListener(event -> {

			bankInfoCheckBoxChangeListener(bankInfoCheckBox);

		});

		ComboBox expensesWindowCorpsComboBox = new ComboBox("Building Corps List");
		expensesWindowCorpsComboBox.setNullSelectionAllowed(false);
		checkBoxHorizontalLayout.addComponent(expensesWindowCorpsComboBox);

		Flat flatCorps = new Flat();
		flatCorps.setBuildingCorps("All Building Corps");

		List<Flat> corpsList = new ArrayList<>();

		try {

			corpsList = flatService.getCorpsFromSettingsTable();
			corpsList.add(0, flatCorps);

			Iterator<Flat> itr = corpsList.iterator();
			while (itr.hasNext()) {
				Flat flatFromList = itr.next();

				expensesWindowCorpsComboBox.addItem(flatFromList.getBuildingCorps());

			}

			expensesWindowCorpsComboBox.select("All Building Corps");

		} catch (NumberFormatException | SQLException e4) {
			// TODO Auto-generated catch block
			e4.printStackTrace();
		}

		expensesWindowCorpsComboBox.addValueChangeListener(select -> {

			if (expensesWindowCorpsComboBox.getValue().toString().equals("All Building Corps")) {

				try {
					flatGrid = flatGridFlatInfoView(flatService.getFlatsFromOrderedFlatTable());
				} catch (SQLException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}

			} else {

				try {

					flatGrid = flatGridFlatInfoView(flatService
							.getFlatsByCorpsFromFlatTable(expensesWindowCorpsComboBox.getValue().toString()));

				} catch (SQLException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}

			}

			flatInfoCheckBoxChangeListener(flatInfoCheckBox);

			buyerInfoCheckBoxChangeListener(buyerInfoCheckBox);

			contractInfoCheckBoxChangeListener(contractInfoCheckBox);

			flatCostM2CheckBoxChangeListener(flatCostM2CheckBox);

			flatCostCheckBoxChangeListener(flatCostCheckBox);

			expensesCheckBoxChangeListener(expensesCheckBox);

			availableSumCheckBoxChangeListener(availableSumCheckBox);

			generalExpensesCheckBoxChangeListener(generalExpensesCheckBox);

			bcExpensesCheckBoxChangeListener(bcExpensesCheckBox);

			cmExpensesCheckBoxChangeListener(cmExpensesCheckBox);

			bankInfoCheckBoxChangeListener(bankInfoCheckBox);

			accountingInfoPanel.setContent(flatGrid);

		});

		return settingsCheckBoxPanel;
	}

	private void flatInfoCheckBoxChangeListener(CheckBox flatInfoCheckBox) {

		if (flatInfoCheckBox.isEmpty()) {

			flatGrid.getColumn("flatRooms").setHidden(true);
			flatGrid.getColumn("flatFloor").setHidden(true);

		} else {

			flatGrid.getColumn("flatRooms").setHidden(false);
			flatGrid.getColumn("flatFloor").setHidden(false);

		}

	}

	private void buyerInfoCheckBoxChangeListener(CheckBox buyerInfoCheckBox) {

		if (buyerInfoCheckBox.isEmpty()) {

			flatGrid.getColumn("flatBuyerFirstname").setHidden(true);
			flatGrid.getColumn("flatBuyerLastname").setHidden(true);
			flatGrid.getColumn("flatBuyerSurname").setHidden(true);

		} else {

			flatGrid.getColumn("flatBuyerFirstname").setHidden(false);
			flatGrid.getColumn("flatBuyerLastname").setHidden(false);
			flatGrid.getColumn("flatBuyerSurname").setHidden(false);

		}
	}

	private void contractInfoCheckBoxChangeListener(CheckBox contractInfoCheckBox) {

		if (contractInfoCheckBox.isEmpty()) {

			flatGrid.getColumn("flatContractDate").setHidden(true);
			flatGrid.getColumn("flatContractNumber").setHidden(true);

		} else {

			flatGrid.getColumn("flatContractDate").setHidden(false);
			flatGrid.getColumn("flatContractNumber").setHidden(false);

		}

	}

	private void flatCostM2CheckBoxChangeListener(CheckBox flatCostM2CheckBox) {

		if (flatCostM2CheckBox.isEmpty()) {

			flatGrid.getColumn("m2, flatCost").setHidden(true);

		} else {

			flatGrid.getColumn("m2, flatCost").setHidden(false);

		}

	}

	private void flatCostCheckBoxChangeListener(CheckBox flatCostCheckBox) {

		if (flatCostCheckBox.isEmpty()) {

			flatGrid.getColumn("$, flatCost").setHidden(true);

		} else {

			flatGrid.getColumn("$, flatCost").setHidden(false);

		}

	}

	private void expensesCheckBoxChangeListener(CheckBox expensesCheckBox) {

		if (expensesCheckBox.isEmpty()) {

			flatGrid.getColumn("$, expenses").setHidden(true);
			flatGrid.getColumn("details").setHidden(true);

		} else {

			flatGrid.getColumn("$, expenses").setHidden(false);
			flatGrid.getColumn("details").setHidden(false);

		}

	}

	private void availableSumCheckBoxChangeListener(CheckBox availableSumCheckBox) {

		if (availableSumCheckBox.isEmpty()) {

			flatGrid.getColumn("$, availableSum").setHidden(true);

		} else {

			flatGrid.getColumn("$, availableSum").setHidden(false);

		}

	}

	private void generalExpensesCheckBoxChangeListener(CheckBox generalExpensesCheckBox) {

		if (generalExpensesCheckBox.isEmpty()) {

			flatGrid.getColumn("$, 50/50GeneralExpenses").setHidden(true);
			flatGrid.getColumn("m2, 50/50GeneralExpenses").setHidden(true);

		} else {

			flatGrid.getColumn("$, 50/50GeneralExpenses").setHidden(false);
			flatGrid.getColumn("m2, 50/50GeneralExpenses").setHidden(false);

		}
	}

	private void bcExpensesCheckBoxChangeListener(CheckBox bcExpensesCheckBox) {

		if (bcExpensesCheckBox.isEmpty()) {

			flatGrid.getColumn("$, BCExpenses").setHidden(true);
			flatGrid.getColumn("m2, BCExpenses").setHidden(true);

		} else {

			flatGrid.getColumn("$, BCExpenses").setHidden(false);
			flatGrid.getColumn("m2, BCExpenses").setHidden(false);

		}
	}

	private void cmExpensesCheckBoxChangeListener(CheckBox cmExpensesCheckBox) {

		if (cmExpensesCheckBox.isEmpty()) {

			flatGrid.getColumn("$, CMExpenses").setHidden(true);
			flatGrid.getColumn("m2, CMExpenses").setHidden(true);

		} else {

			flatGrid.getColumn("$, CMExpenses").setHidden(false);
			flatGrid.getColumn("m2, CMExpenses").setHidden(false);

		}
	}

	private void bankInfoCheckBoxChangeListener(CheckBox bankInfoCheckBox) {

		if (bankInfoCheckBox.isEmpty()) {

			flatGrid.getColumn("bankPaymentSum").setHidden(true);
			flatGrid.getColumn("m2, bank").setHidden(true);

		} else {

			flatGrid.getColumn("bankPaymentSum").setHidden(false);
			flatGrid.getColumn("m2, bank").setHidden(false);
		}

	}

	public Button cancelButton() {

		Button cancelButton = new Button("Cancel");
		cancelButton.setSizeFull();
		cancelButton.addClickListener(e1 -> {
			UI.getCurrent().getNavigator().navigateTo("main");
		});

		return cancelButton;
	}

	@Override
	public void enter(ViewChangeEvent event) {
		// TODO Auto-generated method stub

	}

}