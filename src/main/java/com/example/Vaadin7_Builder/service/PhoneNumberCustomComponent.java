package com.example.Vaadin7_Builder.service;

import com.example.Vaadin7_Builder.model.User;
import com.vaadin.ui.Alignment;
import com.vaadin.ui.CustomComponent;
import com.vaadin.ui.HorizontalLayout;
import com.vaadin.ui.Label;
import com.vaadin.ui.NativeSelect;
//import com.vaadin.ui.Notification;
import com.vaadin.ui.TextField;

public class PhoneNumberCustomComponent extends CustomComponent{
	
	public PhoneNumberCustomComponent() {
		
		HorizontalLayout userPhoneHorizontalLayout = new HorizontalLayout();
		userPhoneHorizontalLayout.setSizeUndefined();
		userPhoneHorizontalLayout.setSpacing(true);
		
		Label prePhoneLabel = new Label("+38");
		userPhoneHorizontalLayout.addComponent(prePhoneLabel);
		userPhoneHorizontalLayout.setComponentAlignment(prePhoneLabel, Alignment.MIDDLE_CENTER);
		
		NativeSelect userPhoneNativeSelect = new NativeSelect();
		userPhoneNativeSelect.addItems("067", "068", "096", "097", "098");
		userPhoneNativeSelect.setWidth("60px");
		userPhoneHorizontalLayout.addComponent(userPhoneNativeSelect);
		
	
		TextField userPhoneTextField = new TextField();
		userPhoneTextField.setWidth("90px");
		userPhoneHorizontalLayout.addComponent(userPhoneTextField);
		
		
				
		setCompositionRoot(userPhoneHorizontalLayout);
	
	}



}
