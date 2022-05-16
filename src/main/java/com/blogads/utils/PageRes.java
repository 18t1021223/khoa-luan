package com.blogads.utils;

import lombok.Data;

import java.util.List;

/**
 * @author NhatPA
 * @since 03/05/2022 - 22:03
 */
@Data
public class PageRes<T> {
    private Long totalElements;
    private Integer size;
    private Integer totalPages;
    private Integer number;
    private List<T> content;
}