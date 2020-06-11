package spaceStation.models.astronauts;

import spaceStation.models.bags.Backpack;
import spaceStation.models.bags.Bag;

import static spaceStation.common.ExceptionMessages.ASTRONAUT_NAME_NULL_OR_EMPTY;
import static spaceStation.common.ExceptionMessages.ASTRONAUT_OXYGEN_LESS_THAN_ZERO;

public abstract class BaseAstronaut implements Astronaut {

    private String name;
    private double oxygen;
    private Bag bag;
    private double OXYGEN_TO_BREATH = 10;

    public BaseAstronaut(String name, double oxygen) {
        setName(name);
        setOxygen(oxygen);
        bag = new Backpack();
    }

    @Override
    public String getName() {
        return this.name;
    }

    private void setName(String name) {
        if (name == null || name.trim().isEmpty()) {
            throw new NullPointerException(ASTRONAUT_NAME_NULL_OR_EMPTY);
        }
        this.name = name;
    }

    @Override
    public double getOxygen() {
        return this.oxygen;
    }

    private void setOxygen(double oxygen) {
        if (oxygen < 0) {
            throw new IllegalArgumentException(ASTRONAUT_OXYGEN_LESS_THAN_ZERO);
        }
        this.oxygen = oxygen;
    }

    @Override
    public Bag getBag() {
        return this.bag;
    }

    void setNeededOxygen(double oxygen) {
        OXYGEN_TO_BREATH = oxygen;
    }

    @Override
    public void breath() {
        try {
            this.setOxygen(this.getOxygen() - OXYGEN_TO_BREATH);
        } catch (IllegalArgumentException ex) {
            this.setOxygen(0);
        }
    }

    @Override
    public boolean canBreath() {
        return oxygen >= OXYGEN_TO_BREATH;
    }
}
