package ru.liga.waiter_service.services;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;

import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import ru.liga.waiter_service.models.entity.Menu;
import ru.liga.waiter_service.repositories.MenuRepository;
import ru.liga.waiter_service.services.menu.MenuServiceImpl;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;


@ExtendWith(MockitoExtension.class)
public class MenuServiceImplTest {

    @Mock
    private MenuRepository menuRepository;

    @InjectMocks
    private MenuServiceImpl menuService;

    @Test
    void shouldReturnAllMenu() {
        // Подготовка
        List<Menu> expected = new ArrayList<>(List.of(
                new Menu(1L, "Dish1", BigDecimal.valueOf(1), null),
                new Menu(2L, "Dish2", BigDecimal.valueOf(2), null)));
        Mockito.when(menuRepository.findAll()).thenReturn(expected);

        // Действие
        List<Menu> actual = menuService.getMenu();

        // Проверка
        Assertions.assertIterableEquals(expected, actual);
    }

    @Test
    void shouldReturnMenuById() {
        // Подготовка
        List<Menu> expected = new ArrayList<>(List.of(
                new Menu(1L, "Dish1", BigDecimal.valueOf(1), null),
                new Menu(2L, "Dish2", BigDecimal.valueOf(2), null)));
        List<Long> idList = new ArrayList<>(List.of(1L, 2L));
        Mockito.when(menuRepository.findByIdIn(idList)).thenReturn(expected);

        // Действие
        List<Menu> actual = menuRepository.findByIdIn(idList);

        // Проверка
        Assertions.assertIterableEquals(expected, actual);
    }
}
