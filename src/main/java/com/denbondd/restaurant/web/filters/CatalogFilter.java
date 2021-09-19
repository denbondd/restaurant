package com.denbondd.restaurant.web.filters;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.HttpFilter;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@WebFilter({"/", "/catalog"})
public class CatalogFilter extends HttpFilter {

    @Override
    protected void doFilter(HttpServletRequest req, HttpServletResponse res, FilterChain chain) throws IOException, ServletException {
        String category = req.getParameter("category");
        String sortBy = req.getParameter("sortBy");
        if (category == null || sortBy == null) {
            res.sendRedirect(req.getContextPath() + "/catalog?category=0&sortBy=category_id&page=0&dishesOnPage=5");
        } else {
            chain.doFilter(req, res);
        }
    }
}
