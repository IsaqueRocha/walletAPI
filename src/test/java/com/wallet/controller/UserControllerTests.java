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
class UserControllerTests {

    private static final Long ID = 1L;
    private static final String EMAIL = "test@test.com";
    private static final String NAME = "Test User";
    private static final String PASSWORD = "senha123";
    private static final String URL = "/user";

    @MockBean
    UserService userService;

    @Autowired
    MockMvc mvc;

    @Test
    void testSave() throws Exception {
        BDDMockito.given( userService.save( Mockito.any( User.class ) ) ).willReturn( getMockUser() );

        mvc.perform( MockMvcRequestBuilders.post( URL ).content( getJsonPayload( ID, NAME, EMAIL, PASSWORD ) )
                        .contentType( MediaType.APPLICATION_JSON )
                        .accept( MediaType.APPLICATION_JSON ) )
                .andExpect( MockMvcResultMatchers.status().isCreated() )
                .andExpect( MockMvcResultMatchers.jsonPath( "$.data.id" ).value( ID ) )
                .andExpect( MockMvcResultMatchers.jsonPath( "$.data.name" ).value( NAME ) )
                .andExpect( MockMvcResultMatchers.jsonPath( "$.data.email" ).value( EMAIL ) )
                .andExpect( MockMvcResultMatchers.jsonPath( "$.data.password" ).doesNotExist() );
    }

    @Test
    void testSaveInvalidUser() throws Exception {
        String payload = this.getJsonPayload( ID, NAME, "email", PASSWORD );
        mvc.perform( MockMvcRequestBuilders.post( URL ).content( payload )
                        .contentType( MediaType.APPLICATION_JSON )
                        .accept( MediaType.APPLICATION_JSON ) )
                .andExpect( MockMvcResultMatchers.status().isBadRequest() )
                .andExpect( MockMvcResultMatchers.jsonPath( "$.errors[0]" )
                        .value( "Email inválido." ) );
    }

    User getMockUser() {
        User u = new User();

        u.setId( ID );
        u.setName( NAME );
        u.setEmail( EMAIL );
        u.setPassword( PASSWORD );

        return u;
    }

    String getJsonPayload(Long id, String name, String email, String password) throws JsonProcessingException {
        UserDTO dto = new UserDTO();

        dto.setId( id );
        dto.setName( name );
        dto.setEmail( email );
        dto.setPassword( password );

        ObjectMapper mapper = new ObjectMapper();
        return mapper.writeValueAsString( dto );
    }
}
