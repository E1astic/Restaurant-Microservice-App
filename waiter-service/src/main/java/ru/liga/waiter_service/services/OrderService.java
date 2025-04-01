package ru.liga.waiter_service.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ru.liga.waiter_service.dto.ClientOrder;
import ru.liga.waiter_service.dto.WaiterOrder;
import ru.liga.waiter_service.repositories.OrderRepository;
import ru.liga.waiter_service.utils.OrderStatus;

import java.util.List;

@Service
public class OrderService {

    private final OrderRepository orderRepository;

    @Autowired
    public OrderService(OrderRepository orderRepository) {
        this.orderRepository = orderRepository;
    }

    public List<WaiterOrder> getAllOrders(){
        return orderRepository.getAllOrders();
    }

    public WaiterOrder getOrderById(int id){
        return orderRepository.getOrderById(id);
    }

    public OrderStatus getOrderStatusById(int id){
        return orderRepository.getOrderStatusById(id);
    }

    public int addOrder(ClientOrder clientOrder){
        return orderRepository.addOrder(mapToWaiterOrder(clientOrder));
    }


    private WaiterOrder mapToWaiterOrder(ClientOrder clientOrder){
       return WaiterOrder
                .builder()
                .client(clientOrder.getClient())
                .dish(clientOrder.getDish())
                .status(OrderStatus.ACCEPTED)  // по стандарту добавляем статус "принят"
                .build();
    }

}
