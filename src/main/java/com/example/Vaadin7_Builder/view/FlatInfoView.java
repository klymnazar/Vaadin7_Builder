package com.example.Vaadin7_Builder.view;

import java.sql.SQLException;
import java.text.DateFormat;
import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Date;
import java.util.Iterator;
import java.util.List;
import java.util.Locale;

import com.example.Vaadin7_Builder.model.Flat;
import com.example.Vaadin7_Builder.service.FlatService;
import com.vaadin.data.validator.DoubleValidator;
import com.vaadin.data.validator.IntegerValidator;
import com.vaadin.navigator.View;
import com.vaadin.navigator.ViewChangeListener.ViewChangeEvent;
import com.vaadin.server.ThemeResource;
import com.vaadin.ui.Alignment;
import com.vaadin.ui.Button;
import com.vaadin.ui.CheckBox;
import com.vaadin.ui.DateField;
import com.vaadin.ui.FormLayout;
import com.vaadin.ui.Grid;
import com.vaadin.ui.Grid.Column;
import com.vaadin.ui.Grid.FooterRow;
import com.vaadin.ui.Grid.SingleSelectionModel;
import com.vaadin.ui.GridLayout;
import com.vaadin.ui.GridLayout.OutOfBoundsException;
import com.vaadin.ui.GridLayout.OverlapsException;
import com.vaadin.ui.HorizontalLayout;
import com.vaadin.ui.Image;
import com.vaadin.ui.Layout;
import com.vaadin.ui.Panel;
import com.vaadin.ui.TextField;
import com.vaadin.ui.UI;
import com.vaadin.ui.VerticalLayout;
import com.vaadin.ui.Window;
import com.vaadin.ui.renderers.DateRenderer;

public class FlatInfoView extends VerticalLayout implements View {

	private FlatService flatService = new FlatService();

	private Grid flatGrid = new Grid();

	private String flatSetReserved = "Reserved";
	private String flatSetSolded = "Solded";
	private String flatSetFree = "";

	private GridLayout flatInfoGridLayout = new GridLayout(1, 15);

	private DecimalFormat decimalFormat = new DecimalFormat("0.00");
	
	public FlatInfoView() throws SQLException {

		setSizeFull();
		flatInfoGridLayout.setSizeFull();
		flatInfoGridLayout.setMargin(true);
		flatInfoGridLayout.setSpacing(true);
		addComponent(flatInfoGridLayout);

		flatGrid = flatGridFlatInfoView(flatService.getFlatsFromFlatTableAndFlatBuyerDB());

		flatInfoGridLayout.addComponent(flatGrid, 0, 0, 0, 12);

		checkBoxHorizontalLayout();

		flatButtonHorizontalLayout();

	}

	public Grid flatGridFlatInfoView(List<Flat> flatList) throws SQLException {

		Grid flatGrid = new Grid();

		flatGrid.setSizeFull();

		flatGrid.addColumn("№", Integer.class);
		flatGrid.addColumn("idFlatTable", Integer.class);
		flatGrid.addColumn("buildingCorps", String.class);
		flatGrid.addColumn("flatRooms", Integer.class);
		flatGrid.addColumn("flatFloor", Integer.class);
		flatGrid.addColumn("flatNumber", Integer.class);
		flatGrid.addColumn("flatArea", Double.class);
		flatGrid.addColumn("flatSet", String.class);

		flatGrid.addColumn("flatContractDate", Date.class);
		
		flatGrid.getColumn("idFlatTable").setHidden(true);

		flatGrid.getColumn("flatContractDate").setHeaderCaption("Flat Contract Date")
				.setRenderer(new DateRenderer("%1$td.%1$tm.%1$tY"));
		
		int number = 1;
		double flatArea = 0;

		Iterator<Flat> itr = flatList.iterator();
		while (itr.hasNext()) {
			Flat flatFromList = itr.next();
			flatGrid.addRow(number, flatFromList.getIdFlatTable(), flatFromList.getBuildingCorps(), flatFromList.getFlatRooms(),
					flatFromList.getFlatFloor(), flatFromList.getFlatNumber(), flatFromList.getFlatArea(),
					flatFromList.getFlatSet()
					, flatFromList.getFlatContractDate()
			);
			number ++;
			flatArea = flatArea + flatFromList.getFlatArea();
		}
		
		FooterRow flatGridFooterRow = flatGrid.prependFooterRow();
		flatGridFooterRow.getCell("flatArea").setText("Total: " + decimalFormat.format(flatArea).replace(",", "."));
		

		return flatGrid;

	}

	
	public void updateFlatInfoPage() throws SQLException {

		flatInfoGridLayout.removeComponent(flatGrid);
		flatInfoGridLayout.removeComponent(0, 14);
		flatGrid = flatGridFlatInfoView(flatService.getFlatsFromFlatTableAndFlatBuyerDB());
		flatInfoGridLayout.addComponent(flatGrid, 0, 0, 0, 12);
		flatButtonHorizontalLayout();

	}
	
	
	public Grid checkBoxEvent(CheckBox nameEnableCheckBox, CheckBox name1DisableCheckBox, CheckBox name2DisableCheckBox,
			String flatSet) {

		nameEnableCheckBox.addValueChangeListener(event -> {

			flatInfoGridLayout.removeComponent(flatGrid);
			flatInfoGridLayout.removeComponent(0, 14);

			if (nameEnableCheckBox.isEmpty()) {

				name1DisableCheckBox.setEnabled(true);
				name2DisableCheckBox.setEnabled(true);

				try {

					flatGrid = flatGridFlatInfoView(flatService.getFlatsFromFlatTableAndFlatBuyerDB());

				} catch (SQLException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			} else {
				name1DisableCheckBox.setEnabled(false);
				name2DisableCheckBox.setEnabled(false);

				try {

					flatGrid = flatGridFlatInfoView(flatService.getFlatsByFlatSetFromDB(flatSet));

				} catch (SQLException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
			

			try {
				flatInfoGridLayout.addComponent(flatGrid, 0, 0, 0, 12);
				flatButtonHorizontalLayout();
			} catch (OverlapsException | OutOfBoundsException | SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}

		});

		return flatGrid;
	}

	public Layout checkBoxHorizontalLayout() {

		HorizontalLayout checkBoxHorizontalLayout = new HorizontalLayout();
		checkBoxHorizontalLayout.setSpacing(true);
		checkBoxHorizontalLayout.setWidth("100%");
		checkBoxHorizontalLayout.setHeight("50px");

		CheckBox freeCheckBox = new CheckBox("Free flats");
		CheckBox soldCheckBox = new CheckBox("Solded flats");
		CheckBox reservCheckBox = new CheckBox("Reserved flats");

		checkBoxEvent(freeCheckBox, soldCheckBox, reservCheckBox, flatSetFree);
		checkBoxHorizontalLayout.addComponent(freeCheckBox);

		checkBoxEvent(soldCheckBox, freeCheckBox, reservCheckBox, flatSetSolded);
		checkBoxHorizontalLayout.addComponent(soldCheckBox);

		checkBoxEvent(reservCheckBox, soldCheckBox, freeCheckBox, flatSetReserved);
		checkBoxHorizontalLayout.addComponent(reservCheckBox);

		flatInfoGridLayout.addComponent(checkBoxHorizontalLayout, 0, 13, 0, 13);
		flatInfoGridLayout.setComponentAlignment(checkBoxHorizontalLayout, Alignment.BOTTOM_CENTER);
		
		return checkBoxHorizontalLayout;
	}

	public Layout flatButtonHorizontalLayout() throws SQLException {

		HorizontalLayout buttonHorizontalLayout = new HorizontalLayout();
		buttonHorizontalLayout.setSpacing(true);
		buttonHorizontalLayout.setWidth("100%");
		buttonHorizontalLayout.setHeight("50px");

		buttonHorizontalLayout.addComponent(reservFlatButton());

		buttonHorizontalLayout.addComponent(cancelReservFlatButton());

		buttonHorizontalLayout.addComponent(saleFlatButton());

		buttonHorizontalLayout.addComponent(cancelButton());

		flatInfoGridLayout.addComponent(buttonHorizontalLayout, 0, 14, 0, 14);
		flatInfoGridLayout.setComponentAlignment(buttonHorizontalLayout, Alignment.MIDDLE_CENTER);
		
		return buttonHorizontalLayout;
	}

	public int getIdFlatTableFromSelectedRow() throws SQLException {

		int idFlatTableFromSelectedRow;

		Grid grid = flatGrid;

		Object selected = ((SingleSelectionModel) grid.getSelectionModel()).getSelectedRow();
		String idFlatTableFromSelectedRowS = grid.getContainerDataSource().getItem(selected)
				.getItemProperty("idFlatTable").getValue().toString();

		idFlatTableFromSelectedRow = Integer.parseInt(idFlatTableFromSelectedRowS);

		return idFlatTableFromSelectedRow;

	}

	public void buttonHorizontalLayoutEvent(Button nameButton, String flatSetOld, String flatSetNew) {

		nameButton.setEnabled(false);

		flatGrid.addSelectionListener(SelectionEvent -> {

			try {
				String flatSetFromFlatGrid = flatService.getFlatByFlatIdFromFlatTable(getIdFlatTableFromSelectedRow())
						.getFlatSet();
				if (flatSetFromFlatGrid.equals(flatSetOld)) {
					nameButton.setEnabled(true);
					
				} else {
					nameButton.setEnabled(false);
				}
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}

		});

		nameButton.addClickListener(e1 -> {

			try {
		
				flatService.updateFlatSetByFlatIdInFlatTable(getIdFlatTableFromSelectedRow(), flatSetNew);

				updateFlatInfoPage();
				
			} catch (NumberFormatException | SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}

		});
	}

	public Button reservFlatButton() {

		Button reservFlatButton = new Button("Reserv Flat");
		reservFlatButton.setSizeFull();

		buttonHorizontalLayoutEvent(reservFlatButton, flatSetFree, flatSetReserved);

		return reservFlatButton;
	}

	public Button cancelReservFlatButton() {

		Button canselReservFlatButton = new Button("Cancel Flat Reserv");
		canselReservFlatButton.setSizeFull();

		buttonHorizontalLayoutEvent(canselReservFlatButton, flatSetReserved, flatSetFree);

		return canselReservFlatButton;
	}

	public Button saleFlatButton() throws SQLException {

		Button saleFlatButton = new Button("Sale Flat");
		saleFlatButton.setSizeFull();

		saleFlatButton.setEnabled(false);

		flatGrid.addSelectionListener(SelectionEvent -> {

			try {
				String flatSetFromFlatGrid = flatService.getFlatByFlatIdFromFlatTable(getIdFlatTableFromSelectedRow())
						.getFlatSet();
				if (flatSetFromFlatGrid.equals(flatSetFree) || flatSetFromFlatGrid.equals(flatSetReserved)) {
					saleFlatButton.setEnabled(true);
				} else {
					saleFlatButton.setEnabled(false);
				}
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}

		});

		saleFlatButton.addClickListener(e -> {

			Window saleFlatWindow = new Window("Sale Flat");
			saleFlatWindow.setWidth("900px");
			saleFlatWindow.setHeight("600px");

			GridLayout saleFlatWindowGridLayout = new GridLayout(2, 12);
			
			saleFlatWindowGridLayout.setSizeFull();
			saleFlatWindowGridLayout.setMargin(true);
			saleFlatWindowGridLayout.setSpacing(true);
			
			saleFlatWindow.setContent(saleFlatWindowGridLayout);
			
			FormLayout saleFlatWindowFormLayout = new FormLayout();
			saleFlatWindowFormLayout.setMargin(true);

			saleFlatWindowFormLayout.setSizeFull();

			DateField saleFlatDateField = new DateField("Contract Date");
//			SimpleDateFormat dateFormat = new SimpleDateFormat ("dd.MM.yyyy HH:mm:ss Ze");
//			dateFormat.format(new Date());
//			saleFlatDateField.setLocale(new Locale("ua", "UA"));
//			LocalDateTime localDateTime = LocalDateTime.now();
//			saleFlatDateField.setDate(dateFormat.format(new Date()));
			
			
			saleFlatDateField.setValue(new java.util.Date());

			
//			saleFlatDateField.setDateFormat("");
			
			saleFlatDateField.setDateFormat("dd.MM.yyyy");
//			saleFlatDateField.setDateFormat("dd.MM.yyyy HH:mm:ss Ze");
			saleFlatWindowFormLayout.addComponent(saleFlatDateField);

			TextField firstNameTextField = new TextField("First Name");
			saleFlatWindowFormLayout.addComponent(firstNameTextField);

			TextField lastNameTextField = new TextField("Last Name");
			saleFlatWindowFormLayout.addComponent(lastNameTextField);

			TextField surNameTextField = new TextField("Surname");
			saleFlatWindowFormLayout.addComponent(surNameTextField);

			TextField contractNumberTextField = new TextField("Contract Number");
			saleFlatWindowFormLayout.addComponent(contractNumberTextField);

			TextField costTextField = new TextField("Cost, $");
			saleFlatWindowFormLayout.addComponent(costTextField);

			Panel saleFlatBuyerInfoPanel = new Panel("Buyer Info");
			saleFlatBuyerInfoPanel.setSizeFull();

			saleFlatWindowGridLayout.addComponent(saleFlatBuyerInfoPanel, 0, 0, 0, 10);
			saleFlatBuyerInfoPanel.setContent(saleFlatWindowFormLayout);
			
			
			Panel saleFlatInfoGridPanel = new Panel("Flat Info");
			saleFlatInfoGridPanel.setSizeFull();
			
			VerticalLayout saleFlatWindowInfoGridPanelVerticalLayout = new VerticalLayout();
			saleFlatWindowInfoGridPanelVerticalLayout.setSizeFull();
			
			saleFlatInfoGridPanel.setContent(saleFlatWindowInfoGridPanelVerticalLayout);
			
			Grid saleFlatInfoGrid = new Grid();
			saleFlatInfoGrid.setHeight("230px");
			saleFlatInfoGrid.setWidth("100%");
			

			saleFlatInfoGrid.addColumn("Name", String.class);
			saleFlatInfoGrid.addColumn("Value", String.class);
			

			List<Column> flatGridColumnNameList = new ArrayList<>();

			flatGridColumnNameList.add(0, flatGrid.getColumn("buildingCorps"));
			flatGridColumnNameList.add(1, flatGrid.getColumn("flatRooms"));
			flatGridColumnNameList.add(2, flatGrid.getColumn("flatFloor"));
			flatGridColumnNameList.add(3, flatGrid.getColumn("flatNumber"));
			flatGridColumnNameList.add(4, flatGrid.getColumn("flatArea"));

			int elementFromList = 0;
			
			Iterator<Column> itr = flatGridColumnNameList.iterator();
			while (itr.hasNext()) {
				Column columnNameFromList = itr.next();

				Flat selectedFlat = new Flat();

				try {
		
					selectedFlat = flatService.getFlatByFlatIdFromFlatTableShortInfo(getIdFlatTableFromSelectedRow());

				} catch (SQLException e2) {
					// TODO Auto-generated catch block
					e2.printStackTrace();
				}

				try {
					saleFlatInfoGrid.addRow(flatGridColumnNameList.get(elementFromList).getHeaderCaption(),
							flatService.getFlatFromDbByColumnIndex(elementFromList+1, selectedFlat));
				} catch (IllegalStateException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}

				elementFromList++;
			}
			
			saleFlatWindowGridLayout.addComponent(saleFlatInfoGridPanel, 1, 0, 1, 10);
			saleFlatWindowInfoGridPanelVerticalLayout.addComponent(saleFlatInfoGrid);
			
			
			
			Image logoImage = new Image(null, new ThemeResource("image/logo-design-30.jpg"));
			logoImage.setHeight("100%");
			logoImage.addClickListener( e1 -> {
////			    		vertical.addComponent(new Label("Thanks logo, works!"));
////						vertical.addComponents(panelMain);
			});

			saleFlatWindowInfoGridPanelVerticalLayout.addComponent(logoImage);
			saleFlatWindowInfoGridPanelVerticalLayout.setComponentAlignment(logoImage, Alignment.MIDDLE_CENTER);
			saleFlatWindowInfoGridPanelVerticalLayout.setExpandRatio(logoImage, 1.0f);

			HorizontalLayout saleFlatWindowButtonHorizontalLayout = new HorizontalLayout();
			saleFlatWindowButtonHorizontalLayout.setHeight("50px");
			saleFlatWindowButtonHorizontalLayout.setWidth("100%");
			saleFlatWindowButtonHorizontalLayout.setSpacing(true);

			Button saleFlatWindowButton = new Button("Sale Flat Window");
			saleFlatWindowButton.setSizeFull();
			saleFlatWindowButton.addClickListener(es -> {

				Flat saleBuyerFlat = new Flat();

				try {
					saleBuyerFlat.setIdFlatTable(getIdFlatTableFromSelectedRow());
				} catch (SQLException e2) {
					// TODO Auto-generated catch block
					e2.printStackTrace();
				}

				saleBuyerFlat.setFlatBuyerFirstname(firstNameTextField.getValue());
				saleBuyerFlat.setFlatBuyerLastname(lastNameTextField.getValue());
				saleBuyerFlat.setFlatBuyerSurname(surNameTextField.getValue());
				saleBuyerFlat.setFlatContractNumber(contractNumberTextField.getValue());
				saleBuyerFlat.setFlatCost(Integer.parseInt(costTextField.getValue()));
				saleBuyerFlat.setFlatContractDate(saleFlatDateField.getValue());

				try {

					flatService.updateFlatSetByFlatIdInFlatTable(getIdFlatTableFromSelectedRow(), flatSetSolded);

					flatService.createBuyerFlat(saleBuyerFlat);

					saleFlatWindow.close();
				} catch (SQLException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}

				try {					
					updateFlatInfoPage();
				} catch (NumberFormatException | SQLException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}

			});

			saleFlatWindowButtonHorizontalLayout.addComponent(saleFlatWindowButton);
	
			Button cancelWindowButton = new Button("Cancel");
			cancelWindowButton.setSizeFull();

			cancelWindowButton.addClickListener(e1 -> {
				saleFlatWindow.close();
			});

			saleFlatWindowButtonHorizontalLayout.addComponent(cancelWindowButton);
			
			saleFlatWindowGridLayout.addComponent(saleFlatWindowButtonHorizontalLayout, 0, 11, 1, 11);
			
			saleFlatWindow.center();

			UI.getCurrent().addWindow(saleFlatWindow);

		});

		return saleFlatButton;

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
