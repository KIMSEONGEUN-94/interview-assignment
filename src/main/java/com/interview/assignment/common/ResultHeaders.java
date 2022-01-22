package com.interview.assignment.common;

import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;

import java.util.HashMap;
import java.util.Map;

@Slf4j
public class ResultHeaders extends HttpHeaders {

    final String RESULT_CODE_NAME = "Server-Result-Code";
    final String RESULT_MESSAGE_NAME = "Server-Result-Message";

    public ResultHeaders(EnumResult result) {
        setHeaders(result, null);
    }

    public ResultHeaders(EnumResult result, String mediaType) {
        setHeaders(result, mediaType);
    }

    private void setHeaders(EnumResult result, String mediaType) {
        Map<String, String> header = new HashMap<>();
        header.put(RESULT_CODE_NAME, result.getCd());
        header.put(RESULT_MESSAGE_NAME, result.getMsg());

//        setAccept(Collections.singletonList(MediaType.APPLICATION_JSON));
        setContentType(
            MediaType.valueOf(mediaType != null ? mediaType : MediaType.APPLICATION_JSON_VALUE));

        for (String key : header.keySet()) {
            set(key, String.valueOf(header.get(key)));
        }
    }


}
