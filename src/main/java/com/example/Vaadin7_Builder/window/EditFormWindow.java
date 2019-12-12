package com.example.Vaadin7_Builder.window;

import com.vaadin.ui.Button;
import com.vaadin.ui.Window;

public class EditFormWindow extends Window {
	
	public EditFormWindow() {
		super("Edit");
		center();
		
		setClosable(false);
		
		setContent(new Button("Close me", event -> close()));
	}

}
