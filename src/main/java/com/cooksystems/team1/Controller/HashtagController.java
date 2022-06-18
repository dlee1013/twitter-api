package com.cooksystems.team1.Controller;

import java.util.List;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.cooksystems.team1.dtos.HashtagDto;
import com.cooksystems.team1.services.HashtagService;

import lombok.RequiredArgsConstructor;


@RestController
@RequestMapping("/tags")
@RequiredArgsConstructor
public class HashtagController {
	
	private final HashtagService hashtagService;

	@GetMapping()
	public List<HashtagDto> getAllHashTags() {
		return hashtagService.getAllHashTags();
	}

}
