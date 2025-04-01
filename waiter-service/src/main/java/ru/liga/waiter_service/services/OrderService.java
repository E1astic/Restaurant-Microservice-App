package ru.liga.waiter_service.services;

import ru.liga.waiter_service.dto.ClientOrder;
import ru.liga.waiter_service.dto.WaiterOrder;
import ru.liga.waiter_service.utils.OrderStatus;

import java.util.List;

public interface OrderService {

    List<WaiterOrder> getAllOrders();

    WaiterOrder getOrderById(int id);

    OrderStatus getOrderStatusById(int id);

    int addOrder(ClientOrder clientOrder);
}
