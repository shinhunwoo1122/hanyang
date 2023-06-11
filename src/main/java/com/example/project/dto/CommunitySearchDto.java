package com.example.project.dto;


import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Setter
@Getter
@ToString
public class CommunitySearchDto {
    private int draw;

    private int start;

    private int length;

}
