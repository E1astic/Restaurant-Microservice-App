package ru.liga.kitchen_service.services.dish;

import ru.liga.kitchen_service.models.entity.Dish;

import java.util.List;

/**
 * Интерфейс для работы с блюдами в системе.
 * <p>
 * Предоставляет методы для выполнения операций с блюдами, таких как получение блюда по ID,
 * получение списка блюд по списку ID и обновление количества блюд. Используется для взаимодействия
 * с репозиторием блюд и выполнения бизнес-логики, связанной с блюдами.
 * </p>
 */
public interface DishService {

    /**
     * Возвращает блюдо по его идентификатору.
     * <p>
     * Метод извлекает из базы данных блюдо с указанным ID.
     * </p>
     *
     * @param id уникальный идентификатор блюда. Не должен быть {@code null}.
     * @return объект типа {@link Dish}, представляющий блюдо.
     */
    Dish getDishById(Long id);

    /**
     * Возвращает список блюд по их идентификаторам.
     * <p>
     * Метод извлекает из базы данных все блюда, чьи идентификаторы входят в предоставленный список.
     * Если ни одно из указанных ID не найдено, возвращается пустой список.
     * </p>
     *
     * @param ids список идентификаторов блюд. Не должен быть {@code null}.
     * @return список объектов типа {@link Dish}, соответствующих указанным идентификаторам.
     *         Если совпадений нет, возвращается пустой список.
     */
    List<Dish> getDishesById(List<Long> ids);

    /**
     * Обновляет количество блюд в базе данных.
     * <p>
     * Метод выполняет массовое обновление количества блюд для списка идентификаторов. Каждому ID из списка
     * присваивается новое значение из списка {@code newValues}. Порядок значений в обоих списках должен совпадать.
     * Если хотя бы один ID не найден, обновление может быть частично выполнено.
     * </p>
     *
     * @param idList список идентификаторов блюд, для которых нужно обновить количество. Не должен быть {@code null}.
     * @param newValues список новых значений количества блюд. Не должен быть {@code null}.
     * @return количество обновленных записей в базе данных.
     */
    int updateDishNums(List<Long> idList, List<Long> newValues);
}
