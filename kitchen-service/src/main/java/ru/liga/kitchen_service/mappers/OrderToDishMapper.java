package ru.liga.kitchen_service.mappers;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;
import ru.liga.kitchen_service.models.entity.OrderToDish;

import java.util.Map;

@Mapper
@Repository
public interface OrderToDishMapper {

    void save(@Param("orderToDish") OrderToDish orderToDish);

    void saveAll (@Param("kitchenOrderId") Long kitchenOrderId,
                  @Param("orderToDish") Map<Long, Long> orderToDish);
}
