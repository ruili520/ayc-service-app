package com.ayc.service.app.service;

import com.ayc.service.app.dto.RealnameAuthDto;
import com.ayc.service.app.vo.RealnameAuthVo;

/**
 * 实名认证
 */
public interface RealnameAuthService {
    int insertRealname(Integer rCashId, RealnameAuthDto realnameAuthDto);

    RealnameAuthVo getRealnameAuth(Integer rCashId, RealnameAuthDto realnameAuthDto);
}
