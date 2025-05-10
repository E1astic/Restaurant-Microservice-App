package ru.liga.waiter_service.services.waiter_account;

import ru.liga.waiter_service.models.entity.WaiterAccount;

/**
 * Интерфейс для работы с аккаунтами официантов.
 * <p>
 * Предоставляет методы для получения информации об аккаунтах официантов. Используется для взаимодействия
 * с репозиторием аккаунтов и выполнения операций, связанных с получением данных об официантах.
 * </p>
 */
public interface WaiterAccountService {

    /**
     * Возвращает аккаунт официанта по его идентификатору.
     * <p>
     * Метод извлекает из базы данных аккаунт официанта с указанным ID. Если аккаунт с таким ID не найден,
     * может быть выброшено исключение {@link
     * ru.liga.waiter_service.exceptions.WaiterAccountNotFoundException WaiterAccountNotFoundException}.
     * </p>
     *
     * @param id уникальный идентификатор аккаунта официанта. Не должен быть {@code null}.
     * @return объект типа {@link WaiterAccount}, представляющий аккаунт официанта.
     * @throws ru.liga.waiter_service.exceptions.WaiterAccountNotFoundException если аккаунт
     *                                                                          с указанным ID не существует.
     */
    WaiterAccount getWaiterAccountById(Long id);
}
