package com.example.Vaadin7_Builder.view;

import java.sql.SQLException;

import com.example.Vaadin7_Builder.model.User;
import com.example.Vaadin7_Builder.service.PhoneNumberCustomComponent;
import com.example.Vaadin7_Builder.service.UserService;
import com.vaadin.data.validator.StringLengthValidator;
import com.vaadin.navigator.View;
import com.vaadin.navigator.ViewChangeListener.ViewChangeEvent;
import com.vaadin.shared.ui.MarginInfo;
import com.vaadin.ui.Alignment;
import com.vaadin.ui.Button;
import com.vaadin.ui.ComboBox;
import com.vaadin.ui.FormLayout;
import com.vaadin.ui.HorizontalLayout;
import com.vaadin.ui.Label;
import com.vaadin.ui.NativeSelect;
import com.vaadin.ui.Notification;
import com.vaadin.ui.Panel;
import com.vaadin.ui.TextField;
import com.vaadin.ui.UI;
import com.vaadin.ui.VerticalLayout;

public class RegistrationView extends VerticalLayout implements View{
	
	UserService userService = new UserService();
	
	public void registrationNavigateToMain() {
		UI.getCurrent().getNavigator().navigateTo("");
	}
	
	public void registrationNavigateToUserManagement() {
		UI.getCurrent().getNavigator().navigateTo("userManagement");	
	}
	
	
	public RegistrationView() {
		setMargin(true);
		
		User user = new User();
		
			
		Panel panel = new Panel("Registration Form");
		panel.setSizeUndefined();
		addComponent(panel);
		
		VerticalLayout panelVerticalLayout = new VerticalLayout();
		panelVerticalLayout.setSizeUndefined();
		panel.setContent(panelVerticalLayout);
		
		FormLayout panelFormLayout = new FormLayout();
		panelFormLayout.setSizeUndefined();
		panelFormLayout.setMargin(true);
		panelVerticalLayout.addComponent(panelFormLayout);
		
		TextField usernameTextField = new TextField("Username");
		panelFormLayout.addComponent(usernameTextField);
		
		TextField passwordTextField = new TextField("Password");
		panelFormLayout.addComponent(passwordTextField);
		
		TextField confirmPasswordTextField = new TextField("Confirm Password");
		panelFormLayout.addComponent(confirmPasswordTextField);
		
		TextField firstNameTextField = new TextField("First Name");
		panelFormLayout.addComponent(firstNameTextField);
		
		TextField lastNameTextField = new TextField("Last Name");
		panelFormLayout.addComponent(lastNameTextField);
		
		TextField emailTextField = new TextField("Email");
		panelFormLayout.addComponent(emailTextField);
		
		TextField phoneTextField = new TextField("Phone");
		panelFormLayout.addComponent(phoneTextField);
		
		
		
		
		
//		PhoneNumberCustomComponent phoneNumberCustomComponent = new PhoneNumberCustomComponent();
//		phoneNumberCustomComponent.setCaption("Phone");
//		panelFormLayout.addComponent(phoneNumberCustomComponent);
		
		
		
		HorizontalLayout buttonHorizontalLayout = new HorizontalLayout();
		buttonHorizontalLayout.setMargin(true);
		buttonHorizontalLayout.setSpacing(true);
		buttonHorizontalLayout.setSizeFull();
		
		panelVerticalLayout.addComponent(buttonHorizontalLayout);
		
			
		
		Button registrationButton = new Button("Registration");
		registrationButton.setSizeFull();
		registrationButton.addClickListener( e -> {
			
			try {
				
				if (usernameTextField.getValue().isEmpty()) {
					Notification.show("Please, enter your Username");
				}
				
				else
					if (userService.isUser(usernameTextField.getValue())) {
						Notification.show("Please, enter new Username. Username already exists");
					}
				
				else
					if (passwordTextField.getValue().isEmpty()) {
						Notification.show("Please, enter your Password");
					}
				
				else
					if (confirmPasswordTextField.getValue().isEmpty()) {
						Notification.show("Please, confirm your Password");
					}
				
				else
					if (!passwordTextField.getValue().equals(confirmPasswordTextField.getValue())) {
						Notification.show("Error confirm Password");
					}
				
				else
					if (firstNameTextField.getValue().isEmpty()) {
						Notification.show("Please, enter your FirstName");
					}
				
				else
					if (lastNameTextField.getValue().isEmpty()) {
						Notification.show("Please, enter your LastName");
					}
				
				else
					if (emailTextField.getValue().isEmpty()) {
						Notification.show("Please, enter your Email");
					}
				
				else
					if (!emailTextField.getValue().endsWith("@gmail.com")) {
						Notification.show("Wrong Email, *@gmail.com");
					}
				
				else
					if (phoneTextField.getValue().isEmpty()) {
						Notification.show("Please, enter your Phone");
					}
				
				else
					if(!phoneTextField.getValue().startsWith("+380")) {
						Notification.show("Please, enter right your Phone, +380...");
					}
				
				else
					if (phoneTextField.getValue().startsWith("+380")) {
						phoneTextField.addValidator(new StringLengthValidator("The phone number must be 13 numbers", 13, 13, true));
					}
				
				else {
					user.setUserFirstName(firstNameTextField.getValue());
					user.setUserLastName(lastNameTextField.getValue());
					user.setUserName(usernameTextField.getValue());
					user.setUserPassword(passwordTextField.getValue());
					user.setUserMail(emailTextField.getValue());
					user.setUserPhone(phoneTextField.getValue());

					UI.getCurrent().getNavigator().navigateTo("");
				}
			
			} catch (SQLException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
			
			
			
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
		
		buttonHorizontalLayout.addComponent(registrationButton);
//		setComponentAlignment(panel, Alignment.MIDDLE_CENTER);
		
		Button cancelButton = new Button("Cancel");
		cancelButton.setSizeFull();
		
		cancelButton.addClickListener( e -> {
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
		
		buttonHorizontalLayout.addComponent(cancelButton);

		setComponentAlignment(panel, Alignment.MIDDLE_CENTER);
		
		
	} 

	@Override
	public void enter(ViewChangeEvent event) {
		// TODO Auto-generated method stub
		
	}
	

}
