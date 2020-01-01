package com.hbgc.controller;

import com.hbgc.common.BaseController;
import com.hbgc.domain.Topic;
import com.hbgc.json.Json;
import com.hbgc.service.TopicService;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import java.util.Map;

@RestController
@RequestMapping("topic")
public class TopicController extends BaseController {
    @Resource
    TopicService topicService;

    @PostMapping("saveTopic")
    public Map<String, Object> createVote(@RequestBody Topic topic){
        System.out.println(topic);
        try {
            topicService.save(topic);
            return Json.success(null, "题目创建成功！");
        } catch (Exception ex) {
            ex.printStackTrace();
            return Json.fail("题目创建失败！");
        }

    }
}
