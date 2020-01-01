package com.hbgc.domain;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.hbgc.common.BaseEntity;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Set;

@Data
@EqualsAndHashCode(exclude = {"voter"})
@AllArgsConstructor
@NoArgsConstructor
@Entity
@JsonIgnoreProperties({"hibernateLazyInitializer","handler"})
public class Vote extends BaseEntity{
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    @ManyToOne
    @JoinColumn(name="creatorId",referencedColumnName="id")
    private User creator;
    private String title;
    private String description;
    private String descriptionImg;
    private String shareImg;
    private String startTime;
    private String deadline;
    private Integer onceMaxVote;
    @JsonIgnoreProperties(value = {"votes"})
    @ManyToMany
    @JoinTable(
            name="voter",
            joinColumns={@JoinColumn(name="vid",referencedColumnName="id")},
            inverseJoinColumns={@JoinColumn(name="uid",referencedColumnName="id")}
    )
    //那些用户参与过此投票
    private Set<User> voter;

}
