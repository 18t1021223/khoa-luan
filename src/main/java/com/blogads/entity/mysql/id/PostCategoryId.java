package com.blogads.entity.mysql.id;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.Embeddable;
import java.io.Serializable;

/**
 * @author NhatPA
 * @since 25/02/2022 - 01:55
 */
@Embeddable
@Data
@AllArgsConstructor
@NoArgsConstructor
public class PostCategoryId implements Serializable {

    private int postId;

    private int categoryId;
}
