package com.eventtickets.datatier.controllers;

import com.eventtickets.datatier.model.User;
import com.eventtickets.datatier.persistence.UserRepository;
import org.springframework.web.bind.annotation.*;

@RequestMapping("/users")
@RestController

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


}
