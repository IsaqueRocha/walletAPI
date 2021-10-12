package com.wallet.repository;

import com.wallet.entity.User;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
class UserRepositoryTests {
    @Autowired
    UserRepository userRepository;

    @Test
    void testSave() {
        User u = new User();
        u.setName( "Test" );
        u.setPassword( "123456" );
        u.setEmail( "test@test.com" );

        User response = userRepository.save( u );

        Assertions.assertNotNull( response );
    }
}
