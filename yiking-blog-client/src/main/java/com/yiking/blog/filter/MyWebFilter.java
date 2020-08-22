package com.yiking.blog.filter;

import javax.servlet.*;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

public class MyWebFilter implements Filter {
    @Override
    public void init(FilterConfig filterConfig) throws ServletException {
    }

    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain)
            throws IOException, ServletException {
        HttpServletRequest req = (HttpServletRequest)request;
        HttpServletResponse res = (HttpServletResponse) response;
        // 允许跨域的域名，设置*表示允许所有域名
        String origin = req.getHeader("Origin");
        if ("abcdefg".contains(origin)) {  // 满足指定的条件
            res.addHeader("Access-Control-Allow-Origin", origin);
        }
        res.addHeader("Access-Control-Allow-Origin", "http://localhost:8080");
        // 允许跨域的方法，可设置*表示所有
        res.addHeader("Access-Control-Allow-Methods", "GET, POST, PUT, DELETE");
        // 允许的自定义字段
        String headers = req.getHeader("Access-Control-Request-Headers"); // 获取 request 发来的自定义字段
        res.addHeader("Access-Control-Allow-Headers", headers);
        // 或者
        // res.addHeader("Access-Control-Allow-Headers", "X-Custom-Header");
        // 预检请求的有效期，单位为秒。有效期内，不需要发送预检请求，ps 48小时
        res.addHeader("Access-Control-Max-Age", "172800");
        // 还可以有其他配置...
        chain.doFilter(request, response);
    }

    @Override
    public void destroy() {
    }
}
