package ru.javawebinar.topjava.dao;

import ru.javawebinar.topjava.model.Meal;
import ru.javawebinar.topjava.repository.MealsMemoryRepo;

import java.util.Map;

public class MealsDaoInMemoryImpl implements MealsDao {
    @Override
    public Map<Integer, Meal> getMap() {
        return MealsMemoryRepo.getMealsMap();
    }

    @Override
    public void create(Meal meal) {
        getMap().put(meal.getId(), meal);
    }

    @Override
    public Meal readById(int id) {
        return MealsMemoryRepo.getMealById(id);
    }

    @Override
    public void update(Meal meal) {
        getMap().put(meal.getId(), meal);
    }

    @Override
    public void delete(int id) {
        getMap().remove(id);
    }

    @Override
    public int getId() {
        return MealsMemoryRepo.getNewId();
    }
}
