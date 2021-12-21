package com.fzc.nms.service.impl;

import com.fzc.nms.domain.Admin;
import com.fzc.nms.mapper.AdminMapper;
import com.fzc.nms.service.AdminService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

/**
 * 2021/12/11/0011
 * AdminServiceImpl
 *
 * @author 帅帅付
 */
@Service
public class AdminServiceImpl implements AdminService {
    @Resource
    private AdminMapper adminMapper;


    @Override
    public Admin query(String username) {
        return adminMapper.selectAdmin(username);
    }

    @Override
    public boolean checkLogin(String username, String password) {
        Admin admin = query(username);
        if (admin == null) {
            /*无该管理员*/
            return false;
        }else {
            return password.equals(query(username).getPassword());
        }
    }
}
