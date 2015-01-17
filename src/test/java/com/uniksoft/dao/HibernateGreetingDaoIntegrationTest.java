package com.uniksoft.dao;

import java.io.File;
import java.util.Date;
import java.util.List;

import junit.framework.TestCase;

import org.hibernate.SessionFactory;
import org.hibernate.cfg.AnnotationConfiguration;
import org.hibernate.cfg.Configuration;
import org.hibernate.classic.Session;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import com.uniksoft.domain.Greeting;

public class HibernateGreetingDaoIntegrationTest extends TestCase {
	
	private SessionFactory sessionFactory;
	private Session session;
	HibernateGreetingDao hibernateGreetingDao;
	
	private SessionFactory getSessionFactory() throws Exception {
		return createConfiguration().buildSessionFactory();
	}
	
	private Configuration createConfiguration() throws Exception {
		Configuration cfg = loadConfiguration();
		return cfg;
	}
	
	private Configuration loadConfiguration() {
		return new AnnotationConfiguration().addAnnotatedClass(Greeting.class)
				.configure(new File("/home/plasante/workspace_java/SpringGreetings/src/test/resources/hibernatetest.cfg.xml"));
	}
	
	@Before
	protected void setUp() throws Exception {
		sessionFactory = getSessionFactory();
		session = sessionFactory.getCurrentSession();
		session.beginTransaction();
		hibernateGreetingDao = new HibernateGreetingDao();
		hibernateGreetingDao.setSessionFactory(sessionFactory);
	}
	
	@After
	protected void tearDown() throws Exception {
		session.getTransaction().rollback();
	}
	
	@Test
	public void testNoGreetingsInDBShouldResultInEmptyList() throws Exception {
		//when
		List<Greeting> actualGreetingList = hibernateGreetingDao.getAllGreetings();
		//then
		assertEquals(actualGreetingList.size(), 0);
	}
	
	@Test
	public void testSaveOneGreetingAndReadShouldBeSuccessful() throws Exception {
		//given
		Greeting expectedGreeting = new Greeting("Hello! Inserting the 1st greeting...", new Date(), "pierre");
		//when
		hibernateGreetingDao.addGreeting(expectedGreeting);
		List<Greeting> actualGreetingList = hibernateGreetingDao.getAllGreetings();
		//then
		assertEquals(actualGreetingList.size(), 1);
		assertEquals(expectedGreeting, actualGreetingList.get(0));
	}
	
	@Test
	public void testSaveThreeGreetingsAndReadShouldBeSuccessful() throws Exception {
		//given
		Greeting expectedGreeting1 = new Greeting("Greeting1", new Date(), "plasante");
		hibernateGreetingDao.addGreeting(expectedGreeting1);
		Greeting expectedGreeting2 = new Greeting("Greeting2", new Date(), "cspenard");
		hibernateGreetingDao.addGreeting(expectedGreeting2);
		//when
		List<Greeting> actualGreetingsList = hibernateGreetingDao.getAllGreetings();
		//then
		assertEquals(2, actualGreetingsList.size());
		//the list is sorted correctly (order by id desc)
		assertEquals(expectedGreeting2.getId(), actualGreetingsList.get(0).getId());
	}
}
