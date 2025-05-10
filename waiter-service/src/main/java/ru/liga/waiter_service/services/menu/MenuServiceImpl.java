package ru.liga.waiter_service.services.menu;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import ru.liga.waiter_service.models.entity.Menu;
import ru.liga.waiter_service.repositories.MenuRepository;

import java.util.List;

@Service
@RequiredArgsConstructor
@Slf4j
public class MenuServiceImpl implements MenuService {

    private final MenuRepository menuRepository;

    @Override
    public List<Menu> getMenu() {
        log.info("Запрос на получение всех блюд из меню");
        return menuRepository.findAll();
    }

    @Override
    public List<Menu> getMenuById(List<Long> ids) {
        log.info("Запрос на получение блюд из меню с id {}", ids);
        return menuRepository.findByIdIn(ids);
    }
}
