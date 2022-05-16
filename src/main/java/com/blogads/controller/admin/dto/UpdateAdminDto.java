package com.blogads.controller.admin.dto;

import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.NotNull;

@Getter
@Setter
public class UpdateAdminDto {
    @NotNull
    private Integer adminId;
    @NotNull
    private Boolean enable;
}