package com.blogads.vo;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

/**
 * @author NhatPA
 * @since 03/03/2022 - 21:15
 */
@RequiredArgsConstructor
@Getter
public enum ImgType {
    HTTP_OR_HTTPS("http"),
    BASE64("base64,");

    final String type;
}
