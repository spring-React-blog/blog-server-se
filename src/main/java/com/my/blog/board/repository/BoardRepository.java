package com.my.blog.board.repository;

import com.my.blog.board.domain.Board;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.transaction.annotation.Transactional;

public interface BoardRepository extends JpaRepository<Board,Long> , BoardSearchRepository{

}
