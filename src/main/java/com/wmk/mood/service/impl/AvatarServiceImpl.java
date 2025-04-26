package com.wmk.mood.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.wmk.mood.common.ErrorCode;
import com.wmk.mood.exception.BusinessException;
import com.wmk.mood.mapper.AvatarMapper;
import com.wmk.mood.model.domain.Avatar;
import com.wmk.mood.model.domain.User;
import com.wmk.mood.service.AvatarService;
import jakarta.annotation.Resource;
import org.springframework.stereotype.Service;

import java.util.List;

/**
* @author 16512
* @description 针对表【avatar】的数据库操作Service实现
* @createDate 2025-04-14 00:13:27
*/
@Service
public class AvatarServiceImpl extends ServiceImpl<AvatarMapper, Avatar>
    implements AvatarService{

    @Resource
    private AvatarMapper avatarMapper;

    @Override
    public long avatarCreate(Avatar avatar, User loginUser) {
        if (loginUser == null) {
            throw new BusinessException(ErrorCode.NOT_LOGIN);
        }
        if (avatar == null) {
            throw new BusinessException(ErrorCode.NULL_ERROR);
        }
        String avatarName = avatar.getAvatarName();
        String avatarCharacter = avatar.getAvatarCharacter();
        if (avatarName == null || avatarCharacter == null) {
            throw new BusinessException(ErrorCode.PARAMS_ERROR);
        }
        long userId = loginUser.getUserId();
        avatar.setUserId(userId);
        avatarMapper.insert(avatar);
        return avatar.getAvatarId();
    }

    @Override
    public List<Avatar> searchAvatarListById(long id) {
        if (id <= 0) {
            throw new BusinessException(ErrorCode.NULL_ERROR);
        }
        //不进行JSON转换
        QueryWrapper<Avatar> avatarQueryWrapper = new QueryWrapper<>();
        avatarQueryWrapper.eq("user_id", id);
        List<Avatar> avatarList = avatarMapper.selectList(avatarQueryWrapper);

        return avatarList;
    }

    @Override
    public int avatarDelete(long id, User loginUser) {
        long userId = loginUser.getUserId();
        Avatar avatar = avatarMapper.selectById(id);
        if (avatar==null){
            throw new BusinessException(ErrorCode.NULL_ERROR);
        }
        if (userId != avatar.getUserId()) {
            throw new BusinessException(ErrorCode.NO_AUTH);
        }
        return avatarMapper.deleteById(id);
    }
}




