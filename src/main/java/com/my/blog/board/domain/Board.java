package com.my.blog.board.domain;

import com.my.blog.board.vo.Content;
import com.my.blog.board.vo.Title;
import com.my.blog.board.vo.response.BoardResponse;
import com.my.blog.category.entity.Category;
import com.my.blog.count.entity.BoardCount;
import com.my.blog.member.entity.Member;
import lombok.*;

import javax.persistence.*;
import java.time.LocalDateTime;

@Entity
@Getter
@Builder
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor(access = AccessLevel.PRIVATE)
public class Board {

    @Id @GeneratedValue
    private Long id;

    @Column(name="title")
    private Title title;

    @Column(name="content")
    private Content content;

    @Column(name="rgstDateTime")
    private LocalDateTime rgstDateTime;

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
  /*  public String content(){
        return this.content.
    }*/
    public String categoryName(){
        return this.category.getName();
    }

    public void initBoardCount(BoardCount boardCount){
        this.boardCount = boardCount;
    }

    public void setMember(Member member){
        this.member = member;
    }

    public void setCategory(Category category) {
        this.category = category;
    }

    public BoardResponse toResponse(){
       return BoardResponse.builder()
                .id(this.getId())
                .title(this.getTitle())
                .content(this.getContent())
                .categoryName(this.category.getName())
                .memberId(this.member.getId())
                .rgstDate(this.getRgstDateTime())
                .viewCount(this.boardCount.getViewCount())
                .build();
    }
}
