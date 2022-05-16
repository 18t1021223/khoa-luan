package com.blogads.aop;

import com.blogads.entity.elasticsearch.Admin;
import com.blogads.entity.elasticsearch.Category;
import com.blogads.entity.elasticsearch.Tag;
import com.blogads.entity.mysql.Post;
import com.blogads.repository.elasticsearch.PostElasticSearchRepository;
import com.blogads.repository.mysql.PostRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

import static com.blogads.aop.AdminControllerAop.POST_VIEW;

/**
 * @author NhatPA
 * @since 06/03/2022 - 14:54
 */
@Component
@Slf4j
public class ScheduleUser {

    @Autowired
    private PostRepository postRepository;

    @Autowired
    private RedisTemplate<String, Object> redisTemplate;

    @Autowired
    private PostElasticSearchRepository postElasticSearchRepository;

    /**
     * starting in 5s
     * loop 1'
     */
    @Scheduled(initialDelay = 5 * 1000, fixedDelay = 5 * 1000) //60 * 1000
    @Transactional(readOnly = true, isolation = Isolation.SERIALIZABLE)
    public void syncElasticSearch() {
        List<Post> postSql = postRepository.findAll();
        postSql.parallelStream()
                .forEach(i -> {
                    try {
                        com.blogads.entity.elasticsearch.Post post = new com.blogads.entity.elasticsearch.Post();
                        post.setPostId(i.getPostId());
                        post.setTitle(i.getTitle());
                        post.setViewNumber(i.getViewNumber());
                        post.setImage(i.getImage());
                        post.setCreatedAt(i.getCreatedAt());
                        post.setUpdatedAt(i.getUpdatedAt());
                        post.setPublished(i.isPublished());
                        post.setDescription(i.getDescription());
                        post.setPostTags(i.getPostTags()
                                .stream()
                                .map(is -> new Tag(is.getTag().getTagId(), is.getTag().getName()))
                                .collect(Collectors.toList())
                        );
                        post.setCategory(new Category(i.getCategory().getCategoryId(), i.getCategory().getName()));
                        post.setAdmin(new Admin(i.getAdmin().getAdminId(),
                                i.getAdmin().getFullName(),
                                i.getAdmin().getUsername(),
                                i.getAdmin().getEnable())
                        );
                        postElasticSearchRepository.save(post);
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                });
    }

    @Scheduled(initialDelay = 5 * 1000, fixedDelay = 30L * 1000) //15 * 60 * 1000
    @Transactional(rollbackFor = Exception.class)
    public void updatePostView() {
        Set<String> keys = redisTemplate.keys(POST_VIEW + "*");
        if (keys != null) {
            postRepository.findByPostIdIn(keys.stream()
                    .map(i -> new Integer(i.substring(POST_VIEW.length())))
                    .collect(Collectors.toList())
            ).parallelStream()
                    .forEach(i -> {
                                postRepository.updateView(i.getPostId(),
                                        i.getView() + (int) redisTemplate.opsForValue().get(POST_VIEW + i.getPostId())
                                );
                                redisTemplate.delete(POST_VIEW + i.getPostId());
                            }
                    );
        }
    }
}
