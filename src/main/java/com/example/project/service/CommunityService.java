package com.example.project.service;

import com.example.project.dto.*;

public interface CommunityService {
    void communitySave(CommunitySaveDto communitySaveDto);

    DataTableDto getCommunityList(CommunitySearchDto communitySearchDto);

    ResponseDto passwordCheck(PasswordCheckDto passwordCheckDto);

    void deleteCommunity(Integer id);

    CommunityDto getCommunity(Integer id);

    void communityEdit(Integer id, CommunityEditDto communityEditDto);
}
