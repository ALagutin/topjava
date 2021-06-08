package ru.javawebinar.topjava.web;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import ru.javawebinar.topjava.model.Meal;
import ru.javawebinar.topjava.model.MealTo;
import ru.javawebinar.topjava.util.MealsUtil;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.time.LocalTime;
import java.util.List;

public class MealServlet extends HttpServlet {
    private static final Logger log = LoggerFactory.getLogger(MealServlet.class);

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        log.debug("forward to users");

        List<Meal> meals = MealsUtil.initListMeals();
        List<MealTo> mealsTo = MealsUtil.filteredByStreams(meals, LocalTime.MIN, LocalTime.MAX, MealTo.CALORIES_PER_DAY);
        req.setAttribute("mealsTo", mealsTo);
        req.getRequestDispatcher("meals.jsp").forward(req, resp);
    }
}
