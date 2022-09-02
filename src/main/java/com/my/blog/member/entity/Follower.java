package com.my.blog.member.entity;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Table(name = "followers")
@Entity
public class Follower {

    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name="follower_id")
    private Long id;

    @Column(name="from_member_id")
    private Long fromMemberId;

    @Column(name="to_member_id")
    private Long toMemberId;




}
