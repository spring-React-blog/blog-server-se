package com.my.blog.board.repository;

import com.my.blog.board.domain.Board;
import com.my.blog.board.vo.BoardSchCondition;
import com.my.blog.board.vo.response.BoardResponse;
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

    public Page<BoardResponse> search2(BoardSchCondition condition, Pageable pageable) {

        List<BoardResponse> responses = getBoardList( condition,  pageable);
        JPAQuery<Long> countQuery = countQuery(condition);

        return PageableExecutionUtils.getPage(responses, pageable, countQuery::fetchOne);
    }
    @Override
    public Page<BoardResponse> search(BoardSchCondition condition, Pageable pageable) {
        List<BoardResponse> responses = queryFactory
                .select(Projections.constructor(
                        BoardResponse.class,
                        board.id,
                        board.title,
                        board.content,
                        board.category,
                        board.member,
                        board.boardCount
                ))
                .from(board)
                .leftJoin(board.category, category)
                .leftJoin(board.boardCount, boardCount)
                .leftJoin(board.member, member)
                .where(boardIdEq(condition.getBoardId()),
                        titleContains(condition.title()),
                        contentContains(condition.content()),
                        emailContains(condition.memberEmail()),
                        categoryIs(condition.categoryName())
                )
                .orderBy(orderCondition(pageable))
                .offset(pageable.getOffset())
                .limit(pageable.getPageSize())
                .fetch();

        JPAQuery<Long> countQuery = queryFactory
                .select(board.count())
                .from(board)
                .leftJoin(board.category, category)
                .leftJoin(board.member, member)
                .where(
                        boardIdEq(condition.getBoardId()),
                        titleContains(condition.title()),
                        contentContains(condition.content()),
                        emailContains(condition.memberEmail()),
                        categoryIs(condition.categoryName())
                );

        return PageableExecutionUtils.getPage(responses, pageable, countQuery::fetchOne);
    }

    public List<BoardResponse> getBoardList(BoardSchCondition condition, Pageable pageable){
       return queryFactory
               .select(Projections.constructor(
                       BoardResponse.class,
                       board.id,
                       board.title,
                       board.content,
                       board.category,
                       board.member,
                       board.boardCount
               ))
                .from(board)
                .leftJoin(board.category, category)
                .leftJoin(board.boardCount, boardCount)
                .leftJoin(board.member, member)
                .where(boardIdEq(condition.getBoardId()),
                        titleContains(condition.title()),
                        contentContains(condition.content()),
                        emailContains(condition.memberEmail()),
                        categoryIs(condition.categoryName())
                )
                .orderBy(orderCondition(pageable))
                .offset(pageable.getOffset())
                .limit(pageable.getPageSize())
                .fetch();
    }
    public JPAQuery<Long> countQuery(BoardSchCondition condition){
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
                        categoryIs(condition.categoryName())
                );
    }

    private OrderSpecifier orderCondition( Pageable pageable ){
        for (Sort.Order o : pageable.getSort()) {
            PathBuilder<? extends Board> pathBuilder = new PathBuilder<>(board.getType(), board.getMetadata());
            return new OrderSpecifier(o.isAscending() ? Order.ASC : Order.DESC,
                    pathBuilder.get(o.getProperty()));
        }
        return new OrderSpecifier(Order.DESC, board.createdDate);
    }

    private BooleanExpression titleContains(String title){
        return StringUtils.hasText(title) ? board.title.title.contains(title) : null;
    }
    private BooleanExpression contentContains(String content){
        return StringUtils.hasText(content) ? board.content.content.contains(content) : null;
    }
    private BooleanExpression emailContains(String email){
        return StringUtils.hasText(email) ? board.member.email.email.contains(email) : null;
    }
    private BooleanExpression categoryIs(String category){
        return StringUtils.hasText(category) ? board.category.name.eq(category) : null;
    }
    private BooleanExpression boardIdEq(Long id){
        return id != null ? board.id.eq(id) : null;
    }

}
