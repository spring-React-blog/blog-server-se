package com.my.blog.member.repository;

import com.my.blog.member.dto.MemberResponse;
import com.my.blog.member.dto.MemberSchCondition;
import com.my.blog.member.entity.Member;
import com.my.blog.member.entity.vo.Name;
import com.my.blog.member.entity.vo.NickName;
import com.my.blog.member.entity.vo.RoleType;
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

import java.util.List;
import java.util.Objects;

import static com.my.blog.member.entity.QMember.member;

@RequiredArgsConstructor
public class MemberRepositoryImpl implements MemberSearchRepository {

    private final JPAQueryFactory queryFactory;

    @Override
    public Page<MemberResponse> search(MemberSchCondition condition, Pageable pageable) {
        List<MemberResponse> memberList = getMemberList(condition, pageable);
        JPAQuery<Long> countQuery = countQuery(condition);
        return PageableExecutionUtils.getPage(memberList, pageable, countQuery::fetchOne);
    }

    public List<MemberResponse> getMemberList(final MemberSchCondition condition, final Pageable pageable){
        return queryFactory
                .select(Projections.constructor(MemberResponse.class,
                        member.id,
                        member.email,
                        member.name,
                        member.nickname
                        ))
                .from(member)
                //.leftJoin(MemberCount)
                .where(
                        idEq(condition.getId()),
                        nameEq(condition.getName()),
                        nickNameEq(condition.getNickName()),
                        roleTypeIs(condition.getRoleType()),
                        member.deleteTime.isNotNull()
                        )
                .orderBy(orderCondition(pageable))
                .offset(pageable.getOffset())
                .limit(pageable.getPageSize())
                .fetch();
    }

    public JPAQuery<Long> countQuery(final MemberSchCondition condition){
        return queryFactory
                .select(member.count())
                .from(member)
                .where(
                        idEq(condition.getId()),
                        nameEq(condition.getName()),
                        nickNameEq(condition.getNickName()),
                        roleTypeIs(condition.getRoleType()),
                        member.deleteTime.isNotNull()
                );
    }

    private OrderSpecifier<?> orderCondition(final Pageable pageable) {
        for(Sort.Order o : pageable.getSort()){
            PathBuilder<? extends Member> pathBuilder = new PathBuilder<>(member.getType(), member.getMetadata());
            return new OrderSpecifier(o.isAscending() ? Order.ASC : Order.DESC,
                    pathBuilder.get(o.getProperty()));
        }
        return new OrderSpecifier(Order.DESC, member.name);
    }

    private BooleanExpression roleTypeIs(final RoleType roleType) {
        return Objects.nonNull(roleType) ? member.roleType.eq(roleType) : null;
    }

    private BooleanExpression nickNameEq(final NickName nickName) {
        return Objects.nonNull(nickName) ? member.nickname.eq(nickName) : null;
    }

    private BooleanExpression nameEq(final Name name) {
        return Objects.nonNull(name) ? member.name.eq(name) : null;
    }

    private BooleanExpression idEq(final Long id) {
        return id!=null ? member.id.eq(id) : null;
    }

}
