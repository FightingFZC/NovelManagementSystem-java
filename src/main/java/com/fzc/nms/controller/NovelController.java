package com.fzc.nms.controller;

import com.fzc.nms.domain.Novel;
import com.fzc.nms.response.QueryResponse;
import com.fzc.nms.response.Response;
import com.fzc.nms.service.NovelService;
import org.springframework.util.ResourceUtils;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletResponse;
import java.io.File;
import java.io.IOException;
import java.io.OutputStream;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

/**
 * 2021/12/11/0011
 * NovelController
 *
 * @author 帅帅付
 */
@RestController
@RequestMapping(value = "/novel")
public class NovelController {
    @Resource
    NovelService novelServiceImpl;

    @PostMapping(value = "/add")
    public Response add(@RequestBody Novel novel) {
        int n = novelServiceImpl.add(novel);
        return new Response("成功新增了" + n + "篇文章！", true);
    }

    @GetMapping(value = "/id/{id}")
    public Response query(@PathVariable Integer id) {
        Novel novel = novelServiceImpl.query(id);
        if (novel != null) {
            return new QueryResponse("查询成功！", true, novel);
        }else {
            return new Response("没有该id的文章", false);
        }

    }

    @GetMapping(value = "/username/{username}")
    public Response query(@PathVariable String username, Integer page,
                          Integer length) {
        List<Novel> list = novelServiceImpl.query(username, page * length, length);
        int total = novelServiceImpl.queryTotal(username);
        class QueryResponse extends Response{
            List<Novel> list;
            int total;

            public QueryResponse(String msg, Boolean state, List<Novel> list,
                                 int total) {
                super(msg, state);
                this.list = list;
                this.total = total;
            }

            public QueryResponse() {
            }

            public List<Novel> getList() {
                return list;
            }

            public void setList(List<Novel> list) {
                this.list = list;
            }

            public int getTotal() {
                return total;
            }

            public void setTotal(int total) {
                this.total = total;
            }
        }
        return new QueryResponse("查询用户：" + username + "的文章成功",
                true, list, total);
    }

    @PutMapping(value = "/modify")
    public Response modify(@RequestBody Novel novel) {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH-mm-ss");
        Date now = new Date();
        novel.setLastModifiedTime(sdf.format(now));
        int n = novelServiceImpl.modify(novel);
        return new Response("修改成功！", true);
    }

    @DeleteMapping(value = "/{id}")
    public Response delete(@PathVariable Integer id) {
        int n = novelServiceImpl.delete(id);
        return new Response("删除id为：" + id + " 的文章成功", true);
    }

    @DeleteMapping(value = "/")
    public Response delete(@RequestBody List<Integer> ids) {
        System.out.println(ids.size());
        int n = novelServiceImpl.delete(ids.toArray(new Integer[ids.size()]));
        return new Response("删除了" + n + "篇文章", true);
    }

    @DeleteMapping(value = "/username/{username}")
    public Response delete(@PathVariable String username) {
        int n = novelServiceImpl.delete(username);
        return new Response("删除用户：" + username + "的文章成功！共" + n + "篇", true);
    }

    /*通过导入文件识别内容，存到数据库里*/
    @PostMapping(value = "/import/{username}/{title}")
    public Response imp(MultipartFile file, @PathVariable String username,
                        @PathVariable String title) {
        String fileName = file.getOriginalFilename();
        String suffix = fileName.substring(fileName.lastIndexOf("."));
        System.out.println("NovelController.imp方法：后缀名为：" + suffix);
        /*临时文件，之后文件的转换以File类为参数*/
        File temp = null;
        try {
            temp = new File(ResourceUtils.
                    getURL("classpath:").getPath() + "static/temp/" + fileName);
            if (!temp.exists()) {
                /*不存在就创建目录，创建文件*/
                temp.mkdirs();
                temp.createNewFile();
            }
            file.transferTo(temp);

        } catch (IOException e) {
            e.printStackTrace();
        }

        /*根据不同的后缀名选择不同的方法去处理*/
        String novelData = null;
        try {
            if (".docx".equals(suffix)) {
                novelData = novelServiceImpl.parseWordDocx(temp);
            }else if (".doc".equals(suffix)) {
                novelData = novelServiceImpl.parseWordDoc(temp);
            }else if (".txt".equals(suffix)) {
                novelData = novelServiceImpl.parseTxt(temp);
            }
            SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH-mm-ss");
            String strDate = sdf.format(new Date());
            Novel novel = new Novel(username, strDate, strDate, novelData, title);
            System.out.println("NovelController.imp方法：username:" + username);
            int n = novelServiceImpl.add(novel);
        } catch (Exception e) {
            e.printStackTrace();
            return new QueryResponse("导入失败！", false, null);
        } finally {
            if (temp != null) {
                // 最后把临时文件删掉
                /*记一笔，由于没把流给关掉，导致删不掉，所以流得看到就关*/
                System.out.println("NovelController.imp方法：临时文件删除" + temp.delete());
            }
        }

        return new QueryResponse("导入成功！", true, novelData);
    }

    @GetMapping(value = "/export/{type}/{id}")
    public void export(@PathVariable Integer id, @PathVariable String type,
                        HttpServletResponse response) {

        // 文件名，之后要处理，因为大路电脑默认不是UTF-8
        String fileName = null;
        // 根据id查得到Novel对象，然后将对象转换
        Novel novel = null;
        //是写入到浏览器的output流对象
        OutputStream outputStream = null;

        Boolean state = false;
        try {
            //调用Service查询novel
            novel = novelServiceImpl.query(id);
/*            //处理中文文件名中文乱码问题
            fileName =
                    new String((id + novel.getTitle()).getBytes(StandardCharsets.UTF_8),"ISO-8859-1");
            response.setHeader("Content-Disposition", fileName + "." + type);*/
            outputStream = response.getOutputStream();
            // 根据不同的type，调用不同的方法
            // 调用Service获取POIFSFileSystem
            if ("docx".equals(type)) {
                response.setContentType("application/vnd.openxmlformats-officedocument.wordprocessingml.document");
                state = novelServiceImpl.parseHtmlToDocx(novel.getData()
                        , outputStream);
            } else if ("doc".equals(type)) {
                //这一行太tm重要了，不加就不晓得搞了什么东西
                response.setContentType("application/msword");
                state = novelServiceImpl.parseHtmlToDoc(novel.getData(),
                        outputStream);
            } else if ("txt".equals(type)) {
                response.setContentType("text/plain");
                state = novelServiceImpl.parseHtmlToTxt(novel.getData(), outputStream);
            }

        } catch (IOException e) {
            e.printStackTrace();

        } finally {
            try {
                // 关闭资源
                if (outputStream != null) {
                    outputStream.close();
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

    }




}
