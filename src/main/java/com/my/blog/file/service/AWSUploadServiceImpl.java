package com.my.blog.file.service;

import com.amazonaws.services.s3.AmazonS3Client;
import com.amazonaws.services.s3.model.CannedAccessControlList;
import com.amazonaws.services.s3.model.ObjectMetadata;
import com.amazonaws.services.s3.model.PutObjectRequest;
import com.my.blog.file.dto.ImageDTO;
import com.my.blog.global.common.utils.FileUtil;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

@Slf4j
@RequiredArgsConstructor
@Service
@ConditionalOnProperty(prefix = "upload", name = "service", havingValue = "aws")
public class AWSUploadServiceImpl implements UploadService{

    @Value("${cloud.aws.s3.bucket}")
    public String bucket;
    @Value("${file.upload.path}")
    private String IMAGE_UPLOAD_PATH;

    private final AmazonS3Client amazonS3Client;
    private final FileUtil fileUtil;
    @Override
    public String upload(MultipartFile mfile, String savedName) {

        //aws s3에 업로드
        InputStream inputStream = null;
        try {
            inputStream = mfile.getInputStream();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

        ObjectMetadata meta = new ObjectMetadata();
        meta.addUserMetadata("size" , String.valueOf(mfile.getSize()));

        amazonS3Client.putObject(
                new PutObjectRequest(bucket, savedName, inputStream, meta)
                        .withCannedAcl(CannedAccessControlList.PublicRead));

        return amazonS3Client.getUrl(bucket, savedName).toString();
    }

    @Override
    public ImageDTO getNamesAfterUpload(MultipartFile file) {
            //로컬에 업로드
            String originalName = file.getOriginalFilename();
            String uniqueName =  fileUtil.getUniqueName(originalName);
            String extention = FileUtil.getExtention(originalName);

            //S3 업로드
            String uploadUrl = upload(file, uniqueName);
            ImageDTO dto = ImageDTO.builder()
                    .originalName(originalName)
                    .savedName(uniqueName)
                    .uploadPath(uploadUrl)
                    .extension(extention)
                    .build();
        return dto;
    }
    @Override
    public List<ImageDTO> getNamesAfterUpload(List<MultipartFile> files) {
        List<ImageDTO> list = new ArrayList<>();
        for (MultipartFile file: files) {
            //로컬에 업로드
            String originalName = file.getOriginalFilename();
            String uniqueName =  fileUtil.getUniqueName(originalName);
            String extention = FileUtil.getExtention(originalName);

            //S3 업로드
            String uploadUrl = upload(file, uniqueName);
            ImageDTO dto = ImageDTO.builder()
                    .originalName(originalName)
                    .savedName(uniqueName)
                    .uploadPath(uploadUrl)
                    .extension(extention)
                    .build();
            list.add(dto);
        }
        return list;
    }

}
