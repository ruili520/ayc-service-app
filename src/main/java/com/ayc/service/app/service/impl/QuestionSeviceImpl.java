
package com.ayc.service.app.service.impl;

import com.ayc.framework.datasource.annotation.DataSource;
import com.ayc.framework.util.DozerUtil;
import com.ayc.service.app.dao.mapper.QuestionEntityMapper;
import com.ayc.service.app.entity.QuestionEntity;
import com.ayc.service.app.service.QuestionService;
import com.ayc.service.app.vo.AycQuestionVo;
import com.google.common.collect.Lists;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * 帮助中心服务层
 *
 * @author wwq
 * @version 1.0
 * @createDate 2019/2/18 21:52
 */
@Service("testService")
public class QuestionSeviceImpl implements QuestionService {
    @Autowired
    private QuestionEntityMapper questionEntityMapper;

    @Transactional(readOnly = false, rollbackFor = Exception.class)
    @Override
    public List<AycQuestionVo> listQuestions(@DataSource(field = "rCashId") Integer rCashId) {
        List<QuestionEntity> tempList = questionEntityMapper.listQuestions();
        List<AycQuestionVo> list = null;
        if (tempList != null && tempList.size() > 0){
            list = Lists.newArrayList();
            AycQuestionVo voItem = null;
            for (QuestionEntity question : tempList) {
                voItem = new AycQuestionVo();
                DozerUtil.map(question, voItem);
                list.add(voItem);
            }
        }
        return list;
    }
}