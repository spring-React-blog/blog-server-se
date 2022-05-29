package com.my.blog.board.vo.request;

import com.my.blog.board.domain.Board;
import lombok.Data;
import lombok.ToString;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Size;
import java.time.LocalDateTime;

@Data
@ToString
public class BoardRequest {

    @NotBlank
    @Size(max=30,message = "30자 이하로 입력해주세요.")
    String title;

    @NotEmpty
    String cten;

    public Board toEntity(){
        return Board.builder()
                .title(this.title)
                .content(this.cten)
                .build();
    }

}
