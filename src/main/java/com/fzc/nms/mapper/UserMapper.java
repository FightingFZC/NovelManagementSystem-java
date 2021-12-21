package com.fzc.nms.mapper;

import com.fzc.nms.domain.User;

import java.util.List;

/**
 * @Entity com.fzc.nms.domain.User
 */

public interface UserMapper {
    int insertUser(User user);
    int deleteUser(String username);
    int deleteUsers(String[] usernames);
    int updateUser(User user);
    int setUsersFrozen(String[] usernames);
    User selectUser(String username);
    List<User> selectAll(Integer page, Integer length);
    int selectTotal();

}




