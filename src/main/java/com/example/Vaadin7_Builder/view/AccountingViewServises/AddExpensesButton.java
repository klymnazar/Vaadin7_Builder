package com.example.Vaadin7_Builder.view.AccountingViewServises;

import java.sql.SQLException;
import com.example.Vaadin7_Builder.model.Flat;
import com.example.Vaadin7_Builder.service.FlatService;
import com.vaadin.shared.ui.datefield.Resolution;
import com.vaadin.ui.Alignment;
import com.vaadin.ui.Button;
import com.vaadin.ui.ComboBox;
import com.vaadin.ui.DateField;
import com.vaadin.ui.FormLayout;
import com.vaadin.ui.Grid;
import com.vaadin.ui.HorizontalLayout;
import com.vaadin.ui.Notification;
import com.vaadin.ui.Panel;
import com.vaadin.ui.TextArea;
import com.vaadin.ui.TextField;
import com.vaadin.ui.UI;
import com.vaadin.ui.VerticalLayout;
import com.vaadin.ui.Window;
import com.vaadin.ui.Grid.SingleSelectionModel;

public class AddExpensesButton extends VerticalLayout {

	FlatService flatService = new FlatService();

	private String generalExpenses = "General";
	private String bcExpenses = "B.C.";
	private String cmExpenses = "C.M.";

	

	public Grid infoGrid(int idFlatTableIntFromSelectedRow) {

		Grid infoGrid = new Grid("Info Grid");
		infoGrid.setSizeFull();

		infoGrid.addColumn("flatCost", Integer.class);
		infoGrid.addColumn("genExpenses", Integer.class);
		infoGrid.addColumn("BCExpenses", Integer.class);
		infoGrid.addColumn("CMExpenses", Integer.class);
		infoGrid.addColumn("availableSum", Integer.class);

		Flat flat = new Flat();

		try {
			flat = flatService.getFlatByFlatIdFromFlatBuyerTable(idFlatTableIntFromSelectedRow);

			int flatCost = flat.getFlatCost();
			int generalExp = flatService.getExpensesFlatInfoFromExpensesTableByFlatId(idFlatTableIntFromSelectedRow,
					generalExpenses);
			int bcExp = flatService.getExpensesFlatInfoFromExpensesTableByFlatId(idFlatTableIntFromSelectedRow,
					bcExpenses);
			int cmExp = flatService.getExpensesFlatInfoFromExpensesTableByFlatId(idFlatTableIntFromSelectedRow,
					cmExpenses);
			int availableSum = flatCost - generalExp - bcExp - cmExp;

			infoGrid.addRow(flatCost, generalExp, bcExp, cmExp, availableSum);

		} catch (SQLException e2) {
			// TODO Auto-generated catch block
			e2.printStackTrace();
		}

		return infoGrid;
	}

	public Button addExpensesButton(Grid flatGrid) {

//		expenses sum сума витрат
//		income sum сума доходу
//		available sum оступна сума

		Button expensesButton = new Button("Add Expenses");
		expensesButton.setSizeFull();
		expensesButton.setEnabled(false);

		expensesButton.addClickListener(e -> {

			Window expensesWindow = new Window("Expenses Window");
			expensesWindow.setHeight("600px");
			expensesWindow.setWidth("620px");

			Object selected = ((SingleSelectionModel) flatGrid.getSelectionModel()).getSelectedRow();
			String idFlatTableFromSelectedRow = flatGrid.getContainerDataSource().getItem(selected)
					.getItemProperty("idFlatTable").getValue().toString();
			int idFlatTableIntFromSelectedRow = Integer.parseInt(idFlatTableFromSelectedRow);

			VerticalLayout expensesWindowVerticalLayout = new VerticalLayout();
			expensesWindowVerticalLayout.setMargin(true);
			expensesWindowVerticalLayout.setSpacing(true);
			expensesWindowVerticalLayout.setSizeFull();
			expensesWindow.setContent(expensesWindowVerticalLayout);

			HorizontalLayout expensesWindowInfoHorizontalLayout = new HorizontalLayout();
			expensesWindowInfoHorizontalLayout.setHeight("103px");
			expensesWindowInfoHorizontalLayout.setWidth("100%");
			expensesWindowVerticalLayout.addComponent(expensesWindowInfoHorizontalLayout);

			expensesWindowInfoHorizontalLayout.addComponent(infoGrid(idFlatTableIntFromSelectedRow));

			HorizontalLayout expensesWindowAddInfoHorizontalLayout = new HorizontalLayout();
			expensesWindowAddInfoHorizontalLayout.setSizeFull();
			expensesWindowVerticalLayout.addComponent(expensesWindowAddInfoHorizontalLayout);
			expensesWindowVerticalLayout.setExpandRatio(expensesWindowAddInfoHorizontalLayout, 1.0f);

			Panel addExpensesWindowPanel = new Panel("Add Expenses");
			addExpensesWindowPanel.setSizeFull();
			expensesWindowAddInfoHorizontalLayout.addComponent(addExpensesWindowPanel);

			FormLayout expensesWindowExpensesFormLayout = new FormLayout();
			expensesWindowExpensesFormLayout.setMargin(true);
			expensesWindowExpensesFormLayout.setSizeFull();
			addExpensesWindowPanel.setContent(expensesWindowExpensesFormLayout);

			DateField expensesDateField = new DateField("Expenses Date");
			expensesDateField.setValue(new java.util.Date());

			expensesDateField.setResolution(Resolution.MINUTE);

			expensesDateField.setDateFormat("dd.MM.yyyy HH:mm:ss");

			expensesWindowExpensesFormLayout.addComponent(expensesDateField);

			TextField expensesSumTextField = new TextField("Expenses Sum");

			expensesWindowExpensesFormLayout.addComponent(expensesSumTextField);

			ComboBox expensesCategoryComboBox = new ComboBox("Expenses Category");
			expensesCategoryComboBox.addItem("General");
			expensesCategoryComboBox.addItem("B.C.");
			expensesCategoryComboBox.addItem("C.M.");
			expensesCategoryComboBox.setNullSelectionAllowed(false);

			expensesWindowExpensesFormLayout.addComponent(expensesCategoryComboBox);

			ComboBox expensesValueComboBox = new ComboBox("Expenses Value");
			expensesValueComboBox.addItem("Податки, що залишилися на нашій Львівській фірмі ЛТБ (прогонка квартир)");
			expensesValueComboBox.addItem("Маклер і оформлення в нотаріуса");
			expensesValueComboBox.addItem("Переведено на УМБ ФІНАНС (Козловському)");
			expensesValueComboBox.addItem("Передано В.С.");
			expensesValueComboBox.addItem("Передано С.М.");
			expensesValueComboBox.setNullSelectionAllowed(false);

			expensesWindowExpensesFormLayout.addComponent(expensesValueComboBox);

			TextArea expensesValueTextArea = new TextArea("Expenses Value");
			expensesValueTextArea.setHeight("60px");

			expensesWindowExpensesFormLayout.addComponent(expensesValueTextArea);

			HorizontalLayout expensesWindowButtonHorizontalLayout = new HorizontalLayout();

			expensesWindowButtonHorizontalLayout.setSpacing(true);
			expensesWindowButtonHorizontalLayout.setHeight("40px");
			expensesWindowButtonHorizontalLayout.setWidth("100%");

			expensesWindowVerticalLayout.addComponent(expensesWindowButtonHorizontalLayout);
			expensesWindowVerticalLayout.setComponentAlignment(expensesWindowButtonHorizontalLayout,
					Alignment.BOTTOM_CENTER);

			Button expensesAddButton = new Button("Add Expenses");
			expensesAddButton.setSizeFull();
			expensesAddButton.addClickListener(e1 -> {

				int availableSum = Integer.parseInt(infoGrid(idFlatTableIntFromSelectedRow).getContainerDataSource()
						.getItem(1).getItemProperty("availableSum").getValue().toString());

				
				if (expensesSumTextField.getValue().equals("") || expensesCategoryComboBox.isEmpty() || expensesValueComboBox.isEmpty()) {
					
					Notification.show("Please, fill in all the fields!", Notification.Type.ERROR_MESSAGE);
					
				}
				else 
				
				
				if (Integer.parseInt(expensesSumTextField.getValue()) > availableSum) {

					Notification.show("Please, change expenses sum. You imputed " + expensesSumTextField.getValue()
							+ ", but available sum " + availableSum + "!", Notification.Type.WARNING_MESSAGE);

				}

				else {

					expensesWindowInfoHorizontalLayout.removeAllComponents();

					Flat expensesFlat = new Flat();
					expensesFlat.setIdFlatTable(idFlatTableIntFromSelectedRow);
					expensesFlat.setExpensesTableDate(expensesDateField.getValue());
					expensesFlat.setExpensesTableSum(Integer.parseInt(expensesSumTextField.getValue()));
					expensesFlat.setExpensesTableCategory(expensesCategoryComboBox.getValue().toString());
					expensesFlat.setExpensesTableValue(
							expensesValueComboBox.getValue() + " " + expensesValueTextArea.getValue());

					try {
						flatService.createExpensesFlat(expensesFlat);

						expensesWindowInfoHorizontalLayout.addComponent(infoGrid(idFlatTableIntFromSelectedRow));
						expensesDateField.setValue(new java.util.Date());

						expensesSumTextField.clear();
						expensesCategoryComboBox.clear();
						expensesValueComboBox.clear();
						expensesValueTextArea.clear();

					} catch (SQLException e2) {
						// TODO Auto-generated catch block
						e2.printStackTrace();
					}

				}

			});

			expensesWindowButtonHorizontalLayout.addComponent(expensesAddButton);

			Button expensesCancelButton = new Button("Cancel");
			expensesCancelButton.setSizeFull();
			expensesCancelButton.addClickListener(click -> {
				expensesWindow.close();
			});

			expensesWindowButtonHorizontalLayout.addComponent(expensesCancelButton);

			expensesWindow.center();

			UI.getCurrent().addWindow(expensesWindow);

		});

		flatGrid.addSelectionListener(SelectionEvent -> {
			expensesButton.setEnabled(true);
		});

		return expensesButton;
	}

}
