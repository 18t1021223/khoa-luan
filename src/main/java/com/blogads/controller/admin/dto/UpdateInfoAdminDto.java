package com.blogads.controller.admin.dto;

import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.NotBlank;

/**
 * @author NhatPA
 * @since 05/05/2022 - 20:34
 */
@Getter
@Setter
public class UpdateInfoAdminDto {

    @NotBlank
    private String fullName;

    private String address;
}
