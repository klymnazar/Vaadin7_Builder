package com.example.Vaadin7_Builder;

//import javax.servlet.annotation.WebServlet;

import com.vaadin.annotations.Theme;
import com.vaadin.navigator.View;
import com.vaadin.navigator.ViewChangeListener.ViewChangeEvent;
import com.vaadin.server.ThemeResource;
import com.vaadin.server.VaadinRequest;
import com.vaadin.ui.Alignment;
import com.vaadin.ui.Button;
import com.vaadin.ui.HorizontalLayout;
import com.vaadin.ui.Image;
import com.vaadin.ui.Label;
import com.vaadin.ui.Panel;
import com.vaadin.ui.UI;
import com.vaadin.ui.VerticalLayout;

/**
 * This UI is the application entry point. A UI may either represent a browser window 
 * (or tab) or some part of a html page where a Vaadin application is embedded.
 * <p>
 * The UI is initialized using {@link #init(VaadinRequest)}. This method is intended to be 
 * overridden to add component to the user interface and initialize non-component functionality.
 */
@Theme("mytheme")
public class MainView extends VerticalLayout implements View {

//public class MainView extends VerticalLayout {

	public MainView() {
		setSpacing(true);
//		setSizeFull();
		
		HorizontalLayout horizontalLayout = new HorizontalLayout();
		horizontalLayout.setSizeFull();
		horizontalLayout.setSpacing(true);
		
		
		
		Panel logoPanel = new Panel("Logo Panel");
		logoPanel.setSizeFull();
//		logoPanel.setSizeUndefined();
//		addComponent(logoPanel);
		
		HorizontalLayout logoPanelHorizontalLayout = new HorizontalLayout();
		logoPanelHorizontalLayout.setSizeFull();
		logoPanelHorizontalLayout.setMargin(true);
		
//		VerticalLayout logoPanelVerticalLayout = new VerticalLayout();
//		logoPanelVerticalLayout.setSizeFull();

		
		Image logoImage = new Image(null, new ThemeResource("image/logo-design-30.jpg"));
		logoImage.setSizeUndefined();
		logoImage.addClickListener( e -> {
//		    		vertical.addComponent(new Label("Thanks logo, works!"));
//					vertical.addComponents(panelMain);
		});
		logoPanelHorizontalLayout.addComponent(logoImage);
		logoPanelHorizontalLayout.setComponentAlignment(logoImage, Alignment.MIDDLE_LEFT);
				
//		logoPanelHorizontalLayout.addComponent(logoPanelVerticalLayout);
		
		Image usersImage = new Image(null, new ThemeResource("image/users25.png"));
		usersImage.setSizeUndefined();
		usersImage.addClickListener( e -> {
//					vertical.addComponent(new Label("Thanks users, works!"));
		});
		logoPanelHorizontalLayout.addComponent(usersImage);
		logoPanelHorizontalLayout.setComponentAlignment(usersImage, Alignment.MIDDLE_RIGHT);
		
		
//		logoPanelVerticalLayout.addComponent(usersImage);
//		logoPanelVerticalLayout.setComponentAlignment(usersImage, Alignment.TOP_RIGHT);
//		logoPanelHorizontalLayout.setComponentAlignment(logoPanelVerticalLayout, Alignment.MIDDLE_RIGHT);
		
		
		
		
//		logoPanelVerticalLayout.addComponent(logoutButton);
//		logoPanelVerticalLayout.setComponentAlignment(logoutButton, Alignment.BOTTOM_RIGHT);
		
		logoPanel.setContent(logoPanelHorizontalLayout);
		
		
		
		
		
		
		Button logoutButton = new Button("Logout");
		logoutButton.addClickListener( e -> {
			UI.getCurrent().getNavigator().navigateTo("");
		});
		addComponent(logoutButton);
		setComponentAlignment(logoutButton, Alignment.MIDDLE_RIGHT);
		logoutButton.setSizeUndefined();
		
		horizontalLayout.addComponent(logoPanel);
		horizontalLayout.addComponent(logoutButton);
		
		addComponent(horizontalLayout);
		
		
		
		Panel mainPanel = new Panel("Main Panel");
		mainPanel.setSizeFull();
		addComponent(mainPanel);

		
	
		VerticalLayout MainPanelVerticalLayout = new VerticalLayout();
		MainPanelVerticalLayout.setMargin(true);
		MainPanelVerticalLayout.setSpacing(true);
		
		HorizontalLayout MainPanel1horizontalLayout = new HorizontalLayout();
		MainPanel1horizontalLayout.setSizeFull();
		MainPanel1horizontalLayout.setMargin(true);
		MainPanel1horizontalLayout.setSpacing(true);
		
		Button userManagementButton = new Button("User Management");
		userManagementButton.setStyleName("huge");
		userManagementButton.setSizeFull();
		MainPanel1horizontalLayout.addComponent(userManagementButton);
		MainPanel1horizontalLayout.setComponentAlignment(userManagementButton, Alignment.MIDDLE_LEFT);
		
		userManagementButton.addClickListener( e -> {
//    		panelMain.removeFromParent(panelMain);
//    		vertical.addComponent(new ser());
			
			
//    		vertical.addComponent(new Label("Thanks logo, works!"));
		});
		
		
		
		
		
		Button flatInfoButton = new Button("Flat Info");
		flatInfoButton.setStyleName("huge");
		flatInfoButton.setSizeFull();
		MainPanel1horizontalLayout.addComponent(flatInfoButton);
		MainPanel1horizontalLayout.setComponentAlignment(flatInfoButton, Alignment.MIDDLE_RIGHT);
		
		MainPanelVerticalLayout.addComponent(MainPanel1horizontalLayout);
		
		
		
		
		HorizontalLayout MainPanel2horizontalLayout = new HorizontalLayout();
		MainPanel2horizontalLayout.setSizeFull();
		MainPanel2horizontalLayout.setMargin(true);
		MainPanel2horizontalLayout.setSpacing(true);
		
		Button buildingInfoButton = new Button("Building Info");
		buildingInfoButton.setStyleName("huge");
		buildingInfoButton.setSizeFull();
		MainPanel2horizontalLayout.addComponent(buildingInfoButton);
		MainPanel2horizontalLayout.setComponentAlignment(buildingInfoButton, Alignment.MIDDLE_LEFT);
		
		
		Button accountingButton = new Button("Accounting");
		accountingButton.setStyleName("huge");
		accountingButton.setSizeFull();
		MainPanel2horizontalLayout.addComponent(accountingButton);
		MainPanel2horizontalLayout.setComponentAlignment(accountingButton, Alignment.MIDDLE_RIGHT);
		
		MainPanelVerticalLayout.addComponent(MainPanel2horizontalLayout);
		
		mainPanel.setContent(MainPanelVerticalLayout);
		
		
		
		
	}

	@Override
	public void enter(ViewChangeEvent event) {
		// TODO Auto-generated method stub
		
	}

//	@Override
//	public void enter(ViewChangeEvent event) {
//		// TODO Auto-generated method stub
//		
//	}

	
	
	
 
}
