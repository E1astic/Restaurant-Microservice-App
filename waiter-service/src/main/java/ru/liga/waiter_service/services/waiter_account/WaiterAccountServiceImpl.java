package ru.liga.waiter_service.services.waiter_account;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import ru.liga.waiter_service.models.entity.WaiterAccount;
import ru.liga.waiter_service.repositories.WaiterAccountRepository;
import ru.liga.waiter_service.exceptions.WaiterAccountNotFoundException;

@Service
@RequiredArgsConstructor
@Slf4j
public class WaiterAccountServiceImpl implements WaiterAccountService {

    private final WaiterAccountRepository waiterAccountRepository;

    @Override
    public WaiterAccount getWaiterAccountById(Long id) {
        log.info("Запрос на получение данных официанта с id = {}", id);
        return waiterAccountRepository.findById(id)
                .orElseThrow(() -> new WaiterAccountNotFoundException(
                        String.format("Официанта с id = %d не существует", id)));
    }
}
