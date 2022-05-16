package com.blogads.entity.mysql;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.Hibernate;
import org.springframework.data.annotation.CreatedBy;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import javax.persistence.*;
import java.util.Date;
import java.util.List;
import java.util.Objects;

/**
 * @author NhatPA
 * @since 25/02/2022 - 01:03
 */
@Table(name = "post")
@Entity
@Getter
@Setter
@EntityListeners(AuditingEntityListener.class)
@NoArgsConstructor
public class Post {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int postId;

    @Column(length = 70)
    private String title;

    @Column(length = 300)
    private String description;

    @Column(nullable = false)
    private boolean isPublished;

    @Lob
    @Column(length = Integer.MAX_VALUE, insertable = false)
    private String content;

    @Column(nullable = false)
    private int viewNumber;

    @CreatedDate
    @Column(nullable = false, updatable = false)
    @Temporal(value = TemporalType.TIMESTAMP)
    private Date createdAt;

    @LastModifiedDate
    @Column(nullable = false)
    @Temporal(value = TemporalType.TIMESTAMP)
    private Date updatedAt;

    @JsonIgnore
    @OneToMany(mappedBy = "post", cascade = {CascadeType.ALL}, fetch = FetchType.EAGER)
    private List<PostTag> postTags;

    @ManyToOne
    @JoinColumn(name = "category_id", nullable = false)
    private Category category;

    @Column
    private String image;

    @ManyToOne
    @JoinColumn(name = "admin_id", nullable = false, updatable = false)
    @CreatedBy
    private Admin admin;

    @Transient
    private String author;

    /*Constructor*/
    public Post(String author) {
        this.author = author;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || Hibernate.getClass(this) != Hibernate.getClass(o)) return false;
        Post post = (Post) o;
        return postId != 0 && Objects.equals(postId, post.postId);
    }

    @Override
    public int hashCode() {
        return getClass().hashCode();
    }
}
