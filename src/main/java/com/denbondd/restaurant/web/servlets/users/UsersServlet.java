package com.denbondd.restaurant.web.servlets.users;

import com.denbondd.restaurant.db.Dao;
import com.denbondd.restaurant.db.entity.User;
import com.denbondd.restaurant.exceptions.AppException;
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
import java.util.List;

@WebServlet("/users")
public class UsersServlet extends HttpServlet {

    private static final Logger log = LogManager.getLogger(UsersServlet.class.getName());

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        try {
            List<User> users = Dao.getDao().getUserDao().getAllUsers();
            users.removeIf(user -> user.getId() == ((User) req.getSession().getAttribute("user")).getId());
            req.setAttribute("users", users);
            req.getRequestDispatcher("/WEB-INF/jsp/users.jsp").forward(req, resp);
        } catch (DbException e) {
            log.error(Utils.getErrMessage(e));
            throw new AppException(e);
        }
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        int userId = Integer.parseInt(req.getParameter("user_id"));
        int roleId = Integer.parseInt(req.getParameter("role_id"));
        try {
            if (roleId == -1) {
                Dao.getDao().getUserDao().deleteUser(userId);
            } else {
                Dao.getDao().getUserDao().changeRole(userId, roleId);
            }
            resp.sendRedirect(req.getContextPath() + "/users");
        } catch (DbException e) {
            log.error(Utils.getErrMessage(e));
            throw new AppException(e);
        }
    }
}
