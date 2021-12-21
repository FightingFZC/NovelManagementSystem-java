package com.fzc.nms.interceptor;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fzc.nms.response.Response;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.PrintWriter;

/**
 * 2021/12/19/0019
 * LoginInterceptor
 *
 * @author 帅帅付
 */

public class LoginInterceptor implements HandlerInterceptor {
    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        response.setContentType("application/json;charset=UTF-8");
        ObjectMapper jackson = new ObjectMapper();
        HttpSession session = request.getSession();
        String authorization = request.getHeader("Authorization");
        String token = String.valueOf(session.getAttribute("token"));
        /*注意Writer不能在return true前获取，不然会报错getWriter() has already been called for this response*/
        if (authorization == null) {
            PrintWriter writer = response.getWriter();
            writer.write(jackson.writeValueAsString(new Response("未登录！", false)));
            return false;
        } else if (!authorization.equals(token)) {
            PrintWriter writer = response.getWriter();
            writer.write(jackson.writeValueAsString(new Response("token" +
                    "错误或超时！", false)));
            return false;
        } else {
            return true;
        }



    }

    @Override
    public void postHandle(HttpServletRequest request,
                           HttpServletResponse response, Object handler,
                           ModelAndView modelAndView) throws Exception {
        HandlerInterceptor.super.postHandle(request, response, handler,
                modelAndView);
    }

    @Override
    public void afterCompletion(HttpServletRequest request,
                                HttpServletResponse response, Object handler,
                                Exception ex) throws Exception {
        HandlerInterceptor.super.afterCompletion(request, response, handler, ex);
    }
}
