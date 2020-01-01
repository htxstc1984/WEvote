package com.hbgc.repository;

import com.hbgc.common.BaseRepository;
import com.hbgc.domain.Options;

import java.util.List;

public interface OptionRepository extends BaseRepository<Options,Integer> {
    List<Options> findAllByVoteId(Integer voteId);
}
