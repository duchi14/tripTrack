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
public class ArticleDto {
	private String title;
	private int post_id;
	private int board_no;
	private String content;
	private String localDate;
	private String updateDate;
	private String category_name;
	private String country_name;
	private String city_name;
	private String tag_name;
	private String user_id;
	private int star_point;
}
