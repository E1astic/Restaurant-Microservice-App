package ru.liga.waiter_service.models.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
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
@AllArgsConstructor
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
public class WaiterAccount {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    @EqualsAndHashCode.Include
    private Long id;

    @Column(name = "name")
    @EqualsAndHashCode.Include
    private String name;

    @Column(name = "employment_date")
    @EqualsAndHashCode.Include
    private OffsetDateTime employmentDate;

    @Column(name = "sex")
    @EqualsAndHashCode.Include
    private String sex;

    @OneToMany(mappedBy = "waiter")
    private List<WaiterOrder> orders;
}
