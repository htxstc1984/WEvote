package com.hbgc.domain;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.hbgc.common.BaseEntity;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
@JsonIgnoreProperties({"hibernateLazyInitializer","handler","questionnaire"})
public class Topic extends BaseEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    @ManyToOne
    @JoinColumn(name="questionnaireId",referencedColumnName="id")
    private Questionnaire questionnaire;
    private String title;
    private String options;
    @OneToMany(cascade = {CascadeType.PERSIST})
    @JoinColumn(name = "topicId")
    private List<TopicOption> optionList;
}
