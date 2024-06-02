package org.example.server.entity;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "users")
@Getter
@Setter @NoArgsConstructor
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO, generator = "id")
    private Integer id;

    @Column(name = "username", nullable = false, unique = true)
    private String username;

    @Column(name = "email", nullable = false)
    private String email;

    @Column(name = "password", nullable = false)
    private String password;

    /**
     * Constructor to set all the parameters except the id
     * @param username - the name to be set , it can't be null
     * @param email - the contact_info which can be null
     * @param password - the password
     */
    public User(String username, String email, String password) {
        if(username == null || username.trim().isEmpty()){
            throw new NullPointerException("Username can't be null or empty!");
        }
        if(email == null || email.trim().isEmpty()){
            throw new NullPointerException("Email can't be null or empty!");
        }
        if(password == null || password.trim().isEmpty()){
            throw new NullPointerException("Password can't be null or empty!");
        }
        this.username = username;
        this.email = email;
        this.password = password;
    }
}
