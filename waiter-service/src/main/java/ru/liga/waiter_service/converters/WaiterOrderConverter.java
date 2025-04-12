package ru.liga.waiter_service.converters;

import org.mapstruct.AfterMapping;
import org.mapstruct.Context;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;
import ru.liga.waiter_service.models.dto.KitchenOrderRequest;
import ru.liga.waiter_service.models.dto.WaiterOrderRequest;
import ru.liga.waiter_service.models.dto.WaiterOrderResponse;
import ru.liga.waiter_service.models.entity.OrderPosition;
import ru.liga.waiter_service.models.entity.WaiterAccount;
import ru.liga.waiter_service.models.entity.WaiterOrder;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Mapper(componentModel = "spring")
public interface WaiterOrderConverter {

    @Mapping(target = "status", constant = "ACCEPTED")
    @Mapping(target = "createDateTime", expression = "java(java.time.OffsetDateTime.now())")
    @Mapping(target = "waiter", expression = "java(waiterAccount)")
    @Mapping(target = "tableNo", source = "waiterOrderRequest.tableNo")
    WaiterOrder mapToWaiterOrder(WaiterOrderRequest waiterOrderRequest, @Context WaiterAccount waiterAccount);

    @Mapping(target = "waiterOrderNo", source = "waiterId")
    @Mapping(target = "orderPositions", source = "orderPositions")
    KitchenOrderRequest mapToKitchenOrderRequest(WaiterOrderRequest waiterOrderRequest);

    @Mapping(target = "orderStatus", source = "status")
    @Mapping(target = "createDateTime", source = "createDateTime")
    @Mapping(target = "tableNo", source = "tableNo")
    @Mapping(target = "waiterId", source = "waiter.id")
    @Mapping(target = "orderPositions", ignore = true)
    WaiterOrderResponse mapToWaiterOrderResponse(WaiterOrder waiterOrder);

    default Map<Long, Long> mapOrderPositions(List<OrderPosition> positions) {
        return positions.stream()
                .collect(Collectors.toMap(
                        op -> op.getMenuPosition().getId(),
                        OrderPosition::getDishNum
                ));
    }

    @AfterMapping
    default void mapOrderPositions(@MappingTarget WaiterOrderResponse response, WaiterOrder waiterOrder) {
        response.setOrderPositions(mapOrderPositions(waiterOrder.getPositions()));
    }
}


