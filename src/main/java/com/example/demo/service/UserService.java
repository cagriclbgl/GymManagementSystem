package com.example.demo.service;

import com.example.demo.api.model.User;
import com.example.demo.api.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.List;
import java.util.Optional;
@Service
public class UserService {

    private final UserRepository userRepository;


    @Autowired
    public UserService(UserRepository userRepository) {
        this.userRepository = userRepository;

    }

    // Tüm kullanıcıları listeleme
    public List<User> getAllUsers() {
        return userRepository.findAll();
    }

    // ID'ye göre kullanıcıyı getirme
    public Optional<User> getUserById(Integer id) {
        return userRepository.findById(Long.valueOf(id));
    }

    public User createUser(User user) throws Exception {
        // Email ve telefon numarasının daha önce kaydedilip kaydedilmediğini kontrol et
        if (userRepository.existsByEmail(user.getEmail())) {
            throw new Exception("Email is already taken");
        }

        if (userRepository.existsByPhoneNumber(user.getPhoneNumber())) {
            throw new Exception("Phone number is already taken");
        }

        // Şifreyi şifrelemeden olduğu gibi kaydet
        // Burada şifreyi değiştirmiyoruz, düz metin olarak kaydediyoruz
        user.setPassword(user.getPassword());

        // Kullanıcıyı veritabanına kaydet
        return userRepository.save(user);
    }
    public User authenticateUser(String email, String password) throws Exception {
        Optional<User> user = userRepository.findByEmail(email);

        if (user.isEmpty()) {
            throw new Exception("User not found");
        }

        // Kullanıcının girdiği şifreyi, veritabanındaki şifreyle karşılaştırıyoruz
        if (!user.get().getPassword().equals(password)) {
            throw new Exception("Invalid credentials");
        }

        return user.orElse(null);
    }

    // Kullanıcı güncelleme
    public User updateUser(Integer id, User updatedUser) {
        return userRepository.findById(Long.valueOf(id)).map(user -> {
            user.setName(updatedUser.getName());
            user.setEmail(updatedUser.getEmail());
            // Şifreyi güncellemiyoruz, diğer alanlar güncelleniyor
            return userRepository.save(user);
        }).orElseThrow(() -> new RuntimeException("Kullanıcı bulunamadı"));
    }

    // Kullanıcı silme
    public void deleteUser(Integer id) {
        userRepository.deleteById(Long.valueOf(id));
    }
}
