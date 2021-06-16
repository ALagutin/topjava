package ru.javawebinar.topjava.web;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import ru.javawebinar.topjava.dao.MealsDao;
import ru.javawebinar.topjava.dao.MealsDaoInMemory;
import ru.javawebinar.topjava.model.Meal;
import ru.javawebinar.topjava.model.MealTo;
import ru.javawebinar.topjava.util.MealsUtil;

import javax.servlet.ServletConfig;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.List;

public class MealServlet extends HttpServlet {
    private static final String INSERT_OR_EDIT = "/editMeal.jsp";
    private static final String LIST_MEAL = "/meals.jsp";
    private Logger log;
    private MealsDao dao;

    @Override
    public void init(ServletConfig config) throws ServletException {
        super.init(config);
        log = LoggerFactory.getLogger(MealServlet.class);
        dao = new MealsDaoInMemory();
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        req.setCharacterEncoding("UTF-8");
        resp.setCharacterEncoding("UTF-8");

        String forward;
        String action = req.getParameter("action");

        if (action == null) {
            log.debug("forward to meals");
            forward = LIST_MEAL;
            req.setAttribute("mealsTo", getListMealTo());
        } else if (action.equalsIgnoreCase("edit")) {
            log.debug("forward to edit meal");
            forward = INSERT_OR_EDIT;
            req.setAttribute("meal", dao.readById(Integer.parseInt(req.getParameter("id"))));
        } else if (action.equalsIgnoreCase("delete")) {
            log.debug("forward to delete meal");
            dao.delete(Integer.parseInt(req.getParameter("id")));
            forward = LIST_MEAL;
            req.setAttribute("mealsTo", getListMealTo());
        } else if (action.equalsIgnoreCase("create")) {
            log.debug("forward to create meal");
            forward = INSERT_OR_EDIT;
            req.setAttribute("meal", new Meal(-1, LocalDateTime.now(), "", 0));
        } else {
            return;
        }

        req.getRequestDispatcher(forward).forward(req, resp);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        log.debug("forward to meal");

        req.setCharacterEncoding("UTF-8");
        resp.setCharacterEncoding("UTF-8");

        int id = Integer.parseInt(req.getParameter("id"));
        LocalDateTime dateTime = LocalDateTime.parse(req.getParameter("dateTime"));
        String description = req.getParameter("description");
        int calories = Integer.parseInt(req.getParameter("calories"));

        Meal meal = new Meal(id, dateTime, description, calories);

        if (id < 0) {
            dao.create(meal);
        } else {
            dao.update(meal);
        }
        req.setAttribute("mealsTo", getListMealTo());
        req.getRequestDispatcher(LIST_MEAL).forward(req, resp);
    }

    private List<MealTo> getListMealTo() {
        return MealsUtil.filteredByStreams(dao.getList(), LocalTime.MIN, LocalTime.MAX, dao.getCaloriesPerDay());
    }
}
