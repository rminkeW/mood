package com.wmk.mood.controller;

import com.wmk.mood.common.BaseResponse;
import com.wmk.mood.common.ErrorCode;
import com.wmk.mood.common.ResultUtils;
import com.wmk.mood.exception.BusinessException;
import com.wmk.mood.model.domain.Avatar;
import com.wmk.mood.model.domain.User;
import com.wmk.mood.model.request.AvatarCreateRequest;
import com.wmk.mood.service.AvatarService;
import com.wmk.mood.service.UserService;
import jakarta.annotation.Resource;
import jakarta.servlet.http.HttpServletRequest;
import org.apache.commons.lang3.StringUtils;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/avatar")
@CrossOrigin()
public class AvatarController {
    @Resource
    private AvatarService avatarService;

    @Resource
    private UserService userService;

    @PostMapping("/create")
    public Long avatarCreate(@RequestBody AvatarCreateRequest avatarCreateRequest, HttpServletRequest request) {
        if (avatarCreateRequest == null) {
            throw new BusinessException(ErrorCode.NULL_ERROR);
        }
        String avatarName = avatarCreateRequest.getAvatarName();
        String character = avatarCreateRequest.getCharacter();
        if (StringUtils.isAnyBlank(avatarName, character)) {
            throw new BusinessException(ErrorCode.PARAMS_ERROR);
        }
        Avatar avatar=new Avatar();
        avatar.setAvatarName(avatarName);
        avatar.setAvatarCharacter(character);
        User loginUser = userService.getLoginUser(request);
        return avatarService.avatarCreate(avatar,loginUser);
    }

    @GetMapping("/list")
    public BaseResponse<List<Avatar>> searchAvatarById(HttpServletRequest request){
        if (request == null) {
            throw new BusinessException(ErrorCode.NOT_LOGIN);
        }
        long userId = userService.getLoginUser(request).getUserId();
        List<Avatar> avatarList = avatarService.searchAvatarListById(userId);
        return ResultUtils.success(avatarList);
    }

    @GetMapping("/delete")
    public BaseResponse deleteAvatarById(long avatarId,HttpServletRequest request){
        if (request == null) {
            throw new BusinessException(ErrorCode.NOT_LOGIN);
        }
        User loginUser = userService.getLoginUser(request);
        int i = avatarService.avatarDelete(avatarId, loginUser);
        return ResultUtils.success(i);
    }
}
