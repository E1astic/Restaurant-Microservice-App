package ru.liga.kitchen_service.controllers;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import ru.liga.kitchen_service.models.dto.KitchenOrderRequest;
import ru.liga.kitchen_service.models.dto.KitchenOrderWithDishesResponse;
import ru.liga.kitchen_service.models.dto.SimpleKitchenOrderResponse;
import ru.liga.kitchen_service.models.entity.KitchenOrder;
import ru.liga.kitchen_service.services.kitchen_service.KitchenService;

import java.util.List;

@RestController
@RequestMapping("/orders")
@RequiredArgsConstructor
public class KitchenController {

    private static final String SUCCESS_STATUS_RESPONSE = "The status has been successfully changed";

    private final KitchenService kitchenService;

    @GetMapping()
    public List<KitchenOrderWithDishesResponse> getAllOrders(){
        return kitchenService.getAllOrdersWithDishes();
    }

    @PostMapping()
    public ResponseEntity<SimpleKitchenOrderResponse> acceptOrder(@RequestBody KitchenOrderRequest kitchenOrderRequest){
        KitchenOrder kitchenOrder = kitchenService.acceptOrder(kitchenOrderRequest);
        return ResponseEntity.ok(new SimpleKitchenOrderResponse(kitchenOrder.getId()));
    }

    @PatchMapping("/{id}/status/{status}")
    public ResponseEntity<?> setStatus(@PathVariable Long id, @PathVariable String status) {
        kitchenService.setStatus(id, status);
        return ResponseEntity.ok(SUCCESS_STATUS_RESPONSE);
    }
}
