package com.example.project.controller;

import com.example.project.dto.*;
import com.example.project.service.CommunityService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.validation.ObjectError;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import java.util.List;

@Slf4j
@Controller
@RequiredArgsConstructor
@RequestMapping("/community")
public class CommunityController {

    private final CommunityService communityService;

    /**
     * @Method community
     * @Author: shinhunwoo(신헌우)
     * @Description: 커뮤니티 게시판 조회
     */
    @GetMapping("")
    public ModelAndView community(@RequestParam(defaultValue = "") String message){

        ModelAndView mView = new ModelAndView();
        if(message.equals("success")){
            mView.addObject("message","정상 등록 처리되었습니다.");
        }
        if(message.equals("edit")){
            mView.addObject("message","정상 수정 처리되었습니다.");
        }
        mView.setViewName("community");

        return mView;
    }

    /**
     * @Method communitySaveForm
     * @Author: shinhunwoo(신헌우)
     * @Description: 커뮤니티 게시판 등록폼 이동
     */
    @GetMapping("/save-form")
    public ModelAndView communitySaveForm(){
        ModelAndView mView = new ModelAndView();
        mView.setViewName("community-save");
        mView.addObject("communitySaveDto",new CommunitySaveDto());
        return mView;
    }

    /**
     * @Method communitySave
     * @Author: shinhunwoo(신헌우)
     * @Description: 커뮤니티 게시판 등록 기능
     */
    @PostMapping("/save")
    public String communitySave(@ModelAttribute @Validated CommunitySaveDto communitySaveDto, BindingResult bindingResult){
        //복합룰 적용
        if(!communitySaveDto.getPwd().equals(communitySaveDto.getPwdCheck())){
            bindingResult.addError(new ObjectError("passwordCheckError", "비밀번호가 일치하지 않습니다."));
        }

        if(bindingResult.hasErrors()){
            List<FieldError> fieldErrors = bindingResult.getFieldErrors();

            for(FieldError errorMsg : fieldErrors) {
                log.info("errorMsgCode={}", errorMsg.getField());
                log.info("errorMsg={}", errorMsg.getDefaultMessage());
            }
            return "community-save";
        }
        communityService.communitySave(communitySaveDto);
        return "redirect:/community?message=success";
    }

    /**
     * @Method communityEditForm
     * @Author: shinhunwoo(신헌우)
     * @Description: 커뮤니티 게시판 수정폼 이동
     */
    @GetMapping("/edit/{id}")
    public ModelAndView communityEditForm(@PathVariable Integer id){
        ModelAndView mView = new ModelAndView();
        CommunityDto communityDto = communityService.getCommunity(id);
        mView.setViewName("community-edit");
        mView.addObject("communityDto", communityDto);
        return mView;
    }

    /**
     * @Method communityEdit
     * @Author: shinhunwoo(신헌우)
     * @Description: 커뮤니티 게시판 수정 기능
     */
    @PostMapping("/edit/{id}")
    public String communityEdit(@PathVariable Integer id
            , @ModelAttribute("communityDto") @Validated CommunityEditDto communityEditDto
            , BindingResult bindingResult){

        if(bindingResult.hasErrors()){
            List<FieldError> fieldErrors = bindingResult.getFieldErrors();

            for(FieldError errorMsg : fieldErrors) {
                log.info("errorMsgCode={}", errorMsg.getField());
                log.info("errorMsg={}", errorMsg.getDefaultMessage());
            }
            return  "redirect:/community/edit/" + String.valueOf(id);
        }
        communityService.communityEdit(id,communityEditDto);
        return "redirect:/community?message=edit";
    }

    /**
     * @Method communityList
     * @Author: shinhunwoo(신헌우)
     * @Description: 커뮤니티 게시판 리스트 조회
     */
    @GetMapping("/list")
    @ResponseBody
    public ResponseEntity<?> communityList(@ModelAttribute CommunitySearchDto communitySearchDto){

        DataTableDto dataTableDto = communityService.getCommunityList(communitySearchDto);

        return new ResponseEntity<>(new ResponseDto<>(HttpStatus.OK.value(),"커뮤니티 게시판 조회 성공", dataTableDto), HttpStatus.OK);
    }

    /**
     * @Method passwordCheck
     * @Author: shinhunwoo(신헌우)
     * @Description: 커뮤니티 게시판 글 수정, 삭제를 위한 게시글 비밀번호 조회
     */
    @PostMapping("/password-check")
    @ResponseBody
    public ResponseEntity<?> passwordCheck(@ModelAttribute PasswordCheckDto passwordCheckDto){
        ResponseDto responseDto = communityService.passwordCheck(passwordCheckDto);

        return new ResponseEntity<>(new ResponseDto<>(responseDto.getStatus(), responseDto.getMessage(), responseDto.getData()), HttpStatus.OK);
    }

    /**
     * @Method communityDetail
     * @Author: shinhunwoo(신헌우)
     * @Description: 커뮤니티 게시판 글 상세페이지 조회
     */
    @GetMapping("/{id}")
    public ModelAndView communityDetail(@PathVariable Integer id){
        ModelAndView mView = new ModelAndView();
        CommunityDto communityDto = communityService.getCommunity(id);
        mView.setViewName("community-detail");
        mView.addObject("communityDto", communityDto);

        return mView;
    }

    /**
     * @Method deleteCommunity
     * @Author: shinhunwoo(신헌우)
     * @Description: 커뮤니티 게시판 글 삭제
     */
    @PatchMapping("/delete-community")
    @ResponseBody
    public ResponseEntity<?> deleteCommunity(@RequestParam Integer id){
        //row삭제는 하지 않고 상태 값 만 수정함
        communityService.deleteCommunity(id);

        return new ResponseEntity<>(new ResponseDto<>(HttpStatus.OK.value(),"게시글 삭제 완료", id), HttpStatus.OK);
    }



}
