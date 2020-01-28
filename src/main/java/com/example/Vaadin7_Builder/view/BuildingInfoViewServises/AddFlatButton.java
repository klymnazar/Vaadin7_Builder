package com.example.Vaadin7_Builder.view.BuildingInfoViewServises;

import java.sql.SQLException;

import com.example.Vaadin7_Builder.model.Flat;
import com.example.Vaadin7_Builder.service.FlatService;
import com.vaadin.ui.Button;
import com.vaadin.ui.ComboBox;
import com.vaadin.ui.Grid;
import com.vaadin.ui.Grid.FooterRow;
import com.vaadin.ui.TextField;

public class AddFlatButton extends AddEditFlatWindow {

	FlatService flatService = new FlatService();

	public Button addFlatButton(Grid flatGrid, FooterRow flatGridFooterRow) {

		Button addFlatButton = new Button("Add Flat");

		addFlatButton.addClickListener(e -> {

			addEditFlatWindow("Add", flatGrid, flatGridFooterRow, 0, null);

		});

		addFlatButton.setSizeFull();

		return addFlatButton;
	}

	@Override
	public void createFlat(Flat flat) {
		try {
			flatService.createFlat(flat);
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}

	@Override
	public void updateFlat(int idFlatTableFromSelectedRow, Flat flat) {
		// TODO Auto-generated method stub

	}

	@Override
	public void setFlatValue(Flat selectedFlat, ComboBox buildingCorpsComboBox, ComboBox flatRoomsComboBox,
			ComboBox flatFloorComboBox, TextField flatNumberTextField, TextField flatAreaTextField) {
		// TODO Auto-generated method stub

	}

}
