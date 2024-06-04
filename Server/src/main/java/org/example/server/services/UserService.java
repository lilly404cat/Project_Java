package org.example.server.services;

import org.example.server.entity.User;
import org.example.server.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Optional;

/**
 * the UserService class
 */
@Service
public class UserService {

    @Autowired
    private UserRepository userRepository;
    @Autowired
    private PasswordEncoder passwordEncoder;

    /**
     * method that makes the register
     * @param username the username
     * @param email the email
     * @param password the password
     * @return User
     */
    public User registerUser(String username, String email, String password) {
        if (userRepository.existsByUsername(username)) {
            throw new IllegalArgumentException("Username is already taken!");
        }

        String hashedPassword = passwordEncoder.encode(password);
        User user = new User(username, email, hashedPassword);
        return userRepository.save(user);
    }

    /**
     * method that makes the login
     * @param username the username
     * @param password the password
     * @return Boolean
     */
    public boolean authenticateUser(String username, String password) {
        Optional<User> optionalUser = userRepository.findByUsername(username);
        if (optionalUser.isPresent()) {
            User user = optionalUser.get();
            return passwordEncoder.matches(password, user.getPassword());
        }
        return false;
    }
}
