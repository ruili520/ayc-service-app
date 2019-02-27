package com.ayc.service.app.dao.mapper;

import com.ayc.framework.dao.IBaseMapper;
import com.ayc.service.app.entity.AycRealnameAuthEntity;

public interface AycRealnameAuthMapper extends IBaseMapper {
    int deleteByPrimaryKey(Integer id);

    int insert(AycRealnameAuthEntity record);

    int insertSelective(AycRealnameAuthEntity record);

    AycRealnameAuthEntity selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(AycRealnameAuthEntity record);

    int updateByPrimaryKey(AycRealnameAuthEntity record);

    AycRealnameAuthEntity selectByUid(Integer uid);
}