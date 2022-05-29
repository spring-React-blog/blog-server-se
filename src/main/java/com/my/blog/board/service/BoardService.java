package com.my.blog.board.service;

import com.my.blog.board.domain.Board;
import com.my.blog.board.repository.BoardRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@RequiredArgsConstructor
@Service
@Transactional
public class BoardService {
    private final BoardRepository boardRepository;

    public Long save(Board board){
        Board savedBoard = boardRepository.save(board);
        return savedBoard.getId();
    }
}
