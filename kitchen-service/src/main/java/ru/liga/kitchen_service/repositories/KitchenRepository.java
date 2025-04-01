package ru.liga.kitchen_service.repositories;

import org.springframework.stereotype.Repository;
import ru.liga.kitchen_service.dto.KitchenOrder;
import ru.liga.kitchen_service.utils.KitchenStatus;
import ru.liga.kitchen_service.utils.OrderNotFoundException;

import java.util.ArrayList;
import java.util.List;

@Repository
public class KitchenRepository {
    private List<KitchenOrder> orders=new ArrayList<>();
    private static final String ERROR_MESSAGE = "Order with this id not found";

    public List<KitchenOrder> getAllOrders() {
        return orders;
    }

    public int addOrder(KitchenOrder kitchenOrder) {
        orders.add(kitchenOrder);
        return orders.size()-1;
    }

    public void rejectOrder(int id){
        if(id<0 || id>=orders.size()){
            throw new OrderNotFoundException(ERROR_MESSAGE);
        }
        orders.get(id).setStatus(KitchenStatus.REJECTED);  // заказ отклонен
    }

    public void readyOrder(int id){
        if(id<0 || id>=orders.size()){
            throw new OrderNotFoundException(ERROR_MESSAGE);
        }
        orders.get(id).setStatus(KitchenStatus.READY);     // заказ готов
    }
}
