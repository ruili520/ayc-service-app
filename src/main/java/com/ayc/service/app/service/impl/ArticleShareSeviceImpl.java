
package com.ayc.service.app.service.impl;

import com.ayc.framework.common.BizException;
import com.ayc.framework.datasource.annotation.DataSource;
import com.ayc.service.app.configs.AppBizCode;
import com.ayc.service.app.configs.AppGlobalConfigs;
import com.ayc.service.app.dao.mapper.ArticleMapper;
import com.ayc.service.app.dto.ArticleShareDto;
import com.ayc.service.app.entity.ArticleEntity;
import com.ayc.service.app.service.ArticleShareService;
import com.ayc.service.app.vo.ArticleShareVo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * 文章分享服务层实现类
 *
 * @author wwq
 * @version 1.0
 * @createDate 2019/2/25 14:11
 */
@Service("articleShareService")
public class ArticleShareSeviceImpl implements ArticleShareService {

    @Autowired
    private AppGlobalConfigs appGlobalConfigs;
    @Autowired
    private ArticleMapper articleMapper;

    @Override
    public ArticleShareVo createShareUrl(@DataSource(field = "rCashId") Integer rCashId, ArticleShareDto articleShareDto) {
        ArticleEntity articleEntity = articleMapper.selectByPrimaryKey(articleShareDto.getArticleId());
        if (articleEntity == null){
            throw new BizException(AppBizCode.ARTICLE_NOT_FOUND);
        }
        String url = appGlobalConfigs.getWebUrl() + "/share?article=" + articleShareDto.getArticleId();

        ArticleShareVo result = new ArticleShareVo();
        result.setTitle(articleEntity.getTitle());
        result.setUrl(url);
        return result;
    }
}