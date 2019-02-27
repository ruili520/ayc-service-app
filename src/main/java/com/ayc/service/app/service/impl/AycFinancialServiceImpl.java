package com.ayc.service.app.service.impl;

import com.ayc.framework.common.PageVo;
import com.ayc.framework.util.DozerUtil;
import com.ayc.service.app.dao.mapper.AycFinancialMapper;
import com.ayc.service.app.dto.FinancialDto;
import com.ayc.service.app.entity.AycFinancialEntity;
import com.ayc.service.app.service.AycFinancialService;
import com.ayc.service.app.vo.FinancialVo;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.google.common.collect.Lists;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;
import java.util.List;

/**
 * @Title:
 * @Description: //TODO 现金明细逻辑层
 * @Author: 程亚磊
 * @Date: 2019/2/25 18:33
 */
@Transactional(readOnly = false, rollbackFor = Exception.class)
@Service("AycFinancialService")
public class AycFinancialServiceImpl implements AycFinancialService {

    @Autowired
    private AycFinancialMapper aycFinancialEntityMapper;

    /**
     * @Title:
     * @Description: //TODO 现金明细列表展示（分页）
     * @Author: 程亚磊
     * @Date: 2019/2/25 18:33
     */
    @Override
    public PageVo<FinancialVo> list(Integer rCashId, FinancialDto financialDto) {
        //pageHelper分页，会对后边的第一个sql进行分页
        PageHelper.startPage(financialDto.getPageNum(), financialDto.getPageSize());
        List<AycFinancialEntity> tempList = aycFinancialEntityMapper.selectAll(financialDto);

        List<FinancialVo> list = null;
        if (tempList != null && tempList.size() > 0){
            list = Lists.newArrayList();
            FinancialVo item = null;
            for (AycFinancialEntity aycFinancialEntity : tempList){
                item = new FinancialVo();
                DozerUtil.map(aycFinancialEntity, item);
                list.add(item);
            }

            tempList.clear();
            tempList = null;
        }

        PageInfo<FinancialVo> pageInfo = new PageInfo<>(list);
        PageVo<FinancialVo> pageVo = new PageVo<>();
        //将A对象copy到B对象中
        DozerUtil.map(pageInfo, pageVo);

        return pageVo;
    }

    @Override
    public int insert(Integer rCashId, AycFinancialEntity aycFinancialEntity) {
        aycFinancialEntity.setCreatedAt(new Date());
        return aycFinancialEntityMapper.insert(aycFinancialEntity);
    }
}
