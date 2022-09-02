package com.my.blog.board.dto;

import com.my.blog.board.domain.vo.Content;
import com.my.blog.board.domain.vo.Title;
import com.my.blog.member.entity.vo.Email;
import lombok.Builder;
import lombok.Getter;

import java.io.Serializable;
import java.util.Objects;

@Getter
@Builder
public class BoardSchCondition implements Serializable {
    private static final long serialVersionUID = 1L;
    private Title title;
    private Content content;
    private Long boardId;
    private Email memberEmail;

    public String title() {
        return Objects.nonNull(this.title) ? this.title.getTitle() : null;
    }

    public String content() {
        return Objects.nonNull(this.content) ? this.content.getContent() : null;
    }

    public String memberEmail() {
        return Objects.nonNull(this.memberEmail) ? this.memberEmail.getEmail() : null;
    }
}
