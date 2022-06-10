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
@Table(name="boardcount")
public class BoardCount {

    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name= "count_id")
    private Long id;

    @Column(columnDefinition = "integer default 0")
    private Long viewCount;

    @Column(columnDefinition = "integer default 0")
    private int likeCount;

    @Column(columnDefinition = "integer default 0")
    private int replyCount;

    @OneToOne
    @JoinColumn(name="board_id")
    private Board board;

    public Long increateViewCount() {
        this.viewCount = this.viewCount + 1;
        return this.viewCount;
    }
}
