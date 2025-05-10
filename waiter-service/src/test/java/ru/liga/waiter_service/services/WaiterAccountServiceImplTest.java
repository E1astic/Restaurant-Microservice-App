package ru.liga.waiter_service.services;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.jupiter.api.function.Executable;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import ru.liga.waiter_service.models.entity.WaiterAccount;
import ru.liga.waiter_service.repositories.WaiterAccountRepository;
import ru.liga.waiter_service.services.waiter_account.WaiterAccountServiceImpl;
import ru.liga.waiter_service.exceptions.WaiterAccountNotFoundException;

import java.time.OffsetDateTime;
import java.util.Optional;


@ExtendWith(MockitoExtension.class)
public class WaiterAccountServiceImplTest {

    @Mock
    private WaiterAccountRepository waiterAccountRepository;

    @InjectMocks
    private WaiterAccountServiceImpl waiterAccountService;

    @Test
    void shouldReturnWaiterAccountById() {
        // Подготовка
        WaiterAccount expected = new WaiterAccount(1L, "Name", OffsetDateTime.now(), "MALE", null);
        Mockito.when(waiterAccountRepository.findById(1L)).thenReturn(Optional.of(expected));

        // Действие
        WaiterAccount actual = waiterAccountService.getWaiterAccountById(1L);

        // Проверка
        Assertions.assertEquals(expected, actual);
    }

    @Test
    void shouldThrowExceptionIfWaiterAccountNotFound() {
        // Подготовка
        Mockito.when(waiterAccountRepository.findById(1L)).thenReturn(Optional.empty());

        // Действие
        Executable executable = () -> waiterAccountService.getWaiterAccountById(1L);

        // Проверка
        Assertions.assertThrows(WaiterAccountNotFoundException.class, executable);
    }
}
