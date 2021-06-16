package ru.javawebinar.topjava.dao;

import ru.javawebinar.topjava.model.Meal;

import java.time.LocalDateTime;
import java.time.Month;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentMap;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.function.Function;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class MealsDaoInMemory implements MealsDao {

    private final AtomicInteger lastMealId = new AtomicInteger(-1);
    private final Map<Integer, Meal> mealsMap = getMealsMap();

    @Override
    public List<Meal> getList() {
        return new ArrayList<>(mealsMap.values());
    }

    @Override
    public Meal create(Meal meal) {
        int newId = getNewId();
        Meal newMeal = new Meal(newId, meal.getDateTime(), meal.getDescription(), meal.getCalories());
        mealsMap.put(newId, newMeal);
        return newMeal;
    }

    @Override
    public Meal readById(int id) {
        return mealsMap.get(id);
    }

    @Override
    public Meal update(Meal meal) {
        int id = meal.getId();
        if (mealsMap.containsKey(id)) {
            mealsMap.put(id, meal);
            return meal;
        }
        else {
            return null;
        }

    }

    @Override
    public void delete(int id) {
        mealsMap.remove(id);
    }

    @Override
    public int getCaloriesPerDay() {
        return 2000;
    }

    private int getNewId() {
        return lastMealId.incrementAndGet();
    }

    private ConcurrentMap<Integer, Meal> getMealsMap() {
        return Stream.of(
                new Meal(getNewId(), LocalDateTime.of(2020, Month.JANUARY, 30, 10, 0), "Завтрак", 500),
                new Meal(getNewId(), LocalDateTime.of(2020, Month.JANUARY, 30, 13, 0), "Обед", 1000),
                new Meal(getNewId(), LocalDateTime.of(2020, Month.JANUARY, 30, 20, 0), "Ужин", 500),
                new Meal(getNewId(), LocalDateTime.of(2020, Month.JANUARY, 31, 0, 0), "Еда на граничное значение", 100),
                new Meal(getNewId(), LocalDateTime.of(2020, Month.JANUARY, 31, 10, 0), "Завтрак", 1000),
                new Meal(getNewId(), LocalDateTime.of(2020, Month.JANUARY, 31, 13, 0), "Обед", 500),
                new Meal(getNewId(), LocalDateTime.of(2020, Month.JANUARY, 31, 20, 0), "Ужин", 410))
                .collect(Collectors.toConcurrentMap(Meal::getId, Function.identity()));
    }
}
