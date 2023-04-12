package com.IL.currencies.model;

import lombok.*;

import javax.persistence.*;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "currency")
public class Currency {
    @Id
    @Column(name = "currency_id")
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "currency_id_seq")
    @SequenceGenerator(
            name = "currency_id_seq",
            sequenceName = "currency_seq",
            allocationSize = 1
    )
    private Integer currencyId;
    private String base;

}
