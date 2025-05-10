package ru.liga.waiter_service.services.order_position;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import ru.liga.waiter_service.models.dto.WaiterOrderRequest;
import ru.liga.waiter_service.models.entity.Menu;
import ru.liga.waiter_service.models.entity.OrderPosition;
import ru.liga.waiter_service.models.entity.WaiterOrder;
import ru.liga.waiter_service.repositories.OrderPositionRepository;
import ru.liga.waiter_service.services.menu.MenuService;
import ru.liga.waiter_service.exceptions.MenuPositionNotFoundException;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Slf4j
public class OrderPositionServiceImpl implements OrderPositionService {

    private final OrderPositionRepository orderPositionRepository;

    private final MenuService menuService;

    @Override
    public void addOrderPositions(WaiterOrder waiterOrder, WaiterOrderRequest waiterOrderRequest) {
        log.info("Добавление в базу данных официантов всех позиций заказа с id = {}", waiterOrder.getId());

        // заказы, которые пришли в запросе
        Map<Long, Long> orderPositions = waiterOrderRequest.getOrderPositions();

        // заказы из запроса, которые существуют в БД
        List<Menu> menuList = menuService.getMenuById(new ArrayList<>(orderPositions.keySet()));
        Set<Long> menuIdSet = menuList
                .stream()
                .map(Menu::getId)
                .collect(Collectors.toSet());

        checkExistenceMenuPositions(orderPositions, menuIdSet);

        List<OrderPosition> positions = createOrderPositions(menuList, orderPositions, waiterOrder);

        orderPositionRepository.saveAll(positions);
    }

    /**
     * Проверяет наличие всех запрошенных позиций в меню.
     * <p>
     * Метод сравнивает идентификаторы блюд из запроса с доступными в меню. Если какая-либо позиция отсутствует,
     * выбрасывается исключение {@link MenuPositionNotFoundException}.
     * </p>
     *
     * @param orderPositions карта идентификаторов блюд и их количества из запроса. Не должна быть {@code null}.
     * @param menuIdSet      множество идентификаторов блюд, доступных в меню. Не должно быть {@code null}.
     * @throws MenuPositionNotFoundException если одна или несколько позиций из запроса не найдены в меню.
     */
    public void checkExistenceMenuPositions(Map<Long, Long> orderPositions, Set<Long> menuIdSet) {
        if (menuIdSet.size() != orderPositions.size()) {
            Set<Long> missingIds = new HashSet<>();
            for (long orderId : orderPositions.keySet()) {
                if (!menuIdSet.contains(orderId)) {
                    missingIds.add(orderId);
                }
            }
            throw new MenuPositionNotFoundException("Позиции меню с id " + missingIds + " не найдены");
        }
    }

    /**
     * Создает список позиций заказа на основе данных из запроса.
     * <p>
     * Метод преобразует данные о блюдах и их количествах из запроса в объекты типа {@link OrderPosition},
     * связывая их с заказом и соответствующими блюдами из меню.
     * </p>
     *
     * @param menuList       список блюд из меню, доступных для добавления в заказ. Не должен быть {@code null}.
     * @param orderPositions карта идентификаторов блюд и их количества из запроса. Не должна быть {@code null}.
     * @param waiterOrder    объект заказа, к которому добавляются позиции. Не должен быть {@code null}.
     * @return список объектов типа {@link OrderPosition}, представляющих позиции заказа.
     */
    public List<OrderPosition> createOrderPositions(List<Menu> menuList,
                                                    Map<Long, Long> orderPositions,
                                                    WaiterOrder waiterOrder) {
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
        return positions;
    }
}
