package ru.liga.waiter_service.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import ru.liga.waiter_service.models.entity.Menu;

import java.util.List;

/**
 * Репозиторий для работы с данными о блюдах меню.
 * <p>
 * Интерфейс предоставляет методы для выполнения операций CRUD и дополнительных запросов к таблице "menu"
 * через Spring Data JPA. Наследуется от {@link JpaRepository}, что обеспечивает базовые операции
 * сущности {@link Menu}.
 * </p>
 */
@Repository
public interface MenuRepository extends JpaRepository<Menu, Long> {

    /**
     * Возвращает список блюд по их идентификаторам.
     * <p>
     * Метод выполняет запрос к базе данных для получения всех блюд, чьи идентификаторы входят
     * в предоставленный список. Если ни одно из указанных ID не найдено, возвращается пустой список.
     * </p>
     *
     * @param ids список идентификаторов блюд. Не должен быть {@code null}.
     * @return список объектов типа {@link Menu}, соответствующих указанным идентификаторам.
     * Если совпадений нет, возвращается пустой список.
     */
    List<Menu> findByIdIn(List<Long> ids);
}
