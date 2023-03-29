package com.example.UserManager.repository;

import com.example.UserManager.model.UserDTO;
import org.springframework.context.annotation.Profile;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
@Profile("template")
public class UserRepositoryTemplate implements UserRepository {

    //private final MongoTemplate mongoTemplate;

    @Override
    public UserDTO CreateUser(UserDTO user) {

        return user;
    }

    @Override
    public UserDTO GetUserById(String id) {

        return null;
    }

    @Override
    public List<UserDTO> GetAllUsers() {

        return null;
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