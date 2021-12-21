package com.fzc.nms.service;

import com.fzc.nms.domain.Admin;

/**
 * 2021/12/11/0011
 * AdminService
 *
 * @author 帅帅付
 */

public interface AdminService {
    boolean checkLogin(String username, String password);
    Admin query(String username);
}
