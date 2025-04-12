package ru.liga.waiter_service.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import ru.liga.waiter_service.models.entity.WaiterOrder;
import ru.liga.waiter_service.utils.OrderStatus;

@Repository
public interface WaiterOrderRepository extends JpaRepository<WaiterOrder, Long> {

    @Modifying
    @Query("UPDATE WaiterOrder wo SET wo.status = :status WHERE wo.id = :id")
    int updateStatus(@Param("id") Long id, @Param("status") OrderStatus status);
}
