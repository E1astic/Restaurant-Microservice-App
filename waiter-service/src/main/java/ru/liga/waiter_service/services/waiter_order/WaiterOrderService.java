package ru.liga.waiter_service.services.waiter_order;

import ru.liga.waiter_service.models.dto.WaiterOrderRequest;
import ru.liga.waiter_service.models.dto.WaiterOrderResponse;
import ru.liga.waiter_service.models.entity.WaiterOrder;
import ru.liga.waiter_service.models.enums.OrderStatus;

import java.util.List;

/**
 * Интерфейс для работы с заказами официантов.
 * <p>
 * Предоставляет методы для выполнения операций с заказами, такими как получение списка заказов,
 * создание нового заказа, обновление статуса заказа и получение информации о конкретных заказах.
 * Используется для взаимодействия с репозиторием заказов и выполнения бизнес-логики, связанной с заказами.
 * </p>
 */
public interface WaiterOrderService {

    /**
     * Возвращает информацию о всех заказах.
     * <p>
     * Метод извлекает из базы данных список всех заказов и преобразует их в формат {@link WaiterOrderResponse}.
     * Если заказы отсутствуют, возвращается пустой список.
     * </p>
     *
     * @return список объектов типа {@link WaiterOrderResponse}, содержащих информацию о всех заказах.
     * Если заказы отсутствуют, возвращается пустой список.
     */
    List<WaiterOrderResponse> getAllOrders();

    /**
     * Возвращает информацию о заказе по его идентификатору.
     * <p>
     * Метод извлекает из базы данных заказ с указанным ID и преобразует его в формат {@link WaiterOrderResponse}.
     * Если заказ с таким ID не найден, выбрасывается исключение
     * {@link ru.liga.common.exceptions.OrderNotFoundException OrderNotFoundException}.
     * </p>
     *
     * @param id уникальный идентификатор заказа. Не должен быть {@code null}.
     * @return объект типа {@link WaiterOrderResponse}, содержащий информацию о заказе.
     * @throws ru.liga.common.exceptions.OrderNotFoundException если заказ с указанным ID не существует.
     */
    WaiterOrderResponse getOrderById(Long id);

    /**
     * Возвращает статус заказа по его идентификатору.
     * <p>
     * Метод извлекает из базы данных текущий статус заказа с указанным ID. Если заказ с таким ID не найден,
     * выбрасывается исключение (например, {@link ru.liga.common.exceptions.OrderNotFoundException}).
     * </p>
     *
     * @param id уникальный идентификатор заказа. Не должен быть {@code null}.
     * @return объект типа {@link OrderStatus}, представляющий текущий статус заказа.
     * @throws ru.liga.common.exceptions.OrderNotFoundException если заказ с указанным ID не существует.
     */
    OrderStatus getOrderStatusById(Long id);

    /**
     * Создает новый заказ на основе данных из запроса.
     * <p>
     * Метод обрабатывает данные из объекта {@link WaiterOrderRequest},
     * создает новый заказ и сохраняет его в базе данных.
     * Возвращает созданный заказ в формате сущности {@link WaiterOrder}.
     * </p>
     *
     * @param waiterOrderRequest объект запроса, содержащий информацию для создания заказа.
     *                           Не должен быть {@code null}.
     * @return объект типа {@link WaiterOrder}, представляющий созданный заказ.
     */
    WaiterOrder addOrder(WaiterOrderRequest waiterOrderRequest);

    /**
     * Обновляет статус заказа.
     * <p>
     * Метод изменяет статус заказа с указанным ID на новый статус, переданный в параметре. Если заказ с таким ID
     * не найден, выбрасывается исключение (например, {@link ru.liga.common.exceptions.OrderNotFoundException}).
     * </p>
     *
     * @param id     уникальный идентификатор заказа. Не должен быть {@code null}.
     * @param status новый статус заказа в виде строки. Не должен быть {@code null}.
     * @throws ru.liga.common.exceptions.OrderNotFoundException если заказ с указанным ID не существует.
     */
    void setStatus(Long id, String status);
}
