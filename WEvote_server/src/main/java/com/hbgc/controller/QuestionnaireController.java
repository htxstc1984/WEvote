package com.hbgc.controller;

import com.hbgc.common.BaseController;
import com.hbgc.domain.Questionnaire;
import com.hbgc.domain.User;
import com.hbgc.json.Json;
import com.hbgc.service.QuestionnaireService;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("questionnaire")
public class QuestionnaireController extends BaseController {
    @Resource
    QuestionnaireService questionnaireService;

    @PostMapping("createQuestionnaire")
    public Map<String, Object> createVote(@RequestBody Questionnaire newQuestionnaire){
        try {
            Questionnaire saveQuestionnaire=questionnaireService.createQuestionnaire(newQuestionnaire);
            return Json.success(saveQuestionnaire, "问卷创建成功！");
        } catch (Exception ex) {
            ex.printStackTrace();
            return Json.fail("问卷创建失败！");
        }

    }

    @GetMapping("checkTitle")
    public Map<String, Object> checkTitle(String title){
        boolean isExist;
        try {
            isExist=questionnaireService.isExist(title);
            return Json.success(isExist,"true表示标题不存在");
        } catch (Exception ex) {
            ex.printStackTrace();
            return Json.fail("检查过程出错！");
        }
    }

    @PostMapping("getQues")
    public Map<String, Object> findByCreator(@RequestBody User creator){
        List<Questionnaire> quesList=null;
        try {
            quesList=questionnaireService.findByCreatorId(creator);
            return Json.success(quesList, "查询成功！");
        } catch (Exception ex) {
            ex.printStackTrace();
            return Json.fail("查询失败！");
        }
    }

    @GetMapping("getQuesById")
    public Map<String, Object> findById(Integer id){
        try {
            Questionnaire ques=questionnaireService.findById(id);
            System.out.println(ques);
            return Json.success(ques, "查询成功！");
        } catch (Exception ex) {
            ex.printStackTrace();
            return Json.fail("查询失败！");
        }
    }
}
