package com.blogads.repository.mysql;

import com.blogads.entity.mysql.PostTag;
import com.blogads.entity.mysql.id.PostCategoryId;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface PostTagRepository extends JpaRepository<PostTag, PostCategoryId> {

    @Modifying
    @Query("delete from PostTag p where p.post.postId = ?1")
    void deleteByPost(int postId);
}