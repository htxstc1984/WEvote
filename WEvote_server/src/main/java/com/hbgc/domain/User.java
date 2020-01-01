package com.hbgc.domain;

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
@EqualsAndHashCode(exclude = {"votes"})
@AllArgsConstructor
@NoArgsConstructor
@Entity
@JsonIgnoreProperties({"hibernateLazyInitializer","handler","votes"})
public class User extends BaseEntity{
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    private String gender;
    private String passWord;
    private String userName;
    private String phoneNum;
    private String email;
    private String token;
    private String validateCode;
    private String avatar;
    @ManyToMany(mappedBy="voter")
    @JsonIgnoreProperties(value = {"voter"})
    //用户投过的票
    private Set<Vote> votes;

}