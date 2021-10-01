package com.denbondd.restaurant.web.servlets.dishcount;

import com.denbondd.restaurant.db.Dao;
import com.denbondd.restaurant.exceptions.AppException;
import com.denbondd.restaurant.exceptions.DbException;
import com.denbondd.restaurant.util.Utils;
import javafx.util.Pair;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Map;

@WebServlet("/dish_count")
public class DishCountServlet extends HttpServlet {

    private static final Logger log = LogManager.getLogger(DishCountServlet.class.getName());

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        try {
            Map<Integer, Pair<String, Integer>> ordersCount = Dao.getDao().getDishDao().getDishesOrderCount();
            req.getSession().setAttribute("ordersCount", ordersCount);
            req.getRequestDispatcher("/WEB-INF/jsp/dish_count.jsp").forward(req, resp);
        } catch (DbException e) {
            log.error(Utils.getErrMessage(e));
            throw new AppException();
        }
    }
}
