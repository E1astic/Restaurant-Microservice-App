package ru.liga.kitchen_service.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ru.liga.kitchen_service.dto.ClientOrder;
import ru.liga.kitchen_service.dto.KitchenOrder;
import ru.liga.kitchen_service.repositories.KitchenRepository;
import ru.liga.kitchen_service.utils.KitchenStatus;
import ru.liga.kitchen_service.utils.OrderNotFoundException;


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

    public int addOrder(ClientOrder clientOrder) {
        return kitchenRepository.addOrder(mapToKitchenOrder(clientOrder));
    }

    public void acceptOrder(int id){
        try{
            kitchenRepository.acceptOrder(id);
        }
        catch(OrderNotFoundException e){
            throw new OrderNotFoundException(e.getMessage());
        }
    }

    public void rejectOrder(int id){
        try{
            kitchenRepository.rejectOrder(id);
        }
        catch(OrderNotFoundException e){
            throw new OrderNotFoundException(e.getMessage());
        }
    }

    public void readyOrder(int id){
        try{
            kitchenRepository.readyOrder(id);
        }
        catch(OrderNotFoundException e){
            throw new OrderNotFoundException(e.getMessage());
        }
    }


    private KitchenOrder mapToKitchenOrder(ClientOrder clientOrder) {
        return KitchenOrder
                .builder()
                .client(clientOrder.getClient())
                .dish(clientOrder.getDish())
                .status(KitchenStatus.AWAITING)   // статус - ожидает начала работы
                .build();
    }
}
