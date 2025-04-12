package ru.liga.waiter_service.services.menu;

import ru.liga.waiter_service.models.entity.Menu;

import java.util.List;

public interface MenuService {

    List<Menu> getMenu();

    List<Menu> getMenuById(List<Long> id);
}
