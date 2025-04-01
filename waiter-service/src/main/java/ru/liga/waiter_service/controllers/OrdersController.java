package ru.liga.waiter_service.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ru.liga.waiter_service.dto.ClientOrder;
import ru.liga.waiter_service.dto.WaiterOrder;
import ru.liga.waiter_service.services.OrderService;
import ru.liga.waiter_service.utils.OrderStatus;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/orders")
public class OrdersController {

    private final OrderService orderService;

    @Autowired
    public OrdersController(OrderService orderService) {
        this.orderService = orderService;
    }

    @GetMapping()
    public List<WaiterOrder> getAllOrders(){
        return orderService.getAllOrders();
    }

    @GetMapping("/{id}")
    public WaiterOrder getOrderById(@PathVariable("id") int id){
        return orderService.getOrderById(id);
    }

    @PostMapping()
    public ResponseEntity<Map<String, Integer>> addOrder(@RequestBody ClientOrder clientOrder){
        int orderId = orderService.addOrder(clientOrder);
        return ResponseEntity.ok(Map.of("orderId", orderId));
    }

    @GetMapping("/{id}/status")
    public ResponseEntity<Map<String, OrderStatus>> getOrderStatusById(@PathVariable("id") int id){
        return ResponseEntity.ok(Map.of("orderStatus", orderService.getOrderStatusById(id)));
    }
}
