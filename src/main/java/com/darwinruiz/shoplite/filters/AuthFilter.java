package com.darwinruiz.shoplite.filters;

import jakarta.servlet.*;
import jakarta.servlet.annotation.WebFilter;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

import java.io.IOException;

@WebFilter("/*")
public class AuthFilter implements Filter {
    @Override
    public void doFilter(ServletRequest req, ServletResponse res, FilterChain chain)
            throws IOException, ServletException {
        HttpServletRequest r = (HttpServletRequest) req;
        HttpServletResponse p = (HttpServletResponse) res;

        String uri = r.getRequestURI();
        boolean esPublica =
                uri.endsWith("/index.jsp") || uri.endsWith("/login.jsp") ||
                        uri.contains("/auth/login") || uri.endsWith("/");

        if (esPublica) {
            chain.doFilter(req, res);
            return;
        }

        HttpSession session = r.getSession(false);
        boolean autenticado = (session != null && Boolean.TRUE.equals(session.getAttribute("auth")));

        if (!autenticado) {
            p.sendRedirect(r.getContextPath() + "/login.jsp");
            return;
        }

        chain.doFilter(req, res);
    }
}
