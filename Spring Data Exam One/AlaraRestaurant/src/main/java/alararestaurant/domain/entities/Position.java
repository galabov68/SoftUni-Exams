package alararestaurant.domain.entities;

import javax.persistence.*;
import javax.validation.constraints.Size;
import java.util.Set;

@Entity
@Table(name = "positions")
public class Position extends BaseEntity {
    @Column(nullable = false, unique = true)
    @Size(min = 3, max = 30)
    private String name;
    @OneToMany(mappedBy = "position", targetEntity = Employee.class,
            fetch = FetchType.EAGER, cascade = CascadeType.ALL)
    private Set<Employee> employees;

    public Position() {
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
