
package com.ayc.service.app.service;

import com.ayc.service.app.dto.ArticleShareDto;
import com.ayc.service.app.vo.ArticleShareVo;

/**
 * 文章分享服务层
 *
 * @author wwq
 * @version 1.0
 * @createDate 2019/2/25 14:09
 */
public interface ArticleShareService {
    ArticleShareVo createShareUrl(Integer rCashId, ArticleShareDto articleShareDto);
}