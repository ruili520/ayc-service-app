
package com.ayc.service.app.service.impl;

import com.ayc.service.app.common.enums.OrderEnums;
import com.ayc.service.app.common.utils.PayCommonUtil;
import com.ayc.service.app.dao.mapper.OrderMapper;
import com.ayc.service.app.entity.OrderEntity;
import com.ayc.service.app.service.OrderService;
import com.ayc.framework.datasource.annotation.DataSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;
/**
 * 订单服务层实现类
 * 
 * @author wwq
 * @version 1.0
 * @createDate 2019/2/14 18:15
 */
@Service("orderService")
public class OrderServiceImpl implements OrderService {
    @Autowired
    private OrderMapper orderMapper;


    @Transactional(readOnly = false, rollbackFor = Exception.class)
    @Override
    public int addOrder(@DataSource(field = "rCashId") Integer rCashId, OrderEntity orderEntity) {
        orderEntity.setStatus(OrderEnums.Status.WAIT_FOR_PAY.getCode());
        orderEntity.setCreatedAt(new Date());
        orderEntity.setUpdatedAt(new Date());
        return orderMapper.insert(orderEntity);
    }

    @Override
    public OrderEntity getOrderByCode(@DataSource(field = "rCashId") Integer rCashId, String orderCode) {
        return orderMapper.selectByCode(orderCode);
    }

    @Override
    public int update(@DataSource(field = "rCashId") Integer rCashId, OrderEntity orderCode) {
        return orderMapper.updateByPrimaryKeySelective(orderCode);
    }

    /**
     * 根据订单号修改订单状态
     */
    @Override
    public int updateByCode(Integer rCashId, String code,byte status) {
        OrderEntity orderEntity = new OrderEntity();
        orderEntity.setCode(code);
        orderEntity.setStatus(status);
        return orderMapper.updateByCode(orderEntity);
    }


}