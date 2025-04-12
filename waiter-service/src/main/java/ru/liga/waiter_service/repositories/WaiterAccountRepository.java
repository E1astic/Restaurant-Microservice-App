package ru.liga.waiter_service.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import ru.liga.waiter_service.models.entity.WaiterAccount;

@Repository
public interface WaiterAccountRepository extends JpaRepository<WaiterAccount, Long> {
}
