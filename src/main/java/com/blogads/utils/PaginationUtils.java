package com.blogads.utils;

import com.google.common.base.CaseFormat;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.RequiredArgsConstructor;
import org.apache.commons.lang3.StringUtils;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Sort;
import org.springframework.data.domain.Sort.Direction;

import java.util.ArrayList;
import java.util.List;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public final class PaginationUtils {

    private static final int DEFAULT_RECORD_PER_PAGE = 5;
    private static final int DEFAULT_CURRENT_PAGE = 0;
    private static final int MAX_LIMIT_SIZE = 100;

    public static final String DEFAULT_SORT_PROPERTY = "id";
    public static final String ASC = "asc";
    public static final String SPLIT = "-";

    public static <T> PageRes<T> buildPageRes(final Page<T> page) {
        PageRes<T> res = new PageRes<>();
        res.setContent(page.getContent());
        res.setNumber(page.getNumber());
        res.setSize(page.getSize());
        res.setTotalElements(page.getTotalElements());
        res.setTotalPages(page.getTotalPages());
        return res;
    }

    public static <T> PageRes<T> buildPageResWithoutPagination(final List<T> records) {
        PageRes<T> res = new PageRes<>();
        res.setContent(records);
        return res;
    }

    private static <T> Integer getPrev(final Page<T> page) {
        return page.hasPrevious() ? page.getNumber() - 1 : null;
    }

    private static <T> Integer getNext(final Page<T> page) {
        return page.hasNext() ? page.getNumber() + 1 : null;
    }

    private static Integer getPage(final String currentPage) {
        if (StringUtils.isBlank(currentPage)) {
            return DEFAULT_CURRENT_PAGE;
        }

        int page;
        try {
            page = Integer.parseInt(currentPage);
            page = page > 0 ? page - 1 : DEFAULT_CURRENT_PAGE;
        } catch (NumberFormatException e) {
            page = DEFAULT_CURRENT_PAGE;
        }

        return page;
    }

    private static int getPage(Integer page) {
        if (page == null) {
            return DEFAULT_CURRENT_PAGE;
        }
        return page > 0 ? page - 1 : DEFAULT_CURRENT_PAGE;
    }

    private static int getLimit(Integer limit) {
        if (limit == null || limit <= 0) {
            return DEFAULT_RECORD_PER_PAGE;
        }
        return limit <= MAX_LIMIT_SIZE ? limit : MAX_LIMIT_SIZE;
    }

    private static Sort getSort(String[] sort, FormatType formatType) {
        if (sort == null || sort.length == 0) {
            sort = new String[]{DEFAULT_SORT_PROPERTY};
        }
        List<Sort.Order> list = new ArrayList<>(sort.length);
        String[] e;
        for (String item : sort) {
            e = item.split(SPLIT);
            if (e.length == 1) {
                list.add(new Sort.Order(Direction.ASC, toFormat(formatType, e[0])));
            } else if (e.length >= 2) {
                list.add(new Sort.Order(toDirection(e[1]), toFormat(formatType, e[0])));
            }
        }
        return Sort.by(list);
    }

    private static Direction toDirection(String v) {
        return ASC.equalsIgnoreCase(v) ? Direction.ASC : Direction.DESC;
    }

    private static String toFormat(FormatType formatType, String v) {
        return formatType.getCaseFormat().to(formatType.getTo(), v);
    }

    @Getter
    @RequiredArgsConstructor
    public enum FormatType {
        _1(CaseFormat.LOWER_UNDERSCORE, CaseFormat.LOWER_CAMEL),
        _2(CaseFormat.LOWER_CAMEL, CaseFormat.LOWER_UNDERSCORE);
        private final CaseFormat caseFormat;
        private final CaseFormat to;
    }
}
