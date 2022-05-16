package com.blogads.repository.mysql;

import com.blogads.entity.mysql.Tag;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface TagRepository extends JpaRepository<Tag, Integer> {

    /**
     * @param postId
     * @return {@link List}
     * @author NhatPA
     */
    @Query("select t from Tag t join t.postTags postTags where postTags.post.postId = ?1")
    List<Tag> findByPostTags_Post_PostId(int postId);

    Page<Tag> findByNameContaining(String search, Pageable pageable);

    boolean existsByNameEqualsIgnoreCaseAndTagIdNot(String name,int id);

    boolean existsByNameEqualsIgnoreCase(String name);
}