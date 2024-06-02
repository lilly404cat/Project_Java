package org.example.server;

import org.example.server.entity.User;
import org.example.server.repository.UserRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
import org.springframework.test.annotation.Rollback;
import org.springframework.test.context.ActiveProfiles;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;

@DataJpaTest
@AutoConfigureTestDatabase
@Rollback(false)
@ActiveProfiles("test")
public class UserRepositoryTests {
    @Autowired
    private UserRepository repo;
    @Autowired
    private TestEntityManager entityManager;

    @Test
    public void testCreateUser() {
        User user = new User();
        user.setUsername("eliza");
        user.setPassword("123456");
        user.setEmail("secrieru.eliza@gmail.com");

        User newUser = repo.save(user);
        User existentUser = entityManager.find(User.class, newUser.getId());

        assertThat(existentUser.getEmail()).isEqualTo(user.getEmail());
    }
}
