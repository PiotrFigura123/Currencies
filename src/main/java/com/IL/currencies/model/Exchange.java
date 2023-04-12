package com.IL.currencies.model;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.io.Serializable;


@Getter
@Setter
@NoArgsConstructor
@Entity
@Table(name = "exchange")
public class Exchange implements Serializable {

    @Id
    @Column(name = "transaction_id")
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "transaction_id_seq")
    @SequenceGenerator(
            name = "transaction_id_seq",
            sequenceName = "transaction_seq",
            allocationSize = 1
    )
    private Integer id;
    private Integer versionId;
    public Integer baseCurrency;
    private Integer rateCurrency;
    private Float rateValue;

    public Exchange(final Integer versionId, final Integer baseCurrency, final Integer rateCurrency, final Float rateValue) {
        this.versionId = versionId;
        this.baseCurrency = baseCurrency;
        this.rateCurrency = rateCurrency;
        this.rateValue = rateValue;
    }

    @Override
    public String toString() {
        return "Exchange{" +
                "transactionId=" + id +
                ", versionId=" + versionId +
                ", baseCurrency=" + baseCurrency +
                ", rateCurrency=" + rateCurrency +
                ", rateValue=" + rateValue +
                '}';
    }
}
