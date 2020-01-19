package com.example.Vaadin7_Builder.view.AccountingViewServises;

import java.sql.SQLException;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Iterator;
import java.util.List;

import com.example.Vaadin7_Builder.model.Flat;
import com.example.Vaadin7_Builder.service.FlatService;
import com.vaadin.ui.Alignment;
import com.vaadin.ui.Button;
import com.vaadin.ui.ComboBox;
import com.vaadin.ui.DateField;
import com.vaadin.ui.FormLayout;
import com.vaadin.ui.Grid;
import com.vaadin.ui.GridLayout;
import com.vaadin.ui.HorizontalLayout;
import com.vaadin.ui.Notification;
import com.vaadin.ui.Panel;
import com.vaadin.ui.TextArea;
import com.vaadin.ui.TextField;
import com.vaadin.ui.UI;
import com.vaadin.ui.VerticalLayout;
import com.vaadin.ui.Window;
import com.vaadin.ui.Grid.FooterRow;
import com.vaadin.ui.Grid.SingleSelectionModel;
import com.vaadin.ui.renderers.DateRenderer;

public class ExpensesInfoButton extends VerticalLayout{
	
	FlatService flatService = new FlatService();

	private DecimalFormat decimalFormat = new DecimalFormat("0.00");
	
	
	private String generalExpenses = "General";
	private String bcExpenses = "B.C.";
	private String cmExpenses = "C.M.";
	
	
	
	public Grid expensesWindowInfoGrid(List<Flat> flatList) {

	Grid expensesWindowInfoGrid = new Grid();
	expensesWindowInfoGrid.setSizeFull();

	expensesWindowInfoGrid.addColumn("expensesDate", String.class);
	expensesWindowInfoGrid.addColumn("sum", Integer.class);
	expensesWindowInfoGrid.addColumn("category", String.class);
	expensesWindowInfoGrid.addColumn("value", String.class);

//	expensesWindowInfoGrid.getColumn("expensesDate").setRenderer(new DateRenderer("%1$td.%1$tm.%1$tY"));

//	List<Flat> flatList = new ArrayList<>();

	int expensesSum = 0;

	try {
		
//		flatList = flatService.getExpensesFromExpensesTable();
		
		Iterator<Flat> itr = flatList.iterator();
		while (itr.hasNext()) {
			Flat flatFromList = itr.next();

			expensesWindowInfoGrid.addRow(
					flatService.dateFormatForGrid(flatFromList.getExpensesTableDate())
					, flatFromList.getExpensesTableSum()
					, flatFromList.getExpensesTableCategory()
					, flatFromList.getExpensesTableValue()
					);

			expensesSum = expensesSum + flatFromList.getExpensesTableSum();

		}
	} catch (NumberFormatException e4) {
		// TODO Auto-generated catch block
		e4.printStackTrace();
	}

	FooterRow expensesWindowGridFooterRow = expensesWindowInfoGrid.prependFooterRow();
	expensesWindowGridFooterRow.getCell("expensesDate").setText("Total: ");
	expensesWindowGridFooterRow.getCell("sum").setText(decimalFormat.format(expensesSum).replace(",", "."));
	
	
	
//	FooterRow expensesWindowGridFooterRow = expensesWindowInfoGrid.prependFooterRow();
//	expensesWindowGridFooterRow.getCell("expensesDate").setText("Total: ");
//	expensesWindowGridFooterRow.getCell("sum").setText(decimalFormat.format(expensesSum).replace(",", "."));
	
	
	return expensesWindowInfoGrid;
}
	
	
	
	public Button expensesInfoButton(Grid flatGrid) {

//		expenses sum сума витрат
//		income sum сума доходу
//		available sum оступна сума

		
		
		
		
		
		
		
		
		
		
	Button expensesInfoButton = new Button("Expenses Info");
	expensesInfoButton.setSizeFull();

//	expensesInfoButton.setEnabled(false);

	expensesInfoButton.addClickListener(e -> {

		Window expensesWindow = new Window("Expenses Window");
		expensesWindow.setHeight("600px");
		expensesWindow.setWidth("900px");

		
		
		
//		Object selected = ((SingleSelectionModel) flatGrid.getSelectionModel()).getSelectedRow();
//		String idFlatTableFromSelectedRow = flatGrid.getContainerDataSource().getItem(selected)
//				.getItemProperty("idFlatTable").getValue().toString();
//		int idFlatTableIntFromSelectedRow = Integer.parseInt(idFlatTableFromSelectedRow);

		
		
		
		
		
		
		VerticalLayout expensesWindowVerticalLayout = new VerticalLayout();
		expensesWindow.setContent(expensesWindowVerticalLayout);
		
		
		
		
		
		
		
		
		
		
		
		
		
		
		
		
		
		
		
		
		
		
		
		
		
		
		
		
//		VerticalLayout expensesInfoWindowVerticalLayout = new VerticalLayout();
//		expensesInfoWindowVerticalLayout.setMargin(true);
//		expensesInfoWindowVerticalLayout.setSizeFull();
//		expensesWindow.setContent(expensesInfoWindowVerticalLayout);

		
		
		HorizontalLayout expensesWindowFlatHorizontalLayout = new HorizontalLayout();
		expensesWindowFlatHorizontalLayout.setSpacing(true);
		expensesWindowFlatHorizontalLayout.setHeight("65px");
		expensesWindowFlatHorizontalLayout.setWidth("100%");
		expensesWindowVerticalLayout.addComponent(expensesWindowFlatHorizontalLayout);

		
		
		Panel expensesWindowInfoPanel = new Panel();
		expensesWindowInfoPanel.setSizeFull();
		
		expensesWindowVerticalLayout.addComponent(expensesWindowInfoPanel);
		
		
		
		
		
		
			ComboBox expensesWindowCorpsComboBox = new ComboBox("Building Corps List");
			expensesWindowCorpsComboBox.setNullSelectionAllowed(false);

			List<Flat> corpsList = new ArrayList<>();

			try {

				corpsList = flatService.getCorpsFromSettingsTable();

				Iterator<Flat> itr = corpsList.iterator();
				while (itr.hasNext()) {
					Flat flatFromList = itr.next();

					expensesWindowCorpsComboBox.addItem(flatFromList.getBuildingCorps());

				}
			} catch (NumberFormatException | SQLException e4) {
				// TODO Auto-generated catch block
				e4.printStackTrace();
			}

			expensesWindowCorpsComboBox.addValueChangeListener(select -> {
				Notification.show("Selected " + expensesWindowCorpsComboBox.getValue());

				List<Flat> flatListByCorps = new ArrayList<>();

				List<Flat> flatListExpensesByFlatId = new ArrayList<>();

				List<Flat> expensesList = new ArrayList<>();

				try {

					flatListByCorps = flatService
							.getFlatsByCorpsFromFlatTable(expensesWindowCorpsComboBox.getValue().toString());

					Iterator<Flat> itr = flatListByCorps.iterator();
					while (itr.hasNext()) {
						Flat flatFromList = itr.next();

						flatListExpensesByFlatId = flatService
								.getExpensesByFlatIdFromExpensesTable(flatFromList.getIdFlatTable());

						Iterator<Flat> itr1 = flatListExpensesByFlatId.iterator();
						while (itr1.hasNext()) {
							Flat flatFromList1 = itr1.next();

							expensesList.add(flatFromList1);

						}

						expensesWindowCorpsComboBox.addItem(flatFromList.getBuildingCorps());

					}
				} catch (NumberFormatException | SQLException e4) {
					// TODO Auto-generated catch block
					e4.printStackTrace();
				}

				expensesWindowInfoPanel.setContent(expensesWindowInfoGrid(expensesList));

			});

			expensesWindowFlatHorizontalLayout.addComponent(expensesWindowCorpsComboBox);
		
			
			
			
		ComboBox expensesWindowFlatComboBox = new ComboBox("Flat List");
		
	
		
//		expensesWindowInfoPanel.setContent(expensesWindowInfoGrid(flatService.getExpensesByFlatIdFromExpensesTable(flatFromList.getIdFlatTable())));
		
		
		
		
		
		expensesWindowFlatHorizontalLayout.addComponent(expensesWindowFlatComboBox);
		
		
		
		ComboBox expensesWindowCategoryComboBox = new ComboBox("Category Expenses List");
		
		expensesWindowFlatHorizontalLayout.addComponent(expensesWindowCategoryComboBox);
		
		
		Button expensesWindowSelectAllButton = new Button("Select All Expenses");
		expensesWindowSelectAllButton.addClickListener(lick -> {
			
		
			
			try {
				expensesWindowInfoPanel.setContent(expensesWindowInfoGrid(flatService.getExpensesFromExpensesTable()));
			} catch (SQLException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
			
		});
		
		
		expensesWindowFlatHorizontalLayout.addComponent(expensesWindowSelectAllButton);
		
		
		
		
		
		
		
		
		
		
		
		
		
		
		
		
//		DateField bankPaymentDateFromDateField = new DateField("From Payment Date");
//		bankPaymentDateFromDateField.setValue(new java.util.Date());
//		bankPaymentDateFromDateField.setDateFormat("dd.MM.yyyy");
//		bankInfoWindowDateHorizontalLayout.addComponent(bankPaymentDateFromDateField);
//		bankInfoWindowDateHorizontalLayout.setComponentAlignment(bankPaymentDateFromDateField, Alignment.BOTTOM_CENTER);
//
//
//		
//		DateField bankPaymentDateToDateField = new DateField("To Payment Date");
//		bankPaymentDateToDateField.setValue(new java.util.Date());
//		bankPaymentDateToDateField.setDateFormat("dd.MM.yyyy");
//		bankInfoWindowDateHorizontalLayout.addComponent(bankPaymentDateToDateField);
//		bankInfoWindowDateHorizontalLayout.setComponentAlignment(bankPaymentDateToDateField, Alignment.BOTTOM_CENTER);
//
//		
//		
//		HorizontalLayout bankInfoWindowContractHorizontalLayout = new HorizontalLayout();
//		bankInfoWindowContractHorizontalLayout.setSpacing(true);
//		bankInfoWindowContractHorizontalLayout.setHeight("65px");
//		bankInfoWindowContractHorizontalLayout.setWidth("100%");
//		bankInfoWindowVerticalLayout.addComponent(bankInfoWindowContractHorizontalLayout);
//
//		ComboBox bankInfoWindowContractComboBox = new ComboBox();
//		bankInfoWindowContractComboBox = bankInfoWindowContractComboBox();
//		bankInfoWindowContractHorizontalLayout.addComponent(bankInfoWindowContractComboBox);
//		bankInfoWindowContractHorizontalLayout.setComponentAlignment(bankInfoWindowContractComboBox, Alignment.BOTTOM_CENTER);
		
		
		
		
		
		
		
//		HorizontalLayout bankInfoWindowPreGridHorizontalLayout = new HorizontalLayout();
//		bankInfoWindowPreGridHorizontalLayout.setHeight("5px");
//		bankInfoWindowPreGridHorizontalLayout.setWidth("100%");
//		bankInfoWindowVerticalLayout.addComponent(bankInfoWindowPreGridHorizontalLayout);
//
//		HorizontalLayout bankInfoWindowGridHorizontalLayout = new HorizontalLayout();
//		bankInfoWindowGridHorizontalLayout.setSizeFull();
//		bankInfoWindowVerticalLayout.addComponent(bankInfoWindowGridHorizontalLayout);
//		bankInfoWindowVerticalLayout.setExpandRatio(bankInfoWindowGridHorizontalLayout, 1.0f);
//
//		Button bankInfoWindowSelectDateButton = new Button("Select");
////		bankInfoWindowSelectButton.setHeight("40px");
//		bankInfoWindowSelectDateButton.setWidth("185px");
////		bankInfoWindowSelectDateButton.setWidth("100%");
////		bankInfoWindowSelectButton.setSizeFull();
//		bankInfoWindowDateHorizontalLayout.addComponent(bankInfoWindowSelectDateButton);
//		bankInfoWindowDateHorizontalLayout.setComponentAlignment(bankInfoWindowSelectDateButton, Alignment.BOTTOM_CENTER);
//
//		bankInfoWindowSelectDateButton.addClickListener(click -> {
//
//			bankInfoWindowGridHorizontalLayout.removeAllComponents();
//
//			String fromDate = flatService.dateFormatForDB(bankPaymentDateFromDateField.getValue());
//			String toDate = flatService.dateFormatForDB(bankPaymentDateToDateField.getValue());
//
//			try {
//				bankInfoWindowGridHorizontalLayout.addComponent(bankInfoWindowPaymentInfoGrid(
//						flatService.getSelectedItemsByDateFromBankTable(fromDate, toDate)));
//
//			} catch (SQLException e) {
//				// TODO Auto-generated catch block
//				e.printStackTrace();
//			}
//
//		});
//
//		
//		Button bankInfoWindowSelectContractButton = new Button("Select");
//		bankInfoWindowSelectContractButton.setEnabled(false);
////		bankInfoWindowSelectButton.setHeight("40px");
//		bankInfoWindowSelectContractButton.setWidth("185px");
////		bankInfoWindowSelectContractButton.setWidth("100%");
////		bankInfoWindowSelectButton.setSizeFull();
//		bankInfoWindowContractHorizontalLayout.addComponent(bankInfoWindowSelectContractButton);
//		bankInfoWindowContractHorizontalLayout.setComponentAlignment(bankInfoWindowSelectContractButton, Alignment.BOTTOM_CENTER);
//		
//		bankInfoWindowContractComboBox().addValueChangeListener(event -> {
//			
//			String contractTextFromComboBox = bankInfoWindowContractComboBox().getValue().toString();
//			
//			if (contractTextFromComboBox.equals("")) {
//				bankInfoWindowSelectContractButton.setEnabled(false);
//			}
//			else {
//				bankInfoWindowSelectContractButton.setEnabled(true);
//			}
//
//		});
//		
//		
//		bankInfoWindowSelectContractButton.addClickListener(click -> {
//
//			bankInfoWindowGridHorizontalLayout.removeAllComponents();
//
//			String contractTextFromComboBox = bankInfoWindowContractComboBox().getValue().toString();
//			
//			String[] contractNumberDate = contractTextFromComboBox.split(" from ");
//			
//			String contractDate = flatService.dateFormatForDB(contractNumberDate[1]);
//			
//			try {
//
//				bankInfoWindowGridHorizontalLayout.addComponent(bankInfoWindowPaymentInfoGrid(
//						flatService.getFlatsFromBankTableByIdFlatTable(flatService.getFlatFromFlatBuyerTableByContract(contractNumberDate[0], contractDate).getIdFlatTable())));
//				
//			} catch (SQLException e) {
//				// TODO Auto-generated catch block
//				e.printStackTrace();
//			}
//
//		});
//		
//		
//		
//		
//		Button bankInfoWindowSelectAllButton = new Button("Select All");
////		bankInfoWindowSelectAllButton.setSizeFull();
//		bankInfoWindowSelectAllButton.setWidth("185px");
////		bankInfoWindowSelectAllButton.setWidth("100%");
//		bankInfoWindowContractHorizontalLayout.addComponent(bankInfoWindowSelectAllButton);
//		bankInfoWindowContractHorizontalLayout.setComponentAlignment(bankInfoWindowSelectAllButton, Alignment.BOTTOM_CENTER);
//
//		bankInfoWindowSelectAllButton.addClickListener(click -> {
//
//			bankInfoWindowGridHorizontalLayout.removeAllComponents();
//
//			List<Flat> paymentList = new ArrayList<>();
//			try {
//				paymentList = flatService.getPaymentsFromBankTable();
//				bankInfoWindowGridHorizontalLayout.addComponent(bankInfoWindowPaymentInfoGrid(paymentList));
//			} catch (SQLException e) {
//				// TODO Auto-generated catch block
//				e.printStackTrace();
//			}
//
//		});
//
//		bankInfoWindow.center();
//
//		UI.getCurrent().addWindow(bankInfoWindow);
//
//	});
		
		
		
		
		
		
		
		
		
		
		
		
		
		
		
		
		
		
		
		
//		Panel expensesWindowInfoPanel = new Panel();
//		expensesWindowInfoPanel.setSizeFull();
//
//		expensesWindowVerticalLayout.addComponent(expensesWindowInfoPanel);
//
//		
//		
//		
//		
//
//		
//		Grid expensesWindowInfoGrid = new Grid();
//		expensesWindowInfoGrid.setSizeFull();
//
//		expensesWindowInfoGrid.addColumn("expensesDate", String.class);
////		expensesWindowInfoGrid.addColumn("expensesDateD", Date.class);
//		expensesWindowInfoGrid.addColumn("sum", Integer.class);
//		expensesWindowInfoGrid.addColumn("category", String.class);
//		expensesWindowInfoGrid.addColumn("value", String.class);
//
//		
//		
//		
//		
////		expensesWindowInfoGrid.getColumn("expensesDate").setRenderer(new DateRenderer("%1$td.%1$tm.%1$tY"));
//
//		List<Flat> flatList = new ArrayList<>();
//
////		int expensesSum = 0;
//
//		try {
//			
////			flatList = flatService.getExpensesByFlatIdFromExpensesTable(idFlatTableIntFromSelectedRow);
//			flatList = flatService.getExpensesFromExpensesTable();
//			
//			
////			flatList = flatService
////					.getExpensesByFlatIdFromExpensesTable(Integer.parseInt(idFlatTableFromSelectedRow));
//			Iterator<Flat> itr = flatList.iterator();
//			while (itr.hasNext()) {
//				Flat flatFromList = itr.next();
//
////				expensesWindowInfoGrid.addRow(flatFromList.getExpensesTableDate(), flatFromList.getExpensesTableSum(),
////						flatFromList.getExpensesTableCategory(), flatFromList.getExpensesTableValue());
//				
//				expensesWindowInfoGrid.addRow(
//						flatService.dateFormatForGrid(flatFromList.getExpensesTableDate())
////						flatFromList.getExpensesTableDate()
//						//, 
////						flatFromList.getExpensesTableDate()
////						,
//						
////						"123"
////						,
//						, flatFromList.getExpensesTableSum()
//						, flatFromList.getExpensesTableCategory()
//						, flatFromList.getExpensesTableValue()
//						);
//
////				expensesSum = expensesSum + flatFromList.getExpensesTableSum();
//
//			}
//		} catch (NumberFormatException | SQLException e4) {
//			// TODO Auto-generated catch block
//			e4.printStackTrace();
//		}
//
////		FooterRow expensesWindowGridFooterRow = expensesWindowInfoGrid.prependFooterRow();
////		expensesWindowGridFooterRow.getCell("expensesDate").setText("Total: ");
////		expensesWindowGridFooterRow.getCell("sum").setText(decimalFormat.format(expensesSum).replace(",", "."));
//
//		expensesWindowInfoPanel.setContent(expensesWindowInfoGrid);

		
//		expensesWindowInfoPanel.setContent(bankInfoWindowPaymentInfoGrid);
		
		
//		Flat selectedSoldedFlatBuyerInfo = new Flat();
//
//		try {
//			selectedSoldedFlatBuyerInfo = flatService
//					.getFlatByFlatIdFromFlatBuyerTable(idFlatTableIntFromSelectedRow);
//			int flatCost = selectedSoldedFlatBuyerInfo.getFlatCost();
//			int expensesGeneral = flatService
//					.getExpensesFlatInfoFromExpensesTableByFlatId(idFlatTableIntFromSelectedRow, "General");
//			int expensesBC = flatService.getExpensesFlatInfoFromExpensesTableByFlatId(idFlatTableIntFromSelectedRow,
//					"B.C.");
//			int expensesCM = flatService.getExpensesFlatInfoFromExpensesTableByFlatId(idFlatTableIntFromSelectedRow,
//					"C.M.");
//
////			int availableSum = flatCost - expensesGeneral - expensesBC - expensesCM;
//
//			expensesWindowInfoPanel.setCaption("Expenses Info (B.C. = " + expensesBC + "$: C.M. = " + expensesCM
//					+ "$: General = " + expensesGeneral + "$)");
////			addExpensesWindowPanel.setCaption("Add Expenses (Available Sum = " + availableSum + "$)");
//
//		} catch (SQLException e3) {
//			// TODO Auto-generated catch block
//			e3.printStackTrace();
//		}
		
		
		
		
		
		
		
		
		
		
		
		
		
//		GridLayout expensesWindowGridLayout = new GridLayout(2, 11);
//		expensesWindowGridLayout.setSizeFull();
//		expensesWindowGridLayout.setSpacing(true);
//		expensesWindowGridLayout.setMargin(true);
////		expensesWindow.setContent(expensesWindowGridLayout);
//
////		Object selected = ((SingleSelectionModel) flatGrid.getSelectionModel()).getSelectedRow();
////		String idFlatTableFromSelectedRow = flatGrid.getContainerDataSource().getItem(selected)
////				.getItemProperty("idFlatTable").getValue().toString();
////		int idFlatTableIntFromSelectedRow = Integer.parseInt(idFlatTableFromSelectedRow);
//
//		Panel addExpensesWindowPanel = new Panel("Add Expenses");
//		addExpensesWindowPanel.setSizeFull();
//		expensesWindowGridLayout.addComponent(addExpensesWindowPanel, 0, 0, 0, 9);
//
//		FormLayout expensesWindowExpensesFormLayout = new FormLayout();
//		expensesWindowExpensesFormLayout.setMargin(true);
//		expensesWindowExpensesFormLayout.setSizeFull();
//		addExpensesWindowPanel.setContent(expensesWindowExpensesFormLayout);
//
//		DateField expensesDateField = new DateField("Expenses Date");
//		expensesDateField.setValue(new java.util.Date());
//
//		expensesDateField.setDateFormat("dd.MM.yyyy");
//
//		expensesWindowExpensesFormLayout.addComponent(expensesDateField);
//
//		TextField expensesSumTextField = new TextField("Expenses Sum");
//
//		expensesWindowExpensesFormLayout.addComponent(expensesSumTextField);
//
//		ComboBox expensesCategoryComboBox = new ComboBox("Expenses Category");
//		expensesCategoryComboBox.addItem("General");
//		expensesCategoryComboBox.addItem("B.C.");
//		expensesCategoryComboBox.addItem("C.M.");
//
//		expensesWindowExpensesFormLayout.addComponent(expensesCategoryComboBox);
//
//		ComboBox expensesValueComboBox = new ComboBox("Expenses Value");
//		expensesValueComboBox.addItem("Податки, що залишилися на нашій Львівській фірмі ЛТБ (прогонка квартир)");
//		expensesValueComboBox.addItem("Маклер і оформлення в нотаріуса");
//		expensesValueComboBox.addItem("Переведено на УМБ ФІНАНС (Козловському)");
//		expensesValueComboBox.addItem("Передано В.С.");
//		expensesValueComboBox.addItem("Передано С.М.");
//
//		expensesWindowExpensesFormLayout.addComponent(expensesValueComboBox);
//
//		TextArea expensesValueTextArea = new TextArea("Expenses Value");
//		expensesValueTextArea.setHeight("60px");
//
//		expensesWindowExpensesFormLayout.addComponent(expensesValueTextArea);
//
//		Panel expensesWindowInfoPanel1 = new Panel();
//		expensesWindowInfoPanel1.setSizeFull();
//
//		expensesWindowGridLayout.addComponent(expensesWindowInfoPanel1, 1, 0, 1, 9);
//
//		Grid expensesWindowGrid = new Grid();
//		expensesWindowGrid.setSizeFull();
//
//		expensesWindowGrid.addColumn("expensesDate", Date.class);
//		expensesWindowGrid.addColumn("sum", Integer.class);
//		expensesWindowGrid.addColumn("category", String.class);
//		expensesWindowGrid.addColumn("value", String.class);
//
//		expensesWindowGrid.getColumn("expensesDate").setRenderer(new DateRenderer("%1$td.%1$tm.%1$tY"));
//
//		List<Flat> flatList1 = new ArrayList<>();
//
//		int expensesSum1 = 0;
//
//		try {
//			flatList1 = flatService
//					.getExpensesByFlatIdFromExpensesTable(Integer.parseInt(idFlatTableFromSelectedRow));
//			Iterator<Flat> itr = flatList1.iterator();
//			while (itr.hasNext()) {
//				Flat flatFromList = itr.next();
//
//				expensesWindowGrid.addRow(flatFromList.getExpensesTableDate(), flatFromList.getExpensesTableSum(),
//						flatFromList.getExpensesTableCategory(), flatFromList.getExpensesTableValue());
//
//				expensesSum1 = expensesSum1 + flatFromList.getExpensesTableSum();
//
//			}
//		} catch (NumberFormatException | SQLException e4) {
//			// TODO Auto-generated catch block
//			e4.printStackTrace();
//		}
//
//		FooterRow expensesWindowGridFooterRow1 = expensesWindowGrid.prependFooterRow();
//		expensesWindowGridFooterRow1.getCell("expensesDate").setText("Total: ");
//		expensesWindowGridFooterRow1.getCell("sum").setText(decimalFormat.format(expensesSum1).replace(",", "."));
//
//		expensesWindowInfoPanel1.setContent(expensesWindowGrid);
//
//		Flat selectedSoldedFlatBuyerInfo1 = new Flat();
//
//		try {
//			selectedSoldedFlatBuyerInfo1 = flatService
//					.getFlatByFlatIdFromFlatBuyerTable(idFlatTableIntFromSelectedRow);
//			int flatCost = selectedSoldedFlatBuyerInfo1.getFlatCost();
//			int expensesGeneral = flatService
//					.getExpensesFlatInfoFromExpensesTableByFlatId(idFlatTableIntFromSelectedRow, "General");
//			int expensesBC = flatService.getExpensesFlatInfoFromExpensesTableByFlatId(idFlatTableIntFromSelectedRow,
//					"B.C.");
//			int expensesCM = flatService.getExpensesFlatInfoFromExpensesTableByFlatId(idFlatTableIntFromSelectedRow,
//					"C.M.");
//
//			int availableSum = flatCost - expensesGeneral - expensesBC - expensesCM;
//
//			expensesWindowInfoPanel.setCaption("Expenses Info (B.C. = " + expensesBC + "$: C.M. = " + expensesCM
//					+ "$: General = " + expensesGeneral + "$)");
//			addExpensesWindowPanel.setCaption("Add Expenses (Available Sum = " + availableSum + "$)");
//
//		} catch (SQLException e3) {
//			// TODO Auto-generated catch block
//			e3.printStackTrace();
//		}
//
		HorizontalLayout expensesWindowButtonHorizontalLayout = new HorizontalLayout();

		expensesWindowButtonHorizontalLayout.setSpacing(true);
		expensesWindowButtonHorizontalLayout.setHeight("40px");
		expensesWindowButtonHorizontalLayout.setWidth("100%");
		
		
		expensesWindowVerticalLayout.addComponent(expensesWindowButtonHorizontalLayout);
//
//		expensesWindowGridLayout.addComponent(expensesWindowButtonHorizontalLayout, 0, 10, 1, 10);
//		expensesWindowGridLayout.setComponentAlignment(expensesWindowButtonHorizontalLayout,
//				Alignment.BOTTOM_CENTER);
//
//		Button expensesAddButton = new Button("Add Expenses");
//		expensesAddButton.setSizeFull();
//		expensesAddButton.addClickListener(e1 -> {
//			expensesWindowGrid.addRow(expensesDateField.getValue(),
//					Integer.parseInt(expensesSumTextField.getValue()), expensesCategoryComboBox.getValue(),
//					expensesValueComboBox.getValue() + " " + expensesValueTextArea.getValue());
//
//			Flat expensesFlat = new Flat();
//			expensesFlat.setIdFlatTable(Integer.parseInt(idFlatTableFromSelectedRow));
//			expensesFlat.setExpensesTableDate(expensesDateField.getValue());
//			expensesFlat.setExpensesTableSum(Integer.parseInt(expensesSumTextField.getValue()));
//			expensesFlat.setExpensesTableCategory(expensesCategoryComboBox.getValue().toString());
//			expensesFlat.setExpensesTableValue(
//					expensesValueComboBox.getValue() + " " + expensesValueTextArea.getValue());
//
//			try {
//				flatService.createExpensesFlat(expensesFlat);
//			} catch (SQLException e2) {
//				// TODO Auto-generated catch block
//				e2.printStackTrace();
//			}
//
//		});
//
//		expensesWindowButtonHorizontalLayout.addComponent(expensesAddButton);

		Button expensesCancelButton = new Button("Cancel");
		expensesCancelButton.setSizeFull();
		expensesCancelButton.addClickListener(click -> {
			expensesWindow.close();
		});
		expensesWindowButtonHorizontalLayout.addComponent(expensesCancelButton);

		expensesWindow.center();

		UI.getCurrent().addWindow(expensesWindow);

	});

//	flatGrid.addSelectionListener(SelectionEvent -> {
//		expensesInfoButton.setEnabled(true);
//	});

	return expensesInfoButton;
}
	
	
	
	
	
	
}
