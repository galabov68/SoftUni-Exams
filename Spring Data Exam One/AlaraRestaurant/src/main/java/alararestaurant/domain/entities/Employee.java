package alararestaurant.domain.entities;

import javax.persistence.*;
import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import javax.validation.constraints.Size;
import java.util.Set;

@Entity
@Table(name = "employees")
public class Employee extends BaseEntity {
    @Column(nullable = false)
    @Size(min = 3, max = 30)
    private String name;
    @Column(nullable = false)
    @Min(value = 15)
    @Max(value = 80)
    private int age;
    @ManyToOne(optional = false)
    @JoinColumn(name = "position_id", referencedColumnName = "id")
    private Position position;
    @OneToMany(mappedBy = "employee", targetEntity = Order.class,
            fetch = FetchType.EAGER, cascade = CascadeType.ALL)
    private Set<Order> orders;

    public Employee() {
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getAge() {
        return age;
    }

    public void setAge(int age) {
        this.age = age;
    }

    public Position getPosition() {
        return position;
    }

    public void setPosition(Position position) {
        this.position = position;
    }

    public Set<Order> getOrders() {
        return orders;
    }

    public void setOrders(Set<Order> orders) {
        this.orders = orders;
    }
}
