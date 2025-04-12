package ru.liga.waiter_service.models.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.MapsId;
import jakarta.persistence.OneToOne;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.math.BigDecimal;
import java.time.OffsetDateTime;

@Entity
@Table(name = "payment")
@Getter
@Setter
@NoArgsConstructor
public class Payment {

    @Id
    private Long id;  // является одновременно и первичным, и внешним ключом

    @OneToOne()
    @JoinColumn(name = "id", referencedColumnName = "id")
    @MapsId("id")  // значение первичного ключа Payment берется из связанной сущности WaiterOrder
    private WaiterOrder order;

    @Column(name = "payment_type")
    private String paymentType;

    @Column(name = "payment_date")
    private OffsetDateTime paymentDate;

    @Column(name = "payment_sum")
    private BigDecimal paymentSum;
}
