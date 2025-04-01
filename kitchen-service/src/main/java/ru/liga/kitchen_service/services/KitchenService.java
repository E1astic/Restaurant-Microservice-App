package ru.liga.kitchen_service.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ru.liga.kitchen_service.dto.ClientOrder;
import ru.liga.kitchen_service.dto.KitchenOrder;
import ru.liga.kitchen_service.repositories.KitchenRepository;
import ru.liga.kitchen_service.utils.KitchenStatus;

import java.util.List;

@Service
public class KitchenService {

    private final KitchenRepository kitchenRepository;

    @Autowired
    public KitchenService(KitchenRepository kitchenRepository) {
        this.kitchenRepository = kitchenRepository;
    }

    public List<KitchenOrder> getAllOrders(){
        return kitchenRepository.getAllOrders();
    }

    public int acceptOrder(ClientOrder clientOrder){
        return kitchenRepository.addOrder(mapToKitchenOrder(clientOrder));
    }

    public void rejectOrder(int id){
        kitchenRepository.rejectOrder(id);
    }

    public void readyOrder(int id){
        kitchenRepository.readyOrder(id);
    }


    private KitchenOrder mapToKitchenOrder(ClientOrder clientOrder) {
        return KitchenOrder
                .builder()
                .client(clientOrder.getClient())
                .dish(clientOrder.getDish())
                .status(KitchenStatus.PREPARING)   // статус - принят и начал готовиться
                .build();
    }
}
