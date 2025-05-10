package ru.liga.kitchen_service.mappers;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;
import ru.liga.kitchen_service.models.entity.Dish;

import java.util.List;

/**
 * Mapper для работы с данными о блюдах через MyBatis.
 * <p>
 * Интерфейс предоставляет методы для выполнения операций с блюдами в базе данных через MyBatis.
 * Используется для взаимодействия с таблицей блюд и выполнения запросов, таких как поиск по ID,
 * поиск по списку ID и обновление количества блюд.
 * </p>
 */
@Mapper
@Repository
public interface DishMapper {

    /**
     * Возвращает блюдо по его идентификатору.
     * <p>
     * Метод выполняет запрос к базе данных для получения блюда с указанным ID. Если блюдо с таким ID не найдено,
     * возвращается {@code null}.
     * </p>
     *
     * @param id уникальный идентификатор блюда. Не должен быть {@code null}.
     * @return объект типа {@link Dish}, представляющий блюдо, или {@code null}, если блюдо не найдено.
     */
    Dish findById(@Param("id") Long id);

    /**
     * Возвращает список блюд по их идентификаторам.
     * <p>
     * Метод выполняет запрос к базе данных для получения всех блюд, чьи идентификаторы входят
     * в предоставленный список. Если ни одно из указанных ID не найдено, возвращается пустой список.
     * </p>
     *
     * @param dishIdList список идентификаторов блюд. Не должен быть {@code null}.
     * @return список объектов типа {@link Dish}, соответствующих указанным идентификаторам.
     *         Если совпадений нет, возвращается пустой список.
     */
    List<Dish> findByIdIn(@Param("dishIdList") List<Long> dishIdList);

    /**
     * Обновляет количество блюд в базе данных.
     * <p>
     * Метод выполняет обновление оставшегося количества блюд на складе после успешного
     * оформления заказа. Каждому ID из списка присваивается новое значение из списка
     * {@code newValues}. Порядок значений в обоих списках должен совпадать.
     * </p>
     *
     * @param idList список идентификаторов блюд, для которых нужно обновить количество. Не должен быть {@code null}.
     * @param newValues список новых значений количества блюд. Не должен быть {@code null}.
     * @return количество обновленных записей в базе данных.
     */
    int updateDishNums(@Param("idList") List<Long> idList, @Param("newValues") List<Long> newValues);
}
