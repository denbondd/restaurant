package com.denbondd.restaurant.web.filters;

import com.denbondd.restaurant.db.entity.User;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.HttpFilter;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * Redirects users on admin pages
 */
@WebFilter({"/users", "/receipts"})
public class AdminFilter extends HttpFilter {

    @Override
    protected void doFilter(HttpServletRequest req, HttpServletResponse res, FilterChain chain) throws IOException, ServletException {
        User user = (User) req.getSession().getAttribute("user");
        if (user == null || user.getRoleId() == 1) {
            res.sendRedirect(req.getContextPath() + "/catalog");
        } else {
            chain.doFilter(req, res);
        }
    }
}
