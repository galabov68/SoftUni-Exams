package mostwanted.domain.entities;

import javax.persistence.*;
import java.util.Set;

@Entity
@Table(name = "races")
public class Race extends BaseEntity {
    @Column(nullable = false, columnDefinition = "int default 0")
    private int laps;
    @ManyToOne(optional = false)
    @JoinColumn(name = "district_id", referencedColumnName = "id")
    private District district;
    @OneToMany(mappedBy = "race", targetEntity = RaceEntry.class,
            fetch = FetchType.EAGER, cascade = CascadeType.ALL)
    private Set<RaceEntry> raceEntries;

    public Race() {
    }

    public int getLaps() {
        return laps;
    }

    public void setLaps(int laps) {
        this.laps = laps;
    }

    public District getDistrict() {
        return district;
    }

    public void setDistrict(District district) {
        this.district = district;
    }

    public Set<RaceEntry> getRaceEntries() {
        return raceEntries;
    }

    public void setRaceEntries(Set<RaceEntry> raceEntries) {
        this.raceEntries = raceEntries;
    }
}
