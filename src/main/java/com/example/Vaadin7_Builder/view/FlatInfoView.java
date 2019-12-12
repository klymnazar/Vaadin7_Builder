package com.example.Vaadin7_Builder.view;

import java.sql.Date;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import com.example.Vaadin7_Builder.model.Flat;
import com.example.Vaadin7_Builder.service.FlatService;
import com.vaadin.data.validator.DoubleValidator;
import com.vaadin.data.validator.IntegerValidator;
import com.vaadin.event.SelectionEvent;
import com.vaadin.navigator.View;
import com.vaadin.navigator.ViewChangeListener.ViewChangeEvent;
import com.vaadin.ui.Button;
import com.vaadin.ui.ComboBox;
import com.vaadin.ui.DateField;
import com.vaadin.ui.FormLayout;
import com.vaadin.ui.Grid;
import com.vaadin.ui.Grid.Column;
import com.vaadin.ui.Grid.SingleSelectionModel;
import com.vaadin.ui.HorizontalLayout;
import com.vaadin.ui.Layout;
import com.vaadin.ui.Panel;
import com.vaadin.ui.TextField;
import com.vaadin.ui.UI;
import com.vaadin.ui.VerticalLayout;
import com.vaadin.ui.Window;

public class FlatInfoView extends VerticalLayout implements View {

	FlatService flatService = new FlatService();

	Grid flatGrid = new Grid();

	public FlatInfoView() throws SQLException {

//		FlatCheckBoxHorizontalLayout();

		flatGridFlatInfoView();

		flatButtonHorizontalLayout();

	}

//	public Layout FlatCheckBoxHorizontalLayout() {
//	
//	HorizontalLayout buttonHorizontalLayout = new HorizontalLayout();
//	buttonHorizontalLayout.setMargin(true);
//	buttonHorizontalLayout.setSpacing(true);
//	buttonHorizontalLayout.setSizeFull();
//	addComponent(buttonHorizontalLayout);
//	
//	return FlatCheckBoxHorizontalLayout();
//	
//	}

	public Grid flatGridFlatInfoView() throws SQLException {

		List<Flat> flatList = new ArrayList<>();

		flatGrid.setSizeFull();

//		flatGrid.setSelectionMode(SelectionMode.MULTI);

		flatGrid.addColumn("idFlatTable", Integer.class);
		flatGrid.addColumn("buildingCorps", String.class);
		flatGrid.addColumn("flatRooms", Integer.class);
		flatGrid.addColumn("flatFloor", Integer.class);
		flatGrid.addColumn("flatNumber", Integer.class);
		flatGrid.addColumn("flatArea", Double.class);
		flatGrid.addColumn("flatSet", String.class);
//		flatGrid.addColumn("flatBuyerFirstname", String.class);
//		flatGrid.addColumn("flatBuyerLastname", String.class);
//		flatGrid.addColumn("flatBuyerSurname", String.class);
//		flatGrid.addColumn("flatContractNumber", String.class);
//		
//		flatGrid.addColumn("flatContractDate", Date.class);
//		flatGrid.addColumn("flatCost", Integer.class);
//		flatGrid.addColumn("flatSellerName", String.class);
//		flatGrid.addColumn("flatNotes", String.class);

		addComponent(flatGrid);

		flatList = flatService.getFlatsFromDB();

		Iterator<Flat> itr = flatList.iterator();
		while (itr.hasNext()) {
			Flat flatFromList = itr.next();
			flatGrid.addRow(flatFromList.getIdFlatTable(), flatFromList.getBuildingCorps(), flatFromList.getFlatRooms(),
					flatFromList.getFlatFloor(), flatFromList.getFlatNumber(), flatFromList.getFlatArea(),
					flatFromList.getFlatSet()
//							, flatFromList.getFlatBuyerFirstname(), flatFromList.getFlatBuyerLastname(),
//							flatFromList.getFlatBuyerSurname(), flatFromList.getFlatContractNumber(), 
//					, flatFromList.getFlatContractDate()
					
//							, flatFromList.getFlatCost(), flatFromList.getFlatSellerName(), flatFromList.getFlatNotes()
			);
		}

		return flatGrid;

	}

	public Layout flatButtonHorizontalLayout() {

		HorizontalLayout buttonHorizontalLayout = new HorizontalLayout();
		buttonHorizontalLayout.setMargin(true);
		buttonHorizontalLayout.setSpacing(true);
		buttonHorizontalLayout.setSizeFull();
		addComponent(buttonHorizontalLayout);

		buttonHorizontalLayout.addComponent(addFlatButton());

		buttonHorizontalLayout.addComponent(deleteFlatButton());

		buttonHorizontalLayout.addComponent(reservFlatButton());

//		buttonHorizontalLayout.addComponent(saleFlatButton());

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

				try {
					flatService.createFlat(flat);

					flatGrid.addRow(flat.getIdFlatTable(), flat.getBuildingCorps(), flat.getFlatRooms(),
							flat.getFlatFloor(), flat.getFlatNumber(), flat.getFlatArea());

				} catch (SQLException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}

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

		return deleteFlatButton;
	}

	public Button reservFlatButton() {

		Button reservFlatButton = new Button("Reserv Flat");
		reservFlatButton.setSizeFull();

		return reservFlatButton;
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
//					flattt = flatService.getFlatByFlatId(Integer.parseInt(idFlatTableFromSelectedRow));
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
//					saleFlat = flatService.getFlatByFlatId(Integer.parseInt(idFlatTableFromSelectedRow));
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
//				saleFlat.setFlatContractDate(saleFlatDateField.getValue());
//
//				try {
//					flatService.updateBuyerInfoFlatByFlatId(Integer.parseInt(idFlatTableFromSelectedRow), saleFlat);
//					saleFlatWindow.close();
//				} catch (SQLException e1) {
//					// TODO Auto-generated catch block
//					e1.printStackTrace();
//				}
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

	public Button cancelButton() {

		Button cancelButton = new Button("Cancel");
		cancelButton.setSizeFull();

		return cancelButton;
	}

	@Override
	public void enter(ViewChangeEvent event) {
		// TODO Auto-generated method stub

	}

}
