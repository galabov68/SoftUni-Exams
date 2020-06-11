package mostwanted.domain.entities;

import javax.persistence.*;
import java.util.Set;

@Entity
@Table(name = "districts")
public class District extends BaseEntity {
    @Column(nullable = false, unique = true)
    private String name;
    @ManyToOne
    @JoinColumn(name = "town_id", referencedColumnName = "id")
    private Town town;
    @OneToMany(mappedBy = "district", targetEntity = Race.class,
            fetch = FetchType.EAGER, cascade = CascadeType.ALL)
    private Set<Race> races;

    public District() {
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Town getTown() {
        return town;
    }

    public void setTown(Town town) {
        this.town = town;
    }

    public Set<Race> getRaces() {
        return races;
    }

    public void setRaces(Set<Race> races) {
        this.races = races;
    }
}
