package com.ayc.service.app.service.impl;

import com.ayc.framework.common.PageVo;
import com.ayc.framework.datasource.annotation.DataSource;
import com.ayc.framework.util.DozerUtil;
import com.ayc.service.app.dao.mapper.AycArticleCommentMapper;
import com.ayc.service.app.dto.ArtcleCommentDto;
import com.ayc.service.app.entity.AycArticleCommentEntity;
import com.ayc.service.app.service.AycArticleCommentService;
import com.ayc.service.app.vo.ArtcleCommentVo;
import com.ayc.service.uc.dao.mapper.AycUserMapper;
import com.ayc.service.uc.entity.AycUserEntity;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.google.common.collect.Lists;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;
import java.util.List;

@Service("aycArticleCommentService")
public class AycArticleCommentServiceImpl implements AycArticleCommentService {
    @Autowired
    private AycArticleCommentMapper aycArticleCommentMapper;
    @Autowired
    private AycUserMapper aycUserMapper;

    @Override
    public PageVo<ArtcleCommentVo> queryList(@DataSource(field = "rCashId") Integer rCashId, ArtcleCommentDto artcleCommentDto) {
        PageHelper.startPage(artcleCommentDto.getPageNum(),artcleCommentDto.getPageSize());
        List<AycArticleCommentEntity> list = aycArticleCommentMapper.selectAll(artcleCommentDto.getArticleId());
        List<ArtcleCommentVo> alist = null;
        if(list != null && list.size() > 0){
            alist = Lists.newArrayList();
            ArtcleCommentVo artcleCommentVo = null;
            for(AycArticleCommentEntity articleCommentEntity : list){
                artcleCommentVo = new ArtcleCommentVo();
                DozerUtil.map(articleCommentEntity,artcleCommentVo);
                AycUserEntity aycUserEntity = aycUserMapper.selectByPrimaryKey(articleCommentEntity.getUid());
                artcleCommentVo.setAycUserEntity(aycUserEntity);
                alist.add(artcleCommentVo);
            }
        }
        PageInfo<ArtcleCommentVo> pageInfo = new PageInfo<>(alist);
        PageVo<ArtcleCommentVo> pageVo = new PageVo<>();
        DozerUtil.map(pageInfo,pageVo);
        return pageVo;
    }

    @Override
    @Transactional(readOnly = false, rollbackFor = Exception.class)
    public AycArticleCommentEntity addArtcleComment(@DataSource(field = "rCashId") Integer rCashId, ArtcleCommentDto artcleCommentDto) {
        AycArticleCommentEntity articleCommentEntity = new AycArticleCommentEntity();
            articleCommentEntity.setId(artcleCommentDto.getId());
            articleCommentEntity.setUid(artcleCommentDto.getUid());
            articleCommentEntity.setArticleId(artcleCommentDto.getArticleId());
            articleCommentEntity.setDesc(artcleCommentDto.getDesc());
            articleCommentEntity.setCreatedAt(new Date());
            aycArticleCommentMapper.insert(articleCommentEntity);
        return articleCommentEntity;
    }
}
