package com.hbgc.service.impl;

import com.hbgc.common.BaseServiceImpl;
import com.hbgc.domain.Options;
import com.hbgc.repository.OptionRepository;
import com.hbgc.service.OptionService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

@Service
public class OptionServiceImpl extends BaseServiceImpl<Options,Integer, OptionRepository> implements OptionService {
    @Resource
    OptionRepository optionRepository;
    @Override
    public List<Options> findAllByVoteId(Integer voteId) {
        return optionRepository.findAllByVoteId(voteId);
    }

    @Override
    public Integer getOptionNumByVoteId(Integer voteId) {
        List<Options> optionsList=optionRepository.findAllByVoteId(voteId);
        return optionsList.size();
    }
}
