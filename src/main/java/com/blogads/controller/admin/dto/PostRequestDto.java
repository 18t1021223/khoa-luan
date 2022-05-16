package com.blogads.controller.admin.dto;

import com.blogads.annotation.valid.File;
import lombok.Data;
import org.springframework.web.multipart.MultipartFile;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

/**
 * @author NhatPA
 * @since 02/03/2022 - 00:19
 */
@Data
public class PostRequestDto {

    @NotNull
    @NotBlank
    @Size(min = 1, max = 70)
    private String title;

    private String description;

    @NotNull
    @Min(1)
    private int category;

    private Integer[] hashtag;

    private boolean published;

    @File(format = {"JPG", "PNG", "GIF", "jpeg"})
    private MultipartFile image;

    /*Use for update post, url old*/
    private String currentUrlImage;

    @NotNull
    private String content;

//    private String author;
}
