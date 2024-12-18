package com.springboot.tripTrack.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class MessageDto {
	private String message;
//	private String redirectUrl;
//	private RequestMethod method;
//	private Map<String, Object> data;
	private String href;
}
