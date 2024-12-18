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
public class ReviewDto {
	private int review_id;
	private int post_id;
	private String title;
	private String content;
	private int hit_cnt;
	private String proFile;
	private double star_point;
	private double avg_star;
	private String user_id;
	private String localDate;
	private String updateDate;
}
