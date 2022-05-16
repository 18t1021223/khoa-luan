package com.blogads.exception;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

/**
 * @author NhatPA
 * @since 25/02/2022 - 02:48
 */
@Getter
@RequiredArgsConstructor
public final class BlogAdsException extends RuntimeException {

    public static final String USERNAME_PASSWORD_INCORRECT = "Username password incorrect";
    public static final String PASSWORD_INCORRECT = "Password incorrect";
    public static final String CONFIRM_PASSWORD_NOT_MATCH = "Confirm password not match";
    public static final String EMAIL_ALREADY_EXISTS = "Email already exists";
    public static final String ADMIN_NOT_FOUND = "Admin not found";
    public static final String ACCESS_DENIED = "Access denied";
    public static final String POST_NOT_FOUND = "Post not found";
    public static final String TAG_NOT_FOUND = "Tag not found";
    public static final String CATEGORY_NOT_FOUND = "Category not found";
    public static final String LINK_IMAGE_INVALID = "Link image invalid";
    public static final String UNKNOWN_ERROR_MESSAGE = "Có lỗi xảy ra, vui lòng thử lại!";

    private final int status;

    private final String message;
}
