package com.example.UserManager.repository;

import com.example.UserManager.exception.ExceptionUserService;
import com.example.UserManager.model.UserDTO;

import java.util.List;

public interface UserRepository {
    public UserDTO CreateUser(UserDTO user);
    public UserDTO GetUserById(String id) throws ExceptionUserService;
    public List<UserDTO> GetAllUsers() throws ExceptionUserService;
    public UserDTO UpdateUserById(String id, UserDTO userDTO) throws ExceptionUserService;
    public void MergeUsers();
    public void DeleteUserById(String id) throws ExceptionUserService;

}
