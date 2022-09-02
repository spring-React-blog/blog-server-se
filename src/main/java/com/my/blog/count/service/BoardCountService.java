package com.my.blog.count.service;

import com.my.blog.board.error.BoardErrorCode;
import com.my.blog.count.CacheKey;
import com.my.blog.count.error.BoardCountErrorCode;
import com.my.blog.global.common.exception.CommonException;
import com.my.blog.count.entity.BoardCount;
import com.my.blog.count.entity.BoardCountRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.data.redis.cache.CacheKeyPrefix;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Service
@RequiredArgsConstructor
@Transactional
public class BoardCountService {

    private final BoardCountRepository boardCountRepository;

    public BoardCount increaseViewCount(Long id){
        BoardCount getBoarCount = boardCountRepository.findById(id)
                .orElseThrow(() -> new CommonException(BoardErrorCode.BOARD_NOT_FOUND));

        BoardCount boardCount = getBoarCount.updateViewCount(getBoarCount.getViewCount() + 1);

        BoardCount save = boardCountRepository.save(boardCount);
        return save;
    }

    @Cacheable(value= CacheKey.BOARD_COUNT, key="#id")
    public BoardCount getCount(Long id){
        return boardCountRepository.findById(id).orElseThrow(() -> new CommonException(BoardCountErrorCode.NOT_FOUND));
    }

}
