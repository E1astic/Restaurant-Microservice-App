package ru.liga.common.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * Класс, представляющий успешный ответ на выполнение запроса.
 * <p>
 * Содержит информацию о сообщении, которое возвращается в случае успешного выполнения операции.
 * Используется для формирования ответов API, связанных с успешными действиями.
 * </p>
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
@Schema(description = "Сущность успешного ответа выполнения запроса, содержащая сообщение")
public class SuccessMessageResponse {

    @Schema(description = "Сообщение успешного ответа", example = "Статус заказа был успешно обновлен")
    private String message;
}
