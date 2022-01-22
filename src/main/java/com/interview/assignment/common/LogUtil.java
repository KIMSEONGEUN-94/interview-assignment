package com.interview.assignment.common;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import lombok.extern.slf4j.Slf4j;
import org.json.simple.JSONObject;
import org.springframework.core.io.FileSystemResource;
import org.springframework.core.io.support.ResourceRegion;
import org.springframework.http.ResponseEntity;
import org.springframework.web.multipart.commons.CommonsMultipartFile;

import javax.servlet.http.HttpServletRequest;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.Objects;

@Slf4j
public class LogUtil {

    private static final int SPACE_SIZE = 30;
    private static final int POINT_LINE_LEN = 10;
    private static final int VALUE_LINE_LEN = 3;
    private static final String LINE_SHAPE = "=";

    public static String printPoint(String message) {
        return makeLine() + " " + String.format("%-" + SPACE_SIZE + "s", message) + " " + makeLine();
    }

    public static String printEndLine() {
        StringBuilder emptyLine = new StringBuilder();

        for (int i = 1; i <= (SPACE_SIZE / makeLine().length()); i++) {
            emptyLine.append(makeLine());
        }
        return makeLine() + LINE_SHAPE + emptyLine + LINE_SHAPE + makeLine();
    }

    public static String printValue(String valueName, Object value) {
        StringBuilder prefixLine = new StringBuilder();
        StringBuilder suffixLine = new StringBuilder();

        value = value == null ? "" : value;
        Gson gson = new GsonBuilder().setPrettyPrinting().create();

        for (int i = 0; i < VALUE_LINE_LEN; i++) {
            prefixLine.append(LINE_SHAPE);
            suffixLine.append(LINE_SHAPE);
        }
        suffixLine.append(">");

        return prefixLine + " " + String.format("%" + SPACE_SIZE + "s", valueName) + " " + suffixLine + " " + responseParser(value);

    }

    private static String makeLine() {
        StringBuilder line = new StringBuilder();
        for (int i = 0; i < POINT_LINE_LEN; i++) {
            line.append(LINE_SHAPE);
        }
        return line.toString();
    }

    /**
     * Response 정보를 보기 좋은 형식으로 변경
     */
    public static String responseParser(Object body) {
        StringBuilder sb = new StringBuilder();
        ObjectMapper mapper = new ObjectMapper();

        try {

            if (body instanceof CommonsMultipartFile) {
                HashMap<String, String> hashMap = new HashMap<>();
                hashMap.put("ContentType", ((CommonsMultipartFile) body).getContentType());
                hashMap.put("OriginalFileName", ((CommonsMultipartFile) body).getOriginalFilename());
                hashMap.put("Size", String.valueOf(((CommonsMultipartFile) body).getSize()));

                JSONObject fileData = new JSONObject(hashMap);

                sb.append(mapper.writerWithDefaultPrettyPrinter().writeValueAsString(fileData));
            } else {
                if (body instanceof ResponseEntity) {
                    if (((ResponseEntity<?>) body).getBody() instanceof FileSystemResource) {
                        HashMap<String, String> hashMap = new HashMap<>();
                        hashMap.put("DownloadFile",
                            ((FileSystemResource) Objects.requireNonNull(((ResponseEntity<?>) body).getBody())).getFilename());
                        JSONObject fileData = new JSONObject(hashMap);
                        sb.append(mapper.writerWithDefaultPrettyPrinter().writeValueAsString(fileData));
                    } else if (((ResponseEntity<?>) body).getBody() instanceof ResourceRegion) {
                        sb.append("Streaming Video...");
                    } else {
                        sb.append(mapper.writerWithDefaultPrettyPrinter().writeValueAsString(body));
                    }
                } else {
                    sb.append(mapper.writerWithDefaultPrettyPrinter().writeValueAsString(body));
                }
            }
        } catch (Exception e) {
            log.error("", e);
        }
        return sb.toString();
    }

    /**
     * request 에 담긴 정보를 JSONObject 형태로 반환한다.
     */
    public static JSONObject getParams(HttpServletRequest request) {
        JSONObject jsonObject = new JSONObject();
        Enumeration<String> params = request.getParameterNames();
        while (params.hasMoreElements()) {
            String param = params.nextElement();
            String replaceParam = param.replaceAll("\\.", "-");
            jsonObject.put(replaceParam, request.getParameter(param));
        }
        return jsonObject;
    }

}
