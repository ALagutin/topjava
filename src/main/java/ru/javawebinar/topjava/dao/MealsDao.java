package ru.javawebinar.topjava.dao;

import ru.javawebinar.topjava.model.Meal;

import java.util.Map;

public interface MealsDao {
    Map<Integer, Meal> getMap();

    void create(Meal meal);

    Meal readById(int id);

    void update(Meal meal);

    void delete(int id);

    int getId();

}
