package ru.liga.waiter_service.controllers;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.ExampleObject;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import ru.liga.common.dto.ErrorMessageResponse;
import ru.liga.waiter_service.models.dto.SimpleWaiterOrderResponse;
import ru.liga.common.dto.SuccessMessageResponse;
import ru.liga.waiter_service.models.dto.WaiterOrderRequest;
import ru.liga.waiter_service.models.dto.WaiterOrderResponse;
import ru.liga.waiter_service.models.dto.WaiterOrderStatusResponse;
import ru.liga.waiter_service.models.entity.WaiterOrder;
import ru.liga.waiter_service.services.waiter_order.WaiterOrderService;
import ru.liga.waiter_service.models.enums.OrderStatus;

import java.util.List;

/**
 * Контроллер для управления заказами официантов.
 * <p>
 * Предоставляет RESTful API для выполнения операций с заказами официантов:
 * <ul>
 *     <li>Создание нового заказа ({@link #addOrder(WaiterOrderRequest)}).</li>
 *     <li>Получение информации о заказе по его ID ({@link #getOrderById(Long)}).</li>
 *     <li>Получение информации о всех заказах ({@link #getAllOrders()}).</li>
 *     <li>Получение статуса заказа по его ID ({@link #getOrderStatusById(Long)}).</li>
 *     <li>Изменение статуса заказа ({@link #setStatus(Long, String)}).</li>
 * </ul>
 * </p>
 */
@RestController
@RequestMapping("/orders")
@RequiredArgsConstructor
@Tag(name = "OrdersController", description = "Контроллер для взаимодействия с заказами официантов")
@ApiResponse(responseCode = "200", description = "Операция выполнена успешно")
public class OrdersController {

    private final WaiterOrderService waiterOrderService;

    /**
     * Возвращает информацию о всех заказах официантов.
     * <p>
     * Метод обрабатывает GET-запрос для получения списка всех заказов. Каждый заказ представлен в виде объекта
     * {@link WaiterOrderResponse}, содержащего информацию о заказе. Если заказы отсутствуют, возвращается
     * пустой список.
     * </p>
     *
     * @return список объектов типа {@link WaiterOrderResponse}, содержащих информацию о всех заказах.
     * Если заказы отсутствуют, возвращается пустой список.
     */
    @GetMapping()
    @Operation(summary = "Получение информации о всех заказах официантов")
    public List<WaiterOrderResponse> getAllOrders() {
        return waiterOrderService.getAllOrders();
    }

    /**
     * Возвращает информацию о заказе по его ID.
     * <p>
     * Метод обрабатывает GET-запрос для получения данных о заказе. Если заказ с указанным ID существует,
     * возвращается объект {@link WaiterOrderResponse}, содержащий информацию о заказе.
     * Если заказ не найден, возвращается ошибка с кодом 404 и сообщением об ошибке.
     * </p>
     *
     * @param id уникальный идентификатор заказа. Передается как путь в URL.
     * @return объект типа {@link WaiterOrderResponse}, содержащий информацию о заказе.
     * @throws ru.liga.common.exceptions.OrderNotFoundException если заказа с переданным ID не существует
     */
    @GetMapping("/{id}")
    @Operation(summary = "Получение информации о заказе по его ID")
    @ApiResponse(responseCode = "404", description = "Заказа с переданным ID не существует",
            content = @Content(mediaType = "application/json",
                    schema = @Schema(implementation = ErrorMessageResponse.class)))
    public WaiterOrderResponse getOrderById(
            @PathVariable("id") @Parameter(description = "ID заказа", required = true, example = "10") Long id) {
        return waiterOrderService.getOrderById(id);
    }

    /**
     * Создает новый заказ официанта на основе переданных данных.
     * <p>
     * Метод обрабатывает POST-запрос для создания нового заказа. При успешном создании заказа
     * возвращается объект {@link SimpleWaiterOrderResponse}, содержащий ID созданного заказа.
     * Если переданные данные некорректны или официант/блюда с указанными ID не существуют,
     * возвращается ошибка с кодом 400 и сообщением об ошибке.
     * </p>
     *
     * @param waiterOrderRequest объект типа {@link WaiterOrderRequest}, содержащий данные для создания заказа.
     *                           Должен включать ID официанта и список ID блюд.
     * @return {@link ResponseEntity} с объектом {@link SimpleWaiterOrderResponse}, содержащим ID созданного заказа.
     * Код ответа: 200 (OK).
     * @throws ru.liga.waiter_service.exceptions.WaiterAccountNotFoundException
     * если официанта с переданным id не существует
     * @throws ru.liga.waiter_service.exceptions.MenuPositionNotFoundException  если блюда с переданным id нет в меню
     */
    @PostMapping()
    @Operation(summary = "Создание нового заказа официанта")
    @ApiResponse(responseCode = "400", description = "Официанта или блюда с переданными ID не существует",
            content = @Content(mediaType = "application/json",
                    schema = @Schema(implementation = ErrorMessageResponse.class),
                    examples = @ExampleObject("{\"errorMessage\": \"Официанта с id = 10 не существует\"}")))
    public ResponseEntity<SimpleWaiterOrderResponse> addOrder(@RequestBody WaiterOrderRequest waiterOrderRequest) {
        WaiterOrder waiterOrder = waiterOrderService.addOrder(waiterOrderRequest);
        return ResponseEntity.ok(new SimpleWaiterOrderResponse(waiterOrder.getId()));
    }

    /**
     * Возвращает статус заказа по его ID.
     * <p>
     * Метод обрабатывает GET-запрос для получения статуса заказа. Если заказ с указанным ID существует,
     * возвращается объект {@link WaiterOrderStatusResponse}, содержащий текущий статус заказа.
     * Если заказ не найден, возвращается ошибка с кодом 404 и сообщением об ошибке.
     * </p>
     *
     * @param id уникальный идентификатор заказа. Передается как путь в URL.
     * @return {@link ResponseEntity} с объектом {@link WaiterOrderStatusResponse}, содержащим статус заказа.
     * Код ответа: 200 (OK).
     * @throws ru.liga.common.exceptions.OrderNotFoundException если официанта с переданным id не существует.
     */
    @GetMapping("/{id}/status")
    @Operation(summary = "Получение статуса заказа по ID заказа")
    @ApiResponse(responseCode = "404", description = "Заказа с переданным ID не существует",
            content = @Content(mediaType = "application/json",
                    schema = @Schema(implementation = ErrorMessageResponse.class)))
    public ResponseEntity<WaiterOrderStatusResponse> getOrderStatusById(
            @PathVariable("id") @Parameter(description = "ID заказа", required = true, example = "10") Long id) {
        OrderStatus orderStatus = waiterOrderService.getOrderStatusById(id);
        return ResponseEntity.ok(new WaiterOrderStatusResponse(orderStatus));
    }

    /**
     * Изменяет статус заказа официанта с указанным ID.
     * <p>
     * Метод обрабатывает PUT-запрос для обновления статуса заказа. Если заказ с указанным ID существует
     * и переданный статус корректен, статус успешно обновляется, и возвращается сообщение об успехе.
     * Если заказ не найден, возвращается ошибка с кодом 404. Если передан некорректный статус,
     * возвращается ошибка с кодом 400.
     * </p>
     *
     * @param id     уникальный идентификатор заказа. Передается как путь в URL.
     * @param status новый статус заказа. Передается как путь в URL.
     *               Корректные значения: "ACCEPTED", "REJECTED", "READY".
     * @return {@link ResponseEntity} с объектом {@link SuccessMessageResponse},
     * содержащим сообщение об успешном изменении статуса.
     * Код ответа: 200 (OK).
     * @throws ru.liga.common.exceptions.OrderNotFoundException если заказа с переданным id не существует
     * @throws ru.liga.waiter_service.exceptions.IncorrectOrderStatusException если переданный статус некорректен
     */
    @PutMapping("/{id}/status/{status}")
    @Operation(summary = "Изменение статуса заказа официанта с указанным ID")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "404", description = "Заказа с переданным ID не существует",
                    content = @Content(mediaType = "application/json",
                            schema = @Schema(implementation = ErrorMessageResponse.class))),
            @ApiResponse(responseCode = "400", description = "Передан некорректный статус заказа официанта",
                    content = @Content(mediaType = "application/json",
                            schema = @Schema(implementation = ErrorMessageResponse.class),
                            examples = @ExampleObject(value = "{\"errorMessage\": \"Некорректный статус заказа\"}")))
    })
    public ResponseEntity<SuccessMessageResponse> setStatus(
            @PathVariable("id") @Parameter(description = "ID заказа", required = true, example = "10") Long id,
            @PathVariable("status") @Parameter(description = "Новый статус заказа",
                    required = true, example = "ACCEPTED/REJECTED/READY") String status) {
        waiterOrderService.setStatus(id, status);
        return ResponseEntity.ok(new SuccessMessageResponse("Статус заказа был успешно обновлен"));
    }

}
