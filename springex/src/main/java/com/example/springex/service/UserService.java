package com.example.springex.service;

import com.example.springex.dto.User;

public interface UserService {
	
	User getEmployeeByName(String name);
	
	User getEmployeeById(int userid);
	
	User findByEmail(String email);
	
	public void deleteUserById(int userid);
	

}
