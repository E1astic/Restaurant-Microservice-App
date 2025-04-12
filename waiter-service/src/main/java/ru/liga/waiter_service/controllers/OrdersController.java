package ru.liga.waiter_service.controllers;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ru.liga.waiter_service.models.dto.SimpleWaiterOrderResponse;
import ru.liga.waiter_service.models.dto.WaiterOrderRequest;
import ru.liga.waiter_service.models.dto.WaiterOrderResponse;
import ru.liga.waiter_service.models.dto.WaiterOrderStatusResponse;
import ru.liga.waiter_service.models.entity.WaiterOrder;
import ru.liga.waiter_service.services.waiter_order.WaiterOrderService;
import ru.liga.waiter_service.utils.OrderStatus;

import java.util.List;

@RestController
@RequestMapping("/orders")
@RequiredArgsConstructor
public class OrdersController {

    private final WaiterOrderService waiterOrderService;

    private static final String SUCCESS_STATUS_RESPONSE = "The status has been successfully changed";

    @GetMapping()
    public List<WaiterOrderResponse> getAllOrders(){
        return waiterOrderService.getAllOrders();
    }

    @GetMapping("/{id}")
    public WaiterOrderResponse getOrderById(@PathVariable("id") Long id){
        return waiterOrderService.getOrderById(id);
    }

    @PostMapping()
    public ResponseEntity<SimpleWaiterOrderResponse> addOrder(@RequestBody WaiterOrderRequest waiterOrderRequest){
        WaiterOrder waiterOrder = waiterOrderService.addOrder(waiterOrderRequest);
        return ResponseEntity.ok(new SimpleWaiterOrderResponse(waiterOrder.getId()));
    }

    @GetMapping("/{id}/status")
    public ResponseEntity<WaiterOrderStatusResponse> getOrderStatusById(@PathVariable("id") Long id){
        OrderStatus orderStatus = waiterOrderService.getOrderStatusById(id);
        return ResponseEntity.ok(new WaiterOrderStatusResponse(orderStatus));
    }


    @PutMapping("/{id}/status/{status}")
    public ResponseEntity<?> setStatus(@PathVariable("id") Long id, @PathVariable("status") String status){
        waiterOrderService.setStatus(id, status);
        return ResponseEntity.ok(SUCCESS_STATUS_RESPONSE);
    }

}
