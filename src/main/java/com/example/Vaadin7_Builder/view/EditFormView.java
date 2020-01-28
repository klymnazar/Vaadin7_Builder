package com.example.Vaadin7_Builder.view;

import java.sql.SQLException;

import com.example.Vaadin7_Builder.model.User;
import com.example.Vaadin7_Builder.service.UserService;
import com.vaadin.navigator.View;
import com.vaadin.ui.Button;
import com.vaadin.ui.HorizontalLayout;
import com.vaadin.ui.Layout;
import com.vaadin.ui.UI;

public class EditFormView extends AbstractUserForm implements View {

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

		user = userService.getUserByUserName(getUserNameByUserGridSelectedRoweFromUserManagement());

		return user;

	}

}
