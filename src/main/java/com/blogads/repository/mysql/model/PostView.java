package com.blogads.repository.mysql.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
public class PostView {
    private int postId;
    private int view;
}
