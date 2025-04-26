package com.wmk.mood.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.wmk.mood.model.domain.Moodrecord;
import com.wmk.mood.model.domain.User;

import java.util.List;

/**
* @author 16512
* @description 针对表【moodrecord】的数据库操作Service
* @createDate 2025-04-14 09:31:34
*/
public interface MoodrecordService extends IService<Moodrecord> {
    /**
     * 情绪记录
     *
     * @param record
     * @param loginUser
     * @return
     */
    long moodRecord(Moodrecord record, User loginUser);

    /**
     * 获取当前用户情绪记录
     * @param userId
     * @return
     */
    List<Moodrecord> getMoodRecord(long userId);

    /**
     * 更改记录（仅限记录当天修改）
     *
     * @param record
     * @param loginUser
     * @return
     */
    int updateRecord(Moodrecord record, User loginUser);

    /**
     * 删除记录
     * @param recordId
     * @param loginUser
     * @return
     */
    int deleteRecord(long recordId, User loginUser);

    /**
     * 判断用户记录和登录状态是否合法
     *
     * @param record
     * @param loginUser
     * @return
     */
    boolean isOK(Moodrecord record, User loginUser);
}
