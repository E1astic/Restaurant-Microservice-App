package ru.liga.kitchen_service.mappers;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;
import ru.liga.kitchen_service.models.entity.Dish;

import java.util.List;

@Mapper
@Repository
public interface DishMapper {

    Dish findById (@Param("id") Long id);

    List<Dish> findByIdIn (@Param("dishIdList") List<Long> dishIdList);
}
