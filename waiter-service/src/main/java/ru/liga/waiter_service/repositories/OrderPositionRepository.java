package ru.liga.waiter_service.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import ru.liga.waiter_service.models.entity.OrderPosition;

/**
 * Репозиторий для работы с данными о позициях в заказах.
 * <p>
 * Интерфейс предоставляет методы для выполнения операций CRUD сущности {@link OrderPosition}
 * через Spring Data JPA. Наследуется от {@link JpaRepository}, что обеспечивает базовые операции
 * для управления позициями в заказах.
 * </p>
 */
@Repository
public interface OrderPositionRepository extends JpaRepository<OrderPosition, Long> {
}
