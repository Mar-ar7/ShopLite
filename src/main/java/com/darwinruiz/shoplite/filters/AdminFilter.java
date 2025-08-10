package com.darwinruiz.shoplite.filters;

import jakarta.servlet.*;
import jakarta.servlet.annotation.WebFilter;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

import java.io.IOException;

@WebFilter("/admin")
public class AdminFilter implements Filter {
    @Override
    public void doFilter(ServletRequest req, ServletResponse res, FilterChain chain)
            throws IOException, ServletException {
        HttpServletRequest r = (HttpServletRequest) req;
        HttpServletResponse p = (HttpServletResponse) res;

        HttpSession session = r.getSession(false);
        boolean autorizado = session != null &&
                Boolean.TRUE.equals(session.getAttribute("auth")) &&
                "ADMIN".equals(session.getAttribute("role"));

        if (!autorizado) {
            r.getRequestDispatcher("/403.jsp").forward(req, res);
            return;
        }

        chain.doFilter(req, res);
    }
}
