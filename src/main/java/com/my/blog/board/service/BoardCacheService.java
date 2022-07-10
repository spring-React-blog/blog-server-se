package com.my.blog.board.service;

import lombok.extern.slf4j.Slf4j;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.CachePut;
import org.springframework.cache.annotation.Caching;
import org.springframework.stereotype.Service;

@Slf4j
@Service
public class BoardCacheService {
    private static final String CACHE_BOARD = "board";
    private static final String CACHE_LIST = "boards";

    @Caching(put = {
            @CachePut(key= CACHE_BOARD, value="#id"),
            @CachePut(key= CACHE_LIST, value="#id"),
    })
    public void updateCache(final Long id){

    }

    @Caching(evict = {
            @CacheEvict(key= CACHE_BOARD, value="#id"),
            @CacheEvict(key= CACHE_LIST, value="#id"),
    })
    public void deleteCache(final Long id){

    }
}
