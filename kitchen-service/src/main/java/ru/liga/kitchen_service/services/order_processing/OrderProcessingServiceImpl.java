package ru.liga.kitchen_service.services.order_processing;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import ru.liga.common.dto.KitchenOrderRequest;
import ru.liga.kitchen_service.exceptions.NotEnoughDishesException;
import ru.liga.kitchen_service.models.entity.Dish;
import ru.liga.kitchen_service.models.entity.KitchenOrder;
import ru.liga.kitchen_service.models.enums.KitchenStatus;
import ru.liga.kitchen_service.services.dish.DishService;
import ru.liga.kitchen_service.services.kitchen.KitchenService;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
@Slf4j
@RequiredArgsConstructor
public class OrderProcessingServiceImpl implements OrderProcessingService {

    private final KitchenService kitchenService;

    private final DishService dishService;

    @Override
    public Long processOrder(KitchenOrderRequest kitchenOrderRequest) {
        log.info("Обработка кухней принятия/отклонения заказа с id = {}", kitchenOrderRequest.getWaiterOrderNo());
        KitchenOrder kitchenOrder = kitchenService.receiveOrder(kitchenOrderRequest);

        try {
            Map<Long, Long> dishIdAndRemains = checkDishNumberInStock(kitchenOrderRequest);
            kitchenService.setStatus(kitchenOrder.getId(), KitchenStatus.PREPARING.toString());
            dishService.updateDishNums(new ArrayList<>(dishIdAndRemains.keySet()),
                    new ArrayList<>(dishIdAndRemains.values()));
        } catch (NotEnoughDishesException e) {
            kitchenService.setStatus(kitchenOrder.getId(), KitchenStatus.REJECTED.toString());
        }
        return kitchenOrder.getId();
    }

    /**
     * <p>
     * Метод проверяет, достаточно ли блюд на складе для выполнения заказа. Для каждого блюда в заказе сравнивается
     * требуемое количество с доступным количеством на складе. Если блюд недостаточно, выбрасывается исключение
     * {@link NotEnoughDishesException}. Возвращает карту, где ключ — идентификатор блюда, а значение — остаток
     * блюд на складе после выполнения заказа.
     * </p>
     *
     * @param kitchenOrderRequest объект запроса, содержащий информацию о заказе. Не должен быть {@code null}.
     * @return карта, где ключ — идентификатор блюда, а значение — остаток блюд на складе после выполнения заказа.
     * @throws NotEnoughDishesException если на складе недостаточно блюд для выполнения заказа.
     */
    public Map<Long, Long> checkDishNumberInStock(KitchenOrderRequest kitchenOrderRequest) {
        Map<Long, Long> orderPositions = kitchenOrderRequest.getOrderPositions();
        Map<Long, Long> dishIdAndRemains = new HashMap<>();

        List<Dish> dishesInStock = dishService.getDishesById(new ArrayList<>(orderPositions.keySet()));
        log.info("Проверка кухней имеющегося количества блюд на складе");
        for (Dish dish : dishesInStock) {
            long dishNumInOrder = orderPositions.get(dish.getId());
            long dishNumInStock = dish.getBalance();
            if (dishNumInOrder > dishNumInStock) {
                throw new NotEnoughDishesException(String.format(
                        "На складе недостаточно товара с id = %d", dish.getId()));
            }
            dishIdAndRemains.put(dish.getId(), dishNumInStock - dishNumInOrder);
        }
        return dishIdAndRemains;
    }
}
