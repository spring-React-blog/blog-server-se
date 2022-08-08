package com.my.blog.file.dto;

import lombok.*;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class ImageDTO {
    private String originalName;
    private String extension;
    private String savedName;
    private String uploadPath;

    @Builder
    public ImageDTO(String extension,
                    String savedName,
                    String uploadPath,
                    String originalName
                    ){
        this.extension = extension;
        this.savedName = savedName;
        this.uploadPath = uploadPath;
        this.originalName = originalName;
    }

}
