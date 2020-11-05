package ru.javawebinar.topjava.repository.jdbc;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Profile;
import org.springframework.dao.support.DataAccessUtils;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.jdbc.core.namedparam.SqlParameterSource;
import org.springframework.jdbc.core.simple.SimpleJdbcInsert;
import org.springframework.stereotype.Repository;
import ru.javawebinar.topjava.Profiles;
import ru.javawebinar.topjava.model.Meal;
import ru.javawebinar.topjava.repository.MealRepository;

import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.util.List;

@Repository
public abstract class JdbcMealRepository {

    private static final RowMapper<Meal> ROW_MAPPER = BeanPropertyRowMapper.newInstance(Meal.class);

    private final JdbcTemplate jdbcTemplate;

    private final NamedParameterJdbcTemplate namedParameterJdbcTemplate;

    private final SimpleJdbcInsert insertMeal;

    @Autowired
    public JdbcMealRepository(JdbcTemplate jdbcTemplate, NamedParameterJdbcTemplate namedParameterJdbcTemplate) {
        this.insertMeal = new SimpleJdbcInsert(jdbcTemplate)
                .withTableName("meals")
                .usingGeneratedKeyColumns("id");

        this.jdbcTemplate = jdbcTemplate;
        this.namedParameterJdbcTemplate = namedParameterJdbcTemplate;
    }

    public Meal save(Meal meal, SqlParameterSource map) {

        if (meal.isNew()) {
            Number newId = insertMeal.executeAndReturnKey(map);
            meal.setId(newId.intValue());
        } else {
            if (namedParameterJdbcTemplate.update("" +
                    "UPDATE meals " +
                    "   SET description=:description, calories=:calories, date_time=:date_time " +
                    " WHERE id=:id AND user_id=:user_id", map) == 0) {
                return null;
            }
        }
        return meal;
    }


    public boolean delete(int id, int userId) {
        return jdbcTemplate.update("DELETE FROM meals WHERE id=? AND user_id=?", id, userId) != 0;
    }


    public Meal get(int id, int userId) {
        List<Meal> meals = jdbcTemplate.query(
                "SELECT * FROM meals WHERE id = ? AND user_id = ?", ROW_MAPPER, id, userId);
        return DataAccessUtils.singleResult(meals);
    }


    public List<Meal> getAll(int userId) {
        return jdbcTemplate.query(
                "SELECT * FROM meals WHERE user_id=? ORDER BY date_time DESC", ROW_MAPPER, userId);
    }


    public <T> List<Meal> getBetweenHalfOpen(T startDateTime, T endDateTime, int userId) {
        return jdbcTemplate.query(
                "SELECT * FROM meals WHERE user_id=?  AND date_time >=  ? AND date_time < ? ORDER BY date_time DESC",
                ROW_MAPPER, userId, startDateTime, endDateTime);
    }

    @Profile(Profiles.POSTGRES_DB)
    @Repository
    public static class PostgresJdbcMealRepository extends JdbcMealRepository implements MealRepository {

        public PostgresJdbcMealRepository(JdbcTemplate jdbcTemplate, NamedParameterJdbcTemplate namedParameterJdbcTemplate) {
            super(jdbcTemplate, namedParameterJdbcTemplate);
        }

        @Override
        public Meal save(Meal meal, int userId) {
            MapSqlParameterSource map = new MapSqlParameterSource()
                    .addValue("id", meal.getId())
                    .addValue("description", meal.getDescription())
                    .addValue("calories", meal.getCalories())
                    .addValue("date_time", meal.getDateTime())
                    .addValue("user_id", userId);
            return super.save(meal, map);
        }

        @Override
        public List<Meal> getBetweenHalfOpen(LocalDateTime startDateTime, LocalDateTime endDateTime, int userId) {
            return super.getBetweenHalfOpen(startDateTime, endDateTime, userId);
        }


    }

    @Profile(Profiles.HSQL_DB)
    @Repository
    public static class HsqldbJdbcMealRepository extends JdbcMealRepository implements MealRepository {

        public HsqldbJdbcMealRepository(JdbcTemplate jdbcTemplate, NamedParameterJdbcTemplate namedParameterJdbcTemplate) {
            super(jdbcTemplate, namedParameterJdbcTemplate);
        }

        @Override
        public Meal save(Meal meal, int userId) {
            MapSqlParameterSource map = new MapSqlParameterSource()
                    .addValue("id", meal.getId())
                    .addValue("description", meal.getDescription())
                    .addValue("calories", meal.getCalories())
                    .addValue("date_time", Timestamp.valueOf(meal.getDateTime()))
                    .addValue("user_id", userId);
            return super.save(meal, map);
        }

        @Override
        public List<Meal> getBetweenHalfOpen(LocalDateTime startDateTime, LocalDateTime endDateTime, int userId) {
            return super.getBetweenHalfOpen(Timestamp.valueOf(startDateTime), Timestamp.valueOf(endDateTime), userId);
        }
    }
}
