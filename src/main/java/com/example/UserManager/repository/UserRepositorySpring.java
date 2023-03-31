package com.example.UserManager.repository;

import com.example.UserManager.exception.ExceptionUserService;
import com.example.UserManager.model.UserDTO;
import jakarta.validation.ConstraintViolation;
import jakarta.validation.Validator;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.BeanUtils;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import static com.example.UserManager.repository.CheckValues.getNullPropertyNames;

@Repository
@Profile("spring")
@RequiredArgsConstructor
public class UserRepositorySpring implements UserRepository {
    private final UserRepositoryMongo userRepo;
    private final Validator validator;

    @Override
    public UserDTO CreateUser(UserDTO user) { //check
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
        List<UserDTO> users = new ArrayList<>(userRepo.findAll());

        if (!users.isEmpty()) {
            return users;
        } else throw new ExceptionUserService(ExceptionUserService.NoUsersFound());
    }

    @Override
    public UserDTO UpdateUserById(String id, UserDTO user) throws ExceptionUserService {
        Optional<UserDTO> userOptional = userRepo.findById(id);

        if(userOptional.isPresent()) {
            UserDTO userToUpdate = userOptional.get();
            UserDTO tempDTO = new UserDTO();

            BeanUtils.copyProperties(userToUpdate, tempDTO);
            BeanUtils.copyProperties(user, tempDTO, getNullPropertyNames(user));

            Set<ConstraintViolation<UserDTO>> violations = validator.validate(tempDTO);

            if (violations.isEmpty()) {
                BeanUtils.copyProperties(tempDTO, userToUpdate, getNullPropertyNames(tempDTO));

                userRepo.save(userToUpdate);
            } else {
                BeanUtils.copyProperties(userToUpdate, user, getNullPropertyNames(userToUpdate));

                throw new ExceptionUserService(violations.stream()
                        .map(ConstraintViolation::getMessage)
                        .collect(Collectors.joining("\n")));
            }
            return userToUpdate;
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
