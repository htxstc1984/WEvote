package com.hbgc.controller;

import com.hbgc.common.BaseController;
import com.hbgc.domain.User;
import com.hbgc.json.Json;
import com.hbgc.service.UserService;
import com.hbgc.util.MyUtils;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.annotation.Resource;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("user")
public class UserController extends BaseController {
    @Resource
    UserService userService;
    final private int pageSize = 10;
    @Value("${upload.win.path}")
    private String uploadWinPath;

    @Value("${upload.linux.path}")
    private String  uploadLinuxPath;
    @GetMapping("page/{type}/{pageNum}")
    public Map<String, Object> queryUsersByPager(@PathVariable("type") int type, @PathVariable("pageNum") int pageNum) {
        List<User> userList = null;
        userList = userService.queryUsersByPager(type, pageNum, pageSize);
        if (userList != null) {
            return Json.success(userList, "查询成功");
        }
        return Json.fail("查询失败");
    }

    @ApiOperation(value = "用户登录接口", notes = "URL传入用户名和密码执行用户登录")
    @ApiImplicitParam(name = "token", value = "token值", required = true)
    @GetMapping("Login")
    public Map<String, Object> userLogin(String userName, String passWord, String token, String validateCode) {
        User loginUser = null;
        try {
            if (!redisTemplate.hasKey(token)) {
                return Json.fail("验证码不正确");
            }
            String redis_ValidateCode = (String) redisTemplate.opsForValue().get(token);
            if (!redis_ValidateCode.equals(validateCode)) {
                return Json.fail("验证码不正确");
            }
//        Integer redis_ValidateCode = (Integer) redisTemplate.opsForValue().get(token);
//        if (redis_ValidateCode!=Integer.valueOf(validateCode) ) {
//            return Json.fail("验证码不正确");
//        }
            loginUser = userService.userLogin(userName, passWord);
            if (loginUser != null) {
                return Json.success(loginUser, "登陆成功");
            }
            return Json.fail("登陆失败");
        } catch (Exception e) {
            e.printStackTrace();
            return Json.fail("登陆失败");
        }

    }

    @PostMapping("register")
    public Map<String, Object> reg(@RequestBody User regUser) {
        System.out.println(regUser);
//        Users regUser = null;
        try {
            if (!checkValidateCode(regUser.getToken(), regUser.getValidateCode())) {
                return Json.fail("验证码不正确！");
            }
            System.out.println(regUser);
            userService.save(regUser);
            return Json.success(null, "注册成功！");
        } catch (Exception ex) {
            ex.printStackTrace();
            return Json.fail("注册失败！");
        }
    }

    @GetMapping("mLogin")
    public Map<String, Object> mUserLogin(String userName, String passWord) {
        User loginUser = null;
        try {
            loginUser = userService.userLogin(userName, passWord);
            if (loginUser != null) {
                return Json.success(loginUser, "登陆成功");
            }
            return Json.fail("登陆失败");
        } catch (Exception e) {
            e.printStackTrace();
            return Json.fail("登陆失败");
        }

    }
    @PostMapping("uploadAvatar")
    public Map<String, Object> loadFiles(MultipartFile file,Integer userId) {
        System.out.println(file);
        System.out.println("执行上传...");
        String path = null;
        if("windows".equals(MyUtils.getOperateSysName())){
            path = this.uploadWinPath;
        }else{
            path = this.uploadLinuxPath;
        }
        try {
            String uploadSuccessFileName = MyUtils.uploadFile(file, path);
            User updateUser=userService.getOne(userId);
            updateUser.setAvatar(uploadSuccessFileName);
            userService.update(updateUser);
            return Json.success(uploadSuccessFileName, "头像上传成功！");
        } catch (Exception ex) {
            ex.printStackTrace();
            return Json.fail("头像上传失败！");
        }
    }

}
