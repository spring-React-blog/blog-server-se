package com.my.blog.board.repository;

import com.my.blog.board.vo.BoardSchCondition;
import com.my.blog.board.vo.response.BoardResponse;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;


public interface BoardSearchRepository {

    Page<BoardResponse> search(BoardSchCondition condition, Pageable pageable);

}
