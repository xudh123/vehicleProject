/*
package com.example.bootopen.filter;

import com.example.bootopen.Consts.UserConsts;
import com.example.bootopen.redis.RedisService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.web.servlet.ServletComponentScan;

import javax.servlet.*;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@WebFilter(urlPatterns = {"/*"})
public class UrlFilter implements Filter{

    @Autowired
    private RedisService redisService;

    String[] uris = {"/index", "/login", "/login_failed", "/getVehicles","/register", "/forVehicle",
            "getVehiclesByBrand", "getVehiclesByPrice", "", "/vehicle", "/buy_vehicle"};

    @Override
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain) throws IOException, ServletException {
        HttpServletRequest request = (HttpServletRequest) servletRequest;
        HttpServletResponse response = (HttpServletResponse) servletResponse;
        System.out.println(request.getRequestURI());
        if (redisService.get("user") != null){
            UserConsts.userLogined = 1;
            filterChain.doFilter(servletRequest, servletResponse);
        }
        if ( judge(request.getRequestURI()) || request.getRequestURI().contains("static")){
            filterChain.doFilter(request, response);
        }else {
            response.sendRedirect(request.getContextPath() + "/index");
        }
    }

    public boolean judge(String request_uri){
        for (String uri : uris){
            if (uri.equals(request_uri)){
                return true;
            }
        }
        return false;
    }
}
*/
