package com.denbondd.restaurant.web.servlets.account;

import com.denbondd.restaurant.db.Dao;
import com.denbondd.restaurant.db.entity.User;
import com.denbondd.restaurant.exceptions.DbException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@WebServlet("/account/signup")
public class SignUpServlet extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        req.getRequestDispatcher("/WEB-INF/jsp/signup.jsp").forward(req, resp);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        String login = req.getParameter("login");
        char[] password = req.getParameter("password").toCharArray();
        try {
            if (!Dao.getDao().getUserDao().isLoginUnique(login)) {
                System.out.println("duplicate login");
                resp.sendRedirect(req.getContextPath() + "/account/signup");
                return;
            }
            User user = Dao.getDao().getUserDao().signUp(login, password);
            req.getSession().setAttribute("user", user);
            resp.sendRedirect(req.getContextPath() + "/account");
        } catch (DbException e) {
            //TODO
            e.printStackTrace();
        }
    }
}
