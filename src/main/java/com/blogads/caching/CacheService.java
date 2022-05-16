package com.blogads.caching;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.Cache;
import org.springframework.cache.CacheManager;
import org.springframework.stereotype.Service;

import java.util.Optional;

/**
 * @author NhatPA
 * @since 05/03/2022 - 05:53
 */
@Service
@Slf4j
public class CacheService {

    @Autowired
    private CacheManager cacheManager;

    public synchronized void clearCache(String... name) {
        cacheManager.getCacheNames().forEach(item -> {
            for (String s : name) {
                if (item.contains(s)) {
                    Optional.ofNullable(cacheManager.getCache(item)).ifPresent(Cache::clear);
                }
            }
        });
    }
}
