package com.example.Vaadin7_Builder.view;

import java.sql.Connection;

import com.example.Vaadin7_Builder.NavigatorUI;
//import com.example.Vaadin7_Builder.service.SqlConnection;
import com.vaadin.annotations.Theme;
import com.vaadin.navigator.View;
import com.vaadin.navigator.ViewChangeListener.ViewChangeEvent;
import com.vaadin.ui.Alignment;
import com.vaadin.ui.Button;
import com.vaadin.ui.Notification;
import com.vaadin.ui.Panel;
import com.vaadin.ui.PasswordField;
import com.vaadin.ui.TextField;
import com.vaadin.ui.UI;
import com.vaadin.ui.VerticalLayout;

@Theme("mytheme")
public class LoginView extends VerticalLayout implements View {
	
	public LoginView() {
		setSizeFull();

		Panel loginPanel = new Panel("Login Panel");
		loginPanel.setSizeUndefined();

		VerticalLayout loginPanelVerticalLayout = new VerticalLayout();
		loginPanelVerticalLayout.setMargin(true);
		loginPanelVerticalLayout.setSpacing(true);
		loginPanelVerticalLayout.setWidthUndefined();
				
		TextField usernameTextField = new TextField();
		usernameTextField.setValue("username");
		usernameTextField.setWidthUndefined();
		
		PasswordField passwordPasswordField = new PasswordField();
		passwordPasswordField.setValue("password");
		
		Button loginButton = new Button("Login");
		loginButton.setWidth("100%");

		loginButton.addClickListener( e -> {
			UI.getCurrent().getNavigator().navigateTo(NavigatorUI.MAINVIEW);
			
//			if (usernameTextField.getValue().equals("admin") && passwordPasswordField.getValue().equals("admin"))
//			{
//				UI.getCurrent().getNavigator().navigateTo(NavigatorUI.MAINVIEW);
//			}
//			if (!usernameTextField.getValue().equals("admin") && !passwordPasswordField.getValue().equals("admin"))
//			{
//				Notification.show("Wrong username and password!!!", Notification.TYPE_ERROR_MESSAGE);
//			} 
//			else if (!usernameTextField.getValue().equals("admin"))
//			{
//				Notification.show("Wrong username!!!", Notification.TYPE_ERROR_MESSAGE);
//			}
//			else if (!passwordPasswordField.getValue().equals("admin"))
//			{
//				Notification.show("Wrong password!!!", Notification.TYPE_ERROR_MESSAGE);				
//			}
	    });
			
		Button registrationButton = new Button("Registration");
		registrationButton.setWidth("100%");
		registrationButton.addClickListener(e -> {
			UI.getCurrent().getNavigator().navigateTo(NavigatorUI.REGISTRATION);
		});
		
		
//***********************		
		Button startButton = new Button("Start");
		startButton.setWidth("100%");
		startButton.addClickListener(e -> {
			UI.getCurrent().getNavigator().navigateTo(NavigatorUI.START);
		});
		
		
//		loginPanelVerticalLayout.addComponents(startButton);
		
		
		
//***********************		


		
		
		
		
		
		
		loginPanelVerticalLayout.addComponents(usernameTextField, passwordPasswordField, loginButton, registrationButton);
		
		loginPanel.setContent(loginPanelVerticalLayout);

		addComponent(loginPanel);
		setComponentAlignment(loginPanel, Alignment.MIDDLE_CENTER);

	}

	@Override
	public void enter(ViewChangeEvent event) {
		// TODO Auto-generated method stub
		
	}


}
