package com.fzc.nms.service;

import com.fzc.nms.domain.User;

import java.util.List;

/**
 * 2021/12/11/0011
 * UserService
 *
 * @author 帅帅付
 */

public interface UserService {
    int add(User user);
    int delete(String username);
    int delete(String[] usernames);
    int modify(User user);
    int setUsersFrozen(String[] usernames);
    User query(String username);
    List<User> queryAll(Integer page, Integer length);
    int queryTotal();
    boolean checkLogin(String username, String password);
}
