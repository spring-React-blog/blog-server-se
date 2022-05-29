package com.my.blog.board.vo.response;

import com.my.blog.common.response.ResponseHeader;
import lombok.*;

import java.time.LocalDateTime;

@Getter
public class BoardResponse {
    private Long id;
    private String title;
    private String cten;
    private LocalDateTime rgstDate;

    private BoardResponse(BoardResponseBuilder builder){
        this.id = builder.getId();
        this.title = builder.getTitle();
        this.cten = builder.getCten();
        this.rgstDate = builder.getRgstDate();
    }

    public static BoardResponseBuilder builder(){
        return new BoardResponseBuilder();
    }

    @Getter
    public static class BoardResponseBuilder {
        private Long id;
        private String title;
        private String cten;
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
        public BoardResponseBuilder cten(String cten){
            this.cten = cten;
            return this;
        }
        public BoardResponseBuilder rgstDate(LocalDateTime rgstDate){
            this.rgstDate = rgstDate;
            return this;
        }

        public BoardResponse build(){
            return new BoardResponse(this);
        }
    }
}
