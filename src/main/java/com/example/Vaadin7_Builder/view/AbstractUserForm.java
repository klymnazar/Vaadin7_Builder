package com.example.Vaadin7_Builder.view;

import java.net.URL;
import java.sql.SQLException;

import com.example.Vaadin7_Builder.model.User;
import com.mysql.cj.x.protobuf.MysqlxNotice.SessionStateChanged.Parameter; //change DataBase SQL -> H2
import com.vaadin.navigator.View;
import com.vaadin.navigator.ViewChangeListener.ViewChangeEvent;
//import com.vaadin.shared.ui.MarginInfo;
import com.vaadin.ui.Alignment;
//import com.vaadin.ui.Button;
//import com.vaadin.ui.ComboBox;
import com.vaadin.ui.FormLayout;
//import com.vaadin.ui.HorizontalLayout;
//import com.vaadin.ui.Label;
import com.vaadin.ui.Layout;
//import com.vaadin.ui.NativeSelect;
//import com.vaadin.ui.Notification;
import com.vaadin.ui.Panel;
import com.vaadin.ui.TextField;
import com.vaadin.ui.UI;
//import com.vaadin.ui.UI;
import com.vaadin.ui.VerticalLayout;

public abstract class AbstractUserForm extends VerticalLayout implements View{

//	UserService userService = new UserService();
//	
//	public void registrationNavigateToMain() {
//		UI.getCurrent().getNavigator().navigateTo("");
//	}
//	
//	public void registrationNavigateToUserManagement() {
//		UI.getCurrent().getNavigator().navigateTo("userManagement");	
//	}
	
//	private String userName;
	
	
	
	
	public AbstractUserForm() throws SQLException {
		Panel editForm = createEditForm();
		addComponent(editForm);
	}
	
	public Panel createEditForm() throws SQLException {
		
		
//		UserForm userForm = new UserForm();
		User user = new User();
		
		user = setEditFormUser();
		
		
		setMargin(true);
//		setSizeFull();
//		setSizeUndefined();
		
//		User user = new User();
		
			
		Panel panel = new Panel(userEditFormPanelName());
		panel.setSizeUndefined();
		addComponent(panel);
		
		VerticalLayout panelVerticalLayout = new VerticalLayout();
		panelVerticalLayout.setSizeUndefined();
		panel.setContent(panelVerticalLayout);
		
		FormLayout panelFormLayout = new FormLayout();
		panelFormLayout.setSizeUndefined();
		panelFormLayout.setMargin(true);
		panelVerticalLayout.addComponent(panelFormLayout);
		
		TextField userNameTextField = new TextField("Username");
		panelFormLayout.addComponent(userNameTextField);
		
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
		
		
		panelVerticalLayout.addComponent(createActionButtons());
		
		
		setComponentAlignment(panel, Alignment.MIDDLE_CENTER);
		
				//UI.getCurrent().getNavigator().navigateTo("edit?userNameByUserGridSelectedRow");
		
//		userNameTextField.setValue("999");
//		userNameTextField.setValue(user.getUserName());
		
		userNameTextField.setValue(user.getUserName());
		
//		UserManagementView userManagement = new UserManagementView();
//		
//		userNameTextField.setValue(userManagement.getUserName());
		
		
		
		return panel;
		
	} 
	
	
	
	
	
	public abstract Layout createActionButtons();
	
	public abstract String userEditFormPanelName();

	public abstract User setEditFormUser() throws SQLException;
	

	@Override
	public void enter(ViewChangeEvent event) {
		// TODO Auto-generated method stub
		
	}
	

}
