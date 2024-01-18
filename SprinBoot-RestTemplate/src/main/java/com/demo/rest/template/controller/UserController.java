package com.demo.rest.template.controller;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.demo.rest.template.entity.User;
import com.demo.rest.template.exception.ResourceNotFoundException;
import com.demo.rest.template.repository.UserRepository;

@RestController
@RequestMapping("/users")
public class UserController {

	@Autowired
	private UserRepository userRepository;

	@PostMapping("/add")
	public ResponseEntity<User> createUser(@RequestBody User user) {
		if (userRepository.existsById(user.getId())) {
			throw new ResourceNotFoundException("User with id is already present");
		} else {
			return new ResponseEntity<User>(userRepository.save(user), HttpStatus.CREATED);
		}
	}

	@GetMapping("/getAll")
	public ResponseEntity<List<User>> getAllUser() {

		List<User> fetchAllUser = userRepository.findAll();
		return ResponseEntity.status(HttpStatus.OK).body(fetchAllUser);
	}

	@GetMapping("/get/{id}")
	public ResponseEntity<User> getUserByID(@PathVariable("id") int userId) {

		User getUsertById = userRepository.findById(userId)
				.orElseThrow(() -> new ResourceNotFoundException("User with this Id is not present"));

		return new ResponseEntity<User>(getUsertById, HttpStatus.OK);
	}

	@PutMapping("/update/{id}")
	public ResponseEntity<User> updateUser(@PathVariable("id") int userId, @RequestBody User user) {

		Optional<User> optionalsave = userRepository.findById(userId);
		if (optionalsave.isPresent()) {

			User existUser = optionalsave.get();
			existUser.setName(user.getName());
			existUser.setEmailId(user.getEmailId());

			return new ResponseEntity<User>(userRepository.save(existUser), HttpStatus.OK);
		} else {
			throw new ResourceNotFoundException("User with this  Id is not present");
		}
	}

	@DeleteMapping("/delete/{id}")
	public ResponseEntity<String> deleteUserById(@PathVariable("id") int userID) {

		if (userRepository.existsById(userID)) {
			userRepository.deleteById(userID);
			return new ResponseEntity<>("User Deleted successfully", HttpStatus.OK);
		} else {
			throw new ResourceNotFoundException("User With this ID is not present");
		}

	}
}
