package com.example.Vaadin7_Builder.view;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import com.example.Vaadin7_Builder.model.Flat;
import com.example.Vaadin7_Builder.service.FlatService;
import com.vaadin.data.validator.DoubleValidator;
import com.vaadin.data.validator.IntegerValidator;
import com.vaadin.navigator.View;
import com.vaadin.navigator.ViewChangeListener.ViewChangeEvent;
import com.vaadin.ui.Button;
import com.vaadin.ui.CheckBox;
import com.vaadin.ui.DateField;
import com.vaadin.ui.FormLayout;
import com.vaadin.ui.Grid;
import com.vaadin.ui.Grid.Column;
import com.vaadin.ui.Grid.SingleSelectionModel;
import com.vaadin.ui.GridLayout;
import com.vaadin.ui.GridLayout.OutOfBoundsException;
import com.vaadin.ui.GridLayout.OverlapsException;
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

	private String flatSetReserved = "Reserved";
	private String flatSetSolded = "Solded";
	private String flatSetFree = "";

	GridLayout flatInfoGridLayout = new GridLayout(1, 3);

	public FlatInfoView() throws SQLException {

		flatInfoGridLayout.setMargin(true);
		flatInfoGridLayout.setSizeFull();
		addComponent(flatInfoGridLayout);

		flatGrid = flatGridFlatInfoView(flatService.getFlatsFromDB());

		flatInfoGridLayout.addComponent(flatGrid, 0, 0, 0, 0);

		flatInfoGridLayout.addComponent(checkBoxHorizontalLayout(), 0, 1, 0, 1);

		flatInfoGridLayout.addComponent(flatButtonHorizontalLayout(), 0, 2, 0, 2);

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
//		flatGrid.addColumn("flatBuyerFirstname", String.class);
//		flatGrid.addColumn("flatBuyerLastname", String.class);
//		flatGrid.addColumn("flatBuyerSurname", String.class);
//		flatGrid.addColumn("flatContractNumber", String.class);
//		
//		flatGrid.addColumn("flatContractDate", Date.class);
//		flatGrid.addColumn("flatCost", Integer.class);
//		flatGrid.addColumn("flatSellerName", String.class);
//		flatGrid.addColumn("flatNotes", String.class);

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

	public Grid checkBoxEvent(CheckBox nameEnableCheckBox, CheckBox name1DisableCheckBox, CheckBox name2DisableCheckBox,
			String flatSet) {

		nameEnableCheckBox.addValueChangeListener(event -> {

			flatInfoGridLayout.removeComponent(0, 0);
			flatInfoGridLayout.removeComponent(0, 2);

			if (nameEnableCheckBox.isEmpty()) {

				name1DisableCheckBox.setEnabled(true);
				name2DisableCheckBox.setEnabled(true);

				try {

					flatGrid = flatGridFlatInfoView(flatService.getFlatsFromDB());

				} catch (SQLException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			} else {
				name1DisableCheckBox.setEnabled(false);
				name2DisableCheckBox.setEnabled(false);

				try {

					flatGrid = flatGridFlatInfoView(flatService.getFlatsByFlatSetFromDB(flatSet));

				} catch (SQLException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
			try {

				flatInfoGridLayout.addComponent(flatGrid, 0, 0, 0, 0);

			} catch (OverlapsException | OutOfBoundsException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}

			try {
				flatInfoGridLayout.addComponent(flatButtonHorizontalLayout(), 0, 2, 0, 2);
			} catch (OverlapsException | OutOfBoundsException | SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}

		});

		return flatGrid;
	}

	public Layout checkBoxHorizontalLayout() {

		HorizontalLayout checkBoxHorizontalLayout = new HorizontalLayout();
		checkBoxHorizontalLayout.setMargin(true);
		checkBoxHorizontalLayout.setSpacing(true);
		checkBoxHorizontalLayout.setSizeFull();

		CheckBox freeCheckBox = new CheckBox("Free flats");
		CheckBox soldCheckBox = new CheckBox("Solded flats");
		CheckBox reservCheckBox = new CheckBox("Reserved flats");

		checkBoxEvent(freeCheckBox, soldCheckBox, reservCheckBox, flatSetFree);
		checkBoxHorizontalLayout.addComponent(freeCheckBox);

		checkBoxEvent(soldCheckBox, freeCheckBox, reservCheckBox, flatSetSolded);
		checkBoxHorizontalLayout.addComponent(soldCheckBox);

		checkBoxEvent(reservCheckBox, soldCheckBox, freeCheckBox, flatSetReserved);
		checkBoxHorizontalLayout.addComponent(reservCheckBox);

		return checkBoxHorizontalLayout;

	}

	public Layout flatButtonHorizontalLayout() throws SQLException {

		HorizontalLayout buttonHorizontalLayout = new HorizontalLayout();
		buttonHorizontalLayout.setMargin(true);
		buttonHorizontalLayout.setSpacing(true);
		buttonHorizontalLayout.setSizeFull();

		buttonHorizontalLayout.addComponent(reservFlatButton());

		buttonHorizontalLayout.addComponent(cancelReservFlatButton());

		buttonHorizontalLayout.addComponent(saleFlatButton());

		buttonHorizontalLayout.addComponent(cancelButton());

		return buttonHorizontalLayout;
	}

	public int getIdFlatTableFromSelectedRow() throws SQLException {

		int idFlatTableFromSelectedRow;

		Grid grid = flatGrid;

		Object selected = ((SingleSelectionModel) grid.getSelectionModel()).getSelectedRow();
		String idFlatTableFromSelectedRowS = grid.getContainerDataSource().getItem(selected)
				.getItemProperty("idFlatTable").getValue().toString();

		idFlatTableFromSelectedRow = Integer.parseInt(idFlatTableFromSelectedRowS);

		return idFlatTableFromSelectedRow;

	}

	public void buttonHorizontalLayoutEvent(Button nameButton, String flatSetOld, String flatSetNew) {

		nameButton.setEnabled(false);

		flatGrid.addSelectionListener(SelectionEvent -> {

			try {
				String flatSetFromFlatGrid = flatService.getFlatByFlatIdFromFlatTable(getIdFlatTableFromSelectedRow())
						.getFlatSet();
				if (flatSetFromFlatGrid.equals(flatSetOld)) {
					nameButton.setEnabled(true);
				} else {
					nameButton.setEnabled(false);
				}
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}

		});

		nameButton.addClickListener(e1 -> {

			try {

				flatService.updateFlatSetByFlatIdInFlatTable(getIdFlatTableFromSelectedRow(), flatSetNew);
				flatInfoGridLayout.removeComponent(flatGrid);
				flatInfoGridLayout.removeComponent(0, 2);
				flatGrid = flatGridFlatInfoView(flatService.getFlatsFromDB());
				flatInfoGridLayout.addComponent(flatGrid, 0, 0, 0, 0);
				flatInfoGridLayout.addComponent(flatButtonHorizontalLayout(), 0, 2, 0, 2);

			} catch (NumberFormatException | SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}

		});
	}

	public Button reservFlatButton() {

		Button reservFlatButton = new Button("Reserv Flat");
		reservFlatButton.setSizeFull();

		buttonHorizontalLayoutEvent(reservFlatButton, flatSetFree, flatSetReserved);

		return reservFlatButton;
	}

	public Button cancelReservFlatButton() {

		Button canselReservFlatButton = new Button("Cancel Flat Reserv");
		canselReservFlatButton.setSizeFull();

		buttonHorizontalLayoutEvent(canselReservFlatButton, flatSetReserved, flatSetFree);

		return canselReservFlatButton;
	}

	public Button saleFlatButton() throws SQLException {

		Button saleFlatButton = new Button("Sale Flat");
		saleFlatButton.setSizeFull();

		saleFlatButton.setEnabled(false);

		flatGrid.addSelectionListener(SelectionEvent -> {

			try {
				String flatSetFromFlatGrid = flatService.getFlatByFlatIdFromFlatTable(getIdFlatTableFromSelectedRow())
						.getFlatSet();
				if (flatSetFromFlatGrid.equals(flatSetFree) || flatSetFromFlatGrid.equals(flatSetReserved)) {
					saleFlatButton.setEnabled(true);
				} else {
					saleFlatButton.setEnabled(false);
				}
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}

		});

		saleFlatButton.addClickListener(e -> {

			Window saleFlatWindow = new Window("Sale Flat");
			saleFlatWindow.setWidth("850px");

			VerticalLayout saleFlatWindowVerticalLayout = new VerticalLayout();

			saleFlatWindow.setContent(saleFlatWindowVerticalLayout);

			HorizontalLayout saleFlatWindowHorizontalLayout = new HorizontalLayout();
			saleFlatWindowHorizontalLayout.setMargin(true);
			saleFlatWindowHorizontalLayout.setSpacing(true);
			saleFlatWindowHorizontalLayout.setSizeFull();
			saleFlatWindowVerticalLayout.addComponent(saleFlatWindowHorizontalLayout);

			FormLayout saleFlatWindowFormLayout = new FormLayout();
			saleFlatWindowFormLayout.setMargin(true);

			saleFlatWindowFormLayout.setSizeFull();

			DateField saleFlatDateField = new DateField("Contract Date");
			saleFlatDateField.setValue(new java.util.Date());

			saleFlatDateField.setDateFormat("dd-MM-yyyy");
			saleFlatWindowFormLayout.addComponent(saleFlatDateField);

			TextField firstNameTextField = new TextField("First Name");
			saleFlatWindowFormLayout.addComponent(firstNameTextField);

			TextField lastNameTextField = new TextField("Last Name");
			saleFlatWindowFormLayout.addComponent(lastNameTextField);

			TextField surNameTextField = new TextField("Surname");
			saleFlatWindowFormLayout.addComponent(surNameTextField);

			TextField contractNumberTextField = new TextField("Contract Number");
			saleFlatWindowFormLayout.addComponent(contractNumberTextField);

			TextField costTextField = new TextField("Cost, $");
			saleFlatWindowFormLayout.addComponent(costTextField);

			Panel saleFlatBuyerInfoPanel = new Panel("Buyer Info");
			saleFlatBuyerInfoPanel.setSizeFull();
			saleFlatWindowHorizontalLayout.addComponent(saleFlatBuyerInfoPanel);
			saleFlatBuyerInfoPanel.setContent(saleFlatWindowFormLayout);

			Grid saleFlatInfoGrid = new Grid();

			saleFlatInfoGrid.addColumn("Name", String.class);
			saleFlatInfoGrid.addColumn("Value", String.class);

			List<Column> flatGridColumnNameList = new ArrayList<>();
			int i = 0;

			flatGridColumnNameList = flatGrid.getColumns();

			Iterator<Column> itr = flatGridColumnNameList.iterator();
			while (itr.hasNext()) {
				Column columnNameFromList = itr.next();

				Flat selectedFlat = new Flat();

				try {

					selectedFlat = flatService.getFlatByFlatIdFromFlatTable(getIdFlatTableFromSelectedRow());

				} catch (SQLException e2) {
					// TODO Auto-generated catch block
					e2.printStackTrace();
				}

				try {
					saleFlatInfoGrid.addRow(flatGridColumnNameList.get(i).getHeaderCaption(),
							flatService.getFlatFromDbByColumnIndex(i, selectedFlat));
				} catch (IllegalStateException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}

				i++;
			}

			Panel saleFlatInfoGridPanel = new Panel("Flat Info");

			saleFlatInfoGridPanel.setSizeFull();
			saleFlatWindowHorizontalLayout.addComponent(saleFlatInfoGridPanel);
			saleFlatInfoGridPanel.setContent(saleFlatInfoGrid);

			HorizontalLayout saleFlatWindowButtonHorizontalLayout = new HorizontalLayout();
			saleFlatWindowButtonHorizontalLayout.setSizeFull();
			saleFlatWindowButtonHorizontalLayout.setSpacing(true);
			saleFlatWindowVerticalLayout.addComponent(saleFlatWindowButtonHorizontalLayout);

			Button saleFlatWindowButton = new Button("Sale Flat Window");
			saleFlatWindowButton.setSizeFull();
			saleFlatWindowButton.addClickListener(es -> {

				Flat saleBuyerFlat = new Flat();

				try {
					saleBuyerFlat.setIdFlatTable(getIdFlatTableFromSelectedRow());
				} catch (SQLException e2) {
					// TODO Auto-generated catch block
					e2.printStackTrace();
				}

				saleBuyerFlat.setFlatBuyerFirstname(firstNameTextField.getValue());
				saleBuyerFlat.setFlatBuyerLastname(lastNameTextField.getValue());
				saleBuyerFlat.setFlatBuyerSurname(surNameTextField.getValue());
				saleBuyerFlat.setFlatContractNumber(contractNumberTextField.getValue());
				saleBuyerFlat.setFlatCost(Integer.parseInt(costTextField.getValue()));
				saleBuyerFlat.setFlatContractDate(saleFlatDateField.getValue());

				try {

					flatService.updateFlatSetByFlatIdInFlatTable(getIdFlatTableFromSelectedRow(), flatSetSolded);

					flatService.createBuyerFlat(saleBuyerFlat);

					saleFlatWindow.close();
				} catch (SQLException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}

				try {

					flatInfoGridLayout.removeComponent(flatGrid);

					flatInfoGridLayout.removeComponent(0, 2);

					flatGrid = flatGridFlatInfoView(flatService.getFlatsFromDB());
								
					flatInfoGridLayout.addComponent(flatGrid, 0, 0, 0, 0);

					flatInfoGridLayout.addComponent(flatButtonHorizontalLayout(), 0, 2, 0, 2);

				} catch (NumberFormatException | SQLException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}

			});

			saleFlatWindowButtonHorizontalLayout.addComponent(saleFlatWindowButton);

			Button cancelWindowButton = new Button("Cancel");
			cancelWindowButton.setSizeFull();
			saleFlatWindowButtonHorizontalLayout.addComponent(cancelWindowButton);

			cancelWindowButton.addClickListener(e1 -> {
				saleFlatWindow.close();

			});

			saleFlatWindow.center();

			UI.getCurrent().addWindow(saleFlatWindow);

		});

		return saleFlatButton;

	}

//	public Button addFlatButton() {
//
//		Button addFlatButton = new Button("Add Flat");
//
//		addFlatButton.addClickListener(e -> {
//
//			Window addFlatWindow = new Window("Add Flat");
//			VerticalLayout addFlatWindowVerticalLayout = new VerticalLayout();
//			addFlatWindowVerticalLayout.setMargin(true);
//			addFlatWindowVerticalLayout.setSpacing(true);
//
//			addFlatWindow.setContent(addFlatWindowVerticalLayout);
//
//			FormLayout addFlatFormLayout = new FormLayout();
//			addFlatFormLayout.setMargin(true);
//			addFlatFormLayout.setSpacing(true);
//			addFlatWindowVerticalLayout.addComponent(addFlatFormLayout);
//
//			ComboBox buildingCorpsComboBox = new ComboBox("Building Corps");
//			buildingCorpsComboBox.addItems("1/А", "2/Б", "3/В", "4/Г", "5/Д", "6/Е", "7/Є", "8/Ж");
//			addFlatFormLayout.addComponent(buildingCorpsComboBox);
//
//			ComboBox flatRoomsComboBox = new ComboBox("Flat Rooms");
//			flatRoomsComboBox.addItems(1, 2, 3, 4, 5);
//			addFlatFormLayout.addComponent(flatRoomsComboBox);
//
//			ComboBox flatFloorComboBox = new ComboBox("Flat Floor");
//			flatFloorComboBox.addItems(1, 2, 3, 4, 5, 6, 7, 8, 9, 10);
//			addFlatFormLayout.addComponent(flatFloorComboBox);
//
//			TextField flatNumberTextField = new TextField("Flat Number");
//			addFlatFormLayout.addComponent(flatNumberTextField);
//
//			TextField flatAreaTextField = new TextField("Flat Area");
//			addFlatFormLayout.addComponent(flatAreaTextField);
//
//			HorizontalLayout windowButtonHorizontalLayout = new HorizontalLayout();
//			windowButtonHorizontalLayout.setSizeFull();
//			windowButtonHorizontalLayout.setSpacing(true);
//			addFlatWindowVerticalLayout.addComponent(windowButtonHorizontalLayout);
//
//			Button addWindowButton = new Button("Add");
//			addWindowButton.addClickListener(c -> {
//
//				Flat flat = new Flat();
//
//				flat.setBuildingCorps(buildingCorpsComboBox.getValue().toString());
//				flat.setFlatArea(Double.parseDouble(flatAreaTextField.getValue()));
//				flat.setFlatFloor(Integer.parseInt(flatFloorComboBox.getValue().toString()));
//				flat.setFlatNumber(Integer.parseInt(flatNumberTextField.getValue()));
//				flat.setFlatRooms(Integer.parseInt(flatRoomsComboBox.getValue().toString()));
//
//				flatAreaTextField.addValidator(new DoubleValidator("Flat area must be double"));
//				flatNumberTextField.addValidator(new IntegerValidator("Flat number must be integer"));
//
//				if (flatAreaTextField.isValid() && flatNumberTextField.isValid()) {
//					addFlatWindow.close();
//				}
//
//				try {
//					flatService.createFlat(flat);
//					addComponent(flatGridFlatInfoView());
//				} catch (SQLException e1) {
//					// TODO Auto-generated catch block
//					e1.printStackTrace();
//				}
//
//				removeAllComponents();
//
//				try {
//					addComponent(flatButtonHorizontalLayout());
//				} catch (SQLException e1) {
//					// TODO Auto-generated catch block
//					e1.printStackTrace();
//				}
//
//			});
//			addWindowButton.setSizeFull();
//			windowButtonHorizontalLayout.addComponent(addWindowButton);
//
//			Button cancelWindowButton = new Button("Cancel");
//			cancelWindowButton.addClickListener(c -> {
//
//				addFlatWindow.close();
//
//			});
//			cancelWindowButton.setSizeFull();
//			windowButtonHorizontalLayout.addComponent(cancelWindowButton);
//
//			addFlatWindow.center();
//
//			UI.getCurrent().addWindow(addFlatWindow);
//
//		});
//
//		addFlatButton.setSizeFull();
//
//		return addFlatButton;
//	}

//	public Button deleteFlatButton() {
//
//		Button deleteFlatButton = new Button("Delete Flat");
//		deleteFlatButton.setSizeFull();
//
//		deleteFlatButton.addClickListener(c -> {
//
//			
//
//		});
//		
//		
//		return deleteFlatButton;
//	}

//	public Button reservFlatButton() {
//
//		Button reservFlatButton = new Button("Reserv Flat");
//		reservFlatButton.setSizeFull();
//
//		return reservFlatButton;
//	}

//	public Button saleFlatButton() throws SQLException {
//
//		Button saleFlatButton = new Button("Sale Flat");
//		saleFlatButton.setEnabled(false);
//
//		
//		
//		
//		
//		
//		
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
//			Grid saleFlatInfoGrid = new Grid();
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
//				Flat saleBuyerFlat = new Flat();
////				String flatSet = "Solded";
//
//				saleBuyerFlat.setIdFlatTable(Integer.parseInt(idFlatTableFromSelectedRow));
//
//				saleBuyerFlat.setFlatBuyerFirstname(firstNameTextField.getValue());
//				saleBuyerFlat.setFlatBuyerLastname(lastNameTextField.getValue());
//				saleBuyerFlat.setFlatBuyerSurname(surNameTextField.getValue());
//				saleBuyerFlat.setFlatContractNumber(contractNumberTextField.getValue());
//				saleBuyerFlat.setFlatCost(Integer.parseInt(costTextField.getValue()));
////				saleBuyerFlat.setFlatCost_m(Double.parseDouble(costTextField.getValue())/saleBuyerFlat.getFlatArea());
////				saleFlat.setFlatCost_m(Double.parseDouble(costTextField.getValue()));
//				saleBuyerFlat.setFlatContractDate(saleFlatDateField.getValue());
//
//				try {
////					flatService.updateFlatSetByFlatIdInFlatTable(Integer.parseInt(idFlatTableFromSelectedRow), flatSet);
//					flatService.updateFlatSetByFlatIdInFlatTable(Integer.parseInt(idFlatTableFromSelectedRow),
//							flatSetSolded);
//					flatService.createBuyerFlat(saleBuyerFlat);
//
//					removeAllComponents();
//
//					flatGrid = flatGridFlatInfoView(flatService.getFlatsFromDB());
//					addComponent(flatGrid);
//					addComponent(checkBoxHorizontalLayout());
//					addComponent(flatButtonHorizontalLayout());
//
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
//			cancelWindowButton.addClickListener(e1 -> {
//				saleFlatWindow.close();
//
//			});
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

		cancelButton.addClickListener(e1 -> {

			try {
				flatService.updateFlatSetByFlatIdInFlatTable(getIdFlatTableFromSelectedRow(), flatSetFree);
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}

			UI.getCurrent().getNavigator().navigateTo("main");
		});

		return cancelButton;
	}

	@Override
	public void enter(ViewChangeEvent event) {
		// TODO Auto-generated method stub

	}

}

//package com.example.Vaadin7_Builder.view;
//
//import java.sql.Date;
//import java.sql.SQLException;
//import java.util.ArrayList;
//import java.util.Iterator;
//import java.util.List;
//
//import com.example.Vaadin7_Builder.model.Flat;
//import com.example.Vaadin7_Builder.service.FlatService;
//import com.mysql.cj.x.protobuf.MysqlxConnection.Close;
//import com.vaadin.data.validator.DoubleValidator;
//import com.vaadin.data.validator.IntegerValidator;
//import com.vaadin.event.SelectionEvent;
//import com.vaadin.navigator.View;
//import com.vaadin.navigator.ViewChangeListener.ViewChangeEvent;
//import com.vaadin.ui.Button;
//import com.vaadin.ui.CheckBox;
//import com.vaadin.ui.ComboBox;
//import com.vaadin.ui.DateField;
//import com.vaadin.ui.FormLayout;
//import com.vaadin.ui.Grid;
//import com.vaadin.ui.Grid.Column;
//import com.vaadin.ui.Grid.SingleSelectionModel;
//import com.vaadin.ui.HorizontalLayout;
//import com.vaadin.ui.Layout;
//import com.vaadin.ui.Panel;
//import com.vaadin.ui.TextField;
//import com.vaadin.ui.UI;
//import com.vaadin.ui.VerticalLayout;
//import com.vaadin.ui.Window;
//
//public class FlatInfoView extends VerticalLayout implements View {
//
//	FlatService flatService = new FlatService();
//
//	Grid flatGrid = new Grid();
//	
//	private String flatSetReserved = "Reserved";
//	
////	List<Flat> flatList = new ArrayList<>();
//	
//	
//
//	public FlatInfoView() throws SQLException {
//		
//		
////		flatGrid = flatGridFlatInfoView(flatService.getFlatsFromDB());
//////		addComponent(flatGrid);
////		
////		HorizontalLayout flatGridHorizontalLayout = new HorizontalLayout();
////		flatGridHorizontalLayout.setSizeFull();
////		flatGridHorizontalLayout.addComponent(flatGrid);
////		addComponent(flatGridHorizontalLayout);
//		
//		
//		addComponent(flatGridHorizontalLayout());
//
//		addComponent(checkBoxHorizontalLayout());
//		
//		addComponent(flatButtonHorizontalLayout());
//		
//		
//
//	}
//	
//
//	
//	
//
////	public Layout FlatCheckBoxHorizontalLayout() {
////	
////	HorizontalLayout buttonHorizontalLayout = new HorizontalLayout();
////	buttonHorizontalLayout.setMargin(true);
////	buttonHorizontalLayout.setSpacing(true);
////	buttonHorizontalLayout.setSizeFull();
////	addComponent(buttonHorizontalLayout);
////	
////	return FlatCheckBoxHorizontalLayout();
////	
////	}
//
//	public Grid flatGridFlatInfoView(List<Flat> flatList) throws SQLException {
//
////		List<Flat> flatList = new ArrayList<>();
//
//		Grid flatGrid = new Grid();
//
//		flatGrid.setSizeFull();
//
////		flatGrid.setSelectionMode(SelectionMode.MULTI);
//
//		flatGrid.addColumn("idFlatTable", Integer.class);
//		flatGrid.addColumn("buildingCorps", String.class);
//		flatGrid.addColumn("flatRooms", Integer.class);
//		flatGrid.addColumn("flatFloor", Integer.class);
//		flatGrid.addColumn("flatNumber", Integer.class);
//		flatGrid.addColumn("flatArea", Double.class);
//		flatGrid.addColumn("flatSet", String.class);
////		flatGrid.addColumn("flatBuyerFirstname", String.class);
////		flatGrid.addColumn("flatBuyerLastname", String.class);
////		flatGrid.addColumn("flatBuyerSurname", String.class);
////		flatGrid.addColumn("flatContractNumber", String.class);
////		
////		flatGrid.addColumn("flatContractDate", Date.class);
////		flatGrid.addColumn("flatCost", Integer.class);
////		flatGrid.addColumn("flatSellerName", String.class);
////		flatGrid.addColumn("flatNotes", String.class);
//
////		addComponent(flatGrid);
//
////		flatList = flatService.getFlatsFromDB();
//
//		Iterator<Flat> itr = flatList.iterator();
//		while (itr.hasNext()) {
//			Flat flatFromList = itr.next();
//			flatGrid.addRow(flatFromList.getIdFlatTable(), flatFromList.getBuildingCorps(), flatFromList.getFlatRooms(),
//					flatFromList.getFlatFloor(), flatFromList.getFlatNumber(), flatFromList.getFlatArea(),
//					flatFromList.getFlatSet()
////							, flatFromList.getFlatBuyerFirstname(), flatFromList.getFlatBuyerLastname(),
////							flatFromList.getFlatBuyerSurname(), flatFromList.getFlatContractNumber(), 
////					, flatFromList.getFlatContractDate()
//
////							, flatFromList.getFlatCost(), flatFromList.getFlatSellerName(), flatFromList.getFlatNotes()
//			);
//		}
//
//		return flatGrid;
//
//	}
//	
//	
//	public void reloadFlatInfoView() throws SQLException {
//		removeAllComponents();
////		flatGrid = flatGridFlatInfoView(flatService.getFlatsByFlatSetFromDB(flatSetReserved));
//		
//		addComponent(flatGrid);
//		
//		addComponent(checkBoxHorizontalLayout());
//		addComponent(flatButtonHorizontalLayout());
//	}
//	
//	
//	public void reloadFlatGridHorizontalLayout() throws SQLException {
//		flatGridHorizontalLayout().removeAllComponents();
////		flatGrid = flatGridFlatInfoView(flatService.getFlatsByFlatSetFromDB(flatSetReserved));
//		
//		flatGridHorizontalLayout().addComponent(flatGrid);
//		
////		addComponent(checkBoxHorizontalLayout());
////		addComponent(flatButtonHorizontalLayout());
//	}
//	
//	
//	public Layout flatGridHorizontalLayout() throws SQLException {
//		flatGrid = flatGridFlatInfoView(flatService.getFlatsFromDB());
////		addComponent(flatGrid);
//		
//		HorizontalLayout flatGridHorizontalLayout = new HorizontalLayout();
//		flatGridHorizontalLayout.setSizeFull();
//		
////		Panel flatGridHorizontalLayoutPanel = new Panel();
////		flatGridHorizontalLayoutPanel.setSizeFull();
//		
////		flatGridHorizontalLayoutPanel.setContent(flatGrid);
//		
////		flatGridHorizontalLayout.addComponent(flatGridHorizontalLayoutPanel);
//		flatGridHorizontalLayout.addComponent(flatGrid);
//		addComponent(flatGridHorizontalLayout);
//		
//		return flatGridHorizontalLayout;
//	}
//	
//	
//	
//	public Layout checkBoxHorizontalLayout() {
//		
//		HorizontalLayout checkBoxHorizontalLayout = new HorizontalLayout();
//		checkBoxHorizontalLayout.setMargin(true);
//		checkBoxHorizontalLayout.setSpacing(true);
//		checkBoxHorizontalLayout.setSizeFull();
//		
//		CheckBox freeCheckBox = new CheckBox("Free flats");
////		freeCheckBox.addValueChangeListener(event -> {
////			
////			flatService.getFlatsByFlatSetFromDB()createBuyerFlat(saleBuyerFlat);
////		
////			
////			
////		});
//		
//		checkBoxHorizontalLayout.addComponent(freeCheckBox);
//		
//		CheckBox soldCheckBox = new CheckBox("Solded flats");
//		soldCheckBox.addValueChangeListener(event -> {
//			
//			if (soldCheckBox.isEmpty()) {
//				
//				try {
//					flatGrid = flatGridFlatInfoView(flatService.getFlatsFromDB());
//					flatGridHorizontalLayout().removeAllComponents();
//					flatButtonHorizontalLayout().addComponent(flatGrid);
////					reloadFlatInfoView();
//				} catch (SQLException e) {
//					// TODO Auto-generated catch block
//					e.printStackTrace();
//				}
//			}
//			else {
//				try {
//					flatGrid = flatGridFlatInfoView(flatService.getFlatsByFlatSetFromDB(flatSetReserved));
////					reloadFlatInfoView();
//					flatGridHorizontalLayout().removeAllComponents();
//					flatButtonHorizontalLayout().addComponent(flatGrid);
//				} catch (SQLException e) {
//					// TODO Auto-generated catch block
//					e.printStackTrace();
//				}
////				flatService.updateBuyerInfoFlatByFlatId(flatId, flat);
//			}	
//			
////	try {
////		reloadFlatInfoView();
////	} catch (SQLException e) {
////		// TODO Auto-generated catch block
////		e.printStackTrace();
////	}		
////			
//			
//			
////				try {
////					removeAllComponents();
//////					flatGrid = flatGridFlatInfoView(flatService.getFlatsByFlatSetFromDB(flatSetReserved));
////					
////					addComponent(flatGrid);
////					
////					addComponent(checkBoxHorizontalLayout);
////					addComponent(flatButtonHorizontalLayout());
////				} catch (SQLException e) {
////					// TODO Auto-generated catch block
////					e.printStackTrace();
////				}
//
//
//			
//			
//		});
//		checkBoxHorizontalLayout.addComponent(soldCheckBox);
//		
//		CheckBox RezervCheckBox = new CheckBox("Rezerved flats");
//		checkBoxHorizontalLayout.addComponent(RezervCheckBox);
//		
//		CheckBox allCheckBox = new CheckBox("All flats");
//		checkBoxHorizontalLayout.addComponent(allCheckBox);
//		
//		
//
//		
//		
//		return checkBoxHorizontalLayout;
//		
//	}
//	
//	
//	
//	
//
//	public Layout flatButtonHorizontalLayout() throws SQLException {
//
//		HorizontalLayout buttonHorizontalLayout = new HorizontalLayout();
//		buttonHorizontalLayout.setMargin(true);
//		buttonHorizontalLayout.setSpacing(true);
//		buttonHorizontalLayout.setSizeFull();
//
////		buttonHorizontalLayout.addComponent(addFlatButton());
//
////		buttonHorizontalLayout.addComponent(deleteFlatButton());
//
//		buttonHorizontalLayout.addComponent(reservFlatButton());
//		
//		buttonHorizontalLayout.addComponent(cancelReservFlatButton());
//		
//		buttonHorizontalLayout.addComponent(saleFlatButton());
//
//		buttonHorizontalLayout.addComponent(cancelButton());
//
//		return buttonHorizontalLayout;
//	}
//	
//	
//	
//	
//	
//
////	public Button addFlatButton() {
////
////		Button addFlatButton = new Button("Add Flat");
////
////		addFlatButton.addClickListener(e -> {
////
////			Window addFlatWindow = new Window("Add Flat");
////			VerticalLayout addFlatWindowVerticalLayout = new VerticalLayout();
////			addFlatWindowVerticalLayout.setMargin(true);
////			addFlatWindowVerticalLayout.setSpacing(true);
////
////			addFlatWindow.setContent(addFlatWindowVerticalLayout);
////
////			FormLayout addFlatFormLayout = new FormLayout();
////			addFlatFormLayout.setMargin(true);
////			addFlatFormLayout.setSpacing(true);
////			addFlatWindowVerticalLayout.addComponent(addFlatFormLayout);
////
////			ComboBox buildingCorpsComboBox = new ComboBox("Building Corps");
////			buildingCorpsComboBox.addItems("1/А", "2/Б", "3/В", "4/Г", "5/Д", "6/Е", "7/Є", "8/Ж");
////			addFlatFormLayout.addComponent(buildingCorpsComboBox);
////
////			ComboBox flatRoomsComboBox = new ComboBox("Flat Rooms");
////			flatRoomsComboBox.addItems(1, 2, 3, 4, 5);
////			addFlatFormLayout.addComponent(flatRoomsComboBox);
////
////			ComboBox flatFloorComboBox = new ComboBox("Flat Floor");
////			flatFloorComboBox.addItems(1, 2, 3, 4, 5, 6, 7, 8, 9, 10);
////			addFlatFormLayout.addComponent(flatFloorComboBox);
////
////			TextField flatNumberTextField = new TextField("Flat Number");
////			addFlatFormLayout.addComponent(flatNumberTextField);
////
////			TextField flatAreaTextField = new TextField("Flat Area");
////			addFlatFormLayout.addComponent(flatAreaTextField);
////
////			HorizontalLayout windowButtonHorizontalLayout = new HorizontalLayout();
////			windowButtonHorizontalLayout.setSizeFull();
////			windowButtonHorizontalLayout.setSpacing(true);
////			addFlatWindowVerticalLayout.addComponent(windowButtonHorizontalLayout);
////
////			Button addWindowButton = new Button("Add");
////			addWindowButton.addClickListener(c -> {
////
////				Flat flat = new Flat();
////
////				flat.setBuildingCorps(buildingCorpsComboBox.getValue().toString());
////				flat.setFlatArea(Double.parseDouble(flatAreaTextField.getValue()));
////				flat.setFlatFloor(Integer.parseInt(flatFloorComboBox.getValue().toString()));
////				flat.setFlatNumber(Integer.parseInt(flatNumberTextField.getValue()));
////				flat.setFlatRooms(Integer.parseInt(flatRoomsComboBox.getValue().toString()));
////
////				flatAreaTextField.addValidator(new DoubleValidator("Flat area must be double"));
////				flatNumberTextField.addValidator(new IntegerValidator("Flat number must be integer"));
////
////				if (flatAreaTextField.isValid() && flatNumberTextField.isValid()) {
////					addFlatWindow.close();
////				}
////
////				try {
////					flatService.createFlat(flat);
////					addComponent(flatGridFlatInfoView());
////				} catch (SQLException e1) {
////					// TODO Auto-generated catch block
////					e1.printStackTrace();
////				}
////
////				removeAllComponents();
////
////				try {
////					addComponent(flatButtonHorizontalLayout());
////				} catch (SQLException e1) {
////					// TODO Auto-generated catch block
////					e1.printStackTrace();
////				}
////
////			});
////			addWindowButton.setSizeFull();
////			windowButtonHorizontalLayout.addComponent(addWindowButton);
////
////			Button cancelWindowButton = new Button("Cancel");
////			cancelWindowButton.addClickListener(c -> {
////
////				addFlatWindow.close();
////
////			});
////			cancelWindowButton.setSizeFull();
////			windowButtonHorizontalLayout.addComponent(cancelWindowButton);
////
////			addFlatWindow.center();
////
////			UI.getCurrent().addWindow(addFlatWindow);
////
////		});
////
////		addFlatButton.setSizeFull();
////
////		return addFlatButton;
////	}
//
//	public Button reservFlatButton() {
//
//		Button reservFlatButton = new Button("Reserv Flat");
//		reservFlatButton.setSizeFull();
//		reservFlatButton.setEnabled(false);
//		
//		flatGrid.addSelectionListener(SelectionEvent -> {
//			reservFlatButton.setEnabled(true);
//		});
//		
//
//		reservFlatButton.addClickListener(e1 -> {
//			
//			Object selected = ((SingleSelectionModel) flatGrid.getSelectionModel()).getSelectedRow();
//			String idFlatTableFromSelectedRow = flatGrid.getContainerDataSource().getItem(selected)
//					.getItemProperty("idFlatTable").getValue().toString();
//			
//			try {
//				flatService.updateFlatSetByFlatIdInFlatTable(Integer.parseInt(idFlatTableFromSelectedRow), flatSetReserved);
//				flatButtonHorizontalLayout().removeComponent(flatGrid);
//				flatGrid = flatGridFlatInfoView(flatService.getFlatsFromDB());
//				flatGridHorizontalLayout().addComponent(flatGrid);	
////				addComponent(checkBoxHorizontalLayout());
////				addComponent(flatButtonHorizontalLayout());
//			} catch (NumberFormatException | SQLException e) {
//				// TODO Auto-generated catch block
//				e.printStackTrace();
//			}
//			
//
//			
//			
//
//		});
//
//		return reservFlatButton;
//	}
//	
//	
//	public Button cancelReservFlatButton() {
//
//		
//		Button canselReservFlatButton = new Button("Cancel Flat Reserv");
//		canselReservFlatButton.setSizeFull();
//		canselReservFlatButton.setEnabled(false);
//		
//		
//	
//		
//		
//		flatGrid.addSelectionListener(SelectionEvent -> {
//			
//
//			
//				try {
//					Object selected = ((SingleSelectionModel) flatGrid.getSelectionModel()).getSelectedRow();
//					String idFlatTableFromSelectedRow = flatGrid.getContainerDataSource().getItem(selected)
//							.getItemProperty("idFlatTable").getValue().toString();
//					
//					String set = flatService.getFlatByFlatIdFromFlatTable(Integer.parseInt(idFlatTableFromSelectedRow)).getFlatSet();
//					
//					if (set.equals(flatSetReserved)) {
//						canselReservFlatButton.setEnabled(true);
//					}
//					else {
//						canselReservFlatButton.setEnabled(false);
//					}
//					
//					
//					
////					if (flatService.getFlatByFlatIdFromFlatTable(Integer.parseInt(idFlatTableFromSelectedRow)).getFlatSet() == flatSetReserved) {
////						canselReservFlatButton.setEnabled(true);
////					}
//				} catch (NumberFormatException e) {
//					// TODO Auto-generated catch block
//					e.printStackTrace();
//				} catch (SQLException e) {
//					// TODO Auto-generated catch block
//					e.printStackTrace();
//				}
//		});
//		
//
//		canselReservFlatButton.addClickListener(e1 -> {
//			
//			
//			try {
//				Object selected = ((SingleSelectionModel) flatGrid.getSelectionModel()).getSelectedRow();
//				String idFlatTableFromSelectedRow = flatGrid.getContainerDataSource().getItem(selected)
//						.getItemProperty("idFlatTable").getValue().toString();
//
//				flatService.updateFlatSetByFlatIdInFlatTable(Integer.parseInt(idFlatTableFromSelectedRow), " ");
//				flatGridHorizontalLayout().removeAllComponents();
//				flatGrid = flatGridFlatInfoView(flatService.getFlatsFromDB());
//				flatGridHorizontalLayout().addComponent(flatGrid);
//				addComponent(checkBoxHorizontalLayout());
//				addComponent(flatButtonHorizontalLayout());
//			} catch (NumberFormatException | SQLException e) {
//				// TODO Auto-generated catch block
//				e.printStackTrace();
//			}
//			
//
//			
//			
//
//		});
//
//		return canselReservFlatButton;
//	}
//	
//	
//	
////	public Button deleteFlatButton() {
////
////		Button deleteFlatButton = new Button("Delete Flat");
////		deleteFlatButton.setSizeFull();
////
////		deleteFlatButton.addClickListener(c -> {
////
////			
////
////		});
////		
////		
////		return deleteFlatButton;
////	}
//
////	public Button reservFlatButton() {
////
////		Button reservFlatButton = new Button("Reserv Flat");
////		reservFlatButton.setSizeFull();
////
////		return reservFlatButton;
////	}
//
//	public Button saleFlatButton() throws SQLException {
//
//		Button saleFlatButton = new Button("Sale Flat");
//		saleFlatButton.setEnabled(false);
//
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
//			Grid saleFlatInfoGrid = new Grid();
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
//				Flat saleBuyerFlat = new Flat();
//				String flatSet = "Solded";
//
//				saleBuyerFlat.setIdFlatTable(Integer.parseInt(idFlatTableFromSelectedRow));
//
//				saleBuyerFlat.setFlatBuyerFirstname(firstNameTextField.getValue());
//				saleBuyerFlat.setFlatBuyerLastname(lastNameTextField.getValue());
//				saleBuyerFlat.setFlatBuyerSurname(surNameTextField.getValue());
//				saleBuyerFlat.setFlatContractNumber(contractNumberTextField.getValue());
//				saleBuyerFlat.setFlatCost(Integer.parseInt(costTextField.getValue()));
////				saleBuyerFlat.setFlatCost_m(Double.parseDouble(costTextField.getValue())/saleBuyerFlat.getFlatArea());
////				saleFlat.setFlatCost_m(Double.parseDouble(costTextField.getValue()));
//				saleBuyerFlat.setFlatContractDate(saleFlatDateField.getValue());
//
//				try {
//					flatService.updateFlatSetByFlatIdInFlatTable(Integer.parseInt(idFlatTableFromSelectedRow), flatSet);
//					flatService.createBuyerFlat(saleBuyerFlat);
//
//					removeAllComponents();
//
//					flatGrid = flatGridFlatInfoView(flatService.getFlatsFromDB());
//					addComponent(flatGrid);
//					addComponent(checkBoxHorizontalLayout());
//					addComponent(flatButtonHorizontalLayout());
//
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
//			cancelWindowButton.addClickListener(e1 -> {
//				saleFlatWindow.close();
//
//			});
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
//
//
//
//	public Button cancelButton() {
//
//		Button cancelButton = new Button("Cancel");
//		cancelButton.setSizeFull();
//
//		cancelButton.addClickListener(e1 -> {
//
//			UI.getCurrent().getNavigator().navigateTo("main");
//			
//		});
//
//		return cancelButton;
//	}
//
//	@Override
//	public void enter(ViewChangeEvent event) {
//		// TODO Auto-generated method stub
//
//	}
//
//}
