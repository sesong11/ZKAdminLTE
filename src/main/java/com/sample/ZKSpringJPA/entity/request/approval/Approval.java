package com.sample.ZKSpringJPA.entity.request.approval;

import com.sample.ZKSpringJPA.entity.employment.Employee;
import com.sample.ZKSpringJPA.entity.employment.EmployeeAllowance;
import com.sample.ZKSpringJPA.entity.request.DecisionStatus;
import com.sample.ZKSpringJPA.entity.request.Request;
import lombok.Getter;
import lombok.Setter;

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
        //use this statement to avoid null pointer exception, if ids are null.
        if(this.getId()==approval.getId()){
            return Integer.compare(this.getApprovalType().getValue(), approval.getApprovalType().getValue());
        }
        return this.getId().compareTo(approval.getId());
    }
    //endregion
}
