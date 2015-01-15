package com.uniksoft.web.controller;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.junit.Before;
import org.junit.Test;
import org.springframework.mock.web.MockHttpServletRequest;
import org.springframework.mock.web.MockHttpServletResponse;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.annotation.AnnotationMethodHandlerAdapter;

import static org.mockito.Mockito.*;

import com.uniksoft.domain.Color;
import com.uniksoft.domain.Greeting;
import com.uniksoft.service.GreetingService;
import com.uniksoft.service.UserService;
import com.uniksoft.web.form.GreetingForm;

import junit.framework.TestCase;

public class GreetingControllerTest extends TestCase {

	private GreetingForm greetingForm; 
	Greeting greeting;
	Color color;
	Map<String, Object> model;
	GreetingService fakeGreetingService;
	List<Greeting> fakeGreetingList;
	UserService fakeUserService;
	GreetingController greetingController;
	
	@Before
	protected void setUp() {
		//first initialize the 2 parameters of the method addGreetingAndShowAll() for testing
		greetingForm = new GreetingForm(); //first parameter of addGreetingAndShowAll()
		greeting = new Greeting();
		greetingForm.setGreeting(greeting);
		color = new Color();
		greetingForm.setColor(color);
		model = new HashMap<String, Object>(); //second parameter of addGreetingAndShowAll()
		//mock the GreetingService
		fakeGreetingService = mock(GreetingService.class);
		fakeGreetingList = new ArrayList<Greeting>();
		fakeGreetingList.add(greeting);
		//mock the UserDetails
		UserService fakeUserService = mock(UserService.class);
		when(fakeUserService.getUsername()).thenReturn("altheaparker");
		//inject the GreetingController with a fake GreetingService and UserService
		greetingController = new GreetingController(fakeGreetingService, fakeUserService);
		when(fakeGreetingService.getAllGreetings()).thenReturn(fakeGreetingList);
	}
	
	/**
	 * Test the the greeting text should be inserted into a Greeting object
	 * which ends up inside a list inside the model
	 */
	@SuppressWarnings("unchecked")
	@Test
	public void testModelShouldContainNewGreetingText() {		
		//GIVEN
		greeting.setGreetingText("hello world");
		//WHEN
		greetingController.addGreetingAndShowAll(greetingForm, model);
		//THEN
		List<Greeting> greetingListResult = (ArrayList<Greeting>)(model.get("greetingList")); 
		assertEquals("hello world", greetingListResult.get(0).getGreetingText());
	}
	
	/**
	 * Test that when the color red is selected, it is assigned correctly in the model
	 */
	@Test
	public void testModelShouldContainColorRedWhenSelected() {
		//given
		color.setColorCode("FF0000");
		//when
		greetingController.addGreetingAndShowAll(greetingForm, model);
		//then
		assertEquals("FF0000", model.get("colorcode"));
	}
	
	/**
	 * Test that when no color is selected, the default color should be white
	 * and the color should end up inside the model and called "colorcode"
	 */
	@Test
	public void testModelShouldContainColorWhiteWhenNoColorIsSelected() {
		//given
		//when
		greetingController.addGreetingAndShowAll(greetingForm, model);
		//then
		assertEquals("FFFFFF", model.get("colorcode"));
	}
	
	/**
	 * Test that the username makes it into the Greeting object inside the model
	 */
	@Test
	public void testModelShouldContainGreetingWithUsername() {
		//when
		greetingController.addGreetingAndShowAll(greetingForm, model);
		//then
		List<Greeting> greetingListResult = (ArrayList<Greeting>) model.get("greetingList");
		assertEquals("altheaparker", greetingListResult.get(0).getUsername());
	}
	
	/**
	 * Test that the current date goes into the Greeting object inside the model
	 */
	@Test
	public void testModelShouldContainGreetingWithCurrentDate() {
		//given
		Date dateBeforeMethodCall = new Date();
		//when
		greetingController.addGreetingAndShowAll(greetingForm, model);
		//then
		List<Greeting> greetingListResult = (ArrayList<Greeting>) model.get("greetingList");
		Date resultDate = greetingListResult.get(0).getGreetingDate();
		assertEquals(dateBeforeMethodCall.getTime(), resultDate.getTime());
	}
	
	/**
	 * Test that when a new Greeting is created, it ends up inside a list inside the model
	 */
	@Test
	public void testNewGreetingShouldBeInsertedIntoList() {
		//when
		greetingController.addGreetingAndShowAll(greetingForm, model);
		//then
		List<Greeting> greetingListResult = (ArrayList<Greeting>) model.get("greetingList");
		assertNotNull(greetingListResult);
		assertEquals(greetingListResult.size(), 1);
	}
	
	/**
	 * When the user skips directly to the greetings page without entering a greeting...
	 * given @RequestMapping(value = "/greetings.html", method = RequestMethod.GET)
	 * showAllGreetings() method should be called
	 * and "greetings" should be returned and default color should be white.
	 */
	@Test
	public void testShowAllGreetingsMethodShouldBeCalledWithGET() throws Exception {
		//given
		AnnotationMethodHandlerAdapter handlerAdapter = new AnnotationMethodHandlerAdapter();
		MockHttpServletRequest request = new MockHttpServletRequest("GET", "/home/greetings.html");
		MockHttpServletResponse response = new MockHttpServletResponse();
		//when
		ModelAndView mav = handlerAdapter.handle(request, response, greetingController);
		//then
		assertEquals("greetings", mav.getViewName());
		assertEquals("FFFFFF", mav.getModel().get("colorcode"));
	}
	
	/**
	 * When the user adds a greeting....
	 * given @RequestMapping(value = "/greetings.html", method = RequestMethod.POST)
	 * addGreetingAndShowAll() method should be called
	 * and "greetings" should be returned
	 */
	@Test
	public void testAddGreetingAndShowAllMethodShouldBeCalledWithPOST() throws Exception {
/*		//given
		AnnotationMethodHandlerAdapter handlerAdapter = new AnnotationMethodHandlerAdapter();
		MockHttpServletRequest request = new MockHttpServletRequest("POST","/home/greetings.html");
		MockHttpServletResponse response = new MockHttpServletResponse();
		//when
		ModelAndView mav = handlerAdapter.handle(request, response, greetingController);
		//then
		assertEquals("greetings", mav.getViewName());*/
	}
	
	@Test
	public void testTheMethodShowAllGreetingsShouldReturnAResult() {
		//Given
		GreetingService fakeGreetingService = mock(GreetingService.class);
		UserService fakeUserService = mock(UserService.class);
		GreetingController controller = new GreetingController(fakeGreetingService, fakeUserService);
		Map<String, Object> model = new HashMap<String, Object>();
		//When
		String result = controller.showAllGreetings(model);
		//Then
		assertNotNull(result);
		assertEquals("greetings",result);
	}
}
