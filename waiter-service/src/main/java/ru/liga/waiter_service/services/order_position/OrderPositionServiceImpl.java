package ru.liga.waiter_service.services.order_position;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import ru.liga.waiter_service.models.dto.WaiterOrderRequest;
import ru.liga.waiter_service.models.entity.Menu;
import ru.liga.waiter_service.models.entity.OrderPosition;
import ru.liga.waiter_service.models.entity.WaiterOrder;
import ru.liga.waiter_service.repositories.OrderPositionRepository;
import ru.liga.waiter_service.services.menu.MenuService;
import ru.liga.waiter_service.utils.MenuPositionNotFoundException;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class OrderPositionServiceImpl implements OrderPositionService {

    private final OrderPositionRepository orderPositionRepository;
    private final MenuService menuService;

    public void addOrderPositions(WaiterOrder waiterOrder, WaiterOrderRequest waiterOrderRequest) {
        // заказы, которые пришли в запросе
        Map<Long, Long> orderPositions = waiterOrderRequest.getOrderPositions();
        // заказы из запроса, которые существуют в БД
        List<Menu> menuList = menuService.getMenuById(new ArrayList<>(orderPositions.keySet()));
        Set<Long> menuIdSet = new HashSet(menuList
                .stream()
                .map(Menu::getId)
                .collect(Collectors.toSet()));

        // если блюд с некоторыми переданными id не существует
        Set<Long> missingIds = new HashSet<>();
        if(menuIdSet.size() != orderPositions.size()){
            for(long orderId : orderPositions.keySet()){
                if(!menuIdSet.contains(orderId)){
                    missingIds.add(orderId);
                }
            }
            throw new MenuPositionNotFoundException("Menu positions with id " + missingIds + " not found");
        }

        // если все id блюд корректны
        List<OrderPosition> positions = new ArrayList<>();
        for (Menu menu : menuList) {
            OrderPosition orderPosition = OrderPosition
                    .builder()
                    .dishNum(orderPositions.get(menu.getId()))
                    .order(waiterOrder)
                    .menuPosition(menu)
                    .build();
            positions.add(orderPosition);
        }
        orderPositionRepository.saveAll(positions);
    }
}
