package com.my.blog.board.service;

import com.my.blog.board.domain.Board;
import com.my.blog.board.repository.BoardRepository;
import com.my.blog.board.dto.BoardSchCondition;
import com.my.blog.board.dto.response.BoardResponse;
import com.my.blog.board.error.BoardErrorCode;
import com.my.blog.count.CacheKey;
import com.my.blog.global.common.exception.CommonException;
import com.my.blog.count.service.BoardCountService;
import lombok.RequiredArgsConstructor;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.CachePut;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@RequiredArgsConstructor
@Transactional
@Service
public class BoardService {
    private final BoardRepository boardRepository;
    private final BoardCountService boardCountService;
    private final BoardCacheService boardCacheService;

    private static final String CACHE_BOARD = "board";
    private static final String CACHE_LIST = "boards";

    @CacheEvict(key=CACHE_LIST, value = "#id")
    public Long save(final Board board){
        Board savedBoard = boardRepository.save(board);
        return savedBoard.getId();
    }

    @Cacheable(key=CACHE_BOARD, value = "#id", unless = "#result==null")
    public Board getBoard(final Long id){
        Board board = boardRepository.findById(id).orElseThrow(() -> new CommonException(BoardErrorCode.BOARD_NOT_FOUND));
        boardCountService.increaseViewCount(board.getBoardCount().getId());
        return board;
    }

    public void updateBoard(final Board updateBoard, final Long id, final String memberId){
        Board board = manipulableBoard(id, memberId);
        board.update(updateBoard);
        boardCacheService.updateCache(id);
    }

    public void deleteBoard(final Long id, final String memberId){
        manipulableBoard(id, memberId);
        boardRepository.deleteById(id);
        boardCacheService.deleteCache(id);
    }

    private Board manipulableBoard(final Long id, final String memberEmail) {
        Board board = boardRepository.findById(id).orElseThrow(() -> new CommonException(BoardErrorCode.BOARD_NOT_FOUND));
        if(!board.emailEquals(memberEmail)) throw new CommonException(BoardErrorCode.NOT_AUTHORIZED);
        return board;
    }


}