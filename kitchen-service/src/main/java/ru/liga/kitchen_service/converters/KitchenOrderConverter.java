package ru.liga.kitchen_service.converters;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import ru.liga.common.dto.KitchenOrderRequest;
import ru.liga.kitchen_service.models.dto.KitchenOrderResponse;
import ru.liga.kitchen_service.models.entity.KitchenOrder;

/**
 * Маппер для преобразования объектов, связанных с заказами кухни.
 * <p>
 * Этот интерфейс используется для преобразования объектов между различными слоями приложения:
 * <ul>
 *     <li>Преобразование запроса в сущность {@link KitchenOrder}
 *     ({@link #mapToKitchenOrder(KitchenOrderRequest)}).</li>
 *     <li>Преобразование сущности в объект ответа ({@link #mapToKitchenOrderResponse(KitchenOrder)}).</li>
 * </ul>
 * </p>
 * <p>
 * Для преобразования объектов используется библиотека MapStruct
 * </p>
 */
@Mapper(componentModel = "spring")
public interface KitchenOrderConverter {

    /**
     * Преобразует объект запроса в сущность заказа кухни.
     * <p>
     * Метод выполняет маппинг данных из объекта {@link KitchenOrderRequest} в объект {@link KitchenOrder}.
     * При этом устанавливаются дополнительные значения:
     * <ul>
     *     <li>Статус заказа автоматически устанавливается в "PREPARING".</li>
     *     <li>Дата и время создания устанавливаются как текущее время.</li>
     * </ul>
     * </p>
     *
     * @param kitchenOrderRequest объект запроса, содержащий информацию о заказе. Не должен быть {@code null}.
     * @return объект типа {@link KitchenOrder}, представляющий сущность заказа кухни.
     */
    @Mapping(target = "waiterOrderNo", source = "kitchenOrderRequest.waiterOrderNo")
    @Mapping(target = "status", constant = "RECEIVED")
    @Mapping(target = "createDateTime", expression = "java(java.time.OffsetDateTime.now())")
    KitchenOrder mapToKitchenOrder(KitchenOrderRequest kitchenOrderRequest);

    /**
     * Преобразует сущность заказа кухни в объект ответа.
     * <p>
     * Метод выполняет маппинг данных из объекта {@link KitchenOrder} в объект {@link KitchenOrderResponse}.
     * Переносит информацию о номере заказа, статусе и дате создания.
     * </p>
     *
     * @param kitchenOrder объект сущности, содержащий данные о заказе. Не должен быть {@code null}.
     * @return объект типа {@link KitchenOrderResponse}, представляющий DTO заказа кухни.
     */
    @Mapping(target = "waiterOrderNo", source = "kitchenOrder.waiterOrderNo")
    @Mapping(target = "status", source = "kitchenOrder.status")
    @Mapping(target = "createDateTime", source = "kitchenOrder.createDateTime")
    KitchenOrderResponse mapToKitchenOrderResponse(KitchenOrder kitchenOrder);
}
