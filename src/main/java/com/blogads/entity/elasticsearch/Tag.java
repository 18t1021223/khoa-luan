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
 * @since 03/05/2022 - 18:08
 */
@Getter
@Setter
@Document(indexName = "tag")
@AllArgsConstructor
@NoArgsConstructor
public class Tag {
    @Id
    private int tagId;

    @Field(type = FieldType.Text)
    private String name;
}
