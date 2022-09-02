package com.my.blog.file;

import com.my.blog.file.dto.ImageDTO;
import com.my.blog.file.service.UploadService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

@RequiredArgsConstructor
@RestController
@RequestMapping("/api")
public class UploadController {
    private final UploadService uploadService;

    @PostMapping("/upload")
    public ResponseEntity<ImageDTO> upload(MultipartFile file){
        ImageDTO imageDTO = uploadService.getNamesAfterUpload(file);

        return new ResponseEntity(imageDTO, HttpStatus.OK);
    }
}
