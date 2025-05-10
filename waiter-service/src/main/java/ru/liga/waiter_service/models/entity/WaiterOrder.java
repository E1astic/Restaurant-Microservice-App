package ru.liga.waiter_service.models.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import jakarta.persistence.OneToOne;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import ru.liga.waiter_service.models.enums.OrderStatus;

import java.time.OffsetDateTime;
import java.util.List;

@Entity
@Table(name = "waiter_order")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
public class WaiterOrder {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    @EqualsAndHashCode.Include
    private Long id;

    @Column(name = "status")
    @Enumerated(EnumType.STRING)
    @EqualsAndHashCode.Include
    private OrderStatus status;

    @Column(name = "create_dttm")
    @EqualsAndHashCode.Include
    private OffsetDateTime createDateTime;

    @Column(name = "table_no")
    @EqualsAndHashCode.Include
    private String tableNo;

    @OneToOne(mappedBy = "order")
    private Payment payment;

    @ManyToOne
    @JoinColumn(name = "waiter_id", referencedColumnName = "id")
    @EqualsAndHashCode.Include
    private WaiterAccount waiter;

    @OneToMany(mappedBy = "order")
    private List<OrderPosition> positions;
}
