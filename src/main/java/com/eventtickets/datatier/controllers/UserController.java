package com.eventtickets.datatier.controllers;

import com.eventtickets.datatier.controllers.DTO.UserDTO;
import com.eventtickets.datatier.model.User;
import com.eventtickets.datatier.persistence.UserRepository;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;

@RestController
@RequestMapping("/users")
public class UserController {
	private UserRepository userRepository;

	public UserController(UserRepository userRepository) {
		this.userRepository = userRepository;
	}

	@PostMapping
	public UserDTO createUser(@RequestBody UserDTO user) {
		return toDTO(userRepository.save(toEntity(user)));
	}

	@GetMapping
	public UserDTO findUserByEmail(@RequestParam String email) {
		return toDTO(userRepository.findByEmail(email));
	}

	@PatchMapping("/{userId}")
	public UserDTO updateUser(@PathVariable long userId, @RequestBody UserDTO updateData) {
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

		return toDTO(userRepository.save(user));
	}

	private User toEntity(UserDTO dto) {
		return new User(dto.getId(),
			dto.getEmail(),
			dto.getFullName(),
			dto.getPassword(),
			dto.getIsAdmin(),
			new ArrayList<>(),
			new ArrayList<>());
	}

	private UserDTO toDTO(User entity) {
		return new UserDTO(entity.getId(),
			entity.getEmail(),
			entity.getFullName(),
			entity.getPassword(),
			entity.getIsAdmin());
	}
}