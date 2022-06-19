package com.my.blog.board.repository;

import com.my.blog.board.domain.Board;
import com.my.blog.board.dto.BoardSchCondition;
import com.my.blog.board.dto.response.BoardResponse;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;


public interface BoardSearchRepository {

    Page<BoardResponse> search(BoardSchCondition condition, Pageable pageable);

}
