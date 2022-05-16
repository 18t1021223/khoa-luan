package com.blogads.controller.admin.model;

import lombok.Builder;
import lombok.Data;

import java.util.Map;

/**
 * @author NhatPA
 * @since 20/04/2022 - 15:59
 */
@Data
@Builder
public class MailInfo {
    private String emailSubject;
    private String emailFrom;
    private String emailTo;
    private String content;
    private Map<String, String> attachImages;
}
