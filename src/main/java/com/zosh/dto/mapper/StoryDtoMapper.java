package com.zosh.dto.mapper;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import com.zosh.dto.StoryDto;
import com.zosh.dto.StoryFeedDto;
import com.zosh.model.Story;

public class StoryDtoMapper {

	private StoryDtoMapper() {
	}

	public static StoryDto toStoryDto(Story story) {
		StoryDto storyDto = new StoryDto();
		storyDto.setId(story.getId());
		storyDto.setUserId(story.getUser().getId());
		storyDto.setMediaUrl(story.getMediaUrl());
		storyDto.setMediaType(story.getMediaType());
		storyDto.setCaptions(story.getCaptions());
		storyDto.setCreatedAt(story.getCreatedAt());
		return storyDto;
	}

	public static List<StoryDto> toStoryDtos(List<Story> stories) {
		List<StoryDto> storyDtos = new ArrayList<>();
		for (Story story : stories) {
			storyDtos.add(toStoryDto(story));
		}
		return storyDtos;
	}

	public static List<StoryFeedDto> toStoryFeedDtos(List<Story> stories) {
		Map<Integer, StoryFeedDto> groupedStories = new LinkedHashMap<>();

		for (Story story : stories) {
			Integer userId = story.getUser().getId();
			StoryFeedDto feed = groupedStories.computeIfAbsent(userId, ignored -> {
				StoryFeedDto storyFeedDto = new StoryFeedDto();
				storyFeedDto.setUser(UserDtoMapper.userDTO(story.getUser()));
				return storyFeedDto;
			});

			feed.getStories().add(toStoryDto(story));
		}

		List<StoryFeedDto> feedDtos = new ArrayList<>(groupedStories.values());
		for (StoryFeedDto feed : feedDtos) {
			feed.getStories().sort(Comparator.comparing(StoryDto::getCreatedAt));
		}
		return feedDtos;
	}
}
