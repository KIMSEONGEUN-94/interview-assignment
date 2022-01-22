package com.interview.assignment.app.noticeadd.Service;

import com.interview.assignment.app.noticeadd.Dao.NoticeAddDao;
import com.interview.assignment.app.noticeadd.model.NoticeRegsterParam;
import com.interview.assignment.common.FileUploadModel;
import com.interview.assignment.common.FileUtil;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

@Service
@RequiredArgsConstructor
public class NoticeAddService {
    private static final Logger log = LoggerFactory.getLogger(NoticeAddService.class);
    private final FileUtil fileUtil;
    private final NoticeAddDao noticeAddDao;

    /**
     * 파일 저장
     * @param file
     * @param subject
     */
    public void noticeRegister(MultipartFile file, String subject) throws IOException{
        FileUploadModel fileModel = fileUtil.upload(file);
        String fileName = fileModel.getOriginFileName();
        String filePath = fileModel.getUploadPath();

        NoticeRegsterParam param = new NoticeRegsterParam();
        param.setName(subject);
        param.setFileName(fileName);
        param.setFilepath(filePath);
        log.info("::: getName :::{}", param.getName());
        log.info("::: getFileName :::{}", param.getFileName());
        log.info("::: getFilepath :::{}", param.getFilepath());
        noticeAddDao.insertNoticeRegister(param);
    }
}
