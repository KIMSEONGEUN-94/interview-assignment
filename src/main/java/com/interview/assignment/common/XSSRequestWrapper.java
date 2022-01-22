package com.interview.assignment.common;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.extern.slf4j.Slf4j;

import javax.servlet.ReadListener;
import javax.servlet.ServletInputStream;
import javax.servlet.ServletRequest;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletRequestWrapper;
import java.io.*;
import java.util.Map;

@Slf4j
public class XSSRequestWrapper extends HttpServletRequestWrapper {

    private final String body;

    public XSSRequestWrapper(ServletRequest request) throws IOException {
        super((HttpServletRequest) request);
        body = getBody((HttpServletRequest) request);
    }

    @Override
    public ServletInputStream getInputStream() {
        final ByteArrayInputStream bis = new ByteArrayInputStream(body.getBytes());
        return new ServletInputStreamImpl(bis);
    }

    @Override
    public BufferedReader getReader() throws IOException {
        return new BufferedReader(new InputStreamReader(this.getInputStream()));
    }

    private String getBody(HttpServletRequest request) throws IOException {

        StringBuilder sb = new StringBuilder();

        try (
            InputStream inputStream = request.getInputStream();
            BufferedReader br = new BufferedReader(new InputStreamReader(inputStream))
        ) {
            char[] charBuffer = new char[128];
            int bytesRead = -1;
            while ((bytesRead = br.read(charBuffer)) > 0) {
                sb.append(charBuffer, 0, bytesRead);
            }
        }
        ObjectMapper mapper = new ObjectMapper();
        Map<String, Object> map = mapper.readValue(sb.toString(),  new TypeReference<Map<String,Object>>(){});

        return XSSConvertUtil.escape(mapper.writeValueAsString(map));
    }

    static class ServletInputStreamImpl extends ServletInputStream {

        private final InputStream is;

        public ServletInputStreamImpl(InputStream bis) {
            is = bis;
        }

        public int read() throws IOException {
            return is.read();
        }

        public int read(byte[] b) throws IOException {
            return is.read(b);
        }

        @Override
        public boolean isFinished() {
            return false;
        }

        @Override
        public boolean isReady() {
            return false;
        }

        @Override
        public void setReadListener(ReadListener listener) {

        }
    }
}
