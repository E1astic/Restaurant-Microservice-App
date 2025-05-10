package ru.liga.waiter_service.services.waiter_order;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.liga.common.exceptions.OrderNotFoundException;
import ru.liga.waiter_service.converters.WaiterOrderConverter;
import ru.liga.waiter_service.models.dto.WaiterOrderRequest;
import ru.liga.waiter_service.models.dto.WaiterOrderResponse;
import ru.liga.waiter_service.models.entity.WaiterAccount;
import ru.liga.waiter_service.models.entity.WaiterOrder;
import ru.liga.waiter_service.repositories.WaiterOrderRepository;
import ru.liga.waiter_service.services.kitchen.KitchenService;
import ru.liga.waiter_service.services.order_position.OrderPositionService;
import ru.liga.waiter_service.services.waiter_account.WaiterAccountService;
import ru.liga.waiter_service.exceptions.IncorrectOrderStatusException;
import ru.liga.waiter_service.models.enums.OrderStatus;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Slf4j
public class WaiterOrderServiceImpl implements WaiterOrderService {

    private final WaiterOrderRepository waiterOrderRepository;

    private final WaiterAccountService waiterAccountService;

    private final OrderPositionService orderPositionService;

    private final WaiterOrderConverter waiterOrderConverter;

    private final KitchenService kitchenService;

    private static final String ERR_MESSAGE = "Заказа с id = %d не существует";

    @Override
    public List<WaiterOrderResponse> getAllOrders() {
        log.info("Запрос на получение всех заказов официантов");
        return waiterOrderRepository.findAll()
                .stream()
                .map(waiterOrderConverter::mapToWaiterOrderResponse)
                .collect(Collectors.toList());
    }

    @Override
    public WaiterOrderResponse getOrderById(Long id) {
        log.info("Запрос на получение заказа официанта с id = {}", id);
        return waiterOrderConverter.mapToWaiterOrderResponse(waiterOrderRepository.findById(id).orElseThrow(()
                -> new OrderNotFoundException(String.format(ERR_MESSAGE, id))));
    }

    @Override
    public OrderStatus getOrderStatusById(Long id) {
        log.info("Запрос на получение статуса заказа официанта с id = {}", id);
        return getOrderById(id).getOrderStatus();
    }

    @Override
    @Transactional
    public WaiterOrder addOrder(WaiterOrderRequest waiterOrderRequest) {
        WaiterAccount waiterAccount = waiterAccountService.getWaiterAccountById(waiterOrderRequest.getWaiterId());
        WaiterOrder waiterOrder = waiterOrderConverter.mapToWaiterOrder(waiterOrderRequest, waiterAccount);
        waiterOrder = waiterOrderRepository.save(waiterOrder);
        //log.info("Добавление заказа с id = {} в базу данных официантов", waiterOrder.getId());
        orderPositionService.addOrderPositions(waiterOrder, waiterOrderRequest);
        kitchenService.sendOrderToKitchen(waiterOrderRequest, waiterOrder);
        return waiterOrder;
    }

    @Override
    @Transactional
    public void setStatus(Long id, String status) {
        log.info("Изменение статуса заказа официанта с id = {} на {}", id, status);
        try {
            OrderStatus orderStatus = OrderStatus.valueOf(status.toUpperCase());
            int statusUpdated = waiterOrderRepository.updateStatus(id, orderStatus);
            if (statusUpdated == 0) {
                throw new OrderNotFoundException(String.format(ERR_MESSAGE, id));
            }
        } catch (IllegalArgumentException e) {
            throw new IncorrectOrderStatusException("Некорректный статус заказа");
        }
    }
}
