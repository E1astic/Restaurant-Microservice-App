package ru.liga.waiter_service.advices;

import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import ru.liga.common.dto.ErrorMessageResponse;
import ru.liga.common.exceptions.OrderNotFoundException;
import ru.liga.waiter_service.exceptions.IncorrectOrderStatusException;
import ru.liga.waiter_service.exceptions.MenuPositionNotFoundException;
import ru.liga.waiter_service.exceptions.WaiterAccountNotFoundException;

/**
 * Класс-обработчик исключений для контроллера
 * {@link ru.liga.waiter_service.controllers.OrdersController OrdersController}.
 *
 * <p>Обрабатывает исключения, возникающие в процессе работы с заказами,
 * и возвращает соответствующие HTTP-ответы с информацией об ошибке.</p>
 */
@RestControllerAdvice
@Slf4j
public class OrdersControllerAdvice {

    /**
     * Обрабатывает исключение {@link OrderNotFoundException}.
     *
     * @param ex экземпляр исключения
     * @return HTTP-ответ с кодом 404 (Not Found) и сообщением об ошибке
     */
    @ExceptionHandler(OrderNotFoundException.class)
    public ResponseEntity<ErrorMessageResponse> handleException(OrderNotFoundException ex) {
        loggingException(ex);
        ErrorMessageResponse errorMessageResponse = getErrorMessageResponse(ex);
        return new ResponseEntity<>(errorMessageResponse, HttpStatus.NOT_FOUND);
    }

    /**
     * Обрабатывает исключение {@link WaiterAccountNotFoundException}.
     *
     * @param ex экземпляр исключения
     * @return HTTP-ответ с кодом 400 (Bad Request) и сообщением об ошибке
     */
    @ExceptionHandler(WaiterAccountNotFoundException.class)
    public ResponseEntity<ErrorMessageResponse> handleException(WaiterAccountNotFoundException ex) {
        loggingException(ex);
        ErrorMessageResponse errorMessageResponse = getErrorMessageResponse(ex);
        return new ResponseEntity<>(errorMessageResponse, HttpStatus.BAD_REQUEST);
    }

    /**
     * Обрабатывает исключение {@link MenuPositionNotFoundException}.
     *
     * @param ex экземпляр исключения
     * @return HTTP-ответ с кодом 400 (Bad Request) и сообщением об ошибке
     */
    @ExceptionHandler(MenuPositionNotFoundException.class)
    public ResponseEntity<ErrorMessageResponse> handleException(MenuPositionNotFoundException ex) {
        loggingException(ex);
        ErrorMessageResponse errorMessageResponse = getErrorMessageResponse(ex);
        return new ResponseEntity<>(errorMessageResponse, HttpStatus.BAD_REQUEST);
    }

    /**
     * Обрабатывает исключение {@link IncorrectOrderStatusException}.
     *
     * @param ex экземпляр исключения
     * @return HTTP-ответ с кодом 400 (Bad Request) и сообщением об ошибке
     */
    @ExceptionHandler(IncorrectOrderStatusException.class)
    public ResponseEntity<ErrorMessageResponse> handleException(IncorrectOrderStatusException ex) {
        loggingException(ex);
        ErrorMessageResponse errorMessageResponse = getErrorMessageResponse(ex);
        return new ResponseEntity<>(errorMessageResponse, HttpStatus.BAD_REQUEST);
    }

    /**
     * Логирует исключение с уровнем WARN.
     *
     * @param ex экземпляр исключения
     */
    private void loggingException(Exception ex) {
        log.warn(ex.getMessage(), ex);
    }

    /**
     * Создает объект {@link ErrorMessageResponse} на основе сообщения исключения.
     *
     * @param ex экземпляр исключения
     * @return объект с сообщением об ошибке
     */
    private ErrorMessageResponse getErrorMessageResponse(Exception ex) {
        return new ErrorMessageResponse(ex.getMessage());
    }
}
