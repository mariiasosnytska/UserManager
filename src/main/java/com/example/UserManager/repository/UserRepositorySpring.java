package com.example.UserManager.repository;

import com.example.UserManager.model.UserDTO;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
@Profile("default")
@RequiredArgsConstructor
public class UserRepositorySpring implements UserRepository {
    private final UserRepositoryMongo userRepo;
    @Override
    public UserDTO CreateUser(UserDTO user) { //?
        userRepo.save(user);
        return user;
    }

    @Override
    public UserDTO GetUserById(String id) {

        return null;
    }

    @Override
    public List<UserDTO> GetAllUsers() {
        List<UserDTO> users = userRepo.findAll();
        return users;
    }

    @Override
    public UserDTO UpdateUserById(String id, UserDTO user) {
        return user;
    }

    @Override
    public void MergeUsers() {

    }

    @Override
    public void DeleteUserById(String id) {

    }
}
