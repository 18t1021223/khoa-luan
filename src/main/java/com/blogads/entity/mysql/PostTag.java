package com.blogads.entity.mysql;

import com.blogads.entity.mysql.id.PostTagId;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;

/**
 * @author NhatPA
 * @since 25/02/2022 - 02:04
 */
@Table(name = "post_tag")
@Entity
@Getter
@Setter
@NoArgsConstructor
@EqualsAndHashCode
public class PostTag {

    @EmbeddedId
    private PostTagId id;

    @ManyToOne(fetch = FetchType.LAZY)
    @MapsId("postId")
    @JoinColumn(name = "post_id", nullable = false)
    private Post post;

    @ManyToOne(fetch = FetchType.LAZY)
    @MapsId("tagId")
    @JoinColumn(name = "tag_id", nullable = false)
    private Tag tag;

    public PostTag(Post post, Tag tag) {
        this.post = post;
        this.tag = tag;
        this.id = new PostTagId(post.getPostId(), tag.getTagId());
    }
}
