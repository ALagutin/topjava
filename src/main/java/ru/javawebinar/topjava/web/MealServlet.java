package ru.javawebinar.topjava.web;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import ru.javawebinar.topjava.dao.MealsDao;
import ru.javawebinar.topjava.dao.MealsDaoInMemoryImpl;
import ru.javawebinar.topjava.model.Meal;
import ru.javawebinar.topjava.model.MealTo;
import ru.javawebinar.topjava.repository.MealsMemoryRepo;
import ru.javawebinar.topjava.util.MealsUtil;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;

public class MealServlet extends HttpServlet {
    private final Logger log = LoggerFactory.getLogger(MealServlet.class);
    private final String INSERT_OR_EDIT = "/editMeal.jsp";
    private final String LIST_MEAL = "/meals.jsp";
    private final MealsDao daoImpl = new MealsDaoInMemoryImpl();

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
            req.setAttribute("meal", daoImpl.readById(Integer.parseInt(req.getParameter("id"))));
        } else if (action.equalsIgnoreCase("delete")) {
            log.debug("forward to delete meal");
            daoImpl.delete(Integer.parseInt(req.getParameter("id")));
            forward = LIST_MEAL;
            req.setAttribute("mealsTo", getListMealTo());
        } else if (action.equalsIgnoreCase("create")) {
            log.debug("forward to create meal");
            forward = INSERT_OR_EDIT;
            req.setAttribute("meal", new Meal(-1, LocalDateTime.now(), "", 0));
        } else
            return;

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

        Meal meal = new Meal(id < 0 ? daoImpl.getId() : id, dateTime, description, calories);

        if (id < 0)
            daoImpl.create(meal);
        else
            daoImpl.update(meal);

        req.setAttribute("mealsTo", getListMealTo());
        req.getRequestDispatcher(LIST_MEAL).forward(req, resp);
    }

    private List<MealTo> getListMealTo() {
        List<Meal> meals = new ArrayList<>(daoImpl.getMap().values());
        return MealsUtil.filteredByStreams(meals, LocalTime.MIN, LocalTime.MAX, MealsMemoryRepo.CALORIES_PER_DAY);
    }
}
