package com.blogads.utils;

import com.blogads.configuration.security.AdminDetail;
import com.blogads.controller.admin.dto.PostRequestDto;
import com.blogads.entity.mysql.Post;
import com.blogads.vo.UserEnum;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.security.core.Authentication;

import static com.blogads.utils.Constants.MAX_SIZE;
import static com.blogads.utils.Constants.PAGE_DEFAULT;

/**
 * @author NhatPA
 * @since 24/02/2022 - 22:42
 */
@NoArgsConstructor(access = AccessLevel.PRIVATE)
public final class BlogAdsUtils {

    /**
     * valid page
     *
     * @param pageable
     * @return {@link Pageable}
     * @author NhatPA
     */
    public static Pageable validPageable(Pageable pageable) {
        return PageRequest.of(Math.max(pageable.getPageNumber() - 1, PAGE_DEFAULT),
                Math.min(pageable.getPageSize(), MAX_SIZE),
                pageable.getSort()
        );
    }

    /**
     * not use page
     *
     * @return {@link Pageable}
     * @author NhatPA
     */
    public static Pageable unPaged() {
        return PageRequest.of(0, Integer.MAX_VALUE);
    }

    /**
     * mapper
     *
     * @param dto {@link PostRequestDto}
     * @return {@link Post}
     * @author NhatPA
     */
    public static Post toEntity(PostRequestDto dto) {
        Post post = new Post();
        post.setContent(dto.getContent());
        post.setDescription(dto.getDescription());
        post.setPublished(dto.isPublished());
        post.setTitle(dto.getTitle());
        /*not set admin*/
        return post;
    }

    /**
     * update post from dto
     *
     * @param dto
     * @param post
     */
    public static void updateEntity(PostRequestDto dto, Post post) {
        post.setContent(dto.getContent());
        post.setDescription(dto.getDescription());
        post.setPublished(dto.isPublished());
        post.setTitle(dto.getTitle());
        /*not set admin*/
    }

    /**
     * get fullname by user login
     *
     * @param auth
     * @return {@link String}
     * @author NhatPA
     */
    @Deprecated
    public static String getFullName(Authentication auth) {
        if (auth.getName().equals(UserEnum.ADMIN_1.getUsername())) {
            return UserEnum.ADMIN_1.getFullName();
        } else if (auth.getName().equals(UserEnum.ADMIN_2.getUsername())) {
            return UserEnum.ADMIN_2.getFullName();
        }
        return null;
    }

    /**
     * get fullname by user login
     *
     * @return {@link String}
     * @author NhatPA
     */
    public static String getFullName() {
        return SecurityUtils.getCurrentAdminOrElseThrow().getFullName();
    }

    public static String getRole(AdminDetail admin) {
        return admin.getRoles().get(0).getAuthority().toUpperCase();
    }
}
