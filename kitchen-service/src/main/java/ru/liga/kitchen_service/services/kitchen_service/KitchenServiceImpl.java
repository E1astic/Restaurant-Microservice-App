package ru.liga.kitchen_service.services.kitchen_service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import ru.liga.common.dto.KitchenOrderRequest;
import ru.liga.kitchen_service.converters.KitchenOrderConverter;
import ru.liga.kitchen_service.feign.WaiterFeignClient;
import ru.liga.kitchen_service.mappers.KitchenOrderMapper;
import ru.liga.kitchen_service.models.dto.KitchenOrderResponse;
import ru.liga.kitchen_service.models.dto.KitchenOrderWithDishesResponse;
import ru.liga.kitchen_service.models.entity.Dish;
import ru.liga.kitchen_service.models.entity.KitchenOrder;
import ru.liga.kitchen_service.services.dish.DishService;
import ru.liga.kitchen_service.services.order_to_dish.OrderToDishService;
import ru.liga.kitchen_service.utils.IncorrectKitchenStatusException;
import ru.liga.kitchen_service.utils.KitchenStatus;
import ru.liga.kitchen_service.utils.OrderNotFoundException;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class KitchenServiceImpl implements KitchenService {

    private final KitchenOrderMapper kitchenOrderMapper;
    private final KitchenOrderConverter kitchenOrderConverter;

    private final OrderToDishService orderToDishService;
    private final DishService dishService;
    private final WaiterFeignClient waiterFeignClient;

    private static final String ERR_MESSAGE = "Order with id = %d not found";

    @Override
    public List<KitchenOrderResponse> getAllOrders(){
        return kitchenOrderMapper.findAllKitchenOrders()
                .stream()
                .map(kitchenOrderConverter::mapToKitchenOrderResponse)
                .collect(Collectors.toList());
    }

    @Override
    public List<KitchenOrderWithDishesResponse> getAllOrdersWithDishes() {
        return kitchenOrderMapper.findAllKitchenOrdersWithDishes();
    }

    @Override
    public KitchenOrder acceptOrder(KitchenOrderRequest kitchenOrderRequest){
        KitchenOrder kitchenOrder = kitchenOrderConverter.mapToKitchenOrder(kitchenOrderRequest);
        kitchenOrderMapper.save(kitchenOrder);
        orderToDishService.addOrderToDishes(kitchenOrder, kitchenOrderRequest);
        return kitchenOrder;
    }

    @Override
    public void setStatus(Long id, String status) {
        try {
            KitchenStatus kitchenStatus = KitchenStatus.valueOf(status.toUpperCase());
            int statusUpdated = kitchenOrderMapper.setStatus(id, kitchenStatus);
            if(statusUpdated == 0){
                throw new OrderNotFoundException(String.format(ERR_MESSAGE, id));
            }
            waiterFeignClient.setStatus(id, kitchenStatus.toString());
        }
        catch(IllegalArgumentException e){
            throw new IncorrectKitchenStatusException("Incorrect kitchen status");
        }
    }

    @Override
    public Long receiveOrder(KitchenOrderRequest kitchenOrderRequest){
        KitchenOrder kitchenOrder = acceptOrder(kitchenOrderRequest);

        Map<Long, Long> orderPositions = kitchenOrderRequest.getOrderPositions();
        List<Long> dishIdList = new ArrayList(orderPositions.keySet());

        List<Long> dishNumRemains = new ArrayList<>();

        List<Dish> dishesInStock = dishService.getDishesById(dishIdList);
        for(int i = 0; i < dishesInStock.size(); ++i) {
            Dish dish = dishesInStock.get(i);
            long dishNumInOrder = orderPositions.get(dish.getId());
            long dishNumInStock = dish.getBalance();
            if(dishNumInOrder > dishNumInStock){
                setStatus(kitchenOrder.getId(), KitchenStatus.REJECTED.toString());
                return kitchenOrderRequest.getWaiterOrderNo();
            }
            dishNumRemains.add(dishNumInStock - dishNumInOrder);
        }
        dishService.updateDishNums(dishIdList, dishNumRemains);
        return kitchenOrderRequest.getWaiterOrderNo();
    }
}
