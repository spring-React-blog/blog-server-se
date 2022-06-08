package com.my.blog.count.entity;

import lombok.Builder;
import lombok.Getter;
import org.hibernate.annotations.ColumnDefault;

import javax.persistence.*;

@Entity
@Table(name="BoardCount")
@Getter
public class BoardCount {

    @Id @GeneratedValue
    @Column(name= "count_id")
    private Long id;

    @Column(columnDefinition = "integer default 0")
    private Long viewCount;

    @Column(columnDefinition = "integer default 0")
    private int likeCount;

    @Column(columnDefinition = "integer default 0")
    private int replyCount;
}
