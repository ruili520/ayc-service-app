
package com.ayc.service.app.service;

import com.ayc.framework.common.PageVo;
import com.ayc.service.app.dto.ArticleAwardDto;
import com.ayc.service.app.dto.AycFinancialDto;
import com.ayc.service.app.dto.OrderEntityDto;
import com.ayc.service.app.entity.ArticleAwardLogEntity;
import com.ayc.service.app.entity.OrderEntity;
import com.ayc.service.app.vo.ArticleAwardLogVo;

/**
 * 文章打赏记录服务层
 *
 * @author wwq
 * @version 1.0
 * @createDate 2019/2/15 13:22
 */
public interface ArticleAwardLogService {

    /**
     * 获取打赏记录列表
     * @param rCashId
     * @param articleAwardDto
     * @return
     */
    PageVo<ArticleAwardLogVo> listAwardLogs(Integer rCashId, ArticleAwardDto articleAwardDto);

    /**
     * 新增打赏记录
     * @param orderCode
     * @return
     */
    int addAwardLog(Integer rCashId, OrderEntityDto orderCode);

    /**
     * 通过主键获取打赏记录
     * @param rCashId
     * @param id
     * @return
     */
    ArticleAwardLogEntity selectByPrimaryKey(Integer rCashId, Integer id);

    /**
     * 更新打赏记录
     * @param rCashId
     * @param articleAwardLogEntity
     * @return
     */
    int update(Integer rCashId, ArticleAwardLogEntity articleAwardLogEntity);

    /**
     * 通过支付结果更新打赏记录
     * @param rCashId
     * @param orderEntity
     * @param payType
     */
    void updateByPayStatus(Integer rCashId, OrderEntity orderEntity, AycFinancialDto payType);
}