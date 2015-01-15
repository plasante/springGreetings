package com.uniksoft.dao;

import java.util.List;

import org.springframework.stereotype.Repository;

import com.uniksoft.domain.Greeting;

@Repository
public interface GreetingDao {

	public List<Greeting> getAllGreetings();
	public void addGreeting(Greeting greeting);
}
