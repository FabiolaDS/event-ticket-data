package com.eventtickets.datatier.controllers;

import com.eventtickets.datatier.model.User;
import com.eventtickets.datatier.persistence.UserRepository;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/users")
public class UserController {
	private UserRepository userRepository;

	public UserController(UserRepository userRepository) {
		this.userRepository = userRepository;
	}

	@PostMapping
	public User createUser(@RequestBody User user) {
		return userRepository.save(user);
	}

	@GetMapping
	public User findUserByEmail(@RequestParam String email) {
		return userRepository.findByEmail(email);
	}

	@PatchMapping("/{userId}")
	public User updateUser(@PathVariable long userId,
		@RequestBody User updateData) {
		User user = userRepository.findById(userId)
			.orElseThrow(() -> new IllegalArgumentException("No such user"));

		if (updateData.getEmail() != null) {
			user.setEmail(updateData.getEmail());
		}
		if (updateData.getFullName() != null) {
			user.setFullName(updateData.getFullName());
		}
		if (updateData.getPassword() != null) {
			user.setPassword(updateData.getPassword());
		}
		if (updateData.getIsAdmin() != null) {
			user.setIsAdmin(updateData.getIsAdmin());
		}

		return userRepository.save(user);
	}
}