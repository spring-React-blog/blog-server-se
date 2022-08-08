package com.my.blog.board.service;

import com.my.blog.board.domain.Board;
import com.my.blog.board.error.BoardErrorCode;
import com.my.blog.board.repository.BoardRepository;
import com.my.blog.count.service.BoardCountService;
import com.my.blog.global.common.exception.CommonException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@RequiredArgsConstructor
@Transactional
@Service
public class BoardService {
    private final BoardRepository boardRepository;
    private final BoardCountService boardCountService;
   // private final BoardCacheService boardCacheService; 추후 redis 적용

    public Board save(final Board board){
        Board savedBoard = boardRepository.save(board);
        //    boardCacheService.resetListCache(savedBoard );
        return savedBoard;
    }

    public Board getBoard(final Long id){
        Board board = boardRepository.findById(id).orElseThrow(() -> new CommonException(BoardErrorCode.BOARD_NOT_FOUND));
        boardCountService.increaseViewCount(board.getBoardCount().getId());
   //     boardCacheService.cachingBoard(board);
        return board;
    }

    public Board updateBoard(final Board updateBoard, final String memberEmail){
        Board board = manipulableBoard(updateBoard.getId(), memberEmail);
        Board updated = board.update(updateBoard);
        return updated;
     //   boardCacheService.updateCache(id);
    }

    public void deleteBoard(final Long id, final String memberId){
        Board board = manipulableBoard(id, memberId);
        board.deleteBoard();
        //    boardCacheService.deleteCache(id);
    }

    private Board manipulableBoard(final Long id, final String memberEmail) {
        Board board = boardRepository.findById(id).orElseThrow(() -> new CommonException(BoardErrorCode.BOARD_NOT_FOUND));
        if(!board.emailEquals(memberEmail)) throw new CommonException(BoardErrorCode.NOT_AUTHORIZED);
        return board;
    }


}