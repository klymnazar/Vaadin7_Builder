package com.example.Vaadin7_Builder;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Date;
import java.util.Iterator;
import java.util.List;

import com.example.Vaadin7_Builder.model.Flat;
import com.example.Vaadin7_Builder.service.FlatService;
import com.vaadin.data.Property.ReadOnlyException;
import com.vaadin.navigator.View;
import com.vaadin.navigator.ViewChangeListener.ViewChangeEvent;
import com.vaadin.shared.ui.grid.ColumnResizeMode;
import com.vaadin.ui.Button;
import com.vaadin.ui.Grid;
import com.vaadin.ui.Grid.FooterRow;
import com.vaadin.ui.HorizontalLayout;
import com.vaadin.ui.Label;
import com.vaadin.ui.TextField;
import com.vaadin.ui.UI;
import com.vaadin.ui.VerticalLayout;
import com.vaadin.ui.Window;

public class AccountingView extends VerticalLayout implements View {

	FlatService flatService = new FlatService();

	public AccountingView() {

		Button reportButton = new Button("Report");
		addComponent(reportButton);

		reportButton.addClickListener(e -> {
			Window reportWindow = new Window("Report");

			VerticalLayout reportWindowVerticalLayout = new VerticalLayout();
//			reportWindowVerticalLayout.setSizeFull();
			reportWindow.setContent(reportWindowVerticalLayout);

			Grid reportGrid = new Grid();
//			reportGrid.setwColumnsizeMode(ColumnResizeMode.SIMPLE);;
			reportGrid.setSizeFull();
			reportGrid.addColumn("buildingCorps", String.class);
			reportGrid.addColumn("flatNumber", Integer.class);
			reportGrid.addColumn("flatArea", Double.class);
			reportGrid.addColumn("flatCost_$", Integer.class);
			reportGrid.addColumn("generalExpenses_$", Integer.class);
//			reportGrid.addColumn("generalArea", Double.class);
			reportGrid.addColumn("BCExpenses_$", Integer.class);
			reportGrid.addColumn("BC50%generalExpenses_$", Integer.class);
			reportGrid.addColumn("BCArea", Double.class);
			reportGrid.addColumn("CMExpenses_$", Integer.class);
			reportGrid.addColumn("CM50%generalExpenses_$", Integer.class);
			reportGrid.addColumn("CMArea", Double.class);

			List<Flat> flatList = new ArrayList<>();

			double flatArea = 0;
			double BCArea = 0;
			double CMArea = 0;

			try {
//				flatList = flatService.getFlatsFromDB();

				flatList = flatService.getFlatsByFlatSetFromDB("Solded");

			} catch (SQLException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
//			Date dateFromDB = new Date();

			Iterator<Flat> itr = flatList.iterator();
			while (itr.hasNext()) {
				Flat flatFromList = itr.next();

				double BCGridArea = 0;
				double CMGridArea = 0;
				try {
					BCGridArea = (flatService.getSpendFlatInfoFromSpendTableByFlatId(flatFromList.getIdFlatTable(),
							"B.C.")
							+ (flatService.getSpendFlatInfoFromSpendTableByFlatId(flatFromList.getIdFlatTable(),
									"General") / 2))
							/ (flatFromList.getFlatCost() / flatFromList.getFlatArea());
					CMGridArea = (flatService.getSpendFlatInfoFromSpendTableByFlatId(flatFromList.getIdFlatTable(),
							"C.M.")
							+ (flatService.getSpendFlatInfoFromSpendTableByFlatId(flatFromList.getIdFlatTable(),
									"General") / 2))
							/ (flatFromList.getFlatCost() / flatFromList.getFlatArea());

				} catch (SQLException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}

				flatArea = flatArea + flatFromList.getFlatArea();

				BCArea = BCArea + BCGridArea;
				CMArea = CMArea + CMGridArea;

				try {
					reportGrid.addRow(flatFromList.getBuildingCorps(), flatFromList.getFlatNumber()
							, flatFromList.getFlatArea(), flatFromList.getFlatCost()
							, flatService.getSpendFlatInfoFromSpendTableByFlatId(flatFromList.getIdFlatTable(), "General")

							, flatService.getSpendFlatInfoFromSpendTableByFlatId(flatFromList.getIdFlatTable(), "B.C."),
							flatService.getSpendFlatInfoFromSpendTableByFlatId(flatFromList.getIdFlatTable(), "General")
									/ 2,
							BCGridArea

							, flatService.getSpendFlatInfoFromSpendTableByFlatId(flatFromList.getIdFlatTable(), "C.M."),
							flatService.getSpendFlatInfoFromSpendTableByFlatId(flatFromList.getIdFlatTable(), "General")
									/ 2,
							CMGridArea

					);

				} catch (SQLException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}

			}

			FooterRow reportGridFooterRow = reportGrid.prependFooterRow();
			reportGridFooterRow.getCell("flatArea").setText("Total: " + flatArea);
			reportGridFooterRow.getCell("BCArea").setText("Total: " + BCArea);
			reportGridFooterRow.getCell("CMArea").setText("Total: " + CMArea);
//			reportGridFooterRow.getCell("flatArea").setText("Total: " + flatArea);
//			reportGridFooterRow.getCell("flatArea").setText("Total: " + flatArea);

			reportWindowVerticalLayout.addComponent(reportGrid);

			
			
			
			
			TextField allFlatsAreaTextField = new TextField("All Flats Area");
			try {
				allFlatsAreaTextField.setValue(String.valueOf(flatService.sumAllFlatsArea()));
			} catch (ReadOnlyException | SQLException e3) {
				// TODO Auto-generated catch block
				e3.printStackTrace();
			}
			allFlatsAreaTextField.setEnabled(false);

			reportWindowVerticalLayout.addComponent(allFlatsAreaTextField);

			Grid distributionGrid = new Grid();
//			distributionGrid.setHeightByRows(2.0);
			distributionGrid.setSizeFull();
			distributionGrid.addColumn("name", String.class);
			distributionGrid.addColumn("flatArea", Double.class);

			distributionGrid.addColumn("receivedArea", Double.class);

			distributionGrid.addColumn("residualArea", Double.class);

			distributionGrid.addRow("B.C. 70%", Double.parseDouble(allFlatsAreaTextField.getValue()) * 70 / 100, BCArea,
					Double.parseDouble(allFlatsAreaTextField.getValue()) * 70 / 100 - BCArea);
			distributionGrid.addRow("C.M. 30%", Double.parseDouble(allFlatsAreaTextField.getValue()) * 30 / 100, CMArea,
					Double.parseDouble(allFlatsAreaTextField.getValue()) * 30 / 100 - CMArea);

			reportWindowVerticalLayout.addComponent(distributionGrid);

			HorizontalLayout reportInfoWindowHorizontalLayout = new HorizontalLayout();
			reportWindowVerticalLayout.addComponent(reportInfoWindowHorizontalLayout);

			reportWindow.center();
			UI.getCurrent().addWindow(reportWindow);
		});

	}

	@Override
	public void enter(ViewChangeEvent event) {
		// TODO Auto-generated method stub

	}

}
