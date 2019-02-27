package com.ayc.service.app.service;

import com.ayc.framework.common.PageVo;
import com.ayc.service.app.dto.FinancialDto;
import com.ayc.service.app.entity.AycFinancialEntity;
import com.ayc.service.app.vo.FinancialVo;

public interface AycFinancialService {

    /**
     * @Title:
     * @Description: //TODO 现金明细列表
     * @Author: 程亚磊
     * @Date: 2019/2/25 20:48
     */
    PageVo<FinancialVo> list(Integer rCashId, FinancialDto financialDto);


    /**
     * @Title:
     * @Description: //TODO 插入一条现金明细记录
     * @Author: 程亚磊
     * @Date: 2019/2/25 20:48
     */
    int insert(Integer rCashId,AycFinancialEntity aycFinancialEntity);

}
