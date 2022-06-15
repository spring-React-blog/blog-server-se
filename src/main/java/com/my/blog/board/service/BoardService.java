package com.my.blog.board.service;

import com.my.blog.board.domain.Board;
import com.my.blog.board.repository.BoardRepository;
import com.my.blog.board.dto.BoardSchCondition;
import com.my.blog.board.dto.response.BoardResponse;
import com.my.blog.board.error.BoardErrorCode;
import com.my.blog.common.exception.CommonException;
import com.my.blog.count.service.BoardCountService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Objects;

@RequiredArgsConstructor
@Service
@Transactional
public class BoardService {
    private final BoardRepository boardRepository;
    private final BoardCountService boardCountService;

    public Long save(final Board board){
        Board savedBoard = boardRepository.save(board);
        return savedBoard.getId();
    }

    public BoardResponse getBoard(final Long id){
        Board board = boardRepository.findById(id).orElseThrow(() -> new CommonException(BoardErrorCode.BOARD_NOT_FOUND));
        boardCountService.increaseViewCount(board.getBoardCount().getId());
        return BoardResponse.toResponse(board);
    }
    @Transactional(readOnly = true)
    public Page<BoardResponse> getBoards(final BoardSchCondition condition, final Pageable pageable){
        return boardRepository.search(condition,pageable);
    }

    public void updateBoard(final Board updateBoard, final Long id, final String memberId){
        Board board = manipulableBoard(id, memberId);
        board.update(updateBoard);
    }

    public void deleteBoard(final Long id, final String memberId){
        manipulableBoard(id, memberId);
        boardRepository.deleteById(id);
    }

    private Board manipulableBoard(final Long id, final String memberEmail) {
        Board board = boardRepository.findById(id).orElseThrow(() -> new CommonException(BoardErrorCode.BOARD_NOT_FOUND));
        if(!board.emailEquals(memberEmail)) throw new CommonException(BoardErrorCode.NOT_AUTHORIZED);
        return board;
    }


}