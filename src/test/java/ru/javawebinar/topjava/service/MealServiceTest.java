package ru.javawebinar.topjava.service;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.slf4j.bridge.SLF4JBridgeHandler;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.test.context.jdbc.SqlConfig;
import org.springframework.test.context.junit4.SpringRunner;
import ru.javawebinar.topjava.MealTestData;
import ru.javawebinar.topjava.model.Meal;
import ru.javawebinar.topjava.util.exception.NotFoundException;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.Month;
import java.util.List;

import static org.junit.Assert.assertThrows;
import static ru.javawebinar.topjava.MealTestData.*;
import static ru.javawebinar.topjava.UserTestData.ADMIN_ID;
import static ru.javawebinar.topjava.UserTestData.USER_ID;

@ContextConfiguration({
        "classpath:spring/spring-app.xml",
        "classpath:spring/spring-db.xml"
})
@RunWith(SpringRunner.class)
@Sql(scripts = "classpath:db/populateDB.sql", config = @SqlConfig(encoding = "UTF-8"))
public class MealServiceTest {

    static {
        // Only for postgres driver logging
        // It uses java.util.logging and logged via jul-to-slf4j bridge
        SLF4JBridgeHandler.install();
    }

    @Autowired
    private MealService service;

    @Test
    public void get() {
        Meal meal = service.get(USER_MEAL_ID, USER_ID);
        assertMatch(meal, MealTestData.userMeal);
    }

    @Test
    public void getForeignMeal() {
        assertThrows(NotFoundException.class, () -> service.get(ADMIN_MEAL_ID, USER_ID));
    }

    @Test
    public void delete() {
        service.delete(USER_MEAL_ID, USER_ID);
        assertThrows(NotFoundException.class, () -> service.get(USER_MEAL_ID, USER_ID));
    }

    @Test
    public void deleteForeignMeal() {
        assertThrows(NotFoundException.class, () -> service.delete(ADMIN_MEAL_ID, USER_ID));
    }

    @Test
    public void getBetweenInclusive() {
        LocalDate from = LocalDate.of(2020, Month.OCTOBER, 17);
        LocalDate to = LocalDate.of(2020, Month.OCTOBER, 18);
        List<Meal> filtered = service.getBetweenInclusive(from, to, USER_ID);
        assertMatch(filtered, userMeal, userMeal3);
    }

    @Test
    public void getAll() {
        assertMatch(service.getAll(USER_ID), userMeal, userMeal2, userMeal3);
    }

    @Test
    public void update() {
        Meal updated = getUpdated();
        service.update(updated, USER_ID);
        assertMatch(service.get(USER_MEAL_ID, USER_ID), updated);
    }

    @Test
    public void updateForeignMeal() {
        assertThrows(NotFoundException.class, () -> service.update(getUpdated(), ADMIN_ID));
    }

    @Test
    public void create() {
        Meal newMeal = getNew();
        Meal created = service.create(newMeal, USER_ID);
        newMeal.setId(created.getId());
        assertMatch(service.get(newMeal.getId(), USER_ID), newMeal);
    }

    @Test
    public void duplicateDateTimeCreate() {
        LocalDateTime time = LocalDateTime.of(2020, Month.OCTOBER, 17, 10, 0);
        assertThrows(DataAccessException.class, () -> service.create(new Meal(null, time, "duplicate", 100), USER_ID));
    }
}