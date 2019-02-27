
package com.ayc.service.app.dao.mapper;

import com.ayc.service.app.entity.TestEntity;
import com.ayc.framework.dao.IBaseMapper;

import java.util.List;

/**
 * Author:  ysj
 * Date:  2019/1/17 12:46
 * Description:
 */
public interface TestMapper extends IBaseMapper{
    List<TestEntity> queyTest();
}