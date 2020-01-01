package com.hbgc.controller;

import cn.hutool.captcha.CaptchaUtil;
import cn.hutool.captcha.LineCaptcha;
import cn.hutool.core.lang.UUID;
import cn.hutool.core.util.RandomUtil;
import com.hbgc.common.BaseController;
import com.hbgc.json.Json;
import com.hbgc.util.MyUtils;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.imageio.ImageIO;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.OutputStream;
import java.util.Map;
import java.util.Random;
import java.util.concurrent.TimeUnit;


@RestController
@RequestMapping("commons")
public class CommonsController extends BaseController {

    @Value("${upload.win.path}")
    private String uploadWinPath;

    @Value("${upload.linux.path}")
    private String  uploadLinuxPath;
    //生成一个数学公式的验证码的接口
    @GetMapping("mathRandomCode")
    public void createMathExpressRandomCode(String token, HttpServletRequest request, HttpSession session, HttpServletResponse response) {

        try {
            //画出一个数学公式的图片，返回给浏览器。
            //生成图片的尺寸，宽度和高度
            int width = 80;
            int height = 40;
            //create the image
            BufferedImage image = new BufferedImage(width, height, BufferedImage.TYPE_INT_RGB);
            Graphics g = image.getGraphics();
            // set the background color
            g.setColor(new Color(0xDCDCDC));
            g.fillRect(0, 0, width, height);
            // draw the border
            g.setColor(Color.black);
            g.drawRect(0, 0, width - 1, height - 1);
            // create a random instance to generate the codes
            Random rdm = new Random();
            // make some confusion
            for (int i = 0; i < 50; i++) {
                int x = rdm.nextInt(width);
                int y = rdm.nextInt(height);
                g.drawOval(x, y, 0, 0);
            }
            // generate a random code
            String verifyCode = MyUtils.generateVerifyCode(rdm);
            // "3*5"
            //把验证码存，计算结果保存到redis缓存;
            int rnd = MyUtils.calc(verifyCode); //15
            System.out.println("存入缓存成功");
            g.setColor(new Color(0, 100, 0));
            g.setFont(new Font("Candara", Font.BOLD, 24));
            g.drawString(verifyCode + "=", 8, 24);
            g.dispose();
            //使用servlet的out对象浏览器绘制一张图片。
            OutputStream out = response.getOutputStream();
            ImageIO.write(image, "JPEG", out);
            //把结果存到redis缓存中
            redisTemplate.opsForValue().set(token, rnd, 30, TimeUnit.MINUTES);
            out.flush();
            out.close();
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }

    //使用hutool工具类生成验证码
    @GetMapping("hutoolRandomCodeImage")
    public Map<String, Object> createRandomCodeImage(String token, HttpServletResponse response) {
        System.out.println("token值是：" + token);
        try {
            //定义图形验证码的长和宽
            LineCaptcha lineCaptcha = CaptchaUtil.createLineCaptcha(100, 40, 4, 2);
            BufferedImage image = new BufferedImage(100, 40, BufferedImage.TYPE_INT_RGB);
            //图形验证码写出，可以写出到文件，也可以写出到流
            OutputStream out = response.getOutputStream();
            image = (BufferedImage) lineCaptcha.createImage(lineCaptcha.getCode());
            ImageIO.write(image, "JPEG", out);
            //输出code
            //Console.log("hutool生成的验证码是：" + lineCaptcha.getCode());
            //把验证码保存到redis中,默认半个小时内有效。
            redisTemplate.opsForValue().set(token, lineCaptcha.getCode(), 10, TimeUnit.MINUTES);
            System.out.println("验证码保存到redis成功！");
            out.flush();
            out.close();
            return null;
        } catch (Exception ex) {
            ex.printStackTrace();
            return null;
        }
    }
    //给注册手机发送验证码
    @GetMapping("getPhoneRandomCode")
    public Map<String, Object> sendMobileRandomCode(String phoneNum) {
        String randomCode = null;
        try {
            randomCode = RandomUtil.randomNumbers(6);
            String token = UUID.fastUUID().toString(true);
            MyUtils.sendMessageCodeByAliyun(randomCode, phoneNum);
            //把验证码保存到redis中,默认半个小时内有效。
            redisTemplate.opsForValue().set(token, randomCode, 30, TimeUnit.MINUTES);
            return Json.success(token, "验证码发送成功！");
        } catch (Exception e) {
            e.printStackTrace();
            return Json.fail("验证码发送失败！");
        }

    }

    @PostMapping("upload")
    public Map<String, Object> loadFiles(MultipartFile file) {
        System.out.println(file);
        System.out.println("执行上传...");
        //Map<String, Object> map = new HashMap<String, Object>();
        //获得上传的路径
        //String path = request.getSession().getServletContext().getRealPath("/upload");

        String path = null;
        //先判断一下当前是什么操作

        if("windows".equals(MyUtils.getOperateSysName())){
            path = this.uploadWinPath;
        }else{
            path = this.uploadLinuxPath;
        }

        try {
            String uploadSuccessFileName = MyUtils.uploadFile(file, path);
            return Json.success(uploadSuccessFileName, "文件上传成功！");
        } catch (Exception ex) {
            ex.printStackTrace();
            return Json.fail("文件上传失败！");
        }
    }

}
