package com.example.Vaadin7_Builder.view;

import java.sql.SQLException;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Iterator;
import java.util.List;

import com.example.Vaadin7_Builder.model.Flat;
import com.example.Vaadin7_Builder.service.FlatService;
import com.vaadin.data.Property.ReadOnlyException;
import com.vaadin.data.validator.DoubleValidator;
import com.vaadin.data.validator.IntegerValidator;
import com.vaadin.navigator.View;
import com.vaadin.navigator.ViewChangeListener.ViewChangeEvent;
import com.vaadin.ui.Button;
import com.vaadin.ui.CheckBox;
import com.vaadin.ui.ComboBox;
import com.vaadin.ui.DateField;
import com.vaadin.ui.FormLayout;
import com.vaadin.ui.Grid;
import com.vaadin.ui.GridLayout;
import com.vaadin.ui.Grid.FooterRow;
import com.vaadin.ui.Grid.SingleSelectionModel;
//import com.vaadin.ui.Grid.Column;
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

public class AccountingView extends VerticalLayout implements View {

	FlatService flatService = new FlatService();

	private Grid flatGrid = new Grid();

	private GridLayout accountingInfoGridLayout = new GridLayout(1, 3);

	private String generalExpenses = "General";
	private String bcExpenses = "B.C.";
	private String cmExpenses = "C.M.";

//		private String flatSetReserved = "Reserved";
//		private String flatSetSolded = "Solded";
	private String flatSetFree = "";

	public AccountingView() throws SQLException {

		accountingInfoGridLayout.setMargin(true);
		accountingInfoGridLayout.setSizeFull();
		addComponent(accountingInfoGridLayout);

		flatGrid = flatGridFlatInfoView(flatService.getFlatsFromFlatTableAndFlatBuyerDB());

		accountingInfoGridLayout.addComponent(flatGrid, 0, 0, 0, 0);

		accountingInfoGridLayout.addComponent(flatCheckBoxHorizontalLayout(), 0, 1, 0, 1);

		accountingInfoGridLayout.addComponent(flatButtonHorizontalLayout(), 0, 2, 0, 2);
	}

	public Grid flatGridFlatInfoView(List<Flat> flatList) throws SQLException {

		DecimalFormat decimalFormat = new DecimalFormat("0.00");

		Grid flatGrid = new Grid();

		flatGrid.setSizeFull();

//			flatGrid.setSelectionMode(SelectionMode.MULTI);

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
				Flat flatBuyer = flatService.getFlatByFlatIdFromFlatBuyerDB(idFlatTableFromSelectedRowFlatGrid);

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

		flatGrid.getColumn("flatBuyerFirstname").setHidden(true);
		flatGrid.getColumn("flatBuyerLastname").setHidden(true);
		flatGrid.getColumn("flatBuyerSurname").setHidden(true);
		flatGrid.getColumn("flatContractDate").setHidden(true);
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

		flatGrid.getColumn("flatContractDate").setHeaderCaption("Flat Contract Date")
				.setRenderer(new DateRenderer("%1$td.%1$tm.%1$tY"));

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

			flatGrid.addRow(flatFromList.getIdFlatTable(), flatFromList.getBuildingCorps(), flatFromList.getFlatRooms(),
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

			);
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

		return flatGrid;

	}

	public Layout flatCheckBoxHorizontalLayout() {

		HorizontalLayout checkBoxHorizontalLayout = new HorizontalLayout();
		checkBoxHorizontalLayout.setMargin(true);
		checkBoxHorizontalLayout.setSpacing(true);
		checkBoxHorizontalLayout.setSizeFull();
//			addComponent(checkBoxHorizontalLayout);

		CheckBox buyerInfoCheckBox = new CheckBox("Buyer Info");
		checkBoxHorizontalLayout.addComponent(buyerInfoCheckBox);
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
		checkBoxHorizontalLayout.addComponent(contractInfoCheckBox);

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
		checkBoxHorizontalLayout.addComponent(flatCostM2CheckBox);
		flatCostM2CheckBox.addValueChangeListener(event -> {

			if (flatCostM2CheckBox.isEmpty()) {

				flatGrid.getColumn("m2, flatCost").setHidden(true);
			} else {
				flatGrid.getColumn("m2, flatCost").setHidden(false);
			}

		});

		CheckBox flatCostCheckBox = new CheckBox("Flat Cost");
		checkBoxHorizontalLayout.addComponent(flatCostCheckBox);
		flatCostCheckBox.addValueChangeListener(event -> {

			if (flatCostCheckBox.isEmpty()) {

				flatGrid.getColumn("$, flatCost").setHidden(true);
			} else {
				flatGrid.getColumn("$, flatCost").setHidden(false);
			}

		});

		CheckBox expensesCheckBox = new CheckBox("Expenses Info");
		checkBoxHorizontalLayout.addComponent(expensesCheckBox);
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
		checkBoxHorizontalLayout.addComponent(availableSumCheckBox);
		availableSumCheckBox.setValue(false);
		availableSumCheckBox.addValueChangeListener(event -> {

			if (availableSumCheckBox.isEmpty()) {

				flatGrid.getColumn("$, availableSum").setHidden(true);

			} else {
				flatGrid.getColumn("$, availableSum").setHidden(false);

			}

		});

		CheckBox generalExpensesCheckBox = new CheckBox("General Expenses");
		checkBoxHorizontalLayout.addComponent(generalExpensesCheckBox);
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
		checkBoxHorizontalLayout.addComponent(bcExpensesCheckBox);
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
		checkBoxHorizontalLayout.addComponent(cmExpensesCheckBox);
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

		return checkBoxHorizontalLayout;
	}

	public Layout flatButtonHorizontalLayout() {

		HorizontalLayout buttonHorizontalLayout = new HorizontalLayout();
//			buttonHorizontalLayout.setMargin(true);
		buttonHorizontalLayout.setSpacing(true);
		buttonHorizontalLayout.setSizeFull();

//			buttonHorizontalLayout.addComponent(addFlatButton());

//			buttonHorizontalLayout.addComponent(deleteFlatButton());

//			buttonHorizontalLayout.addComponent(saleFlatButton());

		buttonHorizontalLayout.addComponent(reportButton());

		buttonHorizontalLayout.addComponent(expensesButton());

		buttonHorizontalLayout.addComponent(cancelButton());

		return buttonHorizontalLayout;
	}

	public Button expensesButton() {

//			expenses sum сума витрат
//			income sum сума доходу
//			available sum оступна сума

		Button expensesButton = new Button("Expenses");
		expensesButton.setSizeFull();

		expensesButton.setEnabled(false);

		expensesButton.addClickListener(e -> {

			Window expensesWindow = new Window("Expenses Window");

			GridLayout expensesWindowGridLayout = new GridLayout(2, 3);
//				expensesWindowGridLayout.setSizeFull();
			expensesWindowGridLayout.setSpacing(true);
			expensesWindowGridLayout.setMargin(true);
			expensesWindow.setContent(expensesWindowGridLayout);

			Panel saleFlatInfoPanel = new Panel("Sale Flat Info");
			expensesWindowGridLayout.addComponent(saleFlatInfoPanel, 1, 0, 1, 0);

			Grid saleFlatInfoGrid = new Grid();
			saleFlatInfoGrid.setSizeFull();
			saleFlatInfoGrid.setHeight("230px");

			Object selected = ((SingleSelectionModel) flatGrid.getSelectionModel()).getSelectedRow();
			String idFlatTableFromSelectedRow = flatGrid.getContainerDataSource().getItem(selected)
					.getItemProperty("idFlatTable").getValue().toString();

			saleFlatInfoGrid.addColumn("Name", String.class);
			saleFlatInfoGrid.addColumn("Value", String.class);

			Flat selectedFlat = new Flat();
			try {
				selectedFlat = flatService
						.getFlatByFlatIdFromFlatTableAndFlatBuyerDB(Integer.parseInt(idFlatTableFromSelectedRow));
			} catch (NumberFormatException | SQLException e2) {
				// TODO Auto-generated catch block
				e2.printStackTrace();
			}

			List<com.vaadin.ui.Grid.Column> flatGridColumnNameList = new ArrayList<>();
			flatGridColumnNameList = flatGrid.getColumns();

			saleFlatInfoGrid.addRow(flatGridColumnNameList.get(1).getHeaderCaption(),
					flatService.getFlatFromDbByColumnIndex(1, selectedFlat));
			saleFlatInfoGrid.addRow(flatGridColumnNameList.get(4).getHeaderCaption(),
					flatService.getFlatFromDbByColumnIndex(4, selectedFlat));
			saleFlatInfoGrid.addRow(flatGridColumnNameList.get(5).getHeaderCaption(),
					flatService.getFlatFromDbByColumnIndex(5, selectedFlat));
			saleFlatInfoGrid.addRow(flatGridColumnNameList.get(10).getHeaderCaption(),
					flatService.getFlatFromDbByColumnIndex(10, selectedFlat));
			saleFlatInfoGrid.addRow(flatGridColumnNameList.get(12).getHeaderCaption(),
					flatService.getFlatFromDbByColumnIndex(12, selectedFlat));

//				expensesWindowGridLayout.addComponent(saleFlatInfoGrid, 1, 0, 1, 0);
			saleFlatInfoPanel.setContent(saleFlatInfoGrid);

			Panel expensesInfoPanel = new Panel("Expenses Info");
			expensesWindowGridLayout.addComponent(expensesInfoPanel, 1, 1, 1, 1);

			Grid expensesGrid = new Grid();
			expensesGrid.setSizeFull();
			expensesGrid.setHeight("230px");

			expensesGrid.addColumn("expensesDate", Date.class);
			expensesGrid.addColumn("sum", Integer.class);
			expensesGrid.addColumn("category", String.class);
			expensesGrid.addColumn("value", String.class);

			expensesGrid.getColumn("expensesDate").setRenderer(new DateRenderer("%1$td.%1$tm.%1$tY"));

			List<Flat> flatList = new ArrayList<>();

			try {
				flatList = flatService
						.getExpensesByFlatIdFromExpensesTable(Integer.parseInt(idFlatTableFromSelectedRow));
				Iterator<Flat> itr = flatList.iterator();
				while (itr.hasNext()) {
					Flat flatFromList = itr.next();

					expensesGrid.addRow(flatFromList.getExpensesTableDate(), flatFromList.getExpensesTableSum(),
							flatFromList.getExpensesTableCategory(), flatFromList.getExpensesTableValue());
				}
			} catch (NumberFormatException | SQLException e4) {
				// TODO Auto-generated catch block
				e4.printStackTrace();
			}

			expensesInfoPanel.setContent(expensesGrid);

			Panel expensesPanel = new Panel("Add Expenses");
			expensesWindowGridLayout.addComponent(expensesPanel, 0, 0, 0, 1);

			FormLayout expensesWindowExpensesFormLayout = new FormLayout();
			expensesWindowExpensesFormLayout.setMargin(true);
			expensesPanel.setContent(expensesWindowExpensesFormLayout);

			TextField availableSumTextField = new TextField("Available Sum");
			availableSumTextField.setEnabled(false);

			int flatCost = Integer.parseInt(flatService.getFlatFromDbByColumnIndex(12, selectedFlat));
			int expensesGeneral;
			try {
				expensesGeneral = flatService.getExpensesFlatInfoFromExpensesTableByFlatId(
						Integer.parseInt(idFlatTableFromSelectedRow), "General");
				int expensesBC = flatService.getExpensesFlatInfoFromExpensesTableByFlatId(
						Integer.parseInt(idFlatTableFromSelectedRow), "B.C.");
				int expensesCM = flatService.getExpensesFlatInfoFromExpensesTableByFlatId(
						Integer.parseInt(idFlatTableFromSelectedRow), "C.M.");

				int availableSum = flatCost - expensesGeneral - expensesBC - expensesCM;
				availableSumTextField.setValue(Integer.toString(availableSum));
			} catch (NumberFormatException | SQLException e3) {
				// TODO Auto-generated catch block
				e3.printStackTrace();
			}

			expensesWindowExpensesFormLayout.addComponent(availableSumTextField);

			DateField expensesDateField = new DateField("Expenses Date");
			expensesDateField.setValue(new java.util.Date());

			expensesDateField.setDateFormat("dd.MM.yyyy");

			expensesWindowExpensesFormLayout.addComponent(expensesDateField);

			TextField expensesSumTextField = new TextField("Expenses Sum");

			expensesWindowExpensesFormLayout.addComponent(expensesSumTextField);

			ComboBox expensesCategoryComboBox = new ComboBox("Expenses Category ComboBox");
			expensesCategoryComboBox.addItem("General");
			expensesCategoryComboBox.addItem("B.C.");
			expensesCategoryComboBox.addItem("C.M.");

			expensesWindowExpensesFormLayout.addComponent(expensesCategoryComboBox);

			ComboBox expensesValueComboBox = new ComboBox("Expenses Value ComboBox");
			expensesValueComboBox.addItem("Податки, що залишилися на нашій Львівській фірмі ЛТБ (прогонка квартир)");
			expensesValueComboBox.addItem("Маклер і оформлення в нотаріуса");
			expensesValueComboBox.addItem("Переведено на УМБ ФІНАНС (Козловському)");
			expensesValueComboBox.addItem("Передано В.С.");
			expensesValueComboBox.addItem("Передано С.М.");

			expensesWindowExpensesFormLayout.addComponent(expensesValueComboBox);

			TextArea expensesValueTextArea = new TextArea("Expenses Value TextArea");

			expensesWindowExpensesFormLayout.addComponent(expensesValueTextArea);

			HorizontalLayout expensesWindowButtonHorizontalLayout = new HorizontalLayout();
//				expensesWindowButtonHorizontalLayout.setMargin(true);
			expensesWindowButtonHorizontalLayout.setSpacing(true);
			expensesWindowButtonHorizontalLayout.setSizeFull();
//				spendWindowHorizontalLayout.setSpacing(true);
//				spendWindowHorizontalLayout.setSizeFull();

			expensesWindowGridLayout.addComponent(expensesWindowButtonHorizontalLayout, 0, 2, 0, 2);

			Button expensesAddButton = new Button("Add Expenses");
			expensesAddButton.setSizeFull();
			expensesAddButton.addClickListener(e1 -> {
				expensesGrid.addRow(expensesDateField.getValue(), Integer.parseInt(expensesSumTextField.getValue()),
						expensesCategoryComboBox.getValue(),
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
			expensesWindowButtonHorizontalLayout.addComponent(expensesCancelButton);

			expensesWindow.center();

			UI.getCurrent().addWindow(expensesWindow);

		});

		flatGrid.addSelectionListener(SelectionEvent -> {
			expensesButton.setEnabled(true);
		});

		return expensesButton;
	}

	public Button cancelButton() {

		Button cancelButton = new Button("Cancel");
		cancelButton.setSizeFull();
		cancelButton.addClickListener(e1 -> {
			UI.getCurrent().getNavigator().navigateTo("main");
		});

		return cancelButton;
	}

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
