package com.example.Vaadin7_Builder.view;

import java.sql.SQLException;
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
import com.vaadin.ui.ComboBox;
import com.vaadin.ui.FormLayout;
import com.vaadin.ui.Grid;
import com.vaadin.ui.GridLayout;
import com.vaadin.ui.Grid.SingleSelectionModel;
import com.vaadin.ui.renderers.DateRenderer;
import com.vaadin.ui.HorizontalLayout;
import com.vaadin.ui.Layout;
import com.vaadin.ui.TextField;
import com.vaadin.ui.UI;
import com.vaadin.ui.VerticalLayout;
import com.vaadin.ui.Window;

public class BuildingInfoView extends VerticalLayout implements View {

	private FlatService flatService = new FlatService();

	private Grid flatGrid = new Grid();

	private GridLayout buildingInfoGridLayout = new GridLayout(1, 3);

//	private String flatSetReserved = "Reserved";
//	private String flatSetSolded = "Solded";
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

				try {
					flatService.createFlat(flat);

					buildingInfoGridLayout.removeComponent(0, 0);
					flatGrid = flatGridFlatInfoView(flatService.getFlatsFromFlatTableAndFlatBuyerDB());
					buildingInfoGridLayout.addComponent(flatGrid, 0, 0, 0, 0);

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
		deleteFlatButton.setEnabled(false);

		flatGrid.addSelectionListener(SelectionEvent -> {

			Object selected = ((SingleSelectionModel) flatGrid.getSelectionModel()).getSelectedRow();
			String idFlatTableFromSelectedRow = flatGrid.getContainerDataSource().getItem(selected)
					.getItemProperty("idFlatTable").getValue().toString();
			try {
				String flatSetFromFlatGrid = flatService
						.getFlatByFlatIdFromFlatTable(Integer.parseInt(idFlatTableFromSelectedRow)).getFlatSet();

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

	@Override
	public void enter(ViewChangeEvent event) {
		// TODO Auto-generated method stub

	}

}
