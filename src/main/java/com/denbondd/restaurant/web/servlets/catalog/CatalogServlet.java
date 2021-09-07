package com.denbondd.restaurant.web.servlets.catalog;

import com.denbondd.restaurant.db.Dao;
import com.denbondd.restaurant.db.entity.Category;
import com.denbondd.restaurant.db.entity.Dish;
import com.denbondd.restaurant.exceptions.DbException;
import com.denbondd.restaurant.util.SqlUtils;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.util.List;

@WebServlet("/catalog")
public class CatalogServlet extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String category = req.getParameter("category");
        String sortBy = req.getParameter("sortBy");
        try {
            if (req.getAttribute("categories") == null) {
                List<Category> categories = Dao.getDao().getCategoryDao().getAllCategories();
                req.setAttribute("categories", categories);
            }

            List<Dish> dishes;
            if (category != null && !category.isEmpty()
                    && sortBy != null && !sortBy.isEmpty() && SqlUtils.sortingTypes.containsValue(sortBy)) {
                dishes = Dao.getDao().getDishDao().getSortedDishesFromCategory(Integer.parseInt(category), sortBy);
            } else if (category != null && !category.isEmpty()) {
                dishes = Dao.getDao().getDishDao().getDishesFromCategory(Integer.parseInt(category));
            } else if (sortBy != null && !sortBy.isEmpty() && SqlUtils.sortingTypes.containsValue(sortBy)) {
                dishes = Dao.getDao().getDishDao().getSortedDishes(sortBy);
            } else {
                dishes = Dao.getDao().getDishDao().getAllDishes();
            }

            req.getSession().setAttribute("dishes", dishes);
            req.getRequestDispatcher("/WEB-INF/jsp/catalog.jsp").forward(req, resp);
        } catch (DbException e) {
            //TODO
            e.printStackTrace();
        }
    }
}
