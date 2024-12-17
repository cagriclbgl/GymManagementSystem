package com.example.demo;

//Bu test sınıfı istediğimiz gibi olmadı. Test geçilemedi.

import com.example.demo.api.model.User;
import com.example.demo.api.repository.UserRepository;
import com.example.demo.service.UserService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

public class UserServiceTest {

    @Mock
    private UserRepository userRepository;

    @InjectMocks
    private UserService userService;

    private User user;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
//        user = new User(1L, "John Doe", "john@example.com", "123456789", "password123"); // Örnek User nesnesi
    }

    @Test
    void testGetAllUsers() {
        // Setup mock
        when(userRepository.findAll()).thenReturn(List.of(user));

        // Test
        assertFalse(userService.getAllUsers().isEmpty());
        assertEquals(1, userService.getAllUsers().size());
        verify(userRepository, times(1)).findAll();
    }

    @Test
    void testGetUserById() {
        // Setup mock
        when(userRepository.findById(1L)).thenReturn(Optional.of(user));

        // Test
        Optional<User> foundUser = userService.getUserById(1);
        assertTrue(foundUser.isPresent());
        assertEquals(user.getName(), foundUser.get().getName());
        verify(userRepository, times(1)).findById(1L);
    }

    @Test
    void testCreateUser() throws Exception {
        // Setup mock
        when(userRepository.existsByEmail(user.getEmail())).thenReturn(false);
        when(userRepository.existsByPhoneNumber(user.getPhoneNumber())).thenReturn(false);
        when(userRepository.save(user)).thenReturn(user);

        // Test
        User createdUser = userService.createUser(user);
        assertNotNull(createdUser);
        assertEquals(user.getEmail(), createdUser.getEmail());
        verify(userRepository, times(1)).existsByEmail(user.getEmail());
        verify(userRepository, times(1)).existsByPhoneNumber(user.getPhoneNumber());
        verify(userRepository, times(1)).save(user);
    }

    @Test
    void testCreateUserEmailTaken() {
        // Setup mock
        when(userRepository.existsByEmail(user.getEmail())).thenReturn(true);

        // Test
        Exception exception = assertThrows(Exception.class, () -> userService.createUser(user));
        assertEquals("Email is already taken", exception.getMessage());
        verify(userRepository, times(1)).existsByEmail(user.getEmail());
    }

    @Test
    void testCreateUserPhoneNumberTaken() {
        // Setup mock
        when(userRepository.existsByEmail(user.getEmail())).thenReturn(false);
        when(userRepository.existsByPhoneNumber(user.getPhoneNumber())).thenReturn(true);

        // Test
        Exception exception = assertThrows(Exception.class, () -> userService.createUser(user));
        assertEquals("Phone number is already taken", exception.getMessage());
        verify(userRepository, times(1)).existsByPhoneNumber(user.getPhoneNumber());
    }

    @Test
    void testAuthenticateUser() throws Exception {
        // Setup mock
        when(userRepository.findByEmail(user.getEmail())).thenReturn(Optional.of(user));

        // Test
        User authenticatedUser = userService.authenticateUser(user.getEmail(), user.getPassword());
        assertNotNull(authenticatedUser);
        assertEquals(user.getName(), authenticatedUser.getName());
        verify(userRepository, times(1)).findByEmail(user.getEmail());
    }

    @Test
    void testAuthenticateUserNotFound() {
        // Setup mock
        when(userRepository.findByEmail(user.getEmail())).thenReturn(Optional.empty());

        // Test
        Exception exception = assertThrows(Exception.class, () -> userService.authenticateUser(user.getEmail(), user.getPassword()));
        assertEquals("User not found", exception.getMessage());
    }

    @Test
    void testAuthenticateInvalidCredentials() {
        String invalidEmail = "invalid@example.com";
        String invalidPassword = "wrongPassword";

        Exception exception = assertThrows(Exception.class, () -> {
            userService.authenticateUser(invalidEmail, invalidPassword);
        });

        assertEquals("User not found", exception.getMessage());
    }


   // @Test
  /*  void testUpdateUser() {
        // Setup mock
 //       User updatedUser = new User(1L, "John Doe Updated", "john_updated@example.com", "987654321", "password123");
        when(userRepository.findById(1L)).thenReturn(Optional.of(user));
      //  when(userRepository.save(any(User.class))).thenReturn(updatedUser);

        // Test
        User result = userService.updateUser(1, updatedUser);
        assertEquals(updatedUser.getName(), result.getName());
        assertEquals(updatedUser.getEmail(), result.getEmail());
        verify(userRepository, times(1)).findById(1L);
        verify(userRepository, times(1)).save(any(User.class));
    }*/


    @Test
    void testDeleteUser() {
        // Setup mock
        doNothing().when(userRepository).deleteById(1L);

        // Test
        userService.deleteUser(1);
        verify(userRepository, times(1)).deleteById(1L);
    }
}
