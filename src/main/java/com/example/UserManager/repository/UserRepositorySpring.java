package com.example.UserManager.repository;

import com.example.UserManager.exception.ExceptionUserService;
import com.example.UserManager.model.UserDTO;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.context.properties.bind.BindResult;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Repository;
import org.springframework.validation.BindingResult;

import java.util.List;
import java.util.Optional;

@Repository
@Profile("default")
@RequiredArgsConstructor
public class UserRepositorySpring implements UserRepository {
    private final UserRepositoryMongo userRepo;
    @Override
    public UserDTO CreateUser(@Valid UserDTO user) {

        userRepo.save(user);
        return user;
    }

    @Override
    public UserDTO GetUserById(String id) throws ExceptionUserService {
        Optional<UserDTO> userOptional = userRepo.findById(id);
        if (userOptional.isPresent()) return userOptional.get();
        else throw new ExceptionUserService(ExceptionUserService.ThisUserDoesNotExist());
    }

    @Override
    public List<UserDTO> GetAllUsers() throws ExceptionUserService {
        List<UserDTO> users = userRepo.findAll();
        if(users.size() > 0) return users;
        else throw new ExceptionUserService(ExceptionUserService.NoUsersFound());
    }

    @Override
    public UserDTO UpdateUserById(String id, UserDTO user) {
        Optional<UserDTO> userOptional = userRepo.findById(id);
        if(userOptional.isPresent()) {
            UserDTO userToSave = userOptional.get();
            userToSave.setName(user.getName() != null ? user.getName() : userToSave.getName());
            userToSave.setSurname(user.getName() != null ? user.getSurname() : userToSave.getSurname());
            userToSave.setEmail(user.getName() != null ? user.getEmail() : userToSave.getEmail());
            userToSave.setPhoneNumber(user.getPassword() != null ? user.getPassword() : userToSave.getPassword());
            userToSave.setPhoneNumber(user.getPhoneNumber() != null ? user.getPhoneNumber() : userToSave.getPhoneNumber());
            userRepo.save(userToSave);
            return userToSave;
        } else return null;
    }

    @Override
    public void MergeUsers() {
            //Implementation ???
    }

    @Override
    public void DeleteUserById(String id) {
        userRepo.deleteById(id);
    }
}
