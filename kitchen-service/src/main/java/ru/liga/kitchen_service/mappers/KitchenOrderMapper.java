package ru.liga.kitchen_service.mappers;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;
import ru.liga.kitchen_service.models.dto.KitchenOrderWithDishesResponse;
import ru.liga.kitchen_service.models.entity.KitchenOrder;
import ru.liga.kitchen_service.utils.KitchenStatus;

import java.util.List;

@Mapper
@Repository
public interface KitchenOrderMapper {

    void save (KitchenOrder kitchenOrder);

    List<KitchenOrder> findAllKitchenOrders();

    List<KitchenOrderWithDishesResponse> findAllKitchenOrdersWithDishes();

    int setStatus (@Param("id") Long id, @Param("status") KitchenStatus status);
}
