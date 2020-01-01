package com.hbgc.service;

import com.hbgc.common.BaseService;
import com.hbgc.domain.Options;
import com.hbgc.domain.Vote;
import com.hbgc.repository.OptionRepository;

import java.util.List;

public interface OptionService extends BaseService<Options,Integer, OptionRepository> {
    List<Options> findAllByVoteId(Integer voteId);
    Integer getOptionNumByVoteId(Integer voteId);
}
