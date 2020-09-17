package com.example.springex.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.springex.dto.User;
import com.example.springex.repository.UserRepository;

@Service
public class UserServiceImpl implements UserService{
	
	@Autowired
	
	private UserRepository userRepo;

	@Override
	public User getEmployeeByName(String name) {
		
		return userRepo.findByName(name);
	}

	

	@Override
	public User findByEmail(String email) {
		
		return userRepo.findByEmail(email);
	}

	@Override
	public void deleteUserById(int userid) {
		userRepo.deleteById(userid);
		
	}



	@Override
	public User getEmployeeById(int userid) {
		
		return userRepo.getOne(userid);
	}

}
