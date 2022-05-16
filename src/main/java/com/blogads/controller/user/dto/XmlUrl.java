package com.blogads.controller.user.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

/**
 * @author NhatPA
 * @since 19/03/2022 - 18:44
 */
@XmlAccessorType(value = XmlAccessType.NONE)
@XmlRootElement(name = "url")
@AllArgsConstructor
@NoArgsConstructor
public class XmlUrl {

    @Getter
    @AllArgsConstructor
    public enum PriorityType {
        HIGH("1.0"),
        MEDIUM("0.5");

        private final String value;
    }

    @Getter
    @AllArgsConstructor
    public enum changeFreqType {
        DAILY("daily"),
        WEEKLY("weekly");

        private final String value;
    }

    @XmlElement
    private String loc;

    @XmlElement(name = "lastmod")
    private String lastMod;

    @XmlElement(name = "changefreq")
    private String changeFreq;

    @XmlElement
    private String priority;
}