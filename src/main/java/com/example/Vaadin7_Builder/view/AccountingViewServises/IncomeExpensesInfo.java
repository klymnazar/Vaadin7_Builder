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
import com.vaadin.ui.Grid.SingleSelectionModel;

public abstract class IncomeExpensesInfo {


	FlatService flatService = new FlatService();

	private DecimalFormat decimalFormat = new DecimalFormat("0.00");

	private String generalExpenses = "General";
	private String bcExpenses = "B.C.";
	private String cmExpenses = "C.M.";

	private double bcGeneralExpenses = 0.5;
	private double cmGeneralExpenses = 1 - bcGeneralExpenses;

	abstract public Grid infoGrid(List<Flat> flatList) throws SQLException;

	abstract public void updateInfoGrid(Grid infoGrid, List<Flat> flatList);

	abstract public List<Flat> expensesWindowCorpsComboBoxInfoByFlatId(int flatId);

	abstract public List<Flat> expensesWindowFlatComboBoxInfoByCorpsAndNumber(String corps, int number);

	abstract public void deleteRow(Grid infoGrid);
	
//	abstract public void setExpensesValuesBySelectedRow(Grid infoGrid, DateField expensesDateField, TextField expensesSumTextField, ComboBox expensesCategoryComboBox, ComboBox expensesValueComboBox, TextArea expensesValueTextArea);

//	abstract public List<Flat> expensesWindowAllExpenses();

	abstract public Button addEditButton(String buttonCaption, Grid flatGrid, Button deleteButton);

	public Button infoButton(String buttonCaption, String windowCaption, List<Flat> selectAllflatsList,
			List<Flat> updateFlatList) {

//			expenses sum сума витрат
//			income sum сума доходу
//			available sum оступна сума

		Button infoButton = new Button(buttonCaption);
		infoButton.setSizeFull();

		Window infoWindow = new Window(windowCaption);
		infoWindow.setHeight("600px");
		infoWindow.setWidth("850px");

		infoButton.addClickListener(e -> {

			Grid infoGrid = new Grid();

//			Flat flat = new Flat();

			try {
				infoGrid = infoGrid(selectAllflatsList);

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

				expensesWindowInfoPanel.setContent(infoGrid);

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

				Button expensesWindowSelectAllButton = new Button("Select All");
				expensesWindowSelectAllButton.setWidth("180px");
				expensesWindowSelectAllButton.addClickListener(lick -> {

					expensesWindowVerticalLayout.removeComponent(expensesWindowPartsPanel);

					expensesWindowinfoPanelHorizontalLayout.addComponent(expensesWindowInfoPanel);

					List<Flat> expensesList = new ArrayList<>();

					try {

						expensesList = selectAllflatsList;

						expensesWindowInfoPanel.setContent(infoGrid(expensesList));

					} catch (SQLException e1) {
						// TODO Auto-generated catch block
						e1.printStackTrace();
					}

				});

				expensesWindowFlatHorizontalLayout.addComponent(expensesWindowSelectAllButton);
				expensesWindowFlatHorizontalLayout.setComponentAlignment(expensesWindowSelectAllButton,
						Alignment.BOTTOM_CENTER);



				
				
//				expensesWindowButtonHorizontalLayout.addComponent(addEditButton("Edit", flatList, infoGrid));
//
//				expensesWindowButtonHorizontalLayout.addComponent(infoWindowDeleteButton(infoGrid, updateFlatList));
				
				Button infoWindowAddEditButton = new Button("Edit");
				Button infoWindowDeleteButton = new Button("Delete");
				
				infoWindowAddEditButton = addEditButton("Edit", infoGrid, infoWindowDeleteButton);
				expensesWindowButtonHorizontalLayout.addComponent(infoWindowAddEditButton);

				
				infoWindowDeleteButton = infoWindowDeleteButton(infoGrid, infoWindowAddEditButton);
				expensesWindowButtonHorizontalLayout.addComponent(infoWindowDeleteButton);
//				expensesWindowButtonHorizontalLayout.addComponent(infoWindowDeleteButton(infoGrid));
				
				
				
				
				
				
//				Button infoWindowAddEditButton = new Button("Edit");
//				Button infoWindowDeleteButton = new Button("Delete");
//				
////				Button infoWindowAddEditButton = new Button("Edit");
//				
//				infoWindowAddEditButton.addClickListener(click -> {
//					
//				});
//				
//				infoWindowAddEditButton = addEditButton("Edit", infoGrid);
//				infoWindowAddEditButton.setEnabled(false);
//				infoWindowDeleteButton.setEnabled(false);
//				expensesWindowButtonHorizontalLayout.addComponent(infoWindowAddEditButton);
//
//				
//				
//				
//				
////				Button infoWindowDeleteButton = new Button("Delete");
//				infoWindowDeleteButton = infoWindowDeleteButton(infoGrid);
//				
//				expensesWindowButtonHorizontalLayout.addComponent(infoWindowDeleteButton);


				
				
				
				
				
				
//				expensesWindowButtonHorizontalLayout.addComponent(addEditButton("Edit", flatList, infoGrid));
//
//				expensesWindowButtonHorizontalLayout.addComponent(infoWindowDeleteButton(infoGrid, updateFlatList));
				
				
				

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
									/ flatService.getFlatByFlatIdFromFlatTable(flatFromList.getIdFlatTable())
											.getFlatArea();

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
					expensesWindowPartsGridHeaderRow2.getCell("receivedArea")
							.setText("B.C. - " + bcGeneralExpenses * 100 + "% = "
									+ decimalFormat.format(receivedBCGeneralArea).replace(",", "."));
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

			} catch (SQLException e2) {
				// TODO Auto-generated catch block
				e2.printStackTrace();
			}

			infoWindow.center();

			UI.getCurrent().addWindow(infoWindow);

		});

		return infoButton;
	}

	private Button infoWindowDeleteButton(Grid infoGrid, Button editButton) {

		Button infoWindowDeleteButton = new Button("Delete");
//		infoWindowDeleteButton.setWidth("180px");
		infoWindowDeleteButton.setSizeFull();
		infoWindowDeleteButton.setEnabled(false);

		infoGrid.addSelectionListener(select -> {

			infoWindowDeleteButton.setEnabled(true);

		});

		infoWindowDeleteButton.addClickListener(click -> {

			deleteRow(infoGrid);
			infoWindowDeleteButton.setEnabled(false);
			editButton.setEnabled(false);
		
//			try {
//				flatService.deleteFlatByIdFromExpensesTable(getIdExpensesTableIntFromSelectedRow(infoGrid));
//			} catch (SQLException e) {
//				// TODO Auto-generated catch block
//				e.printStackTrace();
//			}

		});

//////	
//
//		updateInfoGrid(infoGrid, updateFlatList);
//
////////

		return infoWindowDeleteButton;
	}

//	private int getIdExpensesTableIntFromSelectedRow(Grid infoGrid) {
//		Object selected = ((SingleSelectionModel) infoGrid.getSelectionModel()).getSelectedRow();
//		String idExpensesTableFromSelectedRow = infoGrid.getContainerDataSource().getItem(selected)
//				.getItemProperty("idExpensesTable").getValue().toString();
//		int idExpensesTableIntFromSelectedRow = Integer.parseInt(idExpensesTableFromSelectedRow);
//
//		return idExpensesTableIntFromSelectedRow;
//	}

}

//package com.example.Vaadin7_Builder.view.AccountingViewServises;
//
//import java.sql.SQLException;
//import java.text.DecimalFormat;
//import java.util.ArrayList;
//import java.util.Iterator;
//import java.util.List;
//import com.example.Vaadin7_Builder.model.Flat;
//import com.example.Vaadin7_Builder.service.FlatService;
//import com.vaadin.ui.Alignment;
//import com.vaadin.ui.Button;
//import com.vaadin.ui.ComboBox;
//import com.vaadin.ui.DateField;
//import com.vaadin.ui.FormLayout;
//import com.vaadin.ui.Grid;
//import com.vaadin.ui.HorizontalLayout;
//import com.vaadin.ui.Panel;
//import com.vaadin.ui.TextArea;
//import com.vaadin.ui.TextField;
//import com.vaadin.ui.UI;
//import com.vaadin.ui.VerticalLayout;
//import com.vaadin.ui.Window;
//import com.vaadin.ui.Grid.FooterRow;
//import com.vaadin.ui.Grid.HeaderRow;
//import com.vaadin.ui.Grid.SingleSelectionModel;
//
//public abstract class IncomeExpensesInfo {
//
//
//	AddEditExpensesWindow addEditExpensesWindow = new AddEditExpensesWindow();
//	
//	FlatService flatService = new FlatService();
//
//	private DecimalFormat decimalFormat = new DecimalFormat("0.00");
//
//	private String generalExpenses = "General";
//	private String bcExpenses = "B.C.";
//	private String cmExpenses = "C.M.";
//
//	private double bcGeneralExpenses = 0.5;
//	private double cmGeneralExpenses = 1 - bcGeneralExpenses;
//
//	abstract public Grid infoGrid(List<Flat> flatList) throws SQLException;
//
//	abstract public List<Flat> expensesWindowCorpsComboBoxInfoByFlatId(int flatId);
//
//	abstract public List<Flat> expensesWindowFlatComboBoxInfoByCorpsAndNumber(String corps, int number);
//	
////	abstract public List<Flat> expensesWindowAllExpenses();
//
//	public Button infoButton(String buttonCaption, String windowCaption, List<Flat> selectAllflatsList) {
//
////			expenses sum сума витрат
////			income sum сума доходу
////			available sum оступна сума
//
//		Button infoButton = new Button(buttonCaption);
//		infoButton.setSizeFull();
//
//		Window infoWindow = new Window(windowCaption);
//		infoWindow.setHeight("600px");
//		infoWindow.setWidth("850px");
//		
//		
//		infoButton.addClickListener(e -> {
//
//			Grid infoGrid = new Grid();
//			
//			Flat flat = new Flat();
//
//			try {
//					infoGrid = infoGrid(selectAllflatsList);
//			
//					
//
//					
////					Object selected = ((SingleSelectionModel) infoGrid.getSelectionModel()).getSelectedRow();
////					String idExpensesTableFromSelectedRow = infoGrid.getContainerDataSource().getItem(selected)
////							.getItemProperty("idExpensesTable").getValue().toString();
////					int idExpensesTableIntFromSelectedRow = Integer.parseInt(idExpensesTableFromSelectedRow);
////					
////					
////
////						flat = flatService.getFlatByIdFromExpensesTable(idExpensesTableIntFromSelectedRow);
//
//					
//
//
//					VerticalLayout expensesWindowVerticalLayout = new VerticalLayout();
//					expensesWindowVerticalLayout.setSpacing(true);
//					expensesWindowVerticalLayout.setMargin(true);
//					expensesWindowVerticalLayout.setSizeFull();
//					infoWindow.setContent(expensesWindowVerticalLayout);
//
//					HorizontalLayout expensesWindowFlatHorizontalLayout = new HorizontalLayout();
//					expensesWindowFlatHorizontalLayout.setSpacing(true);
//					expensesWindowFlatHorizontalLayout.setHeight("65px");
//					expensesWindowFlatHorizontalLayout.setWidth("100%");
//					expensesWindowVerticalLayout.addComponent(expensesWindowFlatHorizontalLayout);
//
//					HorizontalLayout expensesWindowinfoPanelHorizontalLayout = new HorizontalLayout();
//					expensesWindowinfoPanelHorizontalLayout.setSizeFull();
//					expensesWindowVerticalLayout.addComponent(expensesWindowinfoPanelHorizontalLayout);
//					expensesWindowVerticalLayout.setExpandRatio(expensesWindowinfoPanelHorizontalLayout, 1.0f);
//
//					HorizontalLayout expensesWindowButtonHorizontalLayout = new HorizontalLayout();
//					expensesWindowButtonHorizontalLayout.setSpacing(true);
//					expensesWindowButtonHorizontalLayout.setHeight("40px");
//					expensesWindowButtonHorizontalLayout.setWidth("100%");
//					expensesWindowVerticalLayout.addComponent(expensesWindowButtonHorizontalLayout);
//
//					Panel expensesWindowInfoPanel = new Panel(infoWindow.getCaption());
//					expensesWindowInfoPanel.setSizeFull();
//
//					expensesWindowInfoPanel.setContent(infoGrid);
//
//					expensesWindowinfoPanelHorizontalLayout.addComponent(expensesWindowInfoPanel);
//
//					Panel expensesWindowPartsPanel = new Panel("Expenses Parts");
//					expensesWindowPartsPanel.setHeight("267px");
//					expensesWindowPartsPanel.setWidth("100%");	
//						
//						
//						
//					
//					
//					
//					ComboBox expensesWindowCorpsComboBox = new ComboBox("Building Corps List");
//					expensesWindowCorpsComboBox.setNullSelectionAllowed(false);
//
//					List<Flat> corpsList = new ArrayList<>();
//
//					try {
//
//						corpsList = flatService.getCorpsFromSettingsTable();
//
//						Iterator<Flat> itr = corpsList.iterator();
//						while (itr.hasNext()) {
//							Flat flatFromList = itr.next();
//
//							expensesWindowCorpsComboBox.addItem(flatFromList.getBuildingCorps());
//
//						}
//					} catch (NumberFormatException | SQLException e4) {
//						// TODO Auto-generated catch block
//						e4.printStackTrace();
//					}
//
//					expensesWindowCorpsComboBox.addValueChangeListener(select -> {
//
//						expensesWindowVerticalLayout.removeComponent(expensesWindowPartsPanel);
//
//						expensesWindowinfoPanelHorizontalLayout.addComponent(expensesWindowInfoPanel);
//
//						List<Flat> flatListByCorps = new ArrayList<>();
//
//						List<Flat> flatListExpensesByFlatId = new ArrayList<>();
//
//						List<Flat> expensesList = new ArrayList<>();
//
//						try {
//
//							flatListByCorps = flatService
//									.getFlatsByCorpsFromFlatTable(expensesWindowCorpsComboBox.getValue().toString());
//
//							Iterator<Flat> itr = flatListByCorps.iterator();
//							while (itr.hasNext()) {
//								Flat flatFromList = itr.next();
//
//								flatListExpensesByFlatId = expensesWindowCorpsComboBoxInfoByFlatId(
//										flatFromList.getIdFlatTable());
//
//								Iterator<Flat> itr1 = flatListExpensesByFlatId.iterator();
//								while (itr1.hasNext()) {
//									Flat flatFromList1 = itr1.next();
//
//									expensesList.add(flatFromList1);
//
//								}
//
//								expensesWindowInfoPanel.setContent(infoGrid(expensesList));
//
//							}
//						} catch (NumberFormatException | SQLException e4) {
//							// TODO Auto-generated catch block
//							e4.printStackTrace();
//						}
//
//					});
//
//					expensesWindowFlatHorizontalLayout.addComponent(expensesWindowCorpsComboBox);
//					expensesWindowFlatHorizontalLayout.setComponentAlignment(expensesWindowCorpsComboBox,
//							Alignment.BOTTOM_CENTER);
//
//					ComboBox expensesWindowFlatComboBox = new ComboBox("Flat List");
//					expensesWindowFlatComboBox.setNullSelectionAllowed(false);
//
//					List<Flat> flatList = new ArrayList<>();
//
//					try {
//
//						flatList = flatService.getFlatsFromOrderedFlatTable();
//
//						Iterator<Flat> itr = flatList.iterator();
//						while (itr.hasNext()) {
//							Flat flatFromList = itr.next();
//
//							expensesWindowFlatComboBox.addItem(flatFromList.getBuildingCorps() + " - № "
//									+ flatFromList.getFlatNumber() + " - " + flatFromList.getFlatArea());
//
//						}
//					} catch (NumberFormatException | SQLException e4) {
//						// TODO Auto-generated catch block
//						e4.printStackTrace();
//					}
//
//					expensesWindowFlatComboBox.addValueChangeListener(select -> {
//
//						expensesWindowVerticalLayout.removeComponent(expensesWindowPartsPanel);
//
//						expensesWindowinfoPanelHorizontalLayout.addComponent(expensesWindowInfoPanel);
//
//						String flatListTextFromComboBox = expensesWindowFlatComboBox.getValue().toString();
//
//						String[] flatCorpsNumber = flatListTextFromComboBox.split(" - ");
//
//						String corps = flatCorpsNumber[0];
//
//						String[] flatNumber = flatCorpsNumber[1].split(" ");
//
//						int number = Integer.parseInt(flatNumber[1]);
//
//						List<Flat> expensesList = new ArrayList<>();
//
//						try {
//							expensesList = expensesWindowFlatComboBoxInfoByCorpsAndNumber(corps, number);
//							expensesWindowInfoPanel.setContent(infoGrid(expensesList));
//						} catch (SQLException e1) {
//							// TODO Auto-generated catch block
//							e1.printStackTrace();
//						}
//
//					});
//
//					expensesWindowFlatHorizontalLayout.addComponent(expensesWindowFlatComboBox);
//					expensesWindowFlatHorizontalLayout.setComponentAlignment(expensesWindowFlatComboBox,
//							Alignment.BOTTOM_CENTER);
//
//					Button expensesWindowSelectAllButton = new Button("Select All");
//					expensesWindowSelectAllButton.setWidth("180px");
//					expensesWindowSelectAllButton.addClickListener(lick -> {
//
//						expensesWindowVerticalLayout.removeComponent(expensesWindowPartsPanel);
//
//						expensesWindowinfoPanelHorizontalLayout.addComponent(expensesWindowInfoPanel);
//
//						List<Flat> expensesList = new ArrayList<>();
//
//						try {
//
////							expensesList = expensesWindowAllExpenses();
//							
//							expensesList = selectAllflatsList;
//							
//							expensesWindowInfoPanel.setContent(infoGrid(expensesList));
//
//						} catch (SQLException e1) {
//							// TODO Auto-generated catch block
//							e1.printStackTrace();
//						}
//
//					});
//
//					expensesWindowFlatHorizontalLayout.addComponent(expensesWindowSelectAllButton);
//					expensesWindowFlatHorizontalLayout.setComponentAlignment(expensesWindowSelectAllButton,
//							Alignment.BOTTOM_CENTER);
//
//					
//					
//					
//					
//					
//					
//					Button expensesWindowEditButton = new Button("Edit");
//					expensesWindowEditButton.setWidth("180px");
////					expensesWindowEditButton.setEnabled(false);
//					expensesWindowEditButton.addClickListener(lick -> {
//
//				
//							
//							
//							Window editWindow = new Window("Expenses Window");
//							editWindow.setHeight("600px");
//							editWindow.setWidth("620px");
//
//							
//							VerticalLayout editWindowVerticalLayout = new VerticalLayout();
//							editWindowVerticalLayout.setMargin(true);
//							editWindowVerticalLayout.setSpacing(true);
//							editWindowVerticalLayout.setSizeFull();
//							editWindow.setContent(editWindowVerticalLayout);
//
//							HorizontalLayout editWindowInfoHorizontalLayout = new HorizontalLayout();
//							editWindowInfoHorizontalLayout.setHeight("103px");
//							editWindowInfoHorizontalLayout.setWidth("100%");
//							editWindowVerticalLayout.addComponent(editWindowInfoHorizontalLayout);
//
////							expensesWindowInfoHorizontalLayout.addComponent(infoGrid(idFlatTableIntFromSelectedRow));
//
//							HorizontalLayout editWindowAddInfoHorizontalLayout = new HorizontalLayout();
//							editWindowAddInfoHorizontalLayout.setSizeFull();
//							editWindowVerticalLayout.addComponent(editWindowAddInfoHorizontalLayout);
//							editWindowVerticalLayout.setExpandRatio(editWindowAddInfoHorizontalLayout, 1.0f);
//
//							Panel addExpensesWindowPanel = new Panel("Add Expenses");
//							addExpensesWindowPanel.setSizeFull();
//							editWindowAddInfoHorizontalLayout.addComponent(addExpensesWindowPanel);
//
//							FormLayout expensesWindowExpensesFormLayout = new FormLayout();
//							expensesWindowExpensesFormLayout.setMargin(true);
//							expensesWindowExpensesFormLayout.setSizeFull();
//							addExpensesWindowPanel.setContent(expensesWindowExpensesFormLayout);
//
//							DateField expensesDateField = new DateField("Expenses Date");
//							expensesDateField.setValue(new java.util.Date());
//
//							expensesDateField.setDateFormat("dd.MM.yyyy");
//							
////							expensesDateField.setResolution(Resolution.MINUTE);
//					//
////							expensesDateField.setDateFormat("dd.MM.yyyy HH:mm:ss");
//
//							expensesWindowExpensesFormLayout.addComponent(expensesDateField);
//
//							TextField expensesSumTextField = new TextField("Expenses Sum");
////							expensesSumTextField.setValue("" + flat.getExpensesTableSum());
//
//							expensesWindowExpensesFormLayout.addComponent(expensesSumTextField);
//
//							ComboBox expensesCategoryComboBox = new ComboBox("Expenses Category");
//							expensesCategoryComboBox.addItem("General");
//							expensesCategoryComboBox.addItem("B.C.");
//							expensesCategoryComboBox.addItem("C.M.");
//							expensesCategoryComboBox.setNullSelectionAllowed(false);
//
//							expensesWindowExpensesFormLayout.addComponent(expensesCategoryComboBox);
//
//							ComboBox expensesValueComboBox = new ComboBox("Expenses Value");
//							expensesValueComboBox.addItem("Податки, що залишилися на нашій Львівській фірмі ЛТБ (прогонка квартир)");
//							expensesValueComboBox.addItem("Маклер і оформлення в нотаріуса");
//							expensesValueComboBox.addItem("Переведено на УМБ ФІНАНС (Козловському)");
//							expensesValueComboBox.addItem("Передано В.С.");
//							expensesValueComboBox.addItem("Передано С.М.");
//							expensesValueComboBox.setNullSelectionAllowed(false);
//
//							expensesWindowExpensesFormLayout.addComponent(expensesValueComboBox);
//
//							TextArea expensesValueTextArea = new TextArea("Expenses Value");
//							expensesValueTextArea.setHeight("60px");
//
//							expensesWindowExpensesFormLayout.addComponent(expensesValueTextArea);
//
//							
//							
//							
//							
//							HorizontalLayout editWindowButtonHorizontalLayout = new HorizontalLayout();
//
//							editWindowButtonHorizontalLayout.setSpacing(true);
//							editWindowButtonHorizontalLayout.setHeight("40px");
//							editWindowButtonHorizontalLayout.setWidth("100%");
//
//							editWindowVerticalLayout.addComponent(editWindowButtonHorizontalLayout);
//							editWindowVerticalLayout.setComponentAlignment(editWindowButtonHorizontalLayout,
//									Alignment.BOTTOM_CENTER);
//						
//							
//							Button expenses1CancelButton = new Button("Cancel");
//							expenses1CancelButton.setSizeFull();
//							expenses1CancelButton.addClickListener(click -> {
//								editWindow.close();
//							});
//
//							editWindowButtonHorizontalLayout.addComponent(expenses1CancelButton);
//							
//							
//							
//							Button expensesCancelButton = new Button("Cancel");
//							expensesCancelButton.setSizeFull();
//							expensesCancelButton.addClickListener(click -> {
//								editWindow.close();
//							});
//
//							editWindowButtonHorizontalLayout.addComponent(expensesCancelButton);
//							
//							
//							
////							infoGrid.addSelectionListener(select -> {
////								
////							
////							
////							
////							Object selected = ((SingleSelectionModel) infoGrid.getSelectionModel()).getSelectedRow();
////							String idExpensesTableFromSelectedRow = infoGrid.getContainerDataSource().getItem(selected)
////									.getItemProperty("idExpensesTable").getValue().toString();
////							int idExpensesTableIntFromSelectedRow = Integer.parseInt(idExpensesTableFromSelectedRow);
////							
////							
////							Flat flat = new Flat();
////							
////							try {
////								flat = flatService.getFlatByIdFromExpensesTable(idExpensesTableIntFromSelectedRow);
////							} catch (SQLException e1) {
////								// TODO Auto-generated catch block
////								e1.printStackTrace();
////							}
////							
////							
////							expensesSumTextField.setValue("" + flat.getExpensesTableSum());
//		//
//		//
////							
////							
////							
////							});
//							
//							
//							editWindow.center();
//
//							UI.getCurrent().addWindow(editWindow);
//
//						
//						
//						
//						
//						
//						
//						
//
//					});
//
//					expensesWindowFlatHorizontalLayout.addComponent(expensesWindowEditButton);
//					expensesWindowFlatHorizontalLayout.setComponentAlignment(expensesWindowEditButton,
//							Alignment.BOTTOM_CENTER);
//					
//					
//					
//					
//					
//					
//					
//					//
//
//					Button expensesPartsButton = new Button("Parts");
//					expensesPartsButton.setSizeFull();
//					expensesPartsButton.addClickListener(click -> {
//
//						expensesWindowinfoPanelHorizontalLayout.removeComponent(expensesWindowInfoPanel);
//
//						expensesWindowVerticalLayout.addComponent(expensesWindowPartsPanel);
//
//						Grid partsGrid = new Grid();
//
//						partsGrid.setSizeFull();
//						partsGrid.addColumn("name", String.class);
//						partsGrid.addColumn("flatArea", Double.class);
//
//						partsGrid.addColumn("receivedArea", Double.class);
//						partsGrid.addColumn("receivedGeneralArea", Double.class);
//
//						partsGrid.addColumn("residualArea", Double.class);
//
//						double allFlatsArea = 0;
//						double receivedArea = 0;
//						double receivedGeneralArea = 0;
//						double residualArea = 0;
//
//						double BCArea = 0;
//						double CMArea = 0;
//						double receivedBCGeneralArea = 0;
//						double receivedCMGeneralArea = 0;
//						double generalArea = 0;
//
//						try {
//							allFlatsArea = flatService.sumAllFlatsArea();
//
//							List<Flat> soldedFlatList = new ArrayList<>();
//
//							soldedFlatList = flatService.getFlatsByFlatSetFromDB("Solded");
//
//							Iterator<Flat> itr = soldedFlatList.iterator();
//							while (itr.hasNext()) {
//								Flat flatFromList = itr.next();
//
//								double m2 = flatService.getFlatByFlatIdFromFlatBuyerTable(flatFromList.getIdFlatTable())
//										.getFlatCost()
//										/ flatService.getFlatByFlatIdFromFlatTable(flatFromList.getIdFlatTable()).getFlatArea();
//
//								BCArea = BCArea + flatService.getExpensesFlatInfoFromExpensesTableByFlatId(
//										flatFromList.getIdFlatTable(), bcExpenses) / m2;
//
//								CMArea = CMArea + flatService.getExpensesFlatInfoFromExpensesTableByFlatId(
//										flatFromList.getIdFlatTable(), cmExpenses) / m2;
//
//								generalArea = generalArea + flatService.getExpensesFlatInfoFromExpensesTableByFlatId(
//										flatFromList.getIdFlatTable(), generalExpenses) / m2;
//
//								receivedBCGeneralArea = generalArea * bcGeneralExpenses;
//
//								receivedCMGeneralArea = generalArea * cmGeneralExpenses;
//
//							}
//
//							partsGrid.addRow("B.C. 70%", allFlatsArea * 70 / 100, BCArea, receivedBCGeneralArea,
//									allFlatsArea * 70 / 100 - BCArea - receivedBCGeneralArea);
//							partsGrid.addRow("C.M. 30%", flatService.sumAllFlatsArea() * 30 / 100, CMArea,
//									receivedCMGeneralArea, allFlatsArea * 30 / 100 - CMArea - receivedCMGeneralArea);
//
//							receivedGeneralArea = receivedBCGeneralArea + receivedCMGeneralArea;
//							receivedArea = BCArea + CMArea;
//							residualArea = allFlatsArea - receivedArea - receivedGeneralArea;
//
//						} catch (SQLException e1) {
//							// TODO Auto-generated catch block
//							e1.printStackTrace();
//						}
//
//						HeaderRow expensesWindowPartsGridHeaderRow2 = partsGrid.prependHeaderRow();
//						expensesWindowPartsGridHeaderRow2.getCell("name").setText("Received General Area: ");
//						expensesWindowPartsGridHeaderRow2.getCell("flatArea")
//								.setText(decimalFormat.format(generalArea).replace(",", "."));
//						expensesWindowPartsGridHeaderRow2.getCell("receivedArea").setText("B.C. - " + bcGeneralExpenses * 100
//								+ "% = " + decimalFormat.format(receivedBCGeneralArea).replace(",", "."));
//						expensesWindowPartsGridHeaderRow2.getCell("receivedGeneralArea")
//								.setText("C.M. - " + cmGeneralExpenses * 100 + "% = "
//										+ decimalFormat.format(receivedCMGeneralArea).replace(",", "."));
//
//						HeaderRow expensesWindowPartsGridHeaderRow1 = partsGrid.prependHeaderRow();
//						expensesWindowPartsGridHeaderRow1.getCell("name").setText("All Flats Area: ");
//						expensesWindowPartsGridHeaderRow1.getCell("flatArea")
//								.setText(decimalFormat.format(allFlatsArea).replace(",", "."));
//
//						FooterRow expensesWindowPartsGridFooterRow = partsGrid.prependFooterRow();
//						expensesWindowPartsGridFooterRow.getCell("name").setText("Total: ");
//						expensesWindowPartsGridFooterRow.getCell("flatArea")
//								.setText(decimalFormat.format(allFlatsArea).replace(",", "."));
//						expensesWindowPartsGridFooterRow.getCell("receivedArea")
//								.setText(decimalFormat.format(receivedArea).replace(",", "."));
//						expensesWindowPartsGridFooterRow.getCell("receivedGeneralArea")
//								.setText(decimalFormat.format(receivedGeneralArea).replace(",", "."));
//						expensesWindowPartsGridFooterRow.getCell("residualArea")
//								.setText(decimalFormat.format(residualArea).replace(",", "."));
//
//						expensesWindowPartsPanel.setContent(partsGrid);
//
//					});
//					expensesWindowButtonHorizontalLayout.addComponent(expensesPartsButton);
//
//					Button expensesCancelButton = new Button("Cancel");
//					expensesCancelButton.setSizeFull();
//					expensesCancelButton.addClickListener(click -> {
//						infoWindow.close();
//					});
//
//					expensesWindowButtonHorizontalLayout.addComponent(expensesCancelButton);
//					
//					
//					
//						
//						
//						
//						
//						
//			} catch (SQLException e2) {
//				// TODO Auto-generated catch block
//				e2.printStackTrace();
//			}			
//					
//					
//			
//			
//			
//			
////			Window infoWindow = new Window(windowCaption);
////			infoWindow.setHeight("600px");
////			infoWindow.setWidth("850px");
////
////			VerticalLayout expensesWindowVerticalLayout = new VerticalLayout();
////			expensesWindowVerticalLayout.setSpacing(true);
////			expensesWindowVerticalLayout.setMargin(true);
////			expensesWindowVerticalLayout.setSizeFull();
////			infoWindow.setContent(expensesWindowVerticalLayout);
////
////			HorizontalLayout expensesWindowFlatHorizontalLayout = new HorizontalLayout();
////			expensesWindowFlatHorizontalLayout.setSpacing(true);
////			expensesWindowFlatHorizontalLayout.setHeight("65px");
////			expensesWindowFlatHorizontalLayout.setWidth("100%");
////			expensesWindowVerticalLayout.addComponent(expensesWindowFlatHorizontalLayout);
////
////			HorizontalLayout expensesWindowinfoPanelHorizontalLayout = new HorizontalLayout();
////			expensesWindowinfoPanelHorizontalLayout.setSizeFull();
////			expensesWindowVerticalLayout.addComponent(expensesWindowinfoPanelHorizontalLayout);
////			expensesWindowVerticalLayout.setExpandRatio(expensesWindowinfoPanelHorizontalLayout, 1.0f);
////
////			HorizontalLayout expensesWindowButtonHorizontalLayout = new HorizontalLayout();
////			expensesWindowButtonHorizontalLayout.setSpacing(true);
////			expensesWindowButtonHorizontalLayout.setHeight("40px");
////			expensesWindowButtonHorizontalLayout.setWidth("100%");
////			expensesWindowVerticalLayout.addComponent(expensesWindowButtonHorizontalLayout);
////
////			Panel expensesWindowInfoPanel = new Panel(infoWindow.getCaption());
////			expensesWindowInfoPanel.setSizeFull();
////
////			expensesWindowInfoPanel.setContent(infoGrid);
////
////			expensesWindowinfoPanelHorizontalLayout.addComponent(expensesWindowInfoPanel);
////
////			Panel expensesWindowPartsPanel = new Panel("Expenses Parts");
////			expensesWindowPartsPanel.setHeight("267px");
////			expensesWindowPartsPanel.setWidth("100%");
//
////			ComboBox expensesWindowCorpsComboBox = new ComboBox("Building Corps List");
////			expensesWindowCorpsComboBox.setNullSelectionAllowed(false);
////
////			List<Flat> corpsList = new ArrayList<>();
////
////			try {
////
////				corpsList = flatService.getCorpsFromSettingsTable();
////
////				Iterator<Flat> itr = corpsList.iterator();
////				while (itr.hasNext()) {
////					Flat flatFromList = itr.next();
////
////					expensesWindowCorpsComboBox.addItem(flatFromList.getBuildingCorps());
////
////				}
////			} catch (NumberFormatException | SQLException e4) {
////				// TODO Auto-generated catch block
////				e4.printStackTrace();
////			}
////
////			expensesWindowCorpsComboBox.addValueChangeListener(select -> {
////
////				expensesWindowVerticalLayout.removeComponent(expensesWindowPartsPanel);
////
////				expensesWindowinfoPanelHorizontalLayout.addComponent(expensesWindowInfoPanel);
////
////				List<Flat> flatListByCorps = new ArrayList<>();
////
////				List<Flat> flatListExpensesByFlatId = new ArrayList<>();
////
////				List<Flat> expensesList = new ArrayList<>();
////
////				try {
////
////					flatListByCorps = flatService
////							.getFlatsByCorpsFromFlatTable(expensesWindowCorpsComboBox.getValue().toString());
////
////					Iterator<Flat> itr = flatListByCorps.iterator();
////					while (itr.hasNext()) {
////						Flat flatFromList = itr.next();
////
////						flatListExpensesByFlatId = expensesWindowCorpsComboBoxInfoByFlatId(
////								flatFromList.getIdFlatTable());
////
////						Iterator<Flat> itr1 = flatListExpensesByFlatId.iterator();
////						while (itr1.hasNext()) {
////							Flat flatFromList1 = itr1.next();
////
////							expensesList.add(flatFromList1);
////
////						}
////
////						expensesWindowInfoPanel.setContent(infoGrid(expensesList));
////
////					}
////				} catch (NumberFormatException | SQLException e4) {
////					// TODO Auto-generated catch block
////					e4.printStackTrace();
////				}
////
////			});
////
////			expensesWindowFlatHorizontalLayout.addComponent(expensesWindowCorpsComboBox);
////			expensesWindowFlatHorizontalLayout.setComponentAlignment(expensesWindowCorpsComboBox,
////					Alignment.BOTTOM_CENTER);
////
////			ComboBox expensesWindowFlatComboBox = new ComboBox("Flat List");
////			expensesWindowFlatComboBox.setNullSelectionAllowed(false);
////
////			List<Flat> flatList = new ArrayList<>();
////
////			try {
////
////				flatList = flatService.getFlatsFromOrderedFlatTable();
////
////				Iterator<Flat> itr = flatList.iterator();
////				while (itr.hasNext()) {
////					Flat flatFromList = itr.next();
////
////					expensesWindowFlatComboBox.addItem(flatFromList.getBuildingCorps() + " - № "
////							+ flatFromList.getFlatNumber() + " - " + flatFromList.getFlatArea());
////
////				}
////			} catch (NumberFormatException | SQLException e4) {
////				// TODO Auto-generated catch block
////				e4.printStackTrace();
////			}
////
////			expensesWindowFlatComboBox.addValueChangeListener(select -> {
////
////				expensesWindowVerticalLayout.removeComponent(expensesWindowPartsPanel);
////
////				expensesWindowinfoPanelHorizontalLayout.addComponent(expensesWindowInfoPanel);
////
////				String flatListTextFromComboBox = expensesWindowFlatComboBox.getValue().toString();
////
////				String[] flatCorpsNumber = flatListTextFromComboBox.split(" - ");
////
////				String corps = flatCorpsNumber[0];
////
////				String[] flatNumber = flatCorpsNumber[1].split(" ");
////
////				int number = Integer.parseInt(flatNumber[1]);
////
////				List<Flat> expensesList = new ArrayList<>();
////
////				try {
////					expensesList = expensesWindowFlatComboBoxInfoByCorpsAndNumber(corps, number);
////					expensesWindowInfoPanel.setContent(infoGrid(expensesList));
////				} catch (SQLException e1) {
////					// TODO Auto-generated catch block
////					e1.printStackTrace();
////				}
////
////			});
////
////			expensesWindowFlatHorizontalLayout.addComponent(expensesWindowFlatComboBox);
////			expensesWindowFlatHorizontalLayout.setComponentAlignment(expensesWindowFlatComboBox,
////					Alignment.BOTTOM_CENTER);
////
////			Button expensesWindowSelectAllButton = new Button("Select All");
////			expensesWindowSelectAllButton.setWidth("180px");
////			expensesWindowSelectAllButton.addClickListener(lick -> {
////
////				expensesWindowVerticalLayout.removeComponent(expensesWindowPartsPanel);
////
////				expensesWindowinfoPanelHorizontalLayout.addComponent(expensesWindowInfoPanel);
////
////				List<Flat> expensesList = new ArrayList<>();
////
////				try {
////
//////					expensesList = expensesWindowAllExpenses();
////					
////					expensesList = selectAllflatsList;
////					
////					expensesWindowInfoPanel.setContent(infoGrid(expensesList));
////
////				} catch (SQLException e1) {
////					// TODO Auto-generated catch block
////					e1.printStackTrace();
////				}
////
////			});
////
////			expensesWindowFlatHorizontalLayout.addComponent(expensesWindowSelectAllButton);
////			expensesWindowFlatHorizontalLayout.setComponentAlignment(expensesWindowSelectAllButton,
////					Alignment.BOTTOM_CENTER);
////
////			
////			
////			
////			
////			
////			
////			Button expensesWindowEditButton = new Button("Edit");
////			expensesWindowEditButton.setWidth("180px");
//////			expensesWindowEditButton.setEnabled(false);
////			expensesWindowEditButton.addClickListener(lick -> {
////
////		
////					
////					
////					Window editWindow = new Window("Expenses Window");
////					editWindow.setHeight("600px");
////					editWindow.setWidth("620px");
////
////					
////					VerticalLayout editWindowVerticalLayout = new VerticalLayout();
////					editWindowVerticalLayout.setMargin(true);
////					editWindowVerticalLayout.setSpacing(true);
////					editWindowVerticalLayout.setSizeFull();
////					editWindow.setContent(editWindowVerticalLayout);
////
////					HorizontalLayout editWindowInfoHorizontalLayout = new HorizontalLayout();
////					editWindowInfoHorizontalLayout.setHeight("103px");
////					editWindowInfoHorizontalLayout.setWidth("100%");
////					editWindowVerticalLayout.addComponent(editWindowInfoHorizontalLayout);
////
//////					expensesWindowInfoHorizontalLayout.addComponent(infoGrid(idFlatTableIntFromSelectedRow));
////
////					HorizontalLayout editWindowAddInfoHorizontalLayout = new HorizontalLayout();
////					editWindowAddInfoHorizontalLayout.setSizeFull();
////					editWindowVerticalLayout.addComponent(editWindowAddInfoHorizontalLayout);
////					editWindowVerticalLayout.setExpandRatio(editWindowAddInfoHorizontalLayout, 1.0f);
////
////					Panel addExpensesWindowPanel = new Panel("Add Expenses");
////					addExpensesWindowPanel.setSizeFull();
////					editWindowAddInfoHorizontalLayout.addComponent(addExpensesWindowPanel);
////
////					FormLayout expensesWindowExpensesFormLayout = new FormLayout();
////					expensesWindowExpensesFormLayout.setMargin(true);
////					expensesWindowExpensesFormLayout.setSizeFull();
////					addExpensesWindowPanel.setContent(expensesWindowExpensesFormLayout);
////
////					DateField expensesDateField = new DateField("Expenses Date");
////					expensesDateField.setValue(new java.util.Date());
////
////					expensesDateField.setDateFormat("dd.MM.yyyy");
////					
//////					expensesDateField.setResolution(Resolution.MINUTE);
////			//
//////					expensesDateField.setDateFormat("dd.MM.yyyy HH:mm:ss");
////
////					expensesWindowExpensesFormLayout.addComponent(expensesDateField);
////
////					TextField expensesSumTextField = new TextField("Expenses Sum");
//////					expensesSumTextField.setValue("" + flat.getExpensesTableSum());
////
////					expensesWindowExpensesFormLayout.addComponent(expensesSumTextField);
////
////					ComboBox expensesCategoryComboBox = new ComboBox("Expenses Category");
////					expensesCategoryComboBox.addItem("General");
////					expensesCategoryComboBox.addItem("B.C.");
////					expensesCategoryComboBox.addItem("C.M.");
////					expensesCategoryComboBox.setNullSelectionAllowed(false);
////
////					expensesWindowExpensesFormLayout.addComponent(expensesCategoryComboBox);
////
////					ComboBox expensesValueComboBox = new ComboBox("Expenses Value");
////					expensesValueComboBox.addItem("Податки, що залишилися на нашій Львівській фірмі ЛТБ (прогонка квартир)");
////					expensesValueComboBox.addItem("Маклер і оформлення в нотаріуса");
////					expensesValueComboBox.addItem("Переведено на УМБ ФІНАНС (Козловському)");
////					expensesValueComboBox.addItem("Передано В.С.");
////					expensesValueComboBox.addItem("Передано С.М.");
////					expensesValueComboBox.setNullSelectionAllowed(false);
////
////					expensesWindowExpensesFormLayout.addComponent(expensesValueComboBox);
////
////					TextArea expensesValueTextArea = new TextArea("Expenses Value");
////					expensesValueTextArea.setHeight("60px");
////
////					expensesWindowExpensesFormLayout.addComponent(expensesValueTextArea);
////
////					
////					
////					
////					
////					HorizontalLayout editWindowButtonHorizontalLayout = new HorizontalLayout();
////
////					editWindowButtonHorizontalLayout.setSpacing(true);
////					editWindowButtonHorizontalLayout.setHeight("40px");
////					editWindowButtonHorizontalLayout.setWidth("100%");
////
////					editWindowVerticalLayout.addComponent(editWindowButtonHorizontalLayout);
////					editWindowVerticalLayout.setComponentAlignment(editWindowButtonHorizontalLayout,
////							Alignment.BOTTOM_CENTER);
////				
////					
////					Button expenses1CancelButton = new Button("Cancel");
////					expenses1CancelButton.setSizeFull();
////					expenses1CancelButton.addClickListener(click -> {
////						editWindow.close();
////					});
////
////					editWindowButtonHorizontalLayout.addComponent(expenses1CancelButton);
////					
////					
////					
////					Button expensesCancelButton = new Button("Cancel");
////					expensesCancelButton.setSizeFull();
////					expensesCancelButton.addClickListener(click -> {
////						editWindow.close();
////					});
////
////					editWindowButtonHorizontalLayout.addComponent(expensesCancelButton);
////					
////					
////					
//////					infoGrid.addSelectionListener(select -> {
//////						
//////					
//////					
//////					
//////					Object selected = ((SingleSelectionModel) infoGrid.getSelectionModel()).getSelectedRow();
//////					String idExpensesTableFromSelectedRow = infoGrid.getContainerDataSource().getItem(selected)
//////							.getItemProperty("idExpensesTable").getValue().toString();
//////					int idExpensesTableIntFromSelectedRow = Integer.parseInt(idExpensesTableFromSelectedRow);
//////					
//////					
//////					Flat flat = new Flat();
//////					
//////					try {
//////						flat = flatService.getFlatByIdFromExpensesTable(idExpensesTableIntFromSelectedRow);
//////					} catch (SQLException e1) {
//////						// TODO Auto-generated catch block
//////						e1.printStackTrace();
//////					}
//////					
//////					
//////					expensesSumTextField.setValue("" + flat.getExpensesTableSum());
//////
//////
//////					
//////					
//////					
//////					});
////					
////					
////					editWindow.center();
////
////					UI.getCurrent().addWindow(editWindow);
////
////				
////				
////				
////				
////				
////				
////				
////
////			});
////
////			expensesWindowFlatHorizontalLayout.addComponent(expensesWindowEditButton);
////			expensesWindowFlatHorizontalLayout.setComponentAlignment(expensesWindowEditButton,
////					Alignment.BOTTOM_CENTER);
////			
////			
////			
////			
////			
////			
////			
////			//
////
////			Button expensesPartsButton = new Button("Parts");
////			expensesPartsButton.setSizeFull();
////			expensesPartsButton.addClickListener(click -> {
////
////				expensesWindowinfoPanelHorizontalLayout.removeComponent(expensesWindowInfoPanel);
////
////				expensesWindowVerticalLayout.addComponent(expensesWindowPartsPanel);
////
////				Grid partsGrid = new Grid();
////
////				partsGrid.setSizeFull();
////				partsGrid.addColumn("name", String.class);
////				partsGrid.addColumn("flatArea", Double.class);
////
////				partsGrid.addColumn("receivedArea", Double.class);
////				partsGrid.addColumn("receivedGeneralArea", Double.class);
////
////				partsGrid.addColumn("residualArea", Double.class);
////
////				double allFlatsArea = 0;
////				double receivedArea = 0;
////				double receivedGeneralArea = 0;
////				double residualArea = 0;
////
////				double BCArea = 0;
////				double CMArea = 0;
////				double receivedBCGeneralArea = 0;
////				double receivedCMGeneralArea = 0;
////				double generalArea = 0;
////
////				try {
////					allFlatsArea = flatService.sumAllFlatsArea();
////
////					List<Flat> soldedFlatList = new ArrayList<>();
////
////					soldedFlatList = flatService.getFlatsByFlatSetFromDB("Solded");
////
////					Iterator<Flat> itr = soldedFlatList.iterator();
////					while (itr.hasNext()) {
////						Flat flatFromList = itr.next();
////
////						double m2 = flatService.getFlatByFlatIdFromFlatBuyerTable(flatFromList.getIdFlatTable())
////								.getFlatCost()
////								/ flatService.getFlatByFlatIdFromFlatTable(flatFromList.getIdFlatTable()).getFlatArea();
////
////						BCArea = BCArea + flatService.getExpensesFlatInfoFromExpensesTableByFlatId(
////								flatFromList.getIdFlatTable(), bcExpenses) / m2;
////
////						CMArea = CMArea + flatService.getExpensesFlatInfoFromExpensesTableByFlatId(
////								flatFromList.getIdFlatTable(), cmExpenses) / m2;
////
////						generalArea = generalArea + flatService.getExpensesFlatInfoFromExpensesTableByFlatId(
////								flatFromList.getIdFlatTable(), generalExpenses) / m2;
////
////						receivedBCGeneralArea = generalArea * bcGeneralExpenses;
////
////						receivedCMGeneralArea = generalArea * cmGeneralExpenses;
////
////					}
////
////					partsGrid.addRow("B.C. 70%", allFlatsArea * 70 / 100, BCArea, receivedBCGeneralArea,
////							allFlatsArea * 70 / 100 - BCArea - receivedBCGeneralArea);
////					partsGrid.addRow("C.M. 30%", flatService.sumAllFlatsArea() * 30 / 100, CMArea,
////							receivedCMGeneralArea, allFlatsArea * 30 / 100 - CMArea - receivedCMGeneralArea);
////
////					receivedGeneralArea = receivedBCGeneralArea + receivedCMGeneralArea;
////					receivedArea = BCArea + CMArea;
////					residualArea = allFlatsArea - receivedArea - receivedGeneralArea;
////
////				} catch (SQLException e1) {
////					// TODO Auto-generated catch block
////					e1.printStackTrace();
////				}
////
////				HeaderRow expensesWindowPartsGridHeaderRow2 = partsGrid.prependHeaderRow();
////				expensesWindowPartsGridHeaderRow2.getCell("name").setText("Received General Area: ");
////				expensesWindowPartsGridHeaderRow2.getCell("flatArea")
////						.setText(decimalFormat.format(generalArea).replace(",", "."));
////				expensesWindowPartsGridHeaderRow2.getCell("receivedArea").setText("B.C. - " + bcGeneralExpenses * 100
////						+ "% = " + decimalFormat.format(receivedBCGeneralArea).replace(",", "."));
////				expensesWindowPartsGridHeaderRow2.getCell("receivedGeneralArea")
////						.setText("C.M. - " + cmGeneralExpenses * 100 + "% = "
////								+ decimalFormat.format(receivedCMGeneralArea).replace(",", "."));
////
////				HeaderRow expensesWindowPartsGridHeaderRow1 = partsGrid.prependHeaderRow();
////				expensesWindowPartsGridHeaderRow1.getCell("name").setText("All Flats Area: ");
////				expensesWindowPartsGridHeaderRow1.getCell("flatArea")
////						.setText(decimalFormat.format(allFlatsArea).replace(",", "."));
////
////				FooterRow expensesWindowPartsGridFooterRow = partsGrid.prependFooterRow();
////				expensesWindowPartsGridFooterRow.getCell("name").setText("Total: ");
////				expensesWindowPartsGridFooterRow.getCell("flatArea")
////						.setText(decimalFormat.format(allFlatsArea).replace(",", "."));
////				expensesWindowPartsGridFooterRow.getCell("receivedArea")
////						.setText(decimalFormat.format(receivedArea).replace(",", "."));
////				expensesWindowPartsGridFooterRow.getCell("receivedGeneralArea")
////						.setText(decimalFormat.format(receivedGeneralArea).replace(",", "."));
////				expensesWindowPartsGridFooterRow.getCell("residualArea")
////						.setText(decimalFormat.format(residualArea).replace(",", "."));
////
////				expensesWindowPartsPanel.setContent(partsGrid);
////
////			});
////			expensesWindowButtonHorizontalLayout.addComponent(expensesPartsButton);
////
////			Button expensesCancelButton = new Button("Cancel");
////			expensesCancelButton.setSizeFull();
////			expensesCancelButton.addClickListener(click -> {
////				infoWindow.close();
////			});
////
////			expensesWindowButtonHorizontalLayout.addComponent(expensesCancelButton);
//
//			infoWindow.center();
//
//			UI.getCurrent().addWindow(infoWindow);
//
//		});
//
//		return infoButton;
//	}
//
//}
