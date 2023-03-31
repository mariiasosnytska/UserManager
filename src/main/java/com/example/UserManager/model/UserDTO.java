package com.example.UserManager.model;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.*;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.scheduling.annotation.Scheduled;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Document
public class UserDTO {
    @Id
    private String userId;
    @NotEmpty(message = "{name.notempty}")
    private String name;
    @NotEmpty(message = "{surname.notempty}")
    private String surname;
    private String phoneNumber;
    @NotEmpty(message = "{email.notempty}")
    @Email(message = "{email.valid}")
    private String email;
    @NotEmpty(message = "{Password should not be empty}")
    @NotNull(message = "{password.notnull}")
    @Size(min = 3, max = 30, message = "{password.validsize}")
    private String password; //maybe char[] better idk
    private boolean isActive;

}
