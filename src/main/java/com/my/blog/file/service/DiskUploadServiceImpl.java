package com.my.blog.file.service;

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
import java.util.ArrayList;
import java.util.List;

@Slf4j
@RequiredArgsConstructor
@Service
@ConditionalOnProperty(prefix = "upload", name = "service", havingValue = "local")
public class DiskUploadServiceImpl implements UploadService{
    @Value("${file.upload.path}")
    private String IMAGE_UPLOAD_PATH;
    private final FileUtil fileUtil;

    @Override
    public String upload(MultipartFile file, String uploadPath) {
        fileUtil.isFolderExist(IMAGE_UPLOAD_PATH);
        log.debug("pathWithFileName = {} " ,uploadPath);
        String pathWithFileName =  fileUtil.getUpdateNameForDuplication(uploadPath);

        try {
            file.transferTo(new File(pathWithFileName));
        } catch (IOException e) {
            log.info("file upload Fail upload path ={}, ex={} " , pathWithFileName,e );
        } catch (Exception e) {
            log.debug("file upload Exception = {} " , e );
        }
        return pathWithFileName;
    }

    @Override
    public ImageDTO getNamesAfterUpload(MultipartFile file) {
        String originalName = file.getOriginalFilename();
        String fullPath = IMAGE_UPLOAD_PATH + File.separator +  fileUtil.getUniqueName(originalName);
        String uploadFullPath = upload(file, fullPath);

        ImageDTO image = ImageDTO.builder()
                .originalName(originalName)
                .savedName( fileUtil.getSaveFileName(uploadFullPath))
                .extension( fileUtil.getExtention(uploadFullPath))
                .uploadPath(IMAGE_UPLOAD_PATH)
                .build();

        return image;
    }

    /**
     * 파일 업로드
     * @return List<ImageDTO>
     * */
    public List<ImageDTO> getNamesAfterUpload(List<MultipartFile> files){
        List<ImageDTO> imageDTOList = new ArrayList<>();

        for (MultipartFile m : files ) {
            String originalName = m.getOriginalFilename();
            String fullPath = IMAGE_UPLOAD_PATH + File.separator +  fileUtil.getUniqueName(originalName);
            String uploadFullPath = upload(m, fullPath);

            ImageDTO image = ImageDTO.builder()
                    .originalName(originalName)
                    .savedName( fileUtil.getSaveFileName(uploadFullPath))
                    .extension( fileUtil.getExtention(uploadFullPath))
                    .uploadPath(IMAGE_UPLOAD_PATH)
                    .build();

            imageDTOList.add(image);
        }

        return imageDTOList;
    }
}
