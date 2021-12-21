package com.fzc.nms.controller;

import com.fzc.nms.domain.User;
import com.fzc.nms.response.QueryResponse;
import com.fzc.nms.response.Response;
import com.fzc.nms.service.UserService;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.util.List;

/**
 * 2021/12/11/0011
 * UserController
 *
 * @author 帅帅付
 */
@RestController
@RequestMapping(value = "/user")
public class UserController {
    @Resource
    private UserService userServiceImpl;

    /**
     * 删除用户
     * */
    @DeleteMapping(value = "/delete/{username}")
    public Response delete(@PathVariable String username){
        Response response = new Response();
        int n = userServiceImpl.delete(username);
        if (n == 0) {
            response.msg = "删除失败！";
            response.state = false;
        }else if (n == 1) {
            response.msg = "删除成功！";
            response.state = true;
        }
        return response;
    }
    /**
     * 删除一组用户
     * */
    @DeleteMapping(value = "/delete")
    public Response delete(@RequestBody List<String> usernames){
        Response response = new Response();
        int n = userServiceImpl.delete(usernames.toArray(new String[usernames.size()]));
        if (n == 0) {
            response.msg = "删除失败！";
            response.state = false;
        }else {
            response.msg = "删除成功！";
            response.state = true;
        }
        return response;
    }

    /**
     * 更新用户
     * */
    @PutMapping(value = "/modify")
    public Response modify(@RequestBody User user) {
        int n = userServiceImpl.modify(user);
        if (n == 0) {
            return new Response("更新失败", false);
        }else {
            return new Response("更新成功！", true);
       }
    }

    @PutMapping(value = "/setUsersFrozen")
    public Response modify(@RequestBody List<String> usernames) {

        int n =
                userServiceImpl.setUsersFrozen(usernames.toArray(new String[usernames.size()]));
        if (n == 0) {
            return new Response("修改失败！", false);
        }else if (n != usernames.size()) {
            return new Response("有用户不存在，其余修改成功！", true);
        }else {
            return new Response("修改成功！", true);
        }

    }

    /**
     *
     * @param username 查询的用户名
     * @return 返回信息
     */

    @GetMapping(value = "/{username}")
    public Response query(@PathVariable String username){
        User user = userServiceImpl.query(username);

        if (user == null) {
            return new QueryResponse("查询失败！", false, null);
        }else {
            return new QueryResponse("查询成功！", true, user);
        }

    }
    @GetMapping(value = "/")
    public Response query(Integer page, Integer length){
        List<User> users = userServiceImpl.queryAll(page * length, length);
        int total = userServiceImpl.queryTotal();
        class QueryResponse extends Response {
            List<User> list;
            int total;
            public QueryResponse() {
            }

            public QueryResponse(String msg, Boolean state, List<User> list,
                                 int total) {
                super(msg, state);
                this.list = list;
                this.total = total;
            }

            public List<User> getList() {
                return list;
            }

            public void setList(List<User> list) {
                this.list = list;
            }

            public int getTotal() {
                return total;
            }

            public void setTotal(int total) {
                this.total = total;
            }
        }
        if (users == null) {
            return new Response("查询失败！", false);
        }else {
            return new QueryResponse("查询成功！", true, users, total);
        }

    }
}
