package com.example.springex.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.example.springex.dto.ConfirmationToken;
import com.example.springex.dto.User;
import com.example.springex.mail.EmailSenderService;
import com.example.springex.repository.ConfirmationTokenRepository;
import com.example.springex.repository.UserRepository;
import com.example.springex.service.UserServiceImpl;
import com.example.springex.util.ResponseHandler;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

@RestController
public class UserController {

	private static final Logger logger = LogManager.getLogger(UserController.class);

	@Autowired
	private UserRepository userRepository;

	@Autowired
	private UserServiceImpl userServiceImpl;

	@Autowired

	private ConfirmationTokenRepository confirmToken;

	@Autowired
	private EmailSenderService email;

	@PostMapping("/reg")
	public ResponseEntity<Object> register(@RequestBody User emp)

	{

		User em1 = userRepository.findByEmail(emp.getEmail());
		if (em1 != null) {

			return ResponseHandler.generateResponse(HttpStatus.OK, false, "This email already exists!", emp.getEmail());

		}

		em1 = userRepository.findByMobile(emp.getMobile());
		if (em1 != null) {
			return ResponseHandler.generateResponse(HttpStatus.OK, false, "This mobile number already exists!",
					emp.getEmail());

		}
		userRepository.save(emp);
		ConfirmationToken confirmationToken = new ConfirmationToken(emp);

		confirmToken.save(confirmationToken);

		SimpleMailMessage mailMessage = new SimpleMailMessage();

		mailMessage.setTo(emp.getEmail());

		mailMessage.setFrom(emp.getEmail());
		mailMessage.setText("To confirm your account, please click here : "
				+ "http://localhost:7518/confirm-account?token=" + confirmationToken.getConfirmationToken());

		email.sendEmail(mailMessage);

		return ResponseHandler.generateResponse(HttpStatus.OK, false,
				"Registration sucessFull and mail send register email address", emp);

	}

	@RequestMapping(value = "/confirm", method = RequestMethod.POST)
	public ResponseEntity<Object> confirmUserAccount(@RequestParam("token") String confirmationToken) {

		ConfirmationToken token = confirmToken.findByConfirmationToken(confirmationToken);

		if (token != null) {
			User user = userRepository.findByEmail(token.getUser().getEmail());

			

			if (user.isEnabled() == true) {
				return ResponseHandler.generateResponse(HttpStatus.OK, false, "Account  already verified", user);
			}
			user.setEnabled(true);
			userRepository.save(user);
			return ResponseHandler.generateResponse(HttpStatus.OK, false, "Account verified", user);
		} else {
			return ResponseHandler.generateResponse(HttpStatus.OK, false, "This link invalid", null);

		}

	}

	@GetMapping("/findAll")
	List<User> fIndAll() {
		return userRepository.findAll();
	}

	@RequestMapping(value = "/FindByName/{name}.*", method = RequestMethod.GET)
	public ResponseEntity<Object> fIndByName(@RequestBody @PathVariable("name") String name) {

		try {
			User data = userServiceImpl.getEmployeeByName(name);
			if (data.getName() == null) {

			}
			return ResponseHandler.generateResponse(HttpStatus.OK, false, "User Profile find Successfully", data);

		} catch (Exception e) {
			return ResponseHandler.generateResponse(HttpStatus.OK, false, "Email id Not Exit", null);
		}

	}

	@DeleteMapping(value = "/user/{userid}")
	public ResponseEntity<Object> DeleteApp(@RequestBody @PathVariable("userid") int userid) {

		try {

			User employee = userServiceImpl.getEmployeeById(userid);
			userServiceImpl.deleteUserById(userid);
			if (employee == null) {

			}
			return ResponseHandler.generateResponse(HttpStatus.OK, false, "User data Delete Successfully", employee);
		}

		catch (Exception e) {
			return ResponseHandler.generateResponse(HttpStatus.OK, false, "User id not found", null);
		}

	}

	@RequestMapping(value = "/profile/{email}", method = RequestMethod.GET)
	public ResponseEntity<Object> findByEmail(@RequestBody @PathVariable("email") String email) {

		try {
			User data = userServiceImpl.findByEmail(email);
			if (data.getEmail() == null) {

			}
			return ResponseHandler.generateResponse(HttpStatus.OK, false, "User Profile Get", data);

		} catch (Exception e) {
			return ResponseHandler.generateResponse(HttpStatus.OK, false, "Email id Not Exit", null);
		}

	}

	@RequestMapping(value = "/UserUpdate/{userid}", method = RequestMethod.PUT)
	public ResponseEntity<Object> UpdateUserProfile(@RequestBody User user, int userid) {
		try {
			User data = userServiceImpl.getEmployeeById(userid);
			userRepository.save(user);

			return ResponseHandler.generateResponse(HttpStatus.OK, false, "Profile Update Successfull", data);

		} catch (Exception e) {
			return ResponseHandler.generateResponse(HttpStatus.OK, false, "Data not found", null);
		}

	}

}
