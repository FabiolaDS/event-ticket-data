package com.eventtickets.datatier.controllers;

import com.eventtickets.datatier.controllers.DTO.UserDTO;
import com.eventtickets.datatier.model.User;
import com.eventtickets.datatier.persistence.UserRepository;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/users")
public class UserController {

    private UserRepository userRepository;

    public UserController(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @GetMapping("/{id}")
    public ResponseEntity<UserDTO> findById(@PathVariable long id) {
        Optional<User> u = userRepository.findById(id);

        if (u.isPresent())
            return ResponseEntity.ok(toDTO(u.get()));

        return ResponseEntity.notFound().build();
    }

    @PostMapping
    public UserDTO createUser(@RequestBody UserDTO user) {
        return toDTO(userRepository.save(toEntity(user)));
    }

    @GetMapping("/byEmail")
    public ResponseEntity<UserDTO> findUserByEmail(@RequestParam String email) {
        User result = userRepository.findByEmail(email);

        if (result == null)
            return ResponseEntity
                    .notFound()
                    .build();

        return ResponseEntity.ok(toDTO(result));
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

    @GetMapping
    public List<UserDTO> getAllUsers() {
        return userRepository.findAll().stream()
                .map(this::toDTO)
                .collect(Collectors.toList());


    }

    private User toEntity(UserDTO dto) {
        return new User(dto.getId(),
                dto.getEmail(),
                dto.getFullName(),
                dto.getPassword(),
                dto.getIsAdmin(),
                new ArrayList<>(),
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