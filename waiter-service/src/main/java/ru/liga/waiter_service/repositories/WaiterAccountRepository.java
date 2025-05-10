package ru.liga.waiter_service.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import ru.liga.waiter_service.models.entity.WaiterAccount;

/**
 * Репозиторий для работы с данными об аккаунтах официантов.
 * <p>
 * Интерфейс предоставляет методы для выполнения операций CRUD сущности {@link WaiterAccount}
 * через Spring Data JPA. Наследуется от {@link JpaRepository}, что обеспечивает базовые операции
 * для управления аккаунтами официантов.
 * </p>
 */
@Repository
public interface WaiterAccountRepository extends JpaRepository<WaiterAccount, Long> {
}
