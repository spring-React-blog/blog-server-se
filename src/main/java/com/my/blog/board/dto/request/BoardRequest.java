package com.my.blog.board.dto.request;

import com.my.blog.board.domain.Board;
import com.my.blog.board.domain.vo.Status;
import com.my.blog.board.domain.vo.Content;
import com.my.blog.board.domain.vo.Title;
import lombok.*;
import org.springframework.lang.Nullable;

import javax.validation.Valid;

@Valid
@Getter
@Builder
public class BoardRequest {

    private Title title;

    private Content content;

    private Status status;

    private Long memberId;

    @Nullable
    private Long categoryId;


    public Board toEntity(){
        if(this.status == null) this.status = Status.TRUE;
        Board board = Board.builder()
                .title(this.title)
                .content(this.content)
                .status(this.status)
                .build();
        board.initBoardCount();
        return board;
    }

}
