package ru.liga.kitchen_service.converters;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import ru.liga.common.dto.KitchenOrderRequest;
import ru.liga.kitchen_service.models.dto.KitchenOrderResponse;
import ru.liga.kitchen_service.models.entity.KitchenOrder;


@Mapper(componentModel = "spring")
public interface KitchenOrderConverter {

    @Mapping(target = "waiterOrderNo", source = "kitchenOrderRequest.waiterOrderNo")
    @Mapping(target = "status", constant = "PREPARING") // Устанавливаем статус PREPARING
    @Mapping(target = "createDateTime", expression = "java(java.time.OffsetDateTime.now())")
    KitchenOrder mapToKitchenOrder(KitchenOrderRequest kitchenOrderRequest);

    @Mapping(target = "waiterOrderNo", source = "kitchenOrder.waiterOrderNo")
    @Mapping(target = "status", source = "kitchenOrder.status")
    @Mapping(target = "createDateTime", source = "kitchenOrder.createDateTime")
    KitchenOrderResponse mapToKitchenOrderResponse(KitchenOrder kitchenOrder);
}
