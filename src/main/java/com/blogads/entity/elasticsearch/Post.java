package com.blogads.entity.elasticsearch;

import lombok.Getter;
import lombok.Setter;
import org.springframework.data.annotation.Id;
import org.springframework.data.elasticsearch.annotations.Document;
import org.springframework.data.elasticsearch.annotations.Field;
import org.springframework.data.elasticsearch.annotations.FieldType;

import java.util.Date;
import java.util.List;

/**
 * @author NhatPA
 * @since 27/04/2022 - 09:43
 */
@Getter
@Setter
@Document(indexName = "post",createIndex = false)
public class Post {

    public final static String ANALYZER = "my_analyzer";

    @Id
    @Field(type = FieldType.Integer)
    private int postId;

    @Field(type = FieldType.Text, searchAnalyzer = ANALYZER)
    private String title;

    @Field(type = FieldType.Text, searchAnalyzer = ANALYZER, name = "description")
    private String description;

    @Field(type = FieldType.Boolean, name = "isPublished")
    private boolean isPublished;

    private int viewNumber;

    private Date createdAt;

    private Date updatedAt;

    @Field(type = FieldType.Object)
    private List<Tag> postTags;

    @Field(type = FieldType.Object)
    private Category category;

    @Field(type = FieldType.Keyword)
    private String image;

    @Field(type = FieldType.Object)
    private Admin admin;
}
