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
public class TrackPostDto {
	private String user_id;
	private int board_id;
	private int trackPost_id;
	private String post_id;
	private String lat;
	private String lng;
	private String placeName;
	private String title;
	private String content;
	
	private String file_name;
	private String profile;
	private String imgFile;
	private int imgFilePostId;
}
