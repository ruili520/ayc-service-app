package com.ayc.service.app.service;

import com.ayc.service.app.vo.OitRateVo;

import java.util.List;

/**
 * 汇率详情
 */
public interface OitRateService {
    /**
     * 汇率记录展示
     * @return
     */
    List<OitRateVo> queryList(Integer rCashId);

    /**
     * 最近一次汇率展示
     */
    OitRateVo queryOitRate(Integer rCashId);
}
