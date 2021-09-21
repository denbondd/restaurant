package com.denbondd.restaurant.web.servlets.cart;

import com.denbondd.restaurant.db.Dao;
import com.denbondd.restaurant.db.entity.Dish;
import com.denbondd.restaurant.db.entity.User;
import com.denbondd.restaurant.exceptions.DbException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

@WebServlet("/cart")
public class CartServlet extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse res) throws ServletException, IOException {
        HttpSession session = req.getSession();
        //map<dish, count>
        Map<Dish, Integer> cart = new HashMap<>();
        if (session.getAttribute("user") == null) {
            if (session.getAttribute("cart") == null) {
                session.setAttribute("cart", cart);
            }
        } else {
            User user = (User) session.getAttribute("user");
            try {
                cart = Dao.getDao().getCartDao().getCart(user.getId());
                session.setAttribute("cart", cart);
            } catch (DbException e) {
                //todo
            }
        }
        req.getRequestDispatcher("/WEB-INF/jsp/cart.jsp").forward(req, res);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse res) throws IOException {
        HttpSession session = req.getSession();
        int dishId = Integer.parseInt(req.getParameter("id"));
        int count = Integer.parseInt(req.getParameter("count"));
        //map<dish, count>
        Map<Dish, Integer> cart;
        try {
            Dish dish = Dao.getDao().getDishDao().getDishById(dishId);
            if (session.getAttribute("user") == null) {
                if (session.getAttribute("cart") != null) {
                    cart = new HashMap<>((Map<Dish, Integer>) session.getAttribute("cart"));
                } else {
                    cart = new HashMap<>();
                }
                cart.put(dish, count);
            session.setAttribute("cart", cart);
            } else {
                User user = (User) session.getAttribute("user");
                Dao.getDao().getCartDao().addDishToCart(user.getId(), dishId, count);
            }
            res.sendRedirect(req.getContextPath() + "/cart");
        } catch (DbException e) {
            e.printStackTrace();
            //todo
        }
    }
}
