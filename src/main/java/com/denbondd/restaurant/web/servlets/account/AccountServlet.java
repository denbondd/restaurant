package com.denbondd.restaurant.web.servlets.account;

import com.denbondd.restaurant.db.Dao;
import com.denbondd.restaurant.db.entity.Dish;
import com.denbondd.restaurant.db.entity.Receipt;
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
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.util.List;
import java.util.Map;

@WebServlet("/account")
public class AccountServlet extends HttpServlet {

    private static final Logger log = LogManager.getLogger(AccountServlet.class.getName());

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        HttpSession session = req.getSession();
        if (req.getParameter("signout") != null) {
            Utils.logout(req, resp);
            return;
        }
        User user = (User) session.getAttribute("user");
        try {
            List<Receipt> receipts = Dao.getDao().getReceiptDao().getUserReceipts(user.getId());
            session.setAttribute("receipts", receipts);
        } catch (DbException e) {
            log.error(Utils.getErrMessage(e));
            resp.sendError(500);
        }
        req.getRequestDispatcher("/WEB-INF/jsp/account.jsp").forward(req, resp);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        HttpSession session = req.getSession();
        User user = (User) session.getAttribute("user");
        Map<Dish, Integer> cart = (Map<Dish, Integer>) session.getAttribute("cart");
        try {
            Dao.getDao().getCartDao().makeAnOrder(user.getId(), cart);
        } catch (DbException e) {
            log.error(Utils.getErrMessage(e));
            resp.sendError(500);
        }
        resp.sendRedirect(req.getContextPath() + "/account");
    }
}
