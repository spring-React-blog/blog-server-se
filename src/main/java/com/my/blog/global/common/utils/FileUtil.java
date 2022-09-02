package com.my.blog.global.common.utils;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.io.File;
import java.util.UUID;

@Slf4j
@Component
public class FileUtil {

    @Value("${file.upload.path}")
    private String IMAGE_UPLOAD_PATH;

    public static void isFolderExist(String uploadPath){
      //  log.info("IMAGE_UPLOAD_PATH > " + IMAGE_UPLOAD_PATH);
        File file = new File(uploadPath);
        if(!file.exists()){
            if(file.mkdirs()){
                log.info("dir create Success");
            } else {
                log.info("dir create Fail");
            }
        }
    }


    public static String getSaveFileName(String fullPath){
        return fullPath.substring(fullPath.lastIndexOf(File.separator)+1);
    }

    public static String getExtention(String fullPath) {
        return fullPath.substring(fullPath.lastIndexOf(".")+1);
    }

    public static String getUniqueName( String originalName){
        return UUID.randomUUID() + "_" + originalName;
    }

    public static String getUpdateNameForDuplication(String pathWithFileName){
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


}
