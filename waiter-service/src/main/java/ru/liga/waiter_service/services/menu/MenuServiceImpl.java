package ru.liga.waiter_service.services.menu;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import ru.liga.waiter_service.models.entity.Menu;
import ru.liga.waiter_service.repositories.MenuRepository;

import java.util.List;

@Service
@RequiredArgsConstructor
public class MenuServiceImpl implements MenuService {

    private final MenuRepository menuRepository;

    public List<Menu> getMenu(){
        return menuRepository.findAll();
    }

    public List<Menu> getMenuById(List<Long> id){
        return menuRepository.findByIdIn(id);
    }
}
