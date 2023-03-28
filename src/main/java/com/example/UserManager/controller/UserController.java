package com.example.UserManager.controller;

import java.util.List;

import com.example.UserManager.model.UserDTO;
import com.example.UserManager.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class UserController {

    @Autowired
    private UserRepository userRepo;

    @PostMapping("/addUser")
    public UserDTO addUser(@RequestBody UserDTO user) {
        return userRepo.save(user);
    }

    @GetMapping("/getAllUser")
    public List<UserDTO> getAllUser(){
        return userRepo.findAll();
    }
}