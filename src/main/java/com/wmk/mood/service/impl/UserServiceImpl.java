package com.wmk.mood.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.wmk.mood.common.ErrorCode;
import com.wmk.mood.constant.UserConstant;
import com.wmk.mood.exception.BusinessException;
import com.wmk.mood.mapper.UsersMapper;
import com.wmk.mood.model.domain.User;
import com.wmk.mood.service.UserService;
import jakarta.servlet.http.HttpServletRequest;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Service;
import org.springframework.util.DigestUtils;

import java.time.LocalDateTime;
import java.util.regex.Pattern;

import static com.wmk.mood.constant.UserConstant.USER_LOGIN_STATE;

/**
 * 用户服务实现类
 *
 * @author 16512
 * @description 针对表【users】的数据库操作Service实现
 * @createDate 2025-02-08 20:15:08
 */
@Slf4j
@Service
public class UserServiceImpl extends ServiceImpl<UsersMapper, User>
    implements UserService {

    //盐
    private static final String SALT = "MelodyMessenger";
    // 正则表达式用于匹配密码是否包含特殊字符
    private static final String PASSWORD_REGEX = "^[a-zA-Z0-9]+$";
    private static final Pattern PASSWORD_PATTERN = Pattern.compile(PASSWORD_REGEX);
    private UsersMapper usersMapper;



    public UserServiceImpl(UsersMapper usersMapper) {
        this.usersMapper = usersMapper;
    }

    @Override
    public long userRegister(String userAccount, String userPassword, String checkPassword) {
        //1.校验
        //非空
        if(StringUtils.isAnyBlank(userAccount, userPassword, checkPassword)){
            return -1;
        }
        //账户 不能小于4或大于12
        if (userAccount.length()<4||userAccount.length()>12){
            return -1;
        }
        //密码和验证必须一样，长度在 8——16
        if (userPassword.length()<8||checkPassword.length()<8||checkPassword.length()>16||userPassword.length()>16){
            return -1;
        }
        //数据不能重复
        QueryWrapper<User> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("user_account", userAccount);
        long count = usersMapper.selectCount(queryWrapper);
        if (count > 0){
            return -1;
        }
        // 检查密码是否包含特殊字符
        if (!PASSWORD_PATTERN.matcher(userPassword).matches()) {
            return -1;
        }
        // 检查确认密码是否与密码一致
        if (!userPassword.equals(checkPassword)) {
            return -1;
        }
        //2.加密
        String encryptPassword = DigestUtils.md5DigestAsHex((SALT + userPassword).getBytes());
        //3.插入数据
        User user = new User();
        user.setUserAccount(userAccount);
        user.setUserPassword(encryptPassword);
        boolean saveResult = this.save(user);
        if(!saveResult){
            return -1;
        }
        return user.getUserId();
    }

    @Override
    public User userLogin(String userAccount, String userPassword, HttpServletRequest request) {
        //1.校验
        //非空
        if(StringUtils.isAnyBlank(userAccount, userPassword)){
            return null;
        }
        //账户 不能小于4或大于12
        if (userAccount.length()<4||userAccount.length()>12){
            return null;
        }
        //密码长度在 8——16
        if (userPassword.length()<8||userPassword.length()>16){
            return null;
        }

        // 检查密码是否包含特殊字符
        if (!PASSWORD_PATTERN.matcher(userPassword).matches()) {
            return null;
        }

        //2.加密并查询数据
        String encryptPassword = DigestUtils.md5DigestAsHex((SALT + userPassword).getBytes());
        QueryWrapper<User> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("user_account", userAccount);
        queryWrapper.eq("user_password", encryptPassword);
        User users = usersMapper.selectOne(queryWrapper);
        if (users == null){
            log.info("user login failed,userAccount cannot match userPassword");
            return null;
        }

        //3.用户脱敏
        User safetyUser=getSaftUser(users);

        //4.记录用户的登录态
        request.getSession().setAttribute(USER_LOGIN_STATE,safetyUser);


        return safetyUser;
    }

    @Override
    public User getLoginUser(HttpServletRequest request) {
        if (request == null){
            return null;
        }
        Object userObject = request.getSession().getAttribute(USER_LOGIN_STATE);
        if (userObject == null){
            throw new BusinessException(ErrorCode.NO_AUTH);
        }
        return (User) userObject;
    }

    @Override
    public int userLogout(HttpServletRequest request) {
        //移除用户登录态
        request.getSession().removeAttribute(USER_LOGIN_STATE);
        return 1;
    }

    /**
     * 用户脱敏
     * @param originUser
     * @return
     */
    @Override
    public User getSaftUser(User originUser){
        User safetyUser=new User();
        safetyUser.setUserId(originUser.getUserId());
        safetyUser.setUserAccount(originUser.getUserAccount());
        safetyUser.setUsername(originUser.getUsername());
        safetyUser.setAvatarUrl(originUser.getAvatarUrl());
        safetyUser.setUserRole(originUser.getUserRole());
        safetyUser.setCreateTime(LocalDateTime.now());
        return safetyUser;
    }

    @Override
    public int updateUser(User user, User loginUser) {
        long userId = user.getUserId();
        if (userId<=0){
            throw new BusinessException(ErrorCode.PARAMS_ERROR);
        }
        if (!isAdmin(loginUser)&&userId!=loginUser.getUserId()){
            throw new BusinessException(ErrorCode.NO_AUTH);
        }
        User oldUser = usersMapper.selectById(userId);
        if (oldUser==null){
            throw new BusinessException(ErrorCode.NULL_ERROR);
        }
        return usersMapper.updateById(user);
    }

    @Override
    public boolean isAdmin(User loginUser) {
        return loginUser!=null&&loginUser.getUserRole()== UserConstant.ADMIN_ROLE;
    }
}




