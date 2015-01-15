package com.uniksoft.web.form;

import com.uniksoft.domain.Color;
import com.uniksoft.domain.Greeting;

public class GreetingForm {

	// domain model persistent data
	private Greeting greeting;
	
	// domain model non persistent data
	private Color color;

	public Greeting getGreeting() {
		return greeting;
	}

	public void setGreeting(Greeting greeting) {
		this.greeting = greeting;
	}

	public Color getColor() {
		return color;
	}

	public void setColor(Color color) {
		this.color = color;
	}
}
