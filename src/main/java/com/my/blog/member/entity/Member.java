package com.my.blog.member.entity;

import com.my.blog.count.entity.MemberCount;
import com.my.blog.global.common.entity.BaseTimeEntity;
import com.my.blog.member.entity.vo.Name;
import com.my.blog.member.entity.vo.*;
import lombok.*;
import org.springframework.security.crypto.password.PasswordEncoder;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.Objects;

@Getter
@AllArgsConstructor(access=AccessLevel.PRIVATE)
@NoArgsConstructor(access=AccessLevel.PROTECTED)
@Table(name = "members")
@Entity
public class Member extends BaseTimeEntity {

    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name="member_id")
    private Long id;

    @Embedded
    private Email email;

    @Embedded
    private Password password;

    @Embedded
    private Name name;

    @Embedded
    private NickName nickname;

    @Embedded
    private Birth birth;

    @Column(name="roleType")
    @Enumerated(EnumType.STRING)
    private RoleType roleType;

    @Column(name="delete_time")
    private LocalDateTime deleteTime;

    @OneToOne(fetch = FetchType.LAZY,cascade = CascadeType.ALL)
    @JoinColumn(name="membercount_id")
    private MemberCount memberCount;

   /* @ManyToMany(fetch = FetchType.LAZY)
    @JoinTable(
            name = "member_follower",
            joinColumns = {@JoinColumn(name = "member_id")},
            inverseJoinColumns = {@JoinColumn(name = "follower_id")})
    private List<Follower> follower;*/

    @Builder
    public Member(
            Long id,
            Email email,
            Password password,
            Name name,
            NickName nickName,
            RoleType roleType
    ){
        this.id = id;
        this.email = email;
        this.password = password;
        this.name = name;
        this.nickname = nickName;
        this.roleType = roleType;
    }

    public Password encode(final Password password, final PasswordEncoder encoder){
        this.password = password.encode(encoder);
        return this.password ;
    }
    public String email() {
        return this.getEmail().getEmail();
    }

    public Member updateInfo(final Member member, final PasswordEncoder passwordEncoder) {
        if(Objects.nonNull(password)) {
            this.password = pwdChange(member.getPassword(),passwordEncoder);
        }

        this.nickname = nickNameChange(member.getNickname());
        this.name = nameChange(member.getName());

        return this;
    }

    public Name nameChange(final Name name) {
        return Objects.nonNull(name) ? name : this.name;
    }

    public NickName nickNameChange(final NickName nickname) {
        return Objects.nonNull(nickname) ? nickname : this.nickname;
    }

    public Password pwdChange(final Password password, final PasswordEncoder passwordEncoder ){
       return Objects.nonNull(password) ? encode(password,passwordEncoder)  : this.password;
    }

    public boolean emailEquals(final Email email){
        return this.email.equals(email);
    }

    public Member delete() {
        this.deleteTime = LocalDateTime.now();
        return this;
    }

    public boolean hasDeleted() {
        return Objects.nonNull(this.deleteTime) ? true : false;
    }
}
