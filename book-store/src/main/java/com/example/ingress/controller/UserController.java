package com.example.ingress.controller;

import com.example.ingress.domain.UserAuthority;
import com.example.ingress.service.UserService;
import com.example.ingress.service.dto.login.JwtToken;
import com.example.ingress.service.dto.user.UserDto;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

@Slf4j
@RequiredArgsConstructor
@RestController
@RequestMapping("/user")
public class UserController {
    private final UserService userService;

    @PostMapping("/student")
    public JwtToken addStudent(@RequestBody UserDto userDto){
        String token = userService.saveUser(userDto, UserAuthority.STUDENT);
        return new JwtToken(token);
    }

    @PostMapping("/author")
    public JwtToken addAuthor(@RequestBody UserDto userDto){
        String token = userService.saveUser(userDto, UserAuthority.AUTHOR);
        return new JwtToken(token);
    }
}
