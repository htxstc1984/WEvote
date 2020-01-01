package com.hbgc.repository;

import com.hbgc.common.BaseRepository;
import com.hbgc.domain.Vote;

import java.util.List;


public interface VoteRepository extends BaseRepository<Vote,Integer> {
    Vote findByTitle(String voteTitle);
}
