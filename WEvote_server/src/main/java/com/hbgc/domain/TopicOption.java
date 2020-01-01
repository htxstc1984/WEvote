package com.hbgc.domain;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
@JsonIgnoreProperties({"hibernateLazyInitializer","handler","topicId"})
public class TopicOption {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)

    private Integer id;
    private String content;
    private Integer choiceNum;
    @ManyToOne
    @JoinColumn(name="topicId",referencedColumnName="id")
    private Topic topicId;
}
