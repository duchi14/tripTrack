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
public class CitySearchDto {
	private String cat_name;
	private int startRow;
	private int limitPage;
	private int page;
	private String city0;
	private String city1;
	private String city2;
	private String city3;
	private String city4;
	private String city5;
	private String city6;
	private String city7;
	private String city8;
}
