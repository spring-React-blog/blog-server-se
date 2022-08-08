package com.my.blog.board.domain.vo;

import com.fasterxml.jackson.annotation.JsonValue;
import lombok.*;

import javax.persistence.Column;
import javax.persistence.Embeddable;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

@AllArgsConstructor(access = AccessLevel.PRIVATE)
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@EqualsAndHashCode
@Getter
@Embeddable
public class Title {
    @NotBlank
    @NotNull
    @Size(max=30,message = "30자 이하로 입력해주세요.")
    @Column(name="title")
    private String title;

    @JsonValue
    public String title() {
        return title;
    }

    public static Title from(String title){
        return new Title(title);
    }

}
