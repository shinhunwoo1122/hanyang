package com.example.project.service.impl;

import com.example.project.dto.FileDto;
import com.example.project.dto.RestaurantSaveDto;
import com.example.project.mapper.RestaurantMapper;
import com.example.project.service.RestaurantService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.core.io.Resource;
import org.springframework.core.io.ResourceLoader;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.util.UUID;

@Slf4j
@Service
@RequiredArgsConstructor
public class RestaurantServiceImpl implements RestaurantService {

    private final RestaurantMapper restaurantMapper;

    private final PasswordEncoder passwordEncoder;

    private final ResourceLoader resourceLoader;

    @Override
    public int getCount() {

        int count = restaurantMapper.getCount();

        String pwd = "1234G";
        String encodePwd = passwordEncoder.encode(pwd);

        log.info("encodingPwd={}", encodePwd);
        log.info("비밀번호확인={}", passwordEncoder.matches(pwd,encodePwd));
        log.info("비밀번호일부러틀리게={}",passwordEncoder.matches("1234C",encodePwd));

        return 0;
    }

    @Override
    @Transactional
    public boolean restaurantSave(RestaurantSaveDto restaurantSaveDto) throws Exception {

        //localhost 파일저장 경로 설정
        Resource resource = resourceLoader.getResource("classpath:static");
        String staticPath = resource.getURL().getPath();
        String filePath = staticPath + "/uploadFiles/";

        //비밀번호 암호화 처리
        restaurantSaveDto.setPwd(passwordEncoder.encode(restaurantSaveDto.getPwd()));

        //식당정보 등록
        restaurantMapper.restaurantSave(restaurantSaveDto);

        //파일 디렉토리 검색 없는 경우 생성
        File uploadDir = new File(filePath);
        if(!uploadDir.exists()){
            uploadDir.mkdirs();
        }

        //이미지 정보 주입
        MultipartFile file = restaurantSaveDto.getFile();
        String originFileName = file.getOriginalFilename();
        String ext = originFileName.substring(originFileName.lastIndexOf("."));
        Long size = file.getSize();
        String fileSize = String.valueOf(size);
        String changeName = UUID.randomUUID().toString() + ext;
        String fileUrl = filePath + "\\" + changeName;
        String url = "uploadFiles/" + changeName;

        FileDto fileDto = new FileDto();
        fileDto.setOrginName(originFileName);
        fileDto.setFileSize(fileSize);
        fileDto.setChangeName(changeName);
        fileDto.setFileUrl(url);
        fileDto.setRestaurantId(restaurantSaveDto.getId());

        restaurantMapper.restaurantImgSave(fileDto);

        file.transferTo(new File(filePath + "\\" + changeName));

        boolean isSuccess = true;


        return isSuccess;

    }
}
