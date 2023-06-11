package com.example.project.dto;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import org.springframework.web.multipart.MultipartFile;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

@Setter
@Getter
@ToString
public class RestaurantEditDto {

    private Integer id;

    @NotBlank(message = "식당명을 입력해 주세요.")
    @Size(max = 100, message = "최대 길이가 초과되었습니다.")
    private String title;

    @NotBlank(message = "등록할 카테고리를 입력해 주세요.")
    @Size(max = 1, message = "최대 길이가 초과되었습니다.")
    private String category;

    @NotBlank(message = "주소를 입력해 주세요.")
    @Size(max = 255, message = "최대 길이가 초과되었습니다.")
    private String address;

    @NotBlank(message = "내용을 입력해 주세요.")
    private String content;

    private MultipartFile file; //수정을 안하는 경우도 있기 떄문에 사용함


    private String changeName; //기존 파일 저장명
}
