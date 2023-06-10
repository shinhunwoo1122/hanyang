package com.example.project.service.impl;

import com.example.project.dto.FileDto;
import com.example.project.dto.RestaurantDto;
import com.example.project.dto.RestaurantEditDto;
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
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
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
    public void restaurantSave(RestaurantSaveDto restaurantSaveDto) throws Exception {

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
    }

    @Override
    public RestaurantDto getRestaurant(Integer id) {
        return restaurantMapper.getRestaurant(id);
    }

    @Override
    public boolean passwordCheck(Integer id, String pwd) {
        //식당등록시 생긴 암호화된 비밀번호 조회
        String encodePwd = restaurantMapper.getEncodePwd(id);
        //단방향 암호 Sha512일치 여부 조회함
        boolean valid = passwordEncoder.matches(pwd,encodePwd);
        return valid;
    }

    @Override
    public void restaurantEdit(RestaurantEditDto restaurantEditDto) throws Exception {
        MultipartFile file = restaurantEditDto.getFile();
        //파일이 비어있지 않은 경우임 수정시 비어있는 경우 파일유지 추가된경우 변경된 사항
        if(!file.isEmpty()){
            //기존 파일 저장 DB상태 값 변경함, delete대신 update사용
            restaurantMapper.fileEditStatus(restaurantEditDto.getId());

            //원래 경로에서 파일삭제 처리 진행
            //스토리지 서버가 따로없고 로컬에 파일 저장하므로 파일이 존재하지 않는 경우가 있기때문에 DB값 먼저 수정함
            //파일삭제 실패시 GlobalExceptionHandler Handler 오류 log처리
            Resource resource = resourceLoader.getResource("classpath:static");
            String staticPath = resource.getURL().getPath();
            String path = staticPath + "/uploadFiles/";

            Path filePath = Paths.get(path + restaurantEditDto.getChangeName());
            Files.delete(filePath);

            //변경된 이미지 저장 처리
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
            fileDto.setRestaurantId(restaurantEditDto.getId());

            restaurantMapper.restaurantImgSave(fileDto);
            file.transferTo(new File(filePath + "\\" + changeName));
        }
        //식당 정보 업데이트 처리
        restaurantMapper.restaurantEdit(restaurantEditDto);
    }
}
