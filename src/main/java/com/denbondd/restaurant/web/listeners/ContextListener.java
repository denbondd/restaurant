package com.denbondd.restaurant.web.listeners;

import com.denbondd.restaurant.util.SqlUtils;

import javax.servlet.*;
import javax.servlet.http.*;
import javax.servlet.annotation.*;

@WebListener
public class ContextListener implements ServletContextListener, HttpSessionListener, HttpSessionAttributeListener {

    @Override
    public void contextInitialized(ServletContextEvent sce) {
        sce.getServletContext().setAttribute("sortTypes", SqlUtils.sortingTypes);
    }
}
