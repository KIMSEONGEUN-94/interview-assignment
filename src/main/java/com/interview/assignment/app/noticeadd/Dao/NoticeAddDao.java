package com.interview.assignment.app.noticeadd.Dao;

import com.interview.assignment.app.noticeadd.model.NoticeRegsterParam;
import org.springframework.stereotype.Repository;

@Repository
public interface NoticeAddDao {

    /**
     * 파일 저장
     * @param param
     */
    void insertNoticeRegister(NoticeRegsterParam param);
}
