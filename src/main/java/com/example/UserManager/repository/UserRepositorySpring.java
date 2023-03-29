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
    public UserDTO CreateUser(UserDTO user) { //check
        userRepo.save(user);
        return user;
    }

    @Override
    public UserDTO GetUserById(String id) throws ExceptionUserService { //check
        Optional<UserDTO> userOptional = userRepo.findById(id);
        if (userOptional.isPresent()) return userOptional.get();
        else throw new ExceptionUserService(ExceptionUserService.ThisUserDoesNotExist());
    }

    @Override
    public List<UserDTO> GetAllUsers() throws ExceptionUserService { //check
        List<UserDTO> users = userRepo.findAll();
        if(users.size() > 0) return users;
        else throw new ExceptionUserService(ExceptionUserService.NoUsersFound());
    }

    @Override
    public UserDTO UpdateUserById(String id, UserDTO user) throws ExceptionUserService { //fix
        Optional<UserDTO> userOptional = userRepo.findById(id);
        if(userOptional.isPresent()) {
            UserDTO userToSave = userOptional.get();
            UserDTO userPrimary = userOptional.get();
            userToSave.setName(user.getName() != null ? user.getName() : userPrimary.getName());
            userToSave.setSurname(user.getName() != null ? user.getSurname() : userPrimary.getSurname());
            userToSave.setEmail(user.getName() != null ? user.getEmail() : userPrimary.getEmail());
            userToSave.setPhoneNumber(user.getPassword() != null ? user.getPassword() : userPrimary.getPassword());
            userToSave.setPhoneNumber(user.getPhoneNumber() != null ? user.getPhoneNumber() : userPrimary.getPhoneNumber());
            userRepo.save(userToSave);
            return userToSave;
        } else throw new ExceptionUserService(ExceptionUserService.ThisUserDoesNotExist());
    }

    @Override
    public void MergeUsers() {
            //Implementation ???
    }

    @Override
    public void DeleteUserById(String id) throws ExceptionUserService {
        Optional<UserDTO> userOptional = userRepo.findById(id);
        if (userOptional.isPresent()) userRepo.deleteById(id);
        else throw new ExceptionUserService(ExceptionUserService.ThisUserDoesNotExist());

    }
}
