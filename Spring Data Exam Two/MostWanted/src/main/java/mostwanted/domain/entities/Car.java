package mostwanted.domain.entities;

import javax.persistence.*;
import java.math.BigDecimal;
import java.util.Set;

@Entity
@Table(name = "cars")
public class Car extends BaseEntity {
    @Column(nullable = false)
    private String brand;

    @Column(nullable = false)
    private String model;

    @Column
    private BigDecimal price;

    @Column(name = "production_year")
    private int yearOfProduction;

    @Column(name = "max_speed")
    private double maxSpeed;

    @Column(name = "zero_to_sixty")
    private double zeroToSixty;

    @ManyToOne
    @JoinColumn(name = "racer_id", referencedColumnName = "id")
    private Racer racer;

    @OneToMany(mappedBy = "car", targetEntity = RaceEntry.class,
            fetch = FetchType.EAGER, cascade = CascadeType.ALL)
    private Set<RaceEntry> raceEntries;

    public Car() {
    }

    public String getBrand() {
        return brand;
    }

    public void setBrand(String brand) {
        this.brand = brand;
    }

    public String getModel() {
        return model;
    }

    public void setModel(String model) {
        this.model = model;
    }

    public BigDecimal getPrice() {
        return price;
    }

    public void setPrice(BigDecimal price) {
        this.price = price;
    }

    public int getYearOfProduction() {
        return yearOfProduction;
    }

    public void setYearOfProduction(int yearOfProduction) {
        this.yearOfProduction = yearOfProduction;
    }

    public double getMaxSpeed() {
        return maxSpeed;
    }

    public void setMaxSpeed(double maxSpeed) {
        this.maxSpeed = maxSpeed;
    }

    public double getZeroToSixty() {
        return zeroToSixty;
    }

    public void setZeroToSixty(double zeroToSixty) {
        this.zeroToSixty = zeroToSixty;
    }

    public Racer getRacer() {
        return racer;
    }

    public void setRacer(Racer racer) {
        this.racer = racer;
    }

    public Set<RaceEntry> getRaceEntries() {
        return raceEntries;
    }

    public void setRaceEntries(Set<RaceEntry> raceEntries) {
        this.raceEntries = raceEntries;
    }
}
