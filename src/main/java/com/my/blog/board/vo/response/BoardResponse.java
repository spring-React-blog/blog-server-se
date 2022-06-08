package com.my.blog.board.vo.response;

import com.my.blog.board.domain.Board;
import com.my.blog.board.vo.Content;
import com.my.blog.board.vo.Title;
import com.querydsl.core.annotations.QueryProjection;
import lombok.*;

import java.time.LocalDateTime;

@Getter
@Builder
@AllArgsConstructor
@NoArgsConstructor (access = AccessLevel.PROTECTED)
public class BoardResponse {
    private Long id;
    private Title title;
    private Content content;
    private String categoryName;
    private Long memberId;
    private LocalDateTime rgstDate;
    private Long viewCount;

    public static BoardResponse toResponse(Board board){
        return BoardResponse.builder()
                .id(board.getId())
                .title(board.getTitle())
                .content(board.getContent())
                .categoryName(board.getCategory().getName())
                .memberId(board.getMember().getId())
                .rgstDate(board.getRgstDateTime())
                .viewCount(board.getBoardCount().getViewCount())
                .build();
    }
/*    private BoardResponse(BoardResponseBuilder builder){
        this.id = builder.getId();
        this.title = builder.getTitle();
        this.content = builder.getContent();
        this.rgstDate = builder.getRgstDate();
        this.categoryName = builder.getCategoryName();
        this.memberId = builder.getMemberId();
    }

    public static BoardResponseBuilder builder(){
        return new BoardResponseBuilder();
    }

    @Getter
    public static class BoardResponseBuilder {
        private Long id;
        private String title;
        private String content;
        private String categoryName;
        private String memberId;
        private LocalDateTime rgstDate;

        BoardResponseBuilder(){}

        public BoardResponseBuilder id(Long id){
            this.id = id;
            return this;
        }

        public BoardResponseBuilder title(String title){
            this.title = title;
            return this;
        }
        public BoardResponseBuilder cten(String content){
            this.content = content;
            return this;
        }
        public BoardResponseBuilder categoryName(String categoryName){
            this.categoryName = categoryName;
            return this;
        }
        public BoardResponseBuilder memberId(String memberId){
            this.memberId = memberId;
            return this;
        }
        public BoardResponseBuilder rgstDate(LocalDateTime rgstDate){
            this.rgstDate = rgstDate;
            return this;
        }

        public BoardResponse build(){
            return new BoardResponse(this);
        }
    }*/
}
