package com.interview.assignment.common;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.apache.commons.beanutils.BeanUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;

import java.util.HashMap;
import java.util.Map;


public class ResponseUtil {

    private static final Logger log = LoggerFactory.getLogger(ResponseUtil.class);

    private static HttpHeaders setHeaders(Map<String, String> map) throws Exception {

        HttpHeaders headers = new HttpHeaders();

        final Map<String, String> parameterMap = new HashMap<String, String>();
        parameterMap.put("charset", "utf-8");
        headers.setContentType(new MediaType("application", "json", parameterMap));

        for (String key : map.keySet()) {
            headers.set(key, map.get(key));
        }
        return headers;
    }


    public static ResponseEntity<HeaderUtil> setResponseHeaders(HeaderUtil jsonResponse) {
        ResponseEntity<HeaderUtil> responseEntity = null;
        try {
            HttpStatus httpStatus = HttpStatus.valueOf(jsonResponse.getMessage());
            //responseEntity = new ResponseEntity< JsonResponse >( jsonResponse, setHeaders( BeanUtils.describe( jsonResponse ) ), httpStatus );	//header + body
            responseEntity = new ResponseEntity<HeaderUtil>(null, setHeaders(BeanUtils.describe(jsonResponse)), httpStatus);    //header
        } catch (Exception e) {
            log.error("", e);
        }
        return responseEntity;
    }

    private static String setResponseBodys(Object body) {
        StringBuilder sb = new StringBuilder();
        ObjectMapper mapper = new ObjectMapper();

        // 문자열 Response 할떄 치환처리
        mapper.getFactory().setCharacterEscapes(new HTMLCharacterEscapes());

        try {
            sb.append(mapper.writerWithDefaultPrettyPrinter().writeValueAsString(body));
        } catch (Exception e) {
            log.error("", e);
        }
        return sb.toString();
    }


    public static ResponseEntity<Object> setResponse(HeaderUtil header, Object body) {
        ResponseEntity<Object> responseEntity = null;
        try {
            HttpStatus httpStatus = HttpStatus.valueOf(header.getMessage());


            if (body != null) {
                responseEntity = new ResponseEntity<Object>(setResponseBodys(body), setHeaders(BeanUtils.describe(header)), httpStatus); //header + body
            } else {
                responseEntity = new ResponseEntity<Object>(null, setHeaders(BeanUtils.describe(header)), httpStatus); //header
            }
        } catch (Exception e) {
            log.error("", e);
        }
        return responseEntity;
    }



}
