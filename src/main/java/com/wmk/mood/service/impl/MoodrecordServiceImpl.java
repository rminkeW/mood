package com.wmk.mood.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.wmk.mood.common.ErrorCode;
import com.wmk.mood.exception.BusinessException;
import com.wmk.mood.mapper.MoodrecordMapper;
import com.wmk.mood.model.domain.Moodrecord;
import com.wmk.mood.model.domain.User;
import com.wmk.mood.service.MoodrecordService;
import jakarta.annotation.Resource;
import org.springframework.stereotype.Service;

import java.util.List;

/**
* @author 16512
* @description 针对表【moodrecord】的数据库操作Service实现
* @createDate 2025-04-14 09:31:34
*/
@Service
public class MoodrecordServiceImpl extends ServiceImpl<MoodrecordMapper, Moodrecord>
    implements MoodrecordService{

    @Resource
    private MoodrecordMapper moodrecordMapper;

    @Override
    public long moodRecord(Moodrecord record, User loginUser) {
        boolean ok = isOK(record, loginUser);
        if (!ok){
            throw new BusinessException(ErrorCode.PARAMS_ERROR);
        }
        String time = record.getRecordTime();
        Long count = moodrecordMapper.selectCount(new QueryWrapper<Moodrecord>().eq("record_time", time));
        if (count >= 1){
            throw new BusinessException(ErrorCode.PARAMS_ERROR);
        }
        long userId = loginUser.getUserId();
        record.setUserId(userId);
        int insert = moodrecordMapper.insert(record);
        return insert;
    }

    @Override
    public List<Moodrecord> getMoodRecord(long userId) {
        if (userId <= 0) {
            throw new BusinessException(ErrorCode.NULL_ERROR);
        }
        //不进行JSON转换
        QueryWrapper<Moodrecord> MoodrecordQueryWrapper = new QueryWrapper<>();
        MoodrecordQueryWrapper.eq("user_id", userId);
        List<Moodrecord> MoodrecordList = moodrecordMapper.selectList(MoodrecordQueryWrapper);
        return MoodrecordList;
    }

    @Override
    public int updateRecord(Moodrecord record, User loginUser) {
        boolean ok = isOK(record, loginUser);
        if (!ok){
            throw new BusinessException(ErrorCode.PARAMS_ERROR);
        }
        return moodrecordMapper.updateById(record);
    }

    @Override
    public int deleteRecord(long recordId, User loginUser) {
        Moodrecord moodrecord = moodrecordMapper.selectById(recordId);
        long userId = moodrecord.getUserId();
        if (userId != loginUser.getUserId()){
            throw new BusinessException(ErrorCode.PARAMS_ERROR);
        }
        return moodrecordMapper.deleteById(recordId);
    }

    @Override
    public boolean isOK(Moodrecord record, User loginUser) {
        if (record==null){
            throw new BusinessException(ErrorCode.NULL_ERROR);
        }
        if (loginUser==null){
            throw new BusinessException(ErrorCode.NOT_LOGIN);
        }
        Long userrecord = record.getUserId();
        if (userrecord==null){
            throw new BusinessException(ErrorCode.PARAMS_ERROR);
        }
        return true;
    }
}




