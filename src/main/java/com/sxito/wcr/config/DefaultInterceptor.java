package com.sxito.wcr.config;

import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;

/**
 * Created by lzp20 on 2017/3/29.
 */
public class DefaultInterceptor implements HandlerInterceptor {
    /**
     * 普通用户 拦截器
     * @param request
     * @param response
     * @param o
     * @return
     * @throws Exception
     */
    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object o) throws Exception {

//        if(request.getSession().getAttribute("user")!=null){
            return true;
//        }else{
//            System.out.println("登录失效");
//            PrintWriter out = null;
//            try {
//                out = response.getWriter();
//                out.append("{\"status\":-1,\"message\":\"Login failed！\",\"result\":null}");
//            } catch (IOException e) {
//                e.printStackTrace();
//            } finally {
//                if (out != null) {
//                    out.close();
//                }
//            }
//            return false;
//        }
    }

    @Override
    public void postHandle(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse, Object o, ModelAndView modelAndView) throws Exception {

    }

    @Override
    public void afterCompletion(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse, Object o, Exception e) throws Exception {

    }
}
