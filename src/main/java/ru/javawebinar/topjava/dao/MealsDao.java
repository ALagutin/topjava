package ru.javawebinar.topjava.dao;

import ru.javawebinar.topjava.model.Meal;

import java.util.List;

public interface MealsDao {
    List<Meal> getList();

    Meal create(Meal meal);

    Meal readById(int id);

    Meal update(Meal meal);

    void delete(int id);

    int getCaloriesPerDay();

}
