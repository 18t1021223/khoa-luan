package com.blogads.controller.admin.dto;

import lombok.Getter;
import lombok.Setter;
import org.hibernate.validator.constraints.Length;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;

@Getter
@Setter
public class RegisterDto {
    @NotBlank
    @Email
    private String email;

    @NotBlank
    @Length(min = 8, max = 150)
    private String password;

    @NotBlank
    @Length(min = 8, max = 150)
    private String confirmPassword;

    @NotBlank
    @Length(min = 3, max = 50)
    private String fullName;
}
