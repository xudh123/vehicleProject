package com.example.admin.filter;

import com.example.admin.consts.AdminConsts;
import com.example.admin.entity.Admin;
import com.example.admin.redis.RedisService;
import org.springframework.beans.factory.annotation.Autowired;

import javax.servlet.*;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/*过滤器*/
@WebFilter(urlPatterns = {"/*"})
public class loginFilter implements Filter {

    @Autowired
    private RedisService redisService;

    @Override
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain) throws IOException, ServletException {
        HttpServletRequest request = (HttpServletRequest) servletRequest;
        HttpServletResponse response = (HttpServletResponse) servletResponse;
        //System.out.println(request.getRequestURI());

        Admin admin = redisService.get(AdminConsts.adminName, Admin.class);
        if (admin != null){
            filterChain.doFilter(servletRequest, servletResponse);
        }else if ( request.getRequestURI().equals("/login") || request.getRequestURI().equals("/login.do") || request.getRequestURI().contains("static")){
            filterChain.doFilter(servletRequest, servletResponse);
        }else {
            response.sendRedirect(request.getContextPath() + "/login");
        }
    }
}
