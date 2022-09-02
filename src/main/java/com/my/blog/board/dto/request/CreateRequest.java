package com.my.blog.board.dto.request;

import com.my.blog.board.domain.Board;
import com.my.blog.board.domain.BoardImage;
import com.my.blog.board.domain.vo.Content;
import com.my.blog.board.domain.vo.Status;
import com.my.blog.board.domain.vo.Title;
import com.my.blog.category.entity.Category;
import com.my.blog.file.dto.ImageDTO;
import com.my.blog.member.entity.Member;
import lombok.*;

import javax.validation.Valid;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class CreateRequest implements Serializable {
    private static final long serialVersionUID = 1L;
    @Valid
    private Title title;
    @Valid
    private Content content;
    private Status status;
    private List<ImageDTO> files;
    private Long categoryId;

    @Builder
    public CreateRequest(Title title,
                         Content content,
                         Status status,
                         Long categoryId,
                         List<ImageDTO> files) {
        this.title = title;
        this.content = content;
        this.status = status ;
        this.categoryId = categoryId;
        this.files = files;

    }

    public List<BoardImage> toBoardImageEntityList()  {
       List<BoardImage> boardImages = new ArrayList();
        for(ImageDTO image : files) {
            BoardImage boardImage = BoardImage.builder()
                    .originalName(image.getOriginalName())
                    .uploadName(image.getSavedName())
                    .extension(image.getExtension())
                    .uploadPath(image.getUploadPath())
                    .build();

            boardImages.add(boardImage);
        }
        return boardImages;
    }

    public Board toBoardEntity(List<BoardImage> boardImages, Member member, Category category)  {
        if(this.status == null) this.status = Status.TRUE;
        Board board = Board.builder()
                .title(this.title)
                .content(this.content)
                .status(this.status)
                .member(member)
                .category(category)
                .build();

        board.setImages(boardImages);
        board.initBoardCount();
        return board;
    }

}
