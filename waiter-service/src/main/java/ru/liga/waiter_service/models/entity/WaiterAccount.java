package ru.liga.waiter_service.models.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.OffsetDateTime;
import java.util.List;

@Entity
@Table(name = "waiter_account")
@Getter
@Setter
@NoArgsConstructor
public class WaiterAccount {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @Column(name = "name")
    private String name;

    @Column(name = "employment_date")
    private OffsetDateTime employmentDate;

    @Column(name = "sex")
    private String sex;

    @OneToMany(mappedBy = "waiter")
    private List<WaiterOrder> orders;
}
