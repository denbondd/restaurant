package com.denbondd.restaurant.web.listeners;

import com.denbondd.restaurant.util.SqlUtils;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import javax.servlet.*;
import javax.servlet.http.*;
import javax.servlet.annotation.*;
import java.io.File;
import java.io.IOException;

@WebListener
public class ContextListener implements ServletContextListener, HttpSessionListener, HttpSessionAttributeListener {

    private static final Logger log = LogManager.getLogger(ContextListener.class.getName());

    @Override
    public void contextInitialized(ServletContextEvent sce) {
        ServletContext context = sce.getServletContext();
        context.setAttribute("sortTypes", SqlUtils.sortingTypes);

//        String realPath = context.getRealPath("/log4j2.log");
//        System.setProperty("logFile", realPath);
//        log.debug("logPath: " + realPath);
    }
}
