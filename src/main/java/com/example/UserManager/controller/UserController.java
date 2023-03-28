package com.example.UserManager.controller;

import java.util.List;
import java.util.Optional;

import com.example.UserManager.model.UserDTO;
import com.example.UserManager.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
public class UserController {

    @Autowired
    private UserRepository userRepo;

    @PostMapping("/addUser")
    public ResponseEntity<?> addUser(@RequestBody UserDTO user) {
        try{
            userRepo.save(user);
            return new ResponseEntity<>(user, HttpStatus.OK);
        }
        catch (Exception e){
            return new ResponseEntity<>(e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @GetMapping("/getAllUser")
    public ResponseEntity<?> getAllUsers() {
        List<UserDTO> users = userRepo.findAll();
        if(users.size() > 0) return new ResponseEntity<List<UserDTO>>(users, HttpStatus.OK);
        else return new ResponseEntity<>("No users found", HttpStatus.NOT_FOUND);
    }

    @GetMapping("/getUser/{id}")
    public ResponseEntity<?> getUserById(@PathVariable("id") String id){
        Optional<UserDTO> userOptional = userRepo.findById(id);
        if (userOptional.isPresent()) return new ResponseEntity<>(userOptional.get(), HttpStatus.OK);
        else return new ResponseEntity<>("Sorry, user with id " + id + "not found", HttpStatus.NOT_FOUND);
    }
}