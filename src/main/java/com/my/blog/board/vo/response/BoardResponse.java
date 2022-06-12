package com.my.blog.board.vo.response;

import com.my.blog.board.domain.Board;
import com.my.blog.board.domain.Content;
import com.my.blog.board.domain.Title;
import com.my.blog.category.entity.Category;
import com.my.blog.count.entity.BoardCount;
import com.my.blog.member.entity.Member;
import lombok.*;

@Getter
@Builder
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class BoardResponse {
    private Long id;
    private Title title;
    private Content content;
    private Category category;
    private Member member;
  //  private Email email;
    private BoardCount boardCount;

    public String title(){
        return this.title.getTitle();
    }
    public static BoardResponse toResponse(Board board){
        return BoardResponse.builder()
                .id(board.getId())
                .title(board.getTitle())
                .content(board.getContent())
                .category(board.getCategory())
                .member(board.getMember())
                .boardCount(board.getBoardCount())
                .build();
    }



}
