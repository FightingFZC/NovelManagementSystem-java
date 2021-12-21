package com.fzc.nms.response;

/**
 * 2021/12/12/0012
 * QueryResponse
 *
 * @author 帅帅付
 */

public class QueryResponse extends Response{
    private Object data;

    public QueryResponse() {
    }

    public QueryResponse(String msg, Boolean state, Object data) {
        super(msg, state);
        this.data = data;
    }

    public Object getData() {
        return data;
    }

    public void setData(Object data) {
        this.data = data;
    }
}
