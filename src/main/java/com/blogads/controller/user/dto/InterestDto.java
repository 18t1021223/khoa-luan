package com.blogads.controller.user.dto;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.util.Map;

/**
 * Crawl data
 *
 * @author NhatPA
 * @since 26/02/2022 - 21:35
 */
@Data
@AllArgsConstructor
public class InterestDto {

    private Map<Map<Integer, Integer>, Map<Integer, Integer>> listData;
}
