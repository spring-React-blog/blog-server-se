package com.my.blog.file.service;

import com.my.blog.file.dto.ImageDTO;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

public interface UploadService {
    public String upload(MultipartFile file, String pathWithFileName);
    public ImageDTO getNamesAfterUpload(MultipartFile file);
    public List<ImageDTO> getNamesAfterUpload(List<MultipartFile> files);
}
