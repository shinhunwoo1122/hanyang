package com.example.project.service.impl;

import com.example.project.dto.*;
import com.example.project.mapper.RestaurantMapper;
import com.example.project.service.RestaurantService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.core.io.Resource;
import org.springframework.core.io.ResourceLoader;
import org.springframework.http.HttpStatus;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

@Slf4j
@Service
@RequiredArgsConstructor
public class RestaurantServiceImpl implements RestaurantService {

    private final RestaurantMapper restaurantMapper;

    private final PasswordEncoder passwordEncoder;

    private final ResourceLoader resourceLoader;

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

   /* @Override
    public boolean passwordCheck(Integer id, String pwd) {
        //식당등록시 생긴 암호화된 비밀번호 조회
        //String encodePwd = restaurantMapper.getEncodePwd(id);
        //단방향 암호 Sha512일치 여부 조회함
        //boolean valid = passwordEncoder.matches(pwd,encodePwd);
       // return valid;
    }*/

    @Override
    public void restaurantEdit(Integer id, RestaurantEditDto restaurantEditDto) throws Exception {
        restaurantEditDto.setId(id);
        MultipartFile file = restaurantEditDto.getFile();
        //파일이 비어있지 않은 경우임 수정시 비어있는 경우 파일유지 추가된경우 변경된 사항

        System.out.println("file.isEmpty() = " + file.isEmpty());

        if(!file.isEmpty()){
            //기존 파일 저장 DB상태 값 변경함, delete대신 update사용
            restaurantMapper.fileEditStatus(restaurantEditDto.getId());

            //원래 경로에서 파일삭제 처리 진행
            //스토리지 서버가 따로없고 로컬에 파일 저장하므로 파일이 존재하지 않는 경우가 있기때문에 DB값 먼저 수정함
            //파일삭제 실패시 GlobalExceptionHandler Handler 오류 log처리
            Resource resource = resourceLoader.getResource("classpath:static");
            String staticPath = resource.getURL().getPath();
            String filePath = staticPath + "/uploadFiles/";

            log.info("filePath={}", filePath);

            File orgFile = new File(filePath + restaurantEditDto.getChangeName());
            if(orgFile.exists()){
                if(orgFile.delete()){
                    log.info("파일삭제 완료");
                }else{
                    log.info("파일삭제 실패");
                }
            }else{
                log.info("파일이 존재하지 않습니다.");
            }


            //변경된 이미지 저장 처리
            String originFileName = file.getOriginalFilename();

            System.out.println("originFileName = " + originFileName);

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

    @Override
    public List<RestaurantDto> getRestaurants(Integer category) {
        return restaurantMapper.getRestaurants(category);
    }

    @Override
    public ResponseDto passwordCheck(PasswordCheckDto passwordCheckDto) {

        String password = passwordCheckDto.getPwd();
        ResponseDto<Boolean> responseDto = new ResponseDto<>();
        Boolean valid = false;

        if(password.isBlank()){ //비밀번호 미 입력시 바로 돌려보냄
            log.info("비밀번호 미 입력");
            responseDto.setMessage("비밀번호를 입력해 주세요.");
            responseDto.setStatus(HttpStatus.BAD_REQUEST.value());
            responseDto.setData(valid);
            return responseDto;
        }

        //식당등록시 생긴 암호화된 비밀번호 조회
        String encodePwd = restaurantMapper.getEncodePwd(passwordCheckDto);

        valid = passwordEncoder.matches(passwordCheckDto.getPwd(), encodePwd);

        if(valid){
            responseDto.setMessage("비밀번호 정상 확인");
            responseDto.setStatus(HttpStatus.OK.value());
            responseDto.setData(valid);
        }else{
            log.info("비밀번호 미 일치");
            responseDto.setMessage("비밀번호가 일치하지 않습니다.");
            responseDto.setStatus(HttpStatus.BAD_REQUEST.value());
            responseDto.setData(valid);
        }

        return responseDto;
    }

    @Override
    public void deleteRestaurant(Integer id) {
        restaurantMapper.deleteRestaurant(id);
    }
}
