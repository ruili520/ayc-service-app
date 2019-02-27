
package com.ayc.service.app.service;

import com.ayc.service.app.dto.TestDto;
import com.ayc.service.app.entity.TestEntity;
import com.github.pagehelper.PageInfo;

/**
 * Author:  ysj
 * Date:  2019/1/15 17:42
 * Description:
 */

public interface TestSevice {
    PageInfo<TestEntity> reTest(Integer rCashId, TestDto dto);
    void testRedis(TestDto dto);
    void test3rd();

    String getToken();
}