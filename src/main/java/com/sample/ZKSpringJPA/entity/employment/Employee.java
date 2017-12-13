package com.sample.ZKSpringJPA.entity.employment;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.io.Serializable;
import java.sql.Date;
import java.util.List;

@Entity
@Table(name="employee")
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
    private List<EmploymentHistory> employmentHistories;

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
    //endregion

}
