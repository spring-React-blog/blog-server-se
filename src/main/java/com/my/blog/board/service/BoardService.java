package com.my.blog.board.service;

import com.my.blog.board.domain.Board;
import com.my.blog.board.repository.BoardRepository;
import com.my.blog.board.repository.BoardSearchRepository;
import com.my.blog.board.vo.BoardSchCondition;
import com.my.blog.board.vo.response.BoardResponse;
import com.my.blog.common.errorcode.BoardErrorCode;
import com.my.blog.common.errorcode.MemberErrorCode;
import com.my.blog.common.exception.CommonException;
import com.my.blog.member.entity.Member;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@RequiredArgsConstructor
@Service
@Transactional(readOnly = true)
public class BoardService {
    private final BoardRepository boardRepository;
 //   private final BoardSearchRepository boardSearchRepository;

    @Transactional
    public Long save(Board board){
        Board savedBoard = boardRepository.save(board);
        return savedBoard.getId();
    }

    public BoardResponse getBoard(Long id){
        return boardRepository.findById(id).
                map(BoardResponse::toResponse)
                .orElseThrow(()->new CommonException(BoardErrorCode.BOARD_NOT_FOUND));
    }
    public Page<BoardResponse> getBoards(BoardSchCondition condition, Pageable pageable){
        return boardRepository.search(condition,pageable);
    }




}