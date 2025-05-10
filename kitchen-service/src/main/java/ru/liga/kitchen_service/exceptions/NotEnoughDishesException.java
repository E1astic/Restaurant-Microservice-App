package ru.liga.kitchen_service.exceptions;

/**
 * Исключение используется в случае, когда при обработке принятия/отклонения заказа кухней
 * выясняется, что на складе не хватает какого-либо блюда.
 */
public class NotEnoughDishesException extends RuntimeException {

  /**
   * Конструктор с параметром, принимающий сообщение об ошибке и вызывающий
   * конструктор базового класса {@link RuntimeException}.
   *
   * @param message Строка с сообщением об ошибке
   */
  public NotEnoughDishesException(String message) {
    super(message);
  }
}
