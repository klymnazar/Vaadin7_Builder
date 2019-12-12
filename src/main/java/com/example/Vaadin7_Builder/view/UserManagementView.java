package com.example.Vaadin7_Builder.view;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;
import java.util.List;

import com.example.Vaadin7_Builder.model.User;
import com.example.Vaadin7_Builder.service.UserService;
import com.example.Vaadin7_Builder.window.EditFormWindow;
import com.vaadin.navigator.View;
import com.vaadin.navigator.ViewChangeListener.ViewChangeEvent;
import com.vaadin.ui.Button;
import com.vaadin.ui.Grid;
import com.vaadin.ui.HorizontalLayout;
import com.vaadin.ui.Layout;
import com.vaadin.ui.TextField;
import com.vaadin.ui.UI;
import com.vaadin.ui.VerticalLayout;

public class UserManagementView extends VerticalLayout implements View {
	
	private String userNameByUserGridSelectedRow;// = "user5";
	private String userNameByUserGridSelectedRow3;
	

		
		public UserManagementView() throws SQLException {	
		
//		String userNameByUserGridSelectedRow;
		
		UserService userService = new UserService();

		List<User> userList = new ArrayList<>();
		
		
		Grid userGrid = new Grid();
		userGrid.setSizeFull();
		
//		userGrid.setSelectionMode(SelectionMode.MULTI);
				
		userGrid.addColumn("iduserTable", Integer.class);
		userGrid.addColumn("userFirstName", String.class);
		userGrid.addColumn("userLastName", String.class);
		userGrid.addColumn("userName", String.class);
		userGrid.addColumn("userPassword", String.class);
		userGrid.addColumn("userMail", String.class);
		userGrid.addColumn("userPhone", String.class);
		
		addComponent(userGrid);
		
		userList = userService.getUsers();
		
		Iterator<User> itr = userList.iterator();
		while (itr.hasNext()) {
			User user = itr.next();
			userGrid.addRow(user.getIduserTable(), user.getUserFirstName(), user.getUserLastName(),
							user.getUserName(), user.getUserPassword(), user.getUserMail(), user.getUserPhone());
			
		}


		HorizontalLayout buttonHorizontalLayout = new HorizontalLayout();
		buttonHorizontalLayout.setMargin(true);
		buttonHorizontalLayout.setSpacing(true);
		buttonHorizontalLayout.setSizeFull();
		addComponent(buttonHorizontalLayout);
		
		
		
		
		
		
		Button okButton = new Button("OK");
		okButton.addClickListener(e -> {
			
			
			
			
			
//			UI.getCurrent().getNavigator().navigateTo("main");			
		});
		buttonHorizontalLayout.addComponent(okButton);
		okButton.setSizeFull();
		
		
		Button cancelButton = new Button("Cancel");
		cancelButton.addClickListener(e -> {
			UI.getCurrent().getNavigator().navigateTo("main");			
		});
		buttonHorizontalLayout.addComponent(cancelButton);
		cancelButton.setSizeFull();
		
		
		Button editButton = new Button("Edit");
//		editButton.setEnabled(false);
//		if (userGrid.isSelected(okButton)) {
//			editButton.setEnabled(true);
//		}
		editButton.addClickListener(e -> {

			TextField qqq = new TextField();
//			buttonHorizontalLayout.removeComponent(qqq);
			
			Object selected = userGrid.getSelectedRow();
			
//			String userNameByUserGridSelectedRow2 = userGrid.getContainerDataSource().getItem(selected).getItemProperty("userName").getValue().toString();
			
			String userNameByUserGridSelectedRow1 = userGrid.getContainerDataSource().getItem(selected).getItemProperty("userName").getValue().toString();

//			userNameByUserGridSelectedRow3.set
			
			
			userNameByUserGridSelectedRow = "user5";
			
			setUserNameByUserGridSelectedRow(userNameByUserGridSelectedRow);

			qqq.setValue(userNameByUserGridSelectedRow1);
			
			buttonHorizontalLayout.addComponent(qqq);
			
//			UserForm userForm = new UserForm();
//	
//			User user = new User();
//			
//			
//			userForm.setUserNameTextField("123");
//			
//			user.setUserName("777");
			
			

			
			
			

			
			
			
			
			
			UI.getCurrent().getNavigator().navigateTo("edit");
			
			
			
			
			
			

//			UI.getCurrent().getNavigator().navigateTo("main");			
		});
		
		buttonHorizontalLayout.addComponent(editButton);
		editButton.setSizeFull();
		
		
		
		Button subWindowButton = new Button("Sub Window");
		subWindowButton.addClickListener(event -> {
			EditFormWindow editFormWindow = new EditFormWindow();
			UI.getCurrent().addWindow(editFormWindow);
		});
		
		buttonHorizontalLayout.addComponent(subWindowButton);
		subWindowButton.setSizeFull();
		
		
		
		
		
		
		
		Button addButton = new Button("Add");
		addButton.addClickListener(e -> {
			UI.getCurrent().getNavigator().navigateTo("registration");	
			
			RegistrationView registrationView = new RegistrationView();
			
//			registrationView.addComponent(c);(cancelButton);
			
			
			
//			registrationView.registrationNavigateToUserManagement();
			
			
			
//		userGrid.addRow();	
			
			
//		UI.getCurrent().getNavigator().navigateTo("registration");			
		});
		buttonHorizontalLayout.addComponent(addButton);
		addButton.setSizeFull();
		
		
		Button deleteButton = new Button("Delete");
		deleteButton.addClickListener(e -> {
			
//			userGrid.remover
			
			
			
			
			
//			UI.getCurrent().getNavigator().navigateTo("main");			
		});
		buttonHorizontalLayout.addComponent(deleteButton);
		deleteButton.setSizeFull();
		

	}

	public String setUserNameByUserGridSelectedRow(String userNameByUserGridSelectedRow) {
		return userNameByUserGridSelectedRow;
	}
		
		
		
		
	public String getUserNameByUserGridSelectedRow() throws SQLException {
		String userNameByUserGridSelectedRow1;
		
		setUserNameByUserGridSelectedRow(userNameByUserGridSelectedRow);
		

		return userNameByUserGridSelectedRow;
		
//		return null;
	}
	
	
	
	@Override
	public void enter(ViewChangeEvent event) {
		// TODO Auto-generated method stub

	}
	
	
	
	

} 
