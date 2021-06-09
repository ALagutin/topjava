package ru.javawebinar.topjava.repository;

import ru.javawebinar.topjava.model.Meal;

import java.time.LocalDateTime;
import java.time.Month;
import java.util.concurrent.ConcurrentMap;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.function.Function;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class MealsMemoryRepo {
    private static volatile ConcurrentMap<Integer, Meal> mealsMap;
    private static final AtomicInteger lastMealId = new AtomicInteger(-1);
    public static final int CALORIES_PER_DAY = 2000;


    public static int getNewId() {
        return lastMealId.incrementAndGet();
    }

    public static ConcurrentMap<Integer, Meal> getMealsMap() {
        if (mealsMap == null)
            mealsMap = Stream.of(
                    new Meal(getNewId(), LocalDateTime.of(2020, Month.JANUARY, 30, 10, 0), "Завтрак", 500),
                    new Meal(getNewId(), LocalDateTime.of(2020, Month.JANUARY, 30, 13, 0), "Обед", 1000),
                    new Meal(getNewId(), LocalDateTime.of(2020, Month.JANUARY, 30, 20, 0), "Ужин", 500),
                    new Meal(getNewId(), LocalDateTime.of(2020, Month.JANUARY, 31, 0, 0), "Еда на граничное значение", 100),
                    new Meal(getNewId(), LocalDateTime.of(2020, Month.JANUARY, 31, 10, 0), "Завтрак", 1000),
                    new Meal(getNewId(), LocalDateTime.of(2020, Month.JANUARY, 31, 13, 0), "Обед", 500),
                    new Meal(getNewId(), LocalDateTime.of(2020, Month.JANUARY, 31, 20, 0), "Ужин", 410)
            ).collect(Collectors.toConcurrentMap(Meal::getId, Function.identity()));

        return mealsMap;
    }

    public static Meal getMealById(int id) {
        if (mealsMap == null)
            getMealsMap();

        return mealsMap.get(id);
    }
}