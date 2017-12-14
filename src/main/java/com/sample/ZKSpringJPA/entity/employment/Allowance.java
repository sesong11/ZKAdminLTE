package com.sample.ZKSpringJPA.entity.employment;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

import com.sample.ZKSpringJPA.utils.Frequency;
import com.sample.ZKSpringJPA.utils.Unit;

import lombok.Getter;
import lombok.Setter;

@Entity
@Table(name = "allowance")
public class Allowance {
    //region > Fields
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Getter @Setter
    @Column(name = "id")
    private Long id;

    @Column(name = "name")
    @Getter @Setter
    private String name;

    @Column(name = "type")
    @Getter @Setter
    private AllowanceType allowanceType;

    @Column(name = "unit")
    @Getter @Setter
    @Enumerated(EnumType.STRING)
    private Unit unit;

    @Column(name = "end_balance")
    @Getter @Setter
    private double endBalance;

    @Column(name = "frequency_accrual")
    @Getter @Setter
    private Frequency frequencyAccrual;

    @Column(name = "n_frequency_accrual")
    @Getter @Setter
    private double nFrequencyAccrual;

    @Column(name = "frequency_renew")
    @Getter @Setter
    private Frequency frequencyRenew;

    @Column(name = "n_frequency_renew")
    @Getter @Setter
    private int nFrequencyRenew;

    @Column(name = "accrual_balance")
    @Getter @Setter
    private double accrualBalance;

    @Column(name = "start_balance")
    @Getter @Setter
    private double startBalance;

    @Column(name = "max_renewal")
    @Setter @Getter
    private double maxRenewal;

    @Column(name = "accrual_on")
    @Getter @Setter
    private double accrualOn;
    //endregion
}
