package com.example.project.dto;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
public class RestaurantDto {
    private Integer id;
    private String title;
    private String categoryName;
    private String address;
    private String writer;
    private String content;
    private String orgImgName;
    private String fileUrl;
}
