package com.denbondd.restaurant.web.servlets.receipts;

import com.denbondd.restaurant.db.Dao;
import com.denbondd.restaurant.db.entity.Receipt;
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
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.util.List;

@WebServlet("/receipts")
public class ReceiptsServlet extends HttpServlet {

    private static final Logger log = LogManager.getLogger(ReceiptsServlet.class.getName());

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        HttpSession session = req.getSession();
        String filterParam = req.getParameter("filter");
        String filterAttr = (String) session.getAttribute("filter");
        String filter = filterParam == null ? filterAttr == null ? "all" : filterAttr : filterParam;
        session.setAttribute("filter", filter);
        User user = (User) session.getAttribute("user");
        try {
            List<Receipt> receipts;
            switch (filter) {
                case "all":
                    receipts = Dao.getDao().getReceiptDao().getAllReceipts();
                    break;
                case "not-approved":
                    receipts = Dao.getDao().getReceiptDao().getNotApproved();
                    break;
                case "approved-by-me":
                    receipts = Dao.getDao().getReceiptDao().getAllReceiptsAcceptedBy(user.getId());
                    break;
                default:
                    log.error("unknown filter = " + filter);
                    throw new AppException("unknown filter");
            }
            session.setAttribute("receipts", receipts);
            req.getRequestDispatcher("/WEB-INF/jsp/receipts.jsp").forward(req, resp);
        } catch (DbException e) {
            log.error(Utils.getErrMessage(e));
            throw new AppException(e);
        }
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        User user = (User) req.getSession().getAttribute("user");
        long receiptId = Long.parseLong(req.getParameter("id"));
        long newStatusId = Long.parseLong(req.getParameter("status-id"));
        try {
            Dao.getDao().getReceiptDao().changeStatus(receiptId, newStatusId, user.getId());
        } catch (DbException e) {
            log.error(Utils.getErrMessage(e));
            throw new AppException(e);
        }
        resp.sendRedirect(req.getContextPath() + "/receipts");
    }
}
