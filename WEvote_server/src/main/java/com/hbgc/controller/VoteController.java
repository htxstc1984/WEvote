package com.hbgc.controller;

import com.hbgc.common.BaseController;
import com.hbgc.domain.Vote;
import com.hbgc.json.Json;
import com.hbgc.service.VoteService;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("vote")
public class VoteController extends BaseController {
    @Resource
    VoteService voteService;

    @PostMapping("createVote")
    public Map<String, Object> createVote(@RequestBody Vote newVote){
        try {
            Vote saveVote=voteService.createVote(newVote);
            return Json.success(saveVote, "投票创建成功！");
        } catch (Exception ex) {
            ex.printStackTrace();
            return Json.fail("投票创建失败！");
        }

    }
    @GetMapping("getVote")
    public Map<String, Object> getVoteByCreatorId(Integer creatorId){
        List<Vote> voteList=null;
        try {
            voteList=voteService.findByCreatorId(creatorId);
            return Json.success(voteList, "查询成功！");
        } catch (Exception ex) {
            ex.printStackTrace();
            return Json.fail("查询失败！");
        }
    }

    @GetMapping("getVoteNum")
    public Map<String, Object> getVoteNum( Integer creatorId){
        List<Vote> voteList=null;
        try {
            voteList=voteService.findByCreatorId(creatorId);
            return Json.success(voteList.size(), "查询成功！");
        } catch (Exception ex) {
            ex.printStackTrace();
            return Json.fail("查询失败！");
        }
    }

    @GetMapping("getVoteById")
    public Map<String, Object> getVoteById(Integer voteId){
        Vote vote=null;
        try {
            vote=voteService.getVoteById(voteId);
            return Json.success(vote,"查询成功！");
        } catch (Exception ex) {
            ex.printStackTrace();
            return Json.fail("查询失败！");
        }
    }
    @PutMapping("doVote")
    public Map<String, Object> doVote(Integer userId, Integer voteId,Integer optionId,Integer voteNum){
        try {
            voteService.doVoter(userId,voteId,optionId,voteNum);
            return Json.success(null,"投票信息修改成功！");
        } catch (Exception ex) {
            ex.printStackTrace();
            return Json.fail("投票信息修改失败！");
        }
    }
    @GetMapping("checkTitle")
    public Map<String, Object> checkTitle(String voteTitle){
        boolean isExist;
        try {
            isExist=voteService.isExist(voteTitle);
            return Json.success(isExist,"true表示标题不存在");
        } catch (Exception ex) {
            ex.printStackTrace();
            return Json.fail("检查过程出错！");
        }
    }

    @GetMapping("getAllVote")
    public Map<String, Object> getAllVote(){
        try {
            List<Vote> voteList=voteService.findAll();
            return Json.success(voteList,"查询所有投票成功");
        } catch (Exception ex) {
            ex.printStackTrace();
            return Json.fail("查询出错！");
        }
    }

    @PostMapping("delVote")
    public Map<String, Object> delVote(@RequestBody Vote delVote){
        try {
            voteService.update(delVote);
            return Json.success(null,"删除投票成功");
        } catch (Exception ex) {
            ex.printStackTrace();
            return Json.fail("删除失败！");
        }
    }
}
