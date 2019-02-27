
package com.ayc.service.app.service.impl;

import com.alibaba.fastjson.JSON;
import com.ayc.service.api.service.AliyunPushService;
import com.ayc.service.app.dto.TestDto;
import com.ayc.service.app.service.TestSevice;
import com.ayc.service.app.dao.mapper.TestMapper;
import com.ayc.service.app.entity.TestEntity;
import com.ayc.framework.common.PageVo;
import com.ayc.framework.datasource.annotation.DataSource;
import com.ayc.framework.redis.RedisUtil;
import com.ayc.framework.util.TokenGenerator;
import com.ayc.service.app.security.AppSessionUser;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

/**
 * Author:  ysj
 * Date:  2019/1/15 17:44
 * Description:
 */
@Service("testSevice")
public class TestSeviceImpl implements TestSevice {
    private final static String GENERATOR_KEY = "APP_KEY_COUNTER";

    private final static String APPID = "APP";
    @Autowired
    private TestMapper testMapper;
    @Autowired
    private TokenGenerator tokenGenerator;
    @Autowired
    RedisUtil redisUtil;
    @Autowired
    private AliyunPushService aliyunPushService;


    @Override
    public PageInfo<TestEntity> reTest(@DataSource(field = "rCashId") Integer rCashId, TestDto dto) {
        PageVo<Map<String, Object>> pageVo = new PageVo<>();
        PageHelper.startPage(dto.getPageNum(), dto.getPageSize());

        List<TestEntity> list = testMapper.queyTest();
        PageInfo<TestEntity> pageInfo = new PageInfo<>(list);

        return pageInfo;
    }

    @Override
    public void testRedis(TestDto dto) {
        //平台标识+用户ID
        String token = tokenGenerator.generator(GENERATOR_KEY, "11");
        AppSessionUser appSessionUser = new AppSessionUser();
        appSessionUser.setToken(token);
        //appSessionUser.setName("张三");
        appSessionUser.setUserId(1);
        appSessionUser.setMobile("1313131313131");

        //appSessionUser.setRoleCode(2);
        redisUtil.set(token, JSON.toJSONString(appSessionUser),3);
    }

    @Override
    public void test3rd() {
        aliyunPushService.test();
    }

    @Override
    public String getToken() {
        String token = tokenGenerator.generator(GENERATOR_KEY, APPID);
        AppSessionUser appSessionUser = new AppSessionUser();
        appSessionUser.setToken(token);
        //appSessionUser.setName("李四");
        appSessionUser.setUserId(1);
        appSessionUser.setMobile("13512345678");
        //appSessionUser.setRoleCode(2);
        redisUtil.set(token, JSON.toJSONString(appSessionUser), 30 * 60);
        return token;
    }
}