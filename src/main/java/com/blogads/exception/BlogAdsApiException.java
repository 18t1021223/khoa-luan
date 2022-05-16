package com.blogads.exception;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

/**
 * @author NhatPA
 * @since 28/02/2022 - 23:09
 */
@Getter
@RequiredArgsConstructor
public class BlogAdsApiException extends RuntimeException {

    public static final String CATEGORY_NAME_EXISTS = "Thể loại đã tồn tại";
    public static final String CATEGORY_ID_NOT_EXISTS = "Thể loại chưa tồn tại";
    public static final String CATEGORY_NAME_MUST_NOT_BLANK = "Tên thể loại không được rỗng";
    public static final String CATEGORY_WAS_USED = "Thể loại đã được sử dụng";

    public static final String HASHTAG_NAME_EXISTS = "Nhãn đã tồn tại";
    public static final String HASHTAG_ID_NOT_EXISTS = "Nhãn chưa tồn tại";
    public static final String TAG_NAME_MUST_NOT_BLANK = "Tên nhãn không được rỗng";

    public static final String MAIL_NOT_FOUND = "Mail chưa đăng ký";

    private final String message;
}
