package ru.liga.kitchen_service.mappers;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;
import ru.liga.kitchen_service.models.dto.KitchenOrderWithDishesResponse;
import ru.liga.kitchen_service.models.entity.KitchenOrder;
import ru.liga.kitchen_service.models.enums.KitchenStatus;

import java.util.List;

/**
 * Mapper для работы с заказами кухни через MyBatis.
 * <p>
 * Интерфейс предоставляет методы для выполнения операций с заказами кухни в базе данных через MyBatis.
 * Используется для сохранения, поиска и обновления заказов, а также для получения заказов с детализацией блюд.
 * </p>
 */
@Mapper
@Repository
public interface KitchenOrderMapper {

    /**
     * Сохраняет новый заказ кухни в базе данных.
     * <p>
     * Метод выполняет запрос на добавление нового заказа в таблицу заказов. Возвращает уникальный идентификатор
     * созданного заказа.
     * </p>
     *
     * @param kitchenOrder объект заказа, который нужно сохранить. Не должен быть {@code null}.
     * @return уникальный идентификатор созданного заказа.
     */
    Long save(KitchenOrder kitchenOrder);

    /**
     * Возвращает список всех заказов кухни.
     * <p>
     * Метод выполняет запрос к базе данных для получения всех заказов кухни. Если заказы отсутствуют,
     * возвращается пустой список.
     * </p>
     *
     * @return список объектов типа {@link KitchenOrder}, представляющих все заказы кухни.
     *         Если заказы отсутствуют, возвращается пустой список.
     */
    List<KitchenOrder> findAllKitchenOrders();

    /**
     * Возвращает список всех заказов кухни с детализацией блюд.
     * <p>
     * Метод выполняет запрос к базе данных для получения всех заказов кухни вместе с информацией о связанных блюдах.
     * Если заказы отсутствуют, возвращается пустой список.
     * </p>
     *
     * @return список объектов типа {@link KitchenOrderWithDishesResponse}, содержащих информацию
     *         о заказах и связанных блюдах. Если заказы отсутствуют, возвращается пустой список.
     */
    List<KitchenOrderWithDishesResponse> findAllKitchenOrdersWithDishes();

    /**
     * Обновляет статус заказа кухни.
     * <p>
     * Метод выполняет запрос на обновление статуса заказа с указанным ID. Если заказ с таким ID не существует,
     * обновление не производится.
     * </p>
     *
     * @param id уникальный идентификатор заказа. Не должен быть {@code null}.
     * @param status новый статус заказа ({@link KitchenStatus}). Не должен быть {@code null}.
     * @return количество обновленных записей в базе данных (обычно 1, если запись найдена).
     */
    int setStatus(@Param("id") Long id, @Param("status") KitchenStatus status);
}
