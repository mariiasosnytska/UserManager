package com.example.UserManager.controller;

import java.util.ArrayList;
import java.util.List;

import com.example.UserManager.exception.ExceptionUserService;
import com.example.UserManager.model.UserDTO;
import com.example.UserManager.repository.UserRepository;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.stereotype.Service;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.annotation.*;

@RequiredArgsConstructor
@RestController
@Service
@EnableScheduling
@Tag(name = "User API", description = "API for user management")
public class UserController {

    private final UserRepository userRepo;

    @PostMapping("/addUser") //check
    public ResponseEntity<?> addUser(@RequestBody @Valid UserDTO user, BindingResult bindingResult) {
        try{
            if(!bindingResult.hasErrors())
            return new ResponseEntity<>(userRepo.CreateUser(user), HttpStatus.OK);
            else {
                List<FieldError> errors = bindingResult.getFieldErrors();
                List<String> errorMessages = new ArrayList<>();
                for (FieldError error : errors ) {
                    errorMessages.add(error.getDefaultMessage());
                }
                return new ResponseEntity<>(errorMessages, HttpStatus.INTERNAL_SERVER_ERROR);
            }
        }
        catch (Exception e){
            return new ResponseEntity<>(e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @GetMapping("/getAllUsers") //check
    @Operation(summary = "Get all users")
    public ResponseEntity<?> getAllUsers() {
        try{
            return new ResponseEntity<List<UserDTO>>(userRepo.GetAllUsers(), HttpStatus.OK);
        } catch (ExceptionUserService e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @GetMapping("/getUser/{id}") //check
    public ResponseEntity<?> getUserById(@PathVariable("id") String id){
        try{
            return new ResponseEntity<>(userRepo.GetUserById(id), HttpStatus.OK);
        } catch (ExceptionUserService e){
            return new ResponseEntity<>(e.getMessage(), HttpStatus.NOT_FOUND);
        }
    }

    @PutMapping("/updateUserByID/{id}")
    public ResponseEntity<?> updateUserByID(@PathVariable("id") String id, @RequestBody UserDTO user){
        try {
            return new ResponseEntity<>(userRepo.UpdateUserById(id, user), HttpStatus.OK);
        } catch (ExceptionUserService e){
            return new ResponseEntity<>(e.getMessage(), HttpStatus.NOT_FOUND);
        }
    }

    @DeleteMapping("/deleteUserByID/{id}")
    public ResponseEntity<?> deleteUserByID(@PathVariable("id") String id){
        try{
            userRepo.DeleteUserById(id);
            return new ResponseEntity<>("Deleted user with id " + id, HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.NOT_FOUND);
        }
    }

    public void DeactivateUsers(){
        try{
            int counter = 0;
            List<UserDTO> users = userRepo.GetAllUsers();
            for (UserDTO user: users) {
                user.setActive(false);
                userRepo.UpdateUserById(user.getUserId(), user);
                counter++;
            }
            System.out.println(counter + " users are deactivated");
        } catch (Exception e){
            System.out.println(e.getMessage());
        }
    }
}