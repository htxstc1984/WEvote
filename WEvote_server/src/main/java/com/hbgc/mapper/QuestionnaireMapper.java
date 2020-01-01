package com.hbgc.mapper;

import com.hbgc.domain.Questionnaire;
import org.springframework.stereotype.Repository;

@Repository
public interface QuestionnaireMapper {
    Questionnaire findById(Integer querId);
}
