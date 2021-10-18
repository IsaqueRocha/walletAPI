package com.wallet.service;

import com.wallet.entity.User;
import com.wallet.repository.UserRepository;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.BDDMockito;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.ActiveProfiles;

import java.util.Optional;

@SpringBootTest
@ActiveProfiles("test")
class UserServiceTests {
    @MockBean
    UserRepository userRepository;

    @Autowired
    UserService userService;

    @BeforeEach
    public void setUp() {
        BDDMockito
                .given( userRepository.findByEmailEquals( Mockito.anyString() ) )
                .willReturn( Optional.of( new User() ) );
    }

    @Test
    void testFindByEmail() {
        Optional<User> response = userService.findByEmail( "test@test.com" );
        Assertions.assertTrue( response.isPresent() );
    }
}
