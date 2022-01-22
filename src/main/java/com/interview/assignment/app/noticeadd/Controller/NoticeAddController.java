package com.interview.assignment.app.noticeadd.Controller;

import com.interview.assignment.app.noticeadd.Service.NoticeAddService;
import com.interview.assignment.common.EnumResult;
import com.interview.assignment.common.ResultHeaders;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

//@Slf4j
@Controller
@RequiredArgsConstructor
@RequestMapping("/notice")
public class NoticeAddController {
	private final Logger log = LoggerFactory.getLogger(NoticeAddController.class);
	private final NoticeAddService noticeAddService;

	@GetMapping(value = "/noticeadd")
	public String noticeAddPage() {
		log.debug("::: add :::");
		return "notice/noticeadd";
	}
//
//	/**
//	 * 파일 저장
//	 * @param file
//	 * @param subject
//	 */
//	@PostMapping(value = "/register", produces = MediaType.APPLICATION_JSON_VALUE)
//	public ResponseEntity noticeRegister(@RequestParam("noticeFile") MultipartFile file, @RequestParam("subject") String subject) throws IOException {
//		log.info("::: Notice Register IN :::{}", subject);
//		noticeAddService.noticeRegister(file, subject);
//		return new ResponseEntity(new ResultHeaders(EnumResult.SUCCESS), HttpStatus.OK);
//	}


}
