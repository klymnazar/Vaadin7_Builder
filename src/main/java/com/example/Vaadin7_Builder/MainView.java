package com.example.Vaadin7_Builder;

import com.vaadin.annotations.Theme;
import com.vaadin.navigator.Navigator;
import com.vaadin.navigator.View;
import com.vaadin.navigator.ViewChangeListener.ViewChangeEvent;
import com.vaadin.server.Sizeable;
import com.vaadin.server.ThemeResource;
import com.vaadin.ui.Alignment;
import com.vaadin.ui.Button;
import com.vaadin.ui.FormLayout;
import com.vaadin.ui.GridLayout;
import com.vaadin.ui.HorizontalLayout;
import com.vaadin.ui.Image;
import com.vaadin.ui.Button.ClickEvent;
import com.vaadin.ui.Label;
import com.vaadin.ui.Notification;
import com.vaadin.ui.Panel;
import com.vaadin.ui.TextField;
import com.vaadin.ui.UI;
import com.vaadin.ui.VerticalLayout;

@Theme("mytheme")
public class MainView extends VerticalLayout implements View {
	
		public MainView() {
			setSpacing(true);
//			setSizeFull();
//			setWidth("100%");
			setHeight("100%");
			
			HorizontalLayout horizontalLayout = new HorizontalLayout();
			horizontalLayout.setWidth("100%");
			horizontalLayout.setSpacing(true);
			addComponent(horizontalLayout);
			setComponentAlignment(horizontalLayout, Alignment.TOP_RIGHT);
			
			Panel logoPanel = new Panel("Logo Panel");
			horizontalLayout.addComponent(logoPanel);
				
			HorizontalLayout logoPanelHorizontalLayout = new HorizontalLayout();
			logoPanelHorizontalLayout.setSizeFull();
			
			logoPanel.setContent(logoPanelHorizontalLayout);
			horizontalLayout.setExpandRatio(logoPanel, 1.0f);
						
			Image logoImage = new Image(null, new ThemeResource("image/logo-design-30.jpg"));
			logoImage.addClickListener( e -> {
////			    		vertical.addComponent(new Label("Thanks logo, works!"));
////						vertical.addComponents(panelMain);
			});
			logoPanelHorizontalLayout.addComponent(logoImage);
			
			Image usersImage = new Image(null, new ThemeResource("image/users25.png"));
			usersImage.addClickListener( e -> {
//						vertical.addComponent(new Label("Thanks users, works!"));
			});
			logoPanelHorizontalLayout.addComponent(usersImage);
			logoPanelHorizontalLayout.setComponentAlignment(usersImage, Alignment.MIDDLE_RIGHT);
			
			Button logoutButton = new Button("Logout");
			logoutButton.addClickListener( e -> {
				UI.getCurrent().getNavigator().navigateTo("");
			});
			logoutButton.setSizeUndefined();
			horizontalLayout.addComponent(logoutButton);
			
			
			
			Panel mainPanel = new Panel("Main Panel");
			mainPanel.setSizeFull();
			addComponent(mainPanel);
			setExpandRatio(mainPanel, 1.0f);

			GridLayout mainPanelGridLayout = new GridLayout(2, 2);
			mainPanelGridLayout.setSizeFull();
			mainPanelGridLayout.setSpacing(true);
			mainPanelGridLayout.setMargin(true);
			mainPanel.setContent(mainPanelGridLayout);
			
			Button userManagementButton = new Button("User Management");
			userManagementButton.setStyleName("huge");
			userManagementButton.setSizeFull();
			userManagementButton.addClickListener( e -> {
//	    		panelMain.removeFromParent(panelMain);
//	    		vertical.addComponent(new ser());
				
				
//	    		vertical.addComponent(new Label("Thanks logo, works!"));
			});
			mainPanelGridLayout.addComponent(userManagementButton, 0, 0, 0, 0);
			mainPanelGridLayout.setComponentAlignment(userManagementButton, Alignment.MIDDLE_CENTER);

			Button flatInfoButton = new Button("Flat Info");
			flatInfoButton.setStyleName("huge");
			flatInfoButton.setSizeFull();
			mainPanelGridLayout.addComponent(flatInfoButton, 1, 0, 1, 0);
			mainPanelGridLayout.setComponentAlignment(flatInfoButton, Alignment.MIDDLE_CENTER);
			
			Button buildingInfoButton = new Button("Building Info");
			buildingInfoButton.setStyleName("huge");
			buildingInfoButton.setSizeFull();
			mainPanelGridLayout.addComponent(buildingInfoButton, 0, 1, 0, 1);
			mainPanelGridLayout.setComponentAlignment(buildingInfoButton, Alignment.MIDDLE_CENTER);
			
			Button accountingButton = new Button("Accounting");
			accountingButton.setStyleName("huge");
			accountingButton.setSizeFull();
			mainPanelGridLayout.addComponent(accountingButton, 1, 1, 1, 1);
			mainPanelGridLayout.setComponentAlignment(accountingButton, Alignment.MIDDLE_CENTER);
			




	}
	
	@Override
	public void enter(ViewChangeEvent event) {
		// TODO Auto-generated method stub

	}

} 
