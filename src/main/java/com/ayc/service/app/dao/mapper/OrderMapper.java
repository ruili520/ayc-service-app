package com.ayc.service.app.dao.mapper;

import com.ayc.service.app.entity.OrderEntity;
import com.ayc.framework.dao.IBaseMapper;
import org.apache.ibatis.annotations.Param;

public interface OrderMapper extends IBaseMapper {
    int deleteByPrimaryKey(Integer id);

    int insert(OrderEntity record);

    int insertSelective(OrderEntity record);

    OrderEntity selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(OrderEntity record);

    int updateByPrimaryKey(OrderEntity record);

    /**
     * 通过订单编码查询订单
     * @param orderCode
     * @return
     */
    OrderEntity selectByCode(@Param("code") String orderCode);

    /**
     * 根据订单号修改订单状态
     * @param orderEntity
     * @return
     */
    int updateByCode(OrderEntity orderEntity);
}