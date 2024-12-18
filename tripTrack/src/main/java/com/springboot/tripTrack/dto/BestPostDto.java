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
public class BestPostDto {
	private int post_id;
	private int board_id;
	private String category_name;
	private String country_name;
	private String city_name;
	private String id;
	private int cnt_star;
	private double avg_star;
	private String tag_name;
	private String title;
	private String body;
	private String file_name;
}
