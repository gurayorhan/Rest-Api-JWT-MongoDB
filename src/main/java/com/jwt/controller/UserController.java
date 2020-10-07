package com.jwt.controller;

import com.jwt.dto.UserDto;
import com.jwt.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class UserController {

    @Autowired
    private UserService userService;

    @PostMapping("/add")
    public UserDto add(@RequestBody UserDto userDto) throws Exception {
        return userService.add(userDto);
    }

    @GetMapping("/test-user")
    public String test() throws Exception {
        return "test-user";
    }
}
