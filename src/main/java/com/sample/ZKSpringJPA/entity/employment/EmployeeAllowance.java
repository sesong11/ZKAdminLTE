package com.sample.ZKSpringJPA.entity.employment;

import com.sample.ZKSpringJPA.utils.AccrualFormula;
import lombok.Getter;
import lombok.Setter;
import org.joda.time.LocalDate;

import javax.persistence.*;
import java.io.Serializable;
import java.sql.Date;

@Entity
@Table(name = "employee_allowance")
public class EmployeeAllowance implements Serializable, Comparable<EmployeeAllowance>{

    //region > Fields
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Getter @Setter
    @Column(name = "id")
    private Long id;

    @JoinColumn(name = "employee_id")
    @Getter @Setter
    @ManyToOne(fetch = FetchType.LAZY)
    private Employee employee;

    @JoinColumn(name = "allowance_id")
    @Getter @Setter
    @ManyToOne(fetch = FetchType.LAZY)
    private Allowance allowance;

    @Column(name = "active_date")
    @Getter @Setter
    private Date activeDate;

    //endregion

    //region > Programmatic
    public double getAllowanceBalance(){
        AccrualFormula formula = allowance.getFrequencyRenew().getAccrualFormula();
        formula.setAllowance(allowance);
        formula.setFromDate(new LocalDate(activeDate));
        double balance = formula.accrualEnd();
        double endBalance = allowance.getEndBalance();
        return endBalance < balance? endBalance : balance;
    }

    public double getAccrualBalance(){
        AccrualFormula formula = allowance.getFrequencyRenew().getAccrualFormula();
        formula.setAllowance(allowance);
        formula.setFromDate(new LocalDate(activeDate));
        double balance = formula.accrualToday();
        double endBalance = allowance.getEndBalance();
        return endBalance < balance? endBalance : balance;
    }

    public double getExistingBalance(){
        return getAllowanceBalance() - getUsedBalance();
    }

    public double getUsedBalance(){
        return 0;
    }

    //endregion

    //region > Serialize
    @Override
    public boolean equals(Object obj) {
        if(this == obj)
            return true;
        if(obj == null)
            return false;
        if(getClass() != obj.getClass())
            return false;
        EmployeeAllowance other = (EmployeeAllowance) obj;
        if(id == null) {
            if(other.id != null)
                return false;
        } else if(!id.equals(other.id))
            return false;
        return true;
    }

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + ((id == null) ? 0 : id.hashCode());
        return result;
    }

    @Override
    public int compareTo(EmployeeAllowance employeeAllowance) {
        return this.getId().compareTo(employeeAllowance.getId());
    }
    //endregion
}
