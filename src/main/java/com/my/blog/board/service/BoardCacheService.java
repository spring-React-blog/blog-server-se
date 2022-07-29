package com.my.blog.board.service;

import com.my.blog.board.domain.Board;
import com.my.blog.board.dto.response.BoardResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.CachePut;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.cache.annotation.Caching;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Service;

@Slf4j
//@Service
public class BoardCacheService {
    private static final String CACHE_BOARD = "board";
    private static final String CACHE_LIST = "boards";

    @Caching(put = {
            @CachePut(key= CACHE_BOARD, value="#boardId"),
            @CachePut(key= CACHE_LIST, value="#boardId"),
    })
    public void updateCache(final Long id){

    }

    @Caching(evict = {
            @CacheEvict(key= CACHE_BOARD, value="#boardId"),
            @CacheEvict(key= CACHE_LIST, value="#boardId"),
    })
    public void deleteCache(final Long id){

    }

    @Cacheable(key= CACHE_BOARD, value = "#boardId", unless = "#result==null")
    public void getList(final Page<BoardResponse> boardResponses ){

    }

    @CacheEvict(key=CACHE_LIST)
    public void resetListCache(Board savedBoard ) {
    }

    @Cacheable(key=CACHE_BOARD, value = "#id" ) //unless = \"#result==null"
    public void cachingBoard(Board board) {
        log.info("caching board id={}", board.getId());
    }
}
