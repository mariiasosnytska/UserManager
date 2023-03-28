package com.example.UserManager.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
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
    private String name;
    private String surname;
    private String phoneNumber;
    private String email;
    private String password; //maybe char[] better idk
    private boolean isActive;

}
