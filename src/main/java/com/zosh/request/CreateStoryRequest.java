package com.zosh.request;

import com.zosh.model.StoryMediaType;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

@Data
public class CreateStoryRequest {

	@NotBlank
	private String mediaUrl;

	@NotNull
	private StoryMediaType mediaType;

	private String captions;
}
