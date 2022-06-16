package com.my.blog.member.entity;

import com.my.blog.member.entity.vo.Name;
import com.my.blog.member.entity.vo.*;
import lombok.*;
import org.springframework.security.crypto.password.PasswordEncoder;

import javax.persistence.*;
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
    @Embedded
    private Name name;

    @Column(name="nickname")
    @Embedded
    private NickName nickname;

    @Column(name="birth")
    @Embedded
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
            Name name,
            NickName nickName,
            RoleType roleType
    ){
        this.email = email;
        this.password = password;
        this.name = name;
        this.nickname = nickName;
        this.roleType = roleType;
    }

    public Member encode(PasswordEncoder encoder){
        this.password = this.password.encode(encoder);
        return this;
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

    public boolean emailEquals(String email){
        return this.email.equals(email);
    }
}
