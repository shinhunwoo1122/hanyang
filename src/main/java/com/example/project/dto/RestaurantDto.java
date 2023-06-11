package com.example.project.dto;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import org.springframework.web.multipart.MultipartFile;

@Getter
@Setter
@ToString
public class RestaurantDto {
    private Integer id;
    private String title;
    private String categoryName;
    private String category;
    private String address;
    private String writer;
    private String content;
    private String orgImgName;
    private String changeName;
    private String fileUrl;
    private MultipartFile file;
}
