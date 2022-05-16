package com.blogads.controller.admin.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @author NhatPA
 * @since 28/02/2022 - 03:13
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class StatisticDto {

    private int posts;

    private int categories;

    private int hashtags;
}
