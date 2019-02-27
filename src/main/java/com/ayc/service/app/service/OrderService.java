
package com.ayc.service.app.service;

import com.ayc.service.app.entity.OrderEntity;

/**
 * 订单服务层
 *
 * @author wwq
 * @version 1.0
 * @createDate 2019/2/14 18:10
 */
public interface OrderService {

    int addOrder(Integer rCashId, OrderEntity orderEntity);

    /**
     * 通过orderCode 获取订单
     * @param orderCode
     * @return
     */
    OrderEntity getOrderByCode(Integer rCashId, String orderCode);

    /**
     * 修改订单
     * @param orderCode
     * @return
             */
    int update(Integer rCashId, OrderEntity orderCode);

    /**
     * 根据订单号修改订单状态
     * @param rCashId
     * @param code
     * @return
     */
    int updateByCode(Integer rCashId, String code,byte status);
}