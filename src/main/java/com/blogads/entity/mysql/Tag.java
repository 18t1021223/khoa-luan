package com.blogads.entity.mysql;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.util.List;

/**
 * @author NhatPA
 * @since 25/02/2022 - 01:05
 */
@Table(name = "tag")
@Entity
@Getter
@Setter
@NoArgsConstructor
@EqualsAndHashCode
public class Tag {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int tagId;

    @Column(length = 50, nullable = false, unique = true)
    private String name;

    @JsonIgnore
    @OneToMany(mappedBy = "tag", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<PostTag> postTags;

    public Tag(String name) {
        this.name = name;
    }
}
