package com.my.blog.member.entity;

import com.my.blog.member.entity.vo.*;
import lombok.*;

import javax.persistence.*;
import java.sql.Timestamp;
import java.time.LocalDateTime;

@Entity
@Getter

@AllArgsConstructor(access=AccessLevel.PRIVATE)
@NoArgsConstructor(access=AccessLevel.PROTECTED)
@Table(name = "member")
public class Member {

    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name="member_id")
    private Long Id;

    @Column(name="email")
    @Embedded
    private Email email;

    @Column(name="password")
    @Embedded
    private Password password;

    @Column(name="name")
    private String name;

    @Column(name="nickname")
    @Embedded
    private NickName nickname;

    @Column(name="birth")
    private Birth birth;

    @Column(name="roleType")
    @Enumerated(EnumType.STRING)
    private RoleType roleType;

    @Column(name="delete_time")
    private LocalDateTime deleteTime;



    @Builder
    public Member(
            Email email,
            Password password,
            String name,
            NickName nickName,
            RoleType roleType
    ){
        this.email = email;
        this.password = password;
        this.name = name;
        this.nickname = nickName;
        this.roleType = roleType;
    }
    public String email() {
        return this.getEmail().getEmail();
    }

    public Member updateInfo(Member member) {
        this.password = member.getPassword();
        this.nickname = member.getNickname();
        this.name = member.getName();
        return this;
    }
}
