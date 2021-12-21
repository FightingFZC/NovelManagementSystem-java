package com.fzc.nms.service;

import com.fzc.nms.domain.Novel;

import java.io.File;
import java.io.OutputStream;
import java.util.List;

/**
 * 2021/12/11/0011
 * NovelService
 *
 * @author 帅帅付
 */

public interface NovelService {
    int add(Novel novel);

    int delete(Integer id);
    int delete(Integer[] ids);
    int delete(String username);

    int modify(Novel novel);

    Novel query(Integer id);
    List<Novel> query(String username, Integer startIndex, Integer length);
    int queryTotal(String username);

    String parseWordDocx(File file);
    String parseWordDoc(File file);
    String parseTxt(File file);

    /**
     * 该方法用来格式化上传的word，去回车，去换行，去body外的标签
     * @param html 需要格式化的html
     * @return 返回格式化完成的。
     */
    String format(String html);

    /**
     *
     * @param novel html信息
     * @param responseOutputStream 响应的输出流
     * @return 判断是否成功
     */
    Boolean parseHtmlToDocx(String novel,
                            OutputStream responseOutputStream);
    Boolean parseHtmlToDoc(String novel,
                                    OutputStream responseOutputStream);
    Boolean  parseHtmlToTxt(String novel,
                            OutputStream responseOutputStream);

    /**
     * 将无<html><body></body></html>内容包裹上，并定义好编码
     * @param html 没有body标签的HTML
     * @return 将包裹完的html返回
     */
    String wrapNoBodyHtml(String html);
}
