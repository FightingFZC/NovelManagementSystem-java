package com.fzc.nms.mapper;

import com.fzc.nms.domain.Novel;

import java.util.List;

/**
 * @Entity com.fzc.nms.domain.Noval
 */
public interface NovelMapper {
    int insertNovel(Novel novel);
    int deleteNovelById(Integer id);
    int deleteNovelByIds(Integer[] ids);
    int deleteNovelByUsername(String username);
    int modifyNovel(Novel novel);
    Novel selectNovelById(Integer id);
    List<Novel> selectNovelByUsername(String username, Integer startIndex,
                                      Integer length);
    int selectTotalByUsername(String username);
}




