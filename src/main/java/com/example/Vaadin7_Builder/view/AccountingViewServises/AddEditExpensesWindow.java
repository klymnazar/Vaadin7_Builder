package com.example.Vaadin7_Builder.view.AccountingViewServises;

import java.sql.SQLException;
import java.util.List;

import com.example.Vaadin7_Builder.model.Flat;
import com.example.Vaadin7_Builder.service.FlatService;
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

public abstract class AddEditExpensesWindow {

	FlatService flatService = new FlatService();

	UpdateAccountingFlatGrid updateAccountingFlatGrid = new UpdateAccountingFlatGrid();

//		private String generalExpenses = "General";
//		private String bcExpenses = "B.C.";
//		private String cmExpenses = "C.M.";

	public abstract Grid infoGrid(int idFlatTableIntFromSelectedRow);

	public abstract int getIdFlatTableIntFromSelectedRow(Grid flatGrid);

	public abstract void setValuesBySelectedRow(Grid infoGrid, DateField expensesDateField,
			TextField expensesSumTextField, ComboBox expensesCategoryComboBox, ComboBox expensesValueComboBox,
			TextArea expensesValueTextArea);

	public abstract void addInfoGrid(HorizontalLayout expensesWindowInfoHorizontalLayout, Grid flatGrid);

	public Button addExpensesButton(String buttonCaption, Grid flatGrid) {

//			expenses sum сума витрат
//			income sum сума доходу
//			available sum оступна сума

		Button expensesButton = new Button(buttonCaption);
		expensesButton.setSizeFull();
		expensesButton.setEnabled(false);

		DateField expensesDateField = new DateField("Expenses Date");
		expensesDateField.setValue(new java.util.Date());

		expensesDateField.setDateFormat("dd.MM.yyyy");

//			expensesDateField.setResolution(Resolution.MINUTE);
//
//			expensesDateField.setDateFormat("dd.MM.yyyy HH:mm:ss");

//			expensesWindowExpensesFormLayout.addComponent(expensesDateField);

		TextField expensesSumTextField = new TextField("Expenses Sum");

//			expensesWindowExpensesFormLayout.addComponent(expensesSumTextField);

		ComboBox expensesCategoryComboBox = new ComboBox("Expenses Category");
		expensesCategoryComboBox.addItem("General");
		expensesCategoryComboBox.addItem("B.C.");
		expensesCategoryComboBox.addItem("C.M.");
		expensesCategoryComboBox.setNullSelectionAllowed(false);

//			expensesWindowExpensesFormLayout.addComponent(expensesCategoryComboBox);

		ComboBox expensesValueComboBox = new ComboBox("Expenses Value");
		expensesValueComboBox.addItem("Податки, що залишилися на нашій Львівській фірмі ЛТБ (прогонка квартир)");
		expensesValueComboBox.addItem("Маклер і оформлення в нотаріуса");
		expensesValueComboBox.addItem("Переведено на УМБ ФІНАНС (Козловському)");
		expensesValueComboBox.addItem("Передано В.С.");
		expensesValueComboBox.addItem("Передано С.М.");
		expensesValueComboBox.setNullSelectionAllowed(false);

//			expensesWindowExpensesFormLayout.addComponent(expensesValueComboBox);

		TextArea expensesValueTextArea = new TextArea("Expenses Value");
		expensesValueTextArea.setHeight("60px");
//			expensesValueTextArea.setValue("qqq");

//			expensesWindowExpensesFormLayout.addComponent(expensesValueTextArea);

		flatGrid.addSelectionListener(SelectionEvent -> {

			setValuesBySelectedRow(flatGrid, expensesDateField, expensesSumTextField, expensesCategoryComboBox,
					expensesValueComboBox, expensesValueTextArea);

			expensesButton.setEnabled(true);
		});

		expensesButton.addClickListener(e -> {

			Window expensesWindow = new Window(buttonCaption + " Window");
			expensesWindow.setHeight("600px");
			expensesWindow.setWidth("620px");

			VerticalLayout expensesWindowVerticalLayout = new VerticalLayout();
			expensesWindowVerticalLayout.setMargin(true);
			expensesWindowVerticalLayout.setSpacing(true);
			expensesWindowVerticalLayout.setSizeFull();
			expensesWindow.setContent(expensesWindowVerticalLayout);

			HorizontalLayout expensesWindowInfoHorizontalLayout = new HorizontalLayout();
			expensesWindowInfoHorizontalLayout.setHeight("103px");
			expensesWindowInfoHorizontalLayout.setWidth("100%");
			expensesWindowVerticalLayout.addComponent(expensesWindowInfoHorizontalLayout);

//				expensesWindowInfoHorizontalLayout.addComponent(infoGrid(getIdFlatTableIntFromSelectedRow(flatGrid)));

			addInfoGrid(expensesWindowInfoHorizontalLayout, flatGrid);

			HorizontalLayout expensesWindowAddInfoHorizontalLayout = new HorizontalLayout();
			expensesWindowAddInfoHorizontalLayout.setSizeFull();
			expensesWindowVerticalLayout.addComponent(expensesWindowAddInfoHorizontalLayout);
			expensesWindowVerticalLayout.setExpandRatio(expensesWindowAddInfoHorizontalLayout, 1.0f);

			Panel addExpensesWindowPanel = new Panel(buttonCaption + " Expenses");
			addExpensesWindowPanel.setSizeFull();
			expensesWindowAddInfoHorizontalLayout.addComponent(addExpensesWindowPanel);

			FormLayout expensesWindowExpensesFormLayout = new FormLayout();
			expensesWindowExpensesFormLayout.setMargin(true);
			expensesWindowExpensesFormLayout.setSizeFull();
			addExpensesWindowPanel.setContent(expensesWindowExpensesFormLayout);

			expensesWindowExpensesFormLayout.addComponent(expensesDateField);

			expensesWindowExpensesFormLayout.addComponent(expensesSumTextField);

			expensesWindowExpensesFormLayout.addComponent(expensesCategoryComboBox);

			expensesWindowExpensesFormLayout.addComponent(expensesValueComboBox);

			expensesWindowExpensesFormLayout.addComponent(expensesValueTextArea);

			HorizontalLayout expensesWindowButtonHorizontalLayout = new HorizontalLayout();

			expensesWindowButtonHorizontalLayout.setSpacing(true);
			expensesWindowButtonHorizontalLayout.setHeight("40px");
			expensesWindowButtonHorizontalLayout.setWidth("100%");

			expensesWindowVerticalLayout.addComponent(expensesWindowButtonHorizontalLayout);
			expensesWindowVerticalLayout.setComponentAlignment(expensesWindowButtonHorizontalLayout,
					Alignment.BOTTOM_CENTER);

			Button expensesAddButton = new Button(buttonCaption + " Expenses");
			expensesAddButton.setSizeFull();
			expensesAddButton.addClickListener(e1 -> {

				int availableSum = Integer.parseInt(infoGrid(getIdFlatTableIntFromSelectedRow(flatGrid))
						.getContainerDataSource().getItem(1).getItemProperty("availableSum").getValue().toString());

				if (expensesSumTextField.getValue().equals("") || expensesCategoryComboBox.isEmpty()
						|| expensesValueComboBox.isEmpty()) {

					Notification.show("Please, fill in all the fields!", Notification.Type.ERROR_MESSAGE);

				} else

				if (Integer.parseInt(expensesSumTextField.getValue()) > availableSum) {

					Notification.show("Please, change expenses sum. You imputed " + expensesSumTextField.getValue()
							+ ", but available sum " + availableSum + "!", Notification.Type.WARNING_MESSAGE);

				}

				else {

					expensesWindowInfoHorizontalLayout.removeAllComponents();

					Flat expensesFlat = new Flat();
					expensesFlat.setIdFlatTable(getIdFlatTableIntFromSelectedRow(flatGrid));
					expensesFlat.setExpensesTableDate(expensesDateField.getValue());
					expensesFlat.setExpensesTableSum(Integer.parseInt(expensesSumTextField.getValue()));
					expensesFlat.setExpensesTableCategory(expensesCategoryComboBox.getValue().toString());
//						expensesFlat.setExpensesTableValue(
//								expensesValueComboBox.getValue() + " " + expensesValueTextArea.getValue());
					expensesFlat.setExpensesTableValue(expensesValueComboBox.getValue().toString());
					expensesFlat.setExpensesTableValueTA(expensesValueTextArea.getValue());
					expensesDateField.setValue(new java.util.Date());

					try {
						flatService.createExpensesFlat(expensesFlat);

						expensesWindowInfoHorizontalLayout
								.addComponent(infoGrid(getIdFlatTableIntFromSelectedRow(flatGrid)));

						expensesSumTextField.clear();
						expensesCategoryComboBox.clear();
						expensesValueComboBox.clear();
						expensesValueTextArea.clear();

					} catch (SQLException e2) {
						// TODO Auto-generated catch block
						e2.printStackTrace();
					}

				}

				try {
					
					List<Flat> flatList = flatService.getFlatsFromOrderedFlatTable();
					
					
					updateAccountingFlatGrid.updateAccountingFlatGrid(flatGrid);
				} catch (SQLException e2) {
					// TODO Auto-generated catch block
					e2.printStackTrace();
				}
				expensesWindow.close();

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

		return expensesButton;
	}

}
