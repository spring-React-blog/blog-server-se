package com.my.blog.board.service;

import com.my.blog.board.domain.Board;
import com.my.blog.board.repository.BoardRepository;
import com.my.blog.board.repository.BoardSearchRepository;
import com.my.blog.board.vo.response.BoardResponse;
import com.my.blog.common.errorcode.MemberErrorCode;
import com.my.blog.common.exception.CommonException;
import com.my.blog.member.entity.Member;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@RequiredArgsConstructor
@Service
@Transactional(readOnly = true)
public class BoardService {
    private final BoardRepository boardRepository;
    private final BoardSearchRepository boardSearchRepository;

    @Transactional
    public Long save(Board board){
        Board savedBoard = boardRepository.save(board);
        return savedBoard.getId();
    }

    public BoardResponse findByBoardId(Long id){
        return boardSearchRepository.findById(id);
    }

    public BoardResponse findByBoardId2(Long id){
        Board board = boardSearchRepository.findById2(id);
        BoardResponse response = BoardResponse.toResponse(board);
        return response;
    }
    public BoardResponse findByBoardId3(Long id){
        BoardResponse response = boardSearchRepository.findById3(id);
        return response;
    }

    public BoardResponse findByBoardId4(Long id){
        BoardResponse response = boardSearchRepository.findById4(id);
        return response;
    }

}
