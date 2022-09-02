package com.my.blog.board.domain;

import com.my.blog.board.domain.vo.Content;
import com.my.blog.board.domain.vo.Status;
import com.my.blog.board.domain.vo.Title;
import com.my.blog.category.entity.Category;
import com.my.blog.count.entity.BoardCount;
import com.my.blog.global.common.entity.BaseTimeEntity;
import com.my.blog.member.entity.Member;
import com.my.blog.member.entity.vo.Email;
import com.my.blog.reply.domain.Reply;
import lombok.*;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Getter
@Builder
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor(access = AccessLevel.PRIVATE)
@Table(name="board")
@Entity
public class Board extends BaseTimeEntity {

    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name="board_id")
    private Long id;

    @Embedded
    private Title title;

    @Embedded
    private Content content;

    @Column(name="open_status")
    @Enumerated(EnumType.STRING)
    private Status status;

    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "category_id")
    private Category category;

    @OneToOne(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    @JoinColumn(name = "count_id")
    private BoardCount boardCount;

    @Builder.Default
    @OneToMany(mappedBy="board", cascade= CascadeType.ALL)
    private List<BoardImage> boardImages = new ArrayList();

    @Builder.Default
    @OneToMany(mappedBy="board", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Reply> replies = new ArrayList();

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name="member_id")
    private Member member;

    public String title() {
        return this.title.getTitle();
    }
    public String content(){return this.content.getContent();}
    public String categoryName(){
        return this.category.getName();
    }

    public void initBoardCount(){
        this.boardCount = BoardCount.builder()
                .viewCount(0L)
                .likeCount(0)
                .replyCount(0)
                .board(this)
                .build();
    }

    public boolean emailEquals(String email){
        return this.member.emailEquals(Email.from(email));
    }

    public void setMember(Member member){
        this.member = member;
    }

    public void setCategory(Category category) {
        this.category = category;
    }

    public Board update(Board updateBoard) {
        this.title = updateBoard.getTitle();
        this.content = updateBoard.getContent();
        this.status = updateBoard.getStatus();
        this.category = updateBoard.getCategory();
        return this;
    }

    public void addImage(final BoardImage image){
        this.boardImages.add(image);
        image.setBoard(this);
    }
    public void setImages(final List<BoardImage> images) {
        this.boardImages = images;
        for (BoardImage image :  images) {
            image.setBoard(this);
        }
    }

    public void deleteBoard(){
        this.status = Status.DELETED;
    }


}
