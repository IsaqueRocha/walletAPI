package com.wallet.repository;

import com.wallet.entity.User;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;

import java.util.Optional;

@SpringBootTest
class UserRepositoryTests {
    private static final String EMAIL = "email@test.com";

    @Autowired
    UserRepository userRepository;

    @BeforeEach
    public void setUp() {
        User u = new User();
        u.setName( "Setup User " );
        u.setPassword( "senha123" );
        u.setEmail( EMAIL );

        userRepository.save( u );
    }

    @AfterEach
    public void tearDown() {
        userRepository.deleteAll();
    }

    @Test
    void testSave() {
        User u = new User();
        u.setName( "Test" );
        u.setPassword( "123456" );
        u.setEmail( "test@test.com" );

        User response = userRepository.save( u );

        Assertions.assertNotNull( response );
    }

    @Test
    void testFindByEmail() {
        Optional<User> response = userRepository.findByEmailEquals( EMAIL );
        Assertions.assertTrue( response.isPresent() );
        Assertions.assertEquals( EMAIL, response.get().getEmail() );
    }
}
