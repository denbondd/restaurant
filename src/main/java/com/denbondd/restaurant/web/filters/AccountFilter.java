package com.denbondd.restaurant.web.filters;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.HttpFilter;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * Redirects not logged users to login page when accessing to pages that use account
 */
@WebFilter({"/account", "/account/change-password"})
public class AccountFilter extends HttpFilter {

    @Override
    protected void doFilter(HttpServletRequest req, HttpServletResponse res, FilterChain chain) throws IOException, ServletException {
        if (req.getSession().getAttribute("user") == null) {
            res.sendRedirect(req.getContextPath() + "/account/login");
        } else {
            chain.doFilter(req, res);
        }
    }
}
