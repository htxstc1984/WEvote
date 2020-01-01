package com.hbgc.controller;

import com.hbgc.common.BaseController;
import com.hbgc.domain.Options;
import com.hbgc.json.Json;
import com.hbgc.service.OptionService;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("option")
public class OptionController extends BaseController {
    @Resource
    OptionService optionService;

    @PostMapping("saveOption")
    public Map<String, Object> createVote(@RequestBody Options options){
        try {
            optionService.save(options);
            return Json.success(null, "选项创建成功！");
        } catch (Exception ex) {
            ex.printStackTrace();
            return Json.fail("选项创建失败！");
        }

    }
    @GetMapping("getOption")
    public Map<String, Object> getOption(Integer voteId){
        List<Options> optionsList=null;
        try {
            optionsList=optionService.findAllByVoteId(voteId);
            return Json.success(optionsList, "查询成功！");
        } catch (Exception ex) {
            ex.printStackTrace();
            return Json.fail("查询失败！");
        }
    }
    @GetMapping("getOptionNum")
    public Map<String, Object> getOptionNum(Integer voteId){
        try {
            Integer optionNum=optionService.getOptionNumByVoteId(voteId);
            return Json.success(optionNum, "查询成功！");
        } catch (Exception ex) {
            ex.printStackTrace();
            return Json.fail("查询失败！");
        }
    }
    @PutMapping("doVote")
    public Map<String, Object> doVote(Integer optionId,Integer voteNum){
        try {
            Options options=optionService.getOne(optionId);
            options.setVotes(options.getVotes()+voteNum);
            optionService.update(options);
            return Json.success(null, "票数增加成功！");
        } catch (Exception ex) {
            ex.printStackTrace();
            return Json.fail("票数增加失败！");
        }
    }

}
