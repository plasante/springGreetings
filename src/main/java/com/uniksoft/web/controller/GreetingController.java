package com.uniksoft.web.controller;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.annotation.Secured;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.uniksoft.domain.Color;
import com.uniksoft.domain.Greeting;
import com.uniksoft.service.GreetingService;
import com.uniksoft.service.UserService;
import com.uniksoft.web.form.GreetingForm;

@Controller
@RequestMapping("/home")
public class GreetingController {

	protected static Logger logger = Logger.getLogger("GreetingController");
	
	private final String DEFAULT_FAVORITE_COLOR_CODE = "FFFFFF";
	
	private GreetingService greetingService;
	
	private UserService userService;
	
	public GreetingController() {}
	
	@Autowired
	public GreetingController(GreetingService greetingService, UserService userService) {
		this.greetingService = greetingService;
		this.userService = userService;
	}
	
	@Secured("ROLE_ADMIN")
	@RequestMapping(value = "/restrictedarea.html", method = RequestMethod.GET)
	public String goRestrictedAreaPage() {
		return "restrictedarea";
	}
	
	@Secured("ROLE_ADMIN")
	@RequestMapping(value = "/greetings.html", method = RequestMethod.POST)
	public String addGreetingAndShowAll(@ModelAttribute("greetingform") GreetingForm greetingForm, Map<String, Object> model) {
		
		logger.info("entering addGreetingAndShowAll");
		
		// get the greeting instance from the form
		Greeting greeting = greetingForm.getGreeting();
		
		// set the date to the current date
		greeting.setGreetingDate(new Date());
		
		greeting.setUsername(userService.getUsername());
		
		// persist the greeting
		greetingService.addGreeting(greeting);
		
		// prepare the greetings list to be displayed
		List<Greeting> greetings = greetingService.getAllGreetings();
		model.put("greetingList", greetings);
		
		String selectedColorCode = greetingForm.getColor().getColorCode();
		// if no color selected, then assign default
		if (selectedColorCode == null || selectedColorCode.equals("")) {
			selectedColorCode = DEFAULT_FAVORITE_COLOR_CODE;
		}
		model.put("colorcode", selectedColorCode);
		
		return "greetings";
	}
	
	@RequestMapping(value = "/greetings.html", method = RequestMethod.GET)
	public String showAllGreetings(Map<String, Object> model) {
		
		logger.info("entering showAllGreetings");
		
		List<Greeting> greetings = greetingService.getAllGreetings();
		model.put("greetinglist", greetings);
		model.put("colorcode", "FFFFFF");
		
		return "greetings";
	}
	
	@ModelAttribute("colorlist")
	public List<Color> populateColorList() {
		// colorList is hardcoded for now
		List<Color> colorlist = new ArrayList<>();
		colorlist.add(new Color("Indian Red", "F75D59"));
		colorlist.add(new Color("Red", "FF0000"));
		colorlist.add(new Color("Salmon", "F9966B"));
		colorlist.add(new Color("Lemon Chiffon", "FFF8C6"));
		colorlist.add(new Color("Olive Green", "BCE954"));
		colorlist.add(new Color("Steel Blue", "C6DEFF"));
		colorlist.add(new Color("Medium Purple", "9E7BFF"));
		return colorlist;
	}
	
	// addgreeting.html does not exist
	@RequestMapping(value = "/addgreeting.html", method = RequestMethod.GET)
	public String showAddGreetingPage(@ModelAttribute("greetingform") GreetingForm greetingForm) {
		logger.info("entering showAddGreetingPage");
		return "addgreeting";
	}
}
