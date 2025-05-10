package ru.liga.kitchen_service.services.dish;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import ru.liga.kitchen_service.mappers.DishMapper;
import ru.liga.kitchen_service.models.entity.Dish;

import java.util.List;


@Service
@RequiredArgsConstructor
@Slf4j
public class DishServiceImpl implements DishService {

    private final DishMapper dishMapper;

    @Override
    public Dish getDishById(Long id) {
        return dishMapper.findById(id);
    }

    @Override
    public List<Dish> getDishesById(List<Long> ids) {
        return dishMapper.findByIdIn(ids);
    }

    @Override
    public int updateDishNums(List<Long> idList, List<Long> newValues) {
        log.info("Обновление кухней количества блюд на складе после успешного оформления заказа");
        return dishMapper.updateDishNums(idList, newValues);
    }
}
