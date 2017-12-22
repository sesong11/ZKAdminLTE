package com.sample.ZKSpringJPA.entity.request.approval;

import com.sample.ZKSpringJPA.entity.employment.Employee;
import com.sample.ZKSpringJPA.entity.employment.EmployeeAllowance;
import com.sample.ZKSpringJPA.entity.request.DecisionStatus;
import com.sample.ZKSpringJPA.entity.request.Request;
import lombok.Getter;
import lombok.Setter;
import org.zkoss.bind.annotation.Command;

import javax.persistence.*;
import java.io.Serializable;
import java.sql.Timestamp;

@Entity
@Table(name = "approval")
public class Approval implements Serializable, Comparable<Approval>, Cloneable{
    //region > Fields
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Getter @Setter
    @Column(name = "id")
    private Long id;

    @Getter @Setter
    @Column(name = "approval_type")
    @Enumerated(EnumType.STRING)
    private ApprovalType approvalType;

    @Getter @Setter
    @JoinColumn(name = "approve_person_id")
    @ManyToOne(fetch = FetchType.LAZY)
    private Employee approvePerson;

    @Getter @Setter
    @Column(name = "decision_status")
    @Enumerated(EnumType.STRING)
    private DecisionStatus decisionStatus;

    @Getter @Setter
    @Column(name = "approve_date")
    private Timestamp approveDate;

    @Getter @Setter
    @JoinColumn(name = "request_id")
    @ManyToOne(fetch = FetchType.LAZY)
    private Request request;

    @Getter @Setter
    @Column(name = "comment")
    private String comment;

    @Getter @Setter
    @Column(name = "sorted_index")
    private int sortedIndex;
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
        Approval other = (Approval) obj;
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
    public int compareTo(Approval approval) {
        return Integer.compare(sortedIndex, approval.getSortedIndex());
    }
    //endregion
}
