package ru.liga.kitchen_service.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ru.liga.kitchen_service.dto.ClientOrder;
import ru.liga.kitchen_service.dto.KitchenOrder;
import ru.liga.kitchen_service.services.KitchenService;
import ru.liga.kitchen_service.utils.OrderNotFoundException;


import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/orders")
public class KitchenController {

    private static final Map<String, String> SUCCESS_STATUS_RESPONSE
            = Map.of("message", "the status has been successfully changed");

    private final KitchenService kitchenService;

    @Autowired
    public KitchenController(KitchenService kitchenService) {
        this.kitchenService = kitchenService;
    }

    @GetMapping()
    public List<KitchenOrder> getAllOrders(){
        return kitchenService.getAllOrders();
    }

    @PostMapping()
    public ResponseEntity<Map<String, Integer>> addOrder(@RequestBody ClientOrder clientOrder){
        int orderId = kitchenService.addOrder(clientOrder);
        return ResponseEntity.ok(Map.of("orderId", orderId));
    }


    @PatchMapping("/{id}/status/{status}")
    public ResponseEntity<Map<String, String>> setStatus(@PathVariable int id, @PathVariable String status) {
        if(status.equals("accept")) {
            kitchenService.acceptOrder(id);
        }
        else if(status.equals("reject")) {
            kitchenService.rejectOrder(id);
        }
        else if(status.equals("ready")) {
            kitchenService.readyOrder(id);
        }
        else{
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(SUCCESS_STATUS_RESPONSE);
    }


    @ExceptionHandler(OrderNotFoundException.class)
    public ResponseEntity<Map<String, String>> handleException(OrderNotFoundException ex){
        return new ResponseEntity<>(Map.of("message", ex.getMessage()), HttpStatus.NOT_FOUND);
    }
}
