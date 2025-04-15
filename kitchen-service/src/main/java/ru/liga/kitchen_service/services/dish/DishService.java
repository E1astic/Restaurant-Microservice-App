package ru.liga.kitchen_service.services.dish;

import org.apache.ibatis.annotations.Param;
import ru.liga.kitchen_service.models.entity.Dish;

import java.util.List;

public interface DishService {

    Dish getDishById(Long id);

    List<Dish> getDishesById(List<Long> id);

    int updateDishNums(List<Long> idList, List<Long> newValues);
}
