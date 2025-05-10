package ru.liga.kitchen_service.services;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import ru.liga.kitchen_service.mappers.DishMapper;
import ru.liga.kitchen_service.models.entity.Dish;
import ru.liga.kitchen_service.services.dish.DishServiceImpl;

import java.util.ArrayList;
import java.util.List;

import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class DishServiceImplTest {

    @Mock
    private DishMapper dishMapper;

    @InjectMocks
    private DishServiceImpl dishService;

    @Test
    void shouldGetDishById() {
        // Подготовка
        Dish expected = new Dish(1L, 1L, "Dish1", null);
        when(dishMapper.findById(1L)).thenReturn(expected);

        // Действие
        Dish actual = dishService.getDishById(1L);

        // Проверка
        Assertions.assertEquals(expected, actual);
    }

    @Test
    void shouldGetDishesById() {
        // Подготовка
        List<Dish> expected = new ArrayList<>(List.of(
                new Dish(1L, 1L, "Dish1", null),
                new Dish(2L, 2L, "Dish2", null)));
        List<Long> dishIdList = new ArrayList<>(List.of(1L, 2L));
        when(dishMapper.findByIdIn(dishIdList)).thenReturn(expected);

        // Действие
        List<Dish> actual = dishService.getDishesById(dishIdList);

        // Проверка
        Assertions.assertIterableEquals(expected, actual);
    }

    @Test
    void shouldUpdateDishNums() {
        // Подготовка
        List<Long> dishIdList = new ArrayList<>(List.of(1L, 2L));
        List<Long> newValues = new ArrayList<>(List.of(0L, 0L));
        when(dishMapper.updateDishNums(dishIdList, newValues)).thenReturn(2);

        // Действие
        int updatedDishes = dishService.updateDishNums(dishIdList, newValues);

        // Проверка
        Assertions.assertEquals(2, updatedDishes);
    }
}
