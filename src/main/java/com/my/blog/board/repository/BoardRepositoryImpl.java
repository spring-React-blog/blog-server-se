package com.my.blog.board.repository;

import com.my.blog.board.domain.Board;
import com.my.blog.board.domain.vo.Status;
import com.my.blog.board.dto.BoardSchCondition;
import com.my.blog.board.dto.response.BoardResponse;
import com.querydsl.core.types.Order;
import com.querydsl.core.types.OrderSpecifier;
import com.querydsl.core.types.Projections;
import com.querydsl.core.types.dsl.BooleanExpression;
import com.querydsl.core.types.dsl.PathBuilder;
import com.querydsl.jpa.impl.JPAQuery;
import com.querydsl.jpa.impl.JPAQueryFactory;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.support.PageableExecutionUtils;
import org.springframework.util.StringUtils;

import java.util.List;

import static com.my.blog.board.domain.QBoard.board;
import static com.my.blog.category.entity.QCategory.category;
import static com.my.blog.count.entity.QBoardCount.boardCount;
import static com.my.blog.member.entity.QMember.member;

@RequiredArgsConstructor
public class BoardRepositoryImpl implements BoardSearchRepository{
    private final JPAQueryFactory queryFactory;

    @Override
    public Page<BoardResponse> search(BoardSchCondition condition, Pageable pageable) {
        List<BoardResponse> responses = getBoardList( condition,  pageable);
        JPAQuery<Long> countQuery = countQuery(condition);

        return PageableExecutionUtils.getPage(responses, pageable, countQuery::fetchOne);
    }

    public List<BoardResponse> getBoardList(final BoardSchCondition condition,final Pageable pageable){
        return queryFactory
                .select(Projections.constructor(
                        BoardResponse.class,
                        board.id,
                        board.title,
                        board.content,
                        board.category.name,
                        board.member.email,
                        board.boardCount.viewCount
                        //board.boardImages
                ))
                .from(board)
                .leftJoin(board.category, category)
             //   .leftJoin(board.boardImages, boardImage)
                .innerJoin(board.boardCount, boardCount)
                .innerJoin(board.member, member)
                .where(boardIdEq(condition.getBoardId()),
                        titleContains(condition.title()),
                        contentContains(condition.content()),
                        emailContains(condition.memberEmail()),
                        notDeleted()
                )
                .orderBy(orderCondition(pageable))
                .offset(pageable.getOffset())
                .limit(pageable.getPageSize())
                .fetch();
    }
    public JPAQuery<Long> countQuery(final BoardSchCondition condition){
        return queryFactory
                .select(board.count())
                .from(board)
                .leftJoin(board.category, category)
                .leftJoin(board.member, member)
                .where(
                        boardIdEq(condition.getBoardId()),
                        titleContains(condition.title()),
                        contentContains(condition.content()),
                        emailContains(condition.memberEmail()),
                        notDeleted(),
                        notOpened()
                );
    }


    private OrderSpecifier orderCondition( final Pageable pageable ){
        for (Sort.Order o : pageable.getSort()) {
            PathBuilder<? extends Board> pathBuilder = new PathBuilder<>(board.getType(), board.getMetadata());
            return new OrderSpecifier(o.isAscending() ? Order.ASC : Order.DESC,
                    pathBuilder.get(o.getProperty()));
        }
        return new OrderSpecifier(Order.DESC, board.createdDate);
    }

    private BooleanExpression notOpened() {
        return board.status.ne(Status.FALSE) ;
    }

    private BooleanExpression notDeleted(){
        return board.status.ne(Status.DELETED) ;
    }

    private BooleanExpression titleContains(final String title){
        return StringUtils.hasText(title) ? board.title.title.contains(title) : null;
    }
    private BooleanExpression contentContains(final String content){
        return StringUtils.hasText(content) ? board.content.content.contains(content) : null;
    }
    private BooleanExpression emailContains(final String email){
        return StringUtils.hasText(email) ? board.member.email.email.contains(email) : null;
    }

    private BooleanExpression boardIdEq(final Long id){
        return id != null ? board.id.eq(id) : null;
    }

}
