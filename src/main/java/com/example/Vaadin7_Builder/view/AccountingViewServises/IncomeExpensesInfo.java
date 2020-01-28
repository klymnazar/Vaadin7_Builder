package com.example.Vaadin7_Builder.view.AccountingViewServises;

import java.sql.SQLException;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import com.example.Vaadin7_Builder.model.Flat;
import com.example.Vaadin7_Builder.service.FlatService;
import com.vaadin.ui.Alignment;
import com.vaadin.ui.Button;
import com.vaadin.ui.ComboBox;
import com.vaadin.ui.Grid;
import com.vaadin.ui.HorizontalLayout;
import com.vaadin.ui.Panel;
import com.vaadin.ui.UI;
import com.vaadin.ui.VerticalLayout;
import com.vaadin.ui.Window;
import com.vaadin.ui.Grid.FooterRow;
import com.vaadin.ui.Grid.HeaderRow;

public abstract class IncomeExpensesInfo {

	FlatService flatService = new FlatService();

	private DecimalFormat decimalFormat = new DecimalFormat("0.00");

	private String generalExpenses = "General";
	private String bcExpenses = "B.C.";
	private String cmExpenses = "C.M.";

	private double bcGeneralExpenses = 0.5;
	private double cmGeneralExpenses = 1 - bcGeneralExpenses;

	abstract public Grid infoGrid(List<Flat> flatList) throws SQLException;

	abstract public List<Flat> expensesWindowCorpsComboBoxInfoByFlatId(int flatId);

	abstract public List<Flat> expensesWindowFlatComboBoxInfoByCorpsAndNumber(String corps, int number);

	public Button infoButton(String buttonCaption, String windowCaption, List<Flat> selectAllflatsList) {

//			expenses sum сума витрат
//			income sum сума доходу
//			available sum оступна сума

		Button infoButton = new Button(buttonCaption);
		infoButton.setSizeFull();

		infoButton.addClickListener(e -> {

			Window infoWindow = new Window(windowCaption);
			infoWindow.setHeight("600px");
			infoWindow.setWidth("850px");

			VerticalLayout expensesWindowVerticalLayout = new VerticalLayout();
			expensesWindowVerticalLayout.setSpacing(true);
			expensesWindowVerticalLayout.setMargin(true);
			expensesWindowVerticalLayout.setSizeFull();
			infoWindow.setContent(expensesWindowVerticalLayout);

			HorizontalLayout expensesWindowFlatHorizontalLayout = new HorizontalLayout();
			expensesWindowFlatHorizontalLayout.setSpacing(true);
			expensesWindowFlatHorizontalLayout.setHeight("65px");
			expensesWindowFlatHorizontalLayout.setWidth("100%");
			expensesWindowVerticalLayout.addComponent(expensesWindowFlatHorizontalLayout);

			HorizontalLayout expensesWindowinfoPanelHorizontalLayout = new HorizontalLayout();
			expensesWindowinfoPanelHorizontalLayout.setSizeFull();
			expensesWindowVerticalLayout.addComponent(expensesWindowinfoPanelHorizontalLayout);
			expensesWindowVerticalLayout.setExpandRatio(expensesWindowinfoPanelHorizontalLayout, 1.0f);

			HorizontalLayout expensesWindowButtonHorizontalLayout = new HorizontalLayout();
			expensesWindowButtonHorizontalLayout.setSpacing(true);
			expensesWindowButtonHorizontalLayout.setHeight("40px");
			expensesWindowButtonHorizontalLayout.setWidth("100%");
			expensesWindowVerticalLayout.addComponent(expensesWindowButtonHorizontalLayout);

			Panel expensesWindowInfoPanel = new Panel(infoWindow.getCaption());
			expensesWindowInfoPanel.setSizeFull();

			try {
				expensesWindowInfoPanel.setContent(infoGrid(selectAllflatsList));
			} catch (SQLException e2) {
				// TODO Auto-generated catch block
				e2.printStackTrace();
			}

			expensesWindowinfoPanelHorizontalLayout.addComponent(expensesWindowInfoPanel);

			Panel expensesWindowPartsPanel = new Panel("Expenses Parts");
			expensesWindowPartsPanel.setHeight("267px");
			expensesWindowPartsPanel.setWidth("100%");

			ComboBox expensesWindowCorpsComboBox = new ComboBox("Building Corps List");
			expensesWindowCorpsComboBox.setNullSelectionAllowed(false);

			List<Flat> corpsList = new ArrayList<>();

			try {

				corpsList = flatService.getCorpsFromSettingsTable();

				Iterator<Flat> itr = corpsList.iterator();
				while (itr.hasNext()) {
					Flat flatFromList = itr.next();

					expensesWindowCorpsComboBox.addItem(flatFromList.getBuildingCorps());

				}
			} catch (NumberFormatException | SQLException e4) {
				// TODO Auto-generated catch block
				e4.printStackTrace();
			}

			expensesWindowCorpsComboBox.addValueChangeListener(select -> {

				expensesWindowVerticalLayout.removeComponent(expensesWindowPartsPanel);

				expensesWindowinfoPanelHorizontalLayout.addComponent(expensesWindowInfoPanel);

				List<Flat> flatListByCorps = new ArrayList<>();

				List<Flat> flatListExpensesByFlatId = new ArrayList<>();

				List<Flat> expensesList = new ArrayList<>();

				try {

					flatListByCorps = flatService
							.getFlatsByCorpsFromFlatTable(expensesWindowCorpsComboBox.getValue().toString());

					Iterator<Flat> itr = flatListByCorps.iterator();
					while (itr.hasNext()) {
						Flat flatFromList = itr.next();

						flatListExpensesByFlatId = expensesWindowCorpsComboBoxInfoByFlatId(
								flatFromList.getIdFlatTable());

						Iterator<Flat> itr1 = flatListExpensesByFlatId.iterator();
						while (itr1.hasNext()) {
							Flat flatFromList1 = itr1.next();

							expensesList.add(flatFromList1);

						}

						expensesWindowInfoPanel.setContent(infoGrid(expensesList));

					}
				} catch (NumberFormatException | SQLException e4) {
					// TODO Auto-generated catch block
					e4.printStackTrace();
				}

			});

			expensesWindowFlatHorizontalLayout.addComponent(expensesWindowCorpsComboBox);
			expensesWindowFlatHorizontalLayout.setComponentAlignment(expensesWindowCorpsComboBox,
					Alignment.BOTTOM_CENTER);

			ComboBox expensesWindowFlatComboBox = new ComboBox("Flat List");
			expensesWindowFlatComboBox.setNullSelectionAllowed(false);

			List<Flat> flatList = new ArrayList<>();

			try {

				flatList = flatService.getFlatsFromOrderedFlatTable();

				Iterator<Flat> itr = flatList.iterator();
				while (itr.hasNext()) {
					Flat flatFromList = itr.next();

					expensesWindowFlatComboBox.addItem(flatFromList.getBuildingCorps() + " - № "
							+ flatFromList.getFlatNumber() + " - " + flatFromList.getFlatArea());

				}
			} catch (NumberFormatException | SQLException e4) {
				// TODO Auto-generated catch block
				e4.printStackTrace();
			}

			expensesWindowFlatComboBox.addValueChangeListener(select -> {

				expensesWindowVerticalLayout.removeComponent(expensesWindowPartsPanel);

				expensesWindowinfoPanelHorizontalLayout.addComponent(expensesWindowInfoPanel);

				String flatListTextFromComboBox = expensesWindowFlatComboBox.getValue().toString();

				String[] flatCorpsNumber = flatListTextFromComboBox.split(" - ");

				String corps = flatCorpsNumber[0];

				String[] flatNumber = flatCorpsNumber[1].split(" ");

				int number = Integer.parseInt(flatNumber[1]);

				List<Flat> expensesList = new ArrayList<>();

				try {
					expensesList = expensesWindowFlatComboBoxInfoByCorpsAndNumber(corps, number);
					expensesWindowInfoPanel.setContent(infoGrid(expensesList));
				} catch (SQLException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}

			});

			expensesWindowFlatHorizontalLayout.addComponent(expensesWindowFlatComboBox);
			expensesWindowFlatHorizontalLayout.setComponentAlignment(expensesWindowFlatComboBox,
					Alignment.BOTTOM_CENTER);

			Button expensesWindowSelectAllButton = new Button("Select All Expenses");
			expensesWindowSelectAllButton.addClickListener(lick -> {

				expensesWindowVerticalLayout.removeComponent(expensesWindowPartsPanel);

				expensesWindowinfoPanelHorizontalLayout.addComponent(expensesWindowInfoPanel);

				try {

					expensesWindowInfoPanel.setContent(infoGrid(selectAllflatsList));

				} catch (SQLException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}

			});

			expensesWindowFlatHorizontalLayout.addComponent(expensesWindowSelectAllButton);
			expensesWindowFlatHorizontalLayout.setComponentAlignment(expensesWindowSelectAllButton,
					Alignment.BOTTOM_CENTER);

			//

			Button expensesPartsButton = new Button("Parts");
			expensesPartsButton.setSizeFull();
			expensesPartsButton.addClickListener(click -> {

				expensesWindowinfoPanelHorizontalLayout.removeComponent(expensesWindowInfoPanel);

				expensesWindowVerticalLayout.addComponent(expensesWindowPartsPanel);

				Grid partsGrid = new Grid();

				partsGrid.setSizeFull();
				partsGrid.addColumn("name", String.class);
				partsGrid.addColumn("flatArea", Double.class);

				partsGrid.addColumn("receivedArea", Double.class);
				partsGrid.addColumn("receivedGeneralArea", Double.class);

				partsGrid.addColumn("residualArea", Double.class);

				double allFlatsArea = 0;
				double receivedArea = 0;
				double receivedGeneralArea = 0;
				double residualArea = 0;

				double BCArea = 0;
				double CMArea = 0;
				double receivedBCGeneralArea = 0;
				double receivedCMGeneralArea = 0;
				double generalArea = 0;

				try {
					allFlatsArea = flatService.sumAllFlatsArea();

					List<Flat> soldedFlatList = new ArrayList<>();

					soldedFlatList = flatService.getFlatsByFlatSetFromDB("Solded");

					Iterator<Flat> itr = soldedFlatList.iterator();
					while (itr.hasNext()) {
						Flat flatFromList = itr.next();

						double m2 = flatService.getFlatByFlatIdFromFlatBuyerTable(flatFromList.getIdFlatTable())
								.getFlatCost()
								/ flatService.getFlatByFlatIdFromFlatTable(flatFromList.getIdFlatTable()).getFlatArea();

						BCArea = BCArea + flatService.getExpensesFlatInfoFromExpensesTableByFlatId(
								flatFromList.getIdFlatTable(), bcExpenses) / m2;

						CMArea = CMArea + flatService.getExpensesFlatInfoFromExpensesTableByFlatId(
								flatFromList.getIdFlatTable(), cmExpenses) / m2;

						generalArea = generalArea + flatService.getExpensesFlatInfoFromExpensesTableByFlatId(
								flatFromList.getIdFlatTable(), generalExpenses) / m2;

						receivedBCGeneralArea = generalArea * bcGeneralExpenses;

						receivedCMGeneralArea = generalArea * cmGeneralExpenses;

					}

					partsGrid.addRow("B.C. 70%", allFlatsArea * 70 / 100, BCArea, receivedBCGeneralArea,
							allFlatsArea * 70 / 100 - BCArea - receivedBCGeneralArea);
					partsGrid.addRow("C.M. 30%", flatService.sumAllFlatsArea() * 30 / 100, CMArea,
							receivedCMGeneralArea, allFlatsArea * 30 / 100 - CMArea - receivedCMGeneralArea);

					receivedGeneralArea = receivedBCGeneralArea + receivedCMGeneralArea;
					receivedArea = BCArea + CMArea;
					residualArea = allFlatsArea - receivedArea - receivedGeneralArea;

				} catch (SQLException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}

				HeaderRow expensesWindowPartsGridHeaderRow2 = partsGrid.prependHeaderRow();
				expensesWindowPartsGridHeaderRow2.getCell("name").setText("Received General Area: ");
				expensesWindowPartsGridHeaderRow2.getCell("flatArea")
						.setText(decimalFormat.format(generalArea).replace(",", "."));
				expensesWindowPartsGridHeaderRow2.getCell("receivedArea").setText("B.C. - " + bcGeneralExpenses * 100
						+ "% = " + decimalFormat.format(receivedBCGeneralArea).replace(",", "."));
				expensesWindowPartsGridHeaderRow2.getCell("receivedGeneralArea")
						.setText("C.M. - " + cmGeneralExpenses * 100 + "% = "
								+ decimalFormat.format(receivedCMGeneralArea).replace(",", "."));

				HeaderRow expensesWindowPartsGridHeaderRow1 = partsGrid.prependHeaderRow();
				expensesWindowPartsGridHeaderRow1.getCell("name").setText("All Flats Area: ");
				expensesWindowPartsGridHeaderRow1.getCell("flatArea")
						.setText(decimalFormat.format(allFlatsArea).replace(",", "."));

				FooterRow expensesWindowPartsGridFooterRow = partsGrid.prependFooterRow();
				expensesWindowPartsGridFooterRow.getCell("name").setText("Total: ");
				expensesWindowPartsGridFooterRow.getCell("flatArea")
						.setText(decimalFormat.format(allFlatsArea).replace(",", "."));
				expensesWindowPartsGridFooterRow.getCell("receivedArea")
						.setText(decimalFormat.format(receivedArea).replace(",", "."));
				expensesWindowPartsGridFooterRow.getCell("receivedGeneralArea")
						.setText(decimalFormat.format(receivedGeneralArea).replace(",", "."));
				expensesWindowPartsGridFooterRow.getCell("residualArea")
						.setText(decimalFormat.format(residualArea).replace(",", "."));

				expensesWindowPartsPanel.setContent(partsGrid);

			});
			expensesWindowButtonHorizontalLayout.addComponent(expensesPartsButton);

			Button expensesCancelButton = new Button("Cancel");
			expensesCancelButton.setSizeFull();
			expensesCancelButton.addClickListener(click -> {
				infoWindow.close();
			});

			expensesWindowButtonHorizontalLayout.addComponent(expensesCancelButton);

			infoWindow.center();

			UI.getCurrent().addWindow(infoWindow);

		});

		return infoButton;
	}

}
