package com.my.blog.board.service;

import com.my.blog.board.dto.BoardSchCondition;
import com.my.blog.board.dto.response.BoardResponse;
import com.my.blog.board.repository.BoardRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@RequiredArgsConstructor
@Transactional(readOnly = true)
@Service
public class BoardSearchService {
    private final BoardRepository boardRepository;

    public Page<BoardResponse> getBoards(final BoardSchCondition condition, final Pageable pageable){
        return boardRepository.search(condition,pageable);
    }
}