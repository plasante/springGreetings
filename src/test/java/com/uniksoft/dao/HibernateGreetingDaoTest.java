package com.uniksoft.dao;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.hibernate.Query;
import org.hibernate.classic.Session;
import org.hibernate.SessionFactory;
import org.junit.Before;
import org.junit.Test;

import com.uniksoft.domain.Greeting;

import junit.framework.TestCase;

import static org.mockito.Mockito.*;

/**
 * The point of this test is to test the data access code 
 * without actually connecting to the database.
 * 
 * @author plasante
 *
 */
public class HibernateGreetingDaoTest extends TestCase {

	private SessionFactory sessionFactory;
	private Session session;
	private Query query;
	
	@Before
	public void setUp() {
		sessionFactory = mock(SessionFactory.class);
		session = mock(Session.class);
		query = mock(Query.class);
	}
	
	/**
	 * Testing hibernate and the method getAllGreetings() from HibernateGreetingDao
	 * Create an expected list
	 * Use Mockito to return expected list upon query.list()
	 * Verify that the expected list = actual list from getAllGreetings()
	 */
	@Test
	public void testTheMethodGetAllGreetingsShouldReturnAListOfGreetings() {
		//given
		String hql = "select g from Greeting g order by id desc";
		List<Greeting> expectedGreetingList = new ArrayList<>();
		expectedGreetingList.add(new Greeting("Welcome to the world!", new Date(), "johnyd"));
		expectedGreetingList.add(new Greeting("Hello there everyone...", new Date(), "mrsam"));
		expectedGreetingList.add(new Greeting("Hey there", new Date(), "sonialawson"));
		//when
		HibernateGreetingDao hibernateGreetingDao = new HibernateGreetingDao();
		hibernateGreetingDao.setSessionFactory(sessionFactory);
		when(sessionFactory.getCurrentSession()).thenReturn(session);
		when(session.createQuery(hql)).thenReturn(query);
		when(query.list()).thenReturn(expectedGreetingList);
		List<Greeting> actualGreetingList = hibernateGreetingDao.getAllGreetings();
		//then
		assertNotNull(actualGreetingList);
		assertSame(expectedGreetingList, actualGreetingList);
	}
}
