package ru.javawebinar.topjava;

import ru.javawebinar.topjava.model.Meal;

import java.time.LocalDateTime;
import java.time.Month;
import java.util.Arrays;

import static org.assertj.core.api.Assertions.assertThat;
import static ru.javawebinar.topjava.model.AbstractBaseEntity.START_SEQ;

public class MealTestData {
    public static final int USER_MEAL_ID = START_SEQ + 2;
    public static final int ADMIN_MEAL_ID = START_SEQ + 3;
    public static final int USER_MEAL2_ID = START_SEQ + 4;
    public static final int USER_MEAL3_ID = START_SEQ + 5;

    public static final Meal userMeal = new Meal(USER_MEAL_ID, LocalDateTime.of(2020, Month.OCTOBER, 17, 10, 0), "Обед", 1000);
    public static final Meal adminMeal = new Meal(ADMIN_MEAL_ID, LocalDateTime.of(2020, Month.OCTOBER, 17, 14, 0), "адми поел", 900);
    public static final Meal userMeal2 = new Meal(USER_MEAL2_ID, LocalDateTime.of(2020, Month.OCTOBER, 16, 7, 0), "Завтрак", 900);
    public static final Meal userMeal3 = new Meal(USER_MEAL3_ID, LocalDateTime.of(2020, Month.OCTOBER, 18, 20, 0), "Ужин", 650);

    public static Meal getNew() {
        return new Meal(null, LocalDateTime.now(), "new", 100);
    }

    public static Meal getUpdated() {
        Meal updated = new Meal(userMeal);
        updated.setDescription("updated");
        updated.setCalories(200);
        updated.setDateTime(LocalDateTime.MAX);
        return updated;
    }

    public static void assertMatch(Meal actual, Meal expected) {
        assertThat(actual).usingRecursiveComparison().isEqualTo(expected);
    }

    public static void assertMatch(Iterable<Meal> actual, Meal... expected) {
        assertMatch(actual, Arrays.asList(expected));
    }

    public static void assertMatch(Iterable<Meal> actual, Iterable<Meal> expected) {
        assertThat(actual)
                .usingRecursiveFieldByFieldElementComparator()
                .containsExactlyInAnyOrderElementsOf(expected);
    }
}
