package com.my.blog.board.domain;

import lombok.Builder;
import lombok.Getter;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import java.time.LocalDateTime;

@Entity
@Getter
@Builder
public class Board {

    @Id @GeneratedValue
    private Long id;

    @Column(name="title")
    String title;

    @Column(name="cten")
    String content;

    @Column(name="rgst")
    LocalDateTime rgstTime;


}
