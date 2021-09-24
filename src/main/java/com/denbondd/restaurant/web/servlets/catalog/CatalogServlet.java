package com.denbondd.restaurant.web.servlets.catalog;

import com.denbondd.restaurant.db.Dao;
import com.denbondd.restaurant.db.entity.Category;
import com.denbondd.restaurant.db.entity.Dish;
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

@WebServlet("/catalog")
public class CatalogServlet extends HttpServlet {

    private static final Logger log = LogManager.getLogger(CatalogServlet.class.getName());

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        int category = Integer.parseInt(req.getParameter("category"));
        int page = Integer.parseInt(req.getParameter("page"));
        int dishesInPage = Integer.parseInt(req.getParameter("dishesOnPage"));
        String sortBy = req.getParameter("sortBy");
        HttpSession session = req.getSession();
        try {
            if (session.getAttribute("categories") == null) {
                List<Category> categories = Dao.getDao().getCategoryDao().getAllCategories();
                session.setAttribute("categories", categories);
            }

            List<Dish> dishes;
            int maxPage;
            if (category == 0) {
                dishes = Dao.getDao().getDishDao().getSortedDishesOnPage(sortBy, dishesInPage, page);
                maxPage = Dao.getDao().getDishDao().getDishesNumber();
            } else {
                dishes = Dao.getDao().getDishDao().getSortedDishesFromCategoryOnPage(category, sortBy, dishesInPage, page);
                maxPage = Dao.getDao().getDishDao().getDishesNumberInCategory(category);
            }
            maxPage /= dishesInPage;

            session.setAttribute("maxPage", maxPage);
            session.setAttribute("dishes", dishes);
            req.getRequestDispatcher("/WEB-INF/jsp/catalog.jsp").forward(req, resp);
        } catch (DbException e) {
            log.error(Utils.getErrMessage(e));
            resp.sendError(500);
        }
    }
}
