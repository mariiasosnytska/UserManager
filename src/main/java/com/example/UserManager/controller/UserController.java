package com.example.UserManager.controller;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import com.example.UserManager.exception.ExceptionUserService;
import com.example.UserManager.model.UserDTO;
import com.example.UserManager.repository.UserRepository;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.annotation.*;

@RequiredArgsConstructor
@RestController
public class UserController {

    private final UserRepository userRepo;

    @PostMapping("/addUser")
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

    @GetMapping("/getAllUsers")
    public ResponseEntity<?> getAllUsers() {
        try{
            return new ResponseEntity<List<UserDTO>>(userRepo.GetAllUsers(), HttpStatus.OK);
        } catch (ExceptionUserService e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @GetMapping("/getUser/{id}")
    public ResponseEntity<?> getUserById(@PathVariable("id") String id){
        try{
            return new ResponseEntity<>(userRepo.GetUserById(id), HttpStatus.OK);
        } catch (ExceptionUserService e){
            return new ResponseEntity<>(e.getMessage(), HttpStatus.NOT_FOUND);
        }
    }

    @PutMapping("/updateUserByID/{id}")
    public ResponseEntity<?> updateUserByID(@PathVariable("id") String id, @RequestBody UserDTO user){
        /*Optional<UserDTO> userOptional = userRepo.findById(id);
        if(userOptional.isPresent())
        {
            UserDTO userToSave = userOptional.get();
            userToSave.setName(user.getName() != null ? user.getName() : userToSave.getName());
            userToSave.setSurname(user.getName() != null ? user.getSurname() : userToSave.getSurname());
            userToSave.setEmail(user.getName() != null ? user.getEmail() : userToSave.getEmail());
            userToSave.setPhoneNumber(user.getPassword() != null ? user.getPassword() : userToSave.getPassword());
            userToSave.setPhoneNumber(user.getPhoneNumber() != null ? user.getPhoneNumber() : userToSave.getPhoneNumber());
            userRepo.save(userToSave);*/
            return new ResponseEntity<>(userRepo.UpdateUserById(id, user), HttpStatus.OK);
        //}
        //else return new ResponseEntity<>("Sorry, user with id " + id + "not found", HttpStatus.NOT_FOUND);
    }

    @DeleteMapping("/deleteUserByID/{id}")
    public ResponseEntity<?> deleteUserByID(@PathVariable("id") String id){
        try{
            //userRepo.deleteById(id);
            return new ResponseEntity<>("Deleted user with id " + id, HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.NOT_FOUND);
        }
    }
}