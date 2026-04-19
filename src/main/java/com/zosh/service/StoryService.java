package com.zosh.service;

import java.util.List;

import com.zosh.dto.StoryDto;
import com.zosh.dto.StoryFeedDto;
import com.zosh.exception.UserException;
import com.zosh.request.CreateStoryRequest;

public interface StoryService {

	StoryDto createStory(CreateStoryRequest request, Integer userId) throws UserException;

	List<StoryFeedDto> findStoriesForFollowing(Integer userId) throws UserException;

	List<StoryDto> findStoryByUserId(Integer userId) throws UserException;
}
