package com.blogads.repository.mysql;

import com.blogads.entity.mysql.Post;
import com.blogads.repository.mysql.model.PostView;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.Collection;
import java.util.List;
import java.util.Optional;

@Repository
public interface PostRepository extends JpaRepository<Post, Integer> {

    int countByAdmin_AdminId(int id);

    Page<Post> findByAdmin_AdminId(Integer adminId, Pageable pageable);

    @Query("select new com.blogads.repository.mysql.model.PostView(p.postId,p.viewNumber) from Post p where p.postId in ?1")
    List<PostView> findByPostIdIn(Collection<Integer> postIds);

    /**
     * select p from Post p where p.title = ?1 or p.description = ?2
     *
     * @param search
     * @param pageable
     * @return {@link Page}
     * @author NhatPA
     */
    @Query("select p from Post p where ( p.title like %:search% or p.description like %:search% ) and p.isPublished = true")
    Page<Post> findAllByTitleOrDescriptionAndIsPublishedIsTrue(String search, Pageable pageable);

    @Query("select p from Post p where p.admin.adminId = ?1 and p.isPublished=true")
    Page<Post> findByAdmin_AdminId(int id, Pageable pageable);

    /**
     * @param tagId
     * @param pageable
     * @return {@link Page}
     * @author NhatPA
     */
    @Query("select p from Post p join p.postTags postTags where postTags.tag.tagId = ?1 and p.isPublished = true")
    Page<Post> findByPostTags_Tag_TagIdAndIsPublishedIsTrue(int tagId, Pageable pageable);

    /**
     * find posts by categoryId
     *
     * @param categoryId
     * @param pageable
     * @return {@link Page}
     * @author NhatPA
     */
    @Query("select p from Post p where p.category.categoryId = ?1 and p.isPublished = true")
    Page<Post> findByCategory_CategoryIdAndIsPublishedIsTrue(int categoryId, Pageable pageable);

    /**
     * <pre>select p
     * from post p join post_tags pt on p.post_id = pt.post_id
     * where pt.tag_id in (?1) and pt.post_id <> (?2) and is_published = true</pre>
     *
     * @param ids list tag id
     * @return {@link Page}
     * @author NhatPA
     */
    @Query("select distinct p " +
            "from Post p join p.postTags postTags " +
            "where postTags.tag.tagId in ?1 and postTags.post.postId <> ?2 and p.isPublished = true")
    Page<Post> findByRelatedPosts(Collection<Integer> ids, int postId, Pageable pageable);

    /**
     * find post by id and is published
     *
     * @param integer
     * @return {@link Optional}
     * @author NhatPA
     */
    @Query("select p from Post p where p.postId = ?1 and p.isPublished = true")
    Optional<Post> findByPostIdAndIsPublishedIsTrue(Integer integer);

    /**
     * select * from post where title like '%search%'
     *
     * @param search
     * @param validPageable
     * @return {@link Page}
     * @author NhatPA
     */
    Page<Post> findAllByTitleContainingAndAdmin_AdminId(String search, int id, Pageable validPageable);

    @Query("select p from Post p " +
            "where p.title like %?1% or " +
            "p.admin.fullName like %?1% or " +
            "p.admin.username like %?1%")
    Page<Post> findAllByTitleContainingOrAdminContaining(String search, Pageable validPageable);

    /**
     * get all post published
     *
     * @param pageable
     * @return {@link Page}
     * @author NhatPA
     */
    @Query("select p from Post p where p.isPublished = true")
    Page<Post> findByIsPublishedIsTrue(Pageable pageable);

    /**
     * update view post
     *
     * @param postId post id
     * @param i      view post
     * @author NhatPA
     */
    @Query(value = "update Post set viewNumber = ?2 where postId = ?1 ")
    @Modifying
    void updateView(int postId, int i);
}