package org.example.server.controller;

import org.example.server.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/hospital_stocks/users")
public class UserController {

    @Autowired
    private UserService userService;

    /**
     * request to add a user in database
     * @param username to be set
     * @param email to be set
     * @param password to be set
     * @return response
     */
    @PostMapping("/register")
    public ResponseEntity<Integer> registerUser(@RequestParam String username, @RequestParam String email, @RequestParam String password) {
        userService.registerUser(username, email, password);
        return ResponseEntity.ok(200);
    }

    /**
     * request to verify if a user is in database
     * @param username to be checked
     * @param password to be checked
     * @return response
     */
    @PostMapping("/login")
    public ResponseEntity<Integer> loginUser(@RequestParam String username, @RequestParam String password) {
        boolean isAuthenticated = userService.authenticateUser(username, password);
        if (isAuthenticated) {
            return ResponseEntity.ok(200);
        } else {
            return ResponseEntity.status(300).body(300);
        }
    }
}


