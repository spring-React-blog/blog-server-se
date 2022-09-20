package com.my.blog.board.dto.request;

import com.my.blog.board.domain.Board;
import com.my.blog.board.domain.vo.Content;
import com.my.blog.board.domain.vo.Status;
import com.my.blog.board.domain.vo.Title;
import com.my.blog.category.entity.Category;
import lombok.*;

import javax.validation.Valid;
import java.io.Serializable;

@Getter
@Setter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class UpdateRequest implements Serializable {
    private static final long serialVersionUID = 1L;

    private Long boardId;
    @Valid
    private Title title;
    @Valid
    private Content content;
    private Status status;
    private Long categoryId;

    @Builder
    public UpdateRequest(
                        Long boardId,
                        Title title,
                        Content content,
                        Status status,
                        Long categoryId
                      ) {
        this.boardId = boardId;
        this.title = title;
        this.content = content;
        this.status = status ;
        this.categoryId = categoryId;

    }

    public Board toBoardEntity(Category category)  {
        if(this.status == null) this.status= Status.TRUE;// = new EnumValueDTO(Status.TRUE);
        Board board = Board.builder()
                .id(this.boardId)
                .title(this.title)
                .content(this.content)
                .status(this.status)
                .category(category)
                .build();

        return board;
    }

}
