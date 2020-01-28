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
import com.vaadin.ui.DateField;
import com.vaadin.ui.Grid;
import com.vaadin.ui.HorizontalLayout;
import com.vaadin.ui.UI;
import com.vaadin.ui.VerticalLayout;
import com.vaadin.ui.Window;
import com.vaadin.ui.Grid.FooterRow;

public class PaymentInfoButton {

	FlatService flatService = new FlatService();

	AddBankInfoButton addBankInfoButton = new AddBankInfoButton();

	DecimalFormat decimalFormat = new DecimalFormat("0.00");

	public Grid bankInfoWindowPaymentInfoGrid(List<Flat> paymentList) {

		Grid bankInfoWindowPaymentInfoGrid = new Grid();
		bankInfoWindowPaymentInfoGrid.setSizeFull();

		bankInfoWindowPaymentInfoGrid.addColumn("paymentDate", String.class);
		bankInfoWindowPaymentInfoGrid.addColumn("paymentSum", Double.class);
		bankInfoWindowPaymentInfoGrid.addColumn("contractNumber", String.class);
		bankInfoWindowPaymentInfoGrid.addColumn("payer", String.class);

		double paymentSum = 0;

		try {

			Iterator<Flat> itr = paymentList.iterator();
			while (itr.hasNext()) {
				Flat flatFromList = itr.next();

				paymentSum = paymentSum + flatFromList.getBankTablePaymentSum();

				bankInfoWindowPaymentInfoGrid.addRow(
						flatService.dateFormatForGrid(flatFromList.getBankTablePaymentDate()),
						flatFromList.getBankTablePaymentSum(),
						flatService.getFlatByFlatIdFromFlatBuyerTable(flatFromList.getIdFlatTable())
								.getFlatContractNumber()
								+ " from "
								+ flatService.getFlatByFlatIdFromFlatBuyerTable(flatFromList.getIdFlatTable())
										.getFlatContractDate(),
						flatService.getFlatByFlatIdFromFlatBuyerTable(flatFromList.getIdFlatTable())
								.getFlatBuyerFirstname()
								+ " "
								+ flatService.getFlatByFlatIdFromFlatBuyerTable(flatFromList.getIdFlatTable())
										.getFlatBuyerLastname()
								+ " " + flatService.getFlatByFlatIdFromFlatBuyerTable(flatFromList.getIdFlatTable())
										.getFlatBuyerSurname()

				);

			}
			FooterRow bankInfoWindowPaymentInfoGridFooterRow = bankInfoWindowPaymentInfoGrid.prependFooterRow();

			bankInfoWindowPaymentInfoGridFooterRow.getCell("paymentDate").setText("Total: ");
			bankInfoWindowPaymentInfoGridFooterRow.getCell("paymentSum")
					.setText(decimalFormat.format(paymentSum).replace(",", ".") + " hrn");

		} catch (SQLException e3) {
			// TODO Auto-generated catch block
			e3.printStackTrace();
		}

		return bankInfoWindowPaymentInfoGrid;
	}

	public Button paymentInfoButton() {

		Button paymentInfoButton = new Button("Payment Info");
		paymentInfoButton.setSizeFull();

		paymentInfoButton.addClickListener(e1 -> {

			Window bankInfoWindow = new Window("Payment Info");
			bankInfoWindow.setWidth("700px");
			bankInfoWindow.setHeight("500px");

			VerticalLayout bankInfoWindowVerticalLayout = new VerticalLayout();
			bankInfoWindowVerticalLayout.setMargin(true);
			bankInfoWindowVerticalLayout.setSizeFull();
			bankInfoWindow.setContent(bankInfoWindowVerticalLayout);

			HorizontalLayout bankInfoWindowDateHorizontalLayout = new HorizontalLayout();
			bankInfoWindowDateHorizontalLayout.setSpacing(true);
			bankInfoWindowDateHorizontalLayout.setHeight("65px");
			bankInfoWindowDateHorizontalLayout.setWidth("100%");
			bankInfoWindowVerticalLayout.addComponent(bankInfoWindowDateHorizontalLayout);

			DateField bankPaymentDateFromDateField = new DateField("From Payment Date");
			bankPaymentDateFromDateField.setValue(new java.util.Date());
			bankPaymentDateFromDateField.setDateFormat("dd.MM.yyyy");
			bankInfoWindowDateHorizontalLayout.addComponent(bankPaymentDateFromDateField);
			bankInfoWindowDateHorizontalLayout.setComponentAlignment(bankPaymentDateFromDateField,
					Alignment.BOTTOM_CENTER);

			DateField bankPaymentDateToDateField = new DateField("To Payment Date");
			bankPaymentDateToDateField.setValue(new java.util.Date());
			bankPaymentDateToDateField.setDateFormat("dd.MM.yyyy");
			bankInfoWindowDateHorizontalLayout.addComponent(bankPaymentDateToDateField);
			bankInfoWindowDateHorizontalLayout.setComponentAlignment(bankPaymentDateToDateField,
					Alignment.BOTTOM_CENTER);

			HorizontalLayout bankInfoWindowContractHorizontalLayout = new HorizontalLayout();
			bankInfoWindowContractHorizontalLayout.setSpacing(true);
			bankInfoWindowContractHorizontalLayout.setHeight("65px");
			bankInfoWindowContractHorizontalLayout.setWidth("100%");
			bankInfoWindowVerticalLayout.addComponent(bankInfoWindowContractHorizontalLayout);

			ComboBox bankInfoWindowContractComboBox = new ComboBox();
			bankInfoWindowContractComboBox = addBankInfoButton.bankInfoWindowContractComboBox();
			bankInfoWindowContractHorizontalLayout.addComponent(bankInfoWindowContractComboBox);
			bankInfoWindowContractHorizontalLayout.setComponentAlignment(bankInfoWindowContractComboBox,
					Alignment.BOTTOM_CENTER);

			HorizontalLayout bankInfoWindowPreGridHorizontalLayout = new HorizontalLayout();
			bankInfoWindowPreGridHorizontalLayout.setHeight("5px");
			bankInfoWindowPreGridHorizontalLayout.setWidth("100%");
			bankInfoWindowVerticalLayout.addComponent(bankInfoWindowPreGridHorizontalLayout);

			HorizontalLayout bankInfoWindowGridHorizontalLayout = new HorizontalLayout();
			bankInfoWindowGridHorizontalLayout.setSizeFull();
			bankInfoWindowVerticalLayout.addComponent(bankInfoWindowGridHorizontalLayout);
			bankInfoWindowVerticalLayout.setExpandRatio(bankInfoWindowGridHorizontalLayout, 1.0f);

			Button bankInfoWindowSelectDateButton = new Button("Select");
			bankInfoWindowSelectDateButton.setWidth("185px");
			bankInfoWindowDateHorizontalLayout.addComponent(bankInfoWindowSelectDateButton);
			bankInfoWindowDateHorizontalLayout.setComponentAlignment(bankInfoWindowSelectDateButton,
					Alignment.BOTTOM_CENTER);

			bankInfoWindowSelectDateButton.addClickListener(click -> {

				bankInfoWindowGridHorizontalLayout.removeAllComponents();

				String fromDate = flatService.dateFormatForDB(bankPaymentDateFromDateField.getValue());
				String toDate = flatService.dateFormatForDB(bankPaymentDateToDateField.getValue());

				try {
					bankInfoWindowGridHorizontalLayout.addComponent(bankInfoWindowPaymentInfoGrid(
							flatService.getSelectedItemsByDateFromBankTable(fromDate, toDate)));

				} catch (SQLException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}

			});

			Button bankInfoWindowSelectContractButton = new Button("Select");
			bankInfoWindowSelectContractButton.setEnabled(false);
			bankInfoWindowSelectContractButton.setWidth("185px");
			bankInfoWindowContractHorizontalLayout.addComponent(bankInfoWindowSelectContractButton);
			bankInfoWindowContractHorizontalLayout.setComponentAlignment(bankInfoWindowSelectContractButton,
					Alignment.BOTTOM_CENTER);

			addBankInfoButton.bankInfoWindowContractComboBox().addValueChangeListener(event -> {

				String contractTextFromComboBox = addBankInfoButton.bankInfoWindowContractComboBox().getValue()
						.toString();

				if (contractTextFromComboBox.equals("")) {
					bankInfoWindowSelectContractButton.setEnabled(false);
				} else {
					bankInfoWindowSelectContractButton.setEnabled(true);
				}

			});

			bankInfoWindowSelectContractButton.addClickListener(click -> {

				bankInfoWindowGridHorizontalLayout.removeAllComponents();

				String contractTextFromComboBox = addBankInfoButton.bankInfoWindowContractComboBox().getValue()
						.toString();

				String[] contractNumberDate = contractTextFromComboBox.split(" from ");

//				String contractDate = flatService.dateFormatForDB(contractNumberDate[1]);

				try {
					
					
					bankInfoWindowGridHorizontalLayout
					.addComponent(bankInfoWindowPaymentInfoGrid(flatService.getFlatsFromBankTableByIdFlatTable(
							flatService.getFlatFromFlatBuyerTableByContract(contractNumberDate[0]).getIdFlatTable())));
					

//					bankInfoWindowGridHorizontalLayout
//							.addComponent(bankInfoWindowPaymentInfoGrid(flatService.getFlatsFromBankTableByIdFlatTable(
//									flatService.getFlatFromFlatBuyerTableByContract(contractNumberDate[0], contractDate)
//											.getIdFlatTable())));

				} catch (SQLException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}

			});

			Button bankInfoWindowSelectAllButton = new Button("Select All");
			bankInfoWindowSelectAllButton.setWidth("185px");
			bankInfoWindowContractHorizontalLayout.addComponent(bankInfoWindowSelectAllButton);
			bankInfoWindowContractHorizontalLayout.setComponentAlignment(bankInfoWindowSelectAllButton,
					Alignment.BOTTOM_CENTER);

			bankInfoWindowSelectAllButton.addClickListener(click -> {

				bankInfoWindowGridHorizontalLayout.removeAllComponents();

				List<Flat> paymentList = new ArrayList<>();
				try {
					paymentList = flatService.getPaymentsFromBankTable();
					bankInfoWindowGridHorizontalLayout.addComponent(bankInfoWindowPaymentInfoGrid(paymentList));
				} catch (SQLException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}

			});

			bankInfoWindow.center();

			UI.getCurrent().addWindow(bankInfoWindow);

		});

		return paymentInfoButton;
	}

}
