package com.example.Vaadin7_Builder.view;

import java.sql.SQLException;
//import java.text.DateFormat;
import java.util.ArrayList;
import java.util.Date;
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
import com.vaadin.ui.renderers.DateRenderer;

public class FlatInfoView extends VerticalLayout implements View {

	private FlatService flatService = new FlatService();

	private Grid flatGrid = new Grid();

	private String flatSetReserved = "Reserved";
	private String flatSetSolded = "Solded";
	private String flatSetFree = "";

	private GridLayout flatInfoGridLayout = new GridLayout(1, 3);

	public FlatInfoView() throws SQLException {

		flatInfoGridLayout.setMargin(true);
		flatInfoGridLayout.setSizeFull();
		addComponent(flatInfoGridLayout);

//		flatGrid = flatGridFlatInfoView(flatService.getFlatsFromDB());

		flatGrid = flatGridFlatInfoView(flatService.getFlatsFromFlatTableAndFlatBuyerDB());

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

		flatGrid.addColumn("flatContractDate", Date.class);

		flatGrid.getColumn("flatContractDate").setHeaderCaption("Flat Contract Date")
				.setRenderer(new DateRenderer("%1$td.%1$tm.%1$tY"));

		Iterator<Flat> itr = flatList.iterator();
		while (itr.hasNext()) {
			Flat flatFromList = itr.next();
			flatGrid.addRow(flatFromList.getIdFlatTable(), flatFromList.getBuildingCorps(), flatFromList.getFlatRooms(),
					flatFromList.getFlatFloor(), flatFromList.getFlatNumber(), flatFromList.getFlatArea(),
					flatFromList.getFlatSet()

					, flatFromList.getFlatContractDate()

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

					flatGrid = flatGridFlatInfoView(flatService.getFlatsFromFlatTableAndFlatBuyerDB());

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
//		buttonHorizontalLayout.setMargin(true);
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

			saleFlatDateField.setDateFormat("dd.MM.yyyy");
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

					flatGrid = flatGridFlatInfoView(flatService.getFlatsFromFlatTableAndFlatBuyerDB());

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
