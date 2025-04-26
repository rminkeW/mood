package com.wmk.mood.controller;

import com.wmk.mood.common.BaseResponse;
import com.wmk.mood.common.ErrorCode;
import com.wmk.mood.common.ResultUtils;
import com.wmk.mood.exception.BusinessException;
import com.wmk.mood.model.domain.Moodrecord;
import com.wmk.mood.model.domain.User;
import com.wmk.mood.model.request.emotionCreateRequest;
import com.wmk.mood.service.MoodrecordService;
import com.wmk.mood.service.UserService;
import jakarta.annotation.Resource;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.web.bind.annotation.*;


import java.util.List;

@RestController
@RequestMapping("/emotion")
@CrossOrigin()
public class emotionController {

    @Resource
    private UserService userService;

    @Resource
    private MoodrecordService moodrecordService;

    @PostMapping("/save")
    public Long recordCreat(@RequestBody emotionCreateRequest createRequest, HttpServletRequest request){
        if (createRequest == null){
            throw new BusinessException(ErrorCode.NULL_ERROR);
        }
        if (request == null){
            throw new BusinessException(ErrorCode.NOT_LOGIN);
        }
        User loginUser = userService.getLoginUser(request);
        if (loginUser == null){
            throw new BusinessException(ErrorCode.NOT_LOGIN);
        }
        String emotion = createRequest.getEmotion();
        String recordTime = createRequest.getRecordTime();
        if (recordTime == null||emotion == null){
            throw new BusinessException(ErrorCode.NULL_ERROR);
        }
//        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy");
//        LocalDate recordTime = LocalDate.parse(time, formatter);
        long userId = loginUser.getUserId();
        Moodrecord moodrecord = new Moodrecord();
        moodrecord.setEmotion(emotion);
        moodrecord.setRecordTime(recordTime);
        moodrecord.setUserId(userId);
        long l = moodrecordService.moodRecord(moodrecord, loginUser);
        return l;
    }

    @GetMapping("/list")
    public BaseResponse<List<Moodrecord>> searchMoodrecordById(HttpServletRequest request){
        if (request == null) {
            throw new BusinessException(ErrorCode.NOT_LOGIN);
        }
        long userId = userService.getLoginUser(request).getUserId();
        List<Moodrecord> MoodrecordList = moodrecordService.getMoodRecord(userId);
        return ResultUtils.success(MoodrecordList);
    }

    @GetMapping("/delete")
    public BaseResponse deleteMoodrecordById(long MoodrecordId,HttpServletRequest request){
        if (request == null) {
            throw new BusinessException(ErrorCode.NOT_LOGIN);
        }
        User loginUser = userService.getLoginUser(request);
        int i = moodrecordService.deleteRecord(MoodrecordId, loginUser);
        return ResultUtils.success(i);
    }
}