package com.example.Vaadin7_Builder.view.BuildingInfoViewServises;

import java.text.DecimalFormat;
import java.util.Iterator;
import java.util.List;

import com.example.Vaadin7_Builder.model.Flat;
import com.example.Vaadin7_Builder.service.FlatService;
import com.vaadin.ui.Grid;
import com.vaadin.ui.Grid.FooterRow;

public class UpdateGrid {

	FlatService flatService = new FlatService();

	private DecimalFormat decimalFormat = new DecimalFormat("0.00");

	public void updateFlatGrid(List<Flat> flatList, Grid flatGrid, FooterRow flatGridFooterRow) {

		flatGrid.getContainerDataSource().removeAllItems();

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

		flatGrid.removeFooterRow(0);
		flatGridFooterRow = flatGrid.prependFooterRow();
		flatGridFooterRow.getCell("flatArea")
				.setText("Total: " + decimalFormat.format(flatArea).replace(",", ".") + " m2");

	}

}
