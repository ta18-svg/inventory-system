// config/AuthInterceptor.java
package com.example.inventory.config;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import org.springframework.web.servlet.HandlerInterceptor;

public class AuthInterceptor implements HandlerInterceptor {

    @Override
    public boolean preHandle(HttpServletRequest request,
                             HttpServletResponse response,
                             Object handler) throws Exception {

        HttpSession session = request.getSession(false);
        boolean loggedIn = (session != null && session.getAttribute("LOGIN_USER_ID") != null);

        String uri = request.getRequestURI();

        // ログイン画面 & 静的ファイルは除外
        if (uri.startsWith("/login") || uri.startsWith("/logout") || uri.startsWith("/css") || uri.startsWith("/error")) {
            return true;
        }

        if (!loggedIn) {
            response.sendRedirect("/login");
            return false;
        }

        return true;
    }
}
