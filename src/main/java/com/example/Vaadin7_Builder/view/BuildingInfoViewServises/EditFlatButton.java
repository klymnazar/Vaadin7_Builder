package com.example.Vaadin7_Builder.view.BuildingInfoViewServises;

import java.sql.SQLException;

import com.example.Vaadin7_Builder.model.Flat;
import com.example.Vaadin7_Builder.service.FlatService;
import com.vaadin.ui.Button;
import com.vaadin.ui.ComboBox;
import com.vaadin.ui.Grid;
import com.vaadin.ui.TextField;
import com.vaadin.ui.Grid.FooterRow;
import com.vaadin.ui.Grid.SingleSelectionModel;

public class EditFlatButton extends AddEditFlatWindow {

	FlatService flatService = new FlatService();

	private String flatSetFree = "";

	public Button editFlatButton(Grid flatGrid, FooterRow flatGridFooterRow) {

		Button editFlatButton = new Button("Edit Flat");
		editFlatButton.setSizeFull();
		editFlatButton.setEnabled(false);

		flatGrid.addSelectionListener(SelectionEvent -> {

			Object selected = ((SingleSelectionModel) flatGrid.getSelectionModel()).getSelectedRow();
			String idFlatTableFromSelectedRowS = flatGrid.getContainerDataSource().getItem(selected)
					.getItemProperty("idFlatTable").getValue().toString();

			String flatSetFromFlatGrid;
			try {

				flatSetFromFlatGrid = flatService
						.getFlatByFlatIdFromFlatTable(Integer.parseInt(idFlatTableFromSelectedRowS)).getFlatSet();

				if (flatSetFromFlatGrid.equals(flatSetFree)) {
					editFlatButton.setEnabled(true);

				} else {
					editFlatButton.setEnabled(false);
				}

			} catch (SQLException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}

		});

		editFlatButton.addClickListener(e -> {

			Object selected = ((SingleSelectionModel) flatGrid.getSelectionModel()).getSelectedRow();
			String idFlatTableFromSelectedRowS = flatGrid.getContainerDataSource().getItem(selected)
					.getItemProperty("idFlatTable").getValue().toString();

			Flat selectedFlat = new Flat();
			try {
				selectedFlat = flatService.getFlatByFlatIdFromFlatTable(Integer.parseInt(idFlatTableFromSelectedRowS));
			} catch (NumberFormatException | SQLException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}

			addEditFlatWindow("Edit", flatGrid, flatGridFooterRow, Integer.parseInt(idFlatTableFromSelectedRowS),
					selectedFlat);

			editFlatButton.setEnabled(false);

		});

		return editFlatButton;
	}

	@Override
	public void createFlat(Flat flat) {
		// TODO Auto-generated method stub

	}

	@Override
	public void updateFlat(int idFlatTableFromSelectedRow, Flat flat) {
		try {
			flatService.updateFlatTableByFlatId(idFlatTableFromSelectedRow, flat);
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}

	@Override
	public void setFlatValue(Flat selectedFlat, ComboBox buildingCorpsComboBox, ComboBox flatRoomsComboBox,
			ComboBox flatFloorComboBox, TextField flatNumberTextField, TextField flatAreaTextField) {

		buildingCorpsComboBox.setValue(selectedFlat.getBuildingCorps());

		flatRoomsComboBox.setValue(selectedFlat.getFlatRooms());

		flatFloorComboBox.setValue(selectedFlat.getFlatFloor());

		flatNumberTextField.setValue("" + selectedFlat.getFlatNumber());

		flatAreaTextField.setValue("" + selectedFlat.getFlatArea());

	}

}