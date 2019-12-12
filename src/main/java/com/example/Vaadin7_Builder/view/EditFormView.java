package com.example.Vaadin7_Builder.view;

import java.sql.SQLException;

import com.example.Vaadin7_Builder.model.User;
//import com.example.Vaadin7_Builder.model.UserForm;
import com.example.Vaadin7_Builder.service.UserService;
import com.vaadin.navigator.View;
//import com.vaadin.ui.Alignment;
import com.vaadin.ui.Button;
//import com.vaadin.ui.Component;
import com.vaadin.ui.HorizontalLayout;
import com.vaadin.ui.Layout;
//import com.vaadin.ui.Panel;
import com.vaadin.ui.UI;
//import com.vaadin.ui.VerticalLayout;

public class EditFormView extends AbstractUserForm implements View{
	
	private String userNameByUserGridSelectedRoweFromUserManagement;

	
	

	public EditFormView() throws SQLException {
		super();
		// TODO Auto-generated constructor stub
	}

	@Override
	public Layout createActionButtons() {
		// TODO Auto-generated method stub

		HorizontalLayout buttonHorizontalLayout = new HorizontalLayout();
		buttonHorizontalLayout.setMargin(true);
		buttonHorizontalLayout.setSpacing(true);
		buttonHorizontalLayout.setSizeFull();
		
		
		
		Button editButton = new Button("Apply");
		editButton.setSizeFull();
		editButton.addClickListener(e -> {
			
		});
		
		buttonHorizontalLayout.addComponent(editButton);
		
		
		
		Button cancelButton = new Button("Cancel");
		cancelButton.setSizeFull();
		cancelButton.addClickListener(e -> {
			UI.getCurrent().getNavigator().navigateTo("userManagement");
		});
		
		buttonHorizontalLayout.addComponent(cancelButton);
		
		
		
		
		
//		panelVerticalLayout.addComponent(buttonHorizontalLayout);
//		
//			
//		
//		Button registrationButton = new Button("Registration");
//		registrationButton.setSizeFull();
//		registrationButton.addClickListener( e -> {
//			
//			try {
//				
//				if (usernameTextField.getValue().isEmpty()) {
//					Notification.show("Please, enter your Username");
//				}
//				
//				else
//					if (userService.isUser(usernameTextField.getValue())) {
//						Notification.show("Please, enter new Username. Username already exists");
//					}
//				
//				else
//					if (passwordTextField.getValue().isEmpty()) {
//						Notification.show("Please, enter your Password");
//					}
//				
//				else
//					if (confirmPasswordTextField.getValue().isEmpty()) {
//						Notification.show("Please, confirm your Password");
//					}
//				
//				else
//					if (!passwordTextField.getValue().equals(confirmPasswordTextField.getValue())) {
//						Notification.show("Error confirm Password");
//					}
//				
//				else
//					if (firstNameTextField.getValue().isEmpty()) {
//						Notification.show("Please, enter your FirstName");
//					}
//				
//				else
//					if (lastNameTextField.getValue().isEmpty()) {
//						Notification.show("Please, enter your LastName");
//					}
//				
//				else
//					if (emailTextField.getValue().isEmpty()) {
//						Notification.show("Please, enter your Email");
//					}
//				
//				else
//					if (!emailTextField.getValue().endsWith("@gmail.com")) {
//						Notification.show("Wrong Email, *@gmail.com");
//					}
//				
//				else
//					if (phoneTextField.getValue().isEmpty()) {
//						Notification.show("Please, enter your Phone");
//					}
//				
//				else
//					if(!phoneTextField.getValue().startsWith("+380")) {
//						Notification.show("Please, enter right your Phone, +380...");
//					}
//				
//				else
//					if (phoneTextField.getValue().startsWith("+380")) {
//						phoneTextField.addValidator(new StringLengthValidator("The phone number must be 13 numbers", 13, 13, true));
//					}
//				
//				else {
//					user.setUserFirstName(firstNameTextField.getValue());
//					user.setUserLastName(lastNameTextField.getValue());
//					user.setUserName(usernameTextField.getValue());
//					user.setUserPassword(passwordTextField.getValue());
//					user.setUserMail(emailTextField.getValue());
//					user.setUserPhone(phoneTextField.getValue());
//
//					UI.getCurrent().getNavigator().navigateTo("");
//				}
//			
//			} catch (SQLException e1) {
//				// TODO Auto-generated catch block
//				e1.printStackTrace();
//			}
//			
//			
//			
//			try {
//				userService.createUser(user);
//			} catch (SQLException e1) {
//				// TODO Auto-generated catch block
//				e1.printStackTrace();
//			}
//			
//			
//			
//			
//			
//			
//			
//			
//			
//			
//			
//			
//
//			
//			
//			
//			
////			if (usernameTextField.getValue().equals("admin") && passwordPasswordField.getValue().equals("admin"))
////			{
////				UI.getCurrent().getNavigator().navigateTo(NavigatorUI.MAINVIEW);
////			}
////			if (!usernameTextField.getValue().equals("admin") && !passwordPasswordField.getValue().equals("admin"))
////			{
////				Notification.show("Wrong username and password!!!", Notification.TYPE_ERROR_MESSAGE);
////			} 
////			else if (!usernameTextField.getValue().equals("admin"))
////			{
////				Notification.show("Wrong username!!!", Notification.TYPE_ERROR_MESSAGE);
////			}
////			else if (!passwordPasswordField.getValue().equals("admin"))
////			{
////				Notification.show("Wrong password!!!", Notification.TYPE_ERROR_MESSAGE);				
////			}
//	    });
//		
//		buttonHorizontalLayout.addComponent(registrationButton);
////		setComponentAlignment(panel, Alignment.MIDDLE_CENTER);
//		
//		Button cancelButton = new Button("Cancel");
//		cancelButton.setSizeFull();
//		
//		cancelButton.addClickListener( e -> {
//			UI.getCurrent().getNavigator().navigateTo("");
//
////			if (usernameTextField.getValue().equals("admin") && passwordPasswordField.getValue().equals("admin"))
////			{
////				UI.getCurrent().getNavigator().navigateTo(NavigatorUI.MAINVIEW);
////			}
////			if (!usernameTextField.getValue().equals("admin") && !passwordPasswordField.getValue().equals("admin"))
////			{
////				Notification.show("Wrong username and password!!!", Notification.TYPE_ERROR_MESSAGE);
////			} 
////			else if (!usernameTextField.getValue().equals("admin"))
////			{
////				Notification.show("Wrong username!!!", Notification.TYPE_ERROR_MESSAGE);
////			}
////			else if (!passwordPasswordField.getValue().equals("admin"))
////			{
////				Notification.show("Wrong password!!!", Notification.TYPE_ERROR_MESSAGE);				
////			}
//	    });
//		
//		buttonHorizontalLayout.addComponent(cancelButton);
		
		
		
		
		
		return buttonHorizontalLayout;
	}

	public String getUserNameByUserGridSelectedRoweFromUserManagement() {
		return userNameByUserGridSelectedRoweFromUserManagement;
	}
	
	public void setUserNameByUserGridSelectedRoweFromUserManagement(String userNameByUserGridSelectedRow) {
		this.userNameByUserGridSelectedRoweFromUserManagement = userNameByUserGridSelectedRow;
	}
	
	
	
	@Override
	public String userEditFormPanelName() {
		String panelName = "Edit Form" + userNameByUserGridSelectedRoweFromUserManagement;
		return panelName;
	}



	@Override
	public User setEditFormUser() throws SQLException {
		// TODO Auto-generated method stub
		
		UserService userService = new UserService();
		User user = new User();
		UserManagementView userManagement = new UserManagementView();
//		
//		
////		String qqq = userManagement.getUserNameByUserGridSelectedRow();
		user = userService.getUserByUserName(getUserNameByUserGridSelectedRoweFromUserManagement());
//		user = userService.getUserByUserName(userManagement.getUserNameByUserGridSelectedRow());
////		user = userService.getUserByUserName("user5");
//		
////		String www = userManagement.getUserNameByUserGridSelectedRow();
////		user = userServise.getUserByUserName(www);
//		
		return user;
		
//		return null;
	}



	

}
