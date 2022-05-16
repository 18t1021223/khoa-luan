package com.blogads.controller.admin.dto;

import lombok.Getter;
import lombok.Setter;
import org.hibernate.validator.constraints.Length;

import javax.validation.constraints.NotBlank;

/**
 * @author NhatPA
 * @since 04/05/2022 - 00:43
 */
@Getter
@Setter
public class NewPasswordDto {
    @NotBlank
    @Length(min = 8, max = 150)
    private String password;
    @NotBlank
    @Length(min = 8, max = 150)
    private String confirmPassword;

    private String verifyToken;
}
