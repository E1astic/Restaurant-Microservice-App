package ru.liga.kitchen_service.services;

import org.springframework.stereotype.Service;
import ru.liga.kitchen_service.dto.ClientOrder;
import ru.liga.kitchen_service.dto.KitchenOrder;

import java.util.List;

@Service
public interface KitchenService {

    List<KitchenOrder> getAllOrders();

    int acceptOrder(ClientOrder clientOrder);

    void rejectOrder(int id);

    void readyOrder(int id);
}
