package com.my.blog.board.dto.request;

import com.my.blog.board.domain.Board;
import com.my.blog.board.domain.BoardImage;
import com.my.blog.board.domain.vo.Content;
import com.my.blog.board.domain.vo.Status;
import com.my.blog.board.domain.vo.Title;
import com.my.blog.category.entity.Category;
import com.my.blog.member.entity.Member;
import lombok.*;
import org.springframework.lang.Nullable;
import org.springframework.web.multipart.MultipartFile;

import javax.validation.Valid;
import java.util.ArrayList;
import java.util.List;

@Valid
@Getter
@Setter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class BoardRequest {

    private Title title;
    private Content content;
    private Status status;
    private Long memberId;
    private List<MultipartFile> files;
    private Long categoryId;

    @Builder
    public BoardRequest(Title title,
                        Content content,
                        Status status,
                        Long memberId,
                        Long categoryId,
                        List<MultipartFile> files) {
        this.title = title;
        this.content = content;
        this.status = status ;
        this.memberId = memberId;
        this.categoryId = categoryId;
        this.files = files;

    }

    public List<BoardImage> toBoardImageEntityList(List<ImageDTO> imageList)  {
       List<BoardImage> boardImages = new ArrayList();
        for(ImageDTO image : imageList) {
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
