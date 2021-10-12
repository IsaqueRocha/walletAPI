package com.wallet.controller;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.wallet.dto.UserDTO;
import com.wallet.entity.User;
import com.wallet.service.UserService;
import org.junit.jupiter.api.Test;
import org.mockito.BDDMockito;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;


@SpringBootTest
@AutoConfigureMockMvc
@ActiveProfiles("test")
class UserControllerTests {

    public static final String EMAIL = "test@test.com";
    public static final String NAME = "Test User";
    public static final String PASSWORD = "senha123";
    private static final String URL = "/user";

    @MockBean
    UserService userService;

    @Autowired
    MockMvc mvc;

    @Test
    void testSave() throws Exception {
        BDDMockito.given( userService.save( Mockito.any( User.class ) ) ).willReturn( getMockUser() );

        mvc.perform( MockMvcRequestBuilders.post( URL ).content( getJsonPayload() )
                        .contentType( MediaType.APPLICATION_JSON )
                        .accept( MediaType.APPLICATION_JSON ) )
                .andExpect( MockMvcResultMatchers.status().isCreated() );
    }

    User getMockUser() {
        User u = new User();
        u.setEmail( EMAIL );
        u.setName( NAME );
        u.setPassword( PASSWORD );

        return u;
    }

    String getJsonPayload() throws JsonProcessingException {
        UserDTO dto = new UserDTO();
        dto.setEmail( EMAIL );
        dto.setName( NAME );
        dto.setPassword( PASSWORD );

        ObjectMapper mapper = new ObjectMapper();
        return mapper.writeValueAsString( dto );
    }
}
