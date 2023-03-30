package com.example.UserManager.repository;

import com.example.UserManager.exception.ExceptionUserService;
import com.example.UserManager.model.UserDTO;
import jakarta.validation.ConstraintViolation;
import jakarta.validation.Valid;
import jakarta.validation.Validator;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.BeanWrapper;
import org.springframework.beans.BeanWrapperImpl;
import org.springframework.boot.context.properties.bind.BindResult;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Repository;
import org.springframework.validation.BindingResult;

import java.util.HashSet;
import java.util.List;
import java.util.Optional;
import java.util.Set;

@Repository
@Profile("default")
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
            }

            return userToUpdate;
        } else throw new ExceptionUserService(ExceptionUserService.ThisUserDoesNotExist());
    }

    private String[] getNullPropertyNames(Object source) {
        final BeanWrapperImpl src = new BeanWrapperImpl(source);
        java.beans.PropertyDescriptor[] pds = src.getPropertyDescriptors();

        Set<String> emptyNames = new HashSet<>();
        for (java.beans.PropertyDescriptor pd : pds) {
            Object srcValue = src.getPropertyValue(pd.getName());
            if (srcValue == null) emptyNames.add(pd.getName());
        }
        String[] result = new String[emptyNames.size()];
        return emptyNames.toArray(result);
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
