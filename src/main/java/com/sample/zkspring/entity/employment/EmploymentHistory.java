package com.sample.zkspring.entity.employment;

import lombok.Getter;
import lombok.Setter;
import org.springframework.transaction.annotation.Transactional;
import org.zkoss.bind.annotation.Command;

import javax.persistence.*;
import java.io.Serializable;
import java.sql.Date;

@Entity
@Table(name="employee_history", schema = "employment")
public class EmploymentHistory implements Serializable, Cloneable, Comparable<EmploymentHistory> {

    //region > Field
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Getter
    @Setter
    @Column(name = "id")
    private Long id;

    @Getter @Setter
    @Column(name = "active_date")
    private Date activeDate;

    @Getter @Setter
    @Column(name = "history_type")
    @Enumerated(EnumType.STRING)
    private HistoryType historyType;

    @Getter @Setter
    @JoinColumn(name="branch_id")
    @ManyToOne(fetch = FetchType.LAZY)
    private Branch branch;

    @Getter @Setter
    @JoinColumn(name="designation_id")
    @ManyToOne(fetch = FetchType.LAZY)
    private Designation designation;

    @Getter @Setter
    @JoinColumn(name="department_id")
    @ManyToOne(fetch = FetchType.LAZY)
    private Department department;

    @Getter @Setter
    @Column(name = "note")
    private String note;

    @Getter @Setter
    @JoinColumn(name="employee_id")
    @ManyToOne(fetch = FetchType.LAZY)
    private Employee employee;

    @Getter @Setter
    @JoinColumn(name = "supervisor_id")
    @ManyToOne(fetch = FetchType.LAZY)
    private Employee supervisor;
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
        EmploymentHistory other = (EmploymentHistory) obj;
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
    public int compareTo(EmploymentHistory employmentHistory) {
        return this.getId().compareTo(employmentHistory.getId());
    }
    //endregion
}
