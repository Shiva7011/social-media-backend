package com.zosh.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.zosh.dto.StoryDto;
import com.zosh.dto.StoryFeedDto;
import com.zosh.exception.UserException;
import com.zosh.model.User;
import com.zosh.request.CreateStoryRequest;
import com.zosh.service.StoryService;
import com.zosh.service.UserService;

import jakarta.validation.Valid;

@RestController
@RequestMapping("/api/stories")
public class StoryController {

	@Autowired
	private StoryService storyService;

	@Autowired
	private UserService userService;

	@PostMapping({ "", "/create" })
	public ResponseEntity<StoryDto> createStoryHandler(@Valid @RequestBody CreateStoryRequest request,
			@RequestHeader("Authorization") String token) throws UserException {
		User reqUser = userService.findUserProfileByJwt(token);
		StoryDto createdStory = storyService.createStory(request, reqUser.getId());
		return new ResponseEntity<>(createdStory, HttpStatus.CREATED);
	}

	@GetMapping("/following")
	public ResponseEntity<List<StoryFeedDto>> findFollowingStoriesHandler(
			@RequestHeader("Authorization") String token) throws UserException {
		User reqUser = userService.findUserProfileByJwt(token);
		List<StoryFeedDto> stories = storyService.findStoriesForFollowing(reqUser.getId());
		return new ResponseEntity<>(stories, HttpStatus.OK);
	}

	@GetMapping({ "/user/{userId}", "/{userId}" })
	public ResponseEntity<List<StoryDto>> findAllStoryByUserIdHandler(@PathVariable Integer userId)
			throws UserException {
		List<StoryDto> stories = storyService.findStoryByUserId(userId);
		return new ResponseEntity<>(stories, HttpStatus.OK);
	}
}
