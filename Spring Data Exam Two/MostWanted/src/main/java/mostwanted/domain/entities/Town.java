package mostwanted.domain.entities;

import javax.persistence.*;
import java.util.Set;

@Entity
@Table(name = "towns")
public class Town extends BaseEntity {
    @Column(nullable = false, unique = true)
    private String name;
    @OneToMany(mappedBy = "town", targetEntity = District.class,
            fetch = FetchType.EAGER, cascade = CascadeType.ALL)
    private Set<District> districts;
    @OneToMany(mappedBy = "homeTown", targetEntity = Racer.class,
            fetch = FetchType.EAGER, cascade = CascadeType.ALL)
    private Set<Racer> racers;

    public Town() {
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Set<District> getDistricts() {
        return districts;
    }

    public void setDistricts(Set<District> districts) {
        this.districts = districts;
    }

    public Set<Racer> getRacers() {
        return racers;
    }

    public void setRacers(Set<Racer> racers) {
        this.racers = racers;
    }
}
