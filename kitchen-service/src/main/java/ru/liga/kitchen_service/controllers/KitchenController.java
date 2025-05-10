package ru.liga.kitchen_service.controllers;

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
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import ru.liga.common.dto.ErrorMessageResponse;
import ru.liga.common.dto.KitchenOrderRequest;
import ru.liga.common.dto.SuccessMessageResponse;
import ru.liga.kitchen_service.models.dto.KitchenOrderWithDishesResponse;
import ru.liga.kitchen_service.models.dto.SimpleKitchenOrderResponse;
import ru.liga.kitchen_service.models.entity.KitchenOrder;
import ru.liga.kitchen_service.services.kitchen.KitchenService;

import java.util.List;

/**
 * Контроллер для управления заказами кухни.
 * <p>
 * Предоставляет RESTful API для выполнения операций с заказами, обрабатываемыми кухней:
 * <ul>
 *     <li>Получение информации о всех заказах ({@link #getAllOrders()}).</li>
 *     <li>Создание нового заказа кухни ({@link #acceptOrder(KitchenOrderRequest)}).</li>
 *     <li>Изменение статуса заказа по его ID ({@link #setStatus(Long, String)}).</li>
 * </ul>
 * </p>
 * <p>
 * Класс является частью микросервиса кухни и взаимодействует с сервисом {@link KitchenService}
 * для выполнения бизнес-логики.
 * </p>
 */
@RestController
@RequestMapping("/orders")
@RequiredArgsConstructor
@Tag(name = "KitchenController", description = "Контроллер для взаимодействия с заказами кухни")
@ApiResponse(responseCode = "200", description = "Операция выполнена успешно")
public class KitchenController {

    private final KitchenService kitchenService;

    /**
     * Возвращает информацию о всех заказах кухни.
     * <p>
     * Метод обрабатывает GET-запрос для получения списка всех заказов. Каждый заказ представлен в виде объекта
     * {@link KitchenOrderWithDishesResponse}, содержащего информацию о заказе и связанных блюдах.
     * Если заказы отсутствуют, возвращается пустой список.
     * </p>
     *
     * @return список объектов типа {@link KitchenOrderWithDishesResponse}, содержащих информацию о всех заказах.
     *         Если заказы отсутствуют, возвращается пустой список.
     */
    @GetMapping()
    @Operation(summary = "Получение информации о всех заказах кухни")
    public List<KitchenOrderWithDishesResponse> getAllOrders() {
        return kitchenService.getAllOrdersWithDishes();
    }

    /**
     * Создает новый заказ кухни на основе данных из запроса.
     * <p>
     * Метод обрабатывает POST-запрос для создания нового заказа. Данные передаются в формате JSON
     * через объект {@link KitchenOrderRequest}. После успешного создания заказа возвращается
     * уникальный идентификатор созданного заказа.
     * </p>
     *
     * @param kitchenOrderRequest объект запроса, содержащий информацию для создания заказа.
     *                            Не должен быть {@code null}.
     * @return объект {@link ResponseEntity}, содержащий уникальный идентификатор созданного заказа
     *         в формате {@link SimpleKitchenOrderResponse}.
     */
    @PostMapping()
    @Operation(summary = "Создание нового заказа кухни")
    public ResponseEntity<SimpleKitchenOrderResponse> receiveOrder(
            @RequestBody KitchenOrderRequest kitchenOrderRequest) {
        KitchenOrder kitchenOrder = kitchenService.receiveOrder(kitchenOrderRequest);
        return ResponseEntity.ok(new SimpleKitchenOrderResponse(kitchenOrder.getId()));
    }

    /**
     * Изменяет статус заказа кухни по его идентификатору.
     * <p>
     * Метод обрабатывает PATCH-запрос для изменения статуса заказа. Новый статус передается как часть URL.
     * Если заказ с указанным ID не существует, возвращается ошибка с кодом 404. Если передан некорректный статус,
     * возвращается ошибка с кодом 400. При успешном обновлении статуса возвращается сообщение об успехе.
     * </p>
     *
     * @param id уникальный идентификатор заказа. Передается как путь в URL.
     * @param status новый статус заказа (например, "PREPARING", "REJECTED", "READY"). Передается как путь в URL.
     * @return объект {@link ResponseEntity}, содержащий сообщение об успешном обновлении статуса.
     * @throws ru.liga.common.exceptions.OrderNotFoundException если заказ с указанным ID не существует.
     * @throws ru.liga.kitchen_service.exceptions.IncorrectKitchenStatusException если передан некорректный
     * статус заказа.
     */
    @PatchMapping("/{id}/status/{status}")
    @Operation(summary = "Изменение статуса заказа кухни с указанным ID")
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
                    required = true, example = "PREPARING/REJECTED/READY") String status) {
        kitchenService.setStatus(id, status);
        return ResponseEntity.ok(new SuccessMessageResponse("Статус заказа был успешно обновлен"));
    }
}
