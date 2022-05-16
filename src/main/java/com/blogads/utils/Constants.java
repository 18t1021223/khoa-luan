package com.blogads.utils;

import lombok.AccessLevel;
import lombok.NoArgsConstructor;

/**
 * @author NhatPA
 * @since 25/02/2022 - 03:03
 */
@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class Constants {

    public static final int CATEGORY_DEFAULT = 5;
    public static final int TAG_DEFAULT = 50;
    public static final int RECENT_POSTS_DEFAULT = 5;
    public static final int RELATED_POSTS_DEFAULT = 5;
    public static final int HOST_POSTS_DEFAULT = 3;

    // FIELD SORT - USER
    public static final String CREATED_AT = "createdAt";
    public static final String VIEW_NUMBER = "viewNumber";
    public static final int PAGE_DEFAULT = 0;
    public static final int MAX_SIZE = 50;
    public static final int POST_SIZE = 8;

    // FIELD SORT - ADMIN
    public static final int POST_SIZE_ADMIN = 10;
    public static final int CATEGORY_SIZE_ADMIN = 10;
    public static final int HASHTAG_SIZE_ADMIN = 10;

    //COOKIE
    public static final String INTEREST_COOKIE = "interest";

    //IMAGE DEFAULT
    public static final String IMAGE_DEFAULT = "https://foresttime.blob.core.windows.net/foresttime/456476546e0e0a9c7049861783d755ea9bc475b5.jpg";
    public static final String IMAGE_IN_CONTENT_PREFIX = "_";
    public static final String IMAGE_POST_CONTENT_PREFIX = "avt#";
    public static final String BASE64_PREFIX = "data:%s;base64,%s";

    //CACHE
    public static final String POST_CACHE_NAME = "post";
    public static final String POST_CATEGORY_CACHE_NAME = "post_category";
    public static final String POST_TAG_CACHE_NAME = "post_tag";
    //CACHE ADMIN
    public static final String ADMIN_POST_TAG_CACHE_NAME = "admin_post_tag";
    public static final String ADMIN_POST_CATEGORY_CACHE_NAME = "admin_post_category";
    public static final String ADMIN_POST_CACHE_NAME = "admin_post";
    public static final String ADMIN_STATISTIC_CACHE_NAME = "admin_statistic";
}
