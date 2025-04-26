package com.wmk.mood.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.wmk.mood.model.domain.User;
import jakarta.servlet.http.HttpServletRequest;


/**
* @author 16512
* @description 针对表【users】的数据库操作Service
* @createDate 2025-02-08 20:15:08
*/
public interface UserService extends IService<User> {



    /**
     * 用户注册
     *
     * @param userAccount 用户账户
     * @param userPassword 用户密码
     * @param checkPassword 校验密码
     * @return 新用户id
     */
    long userRegister(String userAccount, String userPassword, String checkPassword);

    /**
     * 用户登录
     *
     * @param userAccount 用户账户
     * @param userPassword 用户密码
     * @return 返回脱敏后的用户信息
     */
    User userLogin(String userAccount, String userPassword, HttpServletRequest request);

    /**
     * 获取当前登录用户信息
     * @param request
     * @return
     */
    User getLoginUser(HttpServletRequest request);

    /**
     * 用户注销
     *
     * @param request
     * @return
     */
    int userLogout(HttpServletRequest request);

    /**
     * 用户脱敏
     * @param originUser
     * @return wmk
     */
    User getSaftUser(User originUser);

    /**
     * 更改用户信息
     *
     * @param loginUser
     * @return
     */
    int updateUser(User user,User loginUser);

    /**
     * 是否为管理员
     *
     * @param loginUser
     * @return
     */
    boolean isAdmin(User loginUser);
}
