package com.fzc.nms.response;

/**
 * 2021/12/11/0011
 * Response
 *
 * @author 帅帅付
 */

public class Response {
    public String msg;
    public Boolean state;

    public Response() {
    }

    public Response(String msg, Boolean state) {
        this.msg = msg;
        this.state = state;
    }
}
