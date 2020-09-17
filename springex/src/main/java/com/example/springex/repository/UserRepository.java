package com.example.springex.repository;

import java.util.List;



import org.springframework.data.jpa.repository.JpaRepository;


import com.example.springex.dto.User;
import com.sun.mail.handlers.image_gif;

public interface UserRepository extends JpaRepository<User,Integer> {
	
	public User findByEmail(String email);
	public User findByMobile(String mobile);
	public User findByName(String name);
	
	public List<User> findAll();
	public User deleteByEmail(String email);
	
	
}
