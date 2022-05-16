package com.blogads.repository.elasticsearch;

import com.blogads.entity.elasticsearch.Post;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

/**
 * @author NhatPA
 * @since 27/04/2022 - 09:53
 */
@Repository
public interface PostElasticSearchRepository extends ElasticsearchRepository<Post, String> {

    @Modifying
    void deleteByPostId(int id);
}
