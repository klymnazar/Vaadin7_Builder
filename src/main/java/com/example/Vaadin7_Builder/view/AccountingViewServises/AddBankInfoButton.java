package com.example.Vaadin7_Builder.view.AccountingViewServises;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import com.example.Vaadin7_Builder.model.Flat;
import com.example.Vaadin7_Builder.service.FlatService;
import com.vaadin.shared.ui.datefield.Resolution;
import com.vaadin.ui.Button;
import com.vaadin.ui.ComboBox;
import com.vaadin.ui.DateField;
import com.vaadin.ui.FormLayout;
import com.vaadin.ui.HorizontalLayout;
import com.vaadin.ui.TextField;
import com.vaadin.ui.UI;
import com.vaadin.ui.VerticalLayout;
import com.vaadin.ui.Window;

public class AddBankInfoButton {

	FlatService flatService = new FlatService();

	private ComboBox bankContractNumberComboBox = new ComboBox("Contract Number");

	public ComboBox bankInfoWindowContractComboBox() {

		List<Flat> flatBuyerList = new ArrayList<>();

		try {
			flatBuyerList = flatService.getFlatsFromFlatBuyerTable();
			Iterator<Flat> iter = flatBuyerList.iterator();
			while (iter.hasNext()) {
				Flat flatFromList = iter.next();

				bankContractNumberComboBox.addItem(flatFromList.getFlatContractNumber() + " from "

						+ flatService.dateFormatForGrid(flatFromList.getFlatContractDate()));

				bankContractNumberComboBox.setNullSelectionAllowed(false);
			}

		} catch (SQLException e3) {
			// TODO Auto-generated catch block
			e3.printStackTrace();
		}

		return bankContractNumberComboBox;
	}

	public Button addBankInfoButton() {

		Button addBankInfoButton = new Button("Add Bank Info");
		addBankInfoButton.setSizeFull();

		addBankInfoButton.addClickListener(e1 -> {

			Window bankInfoWindow = new Window("Bank Info");
			bankInfoWindow.setWidth("450px");
			bankInfoWindow.setHeight("410px");

			VerticalLayout bankInfoWindowVerticalLayout = new VerticalLayout();

			bankInfoWindow.setContent(bankInfoWindowVerticalLayout);

			FormLayout bankInfoWindowFormLayout = new FormLayout();
			bankInfoWindowFormLayout.setSizeFull();
			bankInfoWindowFormLayout.setMargin(true);

			bankInfoWindowFormLayout.setSizeFull();

			DateField bankPaymentDateDateField = new DateField("Payment Date");
			bankPaymentDateDateField.setValue(new java.util.Date());

			bankPaymentDateDateField.setResolution(Resolution.MINUTE);

			bankPaymentDateDateField.setDateFormat("dd.MM.yyyy HH:mm:ss");
			bankInfoWindowFormLayout.addComponent(bankPaymentDateDateField);

			TextField bankPaymentSumTextField = new TextField("Payment Sum");
			bankInfoWindowFormLayout.addComponent(bankPaymentSumTextField);

			bankInfoWindowFormLayout.addComponent(bankInfoWindowContractComboBox());

			TextField bankPayerTextField = new TextField("Payer Info");

			bankInfoWindowContractComboBox().addValueChangeListener(select -> {

				Flat paymentFlat = new Flat();

				String contractText = bankInfoWindowContractComboBox().getValue().toString();

				String[] contractNumberDate = contractText.split(" from ");

//				String contractDateReplace = contractNumberDate[1].replace(".", "-");
//
//				String[] contractDate = contractDateReplace.split("-");
//
//				String contractDateNew = contractDate[2] + "." + contractDate[1] + "." + contractDate[0];

				try {
					
					paymentFlat = flatService.getFlatByFlatIdFromFlatBuyerTable(
							flatService.getFlatFromFlatBuyerTableByContract(contractNumberDate[0])
									.getIdFlatTable());
					
//					paymentFlat = flatService.getFlatByFlatIdFromFlatBuyerTable(
//							flatService.getFlatFromFlatBuyerTableByContract(contractNumberDate[0], contractDateNew)
//									.getIdFlatTable());

				} catch (SQLException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}

				bankPayerTextField.setValue(paymentFlat.getFlatBuyerFirstname() + " "
						+ paymentFlat.getFlatBuyerLastname() + " " + paymentFlat.getFlatBuyerSurname());

			});

			bankPayerTextField.setEnabled(false);

			bankInfoWindowFormLayout.addComponent(bankPayerTextField);

			bankInfoWindowVerticalLayout.addComponent(bankInfoWindowFormLayout);

			HorizontalLayout bankInfoWindowHorizontalLayout = new HorizontalLayout();
			bankInfoWindowHorizontalLayout.setSizeFull();
			bankInfoWindowHorizontalLayout.setMargin(true);
			bankInfoWindowHorizontalLayout.setSpacing(true);
			bankInfoWindowVerticalLayout.addComponent(bankInfoWindowHorizontalLayout);

			Button bankInfoWindowPaymentButton = new Button("Add Payment");
			bankInfoWindowPaymentButton.setSizeFull();
			bankInfoWindowPaymentButton.setEnabled(false);

			bankPaymentSumTextField.addValueChangeListener(event -> {

				if (bankPaymentSumTextField.getValue().equals("")) {
					bankInfoWindowPaymentButton.setEnabled(false);
				} else {

					bankPayerTextField.addValueChangeListener(event1 -> {

						if (bankPayerTextField.getValue().equals("")) {
							bankInfoWindowPaymentButton.setEnabled(false);
						} else {
							bankInfoWindowPaymentButton.setEnabled(true);
						}

					});
				}

			});

			bankInfoWindowPaymentButton.addClickListener(click -> {

				Flat paymentFlat = new Flat();

				String contractText = bankInfoWindowContractComboBox().getValue().toString();

				String[] contractNumberDate = contractText.split(" from ");

//				String contractDateReplace = contractNumberDate[1].replace(".", "-");
//
//				String[] contractDate = contractDateReplace.split("-");
//
//				String contractDateNew = contractDate[2] + "." + contractDate[1] + "." + contractDate[0];

				try {
					
					paymentFlat.setIdFlatTable(
							flatService.getFlatFromFlatBuyerTableByContract(contractNumberDate[0]).getIdFlatTable());
					
					
//					paymentFlat.setIdFlatTable(
//							flatService.getFlatFromFlatBuyerTableByContract(contractNumberDate[0], contractDateNew)
//									.getIdFlatTable());
					paymentFlat.setBankTablePaymentDate(bankPaymentDateDateField.getValue());
					paymentFlat.setBankTablePaymentSum(Double.parseDouble(bankPaymentSumTextField.getValue()));
					flatService.createBankPayment(paymentFlat);

				} catch (SQLException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}

				bankInfoWindow.close();
				bankInfoWindowPaymentButton.setEnabled(false);

			});

			bankInfoWindowHorizontalLayout.addComponent(bankInfoWindowPaymentButton);

			Button bankInfoWindowCancelButton = new Button("Cancel");
			bankInfoWindowCancelButton.setSizeFull();

			bankInfoWindowCancelButton.addClickListener(event -> {
				bankInfoWindow.close();
			});

			bankInfoWindowHorizontalLayout.addComponent(bankInfoWindowCancelButton);

			bankInfoWindow.center();

			UI.getCurrent().addWindow(bankInfoWindow);

		});

		return addBankInfoButton;
	}

}
