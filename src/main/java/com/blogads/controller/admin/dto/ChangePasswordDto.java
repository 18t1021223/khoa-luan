package com.blogads.controller.admin.dto;

import lombok.Getter;
import lombok.Setter;
import org.hibernate.validator.constraints.Length;

import javax.validation.constraints.NotBlank;

@Getter
@Setter
public class ChangePasswordDto {
    @NotBlank
    private String currentPass;
    @NotBlank
    @Length(min = 8, max = 150)
    private String newPass;
    @NotBlank
    @Length(min = 8, max = 150)
    private String confirmPass;
}
