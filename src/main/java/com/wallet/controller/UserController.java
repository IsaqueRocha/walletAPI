package com.wallet.controller;

import com.wallet.dto.UserDTO;
import com.wallet.entity.User;
import com.wallet.response.Response;
import com.wallet.service.UserService;
import com.wallet.util.Bcrypt;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;

@RestController
@RequestMapping("user")
public class UserController {

    @Autowired
    private UserService userService;

    @PostMapping
    public ResponseEntity<Response<UserDTO>> create(@Valid @RequestBody UserDTO dto, BindingResult result) {
        Response<UserDTO> response = new Response<>();

        if (result.hasErrors()) {
            result.getAllErrors().forEach( e -> response.getErrors().add( e.getDefaultMessage() ) );
            return ResponseEntity.status( HttpStatus.BAD_REQUEST ).body( response );
        }

        User user = userService.save( this.convertDtoToEntity( dto ) );

        response.setData( this.convertEntityToDto( user ) );

        return ResponseEntity.status( HttpStatus.CREATED ).body( response );
    }

    private User convertDtoToEntity(UserDTO dto) {
        User u = new User();

        u.setId( dto.getId() );
        u.setName( dto.getName() );
        u.setEmail( dto.getEmail() );
        u.setPassword( Bcrypt.getHash( dto.getPassword() ) );

        return u;
    }

    private UserDTO convertEntityToDto(User u) {
        UserDTO dto = new UserDTO();

        dto.setId( u.getId() );
        dto.setName( u.getName() );
        dto.setEmail( u.getEmail() );

        return dto;
    }
}
