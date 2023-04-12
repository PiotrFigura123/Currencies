package com.IL.currencies.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "dates")
public class DatesEntity {
    @Id
    @Column(name = "date_id")
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "date_id_seq")
    @SequenceGenerator(
            name = "date_id_seq",
            sequenceName = "date_seq",
            allocationSize = 1
    )
    private Integer dateId;
    private String dateOfUpdate;

}
