package com.example.project.dto;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;

@Setter
@Getter
@ToString
public class CommunityEditDto {
    private Integer id;

    @NotBlank(message = "제목을 입력해 주세요.")
    @Size(min= 4, max = 100, message = "길이가 유효하지 않습니다. 최소길이 4글자 최대 100글자")
    private String subject;

    @NotBlank(message = "내용을 입력해 주세요.")
    private String content;
}
