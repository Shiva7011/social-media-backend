package com.zosh.dto;

import java.util.ArrayList;
import java.util.List;

import lombok.Data;

@Data
public class StoryFeedDto {

	private UserDto user;
	private List<StoryDto> stories = new ArrayList<>();
}
