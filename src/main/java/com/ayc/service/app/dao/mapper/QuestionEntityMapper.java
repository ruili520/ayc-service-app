package com.ayc.service.app.dao.mapper;

import com.ayc.framework.dao.IBaseMapper;
import com.ayc.service.app.entity.QuestionEntity;
import com.ayc.service.app.entity.QuestionEntityWithBLOBs;

import java.util.List;

public interface QuestionEntityMapper extends IBaseMapper {
    int deleteByPrimaryKey(Integer id);

    int insert(QuestionEntityWithBLOBs record);

    int insertSelective(QuestionEntityWithBLOBs record);

    QuestionEntityWithBLOBs selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(QuestionEntityWithBLOBs record);

    int updateByPrimaryKeyWithBLOBs(QuestionEntityWithBLOBs record);

    int updateByPrimaryKey(QuestionEntity record);

    /**
     * 获取问答列表
     * @return
     */
    List<QuestionEntity> listQuestions();
}