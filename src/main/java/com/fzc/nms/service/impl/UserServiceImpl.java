package com.fzc.nms.service.impl;

import com.fzc.nms.domain.User;
import com.fzc.nms.mapper.UserMapper;
import com.fzc.nms.service.UserService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

/**
 * 2021/12/11/0011
 * UserServiceImpl
 *
 * @author 帅帅付
 */
@Service
public class UserServiceImpl implements UserService {
    @Resource
    private UserMapper userMapper;
    @Override
    public int add(User user) {
        if (query(user.getUsername()) != null) {
            return 0;
        }else {
            int n = userMapper.insertUser(user);
            System.out.println("添加了" + n + "条用户信息");
            return n;
        }
    }

    @Override
    public int delete(String username) {
        return userMapper.deleteUser(username);
    }

    @Override
    public int delete(String[] usernames) {
        return userMapper.deleteUsers(usernames);
    }

    @Override
    public int modify(User user) {
        return userMapper.updateUser(user);
    }

    @Override
    public int setUsersFrozen(String[] usernames) {
        return userMapper.setUsersFrozen(usernames);
    }

    @Override
    public User query(String username) {
        return userMapper.selectUser(username);
    }

    @Override
    public List<User> queryAll(Integer page, Integer length) {
        return userMapper.selectAll(page, length);
    }

    @Override
    public int queryTotal() {
        return userMapper.selectTotal();
    }

    @Override
    public boolean checkLogin(String username, String password) {
        User user = query(username);
        if (user == null) {
            /*没有该账号*/
            return false;
        }else {
            return password.equals(user.getPassword());
        }
    }
}
