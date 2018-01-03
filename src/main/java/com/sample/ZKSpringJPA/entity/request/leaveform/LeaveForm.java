package com.sample.ZKSpringJPA.entity.request.leaveform;

import com.sample.ZKSpringJPA.entity.request.Form;
import com.sample.ZKSpringJPA.entity.request.Request;
import com.sample.ZKSpringJPA.entity.request.approval.Approval;
import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;
import java.sql.Timestamp;

import javax.persistence.*;
import javax.validation.constraints.*;

@Entity
@Table(name = "leave_form", schema = "req")
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
    @NotNull(message = "You can't leave this empty.")
    private LeaveType leaveType;

    @Getter @Setter
    @Column(name = "specify")
    private String specify;

    @Getter @Setter
    @Column(nullable = false, name = "from_date")
    @NotNull(message = "You can't leave this empty.")
    private Timestamp fromDate;

    @Getter @Setter
    @Column(nullable = false, name = "to_date")
    @NotNull(message = "You can't leave this empty.")
    private Timestamp toDate;

    @Getter @Setter
    @Column(name = "total_days", nullable = false)
    @NotNull(message = "You can't leave this empty.")
    private Double totalDays;

    @Getter @Setter
    @Column(name = "reason", length = 2048)
    private String reason;

    @Setter @Getter
    @Column(name = "address", length = 2048)
    @NotEmpty(message = "You can't leave this empty.")
    @Size(max = 2048, message = "Value is more than allowable maximum characters of 2048.")
    private String address;

    @Getter @Setter
    @Column(name = "confirm_policy")
    @AssertTrue(message = "You can't leave this empty.")
    private boolean confirmPolicy;
    //endregion
}
