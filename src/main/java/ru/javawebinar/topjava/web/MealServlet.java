package ru.javawebinar.topjava.web;

import org.slf4j.Logger;
import ru.javawebinar.topjava.dao.MealDao;
import ru.javawebinar.topjava.dao.MealDaoInMemory;
import ru.javawebinar.topjava.model.Meal;
import ru.javawebinar.topjava.model.MealTo;
import ru.javawebinar.topjava.util.MealsUtil;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.List;

import static org.slf4j.LoggerFactory.getLogger;

public class MealServlet extends HttpServlet {
    private static final long serialVersionUID = 1L;
    private static final Logger log = getLogger(MealServlet.class);
    private static final String INSERT_OR_EDIT = "/meal-form.jsp";
    private static final String LIST_MEAL = "/meals.jsp";
    private final MealDao dao;

    public MealServlet() {
        dao = new MealDaoInMemory();
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        request.setCharacterEncoding("UTF-8");
        log.debug("forward to meals");

        String action = request.getParameter("action");
        if (action == null) {
            action = "";
        }

        log.info(action);
        try {
            if (action.equalsIgnoreCase("delete")) {
                int id = Integer.parseInt(request.getParameter("id"));
                dao.delete(id);
                response.sendRedirect("meals");
            } else if (action.equalsIgnoreCase("edit")) {
                int id = Integer.parseInt(request.getParameter("id"));
                Meal meal = dao.getById(id);
                request.setAttribute("meal", meal);
                request.getRequestDispatcher(INSERT_OR_EDIT).forward(request, response);
            } else if (action.equalsIgnoreCase("add")) {
                request.setAttribute("meal", new Meal());
                request.getRequestDispatcher(INSERT_OR_EDIT).forward(request, response);
            } else {
                request.setAttribute("meals", getMealsTo());
                request.getRequestDispatcher(LIST_MEAL).forward(request, response);
            }
        } catch (Exception e) {
            log.error("Dao error", e);
        }
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        request.setCharacterEncoding("UTF-8");
        Meal meal = new Meal();
        meal.setDateTime(LocalDateTime.parse(request.getParameter("date")));
        meal.setDescription(request.getParameter("description"));
        meal.setCalories(Integer.parseInt(request.getParameter("calories")));
        String id = request.getParameter("id");
        if (id == null || id.isEmpty()) {
            dao.add(meal);
        } else {
            meal.setId(Integer.parseInt(id));
            dao.update(meal);
        }

        request.setAttribute("meals", getMealsTo());
        request.getRequestDispatcher(LIST_MEAL).forward(request, response);
    }

    private List<MealTo> getMealsTo() {
        return MealsUtil.filteredByStreams(dao.findAll(), LocalTime.MIN, LocalTime.MAX, 2000);
    }
}
