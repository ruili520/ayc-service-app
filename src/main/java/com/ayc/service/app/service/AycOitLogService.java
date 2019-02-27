package com.ayc.service.app.service;

import com.ayc.framework.common.PageVo;
import com.ayc.service.app.dto.AycOitLogDto;
import com.ayc.service.app.entity.AycOitLogEntity;

public interface AycOitLogService {

    /**
     * 现金明细列表
     * @param rCashId
     * @param aycOitLogDto
     * @return
     */
    PageVo<AycOitLogEntity> list(Integer rCashId, AycOitLogDto aycOitLogDto);
}
