package com.example.project.dto;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
public class FileDto {
    private Integer id;
    private String orginName;
    private String fileSize;
    private String fileUrl;
    private String changeName;
    private Integer restaurantId;
}
