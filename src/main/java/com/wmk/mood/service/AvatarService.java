package com.wmk.mood.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.wmk.mood.model.domain.Avatar;
import com.wmk.mood.model.domain.User;

import java.util.List;

/**
* @author 16512
* @description 针对表【avatar】的数据库操作Service
* @createDate 2025-04-14 00:13:27
*/
public interface AvatarService extends IService<Avatar> {

    /**
     * 创建分身
     * @param avatar
     * @param loginUser
     * @return
     */
    long avatarCreate(Avatar avatar, User loginUser);

    /**
     * 根据登录用户查询分身
     * @param id
     * @return
     */
    List<Avatar> searchAvatarListById(long id);

    /**
     * 删除用户分身
     * @param id
     * @return
     */
    int avatarDelete(long id,User loginUser);
}
