package com.denbondd.restaurant.web.servlets.account;

import com.denbondd.restaurant.db.Dao;
import com.denbondd.restaurant.db.entity.User;
import com.denbondd.restaurant.exceptions.DbException;
import com.denbondd.restaurant.util.Utils;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@WebServlet("/account/login")
public class LogInServlet extends HttpServlet {

    private static final Logger log = LogManager.getLogger(LogInServlet.class.getName());

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        req.getRequestDispatcher("/WEB-INF/jsp/login.jsp").forward(req, resp);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        String login = req.getParameter("login");
        char[] password = req.getParameter("password").toCharArray();
        if (login.isEmpty() || password.length == 0) {
            resp.sendRedirect(req.getContextPath() + "/account/login");
            return;
        }
        try {
            User user = Dao.getDao().getUserDao().logIn(login, password);
            if (user == null) {
                req.setAttribute("err", "true");
                resp.sendRedirect(req.getContextPath() + "/account/login?err=");
            } else {
                req.getSession().setAttribute("user", user);
                resp.sendRedirect(req.getContextPath() + "/account");
            }
        } catch (DbException e) {
            log.error(Utils.getErrMessage(e));
            resp.sendError(500);
        }
    }
}
