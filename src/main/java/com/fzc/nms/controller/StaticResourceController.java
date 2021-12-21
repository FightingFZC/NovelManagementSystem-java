package com.fzc.nms.controller;

import com.fzc.nms.response.QueryResponse;
import com.fzc.nms.response.Response;
import com.fzc.nms.service.UserService;
import org.apache.commons.io.IOUtils;
import org.springframework.util.ResourceUtils;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import javax.annotation.Resource;
import javax.servlet.http.HttpSession;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;

/**
 * 2021/12/17/0017headImage
 * StaticResourceController
 *
 * @author 帅帅付
 */
@RestController
@RequestMapping(value = "/staticResource")
public class StaticResourceController {
    @Resource
    private UserService userServiceImpl;

    @PostMapping(value = "/imgPost/{username}")
    public Response uploadHeadImage(MultipartFile file,
                                    @PathVariable String username,
                                    HttpSession session){
        String filename = file.getOriginalFilename();
        String suffix = filename.substring(filename.lastIndexOf("."));
        // 上传的图片对象
        File img = null;
        // 储存图片的输出流
        FileOutputStream stream = null;

        try {
            img = new File(ResourceUtils.getURL("classpath:").getPath() +
                    "static/img/" + "headImage_user" + username + suffix);
            stream = new FileOutputStream(img);
            IOUtils.copy(file.getInputStream(), stream);

        } catch (IOException e) {
            e.printStackTrace();
            return new Response("上传失败!", false);
        } finally {
            if (stream != null) {
                try {
                    stream.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }

        return new QueryResponse("上传成功！", true, "http://localhost:8080/img" +
                "/headImage_user" + username + suffix);
    }
}
