package com.example.UserManager.repository;

import com.example.UserManager.model.UserDTO;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface UserRepository extends MongoRepository<UserDTO,String>{

}
