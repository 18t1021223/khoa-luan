package com.blogads.controller.user;

import com.blogads.controller.user.service.UserService;
import com.blogads.entity.mysql.Post;
import com.blogads.entity.mysql.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import java.util.List;

import static com.blogads.utils.Constants.CREATED_AT;
import static com.blogads.utils.Constants.POST_SIZE;

/**
 * @author NhatPA
 * @since 24/02/2022 - 22:35
 */
@Controller
public class UserController {

    @Autowired
    private UserService userService;

    private ModelAndView _mav;

    /**
     * @param pageable
     * @param search
     * @return {@link ModelAndView}
     * @author NhatPA
     */
    @GetMapping(value = {"/", "/search"})
    public ModelAndView home(
            @PageableDefault(
                    size = POST_SIZE,
                    sort = CREATED_AT,
                    direction = Sort.Direction.DESC) Pageable pageable,
            @RequestParam(name = "search", defaultValue = "", required = false) String search) {
        _mav = new ModelAndView("home");
        if (!search.isEmpty()) {
            _mav.addObject("posts", userService.findAllByElasticsearch(search, pageable));
        } else {
            _mav.addObject("posts", userService.findAll(search, pageable));
        }
        _mav.addObject("search", search);
        return _mav;
    }

    /**
     * @param response       use for aop
     * @param interestCookie use for aop
     * @param postId
     * @return {@link ModelAndView}
     * @author NhatPA
     */
    @SuppressWarnings("unchecked")
    @GetMapping("/posts/{id}")
    public ModelAndView post(@PathVariable(value = "id") Integer postId) {
        _mav = new ModelAndView("post");
        Post post = userService.findById(postId);
        _mav.addObject("post", post);
        if (post.getPostTags() != null && !post.getPostTags().isEmpty()) {
            List<Tag> tagList = userService.findAllTagsOfPost(postId);
            _mav.addObject("tagOfPost", tagList);
            _mav.addObject("posts", userService.findByRelatedPosts(post, tagList));
        } else {
            _mav.addObject("posts", userService.findPostByCategory(post));
        }
        return _mav;
    }

    @GetMapping("/admin/{id}")
    public ModelAndView home(
            @PageableDefault(
                    size = POST_SIZE,
                    sort = CREATED_AT,
                    direction = Sort.Direction.DESC) Pageable pageable,
            @PathVariable(value = "id") Integer adminId) {
        _mav = new ModelAndView("home");
        _mav.addObject("posts", userService.findPostByAdmin(adminId, pageable));
        return _mav;
    }

    /**
     * get list post by hashtag
     *
     * @param response       use for aop
     * @param interestCookie use for aop
     * @param tagId
     * @param pageable
     * @return {@link ModelAndView}
     * @author NhatPA
     */
    @GetMapping("/hashtag/{id}")
    public ModelAndView hashTag(
            @PathVariable("id") int tagId,
            @PageableDefault(size = POST_SIZE,
                    sort = {CREATED_AT}, direction = Sort.Direction.DESC) Pageable pageable) {
        _mav = new ModelAndView("home");
        _mav.addObject("tag", userService.findTag(tagId));
        _mav.addObject("posts", userService.findAllByTagId(tagId, pageable));
        return _mav;
    }

    /**
     * get list post by category
     *
     * @param response       use for aop
     * @param interestCookie use for aop
     * @param categoryId
     * @return {@link ModelAndView}
     * @author NhatPA
     */
    @GetMapping("/categories/{id}")
    public ModelAndView category(
            @PathVariable("id") int categoryId,
            @PageableDefault(size = POST_SIZE,
                    sort = {CREATED_AT}, direction = Sort.Direction.DESC) Pageable pageable) {
        _mav = new ModelAndView("home");
        _mav.addObject("category", userService.findCategory(categoryId));
        _mav.addObject("posts", userService.findAllByCategoryId(categoryId, pageable));
        return _mav;
    }
}
