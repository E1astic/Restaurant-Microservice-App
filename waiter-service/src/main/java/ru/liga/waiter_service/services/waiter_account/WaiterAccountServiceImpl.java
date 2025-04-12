package ru.liga.waiter_service.services.waiter_account;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import ru.liga.waiter_service.models.entity.WaiterAccount;
import ru.liga.waiter_service.repositories.WaiterAccountRepository;
import ru.liga.waiter_service.utils.WaiterAccountNotFoundException;

@Service
@RequiredArgsConstructor
public class WaiterAccountServiceImpl implements WaiterAccountService {

    private final WaiterAccountRepository waiterAccountRepository;

    private static final String ERR_MESSAGE = "WaiterAccount with id = %d not found";

    public WaiterAccount getWaiterAccountById(Long id) {
        return waiterAccountRepository.findById(id)
                .orElseThrow(() -> new WaiterAccountNotFoundException(String.format(ERR_MESSAGE, id)));
    }
}
