package com.uniksoft.service;

import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

@Service
public class UserService {

	public UserDetails getUserDetails() {
		Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
		if (authentication == null) {
			return null;
		} else {
			return (UserDetails) authentication.getPrincipal();
		}
	}
	
	public String getUsername() {
		if (getUserDetails() == null) {
			return null;
		} else {
			return getUserDetails().getUsername();
		}
	}
}
