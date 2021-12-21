package com.fzc.nms.controller;

import com.fzc.nms.service.AdminService;
import com.fzc.nms.service.UserService;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;

/**
 * 2021/12/11/0011
 * AdminController
 *
 * @author 帅帅付
 */
@RestController
@RequestMapping(value = "/admin")
public class AdminController {
    @Resource
    private UserService userServiceImpl;
    @Resource
    private AdminService adminServiceImpl;


}
