package com.denbondd.restaurant.web.filters;

import javax.servlet.*;
import javax.servlet.annotation.*;
import javax.servlet.http.HttpFilter;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * If user is logged, then he/she doesn't have access to signup or login pages
 */
@WebFilter({"/account/login", "/account/signup"})
public class LogInSignUpFilter extends HttpFilter {

    @Override
    protected void doFilter(HttpServletRequest req, HttpServletResponse res, FilterChain chain) throws IOException, ServletException {
        if (req.getSession().getAttribute("user") != null) {
            res.sendRedirect(req.getContextPath() + "/account");
        } else {
            chain.doFilter(req, res);
        }
    }
}
