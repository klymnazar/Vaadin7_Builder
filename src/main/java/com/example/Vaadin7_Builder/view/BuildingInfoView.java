package com.example.Vaadin7_Builder.view;

import java.sql.SQLException;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import com.example.Vaadin7_Builder.model.Flat;
import com.example.Vaadin7_Builder.service.FlatService;
import com.example.Vaadin7_Builder.view.BuildingInfoViewServises.AddFlatButton;
import com.example.Vaadin7_Builder.view.BuildingInfoViewServises.EditFlatButton;
import com.example.Vaadin7_Builder.view.BuildingInfoViewServises.UpdateGrid;
import com.vaadin.data.validator.DoubleValidator;
import com.vaadin.data.validator.IntegerValidator;
import com.vaadin.navigator.View;
import com.vaadin.navigator.ViewChangeListener.ViewChangeEvent;
import com.vaadin.ui.Button;
import com.vaadin.ui.CheckBox;
import com.vaadin.ui.Grid;
import com.vaadin.ui.Grid.FooterRow;
import com.vaadin.ui.Grid.SingleSelectionModel;
import com.vaadin.ui.HorizontalLayout;
import com.vaadin.ui.Panel;
import com.vaadin.ui.UI;
import com.vaadin.ui.VerticalLayout;

public class BuildingInfoView extends VerticalLayout implements View {

	AddFlatButton addFlatButton = new AddFlatButton();

	EditFlatButton editFlatButton = new EditFlatButton();

	UpdateGrid updateGrid = new UpdateGrid();

	private FlatService flatService = new FlatService();

	private String flatSetFree = "";

	private DecimalFormat decimalFormat = new DecimalFormat("0.00");

	public BuildingInfoView() throws SQLException {

		setSizeFull();
		setMargin(true);
		setSpacing(true);

		HorizontalLayout buildingInfoHorizontalLayout = new HorizontalLayout();
		buildingInfoHorizontalLayout.setSizeFull();
		buildingInfoHorizontalLayout.setSpacing(true);
		addComponent(buildingInfoHorizontalLayout);
		setExpandRatio(buildingInfoHorizontalLayout, 1.0f);

		Panel buildingInfoGridPanel = new Panel("Building Info");
		buildingInfoGridPanel.setSizeFull();
		buildingInfoHorizontalLayout.addComponent(buildingInfoGridPanel);
		buildingInfoHorizontalLayout.setExpandRatio(buildingInfoGridPanel, 1.0f);

		Grid flatGrid = new Grid();
		FooterRow flatGridFooterRow = flatGrid.prependFooterRow();

		List<Flat> flatList = new ArrayList<>();
		try {
			flatList = flatService.getFlatsFromOrderedFlatTable();
		} catch (SQLException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}

		flatGrid = flatGridFlatInfoView(flatList, flatGridFooterRow);

		buildingInfoGridPanel.setContent(flatGrid);

		buildingInfoHorizontalLayout.addComponent(corpsPanel(flatGrid, flatGridFooterRow));

		HorizontalLayout buildingInfobuttonHorizontalLayout = new HorizontalLayout();
		buildingInfobuttonHorizontalLayout.setWidth("100%");
		buildingInfobuttonHorizontalLayout.setHeight("50px");
		buildingInfobuttonHorizontalLayout.setSpacing(true);
		addComponent(buildingInfobuttonHorizontalLayout);

		buildingInfobuttonHorizontalLayout.addComponent(addFlatButton.addFlatButton(flatGrid, flatGridFooterRow));

		buildingInfobuttonHorizontalLayout.addComponent(editFlatButton.editFlatButton(flatGrid, flatGridFooterRow));

		buildingInfobuttonHorizontalLayout.addComponent(deleteFlatButton(flatGrid, flatGridFooterRow));

		buildingInfobuttonHorizontalLayout.addComponent(cancelButton());

	}

	public Grid flatGridFlatInfoView(List<Flat> flatList, FooterRow flatGridFooterRow) throws SQLException {

		Grid flatGrid = new Grid();
		flatGridFooterRow = flatGrid.prependFooterRow();

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

		updateGrid.updateFlatGrid(flatList, flatGrid, flatGridFooterRow);

		return flatGrid;

	}

	public Panel corpsPanel(Grid flatGrid, FooterRow flatGridFooterRow) {

		Panel corpsPanel = new Panel("Corps Info");
		corpsPanel.setHeight("100%");
		corpsPanel.setWidth("200px");

		VerticalLayout corpsPanelVerticalLayout = new VerticalLayout();
		corpsPanelVerticalLayout.setMargin(true);
		corpsPanelVerticalLayout.setSpacing(true);
		corpsPanelVerticalLayout.setSizeFull();

		corpsPanel.setContent(corpsPanelVerticalLayout);

		List<Flat> corpsList = new ArrayList<>();

		try {

			corpsList = flatService.getCorpsFromSettingsTable();

			Iterator<Flat> itr = corpsList.iterator();
			while (itr.hasNext()) {
				Flat flatFromList = itr.next();

				String corps = flatFromList.getBuildingCorps();

				CheckBox corpsInfoCheckBox = new CheckBox(corps + " Corps Info");
				corpsPanelVerticalLayout.addComponent(corpsInfoCheckBox);

				corpsInfoCheckBox.addValueChangeListener(event -> {
					corpsInfoCheckBoxChangeListener(corps, corpsInfoCheckBox, flatGrid, flatGridFooterRow);
				});

			}
		} catch (NumberFormatException | SQLException e4) {
			// TODO Auto-generated catch block
			e4.printStackTrace();
		}

		return corpsPanel;
	}

	private void corpsInfoCheckBoxChangeListener(String corps, CheckBox corpsInfoCheckBox, Grid flatGrid,
			FooterRow flatGridFooterRow) {
		if (corpsInfoCheckBox.isEmpty()) {

			List<Flat> flatList = new ArrayList<>();
			try {
				flatList = flatService.getFlatsFromOrderedFlatTable();
			} catch (SQLException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}

			updateGrid.updateFlatGrid(flatList, flatGrid, flatGridFooterRow);
		} else {

			List<Flat> flatList = new ArrayList<>();
			try {
				flatList = flatService.getFlatsByCorpsFromFlatTable(corps);
			} catch (SQLException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}

			updateGrid.updateFlatGrid(flatList, flatGrid, flatGridFooterRow);

		}
	}

	public Button deleteFlatButton(Grid flatGrid, FooterRow flatGridFooterRow) throws SQLException {

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
			} catch (NumberFormatException | SQLException e2) {
				// TODO Auto-generated catch block
				e2.printStackTrace();
			}

			deleteFlatButton.setEnabled(false);

			List<Flat> flatList = new ArrayList<>();
			try {
				flatList = flatService.getFlatsFromOrderedFlatTable();
			} catch (SQLException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}

			updateGrid.updateFlatGrid(flatList, flatGrid, flatGridFooterRow);

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