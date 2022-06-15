package com.my.blog.board.domain.vo;

import lombok.*;

import javax.persistence.Embeddable;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Size;

@Embeddable
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor(access = AccessLevel.PRIVATE)
@EqualsAndHashCode
@Getter
public class Content {
    @NotEmpty
    @NotBlank
    @Size(max=500,message = "500자 이하로 입력해주세요.")
    private String content;

    public static Content of(String content){
        return new Content(content);
    }

}
