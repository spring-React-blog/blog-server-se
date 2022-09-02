package com.my.blog.board.domain;

import com.my.blog.global.common.entity.BaseTimeEntity;
import lombok.*;

import javax.persistence.*;

@Getter
@AllArgsConstructor(access = AccessLevel.PRIVATE)
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Table(name="boardImage")
@Entity
public class BoardImage extends BaseTimeEntity {

    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name="image_id")
    private Long id;

    @Column(name="originalName")
    private String originalName;

    @Column(name="uploadPath")
    private String uploadPath;

    @Column(name="uploadName")
    private String uploadName;

    @Column(name="extension")
    private String extension;

    @Column(name="file_size")
    private Long size;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name="board_id")
    private Board board;

    @Builder
    private BoardImage(String originalName,
                      String uploadName,
                      String extension,
                       String uploadPath
                       ){
        this.originalName = originalName;
        this.uploadName = uploadName;
        this.uploadPath = uploadPath;
        this.extension = extension;

    }

    public void setBoard(Board board){
        this.board = board;
    }


    

}
