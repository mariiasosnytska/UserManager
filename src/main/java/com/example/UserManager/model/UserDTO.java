package com.example.UserManager.model;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.*;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Document
public class UserDTO {
    @Id
    private String userId;
    @NotEmpty(message = "Name should not be empty")
    private String name;
    @NotEmpty(message = "Surname should not be empty")
    private String surname;
    private String phoneNumber;
    @NotEmpty(message = "Email should not be empty")
    @Email(message = "Email should be valid")
    private String email;
    @NotEmpty(message = "Password should not be empty")
    @NotNull(message = "Password should not be null")
    @Size(min = 3, max = 30, message = "Password must have from 3 to 30 symbols")
    private String password; //maybe char[] better idk
    private boolean isActive;

}
