package com.example.project.dto;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Setter
@Getter
@ToString
public class RestaurantSaveDto {

    private Integer id;
    private String name;
    private String category;
    private String address;
    private String writer;
    private String pwd;
    private String regdate;
    private String status;
    private String content;

}
