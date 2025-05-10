package ru.liga.kitchen_service.mappers;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;
import ru.liga.kitchen_service.models.entity.OrderToDish;

import java.util.Map;

/**
 * Mapper для работы с информацией о позициях и количестве блюд в заказе через MyBatis.
 * <p>
 * Интерфейс предоставляет методы для сохранения информации о позициях заказа.
 * </p>
 */
@Mapper
@Repository
public interface OrderToDishMapper {

    /**
     * Сохраняет связь между заказом кухни и блюдом в базе данных.
     * <p>
     * Метод выполняет запрос на добавление одной записи, представляющей связь между заказом кухни и блюдом.
     * </p>
     *
     * @param orderToDish объект, содержащий информацию о заказе кухни, блюде и его количестве в заказе.
     *                    Не должен быть {@code null}.
     */
    void save(@Param("orderToDish") OrderToDish orderToDish);

    /**
     * Массово сохраняет связи между заказом кухни и блюдами в базе данных.
     * <p>
     * Метод выполняет запрос на добавление нескольких записей, представляющих связи между заказом кухни
     * и блюдами. Каждая запись связывает идентификатор блюда с его количеством в заказе.
     * </p>
     *
     * @param kitchenOrderId уникальный идентификатор заказа кухни. Не должен быть {@code null}.
     * @param orderToDish карта, где ключ — идентификатор блюда, а значение — количество блюд в заказе.
     *                    Не должна быть {@code null}.
     */
    void saveAll(@Param("kitchenOrderId") Long kitchenOrderId,
                 @Param("orderToDish") Map<Long, Long> orderToDish);
}
