package com.interview.assignment.common;

import lombok.Getter;
import lombok.Setter;
import lombok.experimental.SuperBuilder;

/**
 * 파일 업로드 모델
 */
@Getter @Setter
@SuperBuilder
public class FileUploadModel {

    // 저장 경로
    private String uploadPath;
    
    // 실제 파일명
    private String originFileName;

}
