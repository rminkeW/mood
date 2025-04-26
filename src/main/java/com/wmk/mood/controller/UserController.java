package com.wmk.mood.controller;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.wmk.mood.common.BaseResponse;
import com.wmk.mood.common.ErrorCode;
import com.wmk.mood.common.ResultUtils;
import com.wmk.mood.exception.BusinessException;
import com.wmk.mood.model.domain.User;
import com.wmk.mood.model.request.UserLoginRequest;
import com.wmk.mood.model.request.UserRegisterRequest;
import com.wmk.mood.service.UserService;
import jakarta.annotation.Resource;
import jakarta.servlet.http.HttpServletRequest;
import org.apache.commons.lang3.StringUtils;
import org.springframework.web.bind.annotation.*;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import static com.wmk.mood.constant.UserConstant.USER_LOGIN_STATE;
import static com.wmk.mood.constant.UserConstant.ADMIN_ROLE;
import static com.wmk.mood.constant.UserConstant.USER_LOGIN_STATE;

/**
 * 用户接口
 *
 * @author wmk
 */

@RestController
@RequestMapping("/user")
@CrossOrigin()
public class UserController {
    @Resource
    private UserService userService;

    @PostMapping("/register")
    public Long userRegister(@RequestBody UserRegisterRequest userRegisterRequest) {
        if (userRegisterRequest == null) {
            throw new BusinessException(ErrorCode.NULL_ERROR);
        }
        String userAccount = userRegisterRequest.getUserAccount();
        String userPassword = userRegisterRequest.getUserPassword();
        String checkPassword = userRegisterRequest.getCheckPassword();
        if (StringUtils.isAnyBlank(userAccount, userPassword, checkPassword)) {
            throw new BusinessException(ErrorCode.PARAMS_ERROR);
        }
        return userService.userRegister(userAccount, userPassword, checkPassword);
    }

    @PostMapping("/login")
    public BaseResponse<User> userLogin(@RequestBody UserLoginRequest userLoginRequest, HttpServletRequest request) {
        if (userLoginRequest == null) {
            throw new BusinessException(ErrorCode.NULL_ERROR);
        }
        String userAccount = userLoginRequest.getUserAccount();
        String userPassword = userLoginRequest.getUserPassword();
        if (StringUtils.isAnyBlank(userAccount, userPassword)) {
            throw new BusinessException(ErrorCode.PARAMS_ERROR);
        }
        User user=userService.userLogin(userAccount,userPassword,request);
        return ResultUtils.success(user);
    }

    @PostMapping("/logout")
    public BaseResponse<Integer> userLogout(HttpServletRequest request) {
        if (request == null) {
            throw new BusinessException(ErrorCode.PARAMS_ERROR);
        }
        int result = userService.userLogout(request);
        return ResultUtils.success(result);
    }

    @PostMapping("/seach")
    public List<User> userSeach(String username, HttpServletRequest request) {
        boolean b = !isAdmin(request);
        if(b){
            return new ArrayList<>();
        }
        QueryWrapper<User> queryWrapper = new QueryWrapper<>();
        if (StringUtils.isNotBlank(username)) {
            queryWrapper.like("username", username);
        }
        List<User> userList = userService.list(queryWrapper);
        return userList.stream().map(user -> userService.getSaftUser(user)).collect(Collectors.toList());
    }

    @PostMapping("/update")
    public int updateUser(@RequestBody User user, HttpServletRequest request) {
        // 校验参数是否为空
        if (user == null) {
            throw new BusinessException(ErrorCode.PARAMS_ERROR);
        }
        User loginUser = userService.getLoginUser(request);
        int result = userService.updateUser(user, loginUser);
        return result;
    }

    @PostMapping("/current")
    public User getCurrentUser(HttpServletRequest request) {
        Object userObj = request.getSession().getAttribute(USER_LOGIN_STATE);
        User currentUser=(User) userObj;
        if (currentUser == null) {
            throw new BusinessException(ErrorCode.NOT_LOGIN);
        }
        long userId = currentUser.getUserId();
        User user = userService.getById(userId);
        User saftUser = userService.getSaftUser(user);
        return saftUser;
    }

    @PostMapping("/delete")
    public boolean deleteSeach(@RequestBody long id,HttpServletRequest request){
        if (!isAdmin(request)){
            return false;
        }
        if (id<=0){
            return false;
        }
        return userService.removeById(id);
    }

    /**
     * 是否为管理员
     *
     * @param request
     * @return
     */
    private boolean  isAdmin(HttpServletRequest request){
        //鉴权，仅管理员可查询
        Object userObj = request.getSession().getAttribute(USER_LOGIN_STATE);
        User user =(User) userObj;
        return user!=null&&user.getUserRole()==ADMIN_ROLE;

    }
}
