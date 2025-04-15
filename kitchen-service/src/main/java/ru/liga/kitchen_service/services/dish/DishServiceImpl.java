package ru.liga.kitchen_service.services.dish;

import lombok.RequiredArgsConstructor;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Service;
import ru.liga.kitchen_service.mappers.DishMapper;
import ru.liga.kitchen_service.models.entity.Dish;

import java.util.List;

@Service
@RequiredArgsConstructor
public class DishServiceImpl implements DishService {

    private final DishMapper dishMapper;

    public Dish getDishById(Long id) {
        return dishMapper.findById(id);
    }

    public List<Dish> getDishesById(List<Long> id) {
        return dishMapper.findByIdIn(id);
    }

    public int updateDishNums(List<Long> idList, List<Long> newValues){
        return dishMapper.updateDishNums(idList, newValues);
    }
}
