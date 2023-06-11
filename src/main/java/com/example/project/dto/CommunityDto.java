package com.example.project.dto;


import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Setter
@Getter
@ToString
public class CommunityDto {
    private Integer id;

    private String subject;

    private String content;

    private String writer;

    private String regdate;

}
