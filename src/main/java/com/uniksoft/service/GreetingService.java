package com.uniksoft.service;

import java.util.List;

import javax.annotation.Resource;

import org.apache.log4j.Logger;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.uniksoft.dao.GreetingDao;
import com.uniksoft.domain.Greeting;

@Service
@Transactional
public class GreetingService {

	protected static Logger logger = Logger.getLogger("GreetingService");
	
	// The greetingDao will be be injected here.
	// The bean greetingDao is declared in applicationContext.xml
	// An instance of HibernateGreetingDao will be injected here.
	// We can decide to use a different implementation of GreetingDao
	// and IBatisGreeting Dao and no change will be required here.
	// This change can be done at runtime.
	@Resource(name="greetingDao")
	private GreetingDao greetingDao;
	public void setGreetindDao(GreetingDao greetingDao) {
		this.greetingDao = greetingDao;
	}
	
	public List<Greeting> getAllGreetings() {
		return greetingDao.getAllGreetings();
	}
	
	public void addGreeting(Greeting greeting) {
		greetingDao.addGreeting(greeting);
	}
}
