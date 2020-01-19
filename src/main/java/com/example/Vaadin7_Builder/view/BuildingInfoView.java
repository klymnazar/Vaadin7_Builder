package com.example.Vaadin7_Builder.view;

import java.sql.SQLException;
import java.text.DecimalFormat;
import java.util.Iterator;
import java.util.List;

import com.example.Vaadin7_Builder.model.Flat;
import com.example.Vaadin7_Builder.service.FlatService;
import com.vaadin.data.validator.DoubleValidator;
import com.vaadin.data.validator.IntegerValidator;
import com.vaadin.navigator.View;
import com.vaadin.navigator.ViewChangeListener.ViewChangeEvent;
import com.vaadin.ui.Alignment;
import com.vaadin.ui.Button;
import com.vaadin.ui.ComboBox;
import com.vaadin.ui.FormLayout;
import com.vaadin.ui.Grid;
import com.vaadin.ui.GridLayout;
import com.vaadin.ui.Grid.FooterRow;
import com.vaadin.ui.Grid.SingleSelectionModel;
import com.vaadin.ui.HorizontalLayout;
import com.vaadin.ui.Layout;
import com.vaadin.ui.TextField;
import com.vaadin.ui.UI;
import com.vaadin.ui.VerticalLayout;
import com.vaadin.ui.Window;

public class BuildingInfoView extends VerticalLayout implements View {

	private FlatService flatService = new FlatService();

	private Grid flatGrid = new Grid();

	private GridLayout buildingInfoGridLayout = new GridLayout(1, 15);

	private String flatSetFree = "";

	private DecimalFormat decimalFormat = new DecimalFormat("0.00");

	public BuildingInfoView() throws SQLException {

		setSizeFull();
		buildingInfoGridLayout.setSizeFull();
		buildingInfoGridLayout.setMargin(true);
		buildingInfoGridLayout.setSpacing(true);
		addComponent(buildingInfoGridLayout);

		flatGrid = flatGridFlatInfoView(flatService.getFlatsFromFlatTable());

		buildingInfoGridLayout.addComponent(flatGrid, 0, 0, 0, 13);

		flatButtonHorizontalLayout();

	}

	public Grid flatGridFlatInfoView(List<Flat> flatList) throws SQLException {

		Grid flatGrid = new Grid();

		flatGrid.setSizeFull();

		flatGrid.addColumn("idFlatTable", Integer.class);
		flatGrid.addColumn("№", Integer.class);
		flatGrid.addColumn("buildingCorps", String.class);
		flatGrid.addColumn("flatRooms", Integer.class);
		flatGrid.addColumn("flatFloor", Integer.class);
		flatGrid.addColumn("flatNumber", Integer.class);
		flatGrid.addColumn("flatArea", Double.class);
		flatGrid.addColumn("flatSet", String.class);

		flatGrid.getColumn("idFlatTable").setHidden(true);

		double flatArea = 0;
		int number = 1;

		Iterator<Flat> itr = flatList.iterator();
		while (itr.hasNext()) {
			Flat flatFromList = itr.next();

			flatArea = flatArea + flatFromList.getFlatArea();

			flatGrid.addRow(flatFromList.getIdFlatTable(), number, flatFromList.getBuildingCorps(),
					flatFromList.getFlatRooms(), flatFromList.getFlatFloor(), flatFromList.getFlatNumber(),
					flatFromList.getFlatArea(), flatFromList.getFlatSet());

			number++;
		}

		FooterRow flatGridFooterRow = flatGrid.prependFooterRow();
		flatGridFooterRow.getCell("flatArea")
				.setText("Total: " + decimalFormat.format(flatArea).replace(",", ".") + " m2");

		return flatGrid;

	}

	public Layout flatButtonHorizontalLayout() {

		HorizontalLayout buttonHorizontalLayout = new HorizontalLayout();
		buttonHorizontalLayout.setWidth("100%");
		buttonHorizontalLayout.setHeight("50px");
		buttonHorizontalLayout.setSpacing(true);

		buttonHorizontalLayout.addComponent(addFlatButton());

		buttonHorizontalLayout.addComponent(editFlatButton());

		buttonHorizontalLayout.addComponent(deleteFlatButton());

		buttonHorizontalLayout.addComponent(cancelButton());

		buildingInfoGridLayout.addComponent(buttonHorizontalLayout, 0, 14, 0, 14);
		buildingInfoGridLayout.setComponentAlignment(buttonHorizontalLayout, Alignment.MIDDLE_CENTER);

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
					updateBuildingInfoPage();
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

	public Button editFlatButton() {

		Button editFlatButton = new Button("Edit Flat");
		editFlatButton.setSizeFull();
		editFlatButton.setEnabled(false);

		flatGrid.addSelectionListener(SelectionEvent -> {

			Object selected = ((SingleSelectionModel) flatGrid.getSelectionModel()).getSelectedRow();
			String idFlatTableFromSelectedRow = flatGrid.getContainerDataSource().getItem(selected)
					.getItemProperty("idFlatTable").getValue().toString();
			try {
				String flatSetFromFlatGrid = flatService
						.getFlatByFlatIdFromFlatTable(Integer.parseInt(idFlatTableFromSelectedRow)).getFlatSet();

				if (flatSetFromFlatGrid.equals(flatSetFree)) {
					editFlatButton.setEnabled(true);

				} else {
					editFlatButton.setEnabled(false);
				}

			} catch (SQLException e1) {
				// TODO Auto-generated catch block
			}

		});

		editFlatButton.addClickListener(e -> {

			Object selected = ((SingleSelectionModel) flatGrid.getSelectionModel()).getSelectedRow();
			String idFlatTableFromSelectedRow = flatGrid.getContainerDataSource().getItem(selected)
					.getItemProperty("idFlatTable").getValue().toString();

			Flat selectedFlat = new Flat();
			try {
				selectedFlat = flatService
						.getFlatByFlatIdFromFlatTableAndFlatBuyerDB(Integer.parseInt(idFlatTableFromSelectedRow));
			} catch (NumberFormatException | SQLException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}

			Window editFlatWindow = new Window("Edit Flat");
			VerticalLayout editFlatWindowVerticalLayout = new VerticalLayout();
			editFlatWindowVerticalLayout.setMargin(true);
			editFlatWindowVerticalLayout.setSpacing(true);

			editFlatWindow.setContent(editFlatWindowVerticalLayout);

			FormLayout editFlatFormLayout = new FormLayout();
			editFlatFormLayout.setMargin(true);
			editFlatFormLayout.setSpacing(true);
			editFlatWindowVerticalLayout.addComponent(editFlatFormLayout);

			ComboBox buildingCorpsComboBox = new ComboBox("Building Corps");
			buildingCorpsComboBox.addItems("1/А", "2/Б", "3/В", "4/Г", "5/Д", "6/Е", "7/Є", "8/Ж");
			buildingCorpsComboBox.setValue(selectedFlat.getBuildingCorps());
			editFlatFormLayout.addComponent(buildingCorpsComboBox);

			ComboBox flatRoomsComboBox = new ComboBox("Flat Rooms");
			flatRoomsComboBox.addItems(1, 2, 3, 4, 5);
			flatRoomsComboBox.setValue(selectedFlat.getFlatRooms());
			editFlatFormLayout.addComponent(flatRoomsComboBox);

			ComboBox flatFloorComboBox = new ComboBox("Flat Floor");
			flatFloorComboBox.addItems(1, 2, 3, 4, 5, 6, 7, 8, 9, 10);
			flatFloorComboBox.setValue(selectedFlat.getFlatFloor());
			editFlatFormLayout.addComponent(flatFloorComboBox);

			TextField flatNumberTextField = new TextField("Flat Number");
			flatNumberTextField.setValue("" + selectedFlat.getFlatNumber());
			editFlatFormLayout.addComponent(flatNumberTextField);

			TextField flatAreaTextField = new TextField("Flat Area");
			flatAreaTextField.setValue("" + selectedFlat.getFlatArea());
			editFlatFormLayout.addComponent(flatAreaTextField);

			HorizontalLayout editFlatWindowButtonHorizontalLayout = new HorizontalLayout();
			editFlatWindowButtonHorizontalLayout.setSizeFull();
			editFlatWindowButtonHorizontalLayout.setSpacing(true);
			editFlatWindowVerticalLayout.addComponent(editFlatWindowButtonHorizontalLayout);

			Button windowEditFlatButton = new Button("Edit Flat");
			windowEditFlatButton.setSizeFull();
			windowEditFlatButton.addClickListener(c -> {

				Flat updateflat = new Flat();

				updateflat.setBuildingCorps(buildingCorpsComboBox.getValue().toString());
				updateflat.setFlatArea(Double.parseDouble(flatAreaTextField.getValue()));
				updateflat.setFlatFloor(Integer.parseInt(flatFloorComboBox.getValue().toString()));
				updateflat.setFlatNumber(Integer.parseInt(flatNumberTextField.getValue()));
				updateflat.setFlatRooms(Integer.parseInt(flatRoomsComboBox.getValue().toString()));

				flatAreaTextField.addValidator(new DoubleValidator("Flat area must be double"));
				flatNumberTextField.addValidator(new IntegerValidator("Flat number must be integer"));

				if (flatAreaTextField.isValid() && flatNumberTextField.isValid()) {

					editFlatWindow.close();
				}

				try {
					flatService.updateFlatTableByFlatId(Integer.parseInt(idFlatTableFromSelectedRow), updateflat);
					updateBuildingInfoPage();
					editFlatWindow.close();
					editFlatButton.setEnabled(false);

				} catch (SQLException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}

			});

			editFlatWindowButtonHorizontalLayout.addComponent(windowEditFlatButton);

			Button windowCancelButton = new Button("Cancel");
			windowCancelButton.setSizeFull();
			windowCancelButton.addClickListener(c -> {
				editFlatWindow.close();
			});
			editFlatWindowButtonHorizontalLayout.addComponent(windowCancelButton);

			editFlatWindow.center();

			UI.getCurrent().addWindow(editFlatWindow);

		});

		return editFlatButton;
	}

	public void updateBuildingInfoPage() throws SQLException {

		buildingInfoGridLayout.removeComponent(flatGrid);
		buildingInfoGridLayout.removeComponent(0, 14);
		flatGrid = flatGridFlatInfoView(flatService.getFlatsFromFlatTable());
		buildingInfoGridLayout.addComponent(flatGrid, 0, 0, 0, 13);
		buildingInfoGridLayout.addComponent(flatButtonHorizontalLayout(), 0, 14, 0, 14);

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

				updateBuildingInfoPage();

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
