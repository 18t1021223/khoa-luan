package com.blogads.controller.user.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.xml.bind.annotation.*;
import java.util.ArrayList;
import java.util.Collection;

/**
 * @author NhatPA
 * @since 19/03/2022 - 18:42
 */
@XmlAccessorType(value = XmlAccessType.NONE)
@XmlRootElement(name = "urlset")
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class XmlUrlSet {

    @XmlElements({@XmlElement(name = "url", type = XmlUrl.class)})
    private Collection<XmlUrl> xmlUrls = new ArrayList<>();
}
