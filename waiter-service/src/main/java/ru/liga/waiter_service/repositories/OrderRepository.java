package ru.liga.waiter_service.repositories;

import org.springframework.stereotype.Repository;
import ru.liga.waiter_service.dto.WaiterOrder;
import ru.liga.waiter_service.utils.OrderNotFoundException;
import ru.liga.waiter_service.utils.OrderStatus;

import java.util.ArrayList;
import java.util.List;

@Repository
public class OrderRepository {

    private List<WaiterOrder> orders=new ArrayList<>();
    private static final String ERROR_MESSAGE = "Order with this id not found";

    public List<WaiterOrder> getAllOrders() {
        return orders;
    }

    public WaiterOrder getOrderById(int id){
        if(id<0 || id>=orders.size()){
            throw new OrderNotFoundException(ERROR_MESSAGE);
        }
        return orders.get(id);
    }

    public OrderStatus getOrderStatusById(int id){
        try {
            return getOrderById(id).getStatus();
        }
        catch(OrderNotFoundException e){
            throw new OrderNotFoundException(e.getMessage());
        }
    }

    public int addOrder(WaiterOrder order){
        orders.add(order);
        return orders.size()-1;   // возвращаем индекс добавленного элемента, то есть последнего
    }

}
