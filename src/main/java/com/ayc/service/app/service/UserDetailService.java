package com.ayc.service.app.service;

import com.ayc.service.app.dto.UserDetailDto;
import com.ayc.service.app.vo.OitTotalVo;
import com.ayc.service.app.vo.UserDetailVo;

/**
 * 用户详情
 */
public interface UserDetailService {
    UserDetailVo selectUserDetail(Integer rCashId, UserDetailDto userDetailDto);

    OitTotalVo selectOit(Integer rCashId, Integer uid);
}
