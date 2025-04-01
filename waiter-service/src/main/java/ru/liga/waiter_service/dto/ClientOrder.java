package ru.liga.waiter_service.dto;

import lombok.*;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ClientOrder {
    private String dish;
    private String client;
}
