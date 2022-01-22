package com.interview.assignment.app.notice;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@Slf4j
@RequiredArgsConstructor
@RequestMapping("/notice")
public class NoticeController {

	@GetMapping(value = "/noticelist")
	public String list() {
		log.debug("::: main:::");
		return "notice/noticelist";
	}
}
