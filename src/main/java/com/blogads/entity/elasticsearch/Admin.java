package com.blogads.entity.elasticsearch;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.data.annotation.Id;
import org.springframework.data.elasticsearch.annotations.Document;
import org.springframework.data.elasticsearch.annotations.Field;
import org.springframework.data.elasticsearch.annotations.FieldType;

/**
 * @author NhatPA
 * @since 03/05/2022 - 18:13
 */
@Getter
@Setter
@Document(indexName = "admin")
@NoArgsConstructor
@AllArgsConstructor
public class Admin {

    @Id
    private int adminId;

    @Field(type = FieldType.Text)
    private String fullName;

    @Field(type = FieldType.Text)
    private String username;

    private boolean enable;
}
