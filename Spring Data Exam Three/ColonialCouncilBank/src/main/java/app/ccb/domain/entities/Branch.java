package app.ccb.domain.entities;

import javax.persistence.*;
import java.util.Set;

@Entity
@Table(name = "branches")
public class Branch extends BaseEntity {
    @Column(nullable = false)
    private String name;
    @OneToMany(mappedBy = "branch", targetEntity = Employee.class,
            fetch = FetchType.EAGER, cascade = CascadeType.ALL)
    private Set<Employee> employees;

    public Branch() {
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Set<Employee> getEmployees() {
        return employees;
    }

    public void setEmployees(Set<Employee> employees) {
        this.employees = employees;
    }
}
