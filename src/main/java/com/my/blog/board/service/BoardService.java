package com.my.blog.board.service;

import com.my.blog.board.domain.Board;
import com.my.blog.board.repository.BoardRepository;
import com.my.blog.board.repository.BoardSearchRepository;
import com.my.blog.board.vo.BoardSchCondition;
import com.my.blog.board.vo.response.BoardResponse;
import com.my.blog.common.errorcode.BoardErrorCode;
import com.my.blog.common.errorcode.MemberErrorCode;
import com.my.blog.common.exception.CommonException;
import com.my.blog.count.service.BoardCountService;
import com.my.blog.member.entity.Member;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Objects;

@RequiredArgsConstructor
@Service
@Transactional(readOnly = true)
public class BoardService {
    private final BoardRepository boardRepository;
    private final BoardCountService boardCountService;

    @Transactional
    public Long save(Board board){
        Board savedBoard = boardRepository.save(board);
        return savedBoard.getId();
    }

    public BoardResponse getBoard(Long id){
        Board board = boardRepository.findById(id).orElseThrow(() -> new CommonException(BoardErrorCode.BOARD_NOT_FOUND));
        Long increasedViewCount = boardCountService.increaseViewCount(board.getBoardCount());
        return BoardResponse.toResponse(board);
    }
    
    public Page<BoardResponse> getBoards(BoardSchCondition condition, Pageable pageable){
        return boardRepository.search(condition,pageable);
    }

    public void updateBoard(final Board updateBoard, Long id, String memberId){
        Board board = manipulableBoard(id, memberId);
        board.update(updateBoard);
    }

    public void deleteBoard(Long id, String memberId){
        manipulableBoard(id, memberId);
        boardRepository.deleteById(id);
    }

    private Board manipulableBoard(Long id, String memberId) {
        Board board = boardRepository.findById(id).orElseThrow(() -> new CommonException(BoardErrorCode.BOARD_NOT_FOUND));
        if(!Objects.equals(board.getMember().getEmail().getEmail(), memberId)) throw new CommonException(BoardErrorCode.NOT_AUTHORIZED);
        return board;
    }


}