package com.hbgc.service;

import com.hbgc.common.BaseService;
import com.hbgc.domain.User;
import com.hbgc.domain.Vote;
import com.hbgc.repository.VoteRepository;

import java.util.List;

public interface VoteService extends BaseService<Vote,Integer, VoteRepository> {
    List<Vote> findByCreatorId(int creatorId);
    void doVoter(Integer userId,Integer voteId,Integer optionId,Integer voteNum);
    Vote getVoteById(Integer voteId);
    Vote createVote(Vote newVote);
    boolean isExist(String voteTitle);
}
