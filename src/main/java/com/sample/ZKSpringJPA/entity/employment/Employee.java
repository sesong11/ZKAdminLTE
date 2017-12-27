package com.sample.ZKSpringJPA.entity.employment;

import com.sample.ZKSpringJPA.entity.authentication.User;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.io.Serializable;
import java.sql.Date;
import java.time.LocalDate;
import java.util.Comparator;
import java.util.List;
import java.util.SortedSet;
import java.util.TreeSet;

@Entity
@Table(name="employee", schema = "employment")
public class Employee implements Serializable, Cloneable{

    //region > Fields
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Getter @Setter
    @Column(name = "id")
    private Long id;

    @Getter @Setter
    @Column(name = "code")
    private String code;

    @Getter @Setter
    @Column(name = "first_name")
    private String firstName;

    @Getter @Setter
    @Column(name = "last_name")
    private String lastName;

    @Getter @Setter
    @Column(name = "gender")
    @Enumerated(EnumType.STRING)
    private Gender gender;

    @Getter @Setter
    @Column(name = "dob")
    private Date dob;

    @Getter @Setter
    @Column(name = "note", length = 2055)
    private String note;

    @Getter @Setter
    @OneToMany(mappedBy = "employee", fetch = FetchType.EAGER)
    @OrderBy("id")
    private SortedSet<EmploymentHistory> employmentHistories;

    @Getter @Setter
    @OneToMany(mappedBy = "employee", fetch = FetchType.EAGER)
    @OrderBy("id")
    private SortedSet<EmployeeAllowance> employeeAllowances;

    @Getter @Setter
    @JoinColumn(name = "user_id")
    @OneToOne(fetch = FetchType.LAZY)
    private User user;
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
        Employee other = (Employee) obj;
        if(id == null) {
            if(other.id != null)
                return false;
        } else if(!id.equals(other.id)) {
            return false;
        }
        return true;
    }

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + ((id == null) ? 0 : id.hashCode());
        return result;
    }
    //endregion

    //region > Programmatic
    public EmploymentHistory getActiveEmploymentHistory(){
        return employmentHistories!=null?
                employmentHistories.stream()
                        .filter(h -> h.getActiveDate().compareTo(new java.util.Date())<=0)
                        //.sorted((h1, h2) -> h1.getFromDate().compareTo(h2.getFromDate()))
                        .sorted(Comparator.comparing(EmploymentHistory::getActiveDate).reversed())
                        .findFirst().orElse(null)
                :null;
    }
    public Branch getBranch(){
        Branch branch = null;
        if (getActiveEmploymentHistory() != null) {
            branch = getActiveEmploymentHistory().getBranch();
        }
        return branch;
    }

    public Department getDepartment(){
        Department department = null;
        if (getActiveEmploymentHistory() != null) {
            department = getActiveEmploymentHistory().getDepartment();
        }
        return department;
    }

    public Designation getDesignation(){
        Designation designation = null;
        if (getActiveEmploymentHistory() != null) {
            designation = getActiveEmploymentHistory().getDesignation();
        }
        return designation;
    }

    public Employee getSupervisor(){
        Employee supervisor = null;
        if (getActiveEmploymentHistory() != null) {
            supervisor = getActiveEmploymentHistory().getSupervisor();
        }
        return supervisor;
    }

    public Date getDateOfHire(){
        EmploymentHistory employmentHistory = employmentHistories!=null?employmentHistories.stream()
                .filter(h -> h.getHistoryType() == HistoryType.JOIN)
                .findFirst().orElse(null):null;
        return employmentHistory!=null?employmentHistory.getActiveDate():null;
    }

    public String getFullName(){
        return getLastName() + " " + getFirstName();
    }

    public void addAllowance(final EmployeeAllowance employeeAllowance){
        if(this.employeeAllowances == null) {
            employeeAllowances = new TreeSet<>();
        }
        employeeAllowances.add(employeeAllowance);
    }

    public void removeAllowance(final EmployeeAllowance employeeAllowance){
        employeeAllowances.remove(employeeAllowance);
    }
    //endregion
}
