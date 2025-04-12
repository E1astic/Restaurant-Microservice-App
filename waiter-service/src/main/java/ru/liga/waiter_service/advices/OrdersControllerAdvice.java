package ru.liga.waiter_service.advices;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import ru.liga.waiter_service.utils.MenuPositionNotFoundException;
import ru.liga.waiter_service.utils.OrderNotFoundException;
import ru.liga.waiter_service.utils.WaiterAccountNotFoundException;

@RestControllerAdvice
public class OrdersControllerAdvice {

    @ExceptionHandler(OrderNotFoundException.class)
    public ResponseEntity<String> handleException(OrderNotFoundException ex){
        return new ResponseEntity<>(ex.getMessage(), HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler(WaiterAccountNotFoundException.class)
    public ResponseEntity<String> handleException(WaiterAccountNotFoundException ex){
        return new ResponseEntity<>(ex.getMessage(), HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(MenuPositionNotFoundException.class)
    public ResponseEntity<String> handleException(MenuPositionNotFoundException ex){
        return new ResponseEntity<>(ex.getMessage(), HttpStatus.BAD_REQUEST);
    }
}
