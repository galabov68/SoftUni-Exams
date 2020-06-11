package mostwanted.domain.entities;

import javax.persistence.*;
import java.math.BigDecimal;
import java.util.Set;

@Entity
@Table(name = "racers")
public class Racer extends BaseEntity {
    @Column(nullable = false, unique = true)
    private String name;
    @Column
    private int age;
    @Column
    private BigDecimal bounty;
    @ManyToOne
    @JoinColumn(name = "town_id", referencedColumnName = "id")
    private Town homeTown;
    @OneToMany(mappedBy = "racer", targetEntity = Car.class,
            fetch = FetchType.EAGER, cascade = CascadeType.ALL)
    private Set<Car> cars;
    @OneToMany(mappedBy = "racer", targetEntity = RaceEntry.class,
            fetch = FetchType.EAGER, cascade = CascadeType.ALL)
    private Set<RaceEntry> raceEntries;

    public Racer() {
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

    public BigDecimal getBounty() {
        return bounty;
    }

    public void setBounty(BigDecimal bounty) {
        this.bounty = bounty;
    }

    public Town getHomeTown() {
        return homeTown;
    }

    public void setHomeTown(Town homeTown) {
        this.homeTown = homeTown;
    }

    public Set<Car> getCars() {
        return cars;
    }

    public void setCars(Set<Car> cars) {
        this.cars = cars;
    }

    public Set<RaceEntry> getRaceEntries() {
        return raceEntries;
    }

    public void setRaceEntries(Set<RaceEntry> raceEntries) {
        this.raceEntries = raceEntries;
    }
}
