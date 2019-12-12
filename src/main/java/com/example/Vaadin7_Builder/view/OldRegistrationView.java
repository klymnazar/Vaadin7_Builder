package com.example.Vaadin7_Builder.view;

import java.sql.SQLException;

import com.example.Vaadin7_Builder.model.User;
import com.example.Vaadin7_Builder.service.UserService;
import com.vaadin.navigator.View;
import com.vaadin.navigator.ViewChangeListener.ViewChangeEvent;
import com.vaadin.ui.Alignment;
import com.vaadin.ui.Button;
import com.vaadin.ui.FormLayout;
import com.vaadin.ui.Panel;
import com.vaadin.ui.TextField;
import com.vaadin.ui.UI;
import com.vaadin.ui.VerticalLayout;

public class OldRegistrationView extends VerticalLayout implements View{
	
	UserService userService = new UserService();
	
	public OldRegistrationView() {
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
			
			
			User user = new User();
			
//			user.setUserFirstName("firstNameTextField");
//			user.setUserLastName("lastNameTextField");
//			user.setUserName("usernameTextField");
//			user.setUserPassword("passwordTextField");
//			user.setUserMail("emailTextField");
//			user.setUserPhone(98);
			
			user.setUserFirstName(firstNameTextField.getValue());
			user.setUserLastName(lastNameTextField.getValue());
			user.setUserName(usernameTextField.getValue());
			user.setUserPassword(passwordTextField.getValue());
			user.setUserMail(emailTextField.getValue());
			
//			user.setUserPhone(0);
//			user.setUserPhone(phoneTextField.getValue());
			
			try {
				userService.createUser(user);
			} catch (SQLException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
			

			
			
			
			
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
