package com.example.project.dto;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;

@Setter
@Getter
@ToString
public class CommunitySaveDto {
    private Integer id;

    @NotBlank(message = "제목을 입력해 주세요.")
    @Size(min= 4, max = 100, message = "길이가 유효하지 않습니다. 최소길이 4글자 최대 100글자")
    private String subject;

    @NotBlank(message = "내용을 입력해 주세요.")
    private String content;

    @NotBlank(message = "비밀번호를 입력해 주세요.")
    @Size(min = 4, max = 255, message = "비밀번호 길이가 유효하지 않습니다. 최소 4글자 이상")
    private String pwd;

    @NotBlank(message = "비밀번호를 입력해 주세요.")
    @Size(min = 4, max = 255, message = "비밀번호 길이가 유효하지 않습니다. 최소 4글자 이상")
    private String pwdCheck;

    @NotBlank(message = "작성자명을 입력해 주세요.")
    @Size(max = 50, message = "최대 길이가 초과되었습니다.")
    private String writer;
}
