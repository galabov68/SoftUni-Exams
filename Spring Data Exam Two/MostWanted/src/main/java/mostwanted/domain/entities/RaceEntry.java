package mostwanted.domain.entities;

import javax.persistence.*;

@Entity
@Table(name = "race_entries")
public class RaceEntry extends BaseEntity {
    @Column(name = "has_finished")
    private boolean hasFinished;
    @Column(name = "finish_time")
    private double finishTime;
    @ManyToOne
    @JoinColumn(name = "car_id", referencedColumnName = "id")
    private Car car;
    @ManyToOne
    @JoinColumn(name = "racer", referencedColumnName = "id")
    private Racer racer;
    @ManyToOne
    @JoinColumn(name = "race_id", referencedColumnName = "id")
    private Race race;

    public RaceEntry() {
    }

    public boolean isHasFinished() {
        return hasFinished;
    }

    public void setHasFinished(boolean hasFinished) {
        this.hasFinished = hasFinished;
    }

    public double getFinishTime() {
        return finishTime;
    }

    public void setFinishTime(double finishTime) {
        this.finishTime = finishTime;
    }

    public Car getCar() {
        return car;
    }

    public void setCar(Car car) {
        this.car = car;
    }

    public Racer getRacer() {
        return racer;
    }

    public void setRacer(Racer racer) {
        this.racer = racer;
    }

    public Race getRace() {
        return race;
    }

    public void setRace(Race race) {
        this.race = race;
    }
}
