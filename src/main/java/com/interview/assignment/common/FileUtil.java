package com.interview.assignment.common;


import lombok.extern.slf4j.Slf4j;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Objects;


@Component
public class FileUtil {
    private static final Logger log = LoggerFactory.getLogger(FileUtil.class);
    private String uploadPath = "\\files\\upload";


    public FileUploadModel upload(MultipartFile file) throws IOException {

        log.info("::: FileUploadModel  :::");
        FileUploadModel uploadModel = null;

        log.debug(LogUtil.printValue("업로드 파일 정보", file));

        boolean makeDirSuccess = true;

        File uploadDir = new File(uploadPath);

        if (!uploadDir.isDirectory()) {
            log.info("::: uploadDir :::");
            makeDirSuccess = uploadDir.mkdirs();
        }

        if (makeDirSuccess) {
            String originFileName = file.getOriginalFilename();
            log.info("::: originFileName :::{}", originFileName);
            File uploadFile = new File(uploadPath, originFileName);

            file.transferTo(uploadFile);

            uploadModel =
                FileUploadModel.builder()
                    .originFileName(originFileName)
                    .uploadPath(uploadPath)
                    .build();
            log.info("::: uploadModel :::{}", uploadModel);
        }

        return uploadModel;
    }
}
