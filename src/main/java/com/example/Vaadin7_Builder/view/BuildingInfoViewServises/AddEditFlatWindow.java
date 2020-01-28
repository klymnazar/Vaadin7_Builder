package com.example.Vaadin7_Builder.view.BuildingInfoViewServises;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import com.example.Vaadin7_Builder.model.Flat;
import com.example.Vaadin7_Builder.service.FlatService;
import com.vaadin.data.validator.DoubleValidator;
import com.vaadin.data.validator.IntegerValidator;
import com.vaadin.ui.Button;
import com.vaadin.ui.ComboBox;
import com.vaadin.ui.FormLayout;
import com.vaadin.ui.Grid;
import com.vaadin.ui.HorizontalLayout;
import com.vaadin.ui.TextField;
import com.vaadin.ui.UI;
import com.vaadin.ui.VerticalLayout;
import com.vaadin.ui.Window;
import com.vaadin.ui.Grid.FooterRow;

public abstract class AddEditFlatWindow {

	FlatService flatService = new FlatService();

	UpdateGrid updateGrid = new UpdateGrid();

	public Window addEditFlatWindow(String buttonCaption, Grid flatGrid, FooterRow flatGridFooterRow,
			int idFlatTableFromSelectedRow, Flat selectedFlat) {

		Window addFlatWindow = new Window(buttonCaption + " Flat");
		VerticalLayout addFlatWindowVerticalLayout = new VerticalLayout();
		addFlatWindowVerticalLayout.setMargin(true);
		addFlatWindowVerticalLayout.setSpacing(true);

		addFlatWindow.setContent(addFlatWindowVerticalLayout);

		FormLayout addFlatFormLayout = new FormLayout();
		addFlatFormLayout.setMargin(true);
		addFlatFormLayout.setSpacing(true);
		addFlatWindowVerticalLayout.addComponent(addFlatFormLayout);

		ComboBox buildingCorpsComboBox = new ComboBox("Building Corps");
		buildingCorpsComboBox.setNullSelectionAllowed(false);

		List<Flat> corpsList = new ArrayList<>();

		try {

			corpsList = flatService.getCorpsFromSettingsTable();

			Iterator<Flat> itr = corpsList.iterator();
			while (itr.hasNext()) {
				Flat flatFromList = itr.next();

				buildingCorpsComboBox.addItem(flatFromList.getBuildingCorps());

			}
		} catch (NumberFormatException | SQLException e4) {
			// TODO Auto-generated catch block
			e4.printStackTrace();
		}

//		buildingCorpsComboBox.addItems("1/А", "2/Б", "3/В", "4/Г", "5/Д", "6/Е", "7/Є", "8/Ж");
		addFlatFormLayout.addComponent(buildingCorpsComboBox);

		ComboBox flatRoomsComboBox = new ComboBox("Flat Rooms");
		flatRoomsComboBox.addItems(1, 2, 3, 4, 5);
		flatRoomsComboBox.setNullSelectionAllowed(false);
		addFlatFormLayout.addComponent(flatRoomsComboBox);

		ComboBox flatFloorComboBox = new ComboBox("Flat Floor");
		flatFloorComboBox.addItems(1, 2, 3, 4, 5, 6, 7, 8, 9, 10, 11, 12, 13, 14, 15, 16);
		flatFloorComboBox.setNullSelectionAllowed(false);
		addFlatFormLayout.addComponent(flatFloorComboBox);

		TextField flatNumberTextField = new TextField("Flat Number");
		addFlatFormLayout.addComponent(flatNumberTextField);

		TextField flatAreaTextField = new TextField("Flat Area");
		addFlatFormLayout.addComponent(flatAreaTextField);

		HorizontalLayout windowButtonHorizontalLayout = new HorizontalLayout();
		windowButtonHorizontalLayout.setSizeFull();
		windowButtonHorizontalLayout.setSpacing(true);
		addFlatWindowVerticalLayout.addComponent(windowButtonHorizontalLayout);

		setFlatValue(selectedFlat, buildingCorpsComboBox, flatRoomsComboBox, flatFloorComboBox, flatNumberTextField,
				flatAreaTextField);

		Button addWindowButton = new Button(buttonCaption);
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

			createFlat(flat);

			updateFlat(idFlatTableFromSelectedRow, flat);

			List<Flat> flatList = new ArrayList<>();
			try {
				flatList = flatService.getFlatsFromOrderedFlatTable();
			} catch (SQLException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}

			updateGrid.updateFlatGrid(flatList, flatGrid, flatGridFooterRow);

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

		return addFlatWindow;
	}

	public abstract void createFlat(Flat flat);

	public abstract void updateFlat(int idFlatTableFromSelectedRow, Flat selectedFlat);

	public abstract void setFlatValue(Flat selectedFlat, ComboBox buildingCorpsComboBox, ComboBox flatRoomsComboBox,
			ComboBox flatFloorComboBox, TextField flatNumberTextField, TextField flatAreaTextField);

}
