package com.ayc.service.app.service;

import com.ayc.framework.common.PageVo;
import com.ayc.service.app.dto.ArtcleCommentDto;
import com.ayc.service.app.entity.AycArticleCommentEntity;
import com.ayc.service.app.vo.ArtcleCommentVo;

import java.util.List;

/**
 * 文章评论
 */
public interface AycArticleCommentService {

    PageVo<ArtcleCommentVo> queryList(Integer rCashId, ArtcleCommentDto artcleCommentDto);

    AycArticleCommentEntity addArtcleComment(Integer rCashId,ArtcleCommentDto artcleCommentDto);

}
