package ru.liga.kitchen_service.advices;

import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import ru.liga.common.dto.ErrorMessageResponse;
import ru.liga.kitchen_service.exceptions.IncorrectKitchenStatusException;
import ru.liga.common.exceptions.OrderNotFoundException;

/**
 * Класс-обработчик исключений для контроллера
 * {@link ru.liga.kitchen_service.controllers.KitchenController KitchenController}.
 *
 * <p>Обрабатывает исключения, возникающие в процессе работы приложения,
 * и возвращает соответствующие HTTP-ответы с информацией об ошибке.</p>
 */
@RestControllerAdvice
@Slf4j
public class KitchenControllerAdvice {

    /**
     * Обрабатывает исключение {@link OrderNotFoundException}.
     *
     * <p>Логирует исключение и возвращает HTTP-ответ с кодом 404 (Not Found).</p>
     *
     * @param ex экземпляр исключения
     * @return HTTP-ответ с сообщением об ошибке
     */
    @ExceptionHandler(OrderNotFoundException.class)
    public ResponseEntity<ErrorMessageResponse> handleException(OrderNotFoundException ex) {
        loggingException(ex);
        ErrorMessageResponse errorMessageResponse = getErrorMessageResponse(ex);
        return new ResponseEntity<>(errorMessageResponse, HttpStatus.NOT_FOUND);
    }

    /**
     * Обрабатывает исключение {@link IncorrectKitchenStatusException}.
     *
     * <p>Логирует исключение и возвращает HTTP-ответ с кодом 400 (Bad Request).</p>
     *
     * @param ex экземпляр исключения
     * @return HTTP-ответ с сообщением об ошибке
     */
    @ExceptionHandler(IncorrectKitchenStatusException.class)
    public ResponseEntity<ErrorMessageResponse> handleException(IncorrectKitchenStatusException ex) {
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
