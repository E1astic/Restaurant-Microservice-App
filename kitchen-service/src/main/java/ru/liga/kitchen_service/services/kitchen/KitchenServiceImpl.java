package ru.liga.kitchen_service.services.kitchen;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import ru.liga.common.dto.KitchenOrderRequest;
import ru.liga.kitchen_service.converters.KitchenOrderConverter;
import ru.liga.kitchen_service.feign.WaiterFeignClient;
import ru.liga.kitchen_service.mappers.KitchenOrderMapper;
import ru.liga.kitchen_service.models.dto.KitchenOrderResponse;
import ru.liga.kitchen_service.models.dto.KitchenOrderWithDishesResponse;
import ru.liga.kitchen_service.models.entity.KitchenOrder;
import ru.liga.kitchen_service.services.order_to_dish.OrderToDishService;
import ru.liga.kitchen_service.exceptions.IncorrectKitchenStatusException;
import ru.liga.kitchen_service.models.enums.KitchenStatus;
import ru.liga.common.exceptions.OrderNotFoundException;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Slf4j
public class KitchenServiceImpl implements KitchenService {

    private final KitchenOrderMapper kitchenOrderMapper;

    private final KitchenOrderConverter kitchenOrderConverter;

    private final OrderToDishService orderToDishService;

    private final WaiterFeignClient waiterFeignClient;

    private static final String ERR_MESSAGE = "Заказа с id = %d не существует";

    @Override
    public List<KitchenOrderResponse> getAllOrders() {
        log.info("Запрос на получение всех заказов кухни");
        return kitchenOrderMapper.findAllKitchenOrders()
                .stream()
                .map(kitchenOrderConverter::mapToKitchenOrderResponse)
                .collect(Collectors.toList());
    }

    @Override
    public List<KitchenOrderWithDishesResponse> getAllOrdersWithDishes() {
        log.info("Запрос на получение всех заказов кухни со списком блюд");
        return kitchenOrderMapper.findAllKitchenOrdersWithDishes();
    }

    @Override
    public KitchenOrder receiveOrder(KitchenOrderRequest kitchenOrderRequest) {
        KitchenOrder kitchenOrder = kitchenOrderConverter.mapToKitchenOrder(kitchenOrderRequest);
        kitchenOrderMapper.save(kitchenOrder);
        log.info("Добавление заказа с id = {} в базу данных кухни", kitchenOrder.getId());
        orderToDishService.addOrderToDishes(kitchenOrder, kitchenOrderRequest);
        return kitchenOrder;
    }

    @Override
    public void setStatus(Long id, String status) {
        log.info("Изменение статуса заказа кухни с id = {} на {}", id, status);
        try {
            KitchenStatus kitchenStatus = KitchenStatus.valueOf(status.toUpperCase());
            int statusUpdated = kitchenOrderMapper.setStatus(id, kitchenStatus);
            if (statusUpdated == 0) {
                throw new OrderNotFoundException(String.format(ERR_MESSAGE, id));
            }
            if (kitchenStatus != KitchenStatus.PREPARING) {
                log.info("Отправка запроса официантам на смену статуса заказа с id = {}", id);
                waiterFeignClient.setStatus(id, kitchenStatus.toString());
            }
        } catch (IllegalArgumentException e) {
            throw new IncorrectKitchenStatusException("Некорректный статус заказа");
        }
    }
}
