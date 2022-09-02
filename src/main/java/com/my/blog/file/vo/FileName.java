package com.my.blog.file.vo;

import com.my.blog.file.dto.ImageDTO;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;

@Getter
@AllArgsConstructor(access = AccessLevel.PRIVATE)
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class FileName {
    private String fileName;

    public FileName from(ImageDTO image){
        return new FileName(image.getUploadPath() + image.getSavedName() + image.getExtension());
    }

    public List<FileName> from(List<ImageDTO> images){
        List<FileName> list = new ArrayList<>();
        for (ImageDTO image:
             images) {
            list.add(new FileName(image.getUploadPath() + image.getSavedName() + image.getExtension()));
        }
        return list;
    }
}
