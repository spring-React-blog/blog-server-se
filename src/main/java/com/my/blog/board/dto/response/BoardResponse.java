package com.my.blog.board.dto.response;

import com.my.blog.board.domain.Board;
import com.my.blog.board.domain.vo.Content;
import com.my.blog.board.domain.vo.Title;
import com.my.blog.member.entity.vo.Email;
import lombok.*;

import java.io.Serializable;

@Getter
@Builder
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class BoardResponse implements Serializable {
    private static final long serialVersionUID = 1L;
    private Long id;
    private Title title;
    private Content content;
    private String categoryName;
    private Email email;
    private long viewCount;

    public String title(){
        return this.title.getTitle();
    }

    public static BoardResponse toResponse(Board board){
        return BoardResponse.builder()
                .id(board.getId())
                .title(board.getTitle())
                .content(board.getContent())
                .categoryName(board.getCategory().getName())
                .email(board.getMember().getEmail())
                .viewCount(board.getBoardCount().getViewCount())
                .build();
    }



}
