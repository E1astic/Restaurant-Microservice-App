package ru.liga.kitchen_service.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ru.liga.kitchen_service.dto.ClientOrder;
import ru.liga.kitchen_service.dto.KitchenOrder;
import ru.liga.kitchen_service.services.KitchenService;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/orders")
public class KitchenController {

    private static final String SUCCESS_STATUS_RESPONSE = "The status has been successfully changed";

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
    public ResponseEntity<Map<String, Integer>> acceptOrder(@RequestBody ClientOrder clientOrder){
        int orderId = kitchenService.acceptOrder(clientOrder);
        return ResponseEntity.ok(Map.of("orderId", orderId));
    }

    @PatchMapping("/{id}/status/{status}")
    public ResponseEntity<?> setStatus(@PathVariable int id, @PathVariable String status) {
        if(status.equals("reject")) {
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
}
