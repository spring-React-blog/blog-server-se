package com.my.blog.count.service;

import com.my.blog.board.error.BoardErrorCode;
import com.my.blog.common.exception.CommonException;
import com.my.blog.count.entity.BoardCount;
import com.my.blog.count.entity.BoardCountRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@Transactional
public class BoardCountService {

    private final BoardCountRepository boardCountRepository;

    @Transactional
    public BoardCount increaseViewCount(Long id){

        BoardCount getBoarCount = boardCountRepository.findById(id)
                .orElseThrow(() -> new CommonException(BoardErrorCode.BOARD_NOT_FOUND));

        BoardCount boardCount = getBoarCount.updateViewCount(getBoarCount.getViewCount() + 1);

        BoardCount save = boardCountRepository.save(boardCount);
        System.out.println("count service "+save.getViewCount());
        return save;


    }
}
