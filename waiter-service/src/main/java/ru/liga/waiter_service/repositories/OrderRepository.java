package ru.liga.waiter_service.repositories;

import org.springframework.stereotype.Repository;
import ru.liga.waiter_service.dto.WaiterOrder;
import ru.liga.waiter_service.utils.OrderNotFoundException;
import ru.liga.waiter_service.utils.OrderStatus;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Repository
public class OrderRepository {

    private List<WaiterOrder> orders=new ArrayList<>();
    private static final String ERROR_MESSAGE = "Order with this id not found";

    public List<WaiterOrder> getAllOrders() {
        return orders;
    }

    public WaiterOrder getOrderById(int id){
        return findOrderById(id).orElseThrow(() -> new OrderNotFoundException(ERROR_MESSAGE));
    }

    public OrderStatus getOrderStatusById(int id){
        return getOrderById(id).getStatus();
    }

    public int addOrder(WaiterOrder order){
        orders.add(order);
        return orders.size()-1;   // возвращаем индекс добавленного элемента, то есть последнего
    }

    private Optional<WaiterOrder> findOrderById(int id){
        if(id<0 || id>=orders.size()){
            return Optional.empty();
        }
        return Optional.ofNullable(orders.get(id));
    }

}
