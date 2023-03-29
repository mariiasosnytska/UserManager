package com.example.UserManager.repository;

import com.example.UserManager.model.UserDTO;
import org.springframework.stereotype.Repository;

import java.util.List;

public interface UserRepository {
    public UserDTO CreateUser(UserDTO user);
    public UserDTO GetUserById(String id);
    public List<UserDTO> GetAllUsers();
    public UserDTO UpdateUserById(String id, UserDTO user);
    public void MergeUsers();
    public void DeleteUserById(String id);

}
