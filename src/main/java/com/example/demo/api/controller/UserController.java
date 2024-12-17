package com.example.demo.api.controller;

import com.example.demo.api.model.User;
import com.example.demo.service.UserService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@RestController
@RequestMapping("/api/users")
public class UserController {

    private final UserService userService;

    public UserController(UserService userService) {
        this.userService = userService;
    }
    @GetMapping("/getusers")
    public List<User> getAllUsers() {
        return userService.getAllUsers();
    }
    // Yeni kullanıcı oluşturma
    @PostMapping("/register")
    public ResponseEntity<User> createUser(@RequestBody User user) {
        try {
            User createdUser = userService.createUser(user);
            return ResponseEntity.status(HttpStatus.CREATED).body(createdUser); // 201 Created
        } catch (Exception e) {
            if (e.getMessage().contains("Email")) {
                return ResponseEntity.status(HttpStatus.CONFLICT).body(null); // 409 Conflict
            } else if (e.getMessage().contains("Phone number")) {
                return ResponseEntity.status(HttpStatus.CONFLICT).body(null); // 409 Conflict
            }
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(null); // 500 Internal Server Error
        }
    }
    // Kullanıcıyı silme
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteUser(@PathVariable Long id) {
        try {
            userService.deleteUser(Math.toIntExact(id));
            return ResponseEntity.noContent().build(); // 204 No Content (başarıyla silindi)
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build(); // 500 Internal Server Error (hata durumu)
        }
    }

}
