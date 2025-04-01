package ru.liga.kitchen_service.repositories;

import org.springframework.stereotype.Repository;
import ru.liga.kitchen_service.dto.KitchenOrder;
import ru.liga.kitchen_service.utils.KitchenStatus;
import ru.liga.kitchen_service.utils.OrderNotFoundException;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

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
        findOrderById(id)
                .orElseThrow(() -> new OrderNotFoundException(ERROR_MESSAGE))
                .setStatus(KitchenStatus.REJECTED);  // заказ отклонен
    }

    public void readyOrder(int id){
        findOrderById(id)
                .orElseThrow(() -> new OrderNotFoundException(ERROR_MESSAGE))
                .setStatus(KitchenStatus.REJECTED);  // заказ готов
    }

    private Optional<KitchenOrder> findOrderById(int id){
        if(id<0 || id>=orders.size()){
            return Optional.empty();
        }
        return Optional.ofNullable(orders.get(id));
    }
}
