package ru.javawebinar.topjava.repository.jpa;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.test.context.jdbc.SqlConfig;
import org.springframework.test.context.junit4.SpringRunner;
import ru.javawebinar.topjava.UserTestData;
import ru.javawebinar.topjava.model.Meal;
import ru.javawebinar.topjava.repository.MealRepository;

import java.time.LocalDateTime;
import java.time.Month;

import static org.junit.Assert.*;
import static ru.javawebinar.topjava.MealTestData.*;
import static ru.javawebinar.topjava.UserTestData.ADMIN_ID;
import static ru.javawebinar.topjava.UserTestData.USER_ID;

@ContextConfiguration({
        "classpath:spring/spring-app.xml",
        "classpath:spring/spring-db.xml"
})
@RunWith(SpringRunner.class)
@Sql(scripts = "classpath:db/populateDB.sql", config = @SqlConfig(encoding = "UTF-8"))
public class JpaMealRepositoryTest {

    @Autowired
    private MealRepository repository;

    @Test
    public void save() {
        Meal newMeal = repository.save(getNew(), USER_ID);
        int newId = newMeal.id();
        Meal copy = getNew();
        copy.setId(newId);
        copy.setUser(UserTestData.user);
        MEAL_MATCHER.assertMatch(newMeal, copy);
        MEAL_MATCHER.assertMatch(repository.get(newId, USER_ID), copy);
    }

    @Test
    public void updateNotOwn() {
        assertNull(repository.save(meal1, ADMIN_ID));
    }

    @Test
    public void delete() {
        assertNotNull(repository.get(MEAL1_ID, USER_ID));
        repository.delete(MEAL1_ID, USER_ID);
        assertNull(repository.get(MEAL1_ID, USER_ID));
    }

    @Test
    public void deleteNotOwn() {
        assertFalse(repository.delete(MEAL1_ID, ADMIN_ID));
    }

    @Test
    public void get() {
        MEAL_MATCHER.assertMatch(repository.get(MEAL1_ID, USER_ID), meal1);
    }

    @Test
    public void getNotOwn() {
        assertNull(repository.get(MEAL1_ID, ADMIN_ID));
    }

    @Test
    public void getAll() {
        MEAL_MATCHER.assertMatch(repository.getAll(USER_ID), meals);
    }

    @Test
    public void getBetweenHalfOpen() {
        MEAL_MATCHER.assertMatch(repository.getBetweenHalfOpen(
                LocalDateTime.of(2020, Month.JANUARY, 31, 0, 0),
                LocalDateTime.of(2020, Month.JANUARY, 31, 13, 0), USER_ID),
                meal5, meal4);
    }
}