package com.pairlearning.expensetracker.user.controller;

import com.pairlearning.expensetracker.Constants;
import com.pairlearning.expensetracker.user.dto.AuthDto;
import com.pairlearning.expensetracker.user.dto.UserDto;
import com.pairlearning.expensetracker.user.service.UserService;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

@RestController
@AllArgsConstructor
@RequestMapping("/api/users")
public class UserController {
    private UserService userService;

    @PostMapping("/register")
    public ResponseEntity<UserDto> createUser(@RequestBody UserDto userDto) {
        UserDto savedUser = userService.createUser(userDto);

        return new ResponseEntity<>(savedUser, HttpStatus.CREATED);
    }

    @PostMapping("/auth")
    public ResponseEntity<Map<String, String>> auth(@RequestBody AuthDto authDto) {
        UserDto userDto = userService.validateUser(authDto);
        return new ResponseEntity<>(generateJWTToken(userDto), HttpStatus.OK);
    }

    private Map<String, String> generateJWTToken(UserDto userDto) {
        long timestamp = System.currentTimeMillis();
        String token = Jwts.builder().signWith(SignatureAlgorithm.HS256, Constants.API_SECRET_KEY)
                .setIssuedAt(new Date(timestamp))
                .setExpiration(new Date(timestamp + Constants.TOKEN_VALIDITY))
                .claim("userId", userDto.getUserId())
                .claim("email", userDto.getEmail())
                .claim("firstName", userDto.getFirstName())
                .claim("lastName", userDto.getLastName())
                .compact();

        Map<String, String> map = new HashMap<>();
        map.put("token", token);
        return map;
    }
}
