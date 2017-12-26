package com.sample.ZKSpringJPA.entity.request.leaveform;

import com.sample.ZKSpringJPA.entity.request.Form;
import com.sample.ZKSpringJPA.entity.request.Request;
import com.sample.ZKSpringJPA.entity.request.approval.Approval;
import lombok.Getter;
import lombok.Setter;
import java.sql.Timestamp;

import javax.persistence.*;

@Entity
@Table(name = "leave_form")
public class LeaveForm extends Form {

    //region > Fields
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Getter
    @Setter
    @Column(name = "id")
    private Long id;

    @Getter @Setter
    @Column(nullable = false, name = "leave_type")
    private LeaveType leaveType;

    @Getter @Setter
    @Column(name = "specify")
    private String specify;

    @Getter @Setter
    @Column(nullable = false, name = "from_date")
    private Timestamp fromDate;

    @Getter @Setter
    @Column(nullable = false, name = "to_date")
    private Timestamp toDate;

    @Getter @Setter
    @Column(name = "total_days")
    private double totalDays;

    @Getter @Setter
    @Column(name = "reason", length = 2048)
    private String reason;

    @Getter @Setter
    @Column(name = "address", length = 2048)
    private String address;

    @Getter @Setter
    @Column(name = "confirm_policy")
    private boolean confirmPolicy;
    //endregion
}
