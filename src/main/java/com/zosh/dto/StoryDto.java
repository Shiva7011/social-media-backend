package com.zosh.dto;

import java.time.LocalDateTime;

import com.zosh.model.StoryMediaType;

import lombok.Data;

@Data
public class StoryDto {

	private Long id;
	private Integer userId;
	private String mediaUrl;
	private StoryMediaType mediaType;
	private String captions;
	private LocalDateTime createdAt;
}
