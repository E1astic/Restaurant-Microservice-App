package ru.liga.waiter_service.converters;

import org.mapstruct.AfterMapping;
import org.mapstruct.Context;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;
import ru.liga.common.dto.KitchenOrderRequest;
import ru.liga.waiter_service.models.dto.WaiterOrderRequest;
import ru.liga.waiter_service.models.dto.WaiterOrderResponse;
import ru.liga.waiter_service.models.entity.OrderPosition;
import ru.liga.waiter_service.models.entity.WaiterAccount;
import ru.liga.waiter_service.models.entity.WaiterOrder;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * Маппер для преобразования объектов, связанных с заказами официантов.
 * <p>
 * Этот интерфейс используется для преобразования объектов между различными слоями приложения:
 * <ul>
 *     <li>Преобразование запроса в сущность {@link WaiterOrder}
 *     ({@link #mapToWaiterOrder(WaiterOrderRequest, WaiterAccount)}).</li>
 *     <li>Преобразование запроса в объект для кухни
 *     ({@link #mapToKitchenOrderRequest(WaiterOrderRequest, Long)}).</li>
 *     <li>Преобразование сущности в объект ответа ({@link #mapToWaiterOrderResponse(WaiterOrder)}).</li>
 * </ul>
 * </p>
 * <p>
 * Для преобразования объектов используется библиотека MapStruct
 * </p>
 */
@Mapper(componentModel = "spring")
public interface WaiterOrderConverter {

    /**
     * Преобразует объект запроса {@link WaiterOrderRequest} в сущность {@link WaiterOrder}.
     * <p>
     * При преобразовании:
     * <ul>
     *     <li>Статус заказа устанавливается как "ACCEPTED".</li>
     *     <li>Дата и время создания устанавливаются текущими.</li>
     *     <li>Официант привязывается к заказу через переданный контекст {@link WaiterAccount}.</li>
     *     <li>Номер столика берется из запроса.</li>
     * </ul>
     * </p>
     *
     * @param waiterOrderRequest объект запроса, содержащий данные для создания заказа.
     * @param waiterAccount      объект контекста, представляющий аккаунт официанта.
     * @return сущность {@link WaiterOrder}, содержащая данные заказа.
     */
    @Mapping(target = "status", constant = "ACCEPTED")
    @Mapping(target = "createDateTime", expression = "java(java.time.OffsetDateTime.now())")
    @Mapping(target = "waiter", expression = "java(waiterAccount)")
    @Mapping(target = "tableNo", source = "waiterOrderRequest.tableNo")
    WaiterOrder mapToWaiterOrder(WaiterOrderRequest waiterOrderRequest, @Context WaiterAccount waiterAccount);

    /**
     * Преобразует объект запроса {@link WaiterOrderRequest} в объект запроса для кухни {@link KitchenOrderRequest}.
     * <p>
     * При преобразовании:
     * <ul>
     *     <li>ID заказа устанавливается явно.</li>
     *     <li>Позиции заказа берутся из запроса.</li>
     * </ul>
     * </p>
     *
     * @param waiterOrderRequest объект запроса, содержащий данные для создания заказа.
     * @param orderId            уникальный идентификатор заказа.
     * @return объект запроса для кухни {@link KitchenOrderRequest}.
     */
    @Mapping(target = "waiterOrderNo", expression = "java(orderId)")
    @Mapping(target = "orderPositions", source = "waiterOrderRequest.orderPositions")
    KitchenOrderRequest mapToKitchenOrderRequest(WaiterOrderRequest waiterOrderRequest, Long orderId);

    /**
     * Преобразует сущность {@link WaiterOrder} в объект ответа {@link WaiterOrderResponse}.
     * <p>
     * При преобразовании:
     * <ul>
     *     <li>Статус заказа отображается в поле ответа.</li>
     *     <li>Дата и время создания заказа переносятся в ответ.</li>
     *     <li>Номер столика переносится в ответ.</li>
     *     <li>ID официанта берется из сущности.</li>
     *     <li>Позиции заказа добавляются отдельно через метод {@link #mapOrderPositions(List)}.</li>
     * </ul>
     * </p>
     *
     * @param waiterOrder сущность, содержащая данные заказа.
     * @return объект ответа {@link WaiterOrderResponse}.
     */
    @Mapping(target = "orderStatus", source = "status")
    @Mapping(target = "createDateTime", source = "createDateTime")
    @Mapping(target = "tableNo", source = "tableNo")
    @Mapping(target = "waiterId", source = "waiter.id")
    @Mapping(target = "orderPositions", ignore = true)
    WaiterOrderResponse mapToWaiterOrderResponse(WaiterOrder waiterOrder);

    /**
     * Преобразует список позиций заказа в структуру {@link Map}, где ключ — ID позиции меню,
     * а значение — количество блюд.
     *
     * @param positions список позиций заказа.
     * @return {@link Map}, где ключ — ID позиции меню, значение — количество блюд.
     */
    default Map<Long, Long> mapOrderPositions(List<OrderPosition> positions) {
        return positions.stream()
                .collect(Collectors.toMap(
                        op -> op.getMenuPosition().getId(),
                        OrderPosition::getDishNum
                ));
    }

    /**
     * Добавляет позиции заказа в объект ответа после завершения основного маппинга.
     * <p>
     * Вызывается автоматически после выполнения метода {@link #mapToWaiterOrderResponse(WaiterOrder)}.
     * </p>
     *
     * @param response    объект ответа, в который добавляются позиции заказа.
     * @param waiterOrder сущность, содержащая данные заказа.
     */
    @AfterMapping
    default void mapOrderPositions(@MappingTarget WaiterOrderResponse response, WaiterOrder waiterOrder) {
        response.setOrderPositions(mapOrderPositions(waiterOrder.getPositions()));
    }
}


