package com.my.blog.count.entity;

import com.my.blog.member.entity.Member;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Table(name="MemberCount")
@Entity
public class MemberCount {

    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name="membercount_id")
    private Long id;

    @Column(name="follower_count")
    private int followerCount;

    @Column(name="following_count")
    private int followingCount;

    @Column(name="board_count")
    private int boardCount;

    @OneToOne(mappedBy = "memberCount")
    private Member member;
}
