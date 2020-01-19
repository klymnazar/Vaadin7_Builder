package com.example.Vaadin7_Builder;

import java.sql.SQLException;

import javax.servlet.annotation.WebServlet;

import com.example.Vaadin7_Builder.view.LoginView;
import com.example.Vaadin7_Builder.view.MainView;
import com.example.Vaadin7_Builder.view.AccountingView;
import com.example.Vaadin7_Builder.view.BuildingInfoView;
import com.example.Vaadin7_Builder.view.EditFormView;
import com.example.Vaadin7_Builder.view.FlatInfoView;
import com.example.Vaadin7_Builder.view.RegistrationView;
import com.example.Vaadin7_Builder.view.SettingsView;
import com.example.Vaadin7_Builder.view.UserManagementView;
import com.vaadin.annotations.Theme;
import com.vaadin.annotations.VaadinServletConfiguration;
import com.vaadin.navigator.Navigator;
import com.vaadin.server.VaadinRequest;
import com.vaadin.server.VaadinServlet;
import com.vaadin.ui.UI;

@Theme("mytheme")
public class NavigatorUI extends UI {
	Navigator navigator;
	public static final String MAINVIEW = "main";
	public static final String REGISTRATION = "registration";
	public static final String START = "start";
	private static final String USERMANAGEMENT = "userManagement";
	private static final String FLATINFO = "flatInfo";
	private static final String BUILDINGINFO = "buildingInfo";
	private static final String ACCOUNTING = "accounting";
	private static final String SETTINGS = "settings";

	private static final String EDIT = "edit";

	@Override
	protected void init(VaadinRequest request) {
		getPage().setTitle("Navigation Example");

		// Create a navigator to control the views
		navigator = new Navigator(this, this);

		// Create and register the views
		navigator.addView("", new LoginView());
		navigator.addView(REGISTRATION, new RegistrationView());
		
		navigator.addView(SETTINGS, new SettingsView());

		try {
			navigator.addView(FLATINFO, new FlatInfoView());
		} catch (SQLException e2) {
			// TODO Auto-generated catch block
			e2.printStackTrace();
		}

		try {
			navigator.addView(BUILDINGINFO, new BuildingInfoView());
		} catch (SQLException e2) {
			// TODO Auto-generated catch block
			e2.printStackTrace();
		}

		try {
			navigator.addView(ACCOUNTING, new AccountingView());
		} catch (SQLException e2) {
			// TODO Auto-generated catch block
			e2.printStackTrace();
		}

		try {
			navigator.addView(EDIT, new EditFormView());
		} catch (SQLException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}

		try {
			navigator.addView(USERMANAGEMENT, new UserManagementView());
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		navigator.addView(MAINVIEW, new MainView());

	}

	@WebServlet(urlPatterns = "/*", name = "MyUIServlet", asyncSupported = true)
	@VaadinServletConfiguration(ui = NavigatorUI.class, productionMode = false)
	public static class MyUIServlet extends VaadinServlet {
	}

}