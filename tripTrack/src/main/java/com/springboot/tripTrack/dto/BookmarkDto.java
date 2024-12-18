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
public class BookmarkDto {
	private String user_id;
	private int post_id;
	private String lat;
	private String lng;
	private String placeName;
	private String category_name;
	private String tag_name;
	private String city_name;
}
