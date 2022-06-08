package com.my.blog.member.entity;

import com.my.blog.member.vo.Email;
import com.my.blog.member.vo.Password;
import com.my.blog.member.vo.RoleType;
import lombok.*;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import java.sql.Timestamp;

@Entity
@Getter
@Builder
@AllArgsConstructor(access=AccessLevel.PRIVATE)
@NoArgsConstructor(access=AccessLevel.PROTECTED)
public class Member {

    @Id @GeneratedValue
    @Column(name="member_id")
    private Long Id;

    @Column(name="email")
    private Email email;

    @Column(name="password")
    private Password password;

    @Column(name="name")
    private String name;

    @Column(name="nickname")
    private String nickname;

    @Column(name="birth")
    private Timestamp birth;

    @Column(name="roleType")
    private RoleType roleType;

    @Column(name="delete_time")
    private Timestamp deleteTime;

}
