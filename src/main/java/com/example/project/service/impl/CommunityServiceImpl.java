package com.example.project.service.impl;

import com.example.project.dto.*;
import com.example.project.mapper.CommunityMapper;
import com.example.project.service.CommunityService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;
@Slf4j
@Service
@RequiredArgsConstructor
public class CommunityServiceImpl implements CommunityService {

    private final CommunityMapper communityMapper;

    private final PasswordEncoder passwordEncoder;

    @Override
    public void communitySave(CommunitySaveDto communitySaveDto){
        //게시글 비밀번호 암호화 처리
        communitySaveDto.setPwd(passwordEncoder.encode(communitySaveDto.getPwd()));
        //커뮤니티 게시글 등록
        communityMapper.communitySave(communitySaveDto);
    }

    @Override
    public DataTableDto getCommunityList(CommunitySearchDto communitySearchDto) {
        int totalRow = communityMapper.getCommunityCount(communitySearchDto);
        List<CommunityDto> list =  communityMapper.getCommunityList(communitySearchDto);

        DataTableDto dataTableDto = new DataTableDto();
        dataTableDto.setTotalRow(totalRow);
        dataTableDto.setContents(list);

        return dataTableDto;
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

        String encodePwd = communityMapper.getCommunityPwd(passwordCheckDto); //암호화된 비밀번호 조회
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
    public void deleteCommunity(Integer id) {
        communityMapper.deleteCommunity(id);
    }

    @Override
    public CommunityDto getCommunity(Integer id) {
        return communityMapper.getCommunity(id);
    }

    @Override
    public void communityEdit(Integer id, CommunityEditDto communityEditDto) {
        communityEditDto.setId(id);
        communityMapper.communityEdit(communityEditDto);
    }
}
