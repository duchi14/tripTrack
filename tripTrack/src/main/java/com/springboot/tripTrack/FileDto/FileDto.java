package com.springboot.tripTrack.FileDto;

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
public class FileDto {
	private int post_id;
	private String user_id;
	private String file_name;
	private String file_path;
	private String org_file_name;
}
