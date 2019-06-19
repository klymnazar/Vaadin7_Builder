package com.example.Vaadin7_Builder;

import com.vaadin.navigator.View;
import com.vaadin.navigator.ViewChangeListener.ViewChangeEvent;
import com.vaadin.ui.Alignment;
import com.vaadin.ui.Button;
import com.vaadin.ui.FormLayout;
import com.vaadin.ui.Panel;
import com.vaadin.ui.TextField;
import com.vaadin.ui.UI;
import com.vaadin.ui.VerticalLayout;

public class RegistrationView extends VerticalLayout implements View{
	
	public RegistrationView() {
		setMargin(true);
		
		
		Panel panel = new Panel("Registration Form");
		panel.setSizeUndefined();
		addComponent(panel);
		
		FormLayout panelFormLayout = new FormLayout();
		panelFormLayout.setSizeUndefined();
		panelFormLayout.setMargin(true);
		panel.setContent(panelFormLayout);
		
		TextField firstNameTextField = new TextField("First Name");
		panelFormLayout.addComponent(firstNameTextField);
		
		TextField lastNameTextField = new TextField("Last Name");
		panelFormLayout.addComponent(lastNameTextField);
		
		TextField emailTextField = new TextField("Email");
		panelFormLayout.addComponent(emailTextField);
		
		TextField phoneTextField = new TextField("Phone");
		panelFormLayout.addComponent(phoneTextField);
		
		TextField usernameTextField = new TextField("Username");
		panelFormLayout.addComponent(usernameTextField);
		
		TextField passwordTextField = new TextField("Password");
		panelFormLayout.addComponent(passwordTextField);
		
		TextField confirmPasswordTextField = new TextField("Confirm Password");
		panelFormLayout.addComponent(confirmPasswordTextField);
		
		
		
		
		Button registrationButton = new Button("Registration");
		registrationButton.addClickListener( e -> {
			UI.getCurrent().getNavigator().navigateTo("");
			
			
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
		panelFormLayout.addComponent(registrationButton);
		setComponentAlignment(panel, Alignment.MIDDLE_CENTER);
		
		
	} 

	@Override
	public void enter(ViewChangeEvent event) {
		// TODO Auto-generated method stub
		
	}
	

}
