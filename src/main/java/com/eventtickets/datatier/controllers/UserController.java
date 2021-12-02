package com.eventtickets.datatier.controllers;

import com.eventtickets.datatier.controllers.DTO.UserDTO;
import com.eventtickets.datatier.model.User;
import com.eventtickets.datatier.persistence.UserRepository;
import org.springframework.http.HttpStatus;
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
        return ResponseEntity.of(userRepository.findById(id)
                .map(this::toDTO));

    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public UserDTO createUser(@RequestBody UserDTO user) {
        return toDTO(userRepository.save(toEntity(user)));
    }

    @GetMapping("/byEmail")
    public ResponseEntity<UserDTO> findUserByEmail(@RequestParam String email) {
        return ResponseEntity.of(userRepository.findByEmail(email)
                .map(this::toDTO));

    }

    @PatchMapping("/{userId}")
    public ResponseEntity<UserDTO> updateUser(@PathVariable long userId, @RequestBody UserDTO updateData) {
        return ResponseEntity.of(userRepository.findById(userId)
                .map(user -> {


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
                    return user;
                }).map(userRepository::save)
                .map(this::toDTO));
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