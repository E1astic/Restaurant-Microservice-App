package ru.liga.kitchen_service.services.kitchen;

import ru.liga.common.dto.KitchenOrderRequest;
import ru.liga.kitchen_service.models.dto.KitchenOrderResponse;
import ru.liga.kitchen_service.models.dto.KitchenOrderWithDishesResponse;
import ru.liga.kitchen_service.models.entity.KitchenOrder;

import java.util.List;


/**
 * Интерфейс для работы с заказами кухни.
 * <p>
 * Предоставляет методы для выполнения операций с заказами, такими как получение списка заказов,
 * создание нового заказа, обновление статуса заказа и получение информации о заказах с детализацией блюд.
 * Используется для взаимодействия с репозиторием заказов и выполнения бизнес-логики, связанной с заказами кухни.
 * </p>
 */
public interface KitchenService {

    /**
     * Возвращает информацию о всех заказах кухни.
     * <p>
     * Метод извлекает из базы данных список всех заказов в формате {@link KitchenOrderResponse}.
     * Если заказы отсутствуют, возвращается пустой список.
     * </p>
     *
     * @return список объектов типа {@link KitchenOrderResponse}, содержащих информацию о всех заказах.
     * Если заказы отсутствуют, возвращается пустой список.
     */
    List<KitchenOrderResponse> getAllOrders();

    /**
     * Возвращает информацию о всех заказах кухни с детализацией блюд.
     * <p>
     * Метод извлекает из базы данных список всех заказов вместе с информацией о связанных блюдах
     * в формате {@link KitchenOrderWithDishesResponse}. Если заказы отсутствуют, возвращается пустой список.
     * </p>
     *
     * @return список объектов типа {@link KitchenOrderWithDishesResponse}, содержащих информацию
     * о заказах и связанных блюдах. Если заказы отсутствуют, возвращается пустой список.
     */
    List<KitchenOrderWithDishesResponse> getAllOrdersWithDishes();

    /**
     * Создает новый заказ кухни на основе данных из запроса.
     * <p>
     * Метод обрабатывает данные из объекта {@link KitchenOrderRequest}, создает новый заказ и сохраняет
     * его в базе данных.
     * Возвращает созданный заказ в формате сущности {@link KitchenOrder}.
     * </p>
     *
     * @param kitchenOrderRequest объект запроса, содержащий информацию для создания заказа.
     *                            Не должен быть {@code null}.
     * @return объект типа {@link KitchenOrder}, представляющий созданный заказ.
     */
    KitchenOrder receiveOrder(KitchenOrderRequest kitchenOrderRequest);

    /**
     * Обновляет статус заказа кухни.
     * <p>
     * Метод изменяет статус заказа с указанным ID на новый статус, переданный в параметре.
     * Если заказ с таким ID не найден, может быть выброшено исключение (например,
     * {@link ru.liga.common.exceptions.OrderNotFoundException OrderNotFoundException}).
     * Если передан некорректный статус, может быть выброшено исключение (например,
     * {@link ru.liga.kitchen_service.exceptions.IncorrectKitchenStatusException IncorrectKitchenStatusException}).
     * </p>
     *
     * @param id     уникальный идентификатор заказа. Не должен быть {@code null}.
     * @param status новый статус заказа в виде строки. Не должен быть {@code null}.
     * @throws ru.liga.common.exceptions.OrderNotFoundException
     * если заказ с указанным ID не существует.
     * @throws ru.liga.kitchen_service.exceptions.IncorrectKitchenStatusException
     * если передан некорректный статус заказа.
     */
    void setStatus(Long id, String status);
}
