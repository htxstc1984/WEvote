package com.hbgc.domain;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.hbgc.common.BaseEntity;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
@JsonIgnoreProperties({"hibernateLazyInitializer","handler"})
public class Questionnaire extends BaseEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    @ManyToOne
    @JoinColumn(name="creatorId",referencedColumnName="id")
    private User creator;
    private String title;
    private String description;
    private String coverImg;
    private String deadline;
    @OneToMany(cascade = {CascadeType.PERSIST})
    @JoinColumn(name = "questionnaireId")
    private List<Topic> topicList;
}
