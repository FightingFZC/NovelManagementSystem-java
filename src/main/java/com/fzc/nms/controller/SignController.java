package com.fzc.nms.controller;

import com.fzc.nms.domain.Admin;
import com.fzc.nms.domain.User;
import com.fzc.nms.response.QueryResponse;
import com.fzc.nms.response.Response;
import com.fzc.nms.service.AdminService;
import com.fzc.nms.service.UserService;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import javax.mail.*;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.util.Map;
import java.util.Properties;
import java.util.Random;
import java.util.UUID;

/**
 * 2021/12/11/0011
 * SignController
 *
 * @author 帅帅付
 */
@RequestMapping(value = "/sign")
@RestController
public class SignController {
    @Resource
    private UserService userServiceImpl;
    @Resource
    private AdminService adminServiceImpl;

    /**
     * @Param map 预计有：username，password，isAdmin三个参数
     * 根据isAdmin的true、false来选择UserService还是AdminService
     */
    @PostMapping(value = "/login")
    public Response login(@RequestBody Map map, HttpServletRequest request) {
        /*为了多返回一个token*/
        class LoginResponse extends QueryResponse {
            String token;

            public LoginResponse() {
            }

            public LoginResponse(String msg, Boolean state, Object data,
                                 String token) {
                super(msg, state, data);
                this.token = token;
            }

            public String getToken() {
                return token;
            }

            public void setToken(String token) {
                this.token = token;
            }
        }
        Boolean isAdmin = Boolean.valueOf(String.valueOf(map.get("isAdmin")));
        String username = String.valueOf(map.get("username"));
        String password = String.valueOf(map.get("password"));
        if (isAdmin) {
            if (adminServiceImpl.checkLogin(username, password)) {
                /*验证成功后生成一个token*/
                String token = UUID.randomUUID().toString();
                System.out.println("管理员：" + username + " 的token为：" + token);
                /*将token放session里*/
                HttpSession session = request.getSession();
                session.setAttribute("token", token);
                return new LoginResponse("登陆成功！", true,
                        adminServiceImpl.query(username), token);
            } else {
                return new Response("账号或密码错误，请重新输入", false);
            }

        } else {
            User user = userServiceImpl.query(username);
            if (userServiceImpl.checkLogin(username, password)) {
                if (user.getIsFrozen()) {
                    return new Response("改账号已冻结，无法登陆", false);
                }
                /*验证成功后生成一个token*/
                String token = UUID.randomUUID().toString();
                System.out.println("用户：" + username + " 的token为：" + token);
                /*将token放session里*/
                HttpSession session = request.getSession();
                session.setAttribute("token", token);
                return new LoginResponse("登陆成功！", true,
                        user, token);
            } else {
                return new Response("账号或密码错误，请重新输入", false);
            }
        }
    }

    /**
     * 注册只有用户注册，所以不需要判断
     */
    @PostMapping(value = "/register")
    public Response register(@RequestBody Map<String, String> data,
                             HttpSession session) {
        String username = data.get("username");
        String password = data.get("password");
        String email = data.get("email");
        String verificationCode = data.get("verificationCode");
        Response response = new Response();
        User user = new User(username, password, email);
        System.out.println(user);
        if (verificationCode.equals(session.getAttribute("verificationCode"))) {
            int n = userServiceImpl.add(user);
            if (n == 0) {
                response.msg = "该账号已被注册，请更换账号！";
                response.state = false;
            } else if (n == 1) {
                response.msg = "注册成功！请登录。";
                response.state = true;
            }
        } else {
            response.msg = "验证码错误！";
            response.state = false;
        }


        return response;
    }

    @GetMapping(value = "/isLogined")
    public Response isLogined(HttpServletRequest request) {
        HttpSession session = request.getSession();
        String username = null;
        Boolean isAdmin = (Boolean) session.getAttribute("isAdmin");
        if (isAdmin && (username = (String) session.getAttribute("username")) != null) {

            Admin admin = adminServiceImpl.query(username);
            //整一下密码，省得暴露出来

            return new QueryResponse("已登录", true, admin);

        } else if (!isAdmin && (username = (String) session.getAttribute(
                "username")) != null) {
            User user = userServiceImpl.query(username);
            //整一下密码，省得暴露出来

            return new QueryResponse("已登录", true, user);
        }

        return new Response("未登录，请登录", false);

    }

    @PostMapping(value = "/loginOut")
    public Response loginOut(HttpSession session) {
        session.removeAttribute("username");
        session.removeAttribute("isAdmin");
        return new Response("退出成功！", true);
    }

    @GetMapping(value = "/getVerificationCode/{email}")
    public Response getVerificationCode(@PathVariable String email,
                                        HttpSession session) {
        //服务器地址
//        String smtp = "smtp.gmail.com";
        String smtp = "smtp.qq.com";
        //发送邮箱的用户名
//        String username = "fuzhichao.me@gmail.com";
        String username = "2026611738@qq.com";
        //发送邮箱的密码
//        String password = "uhnjexzfhfhuhshk";
        String password = "ofiqpmfpbmpceeia";
        //配置连接服务器
        Properties prop = new Properties();
        prop.put("mail.smtp.host", smtp);
        prop.put("mail.smtp.port", "587"); // 主机端口号
        prop.put("mail.smtp.auth", "true"); // 是否需要用户认证
        prop.put("mail.smtp.starttls.enable", "true"); // 启用TLS加密
        //javax.mail里的类
        Session mailSession = Session.getInstance(prop, new Authenticator() {
            @Override
            protected PasswordAuthentication getPasswordAuthentication() {
                return new PasswordAuthentication(username, password);
            }
        });
//        mailSession.setDebug(true);
        try {
            //构建消息内容对象
            MimeMessage message = new MimeMessage(mailSession);
            // 设置发送方地址:
//            message.setFrom(new InternetAddress("fuzhichao.me@gmail.com"));
            message.setFrom(new InternetAddress("2026611738@qq.com"));
            // 设置接收方地址:
            message.setRecipient(Message.RecipientType.TO,
                    new InternetAddress(email));
            // 设置邮件主题:
            message.setSubject("注册验证码！", "UTF-8");
            // 设置邮件正文:
            Random random = new Random();
            StringBuilder content = new StringBuilder();
            /*生成六位数*/
            for (int i = 0; i < 6; i++) {
                content.append(random.nextInt(10));
            }
            session.setAttribute("verificationCode", content.toString());
            message.setText("验证码为：" + content, "UTF-8");
            // 发送:
            Transport.send(message);
        } catch (MessagingException e) {
            e.printStackTrace();
            return new Response("发送失败！", false);
        }
        return new Response("发送成功！", true);
    }


}
