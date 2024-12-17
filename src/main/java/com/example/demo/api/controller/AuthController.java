package com.example.demo.api.controller;

import com.example.demo.api.model.Trainer;
import com.example.demo.api.model.User;
import com.example.demo.api.repository.TrainerRepository;
import com.example.demo.api.repository.UserRepository;
import com.example.demo.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

@RestController
@RequestMapping("/api/auth")
public class AuthController {
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private TrainerRepository trainerRepository;
    @Autowired
    private UserService userService;

    // Kullanıcı kayıt işlemi
    @PostMapping("/register")
    public ResponseEntity<?> registerUser(@RequestBody User user) {
        try {
            userService.createUser(user);
            return ResponseEntity.ok("User registered successfully");
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Error: " + e.getMessage());
        }
    }
    // Kullanıcı girişi
    @PostMapping("/login")
    public ResponseEntity<?> loginUser(@RequestBody User loginUser) {
        try {
            User user = userService.authenticateUser(loginUser.getEmail(), loginUser.getPassword());
            Map<String, String> response = new HashMap<>();
            response.put("message", "Login successful");
            response.put("role", String.valueOf(user.getRole())); // Kullanıcının rolünü döner
            return ResponseEntity.ok(response);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());
        }
    }

    // Eğitmenleri listeleme
    @GetMapping("/trainers")
    public ResponseEntity<List<Trainer>> getAllTrainers() {
        List<Trainer> trainers = trainerRepository.findAll();
        if (trainers.isEmpty()) {
            return ResponseEntity.status(HttpStatus.NO_CONTENT).body(null); // Eğitmen yoksa 204 döner
        }
        return ResponseEntity.ok(trainers); // Eğitmenleri başarıyla döndürür
    }
}
