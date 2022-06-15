package com.my.blog.board.domain;

import com.my.blog.board.domain.vo.Content;
import com.my.blog.board.domain.vo.Status;
import com.my.blog.board.domain.vo.Title;
import com.my.blog.category.entity.Category;
import com.my.blog.common.entity.BaseTimeEntity;
import com.my.blog.count.entity.BoardCount;
import com.my.blog.member.entity.Member;
import lombok.*;

import javax.persistence.*;

@Entity
@Getter
@Builder
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor(access = AccessLevel.PRIVATE)
@Table(name="board")
public class Board extends BaseTimeEntity {

    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name="board_id")
    private Long id;

    @Column(name="title")
    private Title title;

    @Column(name="content")
    private Content content;

    @Column(name="open_status")
    @Enumerated(EnumType.STRING)
    private Status status;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "category_id")
    private Category category;

    @OneToOne(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    @JoinColumn(name = "count_id")
    private BoardCount boardCount;

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
        return this.member.emailEquals(email);
    }

   public void setMember(Member member){
        this.member = member;
    }

    public void setCategory(Category category) {
        this.category = category;
    }

    public void update(Board updateBoard) {
        this.title = updateBoard.getTitle();
        this.content = updateBoard.getContent();
    }

}
