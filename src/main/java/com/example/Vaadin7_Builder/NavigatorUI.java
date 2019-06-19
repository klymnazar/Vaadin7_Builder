package com.example.Vaadin7_Builder;

import javax.servlet.annotation.WebServlet;

import com.vaadin.annotations.Theme;
//import com.example.myapp.NavigatorUI;
import com.vaadin.annotations.VaadinServletConfiguration;
import com.vaadin.navigator.Navigator;
import com.vaadin.server.VaadinRequest;
import com.vaadin.server.VaadinServlet;
import com.vaadin.ui.UI;

@Theme("mytheme")
public class NavigatorUI extends UI {
	Navigator navigator;
	protected static final String MAINVIEW = "main";
	protected static final String REGISTRATION = "registration";
	protected static final String START = "start";
	@Override
	protected void init(VaadinRequest request) {
		getPage().setTitle("Navigation Example");
		
		// Create a navigator to control the views 
		navigator = new Navigator(this, this); 
		
		// Create and register the views 
		navigator.addView("", new LoginView());
		navigator.addView(REGISTRATION, new RegistrationView());
//		navigator.addView(START, new StartView());
		
		
//		navigator.addView("", new StartView2());
//		navigator.addView("", new LoginView());
//		navigator.addView(MAINVIEW, new MainView());
		
//		navigator.addView(MAINVIEW, new StartView2());
		navigator.addView(MAINVIEW, new MainView());
		
		
	}
	
    @WebServlet(urlPatterns = "/*", name = "MyUIServlet", asyncSupported = true)
    @VaadinServletConfiguration(ui = NavigatorUI.class, productionMode = false)
    public static class MyUIServlet extends VaadinServlet {
    }
	
}