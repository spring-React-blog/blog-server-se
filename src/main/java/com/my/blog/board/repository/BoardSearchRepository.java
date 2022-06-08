package com.my.blog.board.repository;

import com.my.blog.board.domain.Board;
import com.my.blog.board.domain.QBoard;
import com.my.blog.board.vo.response.BoardResponse;
import com.querydsl.core.types.Projections;
import com.querydsl.jpa.impl.JPAQueryFactory;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;

import static com.my.blog.board.domain.QBoard.board;

@Repository
@RequiredArgsConstructor
public class BoardSearchRepository {
    private final JPAQueryFactory queryFactory;

    public BoardResponse findById(Long boardId){
        return queryFactory
                .select(Projections.constructor(BoardResponse.class,
                        board.id,
                        board.title,
                        board.content,
                        board.category.name,
                        board.member.Id,
                        board.rgstDateTime,
                        board.boardCount.viewCount
                        ))
                .from(board)
                .leftJoin(board.category)
                .leftJoin(board.member)
                .leftJoin(board.boardCount)
                .where(board.id.eq(boardId))
                .fetchOne();
    }


    public Board findById2(Long boardId){
        Board board = queryFactory
                .selectFrom(QBoard.board)
                .leftJoin(QBoard.board.category)
                .leftJoin(QBoard.board.member)
                .leftJoin(QBoard.board.boardCount)
                .where(QBoard.board.id.eq(boardId))
                .fetchOne();
        return board ; //board.toResponse();
    }

    public BoardResponse findById3(Long boardId){
        Board board = queryFactory
                .selectFrom(QBoard.board)
                .leftJoin(QBoard.board.category)
                .leftJoin(QBoard.board.member)
                .leftJoin(QBoard.board.boardCount)
                .where(QBoard.board.id.eq(boardId))
                .fetchOne();
        return BoardResponse.toResponse(board);
    }

    public BoardResponse findById4(Long boardId){
        Board board = queryFactory
                .selectFrom(QBoard.board)
                .leftJoin(QBoard.board.category)
                .leftJoin(QBoard.board.member)
                .leftJoin(QBoard.board.boardCount)
                .where(QBoard.board.id.eq(boardId))
                .fetchOne();
        return board.toResponse();
    }
}
