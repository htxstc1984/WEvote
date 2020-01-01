package com.hbgc.service;

import com.hbgc.common.BaseService;
import com.hbgc.domain.Questionnaire;
import com.hbgc.domain.User;
import com.hbgc.repository.QuestionnaireRepository;

import java.util.List;


public interface QuestionnaireService extends BaseService<Questionnaire,Integer, QuestionnaireRepository> {
    Questionnaire createQuestionnaire(Questionnaire newQuestionnaire);
    boolean isExist(String title);
    List<Questionnaire> findByCreatorId(User user);
    Questionnaire findById(Integer id);
}
