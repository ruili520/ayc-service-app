package com.ayc.service.app.service.impl;

import com.ayc.framework.common.PageVo;
import com.ayc.framework.datasource.annotation.DataSource;
import com.ayc.framework.util.DozerUtil;
import com.ayc.service.app.common.constants.AycFinancialConst;
import com.ayc.service.app.common.enums.ArticleEnums;
import com.ayc.service.app.common.enums.OrderEnums;
import com.ayc.service.app.dao.mapper.ArticleAwardLogMapper;
import com.ayc.service.app.dao.mapper.AycFinancialMapper;
import com.ayc.service.app.dto.ArticleAwardDto;
import com.ayc.service.app.dto.AycFinancialDto;
import com.ayc.service.app.dto.OrderEntityDto;
import com.ayc.service.app.entity.ArticleAwardLogEntity;
import com.ayc.service.app.entity.AycFinancialEntity;
import com.ayc.service.app.entity.OrderEntity;
import com.ayc.service.app.service.ArticleAwardLogService;
import com.ayc.service.app.vo.ArticleAwardLogVo;
import com.ayc.service.uc.dao.mapper.AycUserDetailMapper;
import com.ayc.service.uc.entity.AycUserDetailEntity;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.google.common.collect.Lists;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

/**
 * 文章打赏记录服务层实现类
 *
 * @author wwq
 * @version 1.0
 * @createDate 2019/2/15 13:24
 */
@Service("articleAwardLogService")
public class ArticleAwardLogServiceImpl implements ArticleAwardLogService {

    @Autowired
    private ArticleAwardLogMapper articleAwardLogMapper;
    @Autowired
    private AycUserDetailMapper aycUserDetailMapper;
    @Autowired
    private AycFinancialMapper aycFinancialMapper;

    @Transactional(readOnly = true, rollbackFor = Exception.class)
    @Override
    public PageVo<ArticleAwardLogVo> listAwardLogs(@DataSource(field = "rCashId") Integer rCashId, ArticleAwardDto articleAwardDto) {
        PageHelper.startPage(articleAwardDto.getPageNum(), articleAwardDto.getPageSize());
        List<ArticleAwardLogEntity> tempList = articleAwardLogMapper.listAwardLogs(articleAwardDto);

        List<ArticleAwardLogVo> list = null;
        if (tempList != null && tempList.size() > 0){
            list = Lists.newArrayList();
            ArticleAwardLogVo item = null;
            for (ArticleAwardLogEntity logEntity : tempList){
                item = new ArticleAwardLogVo();
                DozerUtil.map(logEntity, item);
                list.add(item);
            }

            tempList.clear();
            tempList = null;
        }

        PageInfo<ArticleAwardLogVo> pageInfo = new PageInfo<>(list);
        PageVo<ArticleAwardLogVo> pageVo = new PageVo<>();

        DozerUtil.map(pageInfo, pageVo);

        return pageVo;
    }

    @Transactional(readOnly = false, rollbackFor = Exception.class)
    @Override
    public int addAwardLog(@DataSource(field = "rCashId") Integer rCashId, OrderEntityDto orderDto) {
        ArticleAwardLogEntity articleAwardLogEntity = new ArticleAwardLogEntity();

        articleAwardLogEntity.setArticleId(orderDto.getArticleId());
        articleAwardLogEntity.setArticleTitle(orderDto.getArticleTitle());
        articleAwardLogEntity.setCash(orderDto.getAmount());
        articleAwardLogEntity.setCreatedAt(new Date());
        articleAwardLogEntity.setUid(orderDto.getUid());
        articleAwardLogEntity.setToUid(orderDto.getToUid());
        articleAwardLogEntity.setStatus(ArticleEnums.AwardLogStatus.DEFAULT.getCode());
        articleAwardLogMapper.insert(articleAwardLogEntity);
        return articleAwardLogEntity.getId();
    }

    @Transactional(readOnly = true, rollbackFor = Exception.class)
    @Override
    public ArticleAwardLogEntity selectByPrimaryKey(@DataSource(field = "rCashId") Integer rCashId, Integer id) {
        return articleAwardLogMapper.selectByPrimaryKey(id);
    }

    @Transactional(readOnly = false, rollbackFor = Exception.class)
    @Override
    public int update(@DataSource(field = "rCashId") Integer rCashId, ArticleAwardLogEntity articleAwardLogEntity) {
        return articleAwardLogMapper.updateByPrimaryKey(articleAwardLogEntity);
    }

    @Transactional(readOnly = false, rollbackFor = Exception.class)
    @Override
    public void updateByPayStatus(@DataSource(field = "rCashId") Integer rCashId, OrderEntity orderEntity, AycFinancialDto financialDto) {
        ArticleAwardLogEntity articleAwardLogEntity = articleAwardLogMapper.selectByPrimaryKey(orderEntity.getObjId());
        articleAwardLogEntity.setStatus(OrderEnums.Status.SUCCESS.getCode());
        // 更新打赏记录表状态
        articleAwardLogMapper.updateByPrimaryKey(articleAwardLogEntity);

        BigDecimal awardCash = orderEntity.getCash();

        AycUserDetailEntity userParams = new AycUserDetailEntity();
        userParams.setUid(articleAwardLogEntity.getUid());
        // 修改打赏用户详情，新增打赏次数、打赏金额
        AycUserDetailEntity awardUserDetail = aycUserDetailMapper.selectByEntity(userParams);
        awardUserDetail.setAwardTimes(awardUserDetail.getAwardTimes() + 1);
        awardUserDetail.setAwardCash(awardUserDetail.getAwardCash().add(awardCash));
        awardUserDetail.setUpdatedAt(new Date());
        aycUserDetailMapper.updateByPrimaryKey(awardUserDetail);

        // 新增用户打赏与被打赏流水
        // 打赏用户流水
        AycFinancialEntity awardUserFinancial = new AycFinancialEntity();
        DozerUtil.map(financialDto, awardUserFinancial);
        awardUserFinancial.setUid(articleAwardLogEntity.getUid());
        awardUserFinancial.setAction(AycFinancialConst.FINANCIAL_ACTION_AWARD);
        awardUserFinancial.setCreatedAt(new Date());
        awardUserFinancial.setUpdatedAt(new Date());
        awardUserFinancial.setNowBalance(awardUserDetail.getBalance());
        aycFinancialMapper.insert(awardUserFinancial);

        userParams = new AycUserDetailEntity();
        userParams.setUid(articleAwardLogEntity.getToUid());
        // 修改被打赏用户详情，新增打赏次数、打赏金额、账户余额
        AycUserDetailEntity toAwardUserDetail = aycUserDetailMapper.selectByEntity(userParams);
        toAwardUserDetail.setBalance(toAwardUserDetail.getBalance().add(awardCash));
        toAwardUserDetail.setAwardedTimes(awardUserDetail.getAwardedTimes() + 1);
        toAwardUserDetail.setAwardedCash(awardUserDetail.getAwardedCash().add(awardCash));
        toAwardUserDetail.setUpdatedAt(new Date());

        // TODO 用户详情钱包变动状态
//        toAwardUserDetail.setWalletIsRed((byte) 2);
        aycUserDetailMapper.updateByPrimaryKey(toAwardUserDetail);

        // 被打赏用户
        AycFinancialEntity toAwardUser = new AycFinancialEntity();
        DozerUtil.map(financialDto, toAwardUser);
        toAwardUser.setUid(articleAwardLogEntity.getToUid());
        toAwardUser.setAction(AycFinancialConst.FINANCIAL_ACTION_BE_AWARD);
        toAwardUser.setCreatedAt(new Date());
        toAwardUser.setUpdatedAt(new Date());
        toAwardUser.setNowBalance(toAwardUserDetail.getBalance());
        aycFinancialMapper.insert(toAwardUser);
    }
}
