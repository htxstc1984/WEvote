package com.hbgc.service.impl;

import cn.hutool.core.date.DateTime;
import com.hbgc.common.BaseServiceImpl;
import com.hbgc.domain.Options;
import com.hbgc.domain.User;
import com.hbgc.domain.Vote;
import com.hbgc.mapper.UserMapper;
import com.hbgc.mapper.VoteMapper;
import com.hbgc.repository.OptionRepository;
import com.hbgc.repository.UserRepository;
import com.hbgc.repository.VoteRepository;
import com.hbgc.service.VoteService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.util.*;

@Service

public class VoteServiceImpl extends BaseServiceImpl<Vote, Integer, VoteRepository> implements VoteService {

    @Resource
    VoteRepository voteRepository;
    @Resource
    UserRepository userRepository;
    @Resource
    UserMapper userMapper;
    @Resource
    OptionRepository optionRepository;
    @Resource
    VoteMapper voteMapper;

    @Override
    public List<Vote> findByCreatorId(int creatorId) {
        List<Vote> voteList=voteMapper.findByCreatorId(creatorId);
        List<Vote> voteListWithVoter=voteMapper.findByCreatorId2(creatorId);
        for(int i=0;i<voteList.size();i++){
            for(int j=0;j<voteListWithVoter.size();j++){
                if(voteList.get(i).getId()== voteListWithVoter.get(j).getId()){
                    Vote vote=voteList.get(i);
                    Set<User> voterSet=voteListWithVoter.get(j).getVoter();
                    vote.setVoter(voterSet);
                    voteList.set(i,vote);
                }
            }
        }
        return voteList;
    }

    @Override
    @Transactional//启用事务管理
    public void doVoter(Integer userId, Integer voteId,Integer optionId,Integer voteNum) {
        //获取修改时间
        Date d = new Date();
        DateTime dt = DateTime.of(d);
        //增加选项票数
        Options options=optionRepository.getOne(optionId);
        options.setVotes(options.getVotes()+voteNum);
        options.setModifyTime(dt.toString("yyyy-MM-dd HH:mm:ss"));
        optionRepository.save(options);
        //增加投票信息，即绑定某用户投过某次票
        User user = userRepository.getOne(userId);
        Vote vote = voteRepository.getOne(voteId);
        Set<User> userSet = new HashSet<User>();
        userSet.add(user);
        vote.setVoter(userSet);
        vote.setModifyTime(dt.toString("yyyy-MM-dd HH:mm:ss"));
        voteRepository.save(vote);
    }

    @Override
    public Vote getVoteById(Integer voteId) {
        Vote vote=voteRepository.getOne(voteId);
        Integer creatorId=voteMapper.findCreatorIdByVoteId(voteId);
        User user=userMapper.selectByPrimaryKey(creatorId);
        vote.setCreator(user);
        return vote;
    }

    @Override
    public Vote createVote(Vote newVote) {
        String voteTitle=newVote.getTitle();
        voteRepository.save(newVote);
        Vote saveVote=voteRepository.findByTitle(voteTitle);
        return saveVote;
    }

    @Override
    public boolean isExist(String voteTitle) {
        if(voteRepository.findByTitle(voteTitle)==null){
            return true;
        }else {
            return false;
        }
    }



}
