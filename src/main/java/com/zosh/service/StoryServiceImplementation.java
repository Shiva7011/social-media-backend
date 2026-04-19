package com.zosh.service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.zosh.dto.StoryDto;
import com.zosh.dto.StoryFeedDto;
import com.zosh.dto.mapper.StoryDtoMapper;
import com.zosh.exception.UserException;
import com.zosh.model.Story;
import com.zosh.model.User;
import com.zosh.repository.StoryRepository;
import com.zosh.request.CreateStoryRequest;

@Service
public class StoryServiceImplementation implements StoryService {

	private static final long STORY_EXPIRY_IN_HOURS = 24L;

	@Autowired
	private StoryRepository storyRepo;

	@Autowired
	private UserService userService;

	@Override
	public StoryDto createStory(CreateStoryRequest request, Integer userId) throws UserException {
		User user = userService.findUserById(userId);

		Story story = new Story();
		story.setUser(user);
		story.setMediaUrl(request.getMediaUrl());
		story.setMediaType(request.getMediaType());
		story.setCaptions(request.getCaptions());
		story.setCreatedAt(LocalDateTime.now());

		return StoryDtoMapper.toStoryDto(storyRepo.save(story));
	}

	@Override
	public List<StoryFeedDto> findStoriesForFollowing(Integer userId) throws UserException {
		User user = userService.findUserById(userId);
		Set<User> followingUsers = user.getFollowing();

		if (followingUsers == null || followingUsers.isEmpty()) {
			return List.of();
		}

		List<Integer> followingUserIds = followingUsers.stream().map(User::getId).collect(Collectors.toList());
		List<Story> stories = storyRepo.findActiveStoriesByUserIds(followingUserIds, getActiveStoryCutoff());
		return StoryDtoMapper.toStoryFeedDtos(stories);
	}

	@Override
	public List<StoryDto> findStoryByUserId(Integer userId) throws UserException {
		userService.findUserById(userId);
		List<Story> stories = storyRepo.findActiveStoriesByUserId(userId, getActiveStoryCutoff());
		return StoryDtoMapper.toStoryDtos(stories);
	}

	private LocalDateTime getActiveStoryCutoff() {
		return LocalDateTime.now().minusHours(STORY_EXPIRY_IN_HOURS);
	}
}
