package ru.liga.common.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * Класс, представляющий ответ с сообщением об ошибке.
 * <p>
 * Содержит информацию о сообщении об ошибке, которое возвращается в случае возникновения исключительной ситуации.
 * Используется для формирования ответов API, связанных с ошибками.
 * </p>
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
@Schema(description = "Сущность ответа выполнения запроса, содержащая сообщение об ошибке")
public class ErrorMessageResponse {

    @Schema(description = "Сообщение об ошибке", example = "Заказа с id = 10 не существует")
    private String errorMessage;
}
