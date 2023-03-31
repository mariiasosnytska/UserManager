package com.example.UserManager.repository;

import com.example.UserManager.exception.ExceptionUserService;
import com.example.UserManager.model.UserDTO;
import jakarta.validation.ConstraintViolation;
import jakarta.validation.Validator;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.BeanUtils;
import org.springframework.context.annotation.Profile;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.stereotype.Repository;
import org.springframework.data.mongodb.core.query.Query;


import java.util.List;
import java.util.Set;

import static com.example.UserManager.repository.CheckValues.getNullPropertyNames;

@Repository
@Profile("template")
@RequiredArgsConstructor
public class UserRepositoryTemplate implements UserRepository {
    private final MongoTemplate mongoTemplate;
    private final Validator validator;

    @Override
    public UserDTO CreateUser(UserDTO user) {
        mongoTemplate.save(user, "userDTO");
        return user;
    }

    @Override
    public UserDTO GetUserById(String id) throws ExceptionUserService {
        System.out.println("template");
        Query query = new Query();
        query.addCriteria(Criteria.where("_id").is(id));

        UserDTO userDTO = mongoTemplate.findOne(query, UserDTO.class);

        if (userDTO == null) {
            throw new ExceptionUserService(ExceptionUserService.ThisUserDoesNotExist());
        }
        return userDTO;
    }

    @Override
    public List<UserDTO> GetAllUsers() throws ExceptionUserService {
        List<UserDTO> users = mongoTemplate.findAll(UserDTO.class, "userDTO");

        if(users.size() > 0) return users;
        else throw new ExceptionUserService(ExceptionUserService.NoUsersFound());
    }

    @Override
    public UserDTO UpdateUserById(String id, UserDTO user) throws ExceptionUserService {
        Query query = new Query();
        query.addCriteria(Criteria.where("_id").is(id));
        UserDTO userToUpdate = mongoTemplate.findOne(query, UserDTO.class);

        if(userToUpdate != null) {
            UserDTO tempDTO = new UserDTO();

            BeanUtils.copyProperties(userToUpdate, tempDTO);
            BeanUtils.copyProperties(user, tempDTO, getNullPropertyNames(user));

            Set<ConstraintViolation<UserDTO>> violations = validator.validate(tempDTO);
            if (violations.isEmpty()) {
                BeanUtils.copyProperties(tempDTO, userToUpdate, getNullPropertyNames(tempDTO));
                mongoTemplate.save(userToUpdate);
            } else {
                String errorMessage = "";
                for (ConstraintViolation<UserDTO> violation : violations) {
                    errorMessage += violation.getMessage() + "\n";
                }
                BeanUtils.copyProperties(userToUpdate, user, getNullPropertyNames(userToUpdate));
                throw new ExceptionUserService(errorMessage);
            }

            return userToUpdate;
        } else throw new ExceptionUserService(ExceptionUserService.ThisUserDoesNotExist());
    }

    @Override
    public void MergeUsers() {
    }

    @Override
    public void DeleteUserById(String id) throws ExceptionUserService {
        Query query = new Query();
        query.addCriteria(Criteria.where("_id").is(id));

        UserDTO userDTO = mongoTemplate.findOne(query, UserDTO.class);

        if (userDTO == null) {
            throw new ExceptionUserService(ExceptionUserService.ThisUserDoesNotExist());
        } else mongoTemplate.remove(query, UserDTO.class, "userDTO");
    }
}