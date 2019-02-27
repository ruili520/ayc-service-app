
package com.ayc.service.app.service;

import com.ayc.service.app.vo.AycQuestionVo;

import java.util.List;

/**
 * 帮助中心
 *
 * @author wwq
 * @version 1.0
 * @createDate 2019/2/18 21:49
 */
public interface QuestionService {
    /**
     * 帮助中心的问题列表
     * @param rCashId
     * @return
     */
    List<AycQuestionVo> listQuestions(Integer rCashId);
}