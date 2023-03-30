package com.example.UserManager.repository;

import com.example.UserManager.model.UserDTO;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Profile;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Repository;
import org.springframework.data.mongodb.core.query.Query;


import java.util.List;

@Component
@Profile("template")
@RequiredArgsConstructor
public class UserRepositoryTemplate implements UserRepository {
    private final MongoTemplate mongoTemplate;

    @Override
    public UserDTO CreateUser(UserDTO user) {

        return user;
    }

    @Override
    public UserDTO GetUserById(String id) {
        Query query = new Query();
        query.addCriteria(Criteria.where("_id").is(id));
        return null;
    }

    @Override
    public List<UserDTO> GetAllUsers() {
        Query query = new Query();
        List<UserDTO> users = mongoTemplate.findAll(UserDTO.class, "userDTO");
        System.out.println("OK!");
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