package com.hbgc.repository;

import com.hbgc.common.BaseRepository;
import com.hbgc.domain.Questionnaire;
import com.hbgc.domain.User;

import java.util.List;

public interface QuestionnaireRepository extends BaseRepository<Questionnaire,Integer> {
    Questionnaire findByTitle(String title);
    List<Questionnaire> findByCreator(User user);
}
