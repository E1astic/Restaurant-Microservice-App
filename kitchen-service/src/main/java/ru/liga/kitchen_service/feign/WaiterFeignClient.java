package ru.liga.kitchen_service.feign;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;

@FeignClient(name = "waiterFeignClient")
public interface WaiterFeignClient {

    @PutMapping("/orders/{id}/status/{status}")
    void setStatus(@PathVariable("id") Long id, @PathVariable("status") String status);
}
