package com.springboot.tripTrack.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class ReadArticleDto {
	private String title;
	private int post_id;
	private String content;
	private String lat;
	private String lng;
	private String placeName;
	private String file_name;
	private String city_name;
	private String tag_name;
}
