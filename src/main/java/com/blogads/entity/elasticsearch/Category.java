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
 * @since 03/05/2022 - 18:05
 */
@Getter
@Setter
@Document(indexName = "category")
@NoArgsConstructor
@AllArgsConstructor
public class Category {
    @Id
    private int CategoryId;

    @Field(type = FieldType.Text)
    private String name;
}
