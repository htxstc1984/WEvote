package com.hbgc.mapper;

import com.hbgc.domain.Vote;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface VoteMapper {
    //包含创建者信息
    List<Vote> findByCreatorId(int creatorId);
    //包含参与投票者信息
    List<Vote> findByCreatorId2(int creatorId);
    Integer findCreatorIdByVoteId(Integer voteId);
    Vote findVoteById(Integer voteId);
}
