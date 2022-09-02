package com.my.blog.count.entity;

import com.my.blog.board.domain.Board;
import lombok.*;
import org.hibernate.annotations.ColumnDefault;

import javax.persistence.*;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor(access = AccessLevel.PRIVATE)
@Builder
@Table(name="boardCount")
public class BoardCount {

    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name= "count_id")
    private Long id;

    @Column
    private Long viewCount;

    @Column
    private int likeCount;

    @Column
    private int replyCount;

    @OneToOne(mappedBy = "boardCount", fetch = FetchType.LAZY)
    private Board board;

    @Builder
    public BoardCount(Long viewCount
                    ,int likeCount
                    ,int replyCount
                    ,Board board){
        this.viewCount = viewCount;
        this.likeCount = likeCount;
        this.replyCount = replyCount;
        this.board = board;
    }



    public BoardCount updateViewCount(long count) {
        this.viewCount = count;
        return this;
    }
}
