package com.example.project.mapper;

import com.example.project.dto.*;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface CommunityMapper {
    void communitySave(CommunitySaveDto communitySaveDto);

    int getCommunityCount(CommunitySearchDto communitySearchDto);

    List<CommunityDto> getCommunityList(CommunitySearchDto communitySearchDto);

    String getCommunityPwd(PasswordCheckDto passwordCheckDto);

    void deleteCommunity(Integer id);

    CommunityDto getCommunity(Integer id);

    void communityEdit(CommunityEditDto communityEditDto);
}
