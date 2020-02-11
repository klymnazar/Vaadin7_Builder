package com.example.Vaadin7_Builder.view.AccountingViewServises;

import java.sql.SQLException;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import com.example.Vaadin7_Builder.model.Flat;
import com.example.Vaadin7_Builder.service.FlatService;
import com.vaadin.ui.Button;
import com.vaadin.ui.DateField;
import com.vaadin.ui.FormLayout;
import com.vaadin.ui.Grid;
import com.vaadin.ui.Grid.FooterRow;
import com.vaadin.ui.Grid.SingleSelectionModel;
import com.vaadin.ui.HorizontalLayout;
import com.vaadin.ui.Panel;
import com.vaadin.ui.TextArea;
import com.vaadin.ui.TextField;
import com.vaadin.ui.UI;
import com.vaadin.ui.VerticalLayout;
import com.vaadin.ui.Window;

public class IncomeSumButton extends IncomeExpensesInfo {
//дохід

	FlatService flatService = new FlatService();

	private DecimalFormat decimalFormat = new DecimalFormat("0.00");

	@Override
	public Grid infoGrid(List<Flat> flatList) throws SQLException {

		Grid incomeWindowInfoGrid = new Grid();
		incomeWindowInfoGrid.setSizeFull();

		incomeWindowInfoGrid.addColumn("idFlatTable", Integer.class);
		incomeWindowInfoGrid.addColumn("flatContractDate", String.class);
		incomeWindowInfoGrid.addColumn("buildingCorps", String.class);
		incomeWindowInfoGrid.addColumn("flatNumber", Integer.class);
		incomeWindowInfoGrid.addColumn("flatArea", Double.class);
		incomeWindowInfoGrid.addColumn("buyer", String.class);
		incomeWindowInfoGrid.addColumn("flatCost", Integer.class);
		incomeWindowInfoGrid.addColumn("m2_flatCost", Double.class);

		incomeWindowInfoGrid.getColumn("idFlatTable").setHidden(true);

		FooterRow incomeWindowGridFooterRow = incomeWindowInfoGrid.prependFooterRow();
		updateInfoGrid(incomeWindowInfoGrid, flatList);

		return incomeWindowInfoGrid;
	}

	public Button incomeSumButton() throws SQLException {

		List<Flat> selectAllflatsList = flatService.getFlatsFromFlatBuyerTable();

		List<Flat> updateFlatBuyerList = flatService.getFlatsFromFlatBuyerTable();

		Button incomeSumButton = infoButton("Income Sum", "Income Info", selectAllflatsList, updateFlatBuyerList);

		return incomeSumButton;

	}

	@Override
	public List<Flat> expensesWindowCorpsComboBoxInfoByFlatId(int flatId) {

		Flat flatIncomeByFlatId = new Flat();

		List<Flat> flatListIncomeByFlatId = new ArrayList<>();

		try {
			flatIncomeByFlatId = flatService.getFlatByFlatIdFromFlatBuyerTable(flatId);
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		if (flatId == flatIncomeByFlatId.getIdFlatTable()) {

			flatListIncomeByFlatId.add(flatIncomeByFlatId);

		}

		return flatListIncomeByFlatId;

	}

	@Override
	public List<Flat> expensesWindowFlatComboBoxInfoByCorpsAndNumber(String corps, int number) {

		Flat flatFromComboBox = new Flat();
		Flat flatFromBuyerTable = new Flat();

		List<Flat> expensesList = new ArrayList<>();

		try {

			flatFromComboBox = flatService.getFlatByCorpsAndNumberFromFlatTable(corps, number);

			flatFromBuyerTable = flatService.getFlatByFlatIdFromFlatBuyerTable(flatFromComboBox.getIdFlatTable());

			if (flatFromComboBox.getIdFlatTable() == flatFromBuyerTable.getIdFlatTable()) {

				expensesList.add(flatFromBuyerTable);

			}

		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		return expensesList;

	}

	@Override
	public void updateInfoGrid(Grid infoGrid, List<Flat> flatList) {

		infoGrid.getContainerDataSource().removeAllItems();

		FooterRow incomeWindowGridFooterRow = infoGrid.getFooterRow(0);

		try {
			flatService.getFlatsFromFlatBuyerTable();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		double flatArea = 0;
		int flatCost = 0;
		double flatCostM2 = 0;

		try {

			Iterator<Flat> itr = flatList.iterator();
			while (itr.hasNext()) {
				Flat flatFromList = itr.next();

				Flat flat;
				try {
					flat = flatService.getFlatByFlatIdFromFlatTable(flatFromList.getIdFlatTable());
					infoGrid.addRow(flat.getIdFlatTable(),
							flatService.dateFormatForGrid(flatFromList.getFlatContractDate()), flat.getBuildingCorps(),
							flat.getFlatNumber(), flat.getFlatArea(),
							flatFromList.getFlatBuyerFirstname() + " " + flatFromList.getFlatBuyerLastname() + " "
									+ flatFromList.getFlatBuyerSurname(),
							flatFromList.getFlatCost(), flatFromList.getFlatCost() / flat.getFlatArea());

					flatArea = flatArea + flat.getFlatArea();
					flatCost = flatCost + flatFromList.getFlatCost();
					flatCostM2 = flatCost / flatArea;
				} catch (SQLException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}

			}
		} catch (NumberFormatException e4) {
			// TODO Auto-generated catch block
			e4.printStackTrace();
		}

		incomeWindowGridFooterRow.getCell("flatNumber").setText("Total: ");
		incomeWindowGridFooterRow.getCell("flatArea").setText(decimalFormat.format(flatArea).replace(",", "."));
		incomeWindowGridFooterRow.getCell("flatCost").setText(decimalFormat.format(flatCost).replace(",", "."));
		incomeWindowGridFooterRow.getCell("m2_flatCost").setText(decimalFormat.format(flatCostM2).replace(",", "."));

	}

	@Override
	public Button addEditButton(String buttonCaption, Grid flatGrid, Button deleteButton) {

		Button editIncomeSumButton = new Button(buttonCaption);
		editIncomeSumButton.setSizeFull();

		editIncomeSumButton.setEnabled(false);

		
//		if (flatGrid.isSelected(flatGrid)) {
//			editIncomeSumButton.setEnabled(true);
//			
//		} else {
//			editIncomeSumButton.setEnabled(false);
//		}
//		
//		
//		
//		
//		
//		
		flatGrid.addSelectionListener(SelectionEvent -> {

			editIncomeSumButton.setEnabled(true);

		});

		editIncomeSumButton.addClickListener(e -> {

			Object selected = ((SingleSelectionModel) flatGrid.getSelectionModel()).getSelectedRow();
			String idFlatTableFromSelectedRowS = flatGrid.getContainerDataSource().getItem(selected)
					.getItemProperty("idFlatTable").getValue().toString();
			int idFlatTableFromSelectedRow = Integer.parseInt(idFlatTableFromSelectedRowS);

			Flat selectedFlat = new Flat();
			try {
				selectedFlat = flatService.getFlatByFlatIdFromFlatBuyerTable(idFlatTableFromSelectedRow);
			} catch (SQLException e2) {
				// TODO Auto-generated catch block
				e2.printStackTrace();
			}

			Window editIncomeSumWindow = new Window("Edit Buyer");
			editIncomeSumWindow.setWidth("450px");
			editIncomeSumWindow.setHeight("600px");

			VerticalLayout editIncomeSumWindowVerticalLayout = new VerticalLayout();
			editIncomeSumWindowVerticalLayout.setSizeFull();
			editIncomeSumWindowVerticalLayout.setMargin(true);
			editIncomeSumWindowVerticalLayout.setSpacing(true);
			editIncomeSumWindow.setContent(editIncomeSumWindowVerticalLayout);

			Panel editIncomeSumBuyerInfoPanel = new Panel("Buyer Info");
			editIncomeSumBuyerInfoPanel.setSizeFull();

			FormLayout editIncomeSumWindowFormLayout = new FormLayout();
			editIncomeSumWindowFormLayout.setMargin(true);
			editIncomeSumWindowFormLayout.setSizeFull();

			DateField saleFlatDateField = new DateField("Contract Date");
			saleFlatDateField.setValue(new java.util.Date());
			saleFlatDateField.setDateFormat("dd.MM.yyyy");
			saleFlatDateField.setValue(selectedFlat.getFlatContractDate());
			editIncomeSumWindowFormLayout.addComponent(saleFlatDateField);

			TextField lastNameTextField = new TextField("Last Name");
			lastNameTextField.setValue(selectedFlat.getFlatBuyerLastname());
			editIncomeSumWindowFormLayout.addComponent(lastNameTextField);

			TextField firstNameTextField = new TextField("First Name");
			firstNameTextField.setValue(selectedFlat.getFlatBuyerFirstname());
			editIncomeSumWindowFormLayout.addComponent(firstNameTextField);

			TextField surNameTextField = new TextField("Surname");
			surNameTextField.setValue(selectedFlat.getFlatBuyerSurname());
			editIncomeSumWindowFormLayout.addComponent(surNameTextField);

			TextField costTextField = new TextField("Cost, $");
			costTextField.setValue("" + selectedFlat.getFlatCost());
			editIncomeSumWindowFormLayout.addComponent(costTextField);

			TextArea notesTextArea = new TextArea("Notes");
			notesTextArea.setHeight("60px");
			notesTextArea.setValue(selectedFlat.getFlatNotes());
			editIncomeSumWindowFormLayout.addComponent(notesTextArea);

			editIncomeSumBuyerInfoPanel.setContent(editIncomeSumWindowFormLayout);

			editIncomeSumWindowVerticalLayout.addComponent(editIncomeSumBuyerInfoPanel);
			editIncomeSumWindowVerticalLayout.setExpandRatio(editIncomeSumBuyerInfoPanel, 1.0f);

			HorizontalLayout editIncomeSumWindowButtonHorizontalLayout = new HorizontalLayout();
			editIncomeSumWindowButtonHorizontalLayout.setHeight("50px");
			editIncomeSumWindowButtonHorizontalLayout.setWidth("100%");
			editIncomeSumWindowButtonHorizontalLayout.setSpacing(true);
			editIncomeSumWindowVerticalLayout.addComponent(editIncomeSumWindowButtonHorizontalLayout);

			Button editIncomeSumWindowButton = new Button("Edit Window");
			editIncomeSumWindowButton.setSizeFull();
			editIncomeSumWindowButton.addClickListener(es -> {

				Flat saleBuyerFlat = new Flat();

				saleBuyerFlat.setIdFlatTable(idFlatTableFromSelectedRow);

				saleBuyerFlat.setFlatBuyerFirstname(firstNameTextField.getValue());
				saleBuyerFlat.setFlatBuyerLastname(lastNameTextField.getValue());
				saleBuyerFlat.setFlatBuyerSurname(surNameTextField.getValue());
				saleBuyerFlat.setFlatCost(Integer.parseInt(costTextField.getValue()));
				saleBuyerFlat.setFlatContractDate(saleFlatDateField.getValue());
				saleBuyerFlat.setFlatNotes(notesTextArea.getValue());

				try {

					flatService.updateFlatBuyerTableByFlatId(idFlatTableFromSelectedRow, saleBuyerFlat);

					List<Flat> updateList = flatService.getFlatsFromFlatBuyerTable();

					updateInfoGrid(flatGrid, updateList);

					deleteButton.setEnabled(false);
					editIncomeSumButton.setEnabled(false);

					
					
					
					
					editIncomeSumWindow.close();
				} catch (SQLException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}

			});

			editIncomeSumWindowButtonHorizontalLayout.addComponent(editIncomeSumWindowButton);

			Button cancelWindowButton = new Button("Cancel");
			cancelWindowButton.setSizeFull();

			cancelWindowButton.addClickListener(e1 -> {
				editIncomeSumWindow.close();
			});

			editIncomeSumWindowButtonHorizontalLayout.addComponent(cancelWindowButton);

			editIncomeSumWindow.center();

			UI.getCurrent().addWindow(editIncomeSumWindow);

		});

		return editIncomeSumButton;
	}

	@Override
	public void deleteRow(Grid infoGrid) {
		// TODO Auto-generated method stub
//		infoGrid.addSelectionListener(select -> {
			
		
		
			Object selected = ((SingleSelectionModel) infoGrid.getSelectionModel()).getSelectedRow();
			String idExpensesTableFromSelectedRow = infoGrid.getContainerDataSource().getItem(selected)
					.getItemProperty("idFlatTable").getValue().toString();
			int idExpensesTableIntFromSelectedRow = Integer.parseInt(idExpensesTableFromSelectedRow);

		
		
		try {
//			flatService.deleteFlatByIdFromExpensesTable(idExpensesTableIntFromSelectedRow);
			
			flatService.deleteFlatByIdFromBuyerTable(idExpensesTableIntFromSelectedRow);
			
			List<Flat> updateList = flatService.getFlatsFromFlatBuyerTable();
			updateInfoGrid(infoGrid, updateList);
			
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
//		});
		
		
	}

}
