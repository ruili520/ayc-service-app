package com.ayc.service.app.service.impl;

import com.ayc.framework.common.PageVo;
import com.ayc.framework.util.DozerUtil;
import com.ayc.service.app.dao.mapper.AycOitLogEntityMapper;
import com.ayc.service.app.dto.AycOitLogDto;
import com.ayc.service.app.entity.AycOitLogEntity;
import com.ayc.service.app.service.AycOitLogService;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * @Title:
 * @Description: //TODO oit明细逻辑层
 * @Author: 程亚磊
 * @Date: 2019/2/25 18:34
 */
@Transactional(readOnly = true, rollbackFor = Exception.class)
@Service("AycOitLogService")
public class AycOitLogServiceImpl implements AycOitLogService {

    @Autowired
    private AycOitLogEntityMapper aycOitLogEntityMapper;
    /**
     * @Title:
     * @Description: //TODO oit明细列表展示（分页）
     * @Author: 程亚磊
     * @Date: 2019/2/25 18:33
     */
    @Override
    public PageVo<AycOitLogEntity> list(Integer rCashId, AycOitLogDto aycOitLogDto) {
        //pageHelper分页，会对后边的第一个sql进行分页
        PageHelper.startPage(aycOitLogDto.getPageNum(), aycOitLogDto.getPageSize());
        List<AycOitLogEntity> tempList = aycOitLogEntityMapper.selectAll(aycOitLogDto);



        PageInfo<AycOitLogEntity> pageInfo = new PageInfo<>(tempList);
        PageVo<AycOitLogEntity> pageVo = new PageVo<>();
        //将A对象copy到B对象中
        DozerUtil.map(pageInfo, pageVo);

        return pageVo;
    }
}
