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
public class PageDto {
	private String cat_name;
	private int startPage;
	private int endPage;
	private int nowPage;
	private int maxPage;
	private int pageListLimit;
	private int listCount;
	private int limitPage;
}
