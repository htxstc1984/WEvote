package com.hbgc.service.impl;

import com.hbgc.common.BaseServiceImpl;
import com.hbgc.controller.QuestionnaireController;
import com.hbgc.domain.Questionnaire;
import com.hbgc.domain.User;
import com.hbgc.mapper.QuestionnaireMapper;
import com.hbgc.repository.QuestionnaireRepository;
import com.hbgc.service.QuestionnaireService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

@Service
public class QuestionnaireServiceImpl extends BaseServiceImpl<Questionnaire, Integer, QuestionnaireRepository> implements QuestionnaireService {

    @Resource
    QuestionnaireRepository questionnaireRepository;
    @Resource
    QuestionnaireMapper questionnaireMapper;
    @Override
    public Questionnaire createQuestionnaire(Questionnaire newQuestionnaire) {
        String quesTitle=newQuestionnaire.getTitle();
        questionnaireRepository.save(newQuestionnaire);
        Questionnaire saveQuestionnaire=questionnaireRepository.findByTitle(quesTitle);
        return saveQuestionnaire;
    }

    @Override
    public boolean isExist(String title) {
        if(questionnaireRepository.findByTitle(title)==null){
            return true;
        }else {
            return false;
        }
    }

    @Override
    public List<Questionnaire> findByCreatorId(User user) {
        return questionnaireRepository.findByCreator(user);
    }

    @Override
    public Questionnaire findById(Integer id) {
        return questionnaireMapper.findById(id);
    }
}
