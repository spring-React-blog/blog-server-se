package com.my.blog.count.service;

import com.my.blog.count.entity.BoardCount;
import com.my.blog.count.entity.BoardCountRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor

public class BoardCountService {

    private final BoardCountRepository boardCountRepository;

    public Long increaseViewCount(BoardCount boardCount){
        return boardCount.increateViewCount();
    }
}
