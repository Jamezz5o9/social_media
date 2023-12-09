package com.prophius.socialMedia.user.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.Setter;

@Getter@Setter
public class AppUserRegistrationDTO {

    @NotEmpty(message = "first name can not be blank")
    private String firstName;

    @NotEmpty(message = "last name can not be blank")
    private String lastName;

    @NotEmpty(message = "user name can not be blank")
    private String username;

    @Email(regexp = ".+[@].+[\\.].+", message = "Invalid email")
    @NotEmpty(message = "Email can not be blank")
    private String email;

    @Size(min = 6, max = 20, message = "Invalid password, password must be between 6 to 20 characters")
    @NotEmpty(message = "Password can not be blank")
    private String password;
}
