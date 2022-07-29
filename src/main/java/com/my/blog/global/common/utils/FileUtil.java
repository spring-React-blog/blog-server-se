package com.my.blog.global.common.utils;

import com.my.blog.board.dto.request.ImageDTO;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Slf4j
@Component
public class FileUtil {

    @Value("${file.upload.path}")
    private String IMAGE_UPLOAD_PATH;

    private void isFolderExist(){
        log.info("IMAGE_UPLOAD_PATH > " + IMAGE_UPLOAD_PATH);
        File file = new File(IMAGE_UPLOAD_PATH);
        if(!file.exists()){
            if(file.mkdirs()){
                log.info("dir create Success");
            } else {
                log.info("dir create Fail");
            }
        }
    }

    /**
     * 파일 업로드
     * @return List<ImageDTO>
     * */
    public  List<ImageDTO> getNamesAfterUpload(List<MultipartFile> files){
        List<ImageDTO> imageDTOList = new ArrayList<>();

        for (MultipartFile m : files ) {
            String originalName = m.getOriginalFilename();

            String fullPath = IMAGE_UPLOAD_PATH + File.separator +  getUniqueName(originalName);
            log.debug("pathWithFileName = {} " ,fullPath);
            String pathWithFileName = getUpdateNameForDuplication(fullPath);
            uploadFile(m, pathWithFileName);
            ImageDTO image = ImageDTO.builder()
                    .originalName(originalName)
                    .savedName(getSaveFileName(pathWithFileName))
                    .extension(getExtention(pathWithFileName))
                    .uploadPath(IMAGE_UPLOAD_PATH)
                    .build();

            imageDTOList.add(image);
        }

        return imageDTOList;
    }

    private String getSaveFileName(String fullPath){
        return fullPath.substring(fullPath.lastIndexOf(File.separator)+1);
    }

    private String getExtention(String fullPath) {
        return fullPath.substring(fullPath.lastIndexOf(".")+1);
    }

    private String getUniqueName( String originalName){
        return UUID.randomUUID() + "_" + originalName;
    }

    private String getUpdateNameForDuplication(String pathWithFileName){
        String fileName = pathWithFileName;
        File file = new File(fileName);
        if(file.isFile()){
            boolean _exist = true;
            int idx = 0;

            while(_exist){
                String updatedName = fileName + "(" + (idx++) + ")";
                _exist = new File(updatedName).isFile();
                if(!_exist) fileName = updatedName;
            }
        }
        return fileName;
    }

    public void uploadFile(MultipartFile file,  String pathWithFileName ) {
        isFolderExist();
        try {
            file.transferTo(new File(pathWithFileName));
        } catch (IOException e) {
            log.info("file upload Fail upload path ={}, ex={} " , pathWithFileName,e );
        } catch (Exception e) {
            log.debug("file upload Exception = {} " , e );
        }

    }
}
