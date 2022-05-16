package com.blogads.controller.user.service;

import com.blogads.entity.mysql.Category;
import com.blogads.entity.mysql.Post;
import com.blogads.entity.mysql.Tag;
import com.blogads.exception.BlogAdsException;
import com.blogads.repository.elasticsearch.PostElasticSearchRepository;
import com.blogads.repository.mysql.CategoryRepository;
import com.blogads.repository.mysql.PostRepository;
import com.blogads.repository.mysql.TagRepository;
import com.blogads.utils.Constants;
import com.blogads.utils.PageRes;
import com.blogads.utils.PaginationUtils;
import org.elasticsearch.common.unit.Fuzziness;
import org.elasticsearch.index.query.BoolQueryBuilder;
import org.elasticsearch.index.query.QueryBuilder;
import org.elasticsearch.index.query.QueryBuilders;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.data.domain.*;
import org.springframework.data.elasticsearch.core.ElasticsearchOperations;
import org.springframework.data.elasticsearch.core.ElasticsearchRestTemplate;
import org.springframework.data.elasticsearch.core.SearchHitSupport;
import org.springframework.data.elasticsearch.core.SearchHits;
import org.springframework.data.elasticsearch.core.mapping.IndexCoordinates;
import org.springframework.data.elasticsearch.core.query.*;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

import static com.blogads.exception.BlogAdsException.CATEGORY_NOT_FOUND;
import static com.blogads.exception.BlogAdsException.TAG_NOT_FOUND;
import static com.blogads.utils.BlogAdsUtils.validPageable;
import static com.blogads.utils.Constants.*;

/**
 * @author NhatPA
 * @since 25/02/2022 - 03:27
 */
@Service
public class UserService {

    @Autowired
    private ElasticsearchOperations elasticsearchOperations;

    @Autowired
    private PostElasticSearchRepository postElasticSearchRepository;

    @Autowired
    private PostRepository postRepository;

    @Autowired
    private TagRepository tagRepository;

    @Autowired
    private CategoryRepository categoryRepository;

    //region Post

    /**
     * find posts by {@link Post#getTitle()} and {@link Post#getDescription()}
     *
     * @param search
     * @param pageable
     * @return {@link Page}
     * @author NhatPA
     */
    public PageRes<Post> findAll(String search, Pageable pageable) {
        return PaginationUtils.buildPageRes(
                postRepository.findAllByTitleOrDescriptionAndIsPublishedIsTrue(search, validPageable(pageable))
        );
    }

    public PageRes<com.blogads.entity.elasticsearch.Post> findAllByElasticsearch(String search, Pageable pageable) {
        System.out.println(postElasticSearchRepository.findAll());
        QueryBuilder queryMatch = QueryBuilders
                .multiMatchQuery(search, "title", "description", "admin.fullName", "category.name")
                .analyzer(com.blogads.entity.elasticsearch.Post.ANALYZER)
                .fuzziness(Fuzziness.AUTO);

        QueryBuilder filter = QueryBuilders
                .termQuery("isPublished", true);

        BoolQueryBuilder builder = new BoolQueryBuilder();
        builder.must(queryMatch)
                .filter(filter);

        Query searchQuery = new NativeSearchQueryBuilder()
                .withFilter(builder)
                .withPageable(pageable)
                .build();
        SearchHits<com.blogads.entity.elasticsearch.Post> postHits = elasticsearchOperations
                .search(searchQuery, com.blogads.entity.elasticsearch.Post.class, IndexCoordinates.of("post"));
        return PaginationUtils.buildPageRes(
                new PageImpl<>(
                        SearchHitSupport.searchPageFor(postHits, searchQuery.getPageable())
                                .stream()
                                .map(i -> i.getContent())
                                .collect(Collectors.toList())
                )
        );
    }

    public PageRes<Post> findPostByAdmin(int id, Pageable p) {
        return PaginationUtils.buildPageRes(postRepository.findByAdmin_AdminId(id, p));
    }

    /**
     * find posts by {@link Tag#getTagId()}
     *
     * @param tagId
     * @param pageable
     * @return {@link Page}
     * @author NhatPA
     */
    public PageRes<Post> findAllByTagId(int tagId, Pageable pageable) {
        return PaginationUtils.buildPageRes(
                postRepository.findByPostTags_Tag_TagIdAndIsPublishedIsTrue(tagId, validPageable(pageable))
        );
    }

    /**
     * find posts by {@link Category#getCategoryId()}
     *
     * @param categoryId key of category
     * @param pageable
     * @return {@link Page}
     * @author NhatPA
     */
    public PageRes<Post> findAllByCategoryId(int categoryId, Pageable pageable) {
        return PaginationUtils.buildPageRes(
                postRepository.findByCategory_CategoryIdAndIsPublishedIsTrue(categoryId, validPageable(pageable))
        );
    }

    /**
     * find post by {@link Post#getPostId()}
     *
     * @param postId
     * @return {@link Post}
     * @author NhatPA
     */
    @Cacheable(cacheNames = {POST_CACHE_NAME}, keyGenerator = "keyGenerator")
    public Post findById(Integer postId) {
        return postRepository.findByPostIdAndIsPublishedIsTrue(postId).orElseThrow(() ->
                new BlogAdsException(HttpStatus.NOT_FOUND.value(), BlogAdsException.POST_NOT_FOUND)
        );
    }

    /**
     * find top {@link Constants#RECENT_POSTS_DEFAULT}
     *
     * @return {@link List}
     * @author NhatPA
     */
    public List<Post> getRecentPosts() {
        return postRepository.findByIsPublishedIsTrue(PageRequest.of(0, Constants.RECENT_POSTS_DEFAULT,
                Sort.by(Sort.Direction.DESC, "createdAt")))
                .getContent();
    }

    /**
     * find top {@link Constants#HOST_POSTS_DEFAULT}
     *
     * @return {@link List}
     * @author NhatPA
     */
    public List<Post> getHostPosts() {
        return postRepository.findByIsPublishedIsTrue(PageRequest.of(0, Constants.HOST_POSTS_DEFAULT,
                Sort.by(Sort.Direction.DESC, VIEW_NUMBER, "createdAt")))
                .getContent();
    }


//    /**

    /**
     * TODO: MAYBE UPDATE IN FUTURE
     * find posts related (same tags)
     *
     * @param post
     * @param tags tags of post
     * @return {@link List}
     * @author NhatPA
     */
    public Page<Post> findByRelatedPosts(Post post, List<Tag> tags) {
//        List<Post> postList = postRepository
//                .findByRelatedPosts(tagsId, PageRequest.of(0, RELATED_POSTS_DEFAULT))
//                .getContent();
//        if (postList.size() < RELATED_POSTS_DEFAULT) {
//            return postList;
//        }
//        Map<Post, Integer> analysis = postList.stream()
//                .collect(Collectors.toMap(Function.identity(), postItem ->
//                        this.listMatch(this.postTagsToTagsId(postItem), tagsId))
//                );
        return postRepository.findByRelatedPosts(tags.stream().map(Tag::getTagId).collect(Collectors.toList()),
                post.getPostId(),
                PageRequest.of(0, RELATED_POSTS_DEFAULT));
    }

    public PageRes<Post> findPostByCategory(Post post) {
        /*GET POST BY CATEGORY IF NOT EXISTS TAGS*/
        return PaginationUtils.buildPageRes(
                postRepository.findByCategory_CategoryIdAndIsPublishedIsTrue(post.getCategory().getCategoryId(),
                        PageRequest.of(0, RELATED_POSTS_DEFAULT, Sort.Direction.DESC, VIEW_NUMBER, "createdAt")
                ));
    }
    //endregion

    //region Tag

    /**
     * find all tags of post id
     *
     * @param postId
     * @return
     */
    public List<Tag> findAllTagsOfPost(int postId) {
        return tagRepository.findByPostTags_Post_PostId(postId);
    }

    /**
     * find all show navbar
     *
     * @return {@link List}
     * @author NhatPA
     */
    public List<Tag> findAllTag() {
        return tagRepository.findAll(PageRequest.of(0, Constants.TAG_DEFAULT)).getContent();
    }

    /**
     * find tag by id
     *
     * @param id {@link Tag#getTagId()}
     * @return {@link Tag}
     * @author NhatPA
     */
    public Tag findTag(int id) {
        return tagRepository.findById(id).orElseThrow(() ->
                new BlogAdsException(HttpStatus.BAD_REQUEST.value(), TAG_NOT_FOUND)
        );
    }
    //endregion

    //region category

    /**
     * find top {@link Constants#CATEGORY_DEFAULT} category
     *
     * @return {@link List}
     * @author NhatPA
     */
    public List<Category> findAllCategoryPopulation() {
        return categoryRepository.findAll(PageRequest.of(0, Constants.CATEGORY_DEFAULT,
                Sort.by(Sort.Direction.DESC, "categoryId")))
                .getContent();
    }

    /**
     * find category by id
     *
     * @param id {@link Category#getCategoryId()}
     * @return {@link Category}
     * @author NhatPA
     */
    public Category findCategory(int id) {
        return categoryRepository.findById(id).orElseThrow(() ->
                new BlogAdsException(HttpStatus.BAD_REQUEST.value(), CATEGORY_NOT_FOUND)
        );
    }
    //endregion
}
