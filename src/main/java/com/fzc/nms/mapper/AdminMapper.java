package com.fzc.nms.mapper;

import com.fzc.nms.domain.Admin;

/**
 * @Entity com.fzc.nms.domain.Admin
 */
public interface AdminMapper {
    Admin selectAdmin(String username);
}




