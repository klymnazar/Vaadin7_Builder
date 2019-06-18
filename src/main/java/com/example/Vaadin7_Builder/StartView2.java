package com.example.Vaadin7_Builder;

import com.vaadin.navigator.Navigator;
import com.vaadin.navigator.View;
import com.vaadin.navigator.ViewChangeListener.ViewChangeEvent;
import com.vaadin.ui.Alignment;
import com.vaadin.ui.Button;
import com.vaadin.ui.Button.ClickEvent;
import com.vaadin.ui.Label;
import com.vaadin.ui.Notification;
import com.vaadin.ui.UI;
import com.vaadin.ui.VerticalLayout;

public class StartView2 extends VerticalLayout implements View {

	
	
	//public class StartView extends UI {
		public StartView2() {
			setSizeFull();
			
			
			
//			Button button = new Button("HELLO");
			
			Button button = new Button("Go to View",
					new Button.ClickListener() {
						
						@Override
						public void buttonClick(ClickEvent event) {
							UI.getCurrent().getNavigator().navigateTo("");
						}
					});
					addComponent(button);
					setComponentAlignment(button, Alignment.MIDDLE_CENTER);
		} 




//public class StartView extends VerticalLayout implements View {
////	Navigator navigator;
////	protected static final String MAINVIEW = "main";
//
//	public StartView() {
//		setSizeFull();
//		
//		
////		Label label = new Label("OK");
//		
////		Button button = new Button("Go to Main View", new Button.ClickListener() {
////			@Override public void buttonClick(ClickEvent event) {
////				navigator.navigateTo(MAINVIEW);
////			}
////
////			
////		});
////		addComponent(button);
////		setComponentAlignment(button, Alignment.MIDDLE_CENTER);
//		
//		Button button = new Button("HELLO");
//		
////		Button button = new Button("Go to Main View",
////				new Button.ClickListener() {
////					
////					@Override
////					public void buttonClick(ClickEvent event) {
////						navigator.navigateTo(MAINVIEW);
////					}
////				});
//		addComponent(button);
//		setComponentAlignment(button, Alignment.MIDDLE_CENTER);
//		
//		
//		
//		
////		addComponent(label);
//	}
	
	@Override
	public void enter(ViewChangeEvent event) {
		// TODO Auto-generated method stub

	}
	
	
//	@Override
//	public void enter(ViewChangeEvent event) {
//		Notification.show("Welcome to the Animal Farm");
//	}
	
} 
