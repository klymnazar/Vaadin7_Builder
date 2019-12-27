package com.example.Vaadin7_Builder.view;

import java.sql.SQLException;
//import java.text.DateFormat;
//import java.text.SimpleDateFormat;
//import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Date;
import java.util.Iterator;
import java.util.List;

import com.example.Vaadin7_Builder.model.Flat;
import com.example.Vaadin7_Builder.service.FlatService;
//import com.example.Vaadin7_Builder.service.SpendService;
import com.vaadin.data.validator.DoubleValidator;
import com.vaadin.data.validator.IntegerValidator;
//import com.vaadin.event.SelectionEvent;
import com.vaadin.navigator.View;
import com.vaadin.navigator.ViewChangeListener.ViewChangeEvent;
import com.vaadin.ui.Button;
import com.vaadin.ui.CheckBox;
import com.vaadin.ui.ComboBox;
//import com.vaadin.ui.DateField;
import com.vaadin.ui.FormLayout;
import com.vaadin.ui.Grid;
import com.vaadin.ui.GridLayout;
import com.vaadin.ui.Grid.Column;
import com.vaadin.ui.Grid.SingleSelectionModel;
import com.vaadin.ui.renderers.DateRenderer;
import com.vaadin.ui.HorizontalLayout;
import com.vaadin.ui.Layout;
//import com.vaadin.ui.Panel;
import com.vaadin.ui.TextArea;
import com.vaadin.ui.TextField;
import com.vaadin.ui.UI;
import com.vaadin.ui.VerticalLayout;
import com.vaadin.ui.Window;

public class BuildingInfoView extends VerticalLayout implements View {

	private FlatService flatService = new FlatService();

	private Grid flatGrid = new Grid();
	
	private GridLayout buildingInfoGridLayout = new GridLayout(1, 3);
	
	private String flatSetReserved = "Reserved";
	private String flatSetSolded = "Solded";
	private String flatSetFree = "";
	
	

	public BuildingInfoView() throws SQLException {
		
		buildingInfoGridLayout.setMargin(true);
		buildingInfoGridLayout.setSizeFull();
		addComponent(buildingInfoGridLayout);
		
		flatGrid = flatGridFlatInfoView(flatService.getFlatsFromFlatTableAndFlatBuyerDB());

		buildingInfoGridLayout.addComponent(flatGrid, 0, 0, 0, 0);

		buildingInfoGridLayout.addComponent(flatCheckBoxHorizontalLayout(), 0, 1, 0, 1);

		buildingInfoGridLayout.addComponent(flatButtonHorizontalLayout(), 0, 2, 0, 2);
	}

	public Grid flatGridFlatInfoView(List<Flat> flatList) throws SQLException {

		Grid flatGrid = new Grid();
		
		flatGrid.setSizeFull();

//		flatGrid.setSelectionMode(SelectionMode.MULTI);

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
		flatGrid.addColumn("flatCost", Integer.class);
		flatGrid.addColumn("flatCost_m$", Double.class);
		flatGrid.addColumn("flatSellerName", String.class);
		flatGrid.addColumn("flatNotes", String.class);

		flatGrid.getColumn("flatBuyerFirstname").setHidden(true);
		flatGrid.getColumn("flatBuyerLastname").setHidden(true);
		flatGrid.getColumn("flatBuyerSurname").setHidden(true);
		flatGrid.getColumn("flatContractDate").setHidden(true);
		flatGrid.getColumn("flatContractNumber").setHidden(true);
		flatGrid.getColumn("flatCost").setHidden(true);
		flatGrid.getColumn("flatCost_m$").setHidden(true);
		flatGrid.getColumn("flatSellerName").setHidden(true);
		flatGrid.getColumn("flatNotes").setHidden(true);

		flatGrid.getColumn("flatContractDate").setHeaderCaption("Flat Contract Date")
				.setRenderer(new DateRenderer("%1$td.%1$tm.%1$tY"));

		Iterator<Flat> itr = flatList.iterator();
		while (itr.hasNext()) {
			Flat flatFromList = itr.next();

			double flatCost_m$ = flatFromList.getFlatCost() / flatFromList.getFlatArea();

			flatGrid.addRow(flatFromList.getIdFlatTable(), flatFromList.getBuildingCorps(), flatFromList.getFlatRooms(),
					flatFromList.getFlatFloor(), flatFromList.getFlatNumber(), flatFromList.getFlatArea(),
					flatFromList.getFlatSet()
//					, flatFromList.getFlatCost_m()
					, flatFromList.getFlatBuyerFirstname(), flatFromList.getFlatBuyerLastname(),
					flatFromList.getFlatBuyerSurname(), flatFromList.getFlatContractDate(),
					flatFromList.getFlatContractNumber(), flatFromList.getFlatCost(), flatCost_m$,
					flatFromList.getFlatSellerName(), flatFromList.getFlatNotes());
		}

		return flatGrid;

	}

	public Layout flatCheckBoxHorizontalLayout() {

		HorizontalLayout checkBoxHorizontalLayout = new HorizontalLayout();
		checkBoxHorizontalLayout.setMargin(true);
		checkBoxHorizontalLayout.setSpacing(true);
		checkBoxHorizontalLayout.setSizeFull();
//		addComponent(checkBoxHorizontalLayout);

		CheckBox buyerInfoCheckBox = new CheckBox("Buyer Info");
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

		checkBoxHorizontalLayout.addComponent(buyerInfoCheckBox);

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

		CheckBox flatCostCheckBox = new CheckBox("Flat Cost");
		checkBoxHorizontalLayout.addComponent(flatCostCheckBox);
		flatCostCheckBox.addValueChangeListener(event -> {

			if (flatCostCheckBox.isEmpty()) {

				flatGrid.getColumn("flatCost").setHidden(true);
			} else {
				flatGrid.getColumn("flatCost").setHidden(false);
			}

		});

		CheckBox flatCostM2CheckBox = new CheckBox("Flat Cost m2");
		checkBoxHorizontalLayout.addComponent(flatCostM2CheckBox);
		flatCostM2CheckBox.addValueChangeListener(event -> {

			if (flatCostM2CheckBox.isEmpty()) {

				flatGrid.getColumn("flatCost_m$").setHidden(true);
			} else {
				flatGrid.getColumn("flatCost_m$").setHidden(false);
			}

		});



		return checkBoxHorizontalLayout;
	}

	public Layout flatButtonHorizontalLayout() {

		HorizontalLayout buttonHorizontalLayout = new HorizontalLayout();
//		buttonHorizontalLayout.setMargin(true);
		buttonHorizontalLayout.setSpacing(true);
		buttonHorizontalLayout.setSizeFull();
//		addComponent(buttonHorizontalLayout);

		buttonHorizontalLayout.addComponent(addFlatButton());

		buttonHorizontalLayout.addComponent(deleteFlatButton());

//		buttonHorizontalLayout.addComponent(saleFlatButton());

//		buttonHorizontalLayout.addComponent(spendButton());

		buttonHorizontalLayout.addComponent(cancelButton());

		return buttonHorizontalLayout;
	}

	public Button addFlatButton() {

		Button addFlatButton = new Button("Add Flat");

		addFlatButton.addClickListener(e -> {

			Window addFlatWindow = new Window("Add Flat");
			VerticalLayout addFlatWindowVerticalLayout = new VerticalLayout();
			addFlatWindowVerticalLayout.setMargin(true);
			addFlatWindowVerticalLayout.setSpacing(true);

			addFlatWindow.setContent(addFlatWindowVerticalLayout);

			FormLayout addFlatFormLayout = new FormLayout();
			addFlatFormLayout.setMargin(true);
			addFlatFormLayout.setSpacing(true);
			addFlatWindowVerticalLayout.addComponent(addFlatFormLayout);

			ComboBox buildingCorpsComboBox = new ComboBox("Building Corps");
			buildingCorpsComboBox.addItems("1/А", "2/Б", "3/В", "4/Г", "5/Д", "6/Е", "7/Є", "8/Ж");
			addFlatFormLayout.addComponent(buildingCorpsComboBox);

			ComboBox flatRoomsComboBox = new ComboBox("Flat Rooms");
			flatRoomsComboBox.addItems(1, 2, 3, 4, 5);
			addFlatFormLayout.addComponent(flatRoomsComboBox);

			ComboBox flatFloorComboBox = new ComboBox("Flat Floor");
			flatFloorComboBox.addItems(1, 2, 3, 4, 5, 6, 7, 8, 9, 10);
			addFlatFormLayout.addComponent(flatFloorComboBox);

			TextField flatNumberTextField = new TextField("Flat Number");
			addFlatFormLayout.addComponent(flatNumberTextField);

			TextField flatAreaTextField = new TextField("Flat Area");
			addFlatFormLayout.addComponent(flatAreaTextField);

			HorizontalLayout windowButtonHorizontalLayout = new HorizontalLayout();
			windowButtonHorizontalLayout.setSizeFull();
			windowButtonHorizontalLayout.setSpacing(true);
			addFlatWindowVerticalLayout.addComponent(windowButtonHorizontalLayout);

			Button addWindowButton = new Button("Add");
			addWindowButton.addClickListener(c -> {

				
				Flat flat = new Flat();

				flat.setBuildingCorps(buildingCorpsComboBox.getValue().toString());
				flat.setFlatArea(Double.parseDouble(flatAreaTextField.getValue()));
				flat.setFlatFloor(Integer.parseInt(flatFloorComboBox.getValue().toString()));
				flat.setFlatNumber(Integer.parseInt(flatNumberTextField.getValue()));
				flat.setFlatRooms(Integer.parseInt(flatRoomsComboBox.getValue().toString()));

				flatAreaTextField.addValidator(new DoubleValidator("Flat area must be double"));
				flatNumberTextField.addValidator(new IntegerValidator("Flat number must be integer"));

				if (flatAreaTextField.isValid() && flatNumberTextField.isValid()) {
					addFlatWindow.close();
				}
				
//				buildingInfoGridLayout.removeComponent(0, 0);
				

				try {
					flatService.createFlat(flat);
					
					buildingInfoGridLayout.removeComponent(0, 0);
//					flatGrid = flatGridFlatInfoView(flatService.getFlatsFromFlatTableAndFlatBuyerDB());
					buildingInfoGridLayout.addComponent(flatGrid, 0, 0, 0, 0);
//					flatGrid.addRow(flat.getIdFlatTable(), flat.getBuildingCorps(), flat.getFlatRooms(),
//							flat.getFlatFloor(), flat.getFlatNumber(), flat.getFlatArea());

				} catch (SQLException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
				
//				try {
//					flatGrid = flatGridFlatInfoView(flatService.getFlatsFromFlatTableAndFlatBuyerDB());
//					buildingInfoGridLayout.addComponent(flatGrid, 0, 0, 0, 0);
//				} catch (SQLException e1) {
//					// TODO Auto-generated catch block
//					e1.printStackTrace();
//				}

			});
			addWindowButton.setSizeFull();
			windowButtonHorizontalLayout.addComponent(addWindowButton);

			Button cancelWindowButton = new Button("Cancel");
			cancelWindowButton.addClickListener(c -> {

				addFlatWindow.close();

			});
			cancelWindowButton.setSizeFull();
			windowButtonHorizontalLayout.addComponent(cancelWindowButton);

			addFlatWindow.center();

			UI.getCurrent().addWindow(addFlatWindow);

		});

		addFlatButton.setSizeFull();

		return addFlatButton;
	}

	public Button deleteFlatButton() {

		
		
		
		Button deleteFlatButton = new Button("Delete Flat");
		deleteFlatButton.setSizeFull();
		deleteFlatButton.setEnabled(false);

		
		flatGrid.addSelectionListener(SelectionEvent -> {

			Object selected = ((SingleSelectionModel) flatGrid.getSelectionModel()).getSelectedRow();
			String idFlatTableFromSelectedRow = flatGrid.getContainerDataSource().getItem(selected)
					.getItemProperty("idFlatTable").getValue().toString();
			try {
				String flatSetFromFlatGrid = flatService.getFlatByFlatIdFromFlatTable(Integer.parseInt(idFlatTableFromSelectedRow)).getFlatSet();

				if (flatSetFromFlatGrid.equals(flatSetFree)) {
					deleteFlatButton.setEnabled(true);
				} else {
					deleteFlatButton.setEnabled(false);
				}
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}

		});
		
		deleteFlatButton.addClickListener(e -> {
		
			Object selected = ((SingleSelectionModel) flatGrid.getSelectionModel()).getSelectedRow();
			String idFlatTableFromSelectedRow = flatGrid.getContainerDataSource().getItem(selected)
					.getItemProperty("idFlatTable").getValue().toString();
			
			try {
				
					flatService.deleteFlatByFlatId(Integer.parseInt(idFlatTableFromSelectedRow));
					buildingInfoGridLayout.removeComponent(flatGrid);
					flatGrid = flatGridFlatInfoView(flatService.getFlatsFromFlatTableAndFlatBuyerDB());
					buildingInfoGridLayout.addComponent(flatGrid, 0, 0, 0, 0);

			} catch (NumberFormatException | SQLException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}

		});

		

		return deleteFlatButton;
	}
	
	
	public Button cancelButton() {

		Button cancelButton = new Button("Cancel");
		cancelButton.setSizeFull();
		cancelButton.addClickListener(e1 -> {
			UI.getCurrent().getNavigator().navigateTo("main");
		});

		return cancelButton;
	}
	
	
	
	
	

//	public Button saleFlatButton() {
//
//		Button saleFlatButton = new Button("Sale Flat");
//		saleFlatButton.setEnabled(false);
//		saleFlatButton.addClickListener(e -> {
//
//			Window saleFlatWindow = new Window("Sale Flat");
//			saleFlatWindow.setWidth("850px");
//
//			VerticalLayout saleFlatWindowVerticalLayout = new VerticalLayout();
////			saleFlatWindowVerticalLayout.setMargin(true);
////			saleFlatWindowVerticalLayout.setSpacing(true);
//
////			saleFlatWindowVerticalLayout.setSizeFull();
////			saleFlatWindowVerticalLayout.setSizeUndefined();
//			saleFlatWindow.setContent(saleFlatWindowVerticalLayout);
//
//			HorizontalLayout saleFlatWindowHorizontalLayout = new HorizontalLayout();
//			saleFlatWindowHorizontalLayout.setMargin(true);
//			saleFlatWindowHorizontalLayout.setSpacing(true);
//			saleFlatWindowHorizontalLayout.setSizeFull();
//			saleFlatWindowVerticalLayout.addComponent(saleFlatWindowHorizontalLayout);
//
//			FormLayout saleFlatWindowFormLayout = new FormLayout();
//			saleFlatWindowFormLayout.setMargin(true);
////			saleFlatFormLayout.setSpacing(true);
//			saleFlatWindowFormLayout.setSizeFull();
////			saleFlatWindowFormLayout.setSizeUndefined();
////			saleFlatWindowHorizontalLayout.addComponent(saleFlatWindowFormLayout);
//
//			DateField saleFlatDateField = new DateField("Contract Date");
//			saleFlatDateField.setValue(new java.util.Date());
////			saleFlatDateField.setDateFormat("dd-MM-yyyy");
//			saleFlatDateField.setDateFormat("dd-MM-yyyy");
//			saleFlatWindowFormLayout.addComponent(saleFlatDateField);
//			
//			
//			
//			
//
//			TextField firstNameTextField = new TextField("First Name");
//			saleFlatWindowFormLayout.addComponent(firstNameTextField);
//
//			TextField lastNameTextField = new TextField("Last Name");
//			saleFlatWindowFormLayout.addComponent(lastNameTextField);
//
//			TextField surNameTextField = new TextField("Surname");
//			saleFlatWindowFormLayout.addComponent(surNameTextField);
//
//			TextField contractNumberTextField = new TextField("Contract Number");
//			saleFlatWindowFormLayout.addComponent(contractNumberTextField);
//
//			TextField costTextField = new TextField("Cost, $");
//			saleFlatWindowFormLayout.addComponent(costTextField);
//
//			Panel saleFlatBuyerInfoPanel = new Panel("Buyer Info");
//			saleFlatBuyerInfoPanel.setSizeFull();
//			saleFlatWindowHorizontalLayout.addComponent(saleFlatBuyerInfoPanel);
//			saleFlatBuyerInfoPanel.setContent(saleFlatWindowFormLayout);
//
//
//
//			Grid saleFlatInfoGrid = new Grid();
//
////			saleFlatInfoGrid.setSizeUndefined();
//			saleFlatInfoGrid.setSizeFull();
//
//			Object selected = ((SingleSelectionModel) flatGrid.getSelectionModel()).getSelectedRow();
//			String idFlatTableFromSelectedRow = flatGrid.getContainerDataSource().getItem(selected)
//					.getItemProperty("idFlatTable").getValue().toString();
//
//			saleFlatInfoGrid.addColumn("Name", String.class);
//			saleFlatInfoGrid.addColumn("Value", String.class);
//
//			List<Column> flatGridColumnNameList = new ArrayList<>();
//			int i = 0;
//
//			flatGridColumnNameList = flatGrid.getColumns();
//
//			Iterator<Column> itr = flatGridColumnNameList.iterator();
//			while (itr.hasNext()) {
//				Column columnNameFromList = itr.next();
//
//				Flat flattt = new Flat();
//
//				try {
//
//					flattt = flatService.getFlatByFlatIdFromFlatTable(Integer.parseInt(idFlatTableFromSelectedRow));
//				} catch (SQLException e2) {
//					// TODO Auto-generated catch block
//					e2.printStackTrace();
//				}
//
//				try {
//					saleFlatInfoGrid.addRow(flatGridColumnNameList.get(i).getHeaderCaption(),
//							flatService.getFlatFromDbByColumnIndex(i, flattt));
//				} catch (IllegalStateException e1) {
//					// TODO Auto-generated catch block
//					e1.printStackTrace();
//				}
//
//				i++;
//			}
//
////			saleFlatWindowHorizontalLayout.addComponent(saleFlatInfoGrid);
//
//			Panel saleFlatInfoGridPanel = new Panel("Flat Info");
//
//			saleFlatInfoGridPanel.setSizeFull();
//			saleFlatWindowHorizontalLayout.addComponent(saleFlatInfoGridPanel);
//			saleFlatInfoGridPanel.setContent(saleFlatInfoGrid);
//
//			HorizontalLayout saleFlatWindowButtonHorizontalLayout = new HorizontalLayout();
//			saleFlatWindowButtonHorizontalLayout.setSizeFull();
//			saleFlatWindowButtonHorizontalLayout.setSpacing(true);
//			saleFlatWindowVerticalLayout.addComponent(saleFlatWindowButtonHorizontalLayout);
//
//			Button saleFlatWindowButton = new Button("Sale Flat Window");
//			saleFlatWindowButton.setSizeFull();
//			saleFlatWindowButton.addClickListener(es -> {
//
//
//
//				Flat saleFlat = new Flat();
//
//				try {
//					saleFlat = flatService.getFlatByFlatIdFromFlatTable(Integer.parseInt(idFlatTableFromSelectedRow));
//				} catch (NumberFormatException | SQLException e2) {
//					// TODO Auto-generated catch block
//					e2.printStackTrace();
//				}
//
//				saleFlat.setFlatBuyerFirstname(firstNameTextField.getValue());
//				saleFlat.setFlatBuyerLastname(lastNameTextField.getValue());
//				saleFlat.setFlatBuyerSurname(surNameTextField.getValue());
//				saleFlat.setFlatContractNumber(contractNumberTextField.getValue());
//				saleFlat.setFlatCost(Integer.parseInt(costTextField.getValue()));
//				saleFlat.setFlatCost_m(Double.parseDouble(costTextField.getValue())/saleFlat.getFlatArea());
////				saleFlat.setFlatCost_m(Double.parseDouble(costTextField.getValue()));
//				saleFlat.setFlatContractDate(saleFlatDateField.getValue());
//
////				try {
////					
////					
////					
//////!!!!!!!!!!!!					
//////					flatService.updateBuyerInfoFlatByFlatId(Integer.parseInt(idFlatTableFromSelectedRow), saleFlat);
////					
////					
////					
////					
////					saleFlatWindow.close();
////				} catch (SQLException e1) {
////					// TODO Auto-generated catch block
////					e1.printStackTrace();
////				}
//
//			});
//
//			saleFlatWindowButtonHorizontalLayout.addComponent(saleFlatWindowButton);
//
//			Button cancelWindowButton = new Button("Cancel");
//			cancelWindowButton.setSizeFull();
//			saleFlatWindowButtonHorizontalLayout.addComponent(cancelWindowButton);
//
//			saleFlatWindow.center();
//
//			UI.getCurrent().addWindow(saleFlatWindow);
//
//		});
//
//		saleFlatButton.setSizeFull();
//
//		flatGrid.addSelectionListener(SelectionEvent -> {
//			saleFlatButton.setEnabled(true);
//		});
//
//		return saleFlatButton;
//	}

//	public Button spendButton() {
//
////		Flat selectedFlat = new Flat();
//
//		Button spendButton = new Button("Spend");
//		spendButton.setSizeFull();
//
//		spendButton.setEnabled(false);
//
//		spendButton.addClickListener(e -> {
//
//			Window spendWindow = new Window("Spend");
//			VerticalLayout spendWindowVerticalLayout = new VerticalLayout();
////			saleFlatWindowVerticalLayout.setMargin(true);
////			saleFlatWindowVerticalLayout.setSpacing(true);
//
////			saleFlatWindowVerticalLayout.setSizeFull();
//			spendWindowVerticalLayout.setSizeUndefined();
//
//			spendWindow.setContent(spendWindowVerticalLayout);
//
//			HorizontalLayout spendWindowHorizontalLayout = new HorizontalLayout();
//			spendWindowHorizontalLayout.setMargin(true);
//			spendWindowHorizontalLayout.setSpacing(true);
//			spendWindowHorizontalLayout.setSizeFull();
//
//			spendWindowVerticalLayout.addComponent(spendWindowHorizontalLayout);
//
//			Grid spendFlatInfoGrid = new Grid("Spend Flat Info");
//
////			saleFlatInfoGrid.setSizeUndefined();
////			saleFlatInfoGrid.setSizeFull();
//
//			Object selected = ((SingleSelectionModel) flatGrid.getSelectionModel()).getSelectedRow();
//			String idFlatTableFromSelectedRow = flatGrid.getContainerDataSource().getItem(selected)
//					.getItemProperty("idFlatTable").getValue().toString();
//
//			spendFlatInfoGrid.addColumn("Name", String.class);
//			spendFlatInfoGrid.addColumn("Value", String.class);
//
//			Flat selectedFlat = new Flat();
//			try {
//				selectedFlat = flatService.getFlatByFlatIdFromFlatTable(Integer.parseInt(idFlatTableFromSelectedRow));
//			} catch (NumberFormatException | SQLException e2) {
//				// TODO Auto-generated catch block
//				e2.printStackTrace();
//			}
//
//			List<Column> flatGridColumnNameList = new ArrayList<>();
//			flatGridColumnNameList = flatGrid.getColumns();
//
//			spendFlatInfoGrid.addRow(flatGridColumnNameList.get(1).getHeaderCaption(),
//					flatService.getFlatFromDbByColumnIndex(1, selectedFlat));
//			spendFlatInfoGrid.addRow(flatGridColumnNameList.get(4).getHeaderCaption(),
//					flatService.getFlatFromDbByColumnIndex(4, selectedFlat));
//			spendFlatInfoGrid.addRow(flatGridColumnNameList.get(7).getHeaderCaption(),
//					flatService.getFlatFromDbByColumnIndex(7, selectedFlat));
//			spendFlatInfoGrid.addRow(flatGridColumnNameList.get(8).getHeaderCaption(),
//					flatService.getFlatFromDbByColumnIndex(8, selectedFlat));
//			spendFlatInfoGrid.addRow(flatGridColumnNameList.get(9).getHeaderCaption(),
//					flatService.getFlatFromDbByColumnIndex(9, selectedFlat));
//			spendFlatInfoGrid.addRow(flatGridColumnNameList.get(10).getHeaderCaption(),
//					flatService.getFlatFromDbByColumnIndex(10, selectedFlat));
//			spendFlatInfoGrid.addRow(flatGridColumnNameList.get(11).getHeaderCaption(),
//					flatService.getFlatFromDbByColumnIndex(11, selectedFlat));
////			spendFlatInfoGrid.addRow(flatGridColumnNameList.get(13).getHeaderCaption(),
////					flatService.getFlatFromDbByColumnIndex(13, selectedFlat));
//
//			spendWindowHorizontalLayout.addComponent(spendFlatInfoGrid);
//
//			Grid spendGrid = new Grid("Spend Info");
//
////			saleFlatInfoGrid.setSizeUndefined();
////			saleFlatInfoGrid.setSizeFull();
//
////			Object selected = ((SingleSelectionModel) flatGrid.getSelectionModel()).getSelectedRow();
////			String idFlatTableFromSelectedRow = flatGrid.getContainerDataSource().getItem(selected)
////					.getItemProperty("idFlatTable").getValue().toString();
//
//			spendGrid.addColumn("idflatTable", Integer.class);
//			spendGrid.addColumn("Sum", Integer.class);
//			spendGrid.addColumn("Currency", String.class);
//			spendGrid.addColumn("Category", String.class);
//			spendGrid.addColumn("Value", String.class);
//
//			spendWindowHorizontalLayout.addComponent(spendGrid);
//
//			TextField spendSumTextField = new TextField("Spend Sum");
//
//			spendWindowVerticalLayout.addComponent(spendSumTextField);
//
//			ComboBox spendCurrencyComboBox = new ComboBox("Spend Currency ComboBox");
//			spendCurrencyComboBox.addItem("UAN");
//			spendCurrencyComboBox.addItem("$");
//
//			spendWindowVerticalLayout.addComponent(spendCurrencyComboBox);
//
//			ComboBox spendCategoryComboBox = new ComboBox("Spend Category ComboBox");
//			spendCategoryComboBox.addItem("General");
//			spendCategoryComboBox.addItem("B.C.");
//			spendCategoryComboBox.addItem("C.M.");
//
//			spendWindowVerticalLayout.addComponent(spendCategoryComboBox);
//
//			ComboBox spendValueComboBox = new ComboBox("Spend Value ComboBox");
//			spendValueComboBox.addItem("Податки, що залишилися на нашій Львівській фірмі ЛТБ (прогонка квартир)");
//			spendValueComboBox.addItem("Маклер і оформлення в нотаріуса");
//			spendValueComboBox.addItem("Переведено на УМБ ФІНАНС (Козловському)");
//			spendValueComboBox.addItem("Передано В.С.");
//			spendValueComboBox.addItem("Передано С.М.");
//
//			spendWindowVerticalLayout.addComponent(spendValueComboBox);
//
//			TextArea spendValueTextArea = new TextArea("Spend Value TextArea");
//
//			spendWindowVerticalLayout.addComponent(spendValueTextArea);
//
//			Button addSpendButton = new Button("Add Spend");
//			addSpendButton.addClickListener(e1 -> {
//				spendGrid.addRow(Integer.parseInt(idFlatTableFromSelectedRow),
//						Integer.parseInt(spendSumTextField.getValue()), spendCurrencyComboBox.getValue(),
//						spendCategoryComboBox.getValue(),
//						spendValueComboBox.getValue() + " " + spendValueTextArea.getValue());
//
//				Flat spendFlat = new Flat();
//				spendFlat.setIdFlatTable(Integer.parseInt(idFlatTableFromSelectedRow));
//				spendFlat.setSpendTableSum(Integer.parseInt(spendSumTextField.getValue()));
//				spendFlat.setSpendTableCurrency(spendCurrencyComboBox.getValue().toString());
//				spendFlat.setSpendTableCategory(spendCategoryComboBox.getValue().toString());
//				spendFlat.setSpendTableValue(spendValueComboBox.getValue() + " " + spendValueTextArea.getValue());
//
//				try {
//					flatService.createSpendFlat(spendFlat);
//				} catch (SQLException e2) {
//					// TODO Auto-generated catch block
//					e2.printStackTrace();
//				}
//
////				try {
////					
//////!!!!!!!!!!!!					
//////					flatService.updateSpendInfoFlatByFlatId(Integer.parseInt(idFlatTableFromSelectedRow), spendFlat);
////					
//////					spendFlat.setFlatGeneralExpenses(flatService.getSpendFlatInfoFromSpendTableByFlatId(Integer.parseInt(idFlatTableFromSelectedRow), "General"));
////					
//////					spendFlat.setFlatGeneralExpenses(flatService.getSpendFlatInfoFromSpendTableByFlatId(55, "General"));
////					
////					
////				} catch (NumberFormatException | SQLException e2) {
////					// TODO Auto-generated catch block
////					e2.printStackTrace();
////				}
//
////				try {
////					spendFlat.setFlatGeneralExpenses(flatService.getSpendFlatInfoFromSpendTableByFlatId(Integer.parseInt(idFlatTableFromSelectedRow), "General"));
////				} catch (NumberFormatException | SQLException e3) {
////					// TODO Auto-generated catch block
////					e3.printStackTrace();
////				}
//			});
//
//			spendWindowVerticalLayout.addComponent(addSpendButton);
//
////			Flat selectedFlat = new Flat();
////			try {
////				selectedFlat = flatService.getFlatByFlatId(Integer.parseInt(idFlatTableFromSelectedRow));
////			} catch (NumberFormatException | SQLException e2) {
////				// TODO Auto-generated catch block
////				e2.printStackTrace();
////			}
////
////			List<Column> flatGridColumnNameList = new ArrayList<>();
////			flatGridColumnNameList = flatGrid.getColumns();
////
////			spendFlatInfoGrid.addRow(flatGridColumnNameList.get(1).getHeaderCaption(),
////					flatService.getFlatFromDbByColumnIndex(1, selectedFlat));
////			spendFlatInfoGrid.addRow(flatGridColumnNameList.get(4).getHeaderCaption(),
////					flatService.getFlatFromDbByColumnIndex(4, selectedFlat));
////			spendFlatInfoGrid.addRow(flatGridColumnNameList.get(7).getHeaderCaption(),
////					flatService.getFlatFromDbByColumnIndex(7, selectedFlat));
////			spendFlatInfoGrid.addRow(flatGridColumnNameList.get(8).getHeaderCaption(),
////					flatService.getFlatFromDbByColumnIndex(8, selectedFlat));
////			spendFlatInfoGrid.addRow(flatGridColumnNameList.get(9).getHeaderCaption(),
////					flatService.getFlatFromDbByColumnIndex(9, selectedFlat));
////			spendFlatInfoGrid.addRow(flatGridColumnNameList.get(10).getHeaderCaption(),
////					flatService.getFlatFromDbByColumnIndex(10, selectedFlat));
////			spendFlatInfoGrid.addRow(flatGridColumnNameList.get(11).getHeaderCaption(),
////					flatService.getFlatFromDbByColumnIndex(11, selectedFlat));
////			
//
////			List<Column> flatGridColumnNameList = new ArrayList<>();
////			int i = 0;
////
////			flatGridColumnNameList = flatGrid.getColumns();
////
////			Iterator<Column> itr = flatGridColumnNameList.iterator();
////			while (itr.hasNext()) {
////				Column columnNameFromList = itr.next();
////
////				Flat flattt = new Flat();
////
////				try {
////
////					flattt = flatService.getFlatByFlatId(Integer.parseInt(idFlatTableFromSelectedRow));
////				} catch (SQLException e2) {
////					// TODO Auto-generated catch block
////					e2.printStackTrace();
////				}
////
////				try {
////					saleFlatInfoGrid.addRow(flatGridColumnNameList.get(i).getHeaderCaption(),
////							flatService.getFlatFromDbByColumnIndex(i, flattt));
////				} catch (IllegalStateException e1) {
////					// TODO Auto-generated catch block
////					e1.printStackTrace();
////				}
////
////				i++;
////			}
//
////			FormLayout saleFlatWindowFormLayout = new FormLayout();
//////			saleFlatFormLayout.setMargin(true);
//////			saleFlatFormLayout.setSpacing(true);
//////			saleFlatWindowFormLayout.setSizeFull();
////			saleFlatWindowFormLayout.setSizeUndefined();
////			saleFlatWindowHorizontalLayout.addComponent(saleFlatWindowFormLayout);
////			
////			DateField saleFlatDateField = new DateField("Contract Date");
////			saleFlatDateField.setValue(new java.util.Date());
////			saleFlatDateField.setDateFormat("dd-MM-yyyy");
////			saleFlatWindowFormLayout.addComponent(saleFlatDateField);
////			
////
////			TextField firstNameTextField = new TextField("First Name");
////			saleFlatWindowFormLayout.addComponent(firstNameTextField);
////			
////			TextField lastNameTextField = new TextField("Last Name");
////			saleFlatWindowFormLayout.addComponent(lastNameTextField);
////			
////			TextField surNameTextField = new TextField("Surname");
////			saleFlatWindowFormLayout.addComponent(surNameTextField);
////			
////			TextField contractNumberTextField = new TextField("Contract Number");
////			saleFlatWindowFormLayout.addComponent(contractNumberTextField);
////			
////			TextField costTextField = new TextField("Cost, $");
////			saleFlatWindowFormLayout.addComponent(costTextField);
////			
//
////			spendWindowHorizontalLayout.addComponent(spendFlatInfoGrid);
//
////			HorizontalLayout saleFlatWindowButtonHorizontalLayout = new HorizontalLayout();
//////			saleFlatWindowButtonHorizontalLayout.setSizeFull();
////			saleFlatWindowButtonHorizontalLayout.setSpacing(true);
////			saleFlatWindowVerticalLayout.addComponent(saleFlatWindowButtonHorizontalLayout);
////
////			Button saleFlatWindowButton = new Button("Sale Flat");
////			saleFlatWindowButton.setSizeFull();
////			saleFlatWindowButtonHorizontalLayout.addComponent(saleFlatWindowButton);
////			
////			Button cancelWindowButton = new Button("Cancel");
////			cancelWindowButton.setSizeFull();
////			saleFlatWindowButtonHorizontalLayout.addComponent(cancelWindowButton);
////			
//
//			spendWindow.center();
//
//			UI.getCurrent().addWindow(spendWindow);
//
//		});
//
//		spendButton.setSizeFull();
//
//		flatGrid.addSelectionListener(SelectionEvent -> {
//			spendButton.setEnabled(true);
//		});
//
//		return spendButton;
//	}

	

	@Override
	public void enter(ViewChangeEvent event) {
		// TODO Auto-generated method stub

	}

}
