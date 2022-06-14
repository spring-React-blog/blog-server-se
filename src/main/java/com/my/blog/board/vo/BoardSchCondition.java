package com.my.blog.board.vo;

import com.my.blog.board.domain.Content;
import com.my.blog.board.domain.Title;
import com.my.blog.category.entity.Category;
import com.my.blog.member.entity.vo.Email;
import lombok.Data;

import java.util.Objects;

@Data
public class BoardSchCondition {
    private Title title;
    private Content content;
    private Category category;
    private Long boardId;
    private Email memberEmail;

    public String title() {
        return Objects.nonNull(this.title) ? this.title.getTitle() : null ;
    }

    public String content() {
        return Objects.nonNull(this.content) ? this.content.getContent() : null ;
    }
    public String categoryName() {
        return Objects.nonNull(this.category) ? this.category.getName() : null ;
    }

    public String memberEmail(){
        return Objects.nonNull(this.memberEmail) ? this.memberEmail.getEmail() : null ;
    }
}
