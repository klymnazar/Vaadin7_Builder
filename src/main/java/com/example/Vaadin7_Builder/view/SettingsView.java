package com.example.Vaadin7_Builder.view;

import java.sql.SQLException;

import com.example.Vaadin7_Builder.model.Flat;
import com.example.Vaadin7_Builder.service.FlatService;
import com.vaadin.navigator.View;
import com.vaadin.navigator.ViewChangeListener.ViewChangeEvent;
import com.vaadin.ui.Button;
import com.vaadin.ui.HorizontalLayout;
import com.vaadin.ui.Panel;
import com.vaadin.ui.TextField;
import com.vaadin.ui.UI;
import com.vaadin.ui.VerticalLayout;

public class SettingsView extends VerticalLayout implements View {

	FlatService flatService = new FlatService();

	public SettingsView() {

		TextField corpsTextField = new TextField("Building Corps");

		addComponent(corpsTextField);

		Button addCorpsButton = new Button("Add Corps");
		addCorpsButton.addClickListener(click -> {

			Flat flat = new Flat();

			flat.setBuildingCorps(corpsTextField.getValue());

			try {
				flatService.createSettings(flat);
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}

			corpsTextField.clear();

		});
		addComponent(addCorpsButton);

		Panel generalCostSharingPanel = new Panel("General cost sharing");
		addComponent(generalCostSharingPanel);

		HorizontalLayout generalCostSharingPanelHorizontalLayout = new HorizontalLayout();
		generalCostSharingPanelHorizontalLayout.setSpacing(true);
		generalCostSharingPanel.setContent(generalCostSharingPanelHorizontalLayout);

		TextField bcPartTextField = new TextField("B.C. Part");

		generalCostSharingPanelHorizontalLayout.addComponent(bcPartTextField);

		TextField cmPartTextField = new TextField("C.M. Part");
		cmPartTextField.setEnabled(false);
//		Integer cmPart = 100 - Integer.parseInt(bcPartTextField.getValue());
//		cmPartTextField.setValue(cmPart.toString());
		generalCostSharingPanelHorizontalLayout.addComponent(cmPartTextField);

		Button acceptButton = new Button("Accept");
		acceptButton.addClickListener(click -> {
			Integer cmPart = 100 - Integer.parseInt(bcPartTextField.getValue());
			cmPartTextField.setValue(cmPart.toString());

		});
		generalCostSharingPanelHorizontalLayout.addComponent(acceptButton);

		Button cancelButton = new Button("Cancel");
		cancelButton.addClickListener(click -> {
			UI.getCurrent().getNavigator().navigateTo("main");
		});
		addComponent(cancelButton);

	}

	@Override
	public void enter(ViewChangeEvent event) {
		// TODO Auto-generated method stub

	}

}
